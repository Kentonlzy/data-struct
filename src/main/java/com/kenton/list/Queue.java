package com.kenton.list;

import java.util.NoSuchElementException;

/**
 * 双向链表实现队列。
 * 还可以使用数组实现，实现思路跟stack差不多，queue里包含Object[] arr,int front, int last;
 * 入队或出队时移动cursor即可。
 * @param <T>
 */
public class Queue<T> {
    private final Node<T> first;
    private final Node<T> last;
    private int size=0;
    public Queue(){
        first = new Node<T>(null);
        last=new Node<T> (null);

        first.next=last;
        last.pre=first;
    }
    public Queue<T> enqueue(T value){
        Node<T> node = new Node<>(value);
        Node<T> top=first;
        while(top.next!= last){
            top=top.next;
        }
        insert(top,node);
        size++;
        return this;
    }

    public T dequeue(){
        if (size<=0){
            throw new NoSuchElementException("deque is empty");
        }
        T tmp=first.next.value;
        Node<T> theNext=first.next.next;
        theNext.pre=first;
        first.next=theNext;
        size--;
        return tmp;
    }
    public int getSize(){
        return size;
    }
    private void insert(Node<T> top,Node<T> node ) {
        last.pre=node;
        node.next=last;

        node.pre=top;
        top.next=node;
    }

    private static class Node<T>{
        private T value;
        private Node<T> pre;
        private Node<T> next;
        public Node(T value){
            this.value=value;
            pre=null;
            next=null;
        }
    }
}
