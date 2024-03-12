package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * CheckedFunction3Test is a class for testing the functionality
 * of the apply and andThen methods in the CheckedFunction3 interface.
 */
class CheckedFunction3Test
{

    @Test
    void testApply() throws Throwable
    {
        CheckedFunction3<Integer, Integer, Integer, Integer> additionFunc = (param1, param2, param3) -> param1 + param2 + param3;

        var param1 = 5;
        var param2 = 10;
        var param3 = 15;

        int result = additionFunc.apply(param1, param2, param3);

        assertEquals(30, result, "The result of the addition should be equal to the sum of the parameters");
    }

    @Test
    void testApplyWithException()
    {

        CheckedFunction3<Integer, Integer, Integer, Integer> exceptionFunc = (param1, param2, param3) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        assertThrows(UnsupportedOperationException.class, () -> exceptionFunc.apply(1, 2, 3));
    }

    @Test
    void testAndThen() throws Throwable
    {
        CheckedFunction3<Integer, Integer, Integer, Integer> multiplicationFunc = (param1, param2, param3) -> param1 * param2 * param3;
        CheckedFunction1<Integer, Integer> additionFunc = (param) -> param + 3;

        var param1 = 2;
        var param2 = 3;
        var param3 = 4;

        int result = multiplicationFunc.andThen(additionFunc).apply(param1, param2, param3);

        assertEquals(27, result, "The result of the addition should be equal to the product of the parameters plus 3");
    }

    @Test
    void testAndThenWithException()
    {

        CheckedFunction3<Integer, Integer, Integer, Integer> multiplicationFunc = (param1, param2, param3) -> param1 * param2 * param3;
        CheckedFunction1<Integer, Integer> exceptionFunc = (param) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        final CheckedFunction3<Integer, Integer, Integer, Integer> resultFunc = multiplicationFunc.andThen(exceptionFunc);
        assertThrows(UnsupportedOperationException.class, () -> resultFunc.apply(2, 3, 4));
    }

    @Test
    void testCurried() throws Throwable
    {
        CheckedFunction3<Integer, Integer, Integer, Integer> multiplicationFunc = (param1, param2, param3) -> param1 * param2 * param3;

        var param1 = 2;
        var param2 = 3;
        var param3 = 4;

        var curriedFunc = multiplicationFunc.curried();

        int result = curriedFunc.apply(param1).apply(param2).apply(param3);

        assertEquals(24, result, "The result of the curried function should be equal to the product of the parameters");
    }

}