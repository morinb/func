package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Function9Test
{

    @Test
    void testApply()
    {
        Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        int result = function.apply(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertEquals(45, result);

        //Test edge case
        assertThrows(NullPointerException.class, () -> {
            Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> nullFunction = null;
            nullFunction.apply(1, 2, 3, 4, 5, 6, 7, 8, 9);
        });
    }

    @Test
    void testAndThen()
    {
        Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        Function<Integer, Integer> after = a -> a * 2;
        var andThenFunc = function.andThen(after);
        assertEquals(90, andThenFunc.apply(1, 2, 3, 4, 5, 6, 7, 8, 9));

        //Test edge case
        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test
    void testCurried()
    {
        Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        int result = function.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9);
        assertEquals(45, result);
    }
}