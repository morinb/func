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

class CheckedFunction9Test
{

    private final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> checkedFunc =
            (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;

    @Test
    void testAndThenSuccessful() throws Throwable
    {
        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> func =
                (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;

        final CheckedFunction1<Integer, String> after = a -> "Result: " + a;

        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, String> composedFunc = func.andThen(after);

        final String result = composedFunc.apply(1, 1, 1, 1, 1, 1, 1, 1, 1);

        assertEquals("Result: 9", result, "AndThen functionality operates as expected");
    }

    @Test
    void testAndThenWithExceptionInAfter()
    {

        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> func =
                (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;

        final CheckedFunction1<Integer, String> after = a -> {
            throw new IllegalArgumentException("Exception in after function");
        };

        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, String> composedFunc = func.andThen(after);

        assertThrows(IllegalArgumentException.class, () -> composedFunc.apply(1, 1, 1, 1, 1, 1, 1, 1, 1),
                "AndThen functionality propagates exceptions as expected");
    }

    @Test
    void curriedMethodShouldReturnTheSameResultAsOriginalMethod() throws Throwable
    {
        // Arrange & Act
        final int expected = 45;
        final int result = checkedFunc.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void curriedMethodShouldThrowExceptionWhenNullIsPassed()
    {
        // Arrange
        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> nullFunc = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> nullFunc.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9));
    }
}