package com.taogen.commons.collection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Taogen
 */
public class MapUtils {
    public static Map<String, List<Object>> multiStringValueMapToMultiObjectValueMap(Map<String, List<String>> multiStringValueMap) {
        if (multiStringValueMap == null) {
            return null;
        }
        Map<String, List<Object>> multiObjectValueMap = new HashMap<>();
        multiStringValueMap.forEach((key, value) -> {
            List<Object> objectList = null;
            if (value != null) {
                objectList = value.stream()
                        .collect(Collectors.toList());
            }
            multiObjectValueMap.put(key, objectList);
        });
        return multiObjectValueMap;
    }

    public static Map<String, List<String>> multiObjectValueMapToMultiStringValueMap(Map<String, List<Object>> multiObjectValueMap) {
        if (multiObjectValueMap == null) {
            return null;
        }
        Map<String, List<String>> multiStringValueMap = new HashMap<>();
        multiObjectValueMap.forEach((key, value) -> {
            List<String> stringList = null;
            if (value != null) {
                stringList = value.stream()
                        .map(object -> Objects.toString(object, null))
                        .collect(Collectors.toList());
            }
            multiStringValueMap.put(key, stringList);
        });
        return multiStringValueMap;
    }
}
