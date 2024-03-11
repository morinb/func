package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function3Test
{
    @Test
    void testApply()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        final var result = function.apply("Test ", 123, true);
        assertEquals("Test 123true", result, "Mismatched results.");

        final var result2 = function.apply("Check ", 456, false);
        assertEquals("Check 456false", result2, "Mismatched results.");
    }


    @Test
    void testAndThen()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");
        final Function<String, String> after = s -> s + " after";

        final var combinedFunction = function.andThen(after);

        final var result = combinedFunction.apply("Test ", 123, true);
        assertEquals("Test 123true after", result, "Mismatched results with andThen.");

    }

    @Test
    void testCurried()
    {
        final Function3<String, Integer, Boolean, String> function = (a, b, c) -> a + b + (c ? "true" : "false");

        final var curriedFunction = function.curried();

        final var result = curriedFunction.apply("Test ").apply(123).apply(true);
        assertEquals("Test 123true", result, "Mismatched results with currying.");
    }

}