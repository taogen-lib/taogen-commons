package com.taogen.common.jsonparser.jackson;


import com.taogen.common.jsonparser.jackson.entity.Role;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Taogen
 */
public class BaseTest {
    public static final String json = "{\"id\":1,\"name\":\"Jack\",\"age\":18,\"birth_date\":\"1990-01-01\", \"roles\": [{\"id\":1,\"name\":\"admin\"},{\"id\":2,\"name\":\"user\"}]}";
    public static final String jsonRoleArray = "[{\"id\":1,\"name\":\"admin\"},{\"id\":2,\"name\":\"user\"}]";
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Map<String, Object> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put("id", 1);
        USER_MAP.put("name", "Jack");
        USER_MAP.put("age", 18);
        USER_MAP.put("birth_date", "1990-01-01");
        USER_MAP.put("roles", Arrays.asList(
                new Role(1L, "admin"),
                new Role(2L, "user")
        ));
    }

    protected void validateJsonStr(String jsonStr) {
        assertNotNull(jsonStr);
        assertTrue(jsonStr.contains("\"id\":1,"));
        assertTrue(jsonStr.contains("\"name\":\"Jack\""));
        assertTrue(jsonStr.contains("\"age\":18"));
        assertTrue(jsonStr.contains("\"birth_date\":\"1990-01-01\""));
        assertTrue(jsonStr.contains("roles"));
    }

    protected void validateMap(Map<String, Object> map) {
        assertNotNull(map);
        assertEquals(1L, Long.valueOf(map.get("id").toString()));
        assertEquals("Jack", map.get("name"));
        assertEquals(18, Integer.valueOf(map.get("age").toString()));
        assertEquals("1990-01-01", map.get("birth_date"));
        assertNotNull(map.get("roles"));
        List<Map<String, Object>> roles = (List<Map<String, Object>>) map.get("roles");
        assertEquals(2, roles.size());
    }
}
