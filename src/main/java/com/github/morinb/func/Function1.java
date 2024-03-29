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
import java.util.function.Function;

/**
 * Represents a function that accepts one argument and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object)}.
 *
 * @param <T1> the type of the input to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Function1<T1, R> extends Function<T1, R>
{
    /**
     * Applies this function to the given parameter and returns the result.
     *
     * @param param1 the input parameter to apply the function to
     * @return the result of applying this function to the given parameter
     */
    R apply(T1 param1);

    /**
     * Returns a new Function1 that applies the given Function1 after applying this Function1.
     *
     * @param <V>    The return type of the given Function1.
     * @param after  The Function1 to apply after this Function1.
     * @return The composed Function1.
     * @throws NullPointerException if the given Function1 is null.
     */
    //implements andThen
    default <V> Function1<T1, V> andThen(final Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1) -> after.apply(apply(param1));
    }


    /**
     * Returns a curried version of the function.
     *
     * @return a curried version of the function
     */
    // implements curried
    default Function1<T1, R> curried()
    {
        return this;
    }
}
