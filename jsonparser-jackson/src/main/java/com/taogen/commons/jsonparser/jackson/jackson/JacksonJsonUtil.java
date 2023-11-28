package com.taogen.commons.jsonparser.jackson.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Jackson JSON utility
 * <p>
 * Convert json string to object(entity, map, jsonObject, List<entity>) and vice versa.
 * <p>
 * Serialize and deserialize:
 * 1. ignore field: @JsonIgnore
 * Serialization:
 * 1. change field name: @JsonProperty(value = "birth_date")
 * 2. format date time: @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
 * 3. ignore null value fields: @JsonInclude(JsonInclude.Include.NON_NULL)
 * Deserialization:
 * 1. ignore non-declared fields: @JsonIgnoreProperties(ignoreUnknown = true) - fix UnrecognizedPropertyException
 *
 * @author Taogen
 */
public class JacksonJsonUtil {
    private JacksonJsonUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * warning:
     * field data types are only primitive types.
     * Other data types will convert to String, such as Date type will be converted to String.
     *
     * @param object
     * @return
     */
    public static Map objectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, Map.class);
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, clazz);
    }

    public static String objectToJsonStr(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String mapToJsonStr(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    public static String listToJsonStr(List list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    public static <T> T jsonStrToObject(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonStr, clazz);
    }

    public static Map<String, Object> jsonStrToMap(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    public static ObjectNode jsonStrToJsonObject(String jsonStr) throws JsonProcessingException, ClassCastException {
        ObjectMapper objectMapper = new ObjectMapper();
        // JsonNode is immutable(read-only), but ObjectNode is mutable.
        return (ObjectNode) objectMapper.readTree(jsonStr);
    }

    public static <T> List<T> jsonArrayStrToList(String jsonArrayStr, Class<T[]> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return new ArrayList<>(Arrays.asList(objectMapper.readValue(jsonArrayStr, clazz)));
    }
}
