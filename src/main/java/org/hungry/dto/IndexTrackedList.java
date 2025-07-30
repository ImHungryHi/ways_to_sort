package org.hungry.dto;

import lombok.Getter;

import java.util.List;

public class IndexTrackedList<T> {

    private final List<T> list;

    @Getter
    private int index = 0;

    public IndexTrackedList(List<T> list) {
        this.list = list;
    }

    public void increment() {
        this.index++;
    }

    public T getElementAndIncrement() {
        T result = getElement();
        increment();
        return result;
    }

    public T getElement() {
        return this.list.get(index);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isExhausted() {
        return index >= this.list.size();
    }
}