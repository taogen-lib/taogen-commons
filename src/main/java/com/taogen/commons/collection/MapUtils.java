package com.taogen.commons.collection;

import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * convert multiStringValueMap to multiObjectValueMap
     *
     * @param multiStringValueMap
     * @return
     */
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

    /**
     * convert multiObjectValueMap to multiStringValueMap
     *
     * @param multiObjectValueMap
     * @return
     */
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

    /**
     * check if two multi value map equals
     * <p>
     * - sort list values.
     * - compare each value of list by toString()
     *
     * @param a
     * @param b
     * @return
     */
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

    /**
     * check if two multi value map equals
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean multiStringValueMapEquals(Map<String, List<String>> a, Map<String, List<String>> b) {
        Map<String, List<Object>> aMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(a);
        Map<String, List<Object>> bMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(b);
        return multiValueMapEquals(aMultiObjectValueMap, bMultiObjectValueMap);
    }

    /**
     * check if a contains b
     * <p>
     * - filter null value in list
     * - compare each value of list by toString()
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean multiValueMapContains(Map<String, List<Object>> a, Map<String, List<Object>> b) {
        if (a == null) {
            a = Collections.emptyMap();
        }
        if (b == null) {
            b = Collections.emptyMap();
        }
        if (a.isEmpty() && b.isEmpty()) {
            return true;
        }
        if (a.size() < b.size()) {
            return false;
        }
        Set<String> keys = b.keySet();
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
            if (aValues.size() < bValues.size()) {
                return false;
            }
            Set<String> aSet = aValues.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet());
            List<String> bValuesString = bValues.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
            for (String bValue : bValuesString) {
                if (!aSet.contains(bValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if a contains b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean multiStringValueMapContains(Map<String, List<String>> a, Map<String, List<String>> b) {
        Map<String, List<Object>> aMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(a);
        Map<String, List<Object>> bMultiObjectValueMap = multiStringValueMapToMultiObjectValueMap(b);
        return multiValueMapContains(aMultiObjectValueMap, bMultiObjectValueMap);
    }
}
