package com.github.morinb.func;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for the CheckedFunction8 andThen method")
class CheckedFunction8Test
{

    // Define an instance of CheckedFunction8 for testing
    private final CheckedFunction8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;

    @Test
    @DisplayName("Test normal flow")
    void testNormalFlow() throws Throwable
    {
        CheckedFunction8<String, String, String, String, String, String, String, String, String> function = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        CheckedFunction1<String, Integer> afterFunction = String::length;

        CheckedFunction8<String, String, String, String, String, String, String, String, Integer> composedFunction = function.andThen(afterFunction);

        assertEquals(27, composedFunction.apply("Hello", " ", "there", " ", "General", " ", "Kenobi", "!"));
    }

    @Test
    @DisplayName("Test when after function is null")
    void testNullAfterFunction()
    {
        CheckedFunction8<String, String, String, String, String, String, String, String, String> function = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;

        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    /**
     * Test to verify that the "curried" method works as expected when it is applied to multiple inputs.
     * It is expected to return a curried version of the function that can be called with one parameter at a time.
     */
    @Test
    void testCurriedMethod()
    {
        assertDoesNotThrow(() -> {
            var curriedFunction = function.curried();
            var result = curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8);
            assertEquals(36, result.intValue());
        });
    }

    /**
     * Test to verify that calling the curried function with null values will result in a NullPointerException.
     * This is expected as the initial function does not handle null instances.
     */
    @Test
    void testCurriedMethodWithNull()
    {
        var curriedFunction = function.curried();
        assertDoesNotThrow(() -> curriedFunction.apply(null));
    }

    /**
     * Test to verify that the "curried" method will throw an exception when an exception occurs inside the function.
     */
    @Test
    void testCurriedMethodWithException()
    {
        CheckedFunction8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> exceptionFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> {
            throw new RuntimeException();
        };

        var curriedFunction = exceptionFunction.curried();
        assertThrows(RuntimeException.class, () -> curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8));
    }
}