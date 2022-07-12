package com.taogen.commons.reflection;

import com.taogen.commons.datatypes.string.StringCaseUtils;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Taogen
 */
@Log4j2
public class ClassUtils {
    public ClassUtils() {
        throw new IllegalStateException("Can't instantiate the utility class");
    }

    public static Class getGenericTypeOfCollectionField(Field listField) {
        Class genericType = null;
        if (Collection.class.isAssignableFrom(listField.getType()) &&
                listField.getGenericType() instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) listField.getGenericType();
            Type[] types = pType.getActualTypeArguments();
            genericType = (Class<?>) types[0];
            log.debug("list field {} generic type is {}", listField.getName(), genericType.getSimpleName());
        }
        return genericType;
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
