package com.github.morinb.func;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>
{
    R apply(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9);

    default <V> Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9) -> after.apply(apply(param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    default Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, R>>>>>>>>> curried()
    {
        return param1 -> param2 -> param3 -> param4 -> param5 -> param6 -> param7 -> param8 -> param9 -> apply(param1, param2, param3, param4, param5, param6, param7, param8, param9);
    }
}