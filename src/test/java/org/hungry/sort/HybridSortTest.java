package org.hungry.sort;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HybridSortTest {

    @Test
    void sanityCheck() {
        assertTrue(Boolean.TRUE);
    }

    @Nested
    class SortTest {

        private static Stream<Arguments> createElements() {
            return Stream.of(
                    Arguments.of(Named.of("Same input as output", List.of(1, 2)), List.of(1, 2)),
                    Arguments.of(Named.of("Same as input", List.of(2, 2)), List.of(2, 2)),
                    Arguments.of(Named.of("Third is equal to largest of first 2", List.of(2, 1, 2)), List.of(1, 2, 2)),
                    Arguments.of(Named.of("Third is greater than first 2", List.of(2, 1, 3)), List.of(1, 2, 3)),
                    Arguments.of(Named.of("Third is less than first 2, initiates second list",
                            List.of(3, 2, 2, 1)), List.of(1, 2, 2, 3)),
                    Arguments.of(Named.of("Should cover all rules",
                                    List.of(5, 4, 1, 3, 2, 6, 23, 22, 0, 21, 19, 22)),
                            List.of(0, 1, 2, 3, 4, 5, 6, 19, 21, 22, 22, 23)));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("createElements")
        void should_ReturnList_WhenList_HasElements(List<Integer> given, List<Integer> expected){
            // When
            List<Integer> actual = HybridSort.sort(given);

            // Then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SplitTest {

        private static Stream<Arguments> createElements() {
            return Stream.of(
                    Arguments.of(Named.of("Null input returns empty list", null), new ArrayList<>()),
                    Arguments.of(Named.of("Empty input returns empty list",
                            new ArrayList<>()), new ArrayList<>()),
                    Arguments.of(Named.of("Single record returns list with single list",
                            List.of(1)), List.of(List.of(1))),

                    Arguments.of(Named.of("Same input as output", List.of(1, 2)), List.of(List.of(1, 2))),
                    Arguments.of(Named.of("Reverse input", List.of(2, 1)), List.of(List.of(1, 2))),
                    Arguments.of(Named.of("Same as input", List.of(2, 2)), List.of(List.of(2, 2))),
                    Arguments.of(Named.of("Third is equal to smallest of first 2, initiates second list",
                            List.of(2, 1, 1)), List.of(List.of(1, 2), List.of(1))),
                    Arguments.of(Named.of("Third is equal to largest of first 2", List.of(2, 1, 2)), List.of(List.of(1, 2, 2))),
                    Arguments.of(Named.of("Third is greater than first 2", List.of(2, 1, 3)), List.of(List.of(1, 2, 3))),
                    Arguments.of(Named.of("Third is less than first 2, initiates second list",
                            List.of(3, 2, 1)), List.of(List.of(2, 3), List.of(1))),
                    Arguments.of(Named.of("Third is less than first 2, initiates second list",
                            List.of(3, 2, 2, 1)), List.of(List.of(2, 3), List.of(1, 2))),
                    Arguments.of(Named.of("Should cover all rules",
                                    List.of(5, 4, 1, 3, 2, 6, 23, 22, 0, 21, 19, 22)),
                            List.of(List.of(4, 5), List.of(1, 3), List.of(2, 6, 23), List.of(0, 22), List.of(19, 21, 22))));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("createElements")
        void should_ReturnList_WhenList_HasElements(List<Integer> given, List<List<Integer>> expected){
            // When
            List<List<Integer>> actual = HybridSort.splitByInsertionSort(given);

            // Then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class MergeTest {

        private static Stream<Arguments> createElements() {
            return Stream.of(
                    Arguments.of(Named.of("Null input returns empty list", null), new ArrayList<>()),
                    Arguments.of(Named.of("Empty input returns empty list",
                            new ArrayList<>()), new ArrayList<>()),
                    Arguments.of(Named.of("Empty input returns empty list",
                            List.of(new ArrayList<>())), new ArrayList<>()),

                    Arguments.of(Named.of("Single record returns list with single list",
                            List.of(List.of(1))), List.of(1)),
                    Arguments.of(Named.of("Single list", List.of(List.of(1, 2, 3, 4))), List.of(1, 2, 3, 4)),
                    Arguments.of(Named.of("Containing empty list",
                                    List.of(List.of(1, 2, 3, 4), new ArrayList<>())), List.of(1, 2, 3, 4)),
                    Arguments.of(Named.of("Multiple lists in order",
                            List.of(List.of(1, 2), List.of(2, 3))), List.of(1, 2, 2, 3)),
                    Arguments.of(Named.of("Multiple lists in random order",
                                    List.of(List.of(4, 5), List.of(1, 3), List.of(2, 6, 23), List.of(0, 22), List.of(19, 21, 22))),
                            List.of(0, 1, 2, 3, 4, 5, 6, 19, 21, 22, 22, 23)));
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("createElements")
        void should_Merge(List<List<Integer>> given, List<Integer> expected) {
            // When
            List<Integer> actual = HybridSort.mergeSort(given);

            // Then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }
}