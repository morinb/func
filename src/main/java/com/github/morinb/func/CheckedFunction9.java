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

import java.util.Objects;

/**
 * Represents a function that accepts nine arguments and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object, Object, Object, Object, Object, Object, Object, Object, Object)}.
 *
 * @param <T1> the type of the first input to the function
 * @param <T2> the type of the second input to the function
 * @param <T3> the type of the third input to the function
 * @param <T4> the type of the fourth input to the function
 * @param <T5> the type of the fifth input to the function
 * @param <T6> the type of the sixth input to the function
 * @param <T7> the type of the seventh input to the function
 * @param <T8> the type of the eighth input to the function
 * @param <T9> the type of the ninth input to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface CheckedFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>
{
    /**
     * Applies the function to the provided parameters and returns the result.
     *
     * @param param1 the first parameter of the function
     * @param param2 the second parameter of the function
     * @param param3 the third parameter of the function
     * @param param4 the fourth parameter of the function
     * @param param5 the fifth parameter of the function
     * @param param6 the sixth parameter of the function
     * @param param7 the seventh parameter of the function
     * @param param8 the eighth parameter of the function
     * @param param9 the ninth parameter of the function
     * @return the result of the function
     */
    @SuppressWarnings({"squid:S107", "squid:S112"})
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9) throws Throwable;

    /**
     * Returns a new Function9 that applies the given after function after applying this function.
     *
     * @param <V>   the type of the result of the after function
     * @param after the function to apply after this function
     * @return the composed function
     * @throws NullPointerException if the given after function is null
     */
    default <V> CheckedFunction9<T1, T2, T3, T4, T5, T6, T7, T8, T9, V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9) -> after.apply(apply(param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    default CheckedFunction1<T1, CheckedFunction1<T2, CheckedFunction1<T3, CheckedFunction1<T4, CheckedFunction1<T5, CheckedFunction1<T6, CheckedFunction1<T7, CheckedFunction1<T8, CheckedFunction1<T9, R>>>>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> param7 -> param8 -> param9 -> apply(param1, param2, param3, param4, param5, param6, param7, param8, param9);
    }
}