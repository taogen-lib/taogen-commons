package com.taogen.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapUtilsTest {

    @Test
    void multiStringValueMapToMultiObjectValueMap() {
        // TODO
    }

    @Test
    void multiObjectValueMapToMultiStringValueMap() {
        // TODO
    }

    /**
     *
     */
    @Test
    void multiValueMapEquals() {
        String key = null;
        // null null -> true
        assertTrue(MapUtils.multiValueMapEquals(null, null));
        // null empty -> true
        assertTrue(MapUtils.multiValueMapEquals(null, Collections.emptyMap()));
        // empty null -> true
        assertTrue(MapUtils.multiValueMapEquals(Collections.emptyMap(), null));
        // empty empty -> true
        assertTrue(MapUtils.multiValueMapEquals(Collections.emptyMap(), Collections.emptyMap()));
        // map size is not equal -> false
        key = "map size is not equal -> false";
        Map<String, List<Object>> map1 = new HashMap<>();
        map1.put(key, null);
        map1.put(key + "2", null);
        Map<String, List<Object>> map2 = new HashMap<>();
        map2.put(key, null);
        assertFalse(MapUtils.multiValueMapEquals(map1, map2));
        // list=null list=null -> true
        key = "list=null list=null -> true";
        map1.clear();
        map1.put(key, null);
        map2.clear();
        map2.put(key, null);
        assertTrue(MapUtils.multiValueMapEquals(map1, map2));
        // list=null list=empty -> true
        key = "list=null list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, null);
        assertTrue(MapUtils.multiValueMapEquals(map1, map2));
        // list=empty list=empty -> true
        key = "list=empty list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, Collections.emptyList());
        assertTrue(MapUtils.multiValueMapEquals(map1, map1));
        // list size is not equal -> false
        key = "list size is not equal -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1)));
        assertFalse(MapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal -> true
        key = "list size equal and list values equal -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        assertTrue(MapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal but type is different -> true
        key = "list size equal and list values equal but type is different -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList("1", 2)));
        assertTrue(MapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values not equals -> false
        key = "list size equal and list values not equals -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(2, 3)));
        assertFalse(MapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal but order is different -> true
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2, null, 3)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(2, 1, null, 3)));
        assertTrue(MapUtils.multiValueMapEquals(map1, map2));
    }

    @Test
    void multiStringValueMapEquals() {
        // TODO
    }
}