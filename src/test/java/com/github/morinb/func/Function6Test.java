package com.github.morinb.func;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Function6Test {
    private Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = 
        (a, b, c, d, e, f) -> a + b + c + d + e + f;

    @Test
    public void testApply() {
        assertEquals(Integer.valueOf(21), sum.apply(1, 2, 3, 4, 5, 6));
        assertEquals(Integer.valueOf(9), sum.apply(-1, -2, -3, 4, 5, 6));
        assertEquals(Integer.valueOf(-9), sum.apply(-1, -2, -3, -4, -5, 6));
    }

    @Test
    public void testAndThen() {
        var sumPlusOne = sum.andThen(i -> i + 1);

        assertEquals(Integer.valueOf(22), sumPlusOne.apply(1, 2, 3, 4, 5, 6));
        assertEquals(Integer.valueOf(10), sumPlusOne.apply(-1, -2, -3, 4, 5, 6));
        assertEquals(Integer.valueOf(-8), sumPlusOne.apply(-1, -2, -3, -4, -5, 6));
    }

    @Test
    public void testCurried() {
        var curried = sum.curried();

        assertEquals(Integer.valueOf(21), curried.apply(1).apply(2).apply(3).apply(4).apply(5).apply(6));
    }
}