package com.kenton.list;


import java.util.Arrays;

/**
 * 用数组实现栈，用这种方式实现的问题在于：执行pop后，数组内的数据并未被gc回收，会一直占用空间。
 * 并且当没有使用capacity初始化，且有大量push，没有pop时，数组会不断扩容，造成性能下降。
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
        return element(index);
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
