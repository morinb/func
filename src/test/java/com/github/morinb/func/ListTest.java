package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Function;

public class ListTest{

    @Test
    public void testMapWithSquaredFunction() {
        // actual initialization
        var actual = List.of(2, 3, 4);
        actual = actual.map(i -> i * i);

        //Setting up manual expected
        var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.add(4); // = 2*2
        expectedInnerList.add(9); // = 3*3
        expectedInnerList.add(16); // = 4*4
        var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    public void testAdd() {
        // actual initialization
        var actual = List.of(2, 3, 4);
        actual = actual.add(5);

        //Setting up manual expected
        var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.add(2);
        expectedInnerList.add(3);
        expectedInnerList.add(4);
        expectedInnerList.add(5);
        var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    public void testSize() {
        // actual initialization
        var actual = List.of(2, 3, 4);

        //Asserts
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    public void testFlatMap() {
        // actual initialization
        var source = List.of(List.of(1, 2), List.of(3, 4));
        Function<List<Integer>, List<Integer>> flatten = Function.identity();
        var actual = source.flatMap(flatten);

        //Setting up manual expected
        var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.addAll(Arrays.asList(1, 2, 3, 4));
        var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    public void testFilter() {
        // actual initialization
        var actual = List.of(9, 20, 22, 34, 38);
        actual = actual.filter(i -> i % 2 == 0);

        //Setting up manual expected
        var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.addAll(Arrays.asList(20, 22, 34, 38));
        var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    public void testContains() {
        // actual initialization
        var actual = List.of(2, 3, 4);

        Assertions.assertTrue(actual.contains(2));
        Assertions.assertFalse(actual.contains(10));
    }

    @Test
    public void testGet() {
        // actual initialization
        var actual = List.of(11, 22, 33);

        Assertions.assertEquals(22, actual.get(1));
    }

    @Test
    public void testIndexOf() {
        // actual initialization
        var actual = List.of(2, 3, 4, 5);

        Assertions.assertEquals(2, actual.indexOf(4));
    }

    @Test
    public void testIsEmpty() {
        // actual initialization
        List<Integer> actual = List.of();

        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void testAddAll() {
        // actual initialization
        var list1 = List.of(1, 2);
        var list2 = List.of(3, 4);
        var actual = list1.addAll(list2);

        //Setting up manual expected
        var expectedInnerList = new LinkedList<Integer>();
        expectedInnerList.addAll(Arrays.asList(1, 2, 3, 4));
        var expected = List.of(expectedInnerList);

        //Asserts
        Assertions.assertEquals(expected.toJavaList(), actual.toJavaList());
    }

    @Test
    public void testContainsAll() {
        // actual initialization
        var actualList = List.of(1, 2, 3, 4, 5);
        var subList = List.of(2, 3, 4);

        Assertions.assertTrue(actualList.containsAll(subList));
        Assertions.assertFalse(actualList.containsAll(List.of(2, 3, 6)));
    }

}