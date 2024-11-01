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

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LazyTest
{

    /**
     * Tests for the Lazy class and its `of` method.
     * The Lazy class is a hybrid implementation of functional-style "lazy evaluation"
     * and Java's Supplier interface. The `of` method is used to create a new instance
     * of the Lazy class.
     */

    @Test
    void testOf_withNormalSupplier()
    {
        final Supplier<String> supplier = () -> "test";
        final var lazy = Lazy.of(supplier);

        assertFalse(lazy.isEvaluated());
        assertEquals("test", lazy.get());
        assertTrue(lazy.isEvaluated());
    }

    @Test
    void testOf_withLazySupplier()
    {
        final var lazy = Lazy.of(() -> "test");
        final var lazy2 = Lazy.of(lazy);

        assertFalse(lazy.isEvaluated());
        assertEquals("test", lazy.get());
        assertTrue(lazy.isEvaluated());

        assertSame(lazy, lazy2);
    }

    @Test
    void testOf_withNullSupplier()
    {
        assertThrows(NullPointerException.class, () -> Lazy.of(null));
    }

    @Test
    void lazyIsNeverEmpty()
    {
        assertFalse(Lazy.of(() -> 1).isEmpty());
    }

    @Test
    void lazyIsIterable()
    {
        final var iterator = Lazy.of(() -> 1).iterator();
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void lazyMapIsLazy()
    {
        final var lazy = Lazy.of(() -> 64);
        final var expected = Lazy.of(() -> 16);

        assertEquals(expected, lazy.map(i -> i / 4));
    }

    @Test
    void lazyShouldHaveHashCode()
    {
        assertEquals(Lazy.of(() -> 1).hashCode(), Objects.hashCode(1));
    }

    @Test
    void lazyNonEvaluatedHasToString()
    {
        assertEquals("Lazy(?)", Lazy.of(() -> 1).toString());
    }
    @Test
    void lazyEvaluatedHasToString()
    {
        final var lazyValue = Lazy.of(() -> 1);
        lazyValue.get();
        assertEquals("Lazy(1)", lazyValue.toString());
    }

    @Test
    void lazyIsSerializable()
    {
        final var actual = Serializers.deserialize(Serializers.serialize(Lazy.of(() -> 1)));
        final Object expected = Lazy.of(() -> 1);

        assertEquals(expected, actual);

    }

}