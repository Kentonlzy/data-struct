package com.kenton;

import org.junit.Before;
import org.junit.Test;


public class ListTest {
    private List<Integer> list;

    @Before
    public void before() {
        list = new List<>();
        list.add(1).add(2).add(null).add(3).add(4);
    }

    @Test
    public void add() {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void forEach() {
        list.forEach(value -> {
            if (value != null) {
                value = value + 1;
                System.out.println(value);
            }
        });
    }

    @Test
    public void filter() {
        list.filter(value -> value >= 2).
                forEach(System.out::println);
    }
}