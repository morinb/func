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
 * A functional interface representing a function with no arguments that returns a result of type R.
 *
 * @param <R> the type of the result
 */
@FunctionalInterface
public interface CheckedFunction0<R>
{
    /**
     * Applies the function and returns the result.
     *
     * @return the result of applying the function
     * @throws Throwable if an exception occurs during function execution
     */
    @SuppressWarnings("squid:S112")
    R apply() throws Throwable;

    /**
     * Returns a new Function0 by composing this Function0 with the given CheckedFunction1.
     * The resulting Function0 applies this Function0 first, and then applies the given CheckedFunction1 to the result.
     *
     * @param <V>   the type of the result returned by the given CheckedFunction1
     * @param after the CheckedFunction1 to be applied after this Function0 is applied
     * @return a new composed Function0
     * @throws NullPointerException if the given CheckedFunction1 is null
     */
    // implements andThen
    default <V> CheckedFunction0<V> andThen(final CheckedFunction1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return () -> after.apply(apply());
    }
}
