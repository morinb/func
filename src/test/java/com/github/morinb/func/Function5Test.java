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
import org.junit.jupiter.api.Test;

class Function5Test
{

    @Test
    void testApplyMethod()
    {

        // Define a function that concatenates five strings.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var result = concatenateStringsFunction.apply("Hello, ", "this ", "is ", "a ", "test.");

        Assertions.assertEquals("Hello, this is a test.", result);
    }

    @Test
    void testAndThenMethod()
    {

        // Define a function that concatenates five strings and then convert this string to upper case.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var toUpperCaseFunction =
                concatenateStringsFunction.andThen(String::toUpperCase);

        final var result = toUpperCaseFunction.apply("Hello, ", "this ", "is ", "a ", "test.");

        Assertions.assertEquals("HELLO, THIS IS A TEST.", result);
    }

    @Test
    void testCurriedMethod()
    {

        // Define a function that concatenates five strings and then using curried function.
        final Function5<String, String, String, String, String, String> concatenateStringsFunction =
                (param1, param2, param3, param4, param5) -> param1 + param2 + param3 + param4 + param5;

        final var curriedFunction =
                concatenateStringsFunction.curried();

        final var result = curriedFunction.apply("Hello, ").apply("this ").apply("is ").apply("a ").apply("test.");

        Assertions.assertEquals("Hello, this is a test.", result);
    }
}