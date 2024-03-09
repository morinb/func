package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function7Test
{

    @Test
    void testApply()
    {
        Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        int result = function.apply(1, 2, 3, 4, 5, 6, 7);
        assertEquals(28, result);
    }

    @Test
    void testAndThen()
    {
        Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        var andThenFunction = function.andThen(Integer::doubleValue);
        double result = andThenFunction.apply(1, 2, 3, 4, 5, 6, 7);
        assertEquals(28.0, result);
    }

    @Test
    void testCurried()
    {
        Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function = (param1, param2, param3, param4, param5, param6, param7) -> param1 + param2 + param3 + param4 + param5 + param6 + param7;
        var curriedFunction = function.curried();
        int result = curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7);
        assertEquals(28, result);
    }
}