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

class Function10Test
{

    @Test
    void applyShouldApplyFunctionToAllArguments()
    {

        final Function10<String, String, String, String, String, String, String, String, String, String, String> concatenator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var expected = "12345678910";
        final var actual = concatenator.apply("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        assertEquals(expected, actual);

    }

    @Test
    void andThenShouldApplySecondFunction()
    {

        final Function10<String, String, String, String, String, String, String, String, String, String, Integer> lengthCalculator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        (param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10).length();

        final var myLength = lengthCalculator.andThen(length -> length * 2);

        final Integer expected = 22;
        final var actual = myLength.apply("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        assertEquals(expected, actual);

    }

    @Test
    void curriedShouldApplyFunctionToAllArguments()
    {

        final Function10<String, String, String, String, String, String, String, String, String, String, String> concatenator =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var expected = "12345678910";
        final var actual = concatenator.curried()
                .apply("1")
                .apply("2")
                .apply("3")
                .apply("4")
                .apply("5")
                .apply("6")
                .apply("7")
                .apply("8")
                .apply("9")
                .apply("10");

        assertEquals(expected, actual);
    }
}