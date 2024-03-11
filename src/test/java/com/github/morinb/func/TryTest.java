package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TryTest {

    // Testing isFailure method
    @Test
    void testIsFailure_WhenFailure() {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertTrue(tryInstance.isFailure(), "Expected isFailure to return true when Try instance is a Failure");
    }

    @Test
    void testIsFailure_WhenSuccess() {
        final var tryInstance = Try.of(() -> 5);
        assertFalse(tryInstance.isFailure(), "Expected isFailure to return false when Try instance is a Success");
    }

    // Testing get method
    @Test
    void testGet_WhenSuccess() {
        final var tryInstance = Try.of(() -> 5);
        assertEquals(5, tryInstance.get(), "Expected get to return provided value when Try instance is a Success");
    }

    @Test
    void testGet_WhenFailure() {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertThrows(NoSuchElementException.class, tryInstance::get, "Expected get to throw NoSuchElementException when Try instance is a Failure");
    }

    // Testing getCause method
    @Test
    void testGetCause_WhenFailure() {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertEquals("Test exception", tryInstance.getCause().getMessage(), "Expected getCause to return provided exception when Try instance is a Failure");
    }

    @Test
    void testGetCause_WhenSuccess() {
        final var tryInstance = Try.of(() -> 5);
        assertThrows(NoSuchElementException.class, tryInstance::getCause, "Expected getCause to throw NoSuchElementException when Try instance is a Success");
    }

    // Testing toEither method
    @Test
    void testToEither_WhenSuccess() {
        final var tryInstance = Try.of(() -> 5);
        final var either = tryInstance.toEither();
        assertTrue(either.isRight(), "Expected toEither to return Either Right when Try instance is a Success");
        assertEquals(5, either.get(), "Expected toEither to return provided value when Try instance is a Success");
    }

    @Test
    void testToEither_WhenFailure() {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        final var either = tryInstance.toEither();
        assertTrue(either.isLeft(), "Expected toEither to return Either Left when Try instance is a Failure");
        assertEquals("Test exception", either.getLeft().getMessage(), "Expected toEither to return provided exception when Try instance is a Failure");
    }
}