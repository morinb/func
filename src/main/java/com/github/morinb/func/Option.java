package com.github.morinb.func;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public sealed interface Option<T>
        permits Option.None, Option.Some
{

    static <T> Option<T> none()
    {
        return (Option<T>) None.INSTANCE;
    }

    static <T> Option<T> some(T value)
    {
        return new Some<>(value);
    }

    static <T> Option<T> of(T value)
    {
        return value == null ? none() : some(value);
    }

    boolean isNone();

    T getValue();

    default <R> Option<R> map(Function1<T, R> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return isNone() ? Option.none() : Option.some(mapper.apply(this.getValue()));
    }

    default Option<T> filter(Predicate<T> predicate)
    {
        Objects.requireNonNull(predicate, "predicate is null");
        return isNone() || predicate.test(this.getValue()) ? this : Option.none();
    }

    default <R> R fold(Supplier<R> ifNone, Function1<T, R> ifSome)
    {
        Objects.requireNonNull(ifNone, "ifNone is null");
        Objects.requireNonNull(ifSome, "ifSome is null");
        return isNone() ? ifNone.get() : ifSome.apply(this.getValue());
    }

    default <R> Option<Pair<T, R>> zip(Option<R> other)
    {
        Objects.requireNonNull(other, "other is null");
        return isNone() || other.isNone() ? Option.none() : Option.some(new Pair<>(this.getValue(), other.getValue()));
    }

    default Option<T> orElse(Option<T> other)
    {
        Objects.requireNonNull(other, "other is null");
        return isNone() ? other : this;
    }

    // write getOrElse
    default T getOrElse(Supplier<T> supplier)
    {
        Objects.requireNonNull(supplier, "supplier is null");
        return isNone() ? supplier.get() : this.getValue();
    }

    // implements flatMap
    default <R> Option<R> flatMap(Function1<T, Option<R>> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return isNone() ? Option.none() : mapper.apply(this.getValue());
    }

    final class Some<T> implements Option<T>
    {

        private final T value;

        Some(T value)
        {

            this.value = value;
        }

        @Override
        public boolean isNone()
        {
            return false;
        }

        @Override
        public T getValue()
        {
            return value;
        }
    }

    final class None<T> implements Option<T>
    {
        private static final Option<?> INSTANCE = new None<>();

        private None()
        {
        }


        @Override
        public boolean isNone()
        {
            return true;
        }

        @Override
        public T getValue()
        {
            throw new NoSuchElementException("Calling getValue on None");
        }
    }
}
