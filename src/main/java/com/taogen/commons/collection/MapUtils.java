package com.taogen.commons.collection;

import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * @author Taogen
 */
@Log4j2
public class MapUtils {
    public static boolean isEmpty(Map<String, Object> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<String, Object> map) {
        return map != null && !map.isEmpty();
    }
}
