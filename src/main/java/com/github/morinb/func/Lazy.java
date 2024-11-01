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
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Lazy is a class that represents a lazily evaluated value. It provides a way to defer the computation of a value until it is actually needed.
 * It implements the Value interface, the Supplier interface, and is Serializable.
 *
 * @param <T> the type of the value
 */
public final class Lazy<T> implements Value<T>, Supplier<T>, Serializable
{
    private transient Supplier<? extends T> supplier;
    /**
     * This private variable represents the value of the Lazy object.
     * The value is of type T, which is the type parameter of the Lazy class.
     * <p>
     * The value is lazily evaluated, meaning it is computed and cached only when it is actually needed.
     * If the Lazy object was initialized with a supplier, the value will be computed using the supplier and stored in this variable.
     * If the Lazy object already has a computed value, it will be returned immediately without recomputation.
     * <p>
     * To compute the value, the `computeValue()` method is used, which checks if the value has already been computed.
     * If the value is not yet computed, the supplier provided during instantiation is used to compute the value and store it in this variable.
     * Once the value is computed, the supplier is set to null to indicate that the value has been computed.
     */
    private T value;

    /**
     * Lazy is a class that represents a lazily evaluated value. It provides a way to defer the computation of a value until it is actually needed.
     * It implements the Value interface, the Supplier interface, and is Serializable.
     */
    private Lazy(final Supplier<? extends T> supplier)
    {
        this.supplier = supplier;
    }

    /**
     * Creates a lazily evaluated value using the given supplier.
     *
     * @param supplier the supplier that provides the value
     * @param <T>      the type of the value
     * @return a Lazy instance representing the lazily evaluated value
     * @throws NullPointerException if the supplier is null
     */
    @SuppressWarnings("unchecked")
    public static <T> Lazy<T> of(final Supplier<? extends T> supplier)
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

    /**
     * Retrieves the value of the Lazy object.
     * If the Lazy object was initialized with a supplier, the value is computed lazily and cached.
     * If the Lazy object already has a computed value, it is returned immediately.
     *
     * @return the value of the Lazy object
     */
    @Override
    public T get()
    {
        return (supplier == null) ? value : computeValue();
    }

    /**
     * Computes and returns the value of the Lazy instance. If the value has not been computed yet, it will be computed using the supplier provided during instantiation.
     * Once the value is computed, it will be stored in the Lazy instance and the supplier will be set to null to indicate that the value has been computed.
     *
     * @return the computed value
     */
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

    /**
     * Returns whether the Lazy instance is empty.
     *
     * @return {@code false} since Lazy instances are never empty
     */
    @Override
    public boolean isEmpty()
    {
        return false;
    }

    /**
     * Checks if the Lazy object has been evaluated.
     *
     * @return true if the Lazy object has been evaluated, false otherwise.
     */
    public boolean isEvaluated()
    {
        return (supplier == null);
    }

    /**
     * Applies the given mapper function to the value of this Lazy and returns a new Lazy that represents
     * the result of the mapping operation.
     *
     * @param <U>    the type of the resulting Lazy value
     * @param mapper the function to apply to the value of this Lazy
     * @return a new Lazy representing the result of the mapping operation
     */
    @Override
    public <U> Value<U> map(final Function1<? super T, ? extends U> mapper)
    {
        return Lazy.of(() -> mapper.apply(get()));
    }

    /**
     * Returns an iterator over the lazily evaluated value.
     *
     * @return an iterator over the lazily evaluated value
     */
    @Override
    public Iterator<T> iterator()
    {
        return Collections.singleton(get()).iterator();
    }

    /**
     * Compares this Lazy object with the specified object for equality. Returns true if and only if the specified object is also a Lazy object, both objects have the same value (
     *result of invoking the `get` method), and both objects are either both evaluated or both unevaluated.
     *
     * @param o the object to be compared for equality with this Lazy object
     * @return true if the specified object is equal to this Lazy object, false otherwise
     */
    @Override
    public boolean equals(final Object o)
    {
     return (o == this) || (o instanceof Lazy && Objects.equals(((Lazy<?>) o).get(), get()));
    }

    /**
     * Computes the hash code for this Lazy instance.
     *
     * @return the hash code value for this Lazy instance
     */
    @Override
    public int hashCode()
    {
        return Objects.hashCode(get());
    }

    /**
     * Returns a string representation of the Lazy instance. If the value has been evaluated,
     * it returns "Lazy(value)", otherwise it returns "Lazy(?)".
     *
     * @return a string representation of the Lazy instance
     */
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
    @Serial
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException
    {
        get();
        objectOutputStream.defaultWriteObject();
    }
}
