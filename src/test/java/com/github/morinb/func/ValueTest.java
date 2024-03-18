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

import static org.junit.jupiter.api.Assertions.*;

public class ValueTest {

    static class MyValue<T> implements Value<T> {

        private final T value;

        MyValue(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isEmpty() {
            return value == null;
        }

        @Override
        public <U> Value<U> map(Function1<? super T, ? extends U> mapper) {
            return new MyValue<>(mapper.apply(value));
        }

        @Override
        public Iterator<T> iterator() {
            return java.util.Collections.singleton(value).iterator();
        }
    }

    @Test
    public void narrowShouldReturnSameInstanceWhenValueSubclassType() {
        Value<Integer> value = new MyValue<>(1);
        assertSame(value, Value.narrow(value));
    }

    @Test
    public void narrowShouldReturnSameInstanceWhenValueSuperclassType() {
        Value<? extends Integer> numberValue = new MyValue<>(1);
        assertSame(numberValue, Value.narrow(numberValue));
    }

    @Test
    public void narrowShouldReturnSameInstanceWhenValueSameType() {
        MyValue<Integer> value = new MyValue<>(1);
        assertSame(value, Value.narrow(value));
    }

    @Test
    public void narrowShouldReturnSameInstanceWhenValueIsNull() {
        Value<Object> value = null;
        assertNull(Value.narrow(value));
    }

    @Test
    public void containsShouldReturnTrueWhenValueExists() {
        Value<Integer> value = new MyValue<>(1);
        assertTrue(value.contains(1));
    }

    @Test
    public void containsShouldReturnFalseWhenValueDoesNotExist() {
        Value<Integer> value = new MyValue<>(1);
        assertFalse(value.contains(2));
    }

    @Test
    public void containsShouldReturnFalseWhenValueIsNullAndValueExists() {
        Value<Integer> value = new MyValue<>(null);
        assertFalse(value.contains(1));
    }

    @Test
    public void containsShouldReturnTrueWhenValueIsNullAndValueIsNull() {
        Value<Object> value = new MyValue<>(null);
        assertTrue(value.contains(null));
    }

    @Test
    public void forAllShouldReturnTrueWhenPredicateMatchesAllValues() {
        Value<Integer> value = new MyValue<>(10);
        assertTrue(value.forAll(e -> e > 0));
    }

    @Test
    public void forAllShouldReturnFalseWhenPredicateDoesntMatchesAllValues() {
        Value<Integer> value = new MyValue<>(-10);
        assertFalse(value.forAll(e -> e > 0));
    }

    @Test
    public void forAllShouldReturnTrueWhenValueIsNullAndPredicateIsAllTrueForNull() {
        Value<Object> value = new MyValue<>(null);
        assertTrue(value.forAll(Objects::isNull));
    }

    @Test
    public void forEachShouldNotDoAnythingIfActionIsNull() {
        Value<Integer> value = new MyValue<>(1);
        assertThrows(NullPointerException.class, () -> value.forEach(null));
    }

    @Test
    public void forEachShouldProperlyApplyTheAction() {
        Value<Integer> value = new MyValue<>(1);
        Integer[] sum = {0};

        value.forEach(i -> sum[0] += i);

        assertEquals(1, sum[0]);
    }

    @Test
    public void getOrElseTryShouldReturnGetReturnValueWhenNotEmpty() {
        Value<Integer> value = new MyValue<>(1);
        assertEquals(1, value.getOrElseTry(() -> 2));
    }

    @Test
    public void getOrElseTryShouldReturnSupplierValueWhenEmpty() {
        Value<Integer> value = new MyValue<>(null);
        assertEquals(Integer.valueOf(2), value.getOrElseTry(() -> 2));
    }

    @Test
    public void getOrElseTryShouldThrowExceptionWhenSupplierThrows() {
        Value<Integer> value = new MyValue<>(null);
        assertThrows(RuntimeException.class, () -> value.getOrElseTry(() -> {
            throw new RuntimeException();
        }));
    }

    @Test //Test1: getOrNull should return null when the Value is noop
    public void getOrNullShouldReturnNullWhenValueIsEmpty() {
        Value<Integer> value = new MyValue<>(null);
        assertNull(value.getOrNull());
    }

    @Test //Test2: getOrNull should return value when the Value is not noop
    public void getOrNullShouldReturnValueWhenValueIsNotEmpty() {
        Value<Integer> value = new MyValue<>(10);
        assertEquals(Integer.valueOf(10), value.getOrNull());
    }

    @Test
    public void testToEitherWithEitherRight() {
        Value<String> value = Either.right("Hello");
        Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isRight());
        Assertions.assertEquals("Hello", either.get());
    }

    @Test
    public void testToEitherWithEitherLeft() {
        Value<String> value = Either.left("error");
        Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isLeft());
        Assertions.assertEquals(10, either.getLeft());
    }

    @Test
    public void testToEitherWithExistingValue() {
        Value<String> value = new MyValue<>("Hello");
        Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isRight());
        Assertions.assertEquals("Hello", either.get());
    }

    @Test
    public void testToEitherWithEmptyValue() {
        Value<String> value = new MyValue<>(null);
        Either<Integer, String> either = value.toEither(10);
        Assertions.assertTrue(either.isLeft());
        Assertions.assertEquals(10, either.getLeft());
    }

    @Test
    public void testToTryWithExistingValue() {
        Value<String> value = new MyValue<>("Hello");
        Try<String> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Hello", result.get());
    }

    @Test
    public void testToTryWithEmptyValue() {
        Value<Supplier<String>> value = new MyValue<>(() -> {
            throw new RuntimeException();
        });
        Try<Supplier<String>> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());

    }    @Test
    public void testToTryWithSuccess() {
        Value<String> value = Try.of(() -> "Hello");
        Try<String> result = value.toTry();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals("Hello", result.get());
    }

    @Test
    public void testToTryWithFailure() {
        Value<Supplier<String>> value = Try.of(() -> {
            throw new RuntimeException();
        });
        Try<Supplier<String>> result = value.toTry();
        Assertions.assertTrue(result.isFailure());

    }
}
