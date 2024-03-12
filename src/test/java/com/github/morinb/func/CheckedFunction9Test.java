package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckedFunction9Test
{

    private final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> checkedFunc =
            (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;

    @Test
    void testAndThenSuccessful() throws Throwable
    {
        CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> func =
                (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;

        CheckedFunction1<Integer, String> after = a -> "Result: " + a;

        CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, String> composedFunc = func.andThen(after);

        String result = composedFunc.apply(1, 1, 1, 1, 1, 1, 1, 1, 1);

        assertEquals("Result: 9", result, "AndThen functionality operates as expected");
    }

    @Test
    void testAndThenWithExceptionInAfter()
    {

        CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> func =
                (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;

        CheckedFunction1<Integer, String> after = a -> {
            throw new IllegalArgumentException("Exception in after function");
        };

        CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, String> composedFunc = func.andThen(after);

        assertThrows(IllegalArgumentException.class, () -> composedFunc.apply(1, 1, 1, 1, 1, 1, 1, 1, 1),
                "AndThen functionality propagates exceptions as expected");
    }

    @Test
    void curriedMethodShouldReturnTheSameResultAsOriginalMethod() throws Throwable
    {
        // Arrange & Act
        final int expected = 45;
        final int result = checkedFunc.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void curriedMethodShouldThrowExceptionWhenNullIsPassed()
    {
        // Arrange
        final CheckedFunction9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> nullFunc = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> nullFunc.curried().apply(1).apply(2).apply(3).apply(4).apply(5).apply(6).apply(7).apply(8).apply(9));
    }
}