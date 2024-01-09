package com.taogen.commons;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
class FetchDataTest {

    private static Map<String, Object> getPage(Map<String, Object> params) {
        log.debug("params: {}", params);
        Integer pageNum = (Integer) params.get("pageNum");
        Map<String, Object> result = new HashMap<>();
        result.put("total", 3);
        if (pageNum == 1) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", 1);
            row.put("name", "Jack");
            Map<String, Object> row2 = new HashMap<>();
            row2.put("id", 2);
            row2.put("name", "Tom");
            result.put("rows", Arrays.asList(row, row2));
        } else {
            Map<String, Object> row = new HashMap<>();
            row.put("id", 3);
            row.put("name", "Lucy");
            result.put("rows", Arrays.asList(row));
        }
        log.debug("result: {}", result);
        return result;
    }

    @Test
    void getAllPage() {
        BiFunction<Integer, Integer, Map<String, Object>> buildParams = (pageNum, pageSize) -> {
            Map<String, Object> param = new HashMap<>();
            param.put("pageNum", pageNum);
            param.put("pageSize", pageSize);
            return param;
        };
        List<Map<String, Object>> allPage = FetchData.getAllPage(
                FetchDataTest::getPage,
                buildParams, 2,
                item -> (Integer) item.get("total"),
                item -> (List) item.get("rows"));
        log.debug("allPage: {}", allPage);
        assertEquals(3, allPage.size());
    }
}
