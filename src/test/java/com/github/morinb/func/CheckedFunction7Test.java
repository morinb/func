package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class CheckedFunction7Test
{

    /**
     * Test for `CheckedFunction7` interface.
     * This class is responsible for testing `andThen` method for the `CheckedFunction7` class.
     */

    @Test
    @DisplayName("Test for CheckedFunction7.andThen method")
    void testAndThen() throws Throwable
    {
        CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> addNumbers = (a, b, c, d, e, f, g) -> a + b + c + d + e + f + g;
        CheckedFunction1<Integer, Integer> multiplyByTwo = number -> number * 2;

        CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> addAndMultiply = addNumbers.andThen(multiplyByTwo);

        // Test Case: 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28; 28 * 2 = 56
        int result = addAndMultiply.apply(1, 2, 3, 4, 5, 6, 7);
        Assertions.assertEquals(56, result);
    }

    @Test
    void testCurriedMethod()
    {
        CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (a, b, c, d, e, f, g) -> a + b + c + d + e + f + g;

        try
        {
            int result = sumFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7);
            assertEquals(28, result, "The curried sum function did not produce the expected result");
        } catch (Throwable e)
        {
            fail("Unexpected Throwable: " + e.getMessage());
        }
    }

    @Test
    void testCurriedMethodWithDifferentTypes()
    {
        CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, String, String> concatFunction =
                (a, b, c, d, e, f, g) -> a.toString() + b.toString() + c.toString() + d.toString() + e.toString() + f.toString() + g;

        try
        {
            String result = concatFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply("7");
            assertEquals("1234567", result, "The curried concatenate function did not produce the expected result");
        } catch (Throwable e)
        {
            fail("Unexpected Throwable: " + e.getMessage());
        }
    }

    @Test
    void testWithThrowableException()
    {
        CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> exceptionFunction = (a, b, c, d, e, f, g) -> {
            throw new Exception("Expected exception");
        };

        assertThrows(Exception.class, () -> exceptionFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7));
    }
}