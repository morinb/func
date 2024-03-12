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
        CheckedFunction0<String> func = () -> "Test";

        var result = assertDoesNotThrow(func::apply);
        assertEquals("Test", result);
    }

    /**
     * This test case tests the use case when the apply method throws an exception.
     */
    @Test
    void testApplyException()
    {
        CheckedFunction0<String> func = () -> {
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
        CheckedFunction0<String> func = () -> "Test";
        CheckedFunction1<String, String> func1 = (str) -> str + "123";

        final CheckedFunction0<String> stringCheckedFunction0 = func.andThen(func1);
        var result = assertDoesNotThrow(stringCheckedFunction0::apply);
        assertEquals("Test123", result);
    }

    /**
     * This test case tests the use case when the andThen method does not work as expected
     * and the CheckedFunction1 given to it throws an exception.
     */
    @Test
    void testAndThenException()
    {
        CheckedFunction0<String> func = () -> "Test";
        CheckedFunction1<String, String> func1 = (str) -> {
            throw new Exception("Test exception");
        };

        assertThrows(Exception.class, () -> func.andThen(func1).apply(), "Exception expected when applying function");
    }
}  