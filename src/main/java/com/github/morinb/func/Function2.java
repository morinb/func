package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function2<T1, T2, R>
{
    R apply(T1 param1, T2 param2);

    //implements andThen
    default <V> Function2<T1, T2, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2) -> after.apply(apply(param1, param2));
    }


    // implements curried
    default Function1<T1, Function1<T2, R>> curried()
    {
        return (T1 param1) -> (T2 param2) -> apply(param1, param2);
    }


}