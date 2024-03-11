package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Function1Test
{

    @Test
    void testApply()
    {
        // Setup our Function1 instance
        final Function1<String, Integer> lengthFunc = String::length;

        // Test case 1
        final var testStr1 = "Hello, World!";
        final var expectedReturn1 = testStr1.length();
        final int actualReturn1 = lengthFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        final var testStr2 = "";
        final var expectedReturn2 = testStr2.length();
        final int actualReturn2 = lengthFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);

        // Test case 3
        final var testStr3 = "A longer string than the previous ones.";
        final var expectedReturn3 = testStr3.length();
        final int actualReturn3 = lengthFunc.apply(testStr3);
        Assertions.assertEquals(expectedReturn3, actualReturn3);
    }

    @Test
    void testAndThen()
    {
        // Setup our Function1 instance
        final Function1<String, Integer> lengthFunc = String::length;
        final Function1<Integer, String> intToStringFunc = Object::toString;

        // AndThen use case
        final var combinedFunc = lengthFunc.andThen(intToStringFunc);

        // Test case 1
        final var testStr1 = "Hello, World!";
        final var expectedReturn1 = Integer.toString(testStr1.length());
        final var actualReturn1 = combinedFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        final var testStr2 = "A longer string than the previous ones.";
        final var expectedReturn2 = Integer.toString(testStr2.length());
        final var actualReturn2 = combinedFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);
    }

    @Test
    void testCurried()
    {
        // Setup our Function1 instance
        final Function1<String, Integer> lengthFunc = String::length;

        // Curried use case
        final var curriedFunc = lengthFunc.curried();

        // Test case 1
        final var testStr1 = "Hello, World!";
        final var expectedReturn1 = testStr1.length();
        final int actualReturn1 = curriedFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        final var testStr2 = "";
        final var expectedReturn2 = testStr2.length();
        final int actualReturn2 = curriedFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);

        // Test case 3
        final var testStr3 = "A longer string than the previous ones.";
        final var expectedReturn3 = testStr3.length();
        final int actualReturn3 = curriedFunc.apply(testStr3);
        Assertions.assertEquals(expectedReturn3, actualReturn3);
    }
}
