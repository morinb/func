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
