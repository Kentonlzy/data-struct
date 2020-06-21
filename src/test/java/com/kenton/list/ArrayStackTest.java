package com.kenton.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;


public class ArrayStackTest {
    ArrayStack<Integer> capInitStack;
    ArrayStack<Integer> defaultInitStack;
    ArrayStack<Integer> negativeInitStack;
    public static final String CAPACITY="capacity";
    public static final String TOP_CURSOR="topCursor";
    public static final String FRONT_CURSOR="frontCursor";
    @Before
    public void init() {
        capInitStack = new ArrayStack<>(9);
        capInitStack.push(0).push(1).push(2).push(3);

        defaultInitStack =new ArrayStack<>();

        negativeInitStack=new ArrayStack<>(-1);
    }

    public Integer getInteger(ArrayStack stack, String fieldName) {
        try {
            Class stackClass = ArrayStack.class;
            Field field = stackClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(stack);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testCapacity() {
        for (int i = 0; i < 10; i++) {
            capInitStack.push(i);
        }
       Assert.assertEquals(18,(int)getInteger(capInitStack,CAPACITY));
        Assert.assertEquals(16,(int)getInteger(defaultInitStack,CAPACITY));
        Assert.assertEquals(16,(int)getInteger(negativeInitStack,CAPACITY));
    }

    @Test
    public void testPeek() {
        Assert.assertEquals(0, (int) capInitStack.popFront());
        Assert.assertEquals(1, (int) capInitStack.popFront());
        Assert.assertEquals(2, (int) capInitStack.popFront());
        Assert.assertEquals(3,(int) capInitStack.popFront());
        //isEmpty
        Assert.assertTrue(capInitStack.isEmpty());
        //peek to stack top ,capacity will not be changed
        Assert.assertEquals(9,(int)getInteger(capInitStack,CAPACITY));
        //peek to stack top=> front cursor== top cursor==4
        Assert.assertEquals(4,(int)getInteger(capInitStack,TOP_CURSOR));
        Assert.assertEquals(4,(int)getInteger(capInitStack,FRONT_CURSOR));

        capInitStack.push(0);
        //after push stack is not empty
        Assert.assertFalse(capInitStack.isEmpty());
        //top cursor ++
        Assert.assertEquals(5,(int)getInteger(capInitStack,TOP_CURSOR));
        capInitStack.popLast();
        //top cursor --
        Assert.assertEquals(4,(int)getInteger(capInitStack,TOP_CURSOR));

    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPeekOrPop(){
        for (int i = 0; i < 3; i++) {
            capInitStack.popFront();
        }
        capInitStack.popLast();
        capInitStack.popLast();
    }
    @Test
    public void testResizeArray(){
        for (int i=0;i<16;i++){
            defaultInitStack.push(i);
        }
        //capacity*=2
        Assert.assertEquals(32,(int)getInteger(defaultInitStack,CAPACITY));
        Assert.assertEquals(0,(int)getInteger(defaultInitStack,FRONT_CURSOR));
        Assert.assertEquals(16,(int)getInteger(defaultInitStack,TOP_CURSOR));

        defaultInitStack.popFront();
        //resize array:capacity=32*0.75==24,and front cursor to zero,top cursor=element number
        Assert.assertEquals(24,(int)getInteger(defaultInitStack,CAPACITY));
        Assert.assertEquals(0,(int)getInteger(defaultInitStack,FRONT_CURSOR));
        Assert.assertEquals(15,(int)getInteger(defaultInitStack,TOP_CURSOR));

        //min capacity is 16
        //current size is 15,current capacity is 24
        for(int i=0;i<9;i++){
            defaultInitStack.popLast();
        }
        Assert.assertEquals(16,(int)getInteger(defaultInitStack,CAPACITY));
        Assert.assertEquals(0,(int)getInteger(defaultInitStack,FRONT_CURSOR));
        Assert.assertEquals(6,(int)getInteger(defaultInitStack,TOP_CURSOR));

        defaultInitStack.popLast();
        defaultInitStack.popLast();
        defaultInitStack.popLast();

        defaultInitStack.popFront();
        defaultInitStack.popFront();
        defaultInitStack.popFront();

        Assert.assertEquals(16,(int)getInteger(defaultInitStack,CAPACITY));
        Assert.assertEquals(3,(int)getInteger(defaultInitStack,FRONT_CURSOR));
        Assert.assertEquals(3,(int)getInteger(defaultInitStack,TOP_CURSOR));
        Assert.assertTrue(defaultInitStack.isEmpty());



    }
}