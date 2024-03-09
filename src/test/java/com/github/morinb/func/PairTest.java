package com.github.morinb.func;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PairTest
{

    @Test
    void testGetFirst()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals("John", pair.first());
    }

    @Test
    void testGetSecond()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals(20, pair.second());
    }

    @Test
    void testEquals()
    {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 20);
        Assertions.assertEquals(pair1, pair2);

        var pair3 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1, pair3);

        Assertions.assertNotEquals(null, pair1);
        Assertions.assertNotEquals("Another class", pair1);
    }

    @Test
    void testHashCode()
    {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 20);
        Assertions.assertEquals(pair1.hashCode(), pair2.hashCode());

        var pair3 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1.hashCode(), pair3.hashCode());

        EqualsVerifier.forClass(Pair.class).verify();
    }


    @Test
    void testHashCodeWithNulls()
    {
        var pairWithNulls = new Pair<>(null, null);
        var anotherPairWithNulls = new Pair<>(null, null);
        Assertions.assertEquals(pairWithNulls.hashCode(), anotherPairWithNulls.hashCode());

        var pairWithoutNulls = new Pair<>("John", 20);
        Assertions.assertNotEquals(pairWithNulls.hashCode(), pairWithoutNulls.hashCode());
    }

    @Test
    void testToString()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals("Pair{first=John, second=20}", pair.toString());
    }

    @Test
    void testEqualsToDifferentClassObject()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals("Some String", pair);
    }

    @Test
    void testEqualsToDifferentPairWithDifferentFirst()
    {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("Doe", 20);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    void testEqualsToDifferentPairWithDifferentSecond()
    {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    void testEqualsToSamePair()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals(pair, pair);
    }

    @Test
    void testEqualsToNull()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, null);
    }

    @Test
    void testEqualsToOtherClass()
    {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, new Object());
    }
}
