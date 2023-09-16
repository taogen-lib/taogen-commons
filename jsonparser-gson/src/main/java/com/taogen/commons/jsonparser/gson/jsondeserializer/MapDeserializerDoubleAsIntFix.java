package com.taogen.commons.jsonparser.gson.jsondeserializer;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * For fix int value as double in map.
 *
 * @author Taogen
 */
public class MapDeserializerDoubleAsIntFix implements JsonDeserializer<Map<String, Object>> {

    @Override
    public Map<String, Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return (Map<String, Object>) read(jsonElement);
    }

    public Object read(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            List<Object> list = new ArrayList<Object>();
            JsonArray arr = jsonElement.getAsJsonArray();
            for (JsonElement anArr : arr) {
                list.add(read(anArr));
            }
            return list;
        } else if (jsonElement.isJsonObject()) {
            Map<String, Object> map = new LinkedTreeMap<>();
            JsonObject obj = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entitySet) {
                map.put(entry.getKey(), read(entry.getValue()));
            }
            return map;
        } else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive prim = jsonElement.getAsJsonPrimitive();
            if (prim.isBoolean()) {
                return prim.getAsBoolean();
            } else if (prim.isString()) {
                return prim.getAsString();
            } else if (prim.isNumber()) {
                Number num = prim.getAsNumber();
                // here you can handle double int/long values
                // and return any type you want
                // this solution will transform 3.0 float to long values
                if (Math.ceil(num.doubleValue()) == num.longValue()) {
                    return num.longValue();
                } else {
                    return num.doubleValue();
                }
            }
        }
        return null;
    }

}
