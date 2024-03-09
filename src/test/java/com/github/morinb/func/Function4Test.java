package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function4Test
{

    @Test
    void testFunctionApply()
    {
        Function4<String, String, String, String, String> concat4Strings = (s1, s2, s3, s4) -> s1 + s2 + s3 + s4;
        var result = concat4Strings.apply("a", "b", "c", "d");
        assertEquals("abcd", result, "The function did not concatenate strings as expected");
    }

    @Test
    void testAndThenFunction()
    {
        Function4<String, Character, Character, Character, String> accept3Chars = (s, c1, c2, c3) -> s + c1 + c2 + c3;
        var result = accept3Chars.andThen(s -> s.toUpperCase()).apply("abc", 'd', 'e', 'f');
        assertEquals("ABCDEF", result, "The function did not change the string to uppercase as expected");
    }

    @Test
    void testCurriedFunction()
    {
        Function4<Integer, Integer, Integer, Integer, Integer> sum4Integers = (i1, i2, i3, i4) -> i1 + i2 + i3 + i4;
        int result = sum4Integers.curried().apply(1).apply(2).apply(3).apply(4);
        assertEquals(10, result, "The function did not sum the integers as expected");
    }
}