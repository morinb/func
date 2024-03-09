package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function5<T1, T2, T3, T4, T5, R>
{
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5);

    default <V> Function5<T1, T2, T3, T4, T5, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5) -> after.apply(apply(param1, param2, param3, param4, param5));
    }


    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, R>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> apply(param1, param2, param3, param4, param5);
    }
}