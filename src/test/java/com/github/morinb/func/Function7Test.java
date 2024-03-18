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

class Function7Test
{

    @Test
    void testApply()
    {
        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        final int result = function.apply(1, 2, 3, 4, 5, 6, 7);
        assertEquals(28, result);
    }

    @Test
    void testAndThen()
    {
        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        final var andThenFunction = function.andThen(Integer::doubleValue);
        final double result = andThenFunction.apply(1, 2, 3, 4, 5, 6, 7);
        assertEquals(28.0, result);
    }

    @Test
    void testCurried()
    {
        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        final var curriedFunction = function.curried();
        final int result = curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7);
        assertEquals(28, result);
    }
}