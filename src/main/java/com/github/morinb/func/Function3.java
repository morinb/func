package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function3<T1, T2, T3, R>
{
    R apply(T1 param1, T2 param2, T3 param3);

    default <V> Function3<T1, T2, T3, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3) -> after.apply(apply(param1, param2, param3));
    }


    default Function1<T1, Function1<T2, Function1<T3, R>>> curried()
    {
        return param1 -> param2 -> param3 -> apply(param1, param2, param3);
    }
}