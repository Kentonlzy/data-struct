package com.kenton.list;


/**
 * 用数组实现栈，当size==cursor时，数组扩容至原来的2倍。
 * 当size>cursor*2时，数组缩容至原来的0.75倍
 * 也可以用双向链表实现，思路跟队列相似，不再演示。
 */
public class ArrayStack<T> {
    private Object[] arr;
    public static final int DEFAULT_CAPACITY =2<<3;
    private int capacity =0;
    private int topCursor =0;
    private int frontCursor=0;
    public ArrayStack(){
       this(DEFAULT_CAPACITY);
    }
    public ArrayStack(int capacity){
        if (capacity<=0){
            arr=new Object[DEFAULT_CAPACITY];
            this.capacity = DEFAULT_CAPACITY;
        }else{
            arr =new Object[capacity];
            this.capacity =capacity;
        }
    }
    public boolean isEmpty(){return topCursor ==0 || frontCursor==topCursor;}

    public ArrayStack<T> push(T value){
        add(value);
        return this;
    }
    public T popLast(){
       return get(--topCursor);
    }
    public T peekLast(){return get(topCursor);}
    public T peekFront(){return get(frontCursor);}
    public T popFront(){
        return get(frontCursor++);
    }
    private T get(int index){
        if (index<0 ||frontCursor>topCursor){
            throw new IllegalArgumentException("Has reached the bottom of the stack ");
        }
        T element = element(index);
        int elementNum=topCursor-frontCursor;
        if(capacity > elementNum <<1 && capacity> DEFAULT_CAPACITY){//当有一半的空间空闲，且当前容量大于最小容量时才发生拷贝
            int newCapacity=(int)(capacity*0.75);
            if(newCapacity< DEFAULT_CAPACITY){
                newCapacity= DEFAULT_CAPACITY;
            }
            arr=copy(frontCursor,topCursor,newCapacity);
            //缩容后，更新栈首指针,栈顶指针及最大容量
            frontCursor=0;
            topCursor=elementNum;
            capacity=newCapacity;
        }
        return element;
    }
    private T element(int index){
        return (T)arr[index];
    }
    private void add(T value){
        if (topCursor >= capacity -1){//扩容2倍
            capacity = capacity <<1;
            //拷贝值新数组
            arr = copy(frontCursor,topCursor,capacity);
        }
        arr[topCursor++]=value;
    }
    private Object[] copy(int start,int end,int newCapacity){
        System.out.println("发生了一次复制，newCapacity："+newCapacity);
        Object[] arr=new Object[newCapacity];
        int index=0;
        while (start<=end){
            arr[index]=this.arr[start++];
            index++;
        }
        return arr;
    }
}
