package com.kenton.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTest {
    public   BinarySearchTree<Integer> tree;
    @Before
   public void insert() {
        tree = new BinarySearchTree<>();
        /**
         *      6
         *   3     7
         *     5
         *   4
         */
        tree.add(6);
        tree.add(7);
        tree.add(3);
        tree.add(5);
        tree.add(4);

    }

    @Test
   public void makeEmpty() {
        tree.makeEmpty();
        Assert.assertEquals(0,tree.getNodeNum());
    }

    @Test
   public void isEmpty() {
        tree.makeEmpty();
        Assert.assertTrue(tree.isEmpty());
        tree.add(0);
        Assert.assertFalse(tree.isEmpty());
    }

    @Test
    public  void findMin() {
        Assert.assertEquals(3,(int)tree.findMin());
        tree.add(8);
        tree.add(2);
        /**
         *      6
         *   3     7
         * 2    5    8
         *   4
         */
        Assert.assertEquals(2,(int)tree.findMin());
    }

    @Test
   public void findMax() {
        Assert.assertEquals(7,(int)tree.findMax());
        tree.add(8);
        tree.add(2);
        /**
         *      6
         *   3     7
         * 2    5    8
         *   4
         */
        Assert.assertEquals(8,(int)tree.findMax());
    }

    @Test
    public void contains() {
        Assert.assertTrue(tree.contains(4));
        Assert.assertFalse(tree.contains(8));
    }
}