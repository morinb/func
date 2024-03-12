package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This is a test class for CheckedFunction4. It provides comprehensive unit tests for the `apply` method.
 */
class CheckedFunction4Test
{

    /**
     * This test case verifies that `CheckedFunction4.apply` correctly applies the function to inputs and produces expected result.
     */
    @Test
    void apply_functionAndInputs_resultProduced() throws Throwable
    {
        // Define CheckedFunction4 instance for testing.
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> fun = (a, b, c, d) -> a * b + c - d;

        // Inputs for CheckedFunction4.
        Integer input1 = 2;
        Integer input2 = 3;
        Integer input3 = 6;
        Integer input4 = 1;

        // Expected result.
        Integer expectedResult = 11;

        // Apply method. 2*3+6-1
        var actualResult = fun.apply(input1, input2, input3, input4);

        // Ensuring CheckedFunction4 functional interface is working as expected.
        assertEquals(expectedResult, actualResult, "The output is not as expected.");
    }

    /**
     * This test case verifies that `CheckedFunction4.apply` correctly throws an exception when a function that throws an exception is applied.
     */
    @Test
    void apply_functionThatThrowsException_ExceptionThrown()
    {
        // Define CheckedFunction4 instance for testing.
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> fun =
                (a, b, c, d) -> {
                    throw new IllegalArgumentException();
                };

        // Inputs for CheckedFunction4.
        Integer input1 = 2;
        Integer input2 = 3;
        Integer input3 = 6;
        Integer input4 = 1;

        // Apply method and ensuring the expected Exception is thrown.
        assertThrows(IllegalArgumentException.class, () -> fun.apply(input1, input2, input3, input4), "The function did not throw the expected exception.");
    }

    /**
     * This class tests the CheckedFunction4 interface, particularly focusing on the andThen method.
     * The andThen method is expected to compose this function with the after function.
     */
    @Test
    void testAndThen() throws Throwable
    {
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> multiplyNumbers =
                (param1, param2, param3, param4) -> param1 * param2 * param3 * param4;

        CheckedFunction1<Integer, Integer> addTwo = num -> num + 2;

        var combinedFunction = multiplyNumbers.andThen(addTwo);

        var actual = combinedFunction.apply(2, 2, 2, 2);
        Integer expected = 18; // (2*2*2*2) + 2

        assertEquals(expected, actual);
    }

    /**
     * This tests if the andThen method correctly throws a NullPointerException when the given function is null.
     */
    @Test
    void testAndThenThrowsNullPointerException()
    {
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> multiplyNumbers =
                (param1, param2, param3, param4) -> param1 * param2 * param3 * param4;

        CheckedFunction1<Integer, Integer> nullFunction = null;

        assertThrows(NullPointerException.class, () -> multiplyNumbers.andThen(nullFunction));
    }


    /**
     * Tests that the curried function works with an example operation.
     */
    @Test
    void testCurriedFunction() throws Throwable
    {
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> function =
                (a, b, c, d) -> ((a + b) * (c - d));

        var curried = function.curried();
        // ((2 + 3) * (4 - 1)) = 15
        int result = curried.apply(2).apply(3).apply(4).apply(1);
        assertEquals(15, result);
    }

    /**
     * Tests that null parameters to the curried function throws NullPointerExceptions.
     */
    @Test
    void testCurriedFunctionWithNulls()
    {
        CheckedFunction4<Integer, Integer, Integer, Integer, Integer> function =
                (a, b, c, d) -> ((a + b) * (c - d));

        var curried = function.curried();
        assertDoesNotThrow(() -> curried.apply(null));
        assertDoesNotThrow(() -> curried.apply(2).apply(null));
        assertDoesNotThrow(() -> curried.apply(2).apply(3).apply(null));
        assertThrows(NullPointerException.class, () -> curried.apply(2).apply(3).apply(4).apply(null));
    }
}