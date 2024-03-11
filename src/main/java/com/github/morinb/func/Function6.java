package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a functional interface that accepts six arguments and produces a result.
 *
 * @param <T1> the type of the first argument
 * @param <T2> the type of the second argument
 * @param <T3> the type of the third argument
 * @param <T4> the type of the fourth argument
 * @param <T5> the type of the fifth argument
 * @param <T6> the type of the sixth argument
 * @param <R>  the type of the result
 */
@FunctionalInterface
public interface Function6<T1, T2, T3, T4, T5, T6, R>
{
    /**
     * Applies a function to the given parameters and returns the result.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param param3 the third parameter
     * @param param4 the fourth parameter
     * @param param5 the fifth parameter
     * @param param6 the sixth parameter
     * @return the result of applying the function to the parameters
     */
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6);

    /**
     * Returns a new Function6 that applies the given Function after applying this Function6.
     *
     * @param <V>   The return type of the given Function.
     * @param after The Function to apply after this Function6.
     * @return The composed Function6.
     * @throws NullPointerException if the given Function is null.
     */
    default <V> Function6<T1, T2, T3, T4, T5, T6, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6) -> after.apply(apply(param1, param2, param3, param4, param5, param6));
    }


    /**
     * Returns a curried version of the function.
     * <p>
     * The returned function takes multiple parameters of types T1, T2, T3, T4, T5, T6 and returns a result of type R.
     * The curried function can be called using the curried syntax, where each parameter is passed separately.
     *
     * @return The curried function.
     */
    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, R>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> apply(param1, param2, param3, param4, param5, param6);
    }

}