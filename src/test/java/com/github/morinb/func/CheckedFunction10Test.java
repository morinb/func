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
import static org.junit.jupiter.api.Assertions.fail;

class CheckedFunction10Test
{
    /**
     * This class is a unit test for the class CheckedFunction10 and its
     * method andThen.
     * The method 'andThen' is supposed to execute the original function first,
     * and then apply an additional function to the result.
     */

    // Test that verifies correct composition of the function using andThen
    @Test
    void testAndThen()
    {
        CheckedFunction10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> baseFunction =
                (a, b, c, d, e, f, g, h, i, j) -> a + b + c + d + e + f + g + h + i + j;
        CheckedFunction1<Integer, Integer> afterFunction = after -> after * 2;
        CheckedFunction10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> composedFunction = baseFunction.andThen(afterFunction);

        try
        {
            assertEquals(20, composedFunction.apply(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
        } catch (Throwable throwable)
        {
            fail("Unexpected exception thrown: " + throwable.getMessage());
        }
    }

    // Test that verifies NullPointerException is thrown when no "after" function is provided in andThen
    @Test
    void testAndThenNullPointerException()
    {
        CheckedFunction10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> baseFunction =
                (a, b, c, d, e, f, g, h, i, j) -> a + b + c + d + e + f + g + h + i + j;
        assertThrows(NullPointerException.class, () -> baseFunction.andThen(null));
    }

    /**
     * Test to check if the function is working as expected.
     */
    @Test
    void givenCheckedFunction10_whenCurried_ThenReturnsExpectedResult() throws Throwable
    {
        CheckedFunction10<String, String, String, String, String, String, String, String, String, String, String> concatFunction = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        CheckedFunction1<String,
                CheckedFunction1<String,
                        CheckedFunction1<String,
                                CheckedFunction1<String,
                                        CheckedFunction1<String,
                                                CheckedFunction1<String,
                                                        CheckedFunction1<String,
                                                                CheckedFunction1<String,
                                                                        CheckedFunction1<String,
                                                                                CheckedFunction1<String, String>>>>>>>>>> curried = concatFunction.curried();

        String result = curried.apply("1").apply("2").apply("3").apply("4").apply("5").apply("6").apply("7").apply("8").apply("9").apply("0");

        assertEquals("1234567890", result);
    }

    /**
     * Test to check if the function throws NullPointerException when null is passed.
     */
    @Test
    void givenCheckedFunction10_whenCurried_ThenThrowsNullPointerException()
    {
        CheckedFunction10<String, String, String, String, String, String, String, String, String, String, String> functionThatThrows = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) -> {
            throw new NullPointerException();
        };

        CheckedFunction1<String,
                CheckedFunction1<String,
                        CheckedFunction1<String,
                                CheckedFunction1<String,
                                        CheckedFunction1<String,
                                                CheckedFunction1<String,
                                                        CheckedFunction1<String,
                                                                CheckedFunction1<String,
                                                                        CheckedFunction1<String,
                                                                                CheckedFunction1<String, String>>>>>>>>>> curried = functionThatThrows.curried();

        assertThrows(NullPointerException.class, () -> curried.apply("1").apply("2").apply("3").apply("4").apply("5").apply("6").apply("7").apply("8").apply("9").apply("0"));
    }
}