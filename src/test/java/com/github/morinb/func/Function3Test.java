package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function3Test
{
    @Test
    void testApply()
    {
        Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        var result = function.apply("Test ", 123, true);
        assertEquals("Test 123true", result, "Mismatched results.");

        var result2 = function.apply("Check ", 456, false);
        assertEquals("Check 456false", result2, "Mismatched results.");
    }


    @Test
    void testAndThen()
    {
        Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");
        Function<String, String> after = s -> s + " after";

        var combinedFunction = function.andThen(after);

        var result = combinedFunction.apply("Test ", 123, true);
        assertEquals("Test 123true after", result, "Mismatched results with andThen.");

    }

    @Test
    void testCurried()
    {
        Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        var curriedFunction = function.curried();

        var result = curriedFunction.apply("Test ").apply(123).apply(true);
        assertEquals("Test 123true", result, "Mismatched results with currying.");
    }

}