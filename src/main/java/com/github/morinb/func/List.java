package com.github.morinb.func;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class List<T>
{
    private java.util.List<T> innerList;

    private List(java.util.List<T> list)
    {
        innerList = new LinkedList<>(list);
    }

    public <R> List<R> map(Function<? super T, ? extends R> function)
    {
        java.util.List<R> newList = new LinkedList<>();
        for (var element : innerList)
        {
            newList.add(function.apply(element));
        }
        return new List<>(newList);
    }

    public <R> List<R> flatMap(Function<? super T, ? extends List<? extends R>> function)
    {
        java.util.List<R> newList = new LinkedList<>();
        for (var element : innerList)
        {
            newList.addAll(function.apply(element).toJavaList());
        }
        return new List<>(newList);
    }


    public static <R> List<R> of(R... values)
    {
        final var list = new LinkedList<R>();
        Collections.addAll(list, values);
        return new List<>(list);
    }

    public static <R> List<R> of(java.util.List<R> values)
    {
        final var list = new LinkedList<R>(values);
        return new List<>(list);
    }

    public boolean isEmpty()
    {
        return innerList.isEmpty();
    }

    public int size()
    {
        return innerList.size();
    }

    public boolean contains(Object o)
    {
        return innerList.contains(o);
    }

    public boolean containsAll(List<?> c)
    {
        return innerList.containsAll(c.toJavaList());
    }

    public int indexOf(Object o)
    {
        return innerList.indexOf(o);
    }

    public T get(int index)
    {
        return innerList.get(index);
    }

    public List<T> add(T value)
    {
        java.util.List<T> newList = new LinkedList<>(this.innerList);
        newList.add(value);
        return new List<>(newList);
    }

    public java.util.List<T> toJavaList()
    {
        return new LinkedList<>(innerList);
    }

    public List<T> addAll(List<T> values)
    {
        java.util.List<T> newList = new LinkedList<>(this.innerList);
        newList.addAll(values.toJavaList());
        return new List<>(newList);
    }

    public List<T> filter(Predicate<? super T> predicate) {
        var newList = innerList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new List<>(newList);
    }
}
