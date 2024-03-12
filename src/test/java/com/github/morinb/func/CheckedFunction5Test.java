package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckedFunction5Test
{

    /*
     * This class tests the CheckedFunction5 interface, specifically the apply method.
     * Given 5 arguments, the method should apply a functional transformation and return a result.
     */

    @Test
    void testApply()
    {
        CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func = (a1, a2, a3, a4, a5) -> a1 + a2 + a3 + a4 + a5;
        var result = assertDoesNotThrow(() -> func.apply(1, 2, 3, 4, 5));
        Assertions.assertEquals(15, result);
    }

    @Test
    void testApply_throwsException()
    {
        CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func = (a1, a2, a3, a4, a5) -> {
            if (a1 == 0) throw new ArithmeticException("Argument a1 is zero");
            else return a1 + a2 + a3 + a4 + a5;
        };

        Assertions.assertThrows(ArithmeticException.class, () -> func.apply(0, 2, 3, 4, 5));
    }

    /**
     * Test for the {@code andThen} method which applies the provided function after applying the current function.
     * The test checks if the method works correctly when composing non-throwing functions.
     */
    @Test
    void andThen_WithNonThrowingFunctions_ShouldComposeFunctions() throws Throwable
    {
        CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func5 = (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;
        CheckedFunction1<Integer, Integer> after = param -> param * 2;

        var combinedFunc = func5.andThen(after);

        int result = combinedFunc.apply(1, 2, 3, 4, 5);
        assertEquals(30, result);
    }

    /**
     * Test for the {@code andThen} method which applies the provided function after applying the current function.
     * The test checks the behaviour of the method when a null function is passed.
     */
    @Test
    void andThen_WhenNullAfterFunctionIsPassed_ShouldThrowNullPointerException()
    {
        CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func5 = (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;
        CheckedFunction1<Integer, Integer> after = null;

        assertThrows(NullPointerException.class, () -> func5.andThen(after));
    }

    @Test
    void testCurriedMethod() throws Throwable
    {
        CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        var curriedFunction = function.curried();

        int value = curriedFunction.apply(1)
                .apply(2)
                .apply(3)
                .apply(4)
                .apply(5);

        assertEquals(15, value);
    }

    @Test
    void testCurriedMethod_NullArgument()
    {
        CheckedFunction5<Object, Object, Object, Object, Object, Integer> function =
                (param1, param2, param3, param4, param5) -> {
                    if (param1 == null || param2 == null || param3 == null || param4 == null || param5 == null)
                    {
                        throw new NullPointerException("Null parameter detected");
                    }

                    return 1;
                };

        var curriedFunction = function.curried();

        assertThrows(NullPointerException.class, () -> curriedFunction.apply(null)
                .apply(2)
                .apply(3)
                .apply(4)
                .apply(5));
    }

}