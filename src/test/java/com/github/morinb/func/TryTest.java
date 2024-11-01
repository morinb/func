/*
 * Copyright 2024 Baptiste MORIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.morinb.func;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TryTest
{

    // Testing isFailure method
    @Test
    void testIsFailure_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertTrue(tryInstance.isFailure(), "Expected isFailure to return true when Try instance is a Failure");
    }

    @Test
    void testIsFailure_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        assertFalse(tryInstance.isFailure(), "Expected isFailure to return false when Try instance is a Success");
    }

    // Testing get method
    @Test
    void testGet_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        assertEquals(5, tryInstance.get(), "Expected get to return provided value when Try instance is a Success");
    }

    @Test
    void testGet_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertThrows(NoSuchElementException.class, tryInstance::get, "Expected get to throw NoSuchElementException when Try instance is a Failure");
    }

    // Testing getCause method
    @Test
    void testGetCause_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertEquals("Test exception", tryInstance.getCause().getMessage(), "Expected getCause to return provided exception when Try instance is a Failure");
    }

    @Test
    void testGetCause_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        assertThrows(NoSuchElementException.class, tryInstance::getCause, "Expected getCause to throw NoSuchElementException when Try instance is a Success");
    }

    // Testing toEither method
    @Test
    void testToEither_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        final var either = tryInstance.toEither();
        assertTrue(either.isRight(), "Expected toEither to return Either Right when Try instance is a Success");
        assertEquals(5, either.get(), "Expected toEither to return provided value when Try instance is a Success");
    }

    @Test
    void testToEither_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        final var either = tryInstance.toEither();
        assertTrue(either.isLeft(), "Expected toEither to return Either Left when Try instance is a Failure");
        assertEquals("Test exception", either.getLeft().getMessage(), "Expected toEither to return provided exception when Try instance is a Failure");
    }

    @Test
    void testMap_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        final var mappedInstance = tryInstance.map(x -> x * 2);
        assertFalse(mappedInstance.isFailure(), "Expected map to return Success when Try instance is a Success");
        assertEquals(10, mappedInstance.get(), "Expected map to correctly apply the function to the Success instance");
    }

    @Test
    void testMap_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        final var mappedInstance = tryInstance.map(x -> x * 2);
        assertTrue(mappedInstance.isFailure(), "Expected map to return Failure when Try instance is a Failure");
        assertThrows(NoSuchElementException.class, mappedInstance::get, "Expected get to throw NoSuchElementException when mapped instance is a Failure");
    }

    @Test
    void testFlatMap_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        final var flatMappedInstance = tryInstance.flatMap(x -> Try.of(() -> x * 2));
        assertFalse(flatMappedInstance.isFailure(), "Expected flatMap to return Success when Try instance is a Success");
        assertEquals(10, flatMappedInstance.get(), "Expected flatMap to correctly apply the function to the Success instance and flatten the Try");
    }

    @Test
    void testFlatMap_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        final var flatMappedInstance = tryInstance.flatMap(x -> Try.of(() -> x * 2));
        assertTrue(flatMappedInstance.isFailure(), "Expected flatMap to return Failure when Try instance is a Failure");
        assertThrows(NoSuchElementException.class, flatMappedInstance::get, "Expected get to throw NoSuchElementException when flatMapped instance is a Failure");
    }

    /**
     * This method tests the happy path for the getOrElse(T other) method.
     */
    @Test
    void getOrElse_Null_Success()
    {
        final var tryInstance = Try.of(() -> 1);
        assertEquals(1, tryInstance.getOrElse(2)); // 1 is expected as Try instance is a Success
    }

    /**
     * This method tests the failure path for the getOrElse(T other) method.
     */
    @Test
    void getOrElse_Null_Failure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new Exception("Failure");
        });
        assertEquals(2, tryInstance.getOrElse(2)); // 2 is expected as Try instance is a Failure
    }

    /**
     * This method tests the happy path for the getOrElse(Function0<? extends T> supplier) method.
     */
    @Test
    void getOrElse_Function0_Success()
    {
        final var tryInstance = Try.of(() -> 1);
        assertEquals(1, tryInstance.getOrElse(() -> 2)); // 1 is expected as Try instance is a Success
    }

    /**
     * This method tests the failure path for the getOrElse(Function0<? extends T> supplier) method.
     */
    @Test
    void getOrElse_Function0_Failure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new Exception("Failure");
        });
        assertEquals(2, tryInstance.getOrElse(() -> 2)); // 2 is expected as Try instance is a Failure
    }

    /**
     * This method tests the failure path for the getOrElse(Function0<? extends T> supplier) method with a null supplier.
     */
    @Test
    void getOrElse_Function0_Null()
    {
        final var tryInstance = Try.of(() -> 1);
        assertThrows(NullPointerException.class, () -> tryInstance.getOrElse((Supplier<? extends Integer>) null)); // Fail as the supplier is null
    }

    // Testing iterator method for Success class
    @Test
    void testIterator_hasNext_WhenCalledTwice()
    {
        final var successInstance = Try.of(() -> 5);
        final var iterator = successInstance.iterator();
        assertTrue(iterator.hasNext(), "Expected hasNext to return true when first called on a Success iterator");
        iterator.next();
        assertFalse(iterator.hasNext(), "Expected hasNext to return false when called second time on a Success iterator");
    }

    @Test
    void testIterator_next_WhenCalledTwice()
    {
        final var successInstance = Try.of(() -> 5);
        final var iterator = successInstance.iterator();
        assertEquals(5, iterator.next(), "Expected next to return Success value when first called on a Success iterator");
        assertThrows(NoSuchElementException.class, iterator::next, "Expected next to throw NoSuchElementException when called second time on a Success iterator");
    }

    @Test
    void testIterator_WhenFailure()
    {
        final var failureInstance = Try.of(() -> {
            throw new RuntimeException("runtime");
        });
        final var iterator = failureInstance.iterator();
        assertFalse(iterator.hasNext(), "Expected hasNext to return false when called on a Failure iterator");

        assertThrows(NoSuchElementException.class, iterator::next, "Expected next to throw NoSuchElementException when called second time on a Success iterator");
    }

    @Test
    void testIsSuccess_WhenFailure()
    {
        final Try<Integer> tryInstance = Try.of(() -> {
            throw new RuntimeException("Test exception");
        });
        assertFalse(tryInstance.isSuccess(), "Expected isSuccess to return false when Try instance is Failure");
    }

    @Test
    void testIsSuccess_WhenSuccess()
    {
        final var tryInstance = Try.of(() -> 5);
        assertTrue(tryInstance.isSuccess(), "Expected isSuccess to return true when Try instance is Success");
    }

    @Test
    void recoverArithmeticException()
    {
        assertEquals(Integer.MAX_VALUE,
                Try.of(() -> 1 / 0)
                        .recover(ArithmeticException.class, e -> Integer.MAX_VALUE)
                        .get()
        );
    }

    @Test
    void recoverNotCalledOnSuccess()
    {
        assertEquals("ABC", Try.of(() -> "ABC").recover(Throwable.class, e -> "ZZZ").get());

    }

    @Test
    void recoverNotCalledOnWrongExceptionClass()
    {
        assertInstanceOf(Try.Failure.class, Try.of(() -> {
                            throw new NullPointerException();
                        })
                        .recover(ClassCastException.class, e -> "ZZZ")
        );

    }
}
