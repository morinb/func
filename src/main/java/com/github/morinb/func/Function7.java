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
 * Represents a function that accepts seven arguments and produces a result.
 * This is a functional interface whose functional method is {@link #apply(Object, Object, Object, Object, Object, Object, Object)}.
 *
 * @param <T1> the type of the first argument to the function
 * @param <T2> the type of the second argument to the function
 * @param <T3> the type of the third argument to the function
 * @param <T4> the type of the fourth argument to the function
 * @param <T5> the type of the fifth argument to the function
 * @param <T6> the type of the sixth argument to the function
 * @param <T7> the type of the seventh argument to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Function7<T1, T2, T3, T4, T5, T6, T7, R>
{
    /**
     * Applies the given function to the seven provided arguments and returns the result.
     *
     * @param param1 the first argument to the function
     * @param param2 the second argument to the function
     * @param param3 the third argument to the function
     * @param param4 the fourth argument to the function
     * @param param5 the fifth argument to the function
     * @param param6 the sixth argument to the function
     * @param param7 the seventh argument to the function
     * @return the result of applying the function to the given arguments
     */
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7);

    /**
     * Returns a new {@link Function7} that applies the given {@link Function} after applying this {@link Function7}.
     *
     * @param <V>    the type of the result of the given {@link Function}
     * @param after  the {@link Function} to apply after this {@link Function7}
     * @return a new {@link Function7} that applies the given {@link Function} after applying this {@link Function7}
     * @throws NullPointerException if the given {@link Function} is null
     */
    default <V> Function7<T1, T2, T3, T4, T5, T6, T7, V> andThen(final Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7) -> after.apply(apply(param1, param2, param3, param4, param5, param6, param7));
    }


    /**
     * Returns a curried function, which takes 7 parameters of types T1, T2, T3, T4, T5, T6, T7 and
     * returns a result of type R.
     *
     * @return A curried function that takes 7 parameters and returns a result.
     */
    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, R>>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> param7 -> apply(param1, param2, param3, param4, param5, param6, param7);
    }
}