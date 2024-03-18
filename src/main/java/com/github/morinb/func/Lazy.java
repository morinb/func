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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> implements Value<T>, Supplier<T>, Serializable
{
    private transient Supplier<? extends T> supplier;
    private T value;

    private Lazy(Supplier<? extends T> supplier)
    {
        this.supplier = supplier;
    }

    @SuppressWarnings("unchecked")
    public static <T> Lazy<T> of(Supplier<? extends T> supplier)
    {
        Objects.requireNonNull(supplier, "supplier is null");
        if (supplier instanceof Lazy)
        {
            return (Lazy<T>) supplier;
        }
        else
        {
            return new Lazy<>(supplier);
        }
    }

    @Override
    public T get()
    {
        return (supplier == null) ? value : computeValue();
    }

    private synchronized T computeValue()
    {
        final Supplier<? extends T> supp = supplier;
        if (supp != null)
        {
            value = supp.get();
            supplier = null;
        }
        return value;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    public boolean isEvaluated()
    {
        return (supplier == null);
    }

    @Override
    public <U> Value<U> map(Function1<? super T, ? extends U> mapper)
    {
        return Lazy.of(() -> mapper.apply(get()));
    }

    @Override
    public Iterator<T> iterator()
    {
        return Collections.singleton(get()).iterator();
    }

    @Override
    public boolean equals(Object o)
    {
     return (o == this) || (o instanceof Lazy && Objects.equals(((Lazy<?>) o).get(), get()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(get());
    }

    @Override
    public String toString()
    {
        return "Lazy(" + (isEvaluated() ? value : "?") + ")";
    }

    /**
     * Writes the object represented by this Lazy to an ObjectOutputStream.
     *
     * @param objectOutputStream The ObjectOutputStream to write the object to.
     * @throws IOException If an I/O error occurs while writing to the ObjectOutputStream.
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException
    {
        get();
        objectOutputStream.defaultWriteObject();
    }
}
