package com.github.morinb.func;

import java.util.Objects;

/**
 * Represents a pair of two objects.
 *
 * @param <T> the type of the first object in the pair
 * @param <R> the type of the second object in the pair
 */
public record Pair<T, R>(T first, R second)
{

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var pair = (Pair<?, ?>) o;

        if (!Objects.equals(first, pair.first)) return false;
        return Objects.equals(second, pair.second);
    }

    @Override
    public String toString()
    {
        return "Pair{" +
               "first=" + first +
               ", second=" + second +
               '}';
    }
}