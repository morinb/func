/*
 * Copyright 2024 Baptiste MORIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> fun = (a, b, c, d) -> a * b + c - d;

        // Inputs for CheckedFunction4.
        final Integer input1 = 2;
        final Integer input2 = 3;
        final Integer input3 = 6;
        final Integer input4 = 1;

        // Expected result.
        final Integer expectedResult = 11;

        // Apply method. 2*3+6-1
        final var actualResult = fun.apply(input1, input2, input3, input4);

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
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> fun =
                (a, b, c, d) -> {
                    throw new IllegalArgumentException();
                };

        // Inputs for CheckedFunction4.
        final Integer input1 = 2;
        final Integer input2 = 3;
        final Integer input3 = 6;
        final Integer input4 = 1;

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
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> multiplyNumbers =
                (param1, param2, param3, param4) -> param1 * param2 * param3 * param4;

        final CheckedFunction1<Integer, Integer> addTwo = num -> num + 2;

        final var combinedFunction = multiplyNumbers.andThen(addTwo);

        final var actual = combinedFunction.apply(2, 2, 2, 2);
        final Integer expected = 18; // (2*2*2*2) + 2

        assertEquals(expected, actual);
    }

    /**
     * This tests if the andThen method correctly throws a NullPointerException when the given function is null.
     */
    @Test
    void testAndThenThrowsNullPointerException()
    {
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> multiplyNumbers =
                (param1, param2, param3, param4) -> param1 * param2 * param3 * param4;

        final CheckedFunction1<Integer, Integer> nullFunction = null;

        assertThrows(NullPointerException.class, () -> multiplyNumbers.andThen(nullFunction));
    }


    /**
     * Tests that the curried function works with an example operation.
     */
    @Test
    void testCurriedFunction() throws Throwable
    {
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> function =
                (a, b, c, d) -> ((a + b) * (c - d));

        final var curried = function.curried();
        // ((2 + 3) * (4 - 1)) = 15
        final int result = curried.apply(2).apply(3).apply(4).apply(1);
        assertEquals(15, result);
    }

    /**
     * Tests that null parameters to the curried function throws NullPointerExceptions.
     */
    @Test
    void testCurriedFunctionWithNulls()
    {
        final CheckedFunction4<Integer, Integer, Integer, Integer, Integer> function =
                (a, b, c, d) -> ((a + b) * (c - d));

        final var curried = function.curried();
        assertDoesNotThrow(() -> curried.apply(null));
        assertDoesNotThrow(() -> curried.apply(2).apply(null));
        assertDoesNotThrow(() -> curried.apply(2).apply(3).apply(null));
        assertThrows(NullPointerException.class, () -> curried.apply(2).apply(3).apply(4).apply(null));
    }
}