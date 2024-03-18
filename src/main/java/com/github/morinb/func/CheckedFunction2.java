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
 * Represents a function that accepts two arguments and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T1> the type of the first input to the function
 * @param <T2> the type of the second input to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface CheckedFunction2<T1, T2, R>
{
    /**
     * Applies this function to the given parameters.
     *
     * @param param1 the first parameter of type T1
     * @param param2 the second parameter of type T2
     * @return the result of applying this function to the given parameters
     */
    @SuppressWarnings("squid:S112")
    R apply(T1 param1, T2 param2) throws Throwable;

    /**
     * Returns a new Function2 that applies the given Function after applying this Function2.
     *
     * @param <V>   The return type of the given Function.
     * @param after The Function to apply after this Function2.
     * @return The composed Function2.
     * @throws NullPointerException If the given Function is null.
     */
    //implements andThen
    default <V> CheckedFunction2<T1, T2, V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2) -> after.apply(apply(param1, param2));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    // implements curried
    default CheckedFunction1<T1, CheckedFunction1<T2, R>> curried()
    {
        return (T1 param1) -> (T2 param2) -> apply(param1, param2);
    }


}