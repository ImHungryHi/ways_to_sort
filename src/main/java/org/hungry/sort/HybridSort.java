package org.hungry.sort;

import org.hungry.dto.IndexTrackedList;

import java.util.*;
import java.util.stream.Stream;

public class HybridSort {

    public static List<Integer> sort(List<Integer> list) {
        return mergeSort(splitByInsertionSort(list));
    }

    static List<List<Integer>> splitByInsertionSort(List<Integer> list) {

        List<List<Integer>> insertionSplitCollection = new ArrayList<>();

        if (list != null && list.size() == 1)
            insertionSplitCollection.add(new ArrayList<>(list));
        if (list == null || list.size() <= 1)
            return insertionSplitCollection;

        List<Integer> currentList = new ArrayList<>();
        insertionSplitCollection.add(currentList);

        for (int x = 1; x < list.size(); x++) {

            if (currentList.isEmpty()) {

                currentList.add(Math.min(list.get(x - 1), list.get(x)));
                currentList.add(Math.max(list.get(x - 1), list.get(x)));
            } else if (currentList.getLast() <= list.get(x)) {

                currentList.add(list.get(x));
            } else {

                currentList = new ArrayList<>();
                insertionSplitCollection.add(currentList);

                if ((x + 1) == list.size()) currentList.add(list.get(x));
            }
        }

        return insertionSplitCollection;
    }

    static List<Integer> mergeSort(List<List<Integer>> listOfLists) {

        List<IndexTrackedList<Integer>> workingList = Stream.ofNullable(listOfLists)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(list -> !list.isEmpty())
                .map(IndexTrackedList::new)
                .toList();
        List<Integer> result = new ArrayList<>();

        if (workingList.isEmpty()) return result;

        int minIndex = 0;

        while (workingList.stream().anyMatch(list -> !list.isExhausted())) {

            for (int x = 1; x < workingList.size(); x++) {

                IndexTrackedList<Integer> indexTrackedList = workingList.get(x);
                if (indexTrackedList.isExhausted()) continue;
                if (indexTrackedList.getElement() < workingList.get(minIndex).getElement()) minIndex = x;
            }

            result.add(workingList.get(minIndex).getElementAndIncrement());

            for (int x = 0; x < workingList.size(); x++) {
                minIndex = x;

                if (!workingList.get(x).isExhausted()) break;
            }
        }

        return result;
    }
}