package com.kenton.list;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class QueueTest {

    @Test
    public void push() {
        Queue<Integer> d = new Queue<>();
        d.enqueue(1).enqueue(2).enqueue(3);
        Assert.assertEquals(3,d.getSize());
    }
    @Test(expected = NoSuchElementException.class)
   public void delete(){
        Queue<Integer> d = new Queue<>();
        d.enqueue(1).enqueue(2).enqueue(3);
        Assert.assertEquals((Integer) 1,d.dequeue());
        Assert.assertEquals((Integer) 2,d.dequeue());
        Assert.assertEquals((Integer) 3,d.dequeue());
        d.dequeue();
        Assert.assertEquals(0,d.getSize());
    }
}