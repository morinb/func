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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class CheckedFunction6Test
{

    private static final String TEST_RESULT = "Test";
    private static final Integer TEST_PARAM = 1;

    @Test
    void testAndThen() throws Throwable
    {

        CheckedFunction6<Integer, Integer, Integer, Integer, Integer, Integer, String> checkFunction6 = (param1, param2, param3, param4, param5, param6) -> TEST_RESULT;
        CheckedFunction1<String, String> afterFunction = String::toUpperCase;

        var resultFunction = checkFunction6.andThen(afterFunction);

        assertNotNull(resultFunction, "Function must not be null");

        var result = resultFunction.apply(TEST_PARAM, TEST_PARAM, TEST_PARAM, TEST_PARAM, TEST_PARAM, TEST_PARAM);

        assertEquals(TEST_RESULT.toUpperCase(), result, "The result is not as expected");
    }

    @Test
    void testAndThenThrowsNullPointerException()
    {
        CheckedFunction6<Integer, Integer, Integer, Integer, Integer, Integer, String> checkFunction6 = (param1, param2, param3, param4, param5, param6) -> TEST_RESULT;

        assertThrows(NullPointerException.class, () -> checkFunction6.andThen(null));
    }

    /**
     * Tests for the {@link CheckedFunction6#curried()} method.
     */
    @Test
    void curried()
    {
        CheckedFunction6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (p1, p2, p3, p4, p5, p6) -> p1 + p2 + p3 + p4 + p5 + p6;

        // Do currying.
        var curriedFunction = function.curried();

        try
        {
            // Apply arguments one by one.
            var afterFirstArgument = curriedFunction.apply(1);
            var afterSecondArgument = afterFirstArgument.apply(2);
            var afterThirdArgument = afterSecondArgument.apply(3);
            var afterFourthArgument = afterThirdArgument.apply(4);
            var afterFifthArgument = afterFourthArgument.apply(5);
            var result = afterFifthArgument.apply(6);

            assertEquals(21, result);
        } catch (Throwable throwable)
        {
            fail("Test failed due to an exception: " + throwable.getMessage());
        }
    }
}