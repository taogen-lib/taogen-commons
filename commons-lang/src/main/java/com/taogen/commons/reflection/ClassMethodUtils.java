package com.taogen.commons.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    public static void printMethods(Class cls) {
        Method[] methods = cls.getDeclaredMethods();
        Arrays.stream(methods)
                .map(method -> {
                    if (method.getName().contains("lambda")) {
                        return null;
                    }
                    Parameter[] parameters = method.getParameters();
                    Type[] genericParamType = method.getGenericParameterTypes();
                    StringBuilder stringBuilder = new StringBuilder()
                            .append(method.getGenericReturnType().getTypeName().replaceAll("(\\w+\\.)+", ""))
                            .append(" ")
                            .append(method.getName())
                            .append("(");
                    if (parameters.length > 0) {
                        for (int i = 0; i < parameters.length; i++) {
                            stringBuilder.append(genericParamType[i].getTypeName().replaceAll("(\\w+\\.)+", ""))
                                    .append(" ")
                                    .append(parameters[i].getName());
                            if (i < parameters.length - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                    }
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                })
                .filter(Objects::nonNull)
                .forEach(System.out::println);

    }
}
