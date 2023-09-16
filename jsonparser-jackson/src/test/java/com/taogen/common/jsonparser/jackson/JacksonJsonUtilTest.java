package com.taogen.common.jsonparser.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taogen.common.jsonparser.jackson.entity.Role;
import com.taogen.common.jsonparser.jackson.entity.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class JacksonJsonUtilTest extends BaseTest {


    public static User user;

    public static List<Role> roles;

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
    void testJsonStrToJsonObj() throws JsonProcessingException {
        String s1 = "{\"name\": \"a\"}";
        ObjectNode json = JacksonJsonUtil.jsonStrToJsonObject(s1);
        String name = json.get("name").asText();
        System.out.println(name);
        if (json.get("lastName") != null) {
            String lastName = json.get("lastName").asText();
            System.out.println(lastName);
        }
    }

    @Test
    void objectToMap() {
        Map<String, Object> map = JacksonJsonUtil.objectToMap(user);
        log.debug("map: {}", map);
        validateMap(map);
    }

    @Test
    void mapToObject() {
        User user = JacksonJsonUtil.mapToObject(USER_MAP, User.class);
        log.debug(user);
        validateUser(user);
    }

    @Test
    void objectToJsonStr() throws JsonProcessingException {
        String jsonStr = JacksonJsonUtil.objectToJsonStr(user);
        log.debug(jsonStr);
        super.validateJsonStr(jsonStr);
    }

    @Test
    void mapToJsonStr() throws JsonProcessingException {
        String jsonStr = JacksonJsonUtil.mapToJsonStr(USER_MAP);
        log.debug(jsonStr);
        super.validateJsonStr(jsonStr);
    }

    @Test
    void listToJsonStr() throws JsonProcessingException {
        String jsonStr = JacksonJsonUtil.listToJsonStr(roles);
        log.debug(jsonStr);
        assertNotNull(jsonStr);
        assertEquals(jsonRoleArray, jsonStr);
        assertTrue(jsonStr.startsWith("[") && jsonStr.endsWith("]"));
        assertTrue(jsonStr.contains("\"id\":1,"));
    }

    @Test
    void jsonStrToObject() throws JsonProcessingException {
        User user = JacksonJsonUtil.jsonStrToObject(json, User.class);
        log.debug(user);
        validateUser(user);
    }

    private void validateUser(User user) {
        assertNotNull(user);
        assertEquals(1L, user.getId().longValue());
        assertEquals("Jack", user.getName());
        assertEquals(18, user.getAge());
        assertEquals("1990-01-01", dateFormat.format(user.getBirthDate()));
        assertNotNull(user.getRoles());
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void jsonStrToMap() throws JsonProcessingException {
        Map<String, Object> map = JacksonJsonUtil.jsonStrToMap(json);
        log.debug(map);
        validateMap(map);
    }

    @Test
    void jsonStrToJsonObject() throws JsonProcessingException {
        JsonNode jsonNode = JacksonJsonUtil.jsonStrToJsonObject(json);
        log.debug(jsonNode);
        assertNotNull(jsonNode);
        assertEquals(1L, jsonNode.get("id").asLong());
        JsonNode roles = jsonNode.get("roles");
        roles.elements().forEachRemaining(log::debug);
        assertEquals(2, roles.size());
    }

    @Test
    void jsonArrayStrToList() throws JsonProcessingException {
        List<Role> roles = JacksonJsonUtil.jsonArrayStrToList(jsonRoleArray, Role[].class);
        log.debug(roles);
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertTrue(roles.get(0) instanceof Role);
    }
}
