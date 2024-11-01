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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * CheckedFunction0Test is a test class for the CheckedFunction0 interface.
 * This class primarily tests the apply and andThen methods of CheckedFunction0.
 */
class CheckedFunction0Test
{

    /**
     * This test case tests the use case when the apply method works as expected
     * and does not throw an exception.
     */
    @Test
    void testApplyNoException()
    {
        final CheckedFunction0<String> func = () -> "Test";

        final var result = assertDoesNotThrow(func::apply);
        assertEquals("Test", result);
    }

    /**
     * This test case tests the use case when the apply method throws an exception.
     */
    @Test
    void testApplyException()
    {
        final CheckedFunction0<String> func = () -> {
            throw new Exception("Test exception");
        };

        assertThrows(Exception.class, func::apply, "Exception expected when applying function");
    }

    /**
     * This test case tests the use case when the andThen method works as expected
     * and the CheckedFunction1 given to it does not throw an exception.
     */
    @Test
    void testAndThenNoException()
    {
        final CheckedFunction0<String> func = () -> "Test";
        final CheckedFunction1<String, String> func1 = str -> str + "123";

        final CheckedFunction0<String> stringCheckedFunction0 = func.andThen(func1);
        final var result = assertDoesNotThrow(stringCheckedFunction0::apply);
        assertEquals("Test123", result);
    }

    /**
     * This test case tests the use case when the andThen method does not work as expected
     * and the CheckedFunction1 given to it throws an exception.
     */
    @Test
    void testAndThenException()
    {
        final CheckedFunction0<String> func = () -> "Test";
        final CheckedFunction1<String, String> func1 = str -> {
            throw new Exception("Test exception");
        };

        assertThrows(Exception.class, () -> func.andThen(func1).apply(), "Exception expected when applying function");
    }
}  