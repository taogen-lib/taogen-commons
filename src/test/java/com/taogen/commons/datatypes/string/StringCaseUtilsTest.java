package com.taogen.commons.datatypes.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringCaseUtilsTest {

    @Test
    void snakeCaseToCamelCase() {
        assertNull(StringCaseUtils.snakeCaseToCamelCase(null));
        assertEquals("", StringCaseUtils.snakeCaseToCamelCase(""));
        assertEquals("aBC", StringCaseUtils.snakeCaseToCamelCase("a_b_c")); // aBC
        assertEquals("abc", StringCaseUtils.snakeCaseToCamelCase("abc")); // abc
    }

    @Test
    void kebabCaseToCamelCase() {
        assertNull(StringCaseUtils.kebabCaseToCamelCase(null));
        assertEquals("", StringCaseUtils.kebabCaseToCamelCase(""));
        assertEquals("aBC", StringCaseUtils.kebabCaseToCamelCase("a-b-c"));
        assertEquals("abc", StringCaseUtils.kebabCaseToCamelCase("abc"));
    }

    @Test
    void convertAnyCaseToCamelCase() {
    }

    @Test
    void snakeCaseToPascalCase() {
        assertNull(StringCaseUtils.snakeCaseToPascalCase(null));
        assertEquals("", StringCaseUtils.snakeCaseToPascalCase(""));
        assertEquals("ABC", StringCaseUtils.snakeCaseToPascalCase("a_b_c")); // ABC
        assertEquals("Abc", StringCaseUtils.snakeCaseToPascalCase("abc")); // Abc
    }

    @Test
    void kebabCaseToPascalCase() {
        assertNull(StringCaseUtils.kebabCaseToPascalCase(null));
        assertEquals("", StringCaseUtils.kebabCaseToPascalCase(""));
        assertEquals("ABC", StringCaseUtils.kebabCaseToPascalCase("a-b-c"));
        assertEquals("Abc", StringCaseUtils.kebabCaseToPascalCase("abc"));
    }

    @Test
    void covertAnyCaseToPascalCase(String source, String joinChar) {

    }

    @Test
    void firstLetterToUpperCase() {
        assertNull(StringCaseUtils.firstLetterToUpperCase(null));
        assertEquals("", StringCaseUtils.firstLetterToUpperCase(""));
        assertEquals("A", StringCaseUtils.firstLetterToUpperCase("A")); // A
        assertEquals("A", StringCaseUtils.firstLetterToUpperCase("a")); // A
        assertEquals("AA", StringCaseUtils.firstLetterToUpperCase("aA")); // AA
        assertEquals("AA", StringCaseUtils.firstLetterToUpperCase("AA")); // AA
    }

    @Test
    void firstLetterToLowerCase() {
        assertNull(StringCaseUtils.firstLetterToLowerCase(null));
        assertEquals("", StringCaseUtils.firstLetterToLowerCase(""));
        assertEquals("a", StringCaseUtils.firstLetterToLowerCase("a")); // a
        assertEquals("a", StringCaseUtils.firstLetterToLowerCase("A")); // a
        assertEquals("aa", StringCaseUtils.firstLetterToLowerCase("Aa")); // aa
        assertEquals("aa", StringCaseUtils.firstLetterToLowerCase("aa")); // aa
    }
}
