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

class Function8Test
{
    @Test
    void apply()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final int result = sumFunction.apply(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(36, result);
    }

    @Test
    void andThen()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final Function1<Integer, String> toStringFunction = Object::toString;
        final var andThenFunction = sumFunction.andThen(toStringFunction);
        final var result = andThenFunction.apply(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("36", result);
    }

    @Test
    void curried()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final var curriedFunction = sumFunction.curried();
        final int result = curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8);
        assertEquals(36, result);
    }
}