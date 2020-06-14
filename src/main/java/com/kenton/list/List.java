package com.kenton.list;

import java.util.Iterator;
import java.util.function.Consumer;

public class List<T> implements AbstractList<T>{
    private final Node<T> head;
    private int size = 0;

    //生成头节点
    public List() {
        head = new Node<T>(null);
    }

    /**
     * add Template value to list
     *
     * @param data value
     * @return this
     */
    public List<T> add(T data) {
        Node<T> node = new Node<T>(data);
        size++;
        Node<T> curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = node;
        return this;
    }

    /**
     *  获取当前单链表长度
     * @return this.size
     */
    public int size() {
        return size;
    }

    /**
     *  根据index来获取元素
     * @param index 下标
     * @return 下标为index的元素
     */
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        int p = 0;
        Node<T> curr = head.next;
        T result=null;
        while (curr != null) {
            if (index == p++) {
                result= curr.data;
                break;
            }
            curr = curr.next;
        }
        return result;
    }

    /**
     * 获取元素的下标
     * @param value 元素值
     * @return 元素的下标
     */
    public int get(T value) {
        Node<T> curr = head.next;
        int p = 0;
        while (curr != null) {
            if (curr.data == null) {
                if (value == null) {
                    return p;
                }
            } else {
                if (curr.data.equals(value)) {
                    return p;
                }
            }
            curr = curr.next;
            p++;
        }
        return -1;
    }

    /**
     *  插入元素
     * @param index 元素要插入的位置
     * @param value 元素值
     * @return this
     */
    public List<T> insert(int index, T value) {
        if (index > size + 1 || index < 0) {
            return this;
        }
        Node<T> node = new Node<>(value);
        Node<T> curr = head;
        size++;
        int p = -1; //索引指向头节点
        while (curr != null) {
            if (p == index - 1) {//指向要插入索引的上一个节点
                node.next = curr.next;
                curr.next = node;
            }
            curr = curr.next;
            p++;
        }
        return this;
    }

    /**
     * 删除值为value的节点
     * @param value 元素值
     * @return this
     */
    public List<T> delete(T value) {
        Node<T> curr = head;
        while (curr.next != null) {
            if (value == null && curr.next.data == null) { //处理list中存有null的情况
                curr.next = curr.next.next;
                size--;
                return this;
            }
            if (curr.next.data.equals(value)) {
                curr.next = curr.next.next;
                size--;
                return this;
            }
            curr = curr.next;
        }
        return this;
    }

    /**
     * 删除下标为index的元素
     * @param index 下标
     * @return this
     */
    public List<T> delete(int index) {
        if (index > size || index < 0) {
            return this;
        }
        int p = -1; //头节点
        Node<T> curr = head;
        while (curr != null) {
            if (p == index - 1) { //要删除索引的上一个节点
                curr.next = curr.next.next;
                size--;
                return this;
            }
            curr = curr.next;
        }
        return this;
    }

    /**
     * 迭代器 为了让list能使用增强for循环
     * @return  迭代器
     */
    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * for each list
     *
     * @param function handle function
     */
    public void forEach(Consumer<? super T> function) {
        Node<T> curr = head.next;
        while (curr != null) {
            function.accept(curr.data);
            curr = curr.next;
        }
    }

    /**
     * filter list by user
     *
     * @param f filter function
     * @return temp list
     */
    public List<T> filter(Filter<T> f) {
        Node<T> curr = head.next;
        List<T> tempList = new List<>();
        while (curr != null) {
            //filter nullptr
            if (curr.data == null) {
                curr = curr.next;
            }
            if (f.filter(curr.data)) {
                tempList.add(curr.data);
            }
            curr = curr.next;
        }
        return tempList;
    }

    /**
     *  单链表中的节点
     * @param <T>
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            next = null;
        }

    }

    /**
     * 迭代器
     */
    private class Itr implements Iterator<T> {
        private int cursor = 0;

        /**
         * 当游标小于单链表总长度时，证明还有元素尚未遍历
         * @return cursor < size;
         */
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * 当遍历到链表尾部时，继续使用itr.next时返回 null
         * @return 元素值
         */
        @Override
        public T next() {
            if (cursor >=size) {
                return null;
            }
            return get(cursor++);
        }
    }
}
