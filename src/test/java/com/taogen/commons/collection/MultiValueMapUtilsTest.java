package com.taogen.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MultiValueMultiValueMapUtilsTest {

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
        Map<String, List<Object>> map1 = new HashMap<>();
        Map<String, List<Object>> map2 = new HashMap<>();
        // null null -> true
        assertTrue(MultiValueMapUtils.multiValueMapEquals(null, null));
        // null empty -> true
        assertTrue(MultiValueMapUtils.multiValueMapEquals(null, Collections.emptyMap()));
        // empty null -> true
        assertTrue(MultiValueMapUtils.multiValueMapEquals(Collections.emptyMap(), null));
        // empty empty -> true
        assertTrue(MultiValueMapUtils.multiValueMapEquals(Collections.emptyMap(), Collections.emptyMap()));
        // map size is not equal -> false
        key = "map size is not equal -> false";
        map1.put(key, null);
        map1.put(key + "2", null);
        map2.put(key, null);
        assertFalse(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list=null list=null -> true
        key = "list=null list=null -> true";
        map1.clear();
        map1.put(key, null);
        map2.clear();
        map2.put(key, null);
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list=null list=empty -> true
        key = "list=null list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, null);
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list=empty list=empty -> true
        key = "list=empty list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, Collections.emptyList());
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map1));
        // list size is not equal -> false
        key = "list size is not equal -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1)));
        assertFalse(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal -> true
        key = "list size equal and list values equal -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal but type is different -> true
        key = "list size equal and list values equal but type is different -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList("1", 2)));
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values not equals -> false
        key = "list size equal and list values not equals -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(2, 3)));
        assertFalse(MultiValueMapUtils.multiValueMapEquals(map1, map2));
        // list size equal and list values equal but order is different -> true
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2, null, 3)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(2, 1, null, 3)));
        assertTrue(MultiValueMapUtils.multiValueMapEquals(map1, map2));
    }

    @Test
    void multiStringValueMapEquals() {
        // TODO
    }

    @Test
    void multiValueMapContains() {
        String key = null;
        Map<String, List<Object>> map1 = new HashMap<>();
        Map<String, List<Object>> map2 = new HashMap<>();
        // null null -> true
        assertTrue(MultiValueMapUtils.multiValueMapContains(null, null));
        // null empty -> true
        assertTrue(MultiValueMapUtils.multiValueMapContains(null, Collections.emptyMap()));
        // empty null -> true
        assertTrue(MultiValueMapUtils.multiValueMapContains(Collections.emptyMap(), null));
        // empty empty -> true
        assertTrue(MultiValueMapUtils.multiValueMapContains(Collections.emptyMap(), Collections.emptyMap()));
        // a key size is less than b key size -> false
        key = "a key size is less than b key size -> false";
        map1.put(key, null);
        map2.put(key, null);
        map2.put(key + "2", null);
        assertFalse(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // list=null list=null -> true
        key = "list=null list=null -> true";
        map1.clear();
        map1.put(key, null);
        map2.clear();
        map2.put(key, null);
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // list=null list=empty -> true
        key = "list=null list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, null);
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // list=empty list=empty -> true
        key = "list=empty list=empty -> true";
        map1.clear();
        map1.put(key, Collections.emptyList());
        map2.clear();
        map2.put(key, Collections.emptyList());
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map1));
        // a list size is less than b list size -> false
        key = "a list size is less than b list size -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1, 2, 3)));
        assertFalse(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // a list values contains b list values -> true
        key = "a list values contains b list values -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2, 3)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // a list values contains b list values but type is different -> true
        key = "a list values contains b list values but type is different -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2, 3)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList("1", 2)));
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // a list values don't contains b list values -> false
        key = "a list values don't contains b list values -> false";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(1, 2, 3)));
        assertFalse(MultiValueMapUtils.multiValueMapContains(map1, map2));
        // a list values contains b list values but order is different -> true
        key = "a list values contains b list values but order is different -> true";
        map1.clear();
        map1.put(key, new ArrayList<>(Arrays.asList(1, 2, null, 3, 4)));
        map2.clear();
        map2.put(key, new ArrayList<>(Arrays.asList(2, 1, null, 3)));
        assertTrue(MultiValueMapUtils.multiValueMapContains(map1, map2));
    }
}