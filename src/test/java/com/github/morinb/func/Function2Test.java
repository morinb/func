package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function2Test
{

    @Test
    void testApply()
    {
        Function2<Integer, Integer, Integer> addFunction = (Integer a, Integer b) -> a + b;

        int result = addFunction.apply(5, 7);
        assertEquals(12, result);
    }

    @Test
    void testCurried()
    {
        Function2<Integer, Integer, Integer> addFunction = (Integer a, Integer b) -> a + b;
        var curriedFunction = addFunction.curried();

        int result;

        result = curriedFunction.apply(5).apply(7);
        assertEquals(12, result);
    }

    @Test
    void testAndThen()
    {
        Function2<Integer, Integer, Integer> addFunction = (Integer a, Integer b) -> a + b;
        Function<Integer, Integer> doubleFunction = (Integer a) -> a * 2;

        var composedFunction = addFunction.andThen(doubleFunction);

        int result;

        result = composedFunction.apply(5, 7);
        assertEquals((5 + 7) * 2, result);
    }

}