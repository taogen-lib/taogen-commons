package com.taogen.commons.reflection;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Taogen
 */
@Log4j2
public class ClassFieldUtils {
    public static List<Field> getAllFieldsOfClassAndItsParent(Class cls) {
        List<Field> fields = new ArrayList<>();
        Class node = cls;
        while (!Object.class.equals(node)) {
            fields.addAll(Arrays.asList(node.getDeclaredFields()));
            node = node.getSuperclass();
        }
        return fields;
    }

    public static Field getFieldFromClassOrParentClass(Class cls, String fieldName) {
        Field field = null;
        while (!Object.class.equals(cls)) {
            try {
                field = cls.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                log.error("{}: {}", e.getClass().getName(), e.getMessage());
                cls = cls.getSuperclass();
            }
            if (field != null) {
                break;
            }
        }
        return field;
    }

    /**
     * enum field, or List<enum> field
     *
     * @param field
     * @return
     */
    public static boolean isFieldGenericType(Field field) {
        return field.getType().isEnum() || (Collection.class.isAssignableFrom(field.getType()) &&
                ClassUtils.getGenericTypeOfCollectionField(field).isEnum());
    }
}
