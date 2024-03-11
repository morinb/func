package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T1> the type of the first input to the function
 * @param <T2> the type of the second input to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Function2<T1, T2, R>
{
    /**
     * Applies this function to the given parameters.
     *
     * @param param1 the first parameter of type T1
     * @param param2 the second parameter of type T2
     * @return the result of applying this function to the given parameters
     */
    R apply(T1 param1, T2 param2);

    /**
     * Returns a new Function2 that applies the given Function after applying this Function2.
     *
     * @param <V>   The return type of the given Function.
     * @param after The Function to apply after this Function2.
     * @return The composed Function2.
     * @throws NullPointerException If the given Function is null.
     */
    //implements andThen
    default <V> Function2<T1, T2, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2) -> after.apply(apply(param1, param2));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    // implements curried
    default Function1<T1, Function1<T2, R>> curried()
    {
        return (T1 param1) -> (T2 param2) -> apply(param1, param2);
    }


}