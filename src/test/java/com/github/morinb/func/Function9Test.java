package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Function9Test
{

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testApply()
    {
        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        final int result = function.apply(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertEquals(45, result);
         final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> nullFunction = null;
        //Test edge case
        assertThrows(NullPointerException.class, () ->
                nullFunction.apply(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    void testAndThen()
    {
        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        final Function<Integer, Integer> after = a -> a * 2;
        final var andThenFunc = function.andThen(after);
        assertEquals(90, andThenFunc.apply(1, 2, 3, 4, 5, 6, 7, 8, 9));

        //Test edge case
        assertThrows(NullPointerException.class, () -> function.andThen(null));
    }

    @Test
    void testCurried()
    {
        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> function =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9) -> param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        //Test positive case
        final int result = function.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9);
        assertEquals(45, result);
    }
}