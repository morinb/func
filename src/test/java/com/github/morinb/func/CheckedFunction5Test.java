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
        final CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func = (a1, a2, a3, a4, a5) -> a1 + a2 + a3 + a4 + a5;
        final var result = assertDoesNotThrow(() -> func.apply(1, 2, 3, 4, 5));
        Assertions.assertEquals(15, result);
    }

    @Test
    void testApply_throwsException()
    {
        final CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func = (a1, a2, a3, a4, a5) -> {
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
        final CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func5 = (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;
        final CheckedFunction1<Integer, Integer> after = param -> param * 2;

        final var combinedFunc = func5.andThen(after);

        final int result = combinedFunc.apply(1, 2, 3, 4, 5);
        assertEquals(30, result);
    }

    /**
     * Test for the {@code andThen} method which applies the provided function after applying the current function.
     * The test checks the behaviour of the method when a null function is passed.
     */
    @Test
    void andThen_WhenNullAfterFunctionIsPassed_ShouldThrowNullPointerException()
    {
        final CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> func5 = (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;
        final CheckedFunction1<Integer, Integer> after = null;

        assertThrows(NullPointerException.class, () -> func5.andThen(after));
    }

    @Test
    void testCurriedMethod() throws Throwable
    {
        final CheckedFunction5<Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var curriedFunction = function.curried();

        final int value = curriedFunction.apply(1)
                .apply(2)
                .apply(3)
                .apply(4)
                .apply(5);

        assertEquals(15, value);
    }

    @Test
    void testCurriedMethod_NullArgument()
    {
        final CheckedFunction5<Object, Object, Object, Object, Object, Integer> function =
                (param1, param2, param3, param4, param5) -> {
                    if (param1 == null || param2 == null || param3 == null || param4 == null || param5 == null)
                    {
                        throw new NullPointerException("Null parameter detected");
                    }

                    return 1;
                };

        final var curriedFunction = function.curried();

        assertThrows(NullPointerException.class, () -> curriedFunction.apply(null)
                .apply(2)
                .apply(3)
                .apply(4)
                .apply(5));
    }

}