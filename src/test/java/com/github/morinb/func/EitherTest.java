package com.github.morinb.func;


import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class EitherTest
{
    private Either<String, Integer> underTest;
    private Function1<String, String> leftMapper = str -> str + " mapped";
    private Function1<Integer, Integer> rightMapper = num -> num * 2;

    @Test
    void testRight_isRight()
    {
        underTest = Either.right(10);
        assertTrue(underTest.isRight());
    }

    @Test
    void testRight_isNotLeft()
    {
        underTest = Either.right(10);
        assertFalse(underTest.isLeft());
    }

    @Test
    void testRight_returnsCorrectValue()
    {
        underTest = Either.right(10);
        assertEquals(Integer.valueOf(10), underTest.get());
    }

    @Test
    void testRight_getLeftThrowsException()
    {
        underTest = Either.right(10);
        assertThrows(NoSuchElementException.class, () -> {
            underTest.getLeft();
        });
    }

    @Test
    void testRight_mapRight()
    {
        underTest = Either.right(10);
        var result = underTest.map(param1 -> param1 * 2);
        assertEquals(Integer.valueOf(20), result.get());
    }

    @Test
    void testLeft_mapRight()
    {
        underTest = Either.left("under test");
        var result = underTest.map(param1 -> param1 * 2);
        assertEquals(underTest, result);
    }

    @Test
    void testRight_bimapRight()
    {
        underTest = Either.right(10);
        var result = underTest.bimap(leftMapper, rightMapper);
        assertEquals(Integer.valueOf(20), result.get());
    }

    @Test
    void testRight_foldRight()
    {
        underTest = Either.right(10);
        var result = underTest.fold(param1 -> param1 + "mapped", param1 -> Integer.toString(param1 * 2));
        assertEquals(Integer.toString(20), result);
    }

    @Test
    void testLeft_isNotRight()
    {
        underTest = Either.left("Left value");
        assertFalse(underTest.isRight());
    }

    @Test
    void testLeft_isLeft()
    {
        underTest = Either.left("Left value");
        assertTrue(underTest.isLeft());
    }

    @Test
    void testLeft_getThrowsException()
    {
        underTest = Either.left("Left value");
        assertThrows(NoSuchElementException.class, () -> underTest.get());
    }

    @Test
    void testLeft_returnsCorrectValue()
    {
        underTest = Either.left("Left value");
        assertEquals("Left value", underTest.getLeft());
    }

    @Test
    void testLeft_mapLeft()
    {
        underTest = Either.left("Left value");
        var result = underTest.mapLeft(leftMapper);
        assertEquals("Left value mapped", result.getLeft());
    }

    @Test
    void testRight_mapLeft()
    {
        underTest = Either.right(10);
        var result = underTest.mapLeft(leftMapper);
        assertEquals(underTest, result);
    }

    @Test
    void testLeft_bimapLeft()
    {
        underTest = Either.left("Left value");
        var result = underTest.bimap(leftMapper, rightMapper);
        assertEquals("Left value mapped", result.getLeft());
    }

    @Test
    void testLeft_foldLeft()
    {
        underTest = Either.left("Left value");
        var result = underTest.fold(param1 -> "Left value mapped", param1 -> "Right value mapped");
        assertEquals("Left value mapped", result);
    }

    @Test
    void test_zipOrAccumulate10()
    {
        Either<String, Integer> either1 = Either.right(10);
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> sum =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, sum);

        assertTrue(result.isRight());
        assertEquals(Integer.valueOf(550), result.get());
    }

    @Test
    void test_zipOrAccumulate10Left1()
    {
        Either<String, Integer> either1 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left2()
    {
        Either<String, Integer> either2 = Either.left("Error1");
        Either<String, Integer> either1 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left3()
    {
        Either<String, Integer> either3 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either1 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left4()
    {
        Either<String, Integer> either4 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either1 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left5()
    {
        Either<String, Integer> either5 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either1 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left6()
    {
        Either<String, Integer> either6 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either1 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left7()
    {
        Either<String, Integer> either7 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either1 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left8()
    {
        Either<String, Integer> either8 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either1 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left9()
    {
        Either<String, Integer> either9 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either1 = Either.right(90);
        Either<String, Integer> either10 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }
    @Test
    void test_zipOrAccumulate10Left10()
    {
        Either<String, Integer> either10 = Either.left("Error1");
        Either<String, Integer> either2 = Either.right(20);
        Either<String, Integer> either3 = Either.right(30);
        Either<String, Integer> either4 = Either.right(40);
        Either<String, Integer> either5 = Either.right(50);
        Either<String, Integer> either6 = Either.right(60);
        Either<String, Integer> either7 = Either.right(70);
        Either<String, Integer> either8 = Either.right(80);
        Either<String, Integer> either9 = Either.right(90);
        Either<String, Integer> either1 = Either.right(100);

        Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> concat =
                (param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) ->
                        param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8 + param9 + param10;

        var result = Either.zipOrAccumulate(either1, either2, either3, either4, either5, either6, either7, either8, either9, either10, concat);

        assertTrue(result.isLeft());

        assertEquals(NonEmptyList.of("Error1"), result.getLeft());
    }

}