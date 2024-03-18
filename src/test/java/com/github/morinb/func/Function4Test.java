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

class Function4Test
{

    @Test
    void testFunctionApply()
    {
        final Function4<String, String, String, String, String> concat4Strings = (s1, s2, s3, s4) -> s1 + s2 + s3 + s4;
        final var result = concat4Strings.apply("a", "b", "c", "d");
        assertEquals("abcd", result, "The function did not concatenate strings as expected");
    }

    @Test
    void testAndThenFunction()
    {
        final Function4<String, Character, Character, Character, String> accept3Chars = (s, c1, c2, c3) -> s + c1 + c2 + c3;
        final var result = accept3Chars.andThen(String::toUpperCase).apply("abc", 'd', 'e', 'f');
        assertEquals("ABCDEF", result, "The function did not change the string to uppercase as expected");
    }

    @Test
    void testCurriedFunction()
    {
        final Function4<Integer, Integer, Integer, Integer, Integer> sum4Integers = (i1, i2, i3, i4) -> i1 + i2 + i3 + i4;
        final int result = sum4Integers.curried().apply(1).apply(2).apply(3).apply(4);
        assertEquals(10, result, "The function did not sum the integers as expected");
    }
}