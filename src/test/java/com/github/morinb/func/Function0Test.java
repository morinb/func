package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Function0Test {

    // Function0 object under test
    private final Function0<String> greetFn = () -> "Hello, world!";

    @Test
    void testApply() {
        // Act
        final var actualResult = greetFn.apply();

        // Assert
        assertEquals("Hello, world!", actualResult);
    }

    @Test
    void testGet() {
        // Act
        final var actualResult = greetFn.get();

        // Assert
        assertEquals("Hello, world!", actualResult);
    }

    @Test
    void testAndThen() {
        // Arrange
        final Function0<String> nameFn = () -> "John Doe";
        final Function1<String, String> greetingFn = name -> "Hello, " + name;

        final var greetWithName = nameFn.andThen(greetingFn);

        // Act
        final var actualResult = greetWithName.apply();

        // Assert
        assertEquals("Hello, John Doe", actualResult);
    }
}