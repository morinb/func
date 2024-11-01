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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class CheckedFunction7Test
{

    /**
     * Test for `CheckedFunction7` interface.
     * This class is responsible for testing `andThen` method for the `CheckedFunction7` class.
     */

    @Test
    @DisplayName("Test for CheckedFunction7.andThen method")
    void testAndThen() throws Throwable
    {
        final CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> addNumbers = (a, b, c, d, e, f, g) -> a + b + c + d + e + f + g;
        final CheckedFunction1<Integer, Integer> multiplyByTwo = number -> number * 2;

        final CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> addAndMultiply = addNumbers.andThen(multiplyByTwo);

        // Test Case: 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28; 28 * 2 = 56
        final int result = addAndMultiply.apply(1, 2, 3, 4, 5, 6, 7);
        Assertions.assertEquals(56, result);
    }

    @Test
    void testCurriedMethod()
    {
        final CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (a, b, c, d, e, f, g) -> a + b + c + d + e + f + g;

        try
        {
            final int result = sumFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7);
            assertEquals(28, result, "The curried sum function did not produce the expected result");
        } catch (final Throwable e)
        {
            fail("Unexpected Throwable: " + e.getMessage());
        }
    }

    @Test
    void testCurriedMethodWithDifferentTypes()
    {
        final CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, String, String> concatFunction =
                (a, b, c, d, e, f, g) -> a.toString() + b.toString() + c.toString() + d.toString() + e.toString() + f.toString() + g;

        try
        {
            final String result = concatFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply("7");
            assertEquals("1234567", result, "The curried concatenate function did not produce the expected result");
        } catch (final Throwable e)
        {
            fail("Unexpected Throwable: " + e.getMessage());
        }
    }

    @Test
    void testWithThrowableException()
    {
        final CheckedFunction7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> exceptionFunction = (a, b, c, d, e, f, g) -> {
            throw new Exception("Expected exception");
        };

        assertThrows(Exception.class, () -> exceptionFunction.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7));
    }
}