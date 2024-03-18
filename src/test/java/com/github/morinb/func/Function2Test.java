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

class Function2Test
{

    @Test
    void testApply()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;

        final int result = addFunction.apply(5, 7);
        assertEquals(12, result);
    }

    @Test
    void testCurried()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;
        final var curriedFunction = addFunction.curried();

        final int result;

        result = curriedFunction.apply(5).apply(7);
        assertEquals(12, result);
    }

    @Test
    void testAndThen()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;
        final Function1<Integer, Integer> doubleFunction = (Integer a) -> a * 2;

        final var composedFunction = addFunction.andThen(doubleFunction);

        final int result;

        result = composedFunction.apply(5, 7);
        assertEquals((5 + 7) * 2, result);
    }

}