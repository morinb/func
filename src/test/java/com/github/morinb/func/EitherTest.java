package com.github.morinb.func;


import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class EitherTest {
    private Either<String, Integer> underTest;
    private final Function1<String, String> leftMapper = str -> str + " mapped";
    private final Function1<Integer, Integer> rightMapper = num -> num * 2;

    @Test
    void testRight_isRight() {
        underTest = Either.right(10);
        assertTrue(underTest.isRight());
    }

    @Test
    void testRight_isNotLeft() {
        underTest = Either.right(10);
        assertFalse(underTest.isLeft());
    }

    @Test
    void testRight_returnsCorrectValue() {
        underTest = Either.right(10);
        assertEquals(Integer.valueOf(10), underTest.get());
    }

    @Test
    void testRight_getLeftThrowsException() {
        underTest = Either.right(10);
        assertThrows(NoSuchElementException.class, () -> underTest.getLeft());
    }

    @Test
    void testRight_mapRight() {
        underTest = Either.right(10);
        final var result = underTest.map(param1 -> param1 * 2);
        assertEquals(Integer.valueOf(20), result.get());
    }

    @Test
    void testLeft_mapRight() {
        underTest = Either.left("under test");
        final var result = underTest.map(param1 -> param1 * 2);
        assertEquals(underTest, result);
    }

    @Test
    void testRight_bimapRight() {
        underTest = Either.right(10);
        final var result = underTest.bimap(leftMapper, rightMapper);
        assertEquals(Integer.valueOf(20), result.get());
    }

    @Test
    void testRight_foldRight() {
        underTest = Either.right(10);
        final var result = underTest.fold(param1 -> param1 + "mapped", param1 -> Integer.toString(param1 * 2));
        assertEquals(Integer.toString(20), result);
    }

    @Test
    void testLeft_isNotRight() {
        underTest = Either.left("Left value");
        assertFalse(underTest.isRight());
    }

    @Test
    void testLeft_isLeft() {
        underTest = Either.left("Left value");
        assertTrue(underTest.isLeft());
    }

    @Test
    void testLeft_getThrowsException() {
        underTest = Either.left("Left value");
        assertThrows(NoSuchElementException.class, () -> underTest.get());
    }

    @Test
    void testLeft_returnsCorrectValue() {
        underTest = Either.left("Left value");
        assertEquals("Left value", underTest.getLeft());
    }

    @Test
    void testLeft_mapLeft() {
        underTest = Either.left("Left value");
        final var result = underTest.mapLeft(leftMapper);
        assertEquals("Left value mapped", result.getLeft());
    }

    @Test
    void testRight_mapLeft() {
        underTest = Either.right(10);
        final var result = underTest.mapLeft(leftMapper);
        assertEquals(underTest, result);
    }

    @Test
    void testLeft_bimapLeft() {
        underTest = Either.left("Left value");
        final var result = underTest.bimap(leftMapper, rightMapper);
        assertEquals("Left value mapped", result.getLeft());
    }

    @Test
    void testLeft_foldLeft() {
        underTest = Either.left("Left value");
        final var result = underTest.fold(param1 -> "Left value mapped", param1 -> "Right value mapped");
        assertEquals("Left value mapped", result);
    }

    @Test
    void test_zipOrAccumulate10() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(550), result.get());
    }

    @Test
    void test_zipOrAccumulate10Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left6() {
        final Either<String, Integer> either6 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either1 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left7() {
        final Either<String, Integer> either7 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either1 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left8() {
        final Either<String, Integer> either8 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either1 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left9() {
        final Either<String, Integer> either9 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either1 = Either.right(90);
        final Either<String, Integer> either10 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate10Left10() {
        final Either<String, Integer> either10 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);
        final Either<String, Integer> either1 = Either.right(100);

        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(450), result.get());
    }

    @Test
    void test_zipOrAccumulate9Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left6() {
        final Either<String, Integer> either6 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either1 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left7() {
        final Either<String, Integer> either7 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either1 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left8() {
        final Either<String, Integer> either8 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either1 = Either.right(80);
        final Either<String, Integer> either9 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate9Left9() {
        final Either<String, Integer> either9 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);
        final Either<String, Integer> either1 = Either.right(90);


        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8, param9) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(360), result.get());
    }

    @Test
    void test_zipOrAccumulate8Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left6() {
        final Either<String, Integer> either6 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either1 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left7() {
        final Either<String, Integer> either7 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either1 = Either.right(70);
        final Either<String, Integer> either8 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate8Left8() {
        final Either<String, Integer> either8 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);
        final Either<String, Integer> either1 = Either.right(80);


        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7, param8) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(280), result.get());
    }

    @Test
    void test_zipOrAccumulate7Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left6() {
        final Either<String, Integer> either6 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either1 = Either.right(60);
        final Either<String, Integer> either7 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate7Left7() {
        final Either<String, Integer> either7 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);
        final Either<String, Integer> either1 = Either.right(70);

        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6, param7) ->
                param1 + param2 + param3 + param4 + param5 + param6 + param7;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(210), result.get());
    }

    @Test
    void test_zipOrAccumulate6Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);
        final Either<String, Integer> either6 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate6Left6() {
        final Either<String, Integer> either6 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);
        final Either<String, Integer> either1 = Either.right(60);

        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5, param6) ->
                param1 + param2 + param3 + param4 + param5 + param6;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }


    @Test
    void test_zipOrAccumulate5() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(150), result.get());
    }

    @Test
    void test_zipOrAccumulate5Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate5Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate5Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate5Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);
        final Either<String, Integer> either5 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate5Left5() {
        final Either<String, Integer> either5 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);
        final Either<String, Integer> either1 = Either.right(50);

        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4, param5) ->
                param1 + param2 + param3 + param4 + param5;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }


    @Test
    void test_zipOrAccumulate4() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);

        final Function4<Integer, Integer, Integer, Integer, Integer> sum = (param1, param2, param3, param4) ->
                param1 + param2 + param3 + param4;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(100), result.get());
    }

    @Test
    void test_zipOrAccumulate4Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);

        final Function4<Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4) ->
                param1 + param2 + param3 + param4;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate4Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);

        final Function4<Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4) ->
                param1 + param2 + param3 + param4;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate4Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);
        final Either<String, Integer> either4 = Either.right(40);

        final Function4<Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4) ->
                param1 + param2 + param3 + param4;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate4Left4() {
        final Either<String, Integer> either4 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);
        final Either<String, Integer> either1 = Either.right(40);

        final Function4<Integer, Integer, Integer, Integer, Integer> concat = (param1, param2, param3, param4) ->
                param1 + param2 + param3 + param4;

        final var result = Either.zipOrAccumulate(either1, either2, either3, either4, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }


    @Test
    void test_zipOrAccumulate3() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);

        final Function3<Integer, Integer, Integer, Integer> sum = (param1, param2, param3) ->
                param1 + param2 + param3;

        final var result = Either.zipOrAccumulate(either1, either2, either3, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(60), result.get());
    }

    @Test
    void test_zipOrAccumulate3Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);

        final Function3<Integer, Integer, Integer, Integer> concat = (param1, param2, param3) ->
                param1 + param2 + param3;

        final var result = Either.zipOrAccumulate(either1, either2, either3, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate3Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);
        final Either<String, Integer> either3 = Either.right(30);

        final Function3<Integer, Integer, Integer, Integer> concat = (param1, param2, param3) ->
                param1 + param2 + param3;

        final var result = Either.zipOrAccumulate(either1, either2, either3, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate3Left3() {
        final Either<String, Integer> either3 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);
        final Either<String, Integer> either1 = Either.right(30);

        final Function3<Integer, Integer, Integer, Integer> concat = (param1, param2, param3) ->
                param1 + param2 + param3;

        final var result = Either.zipOrAccumulate(either1, either2, either3, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate2() {
        final Either<String, Integer> either1 = Either.right(10);
        final Either<String, Integer> either2 = Either.right(20);

        final Function2<Integer, Integer, Integer> sum = Integer::sum;

        final var result = Either.zipOrAccumulate(either1, either2, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(30), result.get());
    }

    @Test
    void test_zipOrAccumulate2Left1() {
        final Either<String, Integer> either1 = Either.left("Error1");
        final Either<String, Integer> either2 = Either.right(20);

        final Function2<Integer, Integer, Integer> concat = Integer::sum;

        final var result = Either.zipOrAccumulate(either1, either2, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

    @Test
    void test_zipOrAccumulate2Left2() {
        final Either<String, Integer> either2 = Either.left("Error1");
        final Either<String, Integer> either1 = Either.right(20);

        final Function2<Integer, Integer, Integer> concat = Integer::sum;

        final var result = Either.zipOrAccumulate(either1, either2, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }



    @Test
    void testCompanionPrivateConstructor() {
        final Class<?> clazz = Either.Companion.class;
        final Constructor<?> declaredConstructor = clazz.getDeclaredConstructors()[0];
        assertTrue(Modifier.isPrivate(declaredConstructor.getModifiers()));
        declaredConstructor.setAccessible(true); // throws AssertException => newInstance wraps it in a InvocationTargetException
        assertThrows(InvocationTargetException.class, declaredConstructor::newInstance);

    }
}