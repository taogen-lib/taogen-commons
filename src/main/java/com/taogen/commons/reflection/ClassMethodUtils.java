package com.taogen.commons.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Taogen
 */
public class ClassMethodUtils {

    public static List<Method> getAllMethodsOfClassAndItsParent(Class cls) {
        List<Method> methods = new ArrayList<>();
        Class node = cls;
        while (!Object.class.equals(node)) {
            methods.addAll(Arrays.asList(node.getDeclaredMethods()));
            node = node.getSuperclass();
        }
        return methods;
    }
}
