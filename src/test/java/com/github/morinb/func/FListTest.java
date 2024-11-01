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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FListTest
{

    @Test
    void isEmptyTest_whenListContainsNoElements_shouldReturnTrue()
    {
        final FList<Integer> list = FList.empty();
        assertTrue(list.isEmpty());
    }

    @Test
    void isEmptyTest_whenListContainsSomeElements_shouldReturnFalse()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1);
        assertFalse(list.isEmpty());
    }

    @Test
    void sizeTest_whenListContainsSeveralElements_shouldReturnCorrectSize()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2).append(3);
        assertEquals(3, list.size());
    }

    @Test
    void getTest_whenGettingElementByIndex_shouldReturnCorrectElement()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2).append(3);
        assertEquals(2, list.get(1));
    }

    @Test
    void prependTest_whenPrependingElement_shouldReturnCorrectHead()
    {
        FList<Integer> list = FList.empty();
        list = list.prepend(1);
        assertEquals(1, list.head());
    }

    @Test
    void appendTest_whenAppendingElement_shouldReturnCorrectSize()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1);
        assertEquals(1, list.size());
    }

    @Test
    void mapTest_whenMappingList_shouldReturnCorrectlyMappedList()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2).map(i -> i * 2);
        assertEquals(2, list.get(0));
        assertEquals(4, list.get(1));
    }

    @Test
    void filterTest_whenFilteringList_shouldReturnCorrectlyFilteredList()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2).filter(i -> i % 2 == 0);
        assertEquals(1, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    void updateTest_whenUpdatingElementAtCertainIndex_shouldReturnCorrectlyUpdatedList()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2);
        list = list.update(0, 100);
        assertEquals(100, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void updateTest_whenUpdatingElementAtCertainIndex_shouldReturnCorrectlyUpdatedList2()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2);
        list = list.update(1, 100);
        assertEquals(1, list.get(0));
        assertEquals(100, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void getTest_whenGettingElementFromEmptyList_shouldThrowException()
    {
        final FList<Integer> list = FList.empty();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void getTest_whenGettingElementFromNonEmptyList_shouldReturnCorrectElement()
    {
        final var list = FList.<Integer>empty().append(1).append(2);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void getTest_whenGettingElementByInvalidIndex_shouldThrowException()
    {
        final var list = FList.<Integer>empty().append(1).append(2);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }


    @Test
    void updateTest_whenUpdatingElementAtInvalidIndex_shouldThrowException()
    {
        final var list = FList.<Integer>empty().append(1).append(2);

        assertThrows(IndexOutOfBoundsException.class, () -> list.update(2, 100));
    }

    @Test
    void updateTest_whenUpdatingElementAtInvalidIndex_shouldThrowException2()
    {
        final var list = FList.<Integer>empty().append(1).append(2);

        assertThrows(IndexOutOfBoundsException.class, () -> list.update(-1, 100));
    }


    @Test
    void foldRightTest_whenFoldingListUsingAddition_shouldReturnCorrectSum()
    {
        FList<Integer> list = FList.empty();
        list = list.append(1).append(2);
        final int sum = list.foldRight(0, Integer::sum);
        assertEquals(3, sum);
    }

    @Test
    void flatMapTest_whenFlatteningListofList_shouldReturnFlattenedList()
    {
        FList<FList<Integer>> list = FList.empty();
        final var sublist1 = FList.<Integer>empty().append(1).append(2);
        final var subList2 = FList.<Integer>empty().append(3).append(4);
        list = list.append(sublist1).append(subList2);
        final var flatList = list.flatMap(i -> i);
        assertEquals(4, flatList.size());
        assertEquals(1, flatList.get(0));
        assertEquals(2, flatList.get(1));
        assertEquals(3, flatList.get(2));
        assertEquals(4, flatList.get(3));
    }

    @Test
    void appendListTest_whenAppendingTwoLists_shouldReturnCorrectlyAppendedList()
    {
        final var firstList = FList.<Integer>empty().append(1).append(2);
        final var secondList = FList.<Integer>empty().append(3).append(4);
        final var concatenatedList = firstList.appendList(secondList);
        assertEquals(4, concatenatedList.size());
        assertEquals(1, concatenatedList.get(0));
        assertEquals(2, concatenatedList.get(1));
        assertEquals(3, concatenatedList.get(2));
        assertEquals(4, concatenatedList.get(3));
    }

    @Test
    void ofTest_whenCreatingListWithOf_shouldReturnCorrectList()
    {
        final var list = FList.of(1);
        assertEquals(1, list.get(0));
    }

    @Test
    void ofTest_whenCreatingListWithMultipleOf_shouldReturnCorrectList()
    {
        final var list = FList.of(1, 2);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void ofTest_whenCreatingListFromSingleElementArray_shouldReturnCorrectList() {
        final Integer[] numbers = {1};
        final var list = FList.of(numbers);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0));
    }

    @Test
    void ofTest_whenCreatingListFromMultiElementArray_shouldReturnCorrectList() {
        final Integer[] numbers = {1, 2, 3};
        final var list = FList.of(numbers);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void reverseTest_whenReversingList_shouldReturnCorrectlyReversedList() {
        var list = FList.<Integer>empty().append(1).append(2).append(3);
        list = list.reverse();
        assertEquals(3, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(2));
    }

    @Test
    void reverseTest_whenReversingSingleElementList_shouldReturnSameList() {
        var list = FList.<Integer>empty().append(1);
        list = list.reverse();
        assertEquals(1, list.get(0));
    }

    @Test
    void reverseTest_whenReversingEmptyList_shouldReturnEmptyList() {
        FList<Integer> list = FList.empty();
        list = list.reverse();
        assertTrue(list.isEmpty());
    }
    @Test
    void toStringTest_whenEmptyList_shouldReturnEmptyString() {
        final FList<Integer> list = FList.empty();
        assertEquals("", list.toString());
    }

    @Test
    void toStringTest_whenSingleElementList_shouldReturnElementString() {
        final var list = FList.of(1);
        assertEquals("1", list.toString());
    }

    @Test
    void toStringTest_whenMultiElementList_shouldReturnCorrectString() {
        final var list = FList.of(1, 2, 3);
        assertEquals("1::2::3", list.toString());
    }
    @Test
    void toNonEmptyListTest_whenFListContainsElements_shouldReturnNonEmptyList() {
        final var fList = FList.of(1, 2, 3);
        final var list = fList.toNonEmptyList();
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void toNonEmptyListTest_whenFListIsEmpty_shouldThrowException() {
        final FList<Integer> fList = FList.empty();
        assertThrows(NullPointerException.class, fList::toNonEmptyList);
    }
}
