package com.taogen.commons;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * @author taogen
 */
@Slf4j
public class FetchData {
    /**
     * Get All Page
     *
     * @param requestPage          params JSONObject -> response JSONObject
     * @param buildParams          pageNum, pageSize -> JSONObject. pageNum start from 1.
     * @param pageSize
     * @param getTotalFromResponse response JSONObject -> total count
     * @param getRowsFromResponse  response JSONObject -> rows
     * @param <P>                  request params type (e.g. JSONObject, Map, User, etc.)
     * @param <T>                  response type (e.g. JSONObject, Map, etc.)
     * @return
     */
    public static <P, T> List<T> getAllPage(Function<P, T> requestPage,
                                            BiFunction<Integer, Integer, P> buildParams,
                                            Integer pageSize,
                                            ToIntFunction<T> getTotalFromResponse,
                                            Function<T, List<T>> getRowsFromResponse) {
        List<T> result = new ArrayList<>();
        Integer pageNum = 1;
        T firstResponse = requestPage.apply(buildParams.apply(pageNum, pageSize));
        Integer totalCount = getTotalFromResponse.applyAsInt(firstResponse);
        log.debug("total count: {}", totalCount);
        List<T> firstPage = getRowsFromResponse.apply(firstResponse);
        result.addAll(firstPage);
        while (pageSize * pageNum < totalCount) {
            pageNum++;
            T response = requestPage.apply(buildParams.apply(pageNum, pageSize));
            List<T> page = getRowsFromResponse.apply(response);
            result.addAll(page);
        }
        log.debug("actual count: {}", result.size());
        return result;
    }
}
