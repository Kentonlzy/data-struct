package com.kenton.list;

import java.util.function.Consumer;

/**
 * arrayList 用数组实现，当size>=capacity时需要动态扩容，每次扩大1.5倍。不再实现
 * @param <T>
 */
public interface AbstractList<T> extends Iterable<T> {
     AbstractList<T> add(T data);
     T get(int index);
     int get(T value);
     AbstractList<T> insert(int index,T value);
     AbstractList<T> delete(int index);
     AbstractList<T> delete(T value);
     void forEach(Consumer<? super T> func);
     AbstractList<T> filter(Filter<T> f);
}
