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
    @Test
    public void preOrder(){
        /**
         *      6
         *   3     7
         * 2    5    8
         *1    4
         */
        tree.add(2).add(1).add(8);
        tree.preOrder(value-> System.out.print(value+" "));
        System.out.println();
        tree.remove(3);
        tree.preOrder(value-> System.out.print(value+" "));
    }
    @Test
    public void middleOrder(){
        /**
         *      6
         *   3     7
         * 2    5    8
         *1    4
         */
        tree.add(2).add(1).add(8);
        tree.middleOrder(value-> System.out.print(value+" "));
    }
    @Test
    public void findParent(){
        Assert.assertNull(tree.findParent(tree.getRoot(),-1));
        Assert.assertEquals(6,(int)tree.getNodeValue(tree.findParent(tree.getRoot(),6)));
        Assert.assertEquals(6,(int)tree.getNodeValue(tree.findParent(tree.getRoot(),7)));
        Assert.assertEquals(6,(int)tree.getNodeValue(tree.findParent(tree.getRoot(),3)));
        Assert.assertEquals(3,(int)tree.getNodeValue(tree.findParent(tree.getRoot(),5)));
        Assert.assertEquals(5,(int)tree.getNodeValue(tree.findParent(tree.getRoot(),4)));
    }
    @Test
    public void remove(){
        tree.empty();
        tree.add(30).add(10).add(5).add(3).add(1).add(4).add(9).add(7).add(8).add(11).add(100).add(40).add(35).add(80).add(110);
        tree.preOrder(value-> System.out.print(value+" "));

        tree.remove(5);
        System.out.print("remove 5:"); tree.preOrder(value-> System.out.print(value+" "));

//        tree.remove(100);
//        System.out.print("remove 100:");tree.preOrder();

    }
    @Test
    public void remove2(){
        tree.remove(5);
        tree.preOrder(System.out::println);

        tree.empty();
        tree.add(100).add(110).add(105).add(101).add(103).add(102);
        tree.preOrder(System.out::println);

        tree.remove(101);
        tree.preOrder(System.out::println);
    }
}