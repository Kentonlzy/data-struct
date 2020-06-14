package com.kenton.list;


import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 用数组实现栈，当size==cursor时，数组扩容至原来的2倍。
 * 当size>cursor*2时，数组缩容至原来的0.75倍
 * 也可以用双向链表实现，思路跟队列相似，不再演示。
 */
public class ArrayStack<T> {
    private Object[] arr;
    private static  final int DEFAULT_CAPACITY =10;
    private static final int INITIALIZATION =2<<3;
    private int size=0;
    private int cursor=-1;
    public ArrayStack(){
        arr=new Object[DEFAULT_CAPACITY];
        size=DEFAULT_CAPACITY;
    }
    public ArrayStack(int capacity){
        if (capacity<=0){
            arr=new Object[INITIALIZATION];
            size=INITIALIZATION;
        }else{
            arr =new Object[capacity];
            size=capacity;
        }
    }
    public int getSize(){return size;}
    public int getCursor(){return cursor;}

    public ArrayStack<T> push(T value){
        add(value);
        return this;
    }
    public T pop(){
       return get(cursor--);
    }
    private T get(int index){
        if (index<0){
            throw new IllegalArgumentException("Has reached the bottom of the stack ");
        }
        T element = element(index);
        if(size>cursor<<1){//resize
            size=(int)(size*0.75);
            arr = Arrays.copyOf(arr, size);
        }
        return element;
    }
    private T element(int index){
        return (T)arr[index];
    }
    private void add(T value){
        if (cursor>=size-1){//扩容2倍
            size=size<<1;
            //拷贝值新数组
            arr = Arrays.copyOf(arr, size);
        }
        arr[++cursor]=value;
    }

}
