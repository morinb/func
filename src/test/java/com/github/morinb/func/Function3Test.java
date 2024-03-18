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

class Function3Test
{
    @Test
    void testApply()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        final var result = function.apply("Test ", 123, true);
        assertEquals("Test 123true", result, "Mismatched results.");

        final var result2 = function.apply("Check ", 456, false);
        assertEquals("Check 456false", result2, "Mismatched results.");
    }


    @Test
    void testAndThen()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");
        final Function1<String, String> after = s -> s + " after";

        final var combinedFunction = function.andThen(after);

        final var result = combinedFunction.apply("Test ", 123, true);
        assertEquals("Test 123true after", result, "Mismatched results with andThen.");

    }

    @Test
    void testCurried()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        final var curriedFunction = function.curried();

        final var result = curriedFunction.apply("Test ").apply(123).apply(true);
        assertEquals("Test 123true", result, "Mismatched results with currying.");
    }

}