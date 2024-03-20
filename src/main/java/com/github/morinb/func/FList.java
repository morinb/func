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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;


/**
 * Appends the elements of the provided list to the current FList and returns a new FList instance.
 *
 * @param <T>  the type of the elements in the list
 * @param head the first element of the list
 * @param tail the list whose elements are to be appended
 */
public record FList<T>(T head, com.github.morinb.func.FList<T> tail)
{

    /**
     * Constructs a new FList instance with the provided element as the head.
     *
     * @param head the element to be added as the head of the FList
     * @param <U>  the type of the element
     * @return a new FList instance with the provided element as the head
     */
    public static <U> FList<U> of(U head)
    {
        return FList.<U>empty().prepend(head);
    }


    /**
     * Constructs a new FList instance with the provided elements.
     *
     * @param elements the elements to be added to the FList
     * @param <U>      the type of the elements
     * @return a new FList instance populated with the provided elements
     */
    @SafeVarargs
    public static <U> FList<U> of(U... elements)
    {
        FList<U> list = FList.empty();
        for (var i = elements.length - 1; i >= 0; i--)
        {
            list = list.prepend(elements[i]);
        }
        return list;
    }

    /**
     * Constructs a new FList instance with the provided elements.
     *
     * @param elements the elements to be added to the FList
     * @param <U>      the type of the elements
     * @return a new FList instance populated with the provided elements
     */
    public static <U> FList<U> of(List<U> elements)
    {
        FList<U> list = FList.empty();
        for (var i = elements.size() - 1; i >= 0; i--)
        {
            list = list.prepend(elements.get(i));
        }
        return list;
    }

    /**
     * Creates a new FList instance that is empty.
     *
     * @param <U> the type of the elements in the FList
     * @return a new FList instance that is empty
     */
    public static <U> FList<U> empty()
    {
        return new FList<>(null, null);
    }

    /**
     * Reverses the order of the elements in the FList.
     *
     * @return a new FList with the order of elements reversed
     */
    public FList<T> reverse()
    {
        if (isEmpty())
        {
            return this;
        }
        else
        {
            return tail.reverse().append(head);
        }

    }

    /**
     * Checks if the FList is empty or not.
     *
     * @return true if the FList is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return head == null;
    }

    /**
     * Returns the number of elements in the FList.
     *
     * @return the number of elements in the FList
     */
    public int size()
    {
        if (isEmpty())
        {
            return 0;
        }
        else
        {
            return 1 + tail.size();
        }
    }

    /**
     * Retrieves the element at the specified index from the FList.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &ge; size())
     */
    public T get(int index)
    {
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0)
        {
            return head;
        }
        else
        {
            return tail.get(index - 1);
        }
    }

    /**
     * Prepends an element to the current FList and returns a new FList instance.
     *
     * @param element the element to be added at the beginning of the FList
     * @return a new FList instance with the provided element as the head
     */
    public FList<T> prepend(T element)
    {
        return new FList<>(element, this);
    }

    /**
     * Appends an element to the current FList and returns a new FList instance.
     *
     * @param element the element to be added to the end of the FList
     * @return a new FList instance with the provided element appended
     */
    public FList<T> append(T element)
    {
        if (isEmpty())
        {
            return prepend(element);
        }
        else
        {
            return new FList<>(head, tail.append(element));
        }

    }

    /**
     * Applies the given function to each element of the list and returns a new FList
     * with the transformed elements.
     *
     * @param mapper the function to apply to each element of the list
     * @param <U>    the type of elements in the resulting FList
     * @return a new FList with the transformed elements
     * @throws NullPointerException if the mapper function is null
     */
    public <U> FList<U> map(Function1<T, U> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        if (isEmpty())
        {
            return FList.empty();
        }
        else
        {
            return tail.map(mapper).prepend(mapper.apply(head));
        }
    }

    /**
     * Filters the elements of the FList based on the provided predicate.
     *
     * @param predicate the predicate to apply to each element of the FList
     * @return a new FList containing only the elements for which the predicate returns true
     * @throws NullPointerException if the predicate is null
     */
    public FList<T> filter(Predicate<T> predicate)
    {
        Objects.requireNonNull(predicate, "predicate is null");
        if (isEmpty())
        {
            return FList.empty();
        }
        else if (predicate.test(head))
        {
            return tail.filter(predicate).prepend(head);
        }
        else
        {
            return tail.filter(predicate);
        }
    }

    /**
     * Updates the element at the specified index in the FList.
     *
     * @param index   the index of the element to update
     * @param element the new element to replace the existing element at the specified index
     * @return a new FList instance with the updated element
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &ge; size())
     */
    public FList<T> update(int index, T element)
    {

        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0)
        {
            return new FList<>(element, tail);
        }
        else
        {
            return new FList<>(head, tail.update(index - 1, element));
        }
    }

    /**
     * Folds the elements of the FList from right to left using the provided identity and accumulator function.
     *
     * @param identity    the initial value for the folding operation
     * @param accumulator the function that combines the current element with the accumulated value
     * @param <R>         the type of the accumulated value
     * @return the accumulated value after folding all the elements of the FList
     * @throws NullPointerException if the accumulator is null
     */
    public <R> R foldRight(R identity, BiFunction<T, R, R> accumulator)
    {
        Objects.requireNonNull(accumulator, "accumulator is null");
        if (isEmpty())
        {
            return identity;
        }
        else
        {
            return accumulator.apply(head, tail.foldRight(identity, accumulator));
        }
    }

    /**
     * Returns a new FList by applying the given mapper function to each element of this FList,
     * and flattening the result.
     *
     * @param mapper the function to apply to each element of this FList
     * @param <U>    the type of elements in the resulting FList
     * @return a new FList with the flattened elements
     * @throws NullPointerException if the mapper function is null
     */
    public <U> FList<U> flatMap(Function1<T, FList<U>> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return foldRight(FList.empty(), (elem, acc) ->
                mapper.apply(elem).appendList(acc));
    }

    /**
     * Appends the elements of the provided list to the current FList.
     *
     * @param other the list to append
     * @return a new FList instance with the elements of the provided list appended
     */
    public FList<T> appendList(FList<T> other)
    {
        if (isEmpty())
        {
            return other;
        }
        else
        {
            return new FList<>(head, tail.appendList(other));
        }
    }

    /**
     * Converts the FList to a Java Collection.
     *
     * @return a Collection containing the elements of the FList
     */
    public Collection<T> toJavaCollection()
    {
        return reverse().foldRight(new ArrayList<>(), (elem, acc) -> {
            acc.add(elem);
            return acc;
        });
    }

    /**
     * Converts the FList to an array.
     *
     * @return an array containing the elements of the FList
     */
    @SuppressWarnings("unchecked")
    public T[] toArray()
    {
        return toJavaCollection().toArray((T[]) new Object[size()]);
    }

    @Override
    public String toString()
    {
        if (isEmpty())
        {
            return "";
        }
        else if (tail.isEmpty())
        {
            return "" + head;
        }
        return head + "::" + tail;
    }

    /**
     * Converts the FList to a NonEmptyList.
     *
     * @return a NonEmptyList containing the elements of the FList.
     */
    public NonEmptyList<T> toNonEmptyList()
    {
        return new NonEmptyList<>(head, tail);
    }

}
