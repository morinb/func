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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckedFunction2Test
{

    @Test
    void testApply() throws Throwable
    {
        final CheckedFunction2<Integer, Integer, Integer> additionFunc = Integer::sum;

        final var param1 = 5;
        final var param2 = 10;

        final int result = additionFunc.apply(param1, param2);

        assertEquals(15, result, "The result of the addition should be equal to the sum of the parameters");
    }

    @Test
    void testApplyWithException()
    {

        final CheckedFunction2<Integer, Integer, Integer> exceptionFunc = (param1, param2) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        assertThrows(UnsupportedOperationException.class, () -> exceptionFunc.apply(1, 2));
    }

    @Test
    void testAndThen() throws Throwable
    {
        final CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;
        final CheckedFunction1<Integer, Integer> additionFunc = param -> param + 3;

        final var param1 = 2;
        final var param2 = 3;

        final int result = multiplicationFunc.andThen(additionFunc).apply(param1, param2);

        assertEquals(9, result, "The result of the addition should be equal to the product of the parameters plus 3");
    }

    @Test
    void testAndThenWithException()
    {

        final CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;
        final CheckedFunction1<Integer, Integer> exceptionFunc = param -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        final CheckedFunction2<Integer, Integer, Integer> multiplyThenThrow = multiplicationFunc.andThen(exceptionFunc);
        assertThrows(UnsupportedOperationException.class, () -> multiplyThenThrow.apply(2, 3));
    }

    @Test
    void testCurried() throws Throwable
    {
        final CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;

        final var param1 = 2;
        final var param2 = 3;

        final var curriedFunc = multiplicationFunc.curried();

        final int result = curriedFunc.apply(param1).apply(param2);

        assertEquals(6, result, "The result of the curried function should be equal to the product of the parameters");
    }

}