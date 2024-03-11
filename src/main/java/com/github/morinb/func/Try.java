package com.github.morinb.func;

import java.util.NoSuchElementException;
import java.util.Objects;

public sealed interface Try<T>
    permits Try.Success, Try.Failure
{

    Throwable getCause();
    T get();

    boolean isFailure();

    default Either<Throwable, T> toEither()
    {
        return isFailure() ? Either.left(getCause()) : Either.right(get());
    }

    @SuppressWarnings("squid:S1181")
    static <U> Try<U> of(final Function0<U> supplier)
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
        public boolean isFailure()
        {
            return false;
        }
    }

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
        public boolean isFailure()
        {
            return true;
        }

    }
}
