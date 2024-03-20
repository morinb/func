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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

/**
 * The Try interface represents a computation that may either result in a value or an exception.
 *
 * @param <T> The type of the value contained in the Try instance.
 */
public sealed interface Try<T>
    extends Value<T>
    permits Try.Success, Try.Failure
{

    /**
     * Creates a Try instance by applying the given supplier function.
     *
     * @param <U>      the type of the result
     * @param supplier the supplier function to apply
     * @return a Try instance representing the result of the supplier function
     * @throws NullPointerException if the supplier is null
     */
    @SuppressWarnings("squid:S1181")
    static <U> Try<U> of(final CheckedFunction0<? extends U> supplier)
    {
        Objects.requireNonNull(supplier, "supplier is null");
        try
        {
            return new Success<>(supplier.apply());
        } catch (final Throwable throwable)
        {
            return new Failure<>(throwable);
        }
    }

    /**
     * Returns the cause of this throwable or null if the cause is nonexistent or unknown.
     *
     * @return the cause of this throwable or null if the cause is nonexistent or unknown
     */
    Throwable getCause();

    /**
     * Returns the element represented by this Try object.
     *
     * @return The element represented by this Try object.
     */
    T get();

    /**
     * Checks if the current Try instance represents a failure.
     *
     * @return true if the Try instance represents a failure, false otherwise.
     */
    boolean isFailure();

    /**
     * Applies the given mapper function to the value contained in this Try instance and returns a new Try instance
     * with the result.
     * If this Try instance is a Failure, the original instance is returned without applying the mapper function.
     *
     * @param <U>    the result type of the mapper function
     * @param mapper the mapper function to apply
     * @return a new Try instance with the result of the mapper function applied, or the original instance if it is a Failure
     */
    @Override
    @SuppressWarnings("unchecked")
    default <U> Try<U> map(Function1<? super T, ? extends U> mapper)
    {
        return isFailure() ? (Try<U>) this : Try.of(() -> mapper.apply(get()));
    }

    /**
     * Applies the given function to the value contained in this Try instance, and returns a new Try instance
     * that is the result of the function application.
     * If this Try instance is a Failure, it is returned as it is.
     *
     * @param mapper the function to apply to the value contained in this Try instance
     * @param <U>    the type of the value contained in the resulting Try instance
     * @return a new Try instance that is the result of the function application, or this Try instance if it's a Failure
     */
    @SuppressWarnings("unchecked")
    default <U> Try<U> flatMap(Function<? super T, ? extends Try<? extends U>> mapper)
    {
        return isFailure() ? (Try<U>) this : (Try<U>) mapper.apply(get());
    }

    /**
     * Converts the Try object to an Either object.
     *
     * @return Either object representing the Try object
     */
    default Either<Throwable, T> toEither()
    {
        return isFailure() ? Either.left(getCause()) : Either.right(get());
    }

    /**
     * Returns true if the operation represented by this Try instance was successful,
     * false otherwise.
     *
     * @return true if success, false if failure
     */
    boolean isSuccess();


    /**
     * The Success class represents a successful result in the Try monad.
     *
     * @param <T> The type of the value contained in the Success instance.
     */
    record Success<T>(T value) implements Try<T> {

        @Override
        public Throwable getCause()
        {
            throw new NoSuchElementException("Calling getCause on Success");

        }

        @Override
        public T get()
        {
            return value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                private boolean hasNext = true;

                @Override
                public boolean hasNext() {
                    return hasNext;
                }

                @Override
                public T next() {
                    if (!hasNext) {
                        throw new NoSuchElementException();
                    }
                    hasNext = false;
                    return value;
                }
            };
        }

        @Override
        public boolean isFailure()
        {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }

    /**
     * The Failure class represents a failed result in the Try monad.
     *
     * @param <T> The type of the value contained in the Failure instance.
     */
    record Failure<T>(Throwable throwable) implements Try<T> {

        @Override
        public Throwable getCause()
        {
            return throwable;
        }

        @Override
        public T get()
        {
            throw new NoSuchElementException("Calling get on Failure");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public T next() {
                    throw new NoSuchElementException();
                }
            };
        }

        @Override
        public boolean isFailure()
        {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

    }
}
