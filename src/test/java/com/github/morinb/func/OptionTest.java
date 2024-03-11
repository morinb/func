package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionTest
{

    @Test
    void testSomeWithValue()
    {
        final var testValue = "Test";
        final var singleOption = Option.some(testValue);

        assertFalse(singleOption.isNone(), "Option should not be None");
        assertEquals(testValue, singleOption.getValue(), "Value in option does not match expected");
    }

    @Test
    void testSomeWithNull()
    {
        final Option<String> singleOption = Option.some(null);

        assertFalse(singleOption.isNone(), "Option should not be None");
        assertNull(singleOption.getValue(), "Value in option is not null");
    }

    @Test
    void testNone()
    {
        final Option<String> noneOption = Option.none();

        assertTrue(noneOption.isNone(), "Option should be None");
        assertThrows(NoSuchElementException.class, noneOption::getValue, "Getting value from None should throw exception");
    }

    @Test
    void testOrElse()
    {
        final var defaultValue = Option.some("Default");
        final Option<String> noneOption = Option.none();
        assertEquals(defaultValue, noneOption.orElse(defaultValue), "OrElse did not return the default value");

        final var singleOption = Option.some("Test");
        assertEquals(singleOption, singleOption.orElse(defaultValue), "OrElse did not return the test value");
    }

    @Test
    void testMap()
    {
        final var someOption = Option.some(5);
        final var result = someOption.map(i -> i * 2);

        assertFalse(result.isNone(), "Option should not be None");
        assertEquals(10, result.getValue(), "Value in option does not match expected");

        final var mapped = Option.<String>none().map(param1 -> param1 + "mapped");
        assertTrue(mapped.isNone());

    }

    @Test
    void testFilter()
    {
        final var someOption = Option.some(5);
        final var result = someOption.filter(i -> i % 2 == 0);
        final var result2 = someOption.filter(i -> i % 2 == 1);

        assertTrue(result.isNone(), "Option should be None");
        assertFalse(result2.isNone());
        assertTrue(Option.<Integer>none().filter(i -> i % 2 == 0).isNone());
    }

    @Test
    void testFold()
    {
        final var someOption = Option.some(5);
        final int result = someOption.fold(() -> 0, i -> i * 2);

        assertEquals(10, result, "Value does not match expected");
        assertTrue(Option.<String>none().fold(() -> true, param1 -> false));
    }

    @Test
    void testZip()
    {
        final var firstOption = Option.some(5);
        final var secondOption = Option.some(10);
        final var result = firstOption.zip(secondOption);

        assertFalse(result.isNone(), "Option should not be None");
        assertEquals(new Pair<>(5, 10), result.getValue(), "Value in option does not match expected");
        assertTrue(Option.none().zip(Option.some("Test")).isNone());
        assertTrue(firstOption.zip(Option.none()).isNone());
    }

    @Test
    void testGetOrElse()
    {
        final Option<Integer> noneOption = Option.none();
        final int result = noneOption.getOrElse(() -> 5);

        assertEquals(5, result, "Value does not match expected");

        final var someOption = Option.some(5);
        final int result2 = someOption.getOrElse(() -> 0);
        assertEquals(5, result2);
    }

    @Test
    void testFlatMap()
    {
        final var someOption = Option.some(5);
        final var result = someOption.flatMap(i -> Option.some(i * 2));

        assertFalse(result.isNone(), "Option should not be None");
        assertEquals(10, result.getValue(), "Value in option does not match expected");

        final Option<Integer> none = Option.none();
        final var result2 = none.flatMap(i -> Option.some(i * 2));

        assertTrue(result2.isNone());

    }

    @Test
    void testOfWithValue()
    {
        final var testValue = "Test";
        final var singleOption = Option.of(testValue);

        assertFalse(singleOption.isNone(), "Option should not be None");
        assertEquals(testValue, singleOption.getValue(), "Value in option does not match expected");
    }

    @Test
    void testOfWithNull()
    {
        final Option<String> singleOption = Option.of(null);

        assertTrue(singleOption.isNone(), "Option should be None");
        assertThrows(NoSuchElementException.class, singleOption::getValue, "Getting value from None should throw exception");
    }
}
