package com.kenton.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public  class ArrayStackTest {
    ArrayStack<Student> capStack;
    ArrayStack<Integer> defaultStack ;
    ArrayStack<Integer> negativeStack;
    @Before
    public void init(){
        capStack = new ArrayStack<>(9);
        //扩容
        for (int i=0;i<=11;i++){
            capStack.push(new Student("zhangsan",i+10));
        }

        defaultStack =new ArrayStack<>();
        defaultStack.push(-1);
        defaultStack.push(0).push(2);

        negativeStack=new ArrayStack<>(-1);
        negativeStack.push(-1);
    }
    @Test
    public void capacity(){
        Assert.assertEquals(18, capStack.getSize());
        Assert.assertEquals(11,capStack.getCursor());

        Assert.assertEquals(new Student("zhangsan",21),capStack.pop());
        Assert.assertEquals(10, capStack.getCursor());
        Assert.assertEquals(18,capStack.getSize());
    }

    @Test
    public  void pop() {
        Assert.assertEquals(2,(int)defaultStack.pop());
    }
    @Test(expected = IllegalArgumentException.class)
    public void invalidPop(){
        negativeStack.pop();
        negativeStack.pop();
    }
}