package com.github.morinb.func;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public record NonEmptyList<T>(T head, List<T> tail)
{
    public NonEmptyList
    {
        Objects.requireNonNull(head);
    }

    public <U> NonEmptyList<U> map(Function<T, U> f)
    {
        var newHeader = f.apply(head);
        var newTail = tail.stream().map(f).toList();
        return new NonEmptyList<>(newHeader, newTail);
    }

    public <U> NonEmptyList<U> flatMap(Function<T, NonEmptyList<U>> f)
    {
        var newHeaderList = f.apply(head);
        var newHeader = newHeaderList.head();
        List<U> newTail = new ArrayList<>(newHeaderList.tail());
        tail.stream().map(f)
                .forEach(nonEmptyList -> {
                    newTail.add(nonEmptyList.head());
                    newTail.addAll(nonEmptyList.tail());
                });
        return new NonEmptyList<>(newHeader, newTail);
    }

    static <R> NonEmptyList<R> of(R... elements)
    {
        if (elements == null || elements.length == 0)
        {
            throw new IllegalArgumentException("Elements cannot be null or empty");
        }
        var list = new LinkedList<R>();
        Collections.addAll(list, elements);
        return of(list);
    }

    static <R> NonEmptyList<R> of(List<R> javaList)
    {
        if (javaList == null || javaList.isEmpty())
        {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        var head = javaList.get(0);
        List<R> tail = new ArrayList<>(javaList.subList(1, javaList.size()));

        return new NonEmptyList<>(head, tail);

    }
}