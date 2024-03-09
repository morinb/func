package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function0Test
{

    // Function0 object under test
    private final Function0<String> greetFn = () -> "Hello, world!";

    @Test
    void testApply()
    {
        // Act
        var actualResult = greetFn.apply();

        // Assert
        assertEquals("Hello, world!", actualResult);
    }

    @Test
    void testAndThen()
    {
        // Arrange
        Function0<String> nameFn = () -> "John Doe";
        Function1<String, String> greetingFn = name -> "Hello, " + name;

        var greetWithName = nameFn.andThen(greetingFn);

        // Act
        var actualResult = greetWithName.apply();

        // Assert
        assertEquals("Hello, John Doe", actualResult);
    }
}