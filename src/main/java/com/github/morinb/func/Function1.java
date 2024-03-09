package com.github.morinb.func;

import java.util.Objects;

@FunctionalInterface
public interface Function1<T1, R>
{
    R apply(T1 param1);

    //implements andThen
    default <V> Function1<T1, V> andThen(Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1) -> after.apply(apply(param1));
    }


    // implements curried
    default Function1<T1, R> curried()
    {
        return this;
    }
}
