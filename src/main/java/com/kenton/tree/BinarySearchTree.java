package com.kenton.tree;

public class BinarySearchTree<T extends Comparable<? super T>> {
    private Node<T> root;
    private int nodeNum=0;
    public  BinarySearchTree(){
        root=null;
    }


    public BinarySearchTree add(T data)  {
        if(data==null){
            throw new NullPointerException();
        }

        if (root==null){
            root=new Node<>(data);
            return this;
        }
        Node<T> curr=root;
        Node<T> node=new Node<>(data);
        nodeNum++;
        while(true){
            int result = curr.data.compareTo(data);
            if(result<0){
                if(curr.right==null){
                    curr.right=node;
                    return this;
                }
                curr=curr.right;
            }else if(result>0){
                if(curr.left==null){
                    curr.left=node;
                    return this;
                }
                curr=curr.left;
            }else{
                return this;
            }
        }
    }
    public BinarySearchTree<T> remove(T value){
        if(root==null || value==null){
            return this;
        }
        boolean contains = contains(value);
        if(!contains){
            return this;
        }
        Node<T> curr=root;
        while(curr!=null){
            int result=curr.data.compareTo(value);
            if(result==0){
                curr=curr.right;
                return this;
            }else if(result>0){//
                curr=curr.left;
            }else{
                curr=curr.right;
            }
        }
        return this;
    }

    public void makeEmpty() {
        root=null;
        nodeNum=0;
    }


    public boolean isEmpty() {
        return root==null && nodeNum==0;
    }


    public T findMin() {
        if(nodeNum==0 || root==null){
            return null;
        }
        Node<T> curr=root;
        while(curr.left!=null){
            curr=curr.left;
        }
        return curr.data;
    }


    public T findMax() {
        if(nodeNum==0 || root==null){
            return null;
        }
        Node<T> curr=root;
        while(curr.right!=null){
            curr=curr.right;
        }
        return curr.data;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public boolean contains(T value) {
        if (value==null){
            return false;
        }
        if(root==null){
            return false;
        }
        Node<T> curr=root;
        while(true){
            int result = curr.data.compareTo(value);
            if (result==0){
                return true;
            }else if(result>0){//遍历左子树
                if(curr.left==null){ //左子树为空时，退出
                    return false;
                }
                curr=curr.left;
            }else{ //遍历右子树
                if(curr.right==null){//右子树为空时，退出
                    return false;
                }
                curr=curr.right;
            }
        }
    }

    private static class Node<T extends Comparable<? super T>>{
        T data;
        Node<T> left;
        Node<T> right;
        public Node(){
            this(null,null,null);
        }
        public Node(T data){
           this(data,null,null);
        }

        public Node(T data,Node<T> left,Node<T> right){
            this.data=data;
            this.left=left;
            this.right=right;
        }
    }

}
