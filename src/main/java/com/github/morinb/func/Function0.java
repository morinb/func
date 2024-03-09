package com.github.morinb.func;

import java.util.Objects;

@FunctionalInterface
public interface Function0<R>
{
    R apply();

    // implements andThen
    default <V> Function0<V> andThen(Function1<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return () -> after.apply(apply());
    }
}
