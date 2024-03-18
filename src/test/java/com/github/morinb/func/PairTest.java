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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PairTest
{

    @Test
    void testGetFirst()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertEquals("John", pair.first());
    }

    @Test
    void testGetSecond()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertEquals(20, pair.second());
    }

    @Test
    void testEqualsAndHashCode()
    {
        EqualsVerifier.forClass(Pair.class).verify();
    }


    @Test
    void testHashCodeWithNulls()
    {
        final var pairWithNulls = new Pair<>(null, null);
        final var anotherPairWithNulls = new Pair<>(null, null);
        Assertions.assertEquals(pairWithNulls.hashCode(), anotherPairWithNulls.hashCode());

        final var pairWithoutNulls = new Pair<>("John", 20);
        Assertions.assertNotEquals(pairWithNulls.hashCode(), pairWithoutNulls.hashCode());
    }

    @Test
    void testToString()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertEquals("Pair{first=John, second=20}", pair.toString());
    }

    @Test
    void testEqualsToDifferentClassObject()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals("Some String", pair);
    }

    @Test
    void testEqualsToDifferentPairWithDifferentFirst()
    {
        final var pair1 = new Pair<>("John", 20);
        final var pair2 = new Pair<>("Doe", 20);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    void testEqualsToDifferentPairWithDifferentSecond()
    {
        final var pair1 = new Pair<>("John", 20);
        final var pair2 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    void testEqualsToSamePair()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertEquals(pair, pair);
    }

    @SuppressWarnings("squid:S3415")
    @Test
    void testEqualsToNull()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, null);
    }

    @SuppressWarnings("squid:S3415")
    @Test
    void testEqualsToOtherClass()
    {
        final var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, new Object());
    }
}
