package com.github.morinb.func;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public sealed interface Try<T>
    extends Value<T>
    permits Try.Success, Try.Failure
{

    Throwable getCause();
    T get();

    boolean isFailure();


    @Override
    @SuppressWarnings("unchecked")
    default <U> Try<U> map(Function1<? super T, ? extends U> mapper)
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

    boolean isSuccess();


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
