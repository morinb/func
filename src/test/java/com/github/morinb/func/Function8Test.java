package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function8Test
{
    @Test
    void apply()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final int result = sumFunction.apply(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(36, result);
    }

    @Test
    void andThen()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final Function<Integer, String> toStringFunction = Object::toString;
        final var andThenFunction = sumFunction.andThen(toStringFunction);
        final var result = andThenFunction.apply(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("36", result);
    }

    @Test
    void curried()
    {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sumFunction = (p1, p2, p3, p4, p5, p6, p7, p8) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        final var curriedFunction = sumFunction.curried();
        final int result = curriedFunction.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8);
        assertEquals(36, result);
    }
}