package com.github.morinb.func;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public record FList<T>(T head, com.github.morinb.func.FList<T> tail)
{

    public static <U> FList<U> of(U head)
    {
        return FList.<U>empty().prepend(head);
    }


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

    public static <U> FList<U> of(List<U> elements)
    {
        FList<U> list = FList.empty();
        for (var i = elements.size() - 1; i >= 0; i--)
        {
            list = list.prepend(elements.get(i));
        }
        return list;
    }

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

    public boolean isEmpty()
    {
        return head == null;
    }


    // create a static method to create a non null noop FList instance
    public static <U> FList<U> empty()
    {
        return new FList<>(null, null);
    }

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

    public FList<T> prepend(T element)
    {
        return new FList<>(element, this);
    }

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

    public <U> FList<U> flatMap(Function1<T, FList<U>> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        return foldRight(FList.empty(), (elem, acc) ->
                mapper.apply(elem).appendList(acc));
    }

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

    public Collection<T> toJavaCollection()
    {
        return reverse().foldRight(new ArrayList<>(), (elem, acc) -> {
            acc.add(elem);
            return acc;
        });
    }

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

    public NonEmptyList<T> toNonEmptyList()
    {
        return new NonEmptyList<>(head, tail);
    }

}
