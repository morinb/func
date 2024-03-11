package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

/**
 * Function8 represents a function that accepts eight parameters and produces a result.
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 * @param <T4> the type of the fourth parameter
 * @param <T5> the type of the fifth parameter
 * @param <T6> the type of the sixth parameter
 * @param <T7> the type of the seventh parameter
 * @param <T8> the type of the eighth parameter
 * @param <R>  the type of the result
 */
@FunctionalInterface
public interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>
{
    /**
     * Applies a function to the given eight parameters and returns the result.
     *
     * @param param1 the first parameter of type T1
     * @param param2 the second parameter of type T2
     * @param param3 the third parameter of type T3
     * @param param4 the fourth parameter of type T4
     * @param param5 the fifth parameter of type T5
     * @param param6 the sixth parameter of type T6
     * @param param7 the seventh parameter of type T7
     * @param param8 the eighth parameter of type T8
     * @return the result of applying the function to the parameters
     */
    @SuppressWarnings("squid:S107")
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8);

    /**
     * Composes this function with another function, applying the other function after this function.
     *
     * @param <V>   the type of the result of the other function
     * @param after the other function to apply
     * @return a composed function that applies the other function after this function
     * @throws NullPointerException if the other function is null
     */
    default <V> Function8<T1, T2, T3, T4, T5, T6, T7, T8, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8) -> after.apply(apply(param1, param2, param3, param4, param5, param6, param7, param8));
    }

    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, R>>>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> param7 -> param8 -> apply(param1, param2, param3, param4, param5, param6, param7, param8);
    }
}