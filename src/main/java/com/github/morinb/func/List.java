package com.github.morinb.func;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A generic immutable list implementation.
 *
 * @param <T> the type of elements in the list
 */
public final class List<T>
{
    /**
     * The innerList variable represents a private List of elements of type T.
     * It is used internally within the class to store and manipulate elements.
     *
     * <p>This variable should not be accessed directly from outside the class.
     * Instead, appropriate methods should be used to interact with this List.
     *</p>
     *
     * @param <T> the type of elements stored in the innerList
     * @see java.util.List
     */
    private java.util.List<T> innerList;

    /**
     * Private constructor that initializes the innerList with the provided list.
     *
     * @param list the list to be used to initialize the innerList
     * @param <T> the type of elements in the list
     */
    private List(java.util.List<T> list)
    {
        innerList = new LinkedList<>(list);
    }

    /**
     * Applies the given function to each element in the list and returns a new list containing the results.
     *
     * @param <R>      the type of elements in the resulting list
     * @param function the function to apply
     * @return a new list containing the results of applying the function
     */
    public <R> List<R> map(Function<? super T, ? extends R> function)
    {
        java.util.List<R> newList = new LinkedList<>();
        for (var element : innerList)
        {
            newList.add(function.apply(element));
        }
        return new List<>(newList);
    }

    /**
     * Applies the given function to each element in the list and flattens the resulting list.
     *
     * @param function the function to apply to each element
     * @param <R> the type of elements in the resulting list
     * @return a new list containing the flattened elements
     */
    public <R> List<R> flatMap(Function<? super T, ? extends List<? extends R>> function)
    {
        java.util.List<R> newList = new LinkedList<>();
        for (var element : innerList)
        {
            newList.addAll(function.apply(element).toJavaList());
        }
        return new List<>(newList);
    }


    /**
     * Creates an immutable List from the given values.
     *
     * @param <R>     the type of the values
     * @param values  the values to be added to the List
     * @return an immutable List containing the specified values
     */
    public static <R> List<R> of(R... values)
    {
        final var list = new LinkedList<R>();
        Collections.addAll(list, values);
        return new List<>(list);
    }

    /**
     * Creates a new instance of a List based on the given values.
     *
     * @param values The list of values to be added to the new List.
     * @param <R> The type of elements in the List.
     * @return A new List containing the given values.
     */
    public static <R> List<R> of(java.util.List<R> values)
    {
        final var list = new LinkedList<R>(values);
        return new List<>(list);
    }

    /**
     * Checks if the inner list is empty.
     *
     * @return {@code true} if the inner list is empty, {@code false} otherwise.
     */
    public boolean isEmpty()
    {
        return innerList.isEmpty();
    }

    /**
     * Retrieves the size of the inner list.
     *
     * @return The number of elements in the inner list as an integer.
     */
    public int size()
    {
        return innerList.size();
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param o the element to check if it is present in the list
     * @return true if the list contains the specified element, false otherwise
     */
    public boolean contains(Object o)
    {
        return innerList.contains(o);
    }

    /**
     * Checks if this list contains all elements in the specified collection.
     *
     * @param c the collection whose elements are to be checked for containment in this list
     * @return true if this list contains all of the elements in the specified collection, false otherwise
     */
    public boolean containsAll(List<?> c)
    {
        return innerList.containsAll(c.toJavaList());
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
     *
     * @param o the object to be searched for
     * @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
     */
    public int indexOf(Object o)
    {
        return innerList.indexOf(o);
    }

    /**
     * Retrieves the element at the specified index in the list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    public T get(int index)
    {
        return innerList.get(index);
    }

    /**
     * Adds the given value to the list.
     *
     * @param value the value to be added to the list
     * @return a new List instance with the added value
     */
    public List<T> add(T value)
    {
        java.util.List<T> newList = new LinkedList<>(this.innerList);
        newList.add(value);
        return new List<>(newList);
    }

    /**
     * Converts the inner list to a Java List.
     *
     * @return A new Java List containing the elements of the inner list.
     */
    public java.util.List<T> toJavaList()
    {
        return new LinkedList<>(innerList);
    }

    /**
     * Adds all the values from the input list to the current list.
     *
     * @param values the list containing values to be added
     * @return a new list with the values added
     */
    public List<T> addAll(List<T> values)
    {
        java.util.List<T> newList = new LinkedList<>(this.innerList);
        newList.addAll(values.toJavaList());
        return new List<>(newList);
    }

    /**
     * Filters the elements of the list based on the provided predicate.
     *
     * @param predicate the predicate used to filter the elements
     * @return a new List containing the filtered elements
     */
    public List<T> filter(Predicate<? super T> predicate) {
        var newList = innerList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new List<>(newList);
    }
}
