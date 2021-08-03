package com.taogen.commons.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ClassUtilsTest {

    @Test
    void getAllFieldsOfClassAndItsParent() {
        List<Field> fields = ClassUtils.getAllFieldsOfClassAndItsParent(Child.class);
        assertNotNull(fields);
        Set<String> fieldNames = fields.stream()
                .map(item -> item.getName())
                .collect(Collectors.toSet());
        assertTrue(fieldNames.contains("name"));
        assertTrue(fieldNames.contains("id"));
    }

    @Test
    void getAllMethodsOfClassAndItsParent() {
        List<Method> methods = ClassUtils.getAllMethodsOfClassAndItsParent(Child.class);
        assertNotNull(methods);
        Set<String> methodNames = methods.stream()
                .map(item -> item.getName())
                .collect(Collectors.toSet());
        assertTrue(methodNames.contains("printName"));
        assertTrue(methodNames.contains("printId"));
    }

    @Test
    void getFieldValueFromObject() {
        String id = "1";
        String name = "Jack";
        Child child = new Child(id, name);
        Object idValue = ClassUtils.getFieldValueFromObject(child, "id");
        assertEquals(id, idValue);
        Object nameValue = ClassUtils.getFieldValueFromObject(child, "name");
        assertEquals(name, nameValue);
    }

    @Test
    void setFieldValueToObject() {
        String id = "1";
        String name = "Jack";
        Child child = new Child(id, name);
        Object idValue = ClassUtils.getFieldValueFromObject(child, "id");
        assertEquals(id, idValue);
        String newId = "2";
        String newName = "John";
        ClassUtils.setFieldValueToObject(child, "id", newId);
        ClassUtils.setFieldValueToObject(child, "name", newName);
        Object newIdValue = ClassUtils.getFieldValueFromObject(child, "id");
        assertEquals(newId, newIdValue);
        Object newNameValue = ClassUtils.getFieldValueFromObject(child, "name");
        assertEquals(newName, newNameValue);
    }

    class Child extends Parent {
        private String name;

        private Child(String id, String name) {
            super(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void printName() {
            System.out.println(this.name);
        }
    }

    class Parent {
        private String id;

        public Parent(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void printId() {
            System.out.println(this.id);
        }
    }
}
