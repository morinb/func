package com.github.morinb.func;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents an optional value that may or may not exist.
 *
 * @param <T> the type of the value wrapped by this option
 */
public sealed interface Option<T>
        permits Option.None, Option.Some
{

    /**
     * Creates and returns an instance of `Option` representing a None value.
     *
     * @param <T> the type of the value that would have been wrapped by the None object
     * @return an instance of `None`
     */
    @SuppressWarnings("unchecked")
    static <T> Option<T> none()
    {
        return (Option<T>) None.INSTANCE;
    }

    /**
     * Creates an Option instance with a non-null value.
     *
     * @param value the value to be wrapped in the Option
     * @param <T>   the type of the value
     * @return an Option instance containing the specified value
     */
    static <T> Option<T> some(final T value)
    {
        return new Some<>(value);
    }

    /**
     * Creates an Option object that wraps the given value.
     * If the value is null, returns an empty Option.
     * Otherwise, returns an Option containing the value.
     *
     * @param <T>   the type of the value to be wrapped
     * @param value the value to be wrapped
     * @return an Option object containing the value, or an empty Option if the value is null
     */
    static <T> Option<T> of(final T value)
    {
        return value == null ? none() : some(value);
    }

    /**
     * Checks if the value is none.
     *
     * @return {@code true} if the value is none, {@code false} otherwise.
     */
    boolean isNone();

    /**
     * Retrieves the value of type T.
     *
     * @return the value of type T
     */
    T getValue();

    /**
     * Applies the given mapper function to the value of this Option and returns a new Option
     * containing the result.
     *
     * @param mapper the function to apply to the value of this Option
     * @param <R> the type of the result of the mapper function
     * @return a new Option containing the result of applying the mapper function, or Option.none() if this Option is None
     * @throws NullPointerException if the mapper function is null
     */
    default <R> Option<R> map(final Function1<T, R> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return isNone() ? Option.none() : Option.some(mapper.apply(this.getValue()));
    }

    /**
     * Filters the current option based on the given predicate.
     *
     * @param predicate the predicate used to filter the option
     *                  (must not be null)
     * @return the filtered option if the current value satisfies the predicate,
     *         or an empty option if the current option is empty or the value
     *         does not satisfy the predicate
     */
    default Option<T> filter(final Predicate<T> predicate)
    {
        Objects.requireNonNull(predicate, "predicate is null");
        return isNone() || predicate.test(this.getValue()) ? this : Option.none();
    }

    /**
     * Folds the value of the Option.
     *
     * @param ifNone the supplier function to be called when the Option is None
     * @param ifSome the function to be called when the Option is Some
     * @param <R> the type of the result value
     * @return the result value after folding the Option
     * @throws NullPointerException if either ifNone or ifSome is null
     */
    default <R> R fold(final Supplier<R> ifNone, final Function1<T, R> ifSome)
    {
        Objects.requireNonNull(ifNone, "ifNone is null");
        Objects.requireNonNull(ifSome, "ifSome is null");
        return isNone() ? ifNone.get() : ifSome.apply(this.getValue());
    }

    /**
     * Zips the value of this Option with the value of another Option.
     *
     * @param other the other Option to zip with
     * @param <R>   the type parameter of the other Option
     * @return an Option containing the zipped Pair of values if both Options are Some,
     *              or None if either this Option or the other Option is None
     */
    default <R> Option<Pair<T, R>> zip(final Option<R> other)
    {
        Objects.requireNonNull(other, "other is null");
        return isNone() || other.isNone() ? Option.none() : Option.some(new Pair<>(this.getValue(), other.getValue()));
    }

    /**
     * Returns the current Option if it is not None, otherwise returns the provided Option.
     *
     * @param other the Option to return if the current Option is None (not null)
     * @return the current Option if not None, otherwise the provided Option
     * @throws NullPointerException if the provided Option is null
     */
    default Option<T> orElse(final Option<T> other)
    {
        Objects.requireNonNull(other, "other is null");
        return isNone() ? other : this;
    }

    /**
     * Returns the value contained in the {@code Option} or the result of the supplied {@code Supplier} if the {@code Option} is empty.
     *
     * @param supplier the {@code Supplier} to provide a value if the {@code Option} is empty (not null)
     * @return the value contained in the {@code Option} if it is not empty, or the result of the supplied {@code Supplier} if it is empty
     * @throws NullPointerException if the {@code supplier} is null
     */
    default T getOrElse(final Supplier<T> supplier)
    {
        Objects.requireNonNull(supplier, "supplier is null");
        return isNone() ? supplier.get() : this.getValue();
    }

    /**
     * Returns the value contained in the {@code Option} or throws an exception obtained from the provided {@code throwableSupplier} if the {@code Option} is empty.
     *
     * @param <H> the type of the exception that may be thrown
     * @param throwableSupplier the function that supplies the exception to be thrown
     * @return the value contained in the {@code Option}
     * @throws H if the {@code Option} is empty
     * @throws NullPointerException if {@code throwableSupplier} is null
     */
    default <H extends Throwable> T getOrElseThrow(Function0<H> throwableSupplier) throws H
    {
        Objects.requireNonNull(throwableSupplier, "throwableSupplier is null");
        if (isNone())
        {
            throw throwableSupplier.apply();
        }
        return getValue();
    }


    /**
     * Applies the given mapper function to the value of this Option instance if it is present,
     * and returns the result as a new Option instance.
     *
     * @param <R> the type of the result value
     * @param mapper the mapper function to apply
     *
     * @return a new Option instance with the result value if this Option instance is not empty,
     *         otherwise returns an empty Option instance
     *
     * @throws NullPointerException if the mapper function is null
     */
    default <R> Option<R> flatMap(final Function1<T, Option<R>> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return isNone() ? Option.none() : mapper.apply(this.getValue());
    }

    /**
     * Represents a value wrapped in an Option.
     *
     * @param <T> the type of the value
     */
    final class Some<T> implements Option<T>
    {

        /**
         * Represents a value wrapped in an Option.
         *
         */
        private final T value;

        /**
         * Creates an Option instance with a non-null value.
         *
         * @param value the value to be wrapped in the Option
         */
        Some(final T value)
        {

            this.value = value;
        }

        /**
         * Checks if the value is none.
         *
         * @return {@code true} if the value is none, {@code false} otherwise.
         */
        @Override
        public boolean isNone()
        {
            return false;
        }

        /**
         * Retrieves the value contained in the Option.
         *
         * @return the value contained in the Option
         */
        @Override
        public T getValue()
        {
            return value;
        }
    }

    /**
     * Represents an empty option.
     * This class implements the Option interface, providing methods to check if the value is none and to retrieve the value.
     *
     * @param <T> the type of value held by the option
     */
    final class None<T> implements Option<T>
    {
        /**
         * Represents an instance of the Option class that is a None value.
         * This is a private static final variable that is used to represent an empty option.
         * It is of type Option<?>, which means it can hold any type of value.
         */
        private static final Option<?> INSTANCE = new None<>();

        /**
         * Private constructor for the None class.
         * Used to create an instance of None, representing a None value in the Option pattern.
         */
        private None()
        {
        }


        /**
         * Checks if the value of the Option is none.
         *
         * @return true if the value is none, false otherwise
         */
        @Override
        public boolean isNone()
        {
            return true;
        }

        /**
         * Retrieves the value of type T.
         *
         * @return the value of type T
         * @throws NoSuchElementException if called on None
         */
        @Override
        public T getValue()
        {
            throw new NoSuchElementException("Calling getValue on None");
        }
    }
}
