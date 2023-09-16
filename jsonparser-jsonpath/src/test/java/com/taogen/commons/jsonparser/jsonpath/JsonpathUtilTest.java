package com.taogen.commons.jsonparser.jsonpath;

import com.jayway.jsonpath.DocumentContext;
import com.taogen.commons.jsonparser.jsonpath.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

class JsonpathUtilTest {

    String jsonStr = "{\"code\": 200, \"data\": [{\"id\": 1, \"name\":\"Jack\", \"age\": 20}, {\"id\": 1, \"name\":\"Tom\", \"age\": 18}]}";

    @Test
    void getObject() {
        DocumentContext documentContext = JsonpathUtil.getDocumentContext(jsonStr);
        String name = JsonpathUtil.getValue(documentContext,
                "$.data[0].name", String.class);
        System.out.println(name);
        Long id = JsonpathUtil.getValue(documentContext, "$.data[0].id", Long.class);
        System.out.println(id);
        Integer age = JsonpathUtil.getValue(documentContext, "$.data[0].age", Integer.class);
        System.out.println(age);
        User user = JsonpathUtil.getValue(documentContext, "$.data[0]", User.class);
        System.out.println(user);
    }

    @Test
    void getList() {
        DocumentContext documentContext = JsonpathUtil.getDocumentContext(jsonStr);
        List<User> userList = JsonpathUtil.getList(documentContext, "$.data", User[].class);
        System.out.println(userList);
    }
}
