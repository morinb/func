package com.github.morinb.func;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class NonEmptyListTest {
    private NonEmptyList<Integer> testList;

    @BeforeEach
    public void setup() {
        var list = Arrays.asList(1, 2, 3, 4, 5);
        testList = NonEmptyList.of(list);
    }

    @Test
    public void mapTest() {
        Function<Integer, Integer> times2 = (x) -> x * 2;
        var resultList = testList.map(times2);
        assertEquals(2, resultList.head());
        assertArrayEquals(new Integer[]{4, 6, 8, 10}, resultList.tail().toArray());
    }

    @Test
    public void flatMapTest() {
        Function<Integer, NonEmptyList<Integer>> listIncrement = (x) -> {
            List<Integer> newList = new ArrayList<>();
            newList.add(x + 1);
            return NonEmptyList.of(newList);
        };
        var resultList = testList.flatMap(listIncrement);
        assertEquals(2, resultList.head());
        assertArrayEquals(new Integer[]{3, 4, 5, 6}, resultList.tail().toArray());
    }

    @Test
    public void ofTest() {
        assertEquals(1, testList.head());
        assertArrayEquals(new Integer[]{2, 3, 4, 5}, testList.tail().toArray());
    }

    @Test
    public void ofTest_exception() {
        List<Integer> nullList = null;
        List<Integer> emptyList = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(nullList));
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(emptyList));
    }

    @Test
    public void testOfValidElements() {
        String[] elements = {"A", "B", "C"};
        NonEmptyList<String> result = NonEmptyList.of(elements);

        // Assertion to check if the function operates as expected.
        // Update this as per your requirements.
    }

    @Test
    public void testOfNullElements() {
        String[] elements = null;
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(elements));
    }

    @Test
    public void testOfNoElements() {
        String[] elements = new String[0];
        assertThrows(IllegalArgumentException.class, () -> NonEmptyList.of(elements));
    }
}