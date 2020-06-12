package com.kenton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.Iterator;

public class ListTest {
    private List<Integer> list;
    private final List<Student> studentList=new List<>();
    private Iterator<Student> itr;
    @Before
    public void before() {
        list = new List<>();
        list.add(1).add(2).add(null).add(3).add(4);
        studentList.add(new Student("zhangsan",20))
        .add(new Student("lisi",21))
        .add(new Student("wangwu",22));
        itr=studentList.iterator();
    }
    @Test
    public void itr(){
        while(itr.hasNext()){
            itr.next();
        }
    }
    @Test
    public void add() {
        list.add(1);
        list.add(2);
        list.add(3);
    }
    @Test
    public void get(){
        //get value by index parameter invalid
        Assert.assertNull(list.get(-1));
        Assert.assertNull(list.get(18));
        //get value by index
        Assert.assertNull(list.get(2));
        Assert.assertEquals((Integer)1,list.get(0));
        Assert.assertEquals((Integer)4,list.get(4));



        //get index by value
        Assert.assertEquals(0,list.get((Integer)1));
        Assert.assertEquals(2,list.get(null));
        Assert.assertEquals(4,list.get((Integer)4));
        //get index by value  parameter invalid
        Assert.assertEquals(-1,list.get((Integer)(-1)));
        Assert.assertEquals(-1,list.get((Integer)(18)));
    }
    @Test
    public void insert(){
     list.insert(-1,100)
             .insert(0,200)
             .insert(1,-1)
             .insert(list.size(),500)
             .forEach(System.out::println);

    }
    @Test
    public void delete(){
        list.delete(-1)
                .delete(1)
                .delete(4)
                .delete(null)
                .delete(8)
                .forEach(System.out::println);
        System.out.println("=======");
       studentList.delete(new Student("zhangsan",20))
               .forEach(System.out::println);
       Assert.assertEquals(2,studentList.size());
    }
    @Test
    public void forEach() {
//        list.forEach(value -> {
//            if (value != null) {
//                value = value + 1;
//                System.out.println(value);
//            }
//        });
        for(Integer i:list){
            System.out.printf("%d ",i);
        }
    }

    @Test
    public void filter() {
        list.filter(value -> value >= 2).
                forEach(value-> {
                    System.out.printf("%d ",value);
                });
        list.forEach(System.out::println);
    }
}