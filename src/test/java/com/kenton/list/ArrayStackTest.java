package com.kenton.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public  class ArrayStackTest {
    ArrayStack<Student> capStack;
    ArrayStack<Integer> defaultStack ;
    ArrayStack<Integer> negativeStack;
    ArrayStack<Integer> resizeStack;
    @Before
    public void init(){
        capStack = new ArrayStack<>(9);
        //扩容
        for (int i=0;i<=11;i++) {
            capStack.push(new Student("zhangsan", i + 10));
        }
        defaultStack =new ArrayStack<>();
        defaultStack.push(-1);
        defaultStack.push(0).push(2);

        negativeStack=new ArrayStack<>(-1);
        negativeStack.push(-1);

        resizeStack=new ArrayStack<>();
        resizeStack.push(-1);


    }
    @Test
    public void capacity(){
        Assert.assertEquals(18, capStack.getCapacity());
        Assert.assertEquals(12,capStack.getCursor());

        Assert.assertEquals(new Student("zhangsan",21),capStack.pop());
        Assert.assertEquals(11, capStack.getCursor());
        Assert.assertEquals(18,capStack.getCapacity());
        Assert.assertFalse(capStack.isEmpty());
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
    @Test
    public void resize(){
        //stack中有6个元素：-1，4
        resizeStack.push(0).push(1).push(2).push(3).push(4);
        Assert.assertEquals(10,resizeStack.getCapacity());
        Assert.assertEquals(6,resizeStack.getCursor());
        //由于修改了cursor的初始值，所以这段测试用例要重新写，明天再写
//        resizeStack.pop(); //10>4<<1,resize:10*0.75=7
//        Assert.assertEquals(10,resizeStack.getCapacity());
//        Assert.assertEquals(1,resizeStack.getCursor());
//
//        resizeStack.pop(); //7>3<<1，resize:7*0.75=5.25 => 5
//        Assert.assertEquals(5,resizeStack.getCapacity());
//        Assert.assertEquals(4,resizeStack.getCursor());

    }
}