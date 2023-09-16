package com.taogen.commons.jsonparser.orgjson;

import com.taogen.commons.jsonparser.orgjson.entity.Role;
import com.taogen.commons.jsonparser.orgjson.entity.User;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class OrgJsonUtilTest {

    public static final String json = "{\"id\":1,\"name\":\"Jack\",\"age\":18,\"birth_date\":\"1990-01-01\", \"roles\": [{\"id\":1,\"name\":\"admin\"},{\"id\":2,\"name\":\"user\"}]}";
    public static final String jsonRoleArray = "[{\"id\":1,\"name\":\"admin\"},{\"id\":2,\"name\":\"user\"}]";
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Map<String, Object> USER_MAP = new HashMap<>();
    public static User user;
    public static List<Role> roles;

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

    static {
        roles = Arrays.asList(new Role(1L, "admin"), new Role(2L, "user"));
        try {
            user = new User().builder()
                    .id(1L)
                    .name("Jack")
                    .age(18)
                    .birthDate(dateFormat.parse("1990-01-01"))
                    .roles(roles)
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void jsonStrToJsonObject() {
        JSONObject jsonObject = OrgJsonUtil.jsonStrToJsonObject(json);
        log.debug(jsonObject);
        validateJsonObject(jsonObject);
    }

    private void validateJsonObject(JSONObject jsonObject) {
        assertNotNull(jsonObject);
        assertEquals(1L, jsonObject.getLong("id"));
        assertEquals("Jack", jsonObject.getString("name"));
        assertEquals(18, jsonObject.getInt("age"));
        assertEquals("1990-01-01", jsonObject.getString("birth_date"));
    }

    @Test
    void entityToJsonObject() {
        JSONObject jsonObject = OrgJsonUtil.entityToJsonObject(user);
        log.info(jsonObject);
        assertNotNull(jsonObject);
        assertEquals(1L, jsonObject.getLong("id"));
    }

    @Test
    void mapToJsonObject() {
        JSONObject jsonObject = OrgJsonUtil.mapToJsonObject(USER_MAP);
        log.debug(jsonObject);
        assertNotNull(jsonObject);
        assertEquals(1L, jsonObject.getLong("id"));
        assertEquals("Jack", jsonObject.getString("name"));
        assertEquals(18, jsonObject.getInt("age"));
        assertEquals("1990-01-01", jsonObject.getString("birth_date"));
        JSONArray roleArray = jsonObject.getJSONArray("roles");
        assertNotNull(roleArray);
        assertEquals(2, roleArray.length());
        assertEquals(1L, roleArray.getJSONObject(0).getLong("id"));
        assertEquals("admin", roleArray.getJSONObject(0).getString("name"));
    }

    @Test
    void jsonObjectToJsonStr() {
        String jsonStr = OrgJsonUtil.jsonObjectToJsonStr(OrgJsonUtil.jsonStrToJsonObject(json));
        log.debug(jsonStr);
        assertNotNull(jsonStr);
        assertTrue(jsonStr.contains("\"id\":1,"));
    }

    @Test
    void jsonStrToJsonArray() {
        JSONArray jsonArray = OrgJsonUtil.jsonStrToJsonArray(jsonRoleArray);
        log.debug(jsonArray);
        validateJsonArray(jsonArray);
    }

    private void validateJsonArray(JSONArray jsonArray) {
        assertNotNull(jsonArray);
        assertEquals(2, jsonArray.length());
    }

    @Test
    void entityToJsonArray() {
        JSONArray jsonArray = OrgJsonUtil.entityToJsonArray(roles);
        log.debug(jsonArray);
        validateJsonArray(jsonArray);
    }

    @Test
    void jsonArrayToJsonStr() {
        String jsonStr = OrgJsonUtil.jsonArrayToJsonStr(OrgJsonUtil.jsonStrToJsonArray(jsonRoleArray));
        log.debug(jsonStr);
        assertNotNull(jsonStr);
        assertTrue(jsonStr.contains("\"id\":1"));
    }
}
