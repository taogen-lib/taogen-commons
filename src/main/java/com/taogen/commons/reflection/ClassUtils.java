package com.taogen.commons.reflection;

import com.taogen.commons.datatypes.string.StringCaseUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Taogen
 */
public class ClassUtils {
    public ClassUtils() {
        throw new IllegalStateException("Can't instantiate the utility class");
    }

    public static List<Field> getAllFieldsOfClassAndItsParent(Class cls) {
        List<Field> fields = new ArrayList<>();
        Class node = cls;
        while (!Object.class.equals(node)) {
            fields.addAll(Arrays.asList(node.getDeclaredFields()));
            node = node.getSuperclass();
        }
        return fields;
    }

    public static List<Method> getAllMethodsOfClassAndItsParent(Class cls) {
        List<Method> methods = new ArrayList<>();
        Class node = cls;
        while (!Object.class.equals(node)) {
            methods.addAll(Arrays.asList(node.getDeclaredMethods()));
            node = node.getSuperclass();
        }
        return methods;
    }

    /**
     * The Class must have getter methods
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValueFromObject(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }
        Object returnValue = null;
        String methodName = "get" + StringCaseUtils.firstLetterToUpperCase(fieldName);
        Class classNode = obj.getClass();
        boolean isDone = false;
        while (!Object.class.equals(classNode)) {
            List<Method> methodList = Arrays.asList(classNode.getDeclaredMethods());
            for (Method method : methodList) {
                if (method.getName().equals(methodName)) {
                    try {
                        returnValue = method.invoke(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    isDone = true;
                    break;
                }
            }
            if (isDone) {
                break;
            }
            classNode = classNode.getSuperclass();
        }
        return returnValue;
    }

    /**
     * The Class must have setter method
     *
     * @param obj
     * @param fieldName
     * @param fieldValue
     */
    public static void setFieldValueToObject(Object obj, String fieldName, Object fieldValue) {
        if (obj == null || fieldName == null) {
            return;
        }
        String methodName = "set" + StringCaseUtils.firstLetterToUpperCase(fieldName);
        Class classNode = obj.getClass();
        boolean isDone = false;
        while (!Object.class.equals(classNode)) {
            List<Method> methodList = Arrays.asList(classNode.getDeclaredMethods());
            for (Method method : methodList) {
                if (method.getName().equals(methodName)) {
                    try {
                        method.invoke(obj, fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    isDone = true;
                    break;
                }
            }
            if (isDone) {
                break;
            }
            classNode = classNode.getSuperclass();
        }
    }

    public static Set<? extends Serializable> getFieldValuesFromList(List<?> list, String fieldName) {
        Set<Serializable> fieldValues = new HashSet<>();
        for (Object obj : list) {
            Object fieldValue = getFieldValueFromObject(obj, fieldName);
            if (fieldValue != null) {
                fieldValues.add((Serializable) fieldValue);
            }
        }
        return fieldValues != null ? fieldValues : Collections.emptySet();
    }

    public static void setFieldValuesForList(List<?> list, Map<String, String> idValueToFieldValue, String idFieldName, String fieldName) {
        if (list == null) {
            return;
        }
        for (Object obj : list) {
            Object id = getFieldValueFromObject(obj, idFieldName);
            if (id != null) {
                ClassUtils.setFieldValueToObject(obj, fieldName, idValueToFieldValue.get(String.valueOf(id)));
            }
        }
    }
}
