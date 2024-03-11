package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

/**
 *
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R>
{
    /**
     * Applies the function to the given parameters.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param param3 the third parameter
     * @return the result of applying the function
     */
    R apply(T1 param1, T2 param2, T3 param3);

    /**
     * Returns a new Function3 that applies the given Function after applying this Function3.
     *
     * @param <V>    The return type of the given Function.
     * @param after  The Function to apply after this Function3.
     * @return The composed Function3.
     * @throws NullPointerException if the given Function is null.
     */
    default <V> Function3<T1, T2, T3, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3) -> after.apply(apply(param1, param2, param3));
    }


    /**
     * Returns a curried version of the function.
     *
     * @param <T1> the type of the first parameter of the function
     * @param <T2> the type of the second parameter of the function
     * @param <T3> the type of the third parameter of the function
     * @param <R>  the type of the result of the function
     * @return a curried version of the function
     */
    default Function1<T1, Function1<T2, Function1<T3, R>>> curried()
    {
        return param1 -> param2 -> param3 -> apply(param1, param2, param3);
    }
}