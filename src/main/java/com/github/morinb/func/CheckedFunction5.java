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
 * Represents a function that accepts five arguments and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object, Object, Object, Object, Object)}.
 *
 * @param <T1> the type of the first input to the function
 * @param <T2> the type of the second input to the function
 * @param <T3> the type of the third input to the function
 * @param <T4> the type of the fourth input to the function
 * @param <T5> the type of the fifth input to the function
 * @param <R>  the type of the result
 */
@FunctionalInterface
public interface CheckedFunction5<T1, T2, T3, T4, T5, R>
{
    /**
     * Applies this function to the given arguments.
     *
     * @param param1 the first input parameter
     * @param param2 the second input parameter
     * @param param3 the third input parameter
     * @param param4 the fourth input parameter
     * @param param5 the fifth input parameter
     * @return the result of applying this function to the given arguments
     * @throws Throwable if an exception occurs during function execution
     */
    @SuppressWarnings("squid:S112")
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5) throws Throwable;

    /**
     * Returns a new function that applies the provided function after applying this function.
     *
     * @param <V>   the type of the result of the after function
     * @param after the function to apply after this function
     * @return the composed function
     * @throws NullPointerException if the provided function is null
     */
    default <V> CheckedFunction5<T1, T2, T3, T4, T5, V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5) -> after.apply(apply(param1, param2, param3, param4, param5));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    default CheckedFunction1<T1, CheckedFunction1<T2, CheckedFunction1<T3, CheckedFunction1<T4, CheckedFunction1<T5, R>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> apply(param1, param2, param3, param4, param5);
    }
}