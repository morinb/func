package com.github.morinb.func;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a non-empty list.
 *
 * @param <T> the type of elements in the list
 */
public record NonEmptyList<T>(T head, List<T> tail)
{
    /**
     * Creates a NonEmptyList object.
     *
     * @param head the head element of the NonEmptyList
     * @throws NullPointerException if the head parameter is null
     */
    public NonEmptyList
    {
        Objects.requireNonNull(head);
    }

    /**
     * Applies the given function to each element of the list and returns a new NonEmptyList
     * with the transformed elements.
     *
     * @param f the function to apply to each element of the list
     * @param <U> the type of elements in the resulting NonEmptyList
     * @return a new NonEmptyList with the transformed elements
     */
    public <U> NonEmptyList<U> map(Function<T, U> f)
    {
        var newHeader = f.apply(head);
        var newTail = tail.stream().map(f).toList();
        return new NonEmptyList<>(newHeader, newTail);
    }

    /**
     * Applies the given function to each element in the current NonEmptyList and flattens the results into a new NonEmptyList.
     *
     * @param f the function to be applied to each element
     * @param <U> the type of elements in the resulting NonEmptyList
     * @return a new NonEmptyList containing the flattened results
     */
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

    /**
     * Creates a NonEmptyList from the given elements.
     *
     * @param elements the elements to be included in the NonEmptyList
     * @param <R>      the type of elements in the NonEmptyList
     * @return a NonEmptyList containing the given elements
     * @throws IllegalArgumentException if the elements are null or empty
     */
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

    /**
     * Creates a non-empty list from a Java List.
     *
     * @param <R>       the type of elements in the list
     * @param javaList  the Java List to convert
     * @return the non-empty list
     * @throws IllegalArgumentException if the Java List is null or empty
     */
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