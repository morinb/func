package com.github.morinb.func;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a non-empty list.
 *
 * @param <T> the type of elements in the list
 */
public record NonEmptyList<T>(T head, FList<T> tail)
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
     * @param f   the function to apply to each element of the list
     * @param <U> the type of elements in the resulting NonEmptyList
     * @return a new NonEmptyList with the transformed elements
     */
    public <U> NonEmptyList<U> map(final Function1<T, U> f)
    {
        final var newHeader = f.apply(head);
        final var newTail = tail.map(f);
        return new NonEmptyList<>(newHeader, newTail);
    }

    public static <R> NonEmptyList<R> of(final FList<R> fList)
    {
        if (fList == null || fList.isEmpty())
        {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        final var head = fList.head();
        final var tail = fList.tail();

        return new NonEmptyList<>(head, tail);
    }

    public FList<T> toFList()
    {

        return new FList<>(head, tail);
    }


    @SafeVarargs
    public static <R> NonEmptyList<R> of(final R... elements)
    {
        if (elements == null || elements.length == 0
            || Arrays.stream(elements).filter(Objects::nonNull).toArray().length == 0)
        {
            throw new IllegalArgumentException("Elements cannot be null or empty");
        }
        final var list = new LinkedList<R>();
        Collections.addAll(list, elements);
        return of(list);
    }

    public static <R> NonEmptyList<R> of(final List<R> javaList)
    {
        if (javaList == null || javaList.isEmpty())
        {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        final var head = javaList.get(0);
        final var tail = FList.of(new LinkedList<>(javaList.subList(1, javaList.size())));

        return new NonEmptyList<>(head, tail);
    }

    public <U> NonEmptyList<U> flatMap(final Function1<T, NonEmptyList<U>> f)
    {
        var newHead = f.apply(head).head;
        var newTail = tail.flatMap(t -> f.apply(t).toFList());

        return new NonEmptyList<>(newHead, newTail);
    }

    public int size()
    {
        return tail.size() + 1;
    }

    public T get(int index)
    {
        return index == 0 ? head : tail.get(index - 1);
    }
}
