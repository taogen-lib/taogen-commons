package com.taogen.commons.jsonparser.jsonpath;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Taogen
 */
public class JsonpathUtil {

    public static DocumentContext getDocumentContext(String jsonStr) {
//        return Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
        return JsonPath.parse(jsonStr);
    }

    /**
     * Get Java primitive wrapper type or entity object type
     *
     * @param documentContext
     * @param jsonPath
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getValue(DocumentContext documentContext,
                                 String jsonPath,
                                 Class<T> clazz) {
        return documentContext.read(jsonPath, clazz);
    }

    public static <T> List<T> getList(DocumentContext documentContext,
                                      String jsonPath,
                                      Class<T[]> clazz) {
        return new ArrayList<>(Arrays.asList(documentContext.read(jsonPath, clazz)));
    }
}
