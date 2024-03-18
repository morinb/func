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
 *
 */
@FunctionalInterface
public interface CheckedFunction3<T1, T2, T3, R>
{
    /**
     * Applies the function to the given parameters.
     *
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param param3 the third parameter
     * @return the result of applying the function
     */
    @SuppressWarnings("squid:S112")
    R apply(T1 param1, T2 param2, T3 param3) throws Throwable;

    /**
     * Returns a new Function3 that applies the given Function after applying this Function3.
     *
     * @param <V>   The return type of the given Function.
     * @param after The Function to apply after this Function3.
     * @return The composed Function3.
     * @throws NullPointerException if the given Function is null.
     */
    default <V> CheckedFunction3<T1, T2, T3, V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3) -> after.apply(apply(param1, param2, param3));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    default CheckedFunction1<T1, CheckedFunction1<T2, CheckedFunction1<T3, R>>> curried()
    {
        return param1 -> param2 -> param3 -> apply(param1, param2, param3);
    }
}