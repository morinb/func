package com.github.morinb.func;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public sealed interface Try<T>
    permits Try.Success, Try.Failure
{

    Throwable getCause();
    T get();

    boolean isFailure();

    @SuppressWarnings("unchecked")
    default <U> Try<U> map(Function1<T, U> mapper)
    {
        return isFailure() ? (Try<U>) this : Try.of(() -> mapper.apply(get()));
    }

    @SuppressWarnings("unchecked")
    default <U> Try<U> flatMap(Function<? super T, ? extends Try<? extends U>> mapper)
    {
        return isFailure() ? (Try<U>) this : (Try<U>) mapper.apply(get());
    }

    default Either<Throwable, T> toEither()
    {
        return isFailure() ? Either.left(getCause()) : Either.right(get());
    }

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

    default T getOrElse(T other)
    {
        return isFailure() ? other : get();
    }

    default T getOrElse(Function0<? extends T> supplier)
    {
        Objects.requireNonNull(supplier, "supplier is null");
        return isFailure() ? supplier.apply() : get();
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
