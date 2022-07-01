package com.taogen.commons.collection;

import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Taogen
 */
@Log4j2
public class MapUtils {
    public static Map<String, List<Object>> multiStringValueMapToMultiObjectValueMap(Map<String, List<String>> multiStringValueMap) {
        if (multiStringValueMap == null) {
            return null;
        }
        Map<String, List<Object>> multiObjectValueMap = new HashMap<>(multiStringValueMap.size());
        multiStringValueMap.forEach((key, value) -> {
            List<Object> objectList = null;
            if (value != null) {
                objectList = new ArrayList<>(value);
            }
            multiObjectValueMap.put(key, objectList);
        });
        return multiObjectValueMap;
    }

    public static Map<String, List<String>> multiObjectValueMapToMultiStringValueMap(Map<String, List<Object>> multiObjectValueMap) {
        if (multiObjectValueMap == null) {
            return null;
        }
        Map<String, List<String>> multiStringValueMap = new HashMap<>(multiObjectValueMap.size());
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

    public static boolean multiValueMapEquals(Map<String, List<Object>> a, Map<String, List<Object>> b) {
        if (a == null) {
            a = Collections.emptyMap();
        }
        if (b == null) {
            b = Collections.emptyMap();
        }
        if (a.isEmpty() && b.isEmpty()) {
            return true;
        }
        if (a.size() != b.size()) {
            return false;
        }
        Set<String> keys = a.keySet();
        for (String key : keys) {
            log.debug("key is {}", key);
            List<?> aValues = a.get(key);
            if (aValues == null) {
                aValues = Collections.emptyList();
            }
            List<?> bValues = b.get(key);
            if (bValues == null) {
                bValues = Collections.emptyList();
            }
            if (aValues.isEmpty() && bValues.isEmpty()) {
                continue;
            }
            if (aValues.size() != bValues.size()) {
                return false;
            }
            log.debug("before sort: aValue is {}", aValues);
            log.debug("before sort: bValue is {}", bValues);
            Collections.sort(aValues, Comparator.comparing(item ->
                            Objects.toString(item, null),
                    Comparator.nullsLast(Comparator.naturalOrder())));
            Collections.sort(bValues, Comparator.comparing(item ->
                            Objects.toString(item, null),
                    Comparator.nullsLast(Comparator.naturalOrder())));
            log.debug("after sort: aValue is {}", aValues);
            log.debug("after sort: bValue is {}", bValues);
            for (int i = 0; i < aValues.size(); i++) {
                if (!String.valueOf(aValues.get(i)).equals(String.valueOf(bValues.get(i)))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean multiStringValueMapEquals(Map<String, List<String>> a, Map<String, List<String>> b) {
        Map<String, List<Object>> aMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(a);
        Map<String, List<Object>> bMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(b);
        return multiValueMapEquals(aMultiObjectValueMap, bMultiObjectValueMap);
    }

}
