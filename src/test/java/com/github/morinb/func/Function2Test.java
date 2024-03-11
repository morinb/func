package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function2Test
{

    @Test
    void testApply()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;

        final int result = addFunction.apply(5, 7);
        assertEquals(12, result);
    }

    @Test
    void testCurried()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;
        final var curriedFunction = addFunction.curried();

        final int result;

        result = curriedFunction.apply(5).apply(7);
        assertEquals(12, result);
    }

    @Test
    void testAndThen()
    {
        final Function2<Integer, Integer, Integer> addFunction = Integer::sum;
        final Function<Integer, Integer> doubleFunction = (Integer a) -> a * 2;

        final var composedFunction = addFunction.andThen(doubleFunction);

        final int result;

        result = composedFunction.apply(5, 7);
        assertEquals((5 + 7) * 2, result);
    }

}