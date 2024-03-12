package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckedFunction2Test
{

    @Test
    void testApply() throws Throwable
    {
        CheckedFunction2<Integer, Integer, Integer> additionFunc = Integer::sum;

        var param1 = 5;
        var param2 = 10;

        int result = additionFunc.apply(param1, param2);

        assertEquals(15, result, "The result of the addition should be equal to the sum of the parameters");
    }

    @Test
    void testApplyWithException()
    {

        CheckedFunction2<Integer, Integer, Integer> exceptionFunc = (param1, param2) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        assertThrows(UnsupportedOperationException.class, () -> exceptionFunc.apply(1, 2));
    }

    @Test
    void testAndThen() throws Throwable
    {
        CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;
        CheckedFunction1<Integer, Integer> additionFunc = (param) -> param + 3;

        var param1 = 2;
        var param2 = 3;

        int result = multiplicationFunc.andThen(additionFunc).apply(param1, param2);

        assertEquals(9, result, "The result of the addition should be equal to the product of the parameters plus 3");
    }

    @Test
    void testAndThenWithException()
    {

        CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;
        CheckedFunction1<Integer, Integer> exceptionFunc = (param) -> {
            throw new UnsupportedOperationException("Not supported yet.");
        };

        final CheckedFunction2<Integer, Integer, Integer> multiplyThenThrow = multiplicationFunc.andThen(exceptionFunc);
        assertThrows(UnsupportedOperationException.class, () -> multiplyThenThrow.apply(2, 3));
    }

    @Test
    void testCurried() throws Throwable
    {
        CheckedFunction2<Integer, Integer, Integer> multiplicationFunc = (param1, param2) -> param1 * param2;

        var param1 = 2;
        var param2 = 3;

        var curriedFunc = multiplicationFunc.curried();

        int result = curriedFunc.apply(param1).apply(param2);

        assertEquals(6, result, "The result of the curried function should be equal to the product of the parameters");
    }

}