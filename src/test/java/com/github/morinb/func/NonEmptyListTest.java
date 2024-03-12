package com.github.morinb.func;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NonEmptyListTest
{
    private NonEmptyList<Integer> testList;

    @BeforeEach
    void setup()
    {
        final var list = Arrays.asList(1, 2, 3, 4, 5);
        testList = NonEmptyList.of(list);
    }

    @Test
    void mapTest()
    {
        final Function1<Integer, Integer> times2 = (x) -> x * 2;
        final var resultList = testList.map(times2);
        assertEquals(2, resultList.head());
        assertArrayEquals(new Integer[]{4, 6, 8, 10}, resultList.tail().toArray());
    }

    @Test
    void flatMapTest()
    {
        final Function1<Integer, NonEmptyList<Integer>> listIncrement = (x) -> {
            final List<Integer> newList = new ArrayList<>();
            newList.add(x + 1);
            return NonEmptyList.of(newList);
        };
        final var resultList = testList.flatMap(listIncrement);
        assertEquals(2, resultList.head());
        assertArrayEquals(new Integer[]{3, 4, 5, 6}, resultList.tail().toArray());
    }

    @Test
    void ofTest()
    {
        assertEquals(1, testList.head());
        assertArrayEquals(new Integer[]{2, 3, 4, 5}, testList.tail().toArray());
    }

    @Test
    void ofTest_exception()
    {
        final List<Integer> emptyList = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of((List<Integer>) null));
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(emptyList));
    }

    @Test
    void testOfValidElements()
    {
        final var elements = new String[]{"A", "B", "C"};
        final var result = NonEmptyList.of(elements);

        // Assertion to check if the function operates as expected.
        // Update this as per your requirements.
        assertEquals("A", result.head());
        assertArrayEquals(new String[]{"B", "C"}, result.tail().toArray());
    }

    @Test
    void testOfNullElements()
    {
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of((Object) null));
    }



    @Test
    void testOfNoElements()
    {
        final var elements = new String[0];
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(elements));
    }

    @Test
    void testOfNullArray()
    {
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of((Object[]) null));
    }

    @Test
    void testOfZeroLengthArray()
    {
        final var elements = new Integer[0];
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(elements));
    }

    @Test
    void testOfNullFList()
    {
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of((FList<Integer>) null));
    }

    @Test
    void testOfEmptyFList()
    {
        final var nullFList = new FList<>(null, null);
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(nullFList));
    }

}