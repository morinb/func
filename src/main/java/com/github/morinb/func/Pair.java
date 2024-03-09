package com.github.morinb.func;

import java.util.Objects;

public class Pair<T, R>
{
    private final T first;
    private final R second;

    public Pair(T first, R second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public R getSecond()
    {
        return second;
    }

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
    public int hashCode()
    {
        var result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
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