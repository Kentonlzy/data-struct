package com.kenton;

import java.util.function.Consumer;

public class List<T>{
    //指向头节点
    private final Node<T> head;
    //指向尾节点
    public List(){
        head=new Node<T>(null);
    }

    /**
     * add Template value to list
     * @param data value
     * @return this
     */
    public  List<T> add(T data){
        Node<T> node = new Node<T>(data);
        if(head.next==null){
            head.next=node;
            return this;
        }
        Node<T> curr=head;
        while(curr.next!=null){
            curr=curr.next;
        }
        curr.next=node;
        return this;
    }

    /**
     * for each list
     * @param function handle function
     */
    public void forEach(Consumer<T> function){
        Node<T> curr=head.next;
        while(curr!=null){
            function.accept(curr.data);
            curr=curr.next;
        }
    }

    /**
     * filter list by user
     * @param f filter function
     * @return temp list
     */
    public List<T> filter(Filter<T> f){
        Node<T> curr=head.next;
        List<T> tempList = new List<>();
        while(curr!=null){
            //filter nullptr
            if(curr.data==null){
                curr=curr.next;
            }
            if(f.filter(curr.data)){
                tempList.add(curr.data);
            }
            curr=curr.next;
        }
        return tempList;
    }


    private static class Node<T>{
        T data;
        Node<T> next;
        public Node(T data){
            this.data=data;
            next=null;
        }
        public Node(T data,Node<T> next){
            this.data=data;
            this.next=next;
        }
    }
}
