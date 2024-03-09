
package com.github.morinb.func;

import com.github.morinb.func.Function1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

 class Function1Test {

    @Test
     void testApply() {
        // Setup our Function1 instance
        Function1<String, Integer> lengthFunc = String::length;

        // Test case 1
        var testStr1 = "Hello, World!";
        var expectedReturn1 = testStr1.length();
        int actualReturn1 = lengthFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        var testStr2 = "";
        var expectedReturn2 = testStr2.length();
        int actualReturn2 = lengthFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);

        // Test case 3
        var testStr3 = "A longer string than the previous ones.";
        var expectedReturn3 = testStr3.length();
        int actualReturn3 = lengthFunc.apply(testStr3);
        Assertions.assertEquals(expectedReturn3, actualReturn3);
    }

    @Test
     void testAndThen() {
        // Setup our Function1 instance
        Function1<String, Integer> lengthFunc = String::length;
        Function1<Integer, String> intToStringFunc = x -> x.toString();

        // AndThen use case
        var combinedFunc = lengthFunc.andThen(intToStringFunc);

        // Test case 1
        var testStr1 = "Hello, World!";
        var expectedReturn1 = Integer.toString(testStr1.length());
        var actualReturn1 = combinedFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        var testStr2 = "A longer string than the previous ones.";
        var expectedReturn2 = Integer.toString(testStr2.length());
        var actualReturn2 = combinedFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);
    }

    @Test
     void testCurried() {
        // Setup our Function1 instance
        Function1<String, Integer> lengthFunc = String::length;

        // Curried use case
        var curriedFunc = lengthFunc.curried();

        // Test case 1
        var testStr1 = "Hello, World!";
        var expectedReturn1 = testStr1.length();
        int actualReturn1 = curriedFunc.apply(testStr1);
        Assertions.assertEquals(expectedReturn1, actualReturn1);

        // Test case 2
        var testStr2 = "";
        var expectedReturn2 = testStr2.length();
        int actualReturn2 = curriedFunc.apply(testStr2);
        Assertions.assertEquals(expectedReturn2, actualReturn2);

        // Test case 3
        var testStr3 = "A longer string than the previous ones.";
        var expectedReturn3 = testStr3.length();
        int actualReturn3 = curriedFunc.apply(testStr3);
        Assertions.assertEquals(expectedReturn3, actualReturn3);
    }
}
