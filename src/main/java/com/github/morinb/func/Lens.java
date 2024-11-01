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

/**
 * The Lens interface provides a structure for accessing and modifying
 * parts of an object. It essentially consists of two operations:
 * 1. Getting a part of an object.
 * 2. Setting a part of an object.
 *
 * @param <S> the type of the object containing the field
 * @param <F> the type of the field
 */
public interface Lens<S, F>
{
    static <A, B, C> Lens<A, C> compose(final Lens<A, B> ab, final Lens<B, C> bc)
    {
        return new Lens<>()
        {
            @Override
            public C get(final A a)
            {
                return bc.get(ab.get(a));
            }

            @Override
            public A set(final C c, final A a)
            {
                return ab.set(bc.set(c, ab.get(a)), a);
            }
        };
    }

    F get(S s);

    S set(F f, S s);
}
