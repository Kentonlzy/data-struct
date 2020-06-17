package com.kenton.tree;

import com.kenton.list.ArrayStack;

import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<? super T>> {
    private Node<T> root;

    public BinarySearchTree() {
        root = null;
    }
     Node<T> getRoot(){return root;}
     public void empty(){root=null;}
    /**
     * 先序遍历
     */
    public void preOrder(Consumer<? super T> func) {
        if (root == null) {
            return;
        }
        preOrder(root, func);
    }

    /**
     *  先序遍历，根左右
     * @param root
     * @param func
     */
    private void preOrder(Node<T> root, Consumer<? super T> func){
        if (root == null) {
            return;
        }
        ArrayStack<Node<T>> stack = new ArrayStack<>();
        Node<T> curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                func.accept(curr.data); //先输出当前"根"的值
                if (curr.right != null) {
                    stack.push(curr.right);  //如果右子树不为空，则把右子树节点压栈
                }
                curr = curr.left; //遍历至左子树 最左的边为空时，退出内层循环
            }
            if (!stack.isEmpty()) { //当栈中为空且curr==null时退出外层循环
                curr = stack.pop();
            }
        }
    }
    public void middleOrder(Consumer<? super T> func){
        middleOrder(root,func);
    }
    /**
     * 中序遍历，左根右
     * @param root
     * @param func
     */
    private void middleOrder(Node<T> root,Consumer< ? super T> func){
        if(root==null){
            return;
        }
        ArrayStack<Node<T>> stack = new ArrayStack<>();
        Node<T> curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr); //把"根节点"压栈
                curr = curr.left; //遍历至左子树 最左的边为空时，退出内层循环
            }
            if (!stack.isEmpty()) { //当栈中为空且curr==null时退出外层循环
                curr = stack.pop();
                func.accept(curr.data);
                curr=curr.right;
            }
        }
    }

    private void rightLeftRoot(Node<T> root,Consumer<? super T> func){
        if (root == null) {
            return;
        }
        ArrayStack<Node<T>> stack = new ArrayStack<>();
        Node<T> curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.right;
            }
            if (!stack.isEmpty()) {
                curr = stack.pop();
                func.accept(curr.data);
                curr=curr.left;
            }
        }
    }
    /**
     * 建立二叉搜索树
     * @param data 要插入的值
     * @return this
     */
    public BinarySearchTree add(T data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if(root==null){
            root=new Node<T>(data);
        }
        return add(root,data);
    }
    private BinarySearchTree<T> add(Node<T> root,T data){
        Node<T> curr = root;
        Node<T> node = new Node<>(data);
        while (true) {
            int result = curr.data.compareTo(data);
            if (result < 0) {
                if (curr.right == null) {
                    curr.right = node;
                    return this;
                }
                curr = curr.right;
            } else if (result > 0) {
                if (curr.left == null) {
                    curr.left = node;
                    return this;
                }
                curr = curr.left;
            } else {
                return this;
            }
        }
    }
    /**
     * 删除二叉搜索树的节点，分为两种情况
     * 1、删除叶子节点=>直接删除即可
     * 2、删除非叶子节点->使用该节点的左子树或右子树替换该节点即可
     *       6
     *    3     8
     *  2   5  7  11
     * 1   4
     * 比如：要删除左子树中的3，则该二叉搜素树可以变成如下
     *       6
     *     2    8
     *  1   5  7  11
     *    4
     * 比如：要删除右子树中的8，则该二叉搜索树可变成：
     *       6
     *    3      11
     *  2   5  7
     * 1   4
     * @param value
     * @return
     */
    public BinarySearchTree<T> remove(T value) {
        if (root == null || value == null) {
            return this;
        }
        if(root.data.compareTo(value)==0){//要移除根节点
            root=null;
            return this;
        }
        Node<T> parent = findParent(root, value);
        boolean isLeft=root.data.compareTo(value)>0;
        if (parent==null) {
            return this;
        }
        Node<T> curr = parent;
        while ( curr != null) {
            int result = curr.data.compareTo(value);
            if (result == 0) {
                if (curr.left == null && curr.right == null) { //叶子节点
                    removeNode(parent,curr);
                }else{ //不是叶子节点
                    if(isLeft){
                        //在根节点的左侧，找最小值
                        removeLeftCurrentNode(curr);
                    }else{
                        //在根节点的右侧，找最大值
                        removeRightCurrentNode(curr);
                    }

                }
                return this;
            } else if (result > 0) {//比父亲节点的值小，遍历左子树
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return this;
    }





    private void removeLeftCurrentNode(Node<T> curr) {
        if(curr==null){
            throw new NullPointerException();
        }
        Node<T> min =null;

        if(curr.left!=null){
            min=findMin(curr.left);
        }else if(curr.right!=null){
            min=findMin(curr.right);
        }
        if(min==null){
            throw  new NullPointerException();
        }
        curr.data=min.data;
        //保存变量准备重新构建二叉树
        ArrayStack<T> stack = new ArrayStack<>();
        Node<T> finalMin = min;
        rightLeftRoot(curr,value-> {
            if(finalMin.data.compareTo(value)!=0){
                stack.push(value);
            }
        });
        curr.left=null;
        curr.right=null;
        //重新构建二叉树
        while(!stack.isEmpty()){
            add(curr,stack.pop());
        }
    }
    private void removeRightCurrentNode(Node<T> curr){
        if(curr==null){
            throw new NullPointerException();
        }
        Node<T> max =null;

        if(curr.left!=null){
            max=findMax(curr.left);
        }else if(curr.right!=null){
            max=findMax(curr.right);
        }
        if(max==null){
            throw  new NullPointerException();
        }
        curr.data=max.data;
        //保存变量准备重新构建二叉树
        ArrayStack<T> stack = new ArrayStack<>();
        Node<T> finalMax = max;
        preOrder(curr,value-> {
            if(finalMax.data.compareTo(value)!=0){
                stack.push(value);
            }
        });
        curr.left=null;
        curr.right=null;
        //重新构建二叉树
        while(!stack.isEmpty()){
            add(curr,stack.pop());
        }
    }

    private void removeNode(Node<T> parent, Node<T> curr) {
        if (parent==null||curr==null){
            throw new NullPointerException();
        }
        //叶子节点的值比父亲节点小，则证明是左侧的叶子节点
        boolean isLeft=parent.data.compareTo(curr.data)>0;
        if (isLeft){
            parent.left=null;
        }else{
            parent.right=null;
        }
    }

    public T getNodeValue(Node<T> node){
        return node.getData();
    }
    public Node<T> findParent(Node<T> parent, T value) {
        if (parent == null || value == null) {
            throw new NullPointerException();
        }
        Node<T> result = parent;
        Node<T> p = parent;
        while (p != null) {
            int res = p.data.compareTo(value);
            if (res == 0) {
                break;
            } else if (res > 0) {//遍历左子树
                result = p;
                p = p.left;
            } else { //遍历右子树
                result = p;
                p = p.right;
            }
        }
        //
        return p==null?null:result;
    }

    public void makeEmpty() {
        root = null;
    }


    public boolean isEmpty() {
        return root == null ;
    }

    private Node<T> findMin(Node<T> node){
        if (node == null) {
            return null;
        }
        Node<T> curr = node;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }
    public T findMin() {
        Node<T> min = findMin(root);
        if(min==null){
            throw new NullPointerException();
        }
        return min.data;
    }


    public T findMax() {
        Node<T> max = findMax(root);
        if(max==null){
            throw new NullPointerException();
        }
        return max.data;
    }
    private Node<T> findMax(Node<T> node){
        if ( node == null) {
            return null;
        }
        Node<T> curr = node;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }


    public boolean contains(T value) {
        if (value == null) {
            return false;
        }
        if (root == null) {
            return false;
        }
        Node<T> curr = root;
        while (true) {
            int result = curr.data.compareTo(value);
            if (result == 0) {
                return true;
            } else if (result > 0) {//遍历左子树
                if (curr.left == null) { //左子树为空时，退出
                    return false;
                }
                curr = curr.left;
            } else { //遍历右子树
                if (curr.right == null) {//右子树为空时，退出
                    return false;
                }
                curr = curr.right;
            }
        }
    }

    private static   class Node<T extends Comparable<? super T>> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node() {
            this(null, null, null);
        }

        public Node(T data) {
            this(data, null, null);
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
         T getData(){return data;}
    }

}
