package com.taogen.commons.network;

import com.taogen.commons.collection.MapUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class HttpRequestUtilTest {

    @Test
    void multiValueMapToQueryString() {
        String queryString;
        LinkedHashMap<String, List<Object>> map = new LinkedHashMap<>();
        // map is null
        queryString = HttpRequestUtil.multiValueMapToQueryString(null);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("", queryString);
        // map is empty
        map.clear();
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("", queryString);
        // key value is null
        map.clear();
        map.put("key1", null);
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=", queryString);
        // key value is null
        map.clear();
        map.put("key1", null);
        map.put("key2", null);
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=&key2=", queryString);
        // key value is empty
        map.clear();
        map.put("key1", new ArrayList<>());
        map.put("key2", new ArrayList<>());
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=&key2=", queryString);
        // key 1 value is null, key 2 has value
        map.clear();
        map.put("key1", null);
        map.put("key2", Arrays.asList("abc"));
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=&key2=abc", queryString);
        // key 1 has value, key 2 value is null
        map.clear();
        map.put("key1", Arrays.asList("abc"));
        map.put("key2", null);
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=abc&key2=", queryString);
        // key 1 has multiple value, key 2 has one value
        map.clear();
        map.put("key1", Arrays.asList(1, 2, 3));
        map.put("key2", Arrays.asList("abc"));
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=1&key1=2&key1=3&key2=abc", queryString);
        // key 1 has one value, key 2 has multiple value
        map.clear();
        map.put("key1", Arrays.asList("abc"));
        map.put("key2", Arrays.asList(1, 2, 3));
        queryString = HttpRequestUtil.multiValueMapToQueryString(map);
        log.debug("queryString: {}", queryString);
        assertNotNull(queryString);
        assertEquals("key1=abc&key2=1&key2=2&key2=3", queryString);
    }

    @Test
    void queryStringToMultiValueMap() {
        Map<String, List<Object>> expectMap = new LinkedHashMap<>();
        Map<String, List<Object>> returnMap = null;
        // queryString is null
        returnMap = HttpRequestUtil.queryStringToMultiValueMap(null);
        log.debug("returnMap: {}", returnMap);
        assertNotNull(returnMap);
        assertTrue(returnMap.isEmpty());
        // queryString is ""
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("");
        log.debug("returnMap: {}", returnMap);
        assertNotNull(returnMap);
        assertTrue(returnMap.isEmpty());
        // key value is null
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=");
        log.debug("returnMap: {}", returnMap);
        expectMap.put("key1", new ArrayList<>());
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
        // multiple key value is null
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=&key2=");
        log.debug("returnMap: {}", returnMap);
        expectMap = new LinkedHashMap<>();
        expectMap.put("key1", new ArrayList<>());
        expectMap.put("key2", new ArrayList<>());
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
        // key1 value is null, key 2 has value
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=&key2=abc");
        log.debug("returnMap: {}", returnMap);
        expectMap = new LinkedHashMap<>();
        expectMap.put("key1", new ArrayList<>());
        expectMap.put("key2", Arrays.asList("abc"));
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
        // key1 has value, key2 value is null
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=abc&key2=");
        log.debug("returnMap: {}", returnMap);
        expectMap = new LinkedHashMap<>();
        expectMap.put("key1", Arrays.asList("abc"));
        expectMap.put("key2", new ArrayList<>());
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
        // key 1 has one value, key 2 has multiple values
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=1&key1=2&key1=3&key2=abc");
        log.debug("returnMap: {}", returnMap);
        expectMap = new LinkedHashMap<>();
        expectMap.put("key1", Arrays.asList(1, 2, 3));
        expectMap.put("key2", Arrays.asList("abc"));
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
        // key 1 has multiple values, key 2 has one value
        returnMap = HttpRequestUtil.queryStringToMultiValueMap("key1=abc&key2=1&key2=2&key2=3");
        log.debug("returnMap: {}", returnMap);
        expectMap = new LinkedHashMap<>();
        expectMap.put("key1", Arrays.asList("abc"));
        expectMap.put("key2", Arrays.asList(1, 2, 3));
        assertTrue(MapUtils.multiValueMapEquals(expectMap, returnMap));
    }

    @Test
    void multiValueMapToMultipartData() throws IOException {
    }

    @Test
    void multipartDataToMultiValueMap() throws IOException {
        String s = "--boundary\n" +
                "Content-Disposition: form-data; name=\"key1\"\n" +
                "Content-Length: 1\n" +
                "\n" +
                "1\n" +
                "--boundary\n" +
                "Content-Disposition: form-data; name=\"key1\"\n" +
                "\n" +
                "2\n" +
                "--boundary\n" +
                "Content-Disposition: form-data; name=\"key1\"\n" +
                "\n" +
                "3\n" +
                "--boundary\n" +
                "Content-Disposition: form-data; name=\"key2\"\n" +
                "\n" +
                "abc\n" +
                "--boundary\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"a.txt\"\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "Content of a.txt." +
                "--boundary--\n";
        String boundary = "--boundary";
        Map<String, List<Object>> map = HttpRequestUtil.multipartDataToMultiValueMap(s.getBytes(StandardCharsets.UTF_8), boundary);
        log.debug("map: {}", map);
        Map<String, List<Object>> expectMap = new LinkedHashMap<>();
        expectMap.put("key1", Arrays.asList(1, 2, 3));
        expectMap.put("key2", Arrays.asList("abc"));
        expectMap.put("file", Arrays.asList("Content of a.txt."));
        assertTrue(MapUtils.multiValueMapEquals(expectMap, map));
    }

    @Test
    void multipartDataToMultiValueMap_2() throws IOException {
        String s = "--a7fcff8f-4651-4ff7-afd1-29ed4fad0bd0\r\n" +
                "Content-Disposition: form-data; name=\"name\"\r\n" +
                "Content-Length: 4\r\n" +
                "\r\n" +
                "test\r\n" +
                "--a7fcff8f-4651-4ff7-afd1-29ed4fad0bd0\r\n" +
                "Content-Disposition: form-data; name=\"name\"\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n" +
                "test2\r\n" +
                "--a7fcff8f-4651-4ff7-afd1-29ed4fad0bd0\r\n" +
                "Content-Disposition: form-data; name=\"id\"\r\n" +
                "Content-Length: 1\r\n" +
                "\r\n" +
                "1\r\n" +
                "--a7fcff8f-4651-4ff7-afd1-29ed4fad0bd0--\r\n";
        String boundary = "a7fcff8f-4651-4ff7-afd1-29ed4fad0bd0";
        Map<String, List<Object>> map2 = HttpRequestUtil.multipartDataToMultiValueMap(s.getBytes(StandardCharsets.UTF_8), boundary);
        log.debug("map2: {}", map2);
        Map<String, List<Object>> expectMap2 = new LinkedHashMap<>();
        expectMap2.put("name", Arrays.asList("test", "test2"));
        expectMap2.put("id", Arrays.asList(1));
        assertTrue(MapUtils.multiValueMapEquals(expectMap2, map2));
    }

    @Test
    void getBoundaryByContentType() {
        String boundary = "50cd708f-7757-4c88-8852-9729d5450dc2";
        String contentType = "multipart/form-data; boundary=" + boundary;
        assertEquals(boundary, HttpRequestUtil.getBoundaryByContentType(contentType));
    }
}