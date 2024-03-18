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

class Function6Test
{
    private final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum =
            (a, b, c, d, e, f) -> a + b + c + d + e + f;

    @Test
    void testApply()
    {
        assertEquals(Integer.valueOf(21), sum.apply(1, 2, 3, 4, 5, 6));
        assertEquals(Integer.valueOf(9), sum.apply(-1, -2, -3, 4, 5, 6));
        assertEquals(Integer.valueOf(-9), sum.apply(-1, -2, -3, -4, -5, 6));
    }

    @Test
    void testAndThen()
    {
        final var sumPlusOne = sum.andThen(i -> i + 1);

        assertEquals(Integer.valueOf(22), sumPlusOne.apply(1, 2, 3, 4, 5, 6));
        assertEquals(Integer.valueOf(10), sumPlusOne.apply(-1, -2, -3, 4, 5, 6));
        assertEquals(Integer.valueOf(-8), sumPlusOne.apply(-1, -2, -3, -4, -5, 6));
    }

    @Test
    void testCurried()
    {
        final var curried = sum.curried();

        assertEquals(Integer.valueOf(21), curried.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6));
    }
}