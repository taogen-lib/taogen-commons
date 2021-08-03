package com.taogen.commons.datatypes.string;

/**
 * Common Case:
 * - snake_case
 * - kebab-case
 * - PascalCase
 * - camelCase
 * - lowercase
 * - UPPERCASE
 *
 * @author Taogen
 */
public class StringCaseUtils {
    public StringCaseUtils() {
        throw new IllegalStateException("Can't instantiate the utility class");
    }

    /**
     * Convert String from snake case to camel case
     *
     * @param source start with letter
     * @return
     */
    public static String snakeCaseToCamelCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return convertAnyCaseToCamelCase(source, "_");
    }

    public static String kebabCaseToCamelCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return convertAnyCaseToCamelCase(source, "-");
    }

    public static String convertAnyCaseToCamelCase(String source, String joinChar) {
        source = source.toLowerCase();
        int i = 0;
        while (i < source.length()) {
            int index = source.indexOf(joinChar, i);
            if (index == -1) {
                return source;
            } else if (index == source.length() - 1) {
                source = source.replace(joinChar, "");
            } else {
                String targetString = source.substring(index, index + 2);
                String replacementString = source.substring(index + 1, index + 2);
                source = source.replaceFirst(targetString, replacementString.toUpperCase());
            }
            i = index + 1;
        }
        return source;
    }

    /**
     * Convert String from snake case to pascal case
     *
     * @param source start with letter
     * @return
     */
    public static String snakeCaseToPascalCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return covertAnyCaseToPascalCase(source, "_");
    }

    public static String kebabCaseToPascalCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return covertAnyCaseToPascalCase(source, "-");
    }

    public static String covertAnyCaseToPascalCase(String source, String joinChar) {
        source = source.toLowerCase();
        String firstLetter = source.substring(0, 1);
        source = source.replaceFirst(firstLetter, firstLetter.toUpperCase());
        int i = 0;
        while (i < source.length()) {
            int index = source.indexOf(joinChar, i);
            if (index == -1) {
                return source;
            } else if (index == source.length() - 1) {
                source = source.replace(joinChar, "");
            } else {
                String targetString = source.substring(index, index + 2);
                String replacementString = source.substring(index + 1, index + 2);
                source = source.replaceFirst(targetString, replacementString.toUpperCase());
            }
            i = index + 1;
        }
        return source;
    }

    public static String firstLetterToUpperCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }

    public static String firstLetterToLowerCase(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return source.substring(0, 1).toLowerCase() + source.substring(1);
    }
}
