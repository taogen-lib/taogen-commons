package com.taogen.commons.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.taogen.commons.collection.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Taogen
 */
public class MyBatisPlusBusinessLogicUtil {
    public static <T, S> Set<S> getDescendantIds(Integer rootId,
                                             BaseMapper baseMapper,
                                             SFunction<T, S> getId,
                                             SFunction<T, S> getParentId) {
        List<Object> descendantIds = new ArrayList<>();
        List<Object> childIds = baseMapper.selectObjs(new LambdaQueryWrapper<T>()
                .select(getId)
                .eq(getParentId, rootId));
        while (!CollectionUtils.isEmpty(childIds)) {
            descendantIds.addAll(childIds);
            childIds = baseMapper.selectObjs(new LambdaQueryWrapper<T>()
                    .select(getId)
                    .in(getParentId, childIds));
        }
        return descendantIds.stream()
                .filter(Objects::nonNull)
                .map(item -> (S) item)
                .collect(Collectors.toSet());
    }
}
