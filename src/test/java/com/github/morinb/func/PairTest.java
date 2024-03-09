package com.github.morinb.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PairTest {

    @Test
    public void testGetFirst() {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals("John", pair.getFirst());
    }

    @Test
    public void testGetSecond() {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals(20, pair.getSecond());
    }

    @Test
    public void testEquals() {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 20);
        Assertions.assertEquals(pair1, pair2);

        var pair3 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1, pair3);

        Assertions.assertNotEquals(null, pair1);
        Assertions.assertNotEquals("Another class", pair1);
    }

    @Test
    public void testHashCode() {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 20);
        Assertions.assertEquals(pair1.hashCode(), pair2.hashCode());

        var pair3 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    public void testHashCodeWithNulls() {
        var pairWithNulls = new Pair<>(null, null);
        var anotherPairWithNulls = new Pair<>(null, null);
        Assertions.assertEquals(pairWithNulls.hashCode(), anotherPairWithNulls.hashCode());

        var pairWithoutNulls = new Pair<>("John", 20);
        Assertions.assertNotEquals(pairWithNulls.hashCode(), pairWithoutNulls.hashCode());
    }

    @Test
    public void testToString() {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals("Pair{first=John, second=20}", pair.toString());
    }
    @Test
    public void testEqualsToDifferentClassObject() {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals("Some String", pair);
    }

    @Test
    public void testEqualsToDifferentPairWithDifferentFirst() {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("Doe", 20);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    public void testEqualsToDifferentPairWithDifferentSecond() {
        var pair1 = new Pair<>("John", 20);
        var pair2 = new Pair<>("John", 30);
        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    public void testEqualsToSamePair() {
        var pair = new Pair<>("John", 20);
        Assertions.assertEquals(pair, pair);
    }

    @Test
    public void testEqualsToNull() {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, null);
    }
    @Test
    public void testEqualsToOtherClass() {
        var pair = new Pair<>("John", 20);
        Assertions.assertNotEquals(pair, new Object());
    }
}
