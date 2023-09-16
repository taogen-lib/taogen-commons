package com.taogen.commons.jsonparser.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.taogen.commons.jsonparser.gson.entity.RoleForGson;
import com.taogen.commons.jsonparser.gson.entity.UserForGson;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class GsonUtilTest extends BaseTest {

    private static UserForGson user;
    private static List<RoleForGson> roles;

    static {
        roles = Arrays.asList(new RoleForGson(1L, "admin"), new RoleForGson(2L, "user"));
        try {
            user = new UserForGson().builder()
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
    void objectToJsonStr() {
        Map<Type, Object> typeAdapterMap = new HashMap<>();
        typeAdapterMap.put(Date.class, GsonUtil.getDateJsonSerializer("yyyy-MM-dd"));
        String jsonStr = GsonUtil.objectToJsonStr(user, typeAdapterMap);
        log.debug(jsonStr);
        super.validateJsonStr(jsonStr);
    }

    @Test
    void mapToJsonStr() {
        Map<Type, Object> typeAdapterMap = new HashMap<>();
        typeAdapterMap.put(Date.class, GsonUtil.getDateJsonSerializer("yyyy-MM-dd"));
        String jsonStr = GsonUtil.mapToJsonStr(super.USER_MAP, typeAdapterMap);
        log.debug(jsonStr);
        super.validateJsonStr(jsonStr);
    }

    @Test
    void jsonObjToJsonStr() {
        JsonObject jsonObj = GsonUtil.jsonStrToJsonObj(json);
        String jsonStr = GsonUtil.jsonObjToJsonStr(jsonObj);
        log.debug(jsonStr);
        super.validateJsonStr(jsonStr);
    }

    @Test
    void listToJsonStr() {
        String jsonStr = GsonUtil.listToJsonStr(roles);
        log.debug(jsonStr);
        assertNotNull(jsonStr);
        assertEquals(jsonRoleArray, jsonStr);
        assertTrue(jsonStr.startsWith("[") && jsonStr.endsWith("]"));
        assertTrue(jsonStr.contains("\"id\":1,"));
    }

    @Test
    void jsonStrToObj() {
        Map<Type, Object> typeAdapterMap = new HashMap<>();
        typeAdapterMap.put(Date.class, GsonUtil.getDateJsonDeserializer("yyyy-MM-dd"));
        UserForGson user = GsonUtil.jsonStrToObject(json, UserForGson.class, typeAdapterMap);
        log.debug(user);
        validateUser(user);
    }

    private void validateUser(UserForGson user) {
        assertNotNull(user);
        assertEquals(1L, user.getId().longValue());
        assertEquals("Jack", user.getName());
        assertEquals(18, user.getAge());
        assertEquals("1990-01-01", dateFormat.format(user.getBirthDate()));
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void jsonStrToMap() {
        Map<String, Object> map = GsonUtil.jsonStrToMap(json);
        log.debug(map);
        assertNotNull(map);
        assertEquals(1L, Long.valueOf(map.get("id").toString()));
    }

    @Test
    void jsonStrToJsonObj() {
        JsonObject jsonNode = GsonUtil.jsonStrToJsonObj(json);
        log.debug(jsonNode);
        assertNotNull(jsonNode);
        assertEquals(1L, jsonNode.get("id").getAsLong());
        JsonArray roles = jsonNode.get("roles").getAsJsonArray();
        roles.iterator().forEachRemaining(log::debug);
        assertEquals(2, roles.size());
    }

    @Test
    void jsonArrayStrToList() {
        List<RoleForGson> roles = GsonUtil.jsonArrayStrToList(jsonRoleArray, RoleForGson[].class);
        log.debug(roles);
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertTrue(roles.get(0) instanceof RoleForGson);
    }
}
