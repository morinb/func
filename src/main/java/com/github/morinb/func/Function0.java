package com.github.morinb.func;

import java.util.Objects;

/**
 * A functional interface representing a function with no arguments that returns a result of type R.
 *
 * @param <R> the type of the result
 */
@FunctionalInterface
public interface Function0<R>
{
    /**
     * Applies the function and returns the result.
     *
     * @return the result of applying the function
     */
    R apply();

    /**
     * Returns a new Function0 by composing this Function0 with the given Function1.
     * The resulting Function0 applies this Function0 first, and then applies the given Function1 to the result.
     *
     * @param <V>    the type of the result returned by the given Function1
     * @param after  the Function1 to be applied after this Function0 is applied
     * @return a new composed Function0
     * @throws NullPointerException if the given Function1 is null
     */
    // implements andThen
    default <V> Function0<V> andThen(Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return () -> after.apply(apply());
    }
}
