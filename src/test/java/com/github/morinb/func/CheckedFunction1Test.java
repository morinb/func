package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is a test class for the `CheckedFunction1` class.
 */
class CheckedFunction1Test
{

    // ... previous test methods ...

    /**
     * This unit test verifies the successful execution of the `apply` method with different input data.
     * Several `CheckedFunction1` instances are initialized, and the `apply` method is called with various arguments.
     * Assertions are done for each case to make sure the result is as expected.
     */
    @Test
    void testApply_withVariousArguments() throws Throwable
    {
        CheckedFunction1<String, Integer> function1 = String::length;
        assertEquals(5, function1.apply("12345"));
        assertEquals(0, function1.apply(""));
        assertThrows(NullPointerException.class, () -> function1.apply(null));

        CheckedFunction1<Integer, Boolean> function2 = (Integer i) -> i > 0;
        assertTrue(function2.apply(5));
        assertFalse(function2.apply(-1));
        assertThrows(NullPointerException.class, () -> function2.apply(null));
    }

    /**
     * This unit test verifies the successful execution of the `andThen` method with various input data.
     * Several pairs of `CheckedFunction1` instances are initialized, `andThen` is called on the first function of each pair and
     * applied it to the result of the second.
     * Assertions are done for each case to verify the result is as expected.
     */
    @Test
    void testAndThen_withVariousFunctions() throws Throwable
    {
        CheckedFunction1<Double, String> doubleToString = dbl -> Double.toString(dbl);
        CheckedFunction1<String, Integer> function1 = String::length;
        var combinedFunction1 = doubleToString.andThen(function1);

        assertEquals(3, combinedFunction1.apply(1.0));

        CheckedFunction1<String, Integer> multiplyByTwo = (String s) -> Integer.parseInt(s) * 2;
        combinedFunction1 = doubleToString.andThen(multiplyByTwo);
        final var finalCombinedFunction = combinedFunction1;
        assertThrows(NumberFormatException.class, () -> finalCombinedFunction.apply(1.0));

        assertThrows(NullPointerException.class, () -> doubleToString.andThen(null));
    }

    /**
     * This unit test verifies the successful execution of the `curried` method with various input data.
     * Several `CheckedFunction1` instances are initialized, `curried` method is called on each instance.
     * Assertions are done for each case to verify that the `curried` function is the same as the original function.
     */
    @Test
    void testCurried_withVariousFunctions()
    {
        CheckedFunction1<String, Integer> function1 = String::length;
        var curriedFunction1 = function1.curried();
        assertSame(function1, curriedFunction1);

        CheckedFunction1<Integer, Boolean> function2 = (Integer i) -> i > 0;
        var curriedFunction2 = function2.curried();
        assertSame(function2, curriedFunction2);

        CheckedFunction1<Double, String> function3 = dbl -> Double.toString(dbl);
        var curriedFunction3 = function3.curried();
        assertSame(function3, curriedFunction3);
    }
}