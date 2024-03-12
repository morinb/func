package com.github.morinb.func;

import java.util.Objects;

/**
 * A functional interface representing a function that takes four parameters and produces a result.
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 * @param <T4> the type of the fourth parameter
 * @param <R>  the type of the result
 */
@FunctionalInterface
public interface CheckedFunction4<T1, T2, T3, T4, R>
{
    /**
     * Applies the function to the given parameters and returns the result.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param param3 the third parameter
     * @param param4 the fourth parameter
     * @return the result of applying the function to the parameters
     */
    @SuppressWarnings("squid:S112")
    R apply(T1 param1, T2 param2, T3 param3, T4 param4) throws Throwable;

    /**
     * Applies the given function after applying this function, and returns the result.
     *
     * @param after the function to apply after this function
     * @param <V>   the type of the result produced by the given function
     * @return the composed function
     * @throws NullPointerException if the given function is null
     */
    default <V> CheckedFunction4<T1, T2, T3, T4, V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3, T4 param4) -> after.apply(apply(param1, param2, param3, param4));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    default CheckedFunction1<T1, CheckedFunction1<T2, CheckedFunction1<T3, CheckedFunction1<T4, R>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> apply(param1, param2, param3, param4);
    }
}