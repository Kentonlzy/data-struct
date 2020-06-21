package com.kenton.tree;

import com.kenton.list.ArrayStack;

import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<? super T>> {
    private Node<T> root;

    public BinarySearchTree() {
        root = null;
    }

    Node<T> getRoot() {
        return root;
    }

    public void empty() {
        root = null;
    }

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
     * 先序遍历，根左右
     *
     * @param root 某一节点
     * @param func 如何处置当前遍历到的值
     */
    private void preOrder(Node<T> root, Consumer<? super T> func) {
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
                curr = stack.popLast();
            }
        }
    }

    public void middleOrder(Consumer<? super T> func) {
        middleOrder(root, func);
    }

    /**
     * 中序遍历，左根右
     *
     * @param root 某一节点
     * @param func 如何处置当前遍历到的值
     */
    private void middleOrder(Node<T> root, Consumer<? super T> func) {
        if (root == null) {
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
                curr = stack.popLast();
                func.accept(curr.data);
                curr = curr.right;
            }
        }
    }

    public void postOrder(Consumer<? super T> func) {
        preOrder(root, func);
    }

    private void postOrder(Node<T> root, Consumer<? super T> func) {
        if (root == null) {
            return;
        }
        ArrayStack<Node<T>> stack = new ArrayStack<>();
        Node<T> curr = root;
        Node<T> lastVisit = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            //当遍历到左子树的叶子节点时，查看该叶子节点
            curr = stack.peekLast();
            //如果它的右子树为空，或右子树已经遍历过，则直接输出该节点，并将它从栈中抛出
            //否则继续遍历其右子树
            if (curr.right == null || curr.right == lastVisit) {
                func.accept(curr.data);
                stack.popLast();
                lastVisit = curr;//当前节点标记为已遍历
                curr = null;
            } else {
                curr = curr.right;
            }
        }
    }

    /**
     * 右左根遍历，为了尽量保持删除节点后，二叉树基本不变。
     * 使用右左根遍历是否会更好？更能保持二叉树原有的形状
     *
     * @param root 某一节点
     * @param func 如何处置当前遍历到的值
     */
    private void rightRootLeft(Node<T> root, Consumer<? super T> func) {
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
                curr = stack.popLast();
                func.accept(curr.data);
                curr = curr.left;
            }
        }
    }

    /**
     * 建立二叉搜索树
     *
     * @param data 要插入的值
     * @return this
     */
    public BinarySearchTree add(T data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node<T>(data);
        }
        return add(root, data);
    }

    private BinarySearchTree<T> add(Node<T> root, T data) {
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
     * 删除节点
     *
     * @param value 删除节点的值
     * @return this 可以链式调用
     */
    public BinarySearchTree<T> remove(T value) {
        if (root == null || value == null) {
            return this;
        }
        if (root.data.compareTo(value) == 0) {//移除根节点
            root = null;
            return this;
        }
        Node<T> parent = findParent(root, value);//找到指定值的父亲节点
        if (parent == null) {
            return this;
        }
        Node<T> curr = parent;
        while (curr != null) {
            int result = curr.data.compareTo(value);
            if (result == 0) { //找到要删除的节点
                if (curr.left == null && curr.right == null) { //如果要删除的节点为叶子节点，直接删除
                    removeLeafNode(parent, curr);
                } else if (curr.right == null) { //左子树不为空
                    parent.left = curr.left;
                } else if (curr.left == null) {//右子树不为空
                    parent.left = curr.right;
                } else { //左右子树都不为空
                    removeNodeBetter(curr);
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

    public void removeNodeBetter(Node<T> curr) {
        if (curr == null) {
            throw new NullPointerException();
        }
        Node<T> replaceNode = searchReplaceNode(curr);
        if (replaceNode == null) {
            throw new NullPointerException("delete node failed!,because it's left is null");
        }
        Node<T> replaceNodeParent = findParent(curr, replaceNode.data);
        curr.data = replaceNode.data;
        replaceNodeParent.right = replaceNode.right;
    }

    /**
     * 删除节点
     *
     * @param curr 要删除的节点
     */
    private void removeNode(Node<T> curr) {
        if (curr == null) {
            throw new NullPointerException();
        }
        Node<T> replaceNode = searchReplaceNode(curr);
        if (replaceNode == null) {
            throw new NullPointerException("delete node failed!,because it's left is null");
        }
        //找到要替换的节点，替换值后，重新构建 子二叉树
        rebuildTree(curr, replaceNode.data);
    }

    private Node<T> searchReplaceNode(Node<T> curr) {
        Node<T> replaceNode = null;
        if (curr.left != null) {
            replaceNode = findMax(curr.left);
        }
        return replaceNode;
    }

    private void rebuildTree(Node<T> curr, T targetValue) {
        if (targetValue == null || curr == null) {
            return;
        }
        curr.data = targetValue;
        //保存变量准备重新构建二叉树
        ArrayStack<T> stack = new ArrayStack<>();
        rightRootLeft(curr, value -> {
            if (targetValue.compareTo(value) != 0) { //排除要被替换的值，targetValue.compareTo(value)==0会命中两次
                stack.push(value);
            }
        });
        //删除现有节点。事实上，这步操作可以不用做，为了方便理解，所以加上了。
        curr.left = null;
        curr.right = null;
        //重新构建二叉搜索树
        while (!stack.isEmpty()) {
            add(curr, stack.popLast());
        }
    }

    public void removeNode2(Node<T> parent, Node<T> curr) {
        if (parent == null || curr == null) {
            throw new NullPointerException();
        }
        boolean isLeft = root.data.compareTo(curr.data) > 0;
        Node<T> replaceNode = null;

    }

    /**
     * 删除叶子节点
     *
     * @param parent leafNode.parent
     * @param curr   leafNode
     */
    private void removeLeafNode(Node<T> parent, Node<T> curr) {
        if (parent == null || curr == null) {
            throw new NullPointerException();
        }
        //叶子节点的值比父亲节点小，则证明是左侧的叶子节点
        boolean isLeft = parent.data.compareTo(curr.data) > 0;
        if (isLeft) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    public T getNodeValue(Node<T> node) {
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
        return p == null ? null : result;
    }

    public void makeEmpty() {
        root = null;
    }


    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 找到包含最小值的节点。
     * 二叉搜索树中，最小值一定是左子树中的最后一个值。
     *
     * @param node 从哪个节点开始搜索最小值
     * @return 包含最小值的节点
     */
    private Node<T> findMin(Node<T> node) {
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
        if (min == null) {
            throw new NullPointerException();
        }
        return min.data;
    }


    public T findMax() {
        Node<T> max = findMax(root);
        if (max == null) {
            throw new NullPointerException();
        }
        return max.data;
    }

    /**
     * 找到包含最大值的节点。
     * 二叉搜索树中，最大值一定是右子树中的最后一个值。
     *
     * @param node 从哪个节点开始搜索最大值
     * @return 包含最大值的节点
     */
    private Node<T> findMax(Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> curr = node;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    /**
     * 遍历二叉树，搜索是否包含目标值
     *
     * @param value 要搜索的值
     * @return bool：是否包含目标值
     */
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

    private static class Node<T extends Comparable<? super T>> {
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

        T getData() {
            return data;
        }
    }

}
