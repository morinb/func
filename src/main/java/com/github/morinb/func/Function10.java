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
 * Represents a function that accepts ten arguments and produces a result.
 *
 * @param <T1> the type of the first parameter
 * @param <T2> the type of the second parameter
 * @param <T3> the type of the third parameter
 * @param <T4> the type of the fourth parameter
 * @param <T5> the type of the fifth parameter
 * @param <T6> the type of the sixth parameter
 * @param <T7> the type of the seventh parameter
 * @param <T8> the type of the eighth parameter
 * @param <T9> the type of the ninth parameter
 * @param <T10> the type of the tenth parameter
 * @param <R> the type of the result
 */
@SuppressWarnings("squid:S119")
@FunctionalInterface
public interface Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>
{
    /**
     * Applies the given function to the specified parameters and returns the result.
     *
     * @param param1 the first parameter of type T1
     * @param param2 the second parameter of type T2
     * @param param3 the third parameter of type T3
     * @param param4 the fourth parameter of type T4
     * @param param5 the fifth parameter of type T5
     * @param param6 the sixth parameter of type T6
     * @param param7 the seventh parameter of type T7
     * @param param8 the eighth parameter of type T8
     * @param param9 the ninth parameter of type T9
     * @param param10 the tenth parameter of type T10
     * @return the result of applying the function to the parameters
     */
    @SuppressWarnings("squid:S107")
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10);

    /**
     * Returns a composed function that first applies this function to its input,
     * and then applies the given function to the result. If evaluation of either
     * function throws an exception, it is relayed to the caller of the composed
     * function.
     *
     * @param after the function to apply after this function is applied
     * @param <V> the type of output of the {@code after} function, and of the
     *           composed function
     * @return a composed function that first applies this function and then applies
     *         the given function
     * @throws NullPointerException if {@code after} is null
     */
    default <V> Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, V> andThen(final Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "after is null");
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10) -> after.apply(apply(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    /**
     * Returns a curried function that takes 10 parameters and returns a result.
     *
     * @return A curried function that takes 10 parameters and returns a result.
     */
    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, R>>>>>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> param7 -> param8 -> param9 -> param10 -> apply(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
    }
}