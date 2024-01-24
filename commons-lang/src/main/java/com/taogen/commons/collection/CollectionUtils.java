package com.taogen.commons.collection;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Taogen
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    private static List<String> getSubListByMaxCharNum(List<String> list, Integer maxCharNum) {
        int charNumSum = 0;
        int index = list.size();
        for (int i = 0; i < list.size(); i++) {
            charNumSum += list.get(i).length();
            if (charNumSum > maxCharNum) {
                index = i;
                break;
            }
        }
        return list.subList(0, index);
    }

    public static <T, K> List<T> convertListToTree(List<T> list,
                                                   Function<T, K> getId,
                                                   Function<T, K> getParentId,
                                                   BiConsumer<T, T> putChildren) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        Map<K, T> map = list.stream()
                .collect(Collectors.toMap(item -> getId.apply(item), Function.identity()));
        List<T> firstLevelNodeList = new ArrayList<>();
        for (T entity : list) {
            T parent = map.get(getParentId.apply(entity));
            if (parent != null) {
                putChildren.accept(parent, entity);
            } else {
                firstLevelNodeList.add(entity);
            }
        }
        return firstLevelNodeList;
    }

}
