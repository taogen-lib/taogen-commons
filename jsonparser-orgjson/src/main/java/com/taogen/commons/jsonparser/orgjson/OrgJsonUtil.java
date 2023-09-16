package com.taogen.commons.jsonparser.orgjson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * OrgJsonUtil
 * <p>
 * Convert json string to JsonObject and vice versa.
 * <p>
 * Change field name: no
 * format date time: no
 * ignore field: no
 *
 * @author Taogen
 */
public class OrgJsonUtil {
    private OrgJsonUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static JSONObject jsonStrToJsonObject(String jsonStr) {
        return new JSONObject(jsonStr);
    }

    public static JSONObject entityToJsonObject(Object entity) {
        return new JSONObject(entity);
    }

    public static JSONObject mapToJsonObject(Map<String, Object> map) {
        return new JSONObject(map);
    }

    public static String jsonObjectToJsonStr(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    public static JSONArray jsonStrToJsonArray(String jsonStr) {
        return new JSONArray(jsonStr);
    }

    public static JSONArray entityToJsonArray(List<?> entity) {
        return new JSONArray(entity);
    }

    public static String jsonArrayToJsonStr(JSONArray jsonArray) {
        return jsonArray.toString();
    }
}
