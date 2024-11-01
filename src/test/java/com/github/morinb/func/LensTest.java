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

import static org.junit.jupiter.api.Assertions.assertEquals;

class LensTest
{
    private static final Lens<StringWrapper, String> STRING_WRAPPER_LENS = new Lens<>()
    {
        @Override
        public String get(final StringWrapper stringWrapper)
        {
            return stringWrapper.getValue();
        }

        @Override
        public StringWrapper set(final String value, final StringWrapper stringWrapper)
        {
            stringWrapper.setValue(value);
            return stringWrapper;
        }
    };

    @Test
    void setShouldUpdateValueInGivenObject()
    {
        final var stringWrapper = new StringWrapper("initial");
        STRING_WRAPPER_LENS.set("updated", stringWrapper);
        assertEquals("updated", stringWrapper.getValue());
    }

    @Test
    void setShouldReturnTheSameObject()
    {
        final var stringWrapper = new StringWrapper("initial");
        final var returnedWrapper = STRING_WRAPPER_LENS.set("updated", stringWrapper);
        assertEquals(returnedWrapper, stringWrapper);
    }

    @Test
    void composeShouldReturnComposedLens()
    {
        final var stringWrapper = new StringWrapper("initial");

        final var lengthLens = new Lens<String, Integer>()
        {
            @Override
            public Integer get(final String string)
            {
                return string.length();
            }

            @Override
            public String set(final Integer length, final String string)
            {
                if (string.length() > length)
                {
                    return string.substring(0, length);
                }
                return string;
            }
        };

        final var composedLens = Lens.compose(STRING_WRAPPER_LENS, lengthLens);

        // Test getting the composed value
        assertEquals(Integer.valueOf("initial".length()), composedLens.get(stringWrapper));

        // Test setting the composed value
        composedLens.set(3, stringWrapper);
        assertEquals("ini", stringWrapper.getValue());
    }

    private static class StringWrapper
    {
        private String value;

        public StringWrapper(final String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(final String value)
        {
            this.value = value;
        }
    }

}