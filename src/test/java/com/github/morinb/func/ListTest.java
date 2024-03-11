package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;

class ListTest
{

    @Test
    void testMapWithSquaredFunction()
    {
        // actual initialization
        var actual = List.of(2, 3, 4);
        actual = actual.map(i -> i * i);

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.add(4); // = 2*2
        expectedInnerList.add(9); // = 3*3
        expectedInnerList.add(16); // = 4*4
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    void testAdd()
    {
        // actual initialization
        var actual = List.of(2, 3, 4);
        actual = actual.add(5);

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.add(2);
        expectedInnerList.add(3);
        expectedInnerList.add(4);
        expectedInnerList.add(5);
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    void testSize()
    {
        // actual initialization
        final var actual = List.of(2, 3, 4);

        //Asserts
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void testFlatMap()
    {
        // actual initialization
        final var source = List.of(List.of(1, 2), List.of(3, 4));
        final var actual = source.flatMap(Function.identity());

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<>(Arrays.asList(1, 2, 3, 4));
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    void testFilter()
    {
        // actual initialization
        var actual = List.of(9, 20, 22, 34, 38);
        actual = actual.filter(i -> i % 2 == 0);

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<>(Arrays.asList(20, 22, 34, 38));
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    void testContains()
    {
        // actual initialization
        final var actual = List.of(2, 3, 4);

        Assertions.assertTrue(actual.contains(2));
        Assertions.assertFalse(actual.contains(10));
    }

    @Test
    void testGet()
    {
        // actual initialization
        final var actual = List.of(11, 22, 33);

        Assertions.assertEquals(22, actual.get(1));
    }

    @Test
    void testIndexOf()
    {
        // actual initialization
        final var actual = List.of(2, 3, 4, 5);

        Assertions.assertEquals(2, actual.indexOf(4));
    }

    @Test
    void testIsEmpty()
    {
        // actual initialization
        final List<Integer> actual = List.of();

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void testAddAll()
    {
        // actual initialization
        final var list1 = List.of(1, 2);
        final var list2 = List.of(3, 4);
        final var actual = list1.addAll(list2);

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<>(Arrays.asList(1, 2, 3, 4));
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    void testContainsAll()
    {
        // actual initialization
        final var actualList = List.of(1, 2, 3, 4, 5);
        final var subList = List.of(2, 3, 4);

        Assertions.assertTrue(actualList.containsAll(subList));
        Assertions.assertFalse(actualList.containsAll(List.of(2, 3, 6)));
    }
    @Test
    void testPrepend()
    {
        // actual initialization
        var actual = List.of(2, 3, 4);
        actual = actual.prepend(1);

        //Setting up manual expected
        final var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.add(1);
        expectedInnerList.add(2);
        expectedInnerList.add(3);
        expectedInnerList.add(4);
        final var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

}
