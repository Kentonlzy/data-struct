package com.kenton;

public interface Filter<T> {
    boolean filter(T t);
}
