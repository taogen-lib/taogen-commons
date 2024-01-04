package com.taogen.commons.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Print all methods in a class
     * <p>
     * Tip: You can add the option -parameters to javac and recompile source code (rebuild project in IntelliJ IDEA) to print real parameter names instead of arg0 or arg1.
     *
     * @param cls
     */
    private static void printMethods(Class cls) {
        Method[] methods = cls.getDeclaredMethods();
        Arrays.stream(methods).map(item -> {
            Parameter[] parameters = item.getParameters();
            String paramStr = Arrays.stream(parameters)
                    .map(param -> param.getType().getSimpleName() + " " + param.getName())
                    .collect(Collectors.joining(", "));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getReturnType().getSimpleName())
                    .append(" ")
                    .append(item.getName())
                    .append("(")
                    .append(paramStr)
                    .append(")");
            return stringBuilder.toString();
        }).forEach(System.out::println);
    }
}
