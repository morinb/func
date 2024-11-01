/*
 * Copyright 2024 Baptiste MORIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.morinb.func;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a non-empty list of elements.
 *
 * @param <T> the type of elements in the list
 * @param head first element of the list
 * @param tail other elements
 */
public record NonEmptyList<T>(T head, FList<T> tail)
{
    /**
     * Creates a NonEmptyList object.
     *
     * @param head the head element of the NonEmptyList
     * @param tail other elements
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

    /**
     * Creates a new NonEmptyList object from the given FList.
     *
     * @param fList the FList object to create the NonEmptyList from
     * @param <R>   the type of elements in the FList
     * @return a new NonEmptyList object
     * @throws IllegalArgumentException if the fList parameter is null or empty
     */
    public static <R> NonEmptyList<R> of(final FList<R> fList)
    {
        if (fList == null || fList.isEmpty())
        {
            throw new IllegalArgumentException("List cannot be null or noop");
        }

        final var head = fList.head();
        final var tail = fList.tail();

        return new NonEmptyList<>(head, tail);
    }

    /**
     * Creates a {@link NonEmptyList} from the given elements.
     *
     * @param <R>      the type of elements in the list
     * @param elements the elements to include in the list
     * @return a NonEmptyList object containing the given elements
     * @throws IllegalArgumentException if the elements array is null, empty, or contains only null or noop elements
     */
    @SafeVarargs
    public static <R> NonEmptyList<R> of(final R... elements)
    {
        if (elements == null || elements.length == 0
            || Arrays.stream(elements).filter(Objects::nonNull).toArray().length == 0)
        {
            throw new IllegalArgumentException("Elements cannot be null or noop");
        }
        final var list = new LinkedList<R>();
        Collections.addAll(list, elements);
        return of(list);
    }

    /**
     * Creates a {@link NonEmptyList} object from a Java List.
     *
     * @param javaList the Java List to create the NonEmptyList from
     * @param <R>      the type of elements in the list
     * @return a new NonEmptyList object
     * @throws IllegalArgumentException if the javaList parameter is null or empty
     */
    public static <R> NonEmptyList<R> of(final List<R> javaList)
    {
        if (javaList == null || javaList.isEmpty())
        {
            throw new IllegalArgumentException("List cannot be null or noop");
        }

        final var head = javaList.get(0);
        final var tail = FList.of(new LinkedList<>(javaList.subList(1, javaList.size())));

        return new NonEmptyList<>(head, tail);
    }

    /**
     * Converts the NonEmptyList to an FList.
     *
     * @return an FList containing the same elements as the NonEmptyList
     */
    public FList<T> toFList()
    {

        return new FList<>(head, tail);
    }

    /**
     * Applies the given function to each element of the list and returns a new NonEmptyList
     * with the transformed elements.
     *
     * @param f   the function to apply to each element of the list
     * @param <U> the type of elements in the resulting NonEmptyList
     * @return a new NonEmptyList with the transformed elements
     * @throws NullPointerException if the function f is null
     */
    public <U> NonEmptyList<U> flatMap(final Function1<T, NonEmptyList<U>> f)
    {
        final var newHead = f.apply(head).head;
        final var newTail = tail.flatMap(t -> f.apply(t).toFList());

        return new NonEmptyList<>(newHead, newTail);
    }

    /**
     * Returns the number of elements in the NonEmptyList.
     *
     * @return the number of elements in the NonEmptyList
     */
    public int size()
    {
        return tail.size() + 1;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &ge; size())
     */
    public T get(final int index)
    {
        return index == 0 ? head : tail.get(index - 1);
    }

    @Override
    public String toString()
    {
        if (tail.isEmpty())
        {
            return head.toString();
        }
        return head.toString() + "::" + tail;
    }
}
