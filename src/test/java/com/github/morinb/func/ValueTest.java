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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueTest
{

    @Test
    void narrowShouldReturnSameInstanceWhenValueSubclassType()
    {
        final Value<Integer> value = new MyValue<>(1);
        assertSame(value, Value.narrow(value));
    }

    @Test
    void narrowShouldReturnSameInstanceWhenValueSuperclassType()
    {
        final Value<? extends Integer> numberValue = new MyValue<>(1);
        assertSame(numberValue, Value.narrow(numberValue));
    }

    @Test
    void narrowShouldReturnSameInstanceWhenValueSameType()
    {
        final MyValue<Integer> value = new MyValue<>(1);
        assertSame(value, Value.narrow(value));
    }

    @Test
    void narrowShouldReturnSameInstanceWhenValueIsNull()
    {
        final Value<Object> value = null;
        assertNull(Value.narrow(value));
    }

    @Test
    void containsShouldReturnTrueWhenValueExists()
    {
        final Value<Integer> value = new MyValue<>(1);
        assertTrue(value.contains(1));
    }

    @Test
    void containsShouldReturnFalseWhenValueDoesNotExist()
    {
        final Value<Integer> value = new MyValue<>(1);
        assertFalse(value.contains(2));
    }

    @Test
    void containsShouldReturnFalseWhenValueIsNullAndValueExists()
    {
        final Value<Integer> value = new MyValue<>(null);
        assertFalse(value.contains(1));
    }

    @Test
    void containsShouldReturnTrueWhenValueIsNullAndValueIsNull()
    {
        final Value<Object> value = new MyValue<>(null);
        assertTrue(value.contains(null));
    }

    @Test
    void forAllShouldReturnTrueWhenPredicateMatchesAllValues()
    {
        final Value<Integer> value = new MyValue<>(10);
        assertTrue(value.forAll(e -> e > 0));
    }

    @Test
    void forAllShouldReturnFalseWhenPredicateDoesntMatchesAllValues()
    {
        final Value<Integer> value = new MyValue<>(-10);
        assertFalse(value.forAll(e -> e > 0));
    }

    @Test
    void forAllShouldReturnTrueWhenValueIsNullAndPredicateIsAllTrueForNull()
    {
        final Value<Object> value = new MyValue<>(null);
        assertTrue(value.forAll(Objects::isNull));
    }

    @Test
    void forEachShouldNotDoAnythingIfActionIsNull()
    {
        final Value<Integer> value = new MyValue<>(1);
        assertThrows(NullPointerException.class, () -> value.forEach(null));
    }

    @Test
    void forEachShouldProperlyApplyTheAction()
    {
        final Value<Integer> value = new MyValue<>(1);
        final Integer[] sum = {0};

        value.forEach(i -> sum[0] += i);

        assertEquals(1, sum[0]);
    }

    @Test
    void getOrElseTryShouldReturnGetReturnValueWhenNotEmpty()
    {
        final Value<Integer> value = new MyValue<>(1);
        assertEquals(1, value.getOrElseTry(() -> 2));
    }

    @Test
    void getOrElseTryShouldReturnSupplierValueWhenEmpty()
    {
        final Value<Integer> value = new MyValue<>(null);
        assertEquals(Integer.valueOf(2), value.getOrElseTry(() -> 2));
    }

    @Test
    void getOrElseTryShouldThrowExceptionWhenSupplierThrows()
    {
        final Value<Integer> value = new MyValue<>(null);
        assertThrows(RuntimeException.class, () -> value.getOrElseTry(() -> {
            throw new RuntimeException();
        }));
    }

    @Test //Test1: getOrNull should return null when the Value is noop
    void getOrNullShouldReturnNullWhenValueIsEmpty()
    {
        final Value<Integer> value = new MyValue<>(null);
        assertNull(value.getOrNull());
    }

    @Test //Test2: getOrNull should return value when the Value is not noop
    void getOrNullShouldReturnValueWhenValueIsNotEmpty()
    {
        final Value<Integer> value = new MyValue<>(10);
        assertEquals(Integer.valueOf(10), value.getOrNull());
    }

    @Test
    void testToEitherWithEitherRight()
    {
        final Value<String> value = Either.right("Hello");
        final Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isRight());
        Assertions.assertEquals("Hello", either.get());
    }

    @Test
    void testToEitherWithEitherLeft()
    {
        final Value<String> value = Either.left("error");
        final Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isLeft());
        Assertions.assertEquals(10, either.getLeft());
    }

    @Test
    void testToEitherWithExistingValue()
    {
        final Value<String> value = new MyValue<>("Hello");
        final Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isRight());
        Assertions.assertEquals("Hello", either.get());
    }

    @Test
    void testToEitherWithEmptyValue()
    {
        final Value<String> value = new MyValue<>(null);
        final Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isLeft());
        Assertions.assertEquals(10, either.getLeft());
    }

    @Test
    void testToTryWithExistingValue()
    {
        final Value<String> value = new MyValue<>("Hello");
        final Try<String> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Hello", result.get());
    }

    @Test
    void testToTryWithEmptyValue()
    {
        final Value<Supplier<String>> value = new MyValue<>(() -> {
            throw new RuntimeException();
        });
        final Try<Supplier<String>> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());

    }

    @Test
    void testToTryWithSuccess()
    {
        final Value<String> value = Try.of(() -> "Hello");
        final Try<String> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Hello", result.get());
    }

    @Test
    void testToTryWithFailure()
    {
        final Value<Supplier<String>> value = Try.of(() -> {
            throw new RuntimeException();
        });
        final Try<Supplier<String>> result = value.toTry();
        Assertions.assertTrue(result.isFailure());

    }

    static class MyValue<T> implements Value<T>
    {

        private final T value;

        MyValue(final T value)
        {
            this.value = value;
        }

        @Override
        public T get()
        {
            return value;
        }

        @Override
        public boolean isEmpty()
        {
            return value == null;
        }

        @Override
        public <U> Value<U> map(final Function1<? super T, ? extends U> mapper)
        {
            return new MyValue<>(mapper.apply(value));
        }

        @Override
        public Iterator<T> iterator()
        {
            return java.util.Collections.singleton(value).iterator();
        }
    }
}
