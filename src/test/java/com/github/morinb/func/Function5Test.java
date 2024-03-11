package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Function5Test
{

    @Test
    void testApplyMethod()
    {

        // Define a function that concatenates five strings.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var result = concatenateStringsFunction.apply("Hello, ", "this ", "is ", "a ", "test.");

        Assertions.assertEquals("Hello, this is a test.", result);
    }

    @Test
    void testAndThenMethod()
    {

        // Define a function that concatenates five strings and then convert this string to upper case.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var toUpperCaseFunction =
                concatenateStringsFunction.andThen(String::toUpperCase);

        final var result = toUpperCaseFunction.apply("Hello, ", "this ", "is ", "a ", "test.");

        Assertions.assertEquals("HELLO, THIS IS A TEST.", result);
    }

    @Test
    void testCurriedMethod()
    {

        // Define a function that concatenates five strings and then using curried function.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var curriedFunction =
                concatenateStringsFunction.curried();

        final var result = curriedFunction.apply("Hello, ").apply("this ").apply("is ").apply("a ").apply("test.");

        Assertions.assertEquals("Hello, this is a test.", result);
    }
}