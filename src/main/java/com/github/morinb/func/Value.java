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
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The Value interface represents a value that may or may not be present. It provides various operations
 * for working with values such as checking for containment, applying operations to the value, and handling
 * cases where the value is absent.
 *
 * @param <T> The type of the value.
 */
public interface Value<T> extends Iterable<T> {


    /**
     * Narrows the given value to its specific type.
     *
     * @param value The value to be narrowed.
     * @param <T>   The specific type of the value.
     * @return The narrowed value.
     */
    @SuppressWarnings("unchecked")
    static <T> Value<T> narrow(Value<? extends T> value) {
        return (Value<T>) value;
    }

    /**
     * Returns whether this Value contains the specified value.
     *
     * @param value The value to be checked for containment.
     * @return {@code true} if the value is contained in this Value, {@code false} otherwise.
     */
    default boolean contains(T value) {
        return exists(e -> Objects.equals(e, value));
    }

    /**
     * Returns whether any element in this iterable satisfies the specified predicate.
     *
     * @param predicate the predicate to be evaluated
     * @return {@code true} if any element satisfies the predicate, {@code false} otherwise
     * @throws NullPointerException if the predicate is {@code null}
     */
    default boolean exists(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        for (T element : this) {
            if (predicate.test(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether all elements in this iterable satisfy the specified predicate.
     *
     * @param predicate the predicate to be evaluated
     * @return {@code true} if all elements satisfy the predicate, {@code false} otherwise
     * @throws NullPointerException if the predicate is {@code null}
     */
    default boolean forAll(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return !exists(predicate.negate());
    }

    /**
     * Performs the given action on each element of this iterable.
     *
     * @param action the action to be performed on each element
     * @throws NullPointerException if the action is null
     */
    @Override
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action, "action is null");
        for (T t : this) {
            action.accept(t);
        }
    }

    /**
     * Returns the element represented by this Value.
     *
     * @return The element represented by this Value.
     */
    T get();

    /**
     * Returns the element represented by this Value if it is not empty, otherwise returns the provided value.
     *
     * @param other The value to be returned if this Value is empty.
     * @return The element represented by this Value if it is not empty, otherwise the provided value.
     */
    default T getOrElse(T other) {
        return isEmpty() ? other : get();
    }

    /**
     * Returns the element represented by this Value, or the result of the supplier function if the Value is noop.
     *
     * @param supplier the supplier function to provide the result if the Value is noop. Must not be null.
     * @return The element represented by this Value, or the result of the supplier function if the Value is noop.
     * @throws NullPointerException if the supplier is null
     */
    default T getOrElse(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return isEmpty() ? supplier.get() : get();
    }

    /**
     * Returns the element represented by this Value, or throws an exception if the Value is noop.
     *
     * @param exceptionSupplier the supplier function to provide the exception if the Value is noop. Must not be null.
     * @param <X>               the type of exception to be thrown
     * @return The element represented by this Value.
     * @throws X                    if the Value is noop
     * @throws NullPointerException if the exceptionSupplier is null
     */
    default <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier, "exceptionSupplier is null");
        if (isEmpty()) {
            throw exceptionSupplier.get();
        } else {
            return get();
        }
    }


    /**
     * Returns the element represented by this Value, or the result of the supplier function if the Value is noop.
     *
     * @param supplier the supplier function to provide the result if the Value is noop. Must not be null.
     * @return The element represented by this Value, or the result of the supplier function if the Value is noop.
     * @throws NullPointerException if the supplier is null
     */
    default T getOrElseTry(CheckedFunction0<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        return isEmpty() ? Try.of(supplier).get() : get();
    }

    /**
     * Returns the element represented by this Value, or {@code null} if the Value is noop.
     *
     * @return The element represented by this Value, or {@code null} if the Value is noop.
     */
    default T getOrNull() {
        return isEmpty() ? null : get();
    }

    /**
     * Returns whether this Value is noop or not.
     *
     * @return {@code true} if this Value is noop, {@code false} otherwise.
     */
    boolean isEmpty();


    /**
     * Applies the given mapper function to the value of this Value object and returns a new Value object
     * with the result of the mapping operation.
     *
     * @param <U>    The type of the elements in the new Value object.
     * @param mapper The mapper function to apply to each value.
     * @return A new Value object with the result of the mapping operation.
     */
    <U> Value<U> map(Function1<? super T, ? extends U> mapper);

    /**
     * Returns an iterator over the elements of the collection.
     *
     * @return an Iterator.
     */
    @Override
    Iterator<T> iterator();

    /**
     * Converts the current value to an Either object, with the specified value as the left value.
     * If the current object is already an Either, the left value will be replaced with the specified value.
     * If the current object is noop, a new Either object with the specified value as the left value will be returned.
     * If the current object is not noop, a new Either object with the current value as the right value will be returned.
     *
     * @param left The left value for the resulting Either object.
     * @param <L>  The type of the left value.
     * @return An Either object with the specified left value.
     */
    default <L> Either<L, T> toEither(L left) {
        if (this instanceof Either) {
            return ((Either<?, T>) this).mapLeft(ignored -> left);
        } else {
            return isEmpty() ? Either.left(left) : Either.right(get());
        }
    }

    /**
     * Returns the current object as a Try instance.
     *
     * @return The current object as a Try instance.
     */
    default Try<T> toTry() {
        if (this instanceof Try) {
            return (Try<T>) this;
        } else {
            return Try.of(this::get);
        }
    }
}
