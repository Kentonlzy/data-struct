# data-struct
## 学习资料推荐

### 严蔚敏《数据结构》

这本书优点在于结构合理，条理清晰。

缺点也很明显：C和C++及伪代码混用导致可读性低，跟着书本敲代码经常会一连串的报错，体验极其的差。

学习这本书，需要具备相当的C语言功力，建议各位同学搭配《C prime plus》食用

### 大话数据结构

语言风趣，通俗易懂，适合入门级选手食用。

### 视频资源

[王道考研 数据结构](https://www.bilibili.com/video/BV1b7411N798?from=search&seid=8749473164421645727)
[浙江大学 数据结构]( https://www.bilibili.com/video/BV1JW411i731?from=search&seid=14341549613362537413)

## 前言

计算机的基础课程，每一门都很重要！数据结构作为其他基础课程的前导课程是必须要掌握好的！

本项目只讨论数据结构的基本概念及其Java版本的实现，算法将会在下个项目中拿出来单独讨论。由于时间有限，对于部分实现上相似的内容，将会一笔带过，有疑问的同学，可以提issue或发邮件到我的邮箱：kenton.lzy@gmail.com

学习本项目需要具备以下基础：有一定的Java或其他面向对象语言基础，知道junit单元测试的基本用法。

## 概念解释

在list部分我实现了如下几个数据结构：链表，栈，队列。从传统意义上讲，这三个数据结构应该分开来讨论的，但由于他们都可以使用数组或单链表或双向链表来实现，索性就把他们放到一起了。

为了省时间，我用了不同的方式来实现单链表、栈、队列，这样就不用每个数据结构都用三种方式来实现了。我可真是个小机灵鬼～。

List使用单链表实现，Stack使用动态数组实现，Queue使用双向链表实现。

### 单链表

单链表即为单向链表，它有**一个头节点和N个"子”节点**。

每个节点都有一个存放数据的空间(数据域)，和指向下一个节点位置的指针（指针域）。这就像一个火车头（head）带着一节节车厢（node），车厢之间通过链条（指针）相连。

车厢（节点）的定义如下：

```java
class Node<T>{ //T：车厢内所存储的货物类型（相当于严书中的ElementType）
  T value; //value 货物
  Node<T> next;//下一节车厢
}
```

通过定义我们可以知道，单链表的结构长这样：head->node1->node2->node3...->nodeN。为了方便描述，我们把node1称为head的**后继**。同样的，也可以说head的后继是node1。

#### 初始化

初始化时只要初始化head节点即可，之后添加、删除等操作，都是以头节点为基准的。

```java
public class List<T> implements AbstractList<T>{
    private final Node<T> head;
    private int size = 0; //单链表长度
    //生成头节点
    public List() {
        head = new Node<T>(null);
    }
      private static class Node<T> {
        T data;
        Node<T> next;
        public Node(T data) {
            this.data = data;
            next = null;
        }
    }
}
```

#### 添加

我们当我们试图在火车的末尾增加一节车厢时，只需通过火车头（head）来一节节的遍历，不断获取下一节车厢的位置，直到发现某节车厢是最后一节车厢时才停止。当然，我们不能去操作头节点，改变它的位置（火车头跑到火车中间了，那还叫火车吗？）。我们可以用一个指向头节点的指针，去遍历整个链表。

```java
    public List<T> add(T data) {
        Node<T> node = new Node<T>(data); //新增一节车厢
        size++; //火车长度自增
        Node<T> curr = head;
        while (curr.next != null) { //当某节车厢没有下一节车厢时，停止遍历
            curr = curr.next;
        }
        curr.next = node;
        return this;  //为了可以使用list.add().add().add()
    }
```

#### 插入

需要向某个位置插入一节车厢时，应该怎么做呢？

head->node1->node2->node3，要在node2和node3之间新增一个nodeX要怎么做？首先，肯定是使用指针从头到尾遍历，遍历到node2时，才开始链接操作。

那么是直接把nodeX连在node2后面？还是要先把nodeX跟node3连起来，再把node2跟nodeX连起来？

显然，前者是不可行的，因为如果直接把node2跟nodeX相连，node2跟node3的链子就会断掉，就再也找不到node3了。

```java
Node<T> curr=node2;
Node<T> nodeX=new Node<>(value);

nodeX.next=curr.next;//连接node3
curr.next=nodeX;//连接node2；
```

#### 删除

要删除第N+1个节点。

```java
Node<T> curr=nodeN;
curr.next=curr.next.next;
```



#### 总结

概念阐述我不是很在行，大家看严书应该都能轻松的理解含义。

具体实现，大家还是主要看下代码吧。测试用例在test目录下。

```java
package com.kenton.list;

import java.util.function.Consumer;

/**
 * arrayList 用数组实现，当size>=capacity时需要动态扩容，每次扩大1.5倍。
 * 如果要实现arraylist，implements AbstractList<T>即可，这样可以解耦合。
 * @param <T>
 */
public interface AbstractList<T> extends Iterable<T> {
     AbstractList<T> add(T data);
     T get(int index);
     int get(T value);
     AbstractList<T> insert(int index,T value);
     AbstractList<T> delete(int index);
     AbstractList<T> delete(T value);
     void forEach(Consumer<? super T> func);
     AbstractList<T> filter(Filter<T> f);
}

```



```java

package com.kenton.list;

import java.util.Iterator;
import java.util.function.Consumer;

public class List<T> implements AbstractList<T>{
    private final Node<T> head;
    private int size = 0;

    //生成头节点
    public List() {
        head = new Node<T>(null);
    }

    /**
     * add Template value to list
     *
     * @param data value
     * @return this
     */
    public List<T> add(T data) {
        Node<T> node = new Node<T>(data);
        size++;
        Node<T> curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = node;
        return this;
    }

    /**
     *  获取当前单链表长度
     * @return this.size
     */
    public int size() {
        return size;
    }

    /**
     *  根据index来获取元素
     * @param index 下标
     * @return 下标为index的元素
     */
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        int p = 0;
        Node<T> curr = head.next;
        T result=null;
        while (curr != null) {
            if (index == p++) {
                result= curr.data;
                break;
            }
            curr = curr.next;
        }
        return result;
    }

    /**
     * 获取元素的下标
     * @param value 元素值
     * @return 元素的下标
     */
    public int get(T value) {
        Node<T> curr = head.next;
        int p = 0;
        while (curr != null) {
            if (curr.data == null) {//处理当链表中有null时的空指针异常
                if (value == null) {
                    return p;
                }
            } else {
                if (curr.data.equals(value)) { //当T为自定义的对象时，需要实现hashcode和equals才能比较
                    return p;
                }
            }
            curr = curr.next;
            p++;
        }
        return -1;
    }

    /**
     *  插入元素
     * @param index 元素要插入的位置
     * @param value 元素值
     * @return this
     */
    public List<T> insert(int index, T value) {
        if (index > size + 1 || index < 0) {
            return this;
        }
        Node<T> node = new Node<>(value);
        Node<T> curr = head;
        size++;
        int p = -1; //索引指向头节点
        while (curr != null) {
            if (p == index - 1) {//指向要插入索引的上一个节点
                node.next = curr.next;
                curr.next = node;
            }
            curr = curr.next;
            p++;
        }
        return this;
    }

    /**
     * 删除值为value的节点
     * @param value 元素值
     * @return this
     */
    public List<T> delete(T value) {
        Node<T> curr = head;
        while (curr.next != null) {
            if (value == null && curr.next.data == null) { //处理list中存有null的情况
                curr.next = curr.next.next;
                size--;
                return this;
            }
            if (curr.next.data.equals(value)) {
                curr.next = curr.next.next;
                size--;
                return this;
            }
            curr = curr.next;
        }
        return this;
    }

    /**
     * 删除下标为index的元素
     * @param index 下标
     * @return this
     */
    public List<T> delete(int index) {
        if (index > size || index < 0) {
            return this;
        }
        int p = -1; //头节点
        Node<T> curr = head;
        while (curr != null) {
            if (p == index - 1) { //要删除索引的上一个节点
                curr.next = curr.next.next;
                size--;
                return this;
            }
            curr = curr.next;
        }
        return this;
    }

    /**
     * 迭代器 为了让list能使用增强for循环
     * @return  迭代器
     */
    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * for each list
     *
     * @param function handle function
     */
    public void forEach(Consumer<? super T> function) {
        Node<T> curr = head.next;
        while (curr != null) {
            function.accept(curr.data);
            curr = curr.next;
        }
    }

    /**
     * filter list by user
     *
     * @param f filter function
     * @return temp list
     */
    public List<T> filter(Filter<T> f) {
        Node<T> curr = head.next;
        List<T> tempList = new List<>();
        while (curr != null) {
            //filter nullptr
            if (curr.data == null) {
                curr = curr.next;
            }
            if (f.filter(curr.data)) {
                tempList.add(curr.data);
            }
            curr = curr.next;
        }
        return tempList;
    }

    /**
     *  单链表中的节点
     * @param <T>
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            next = null;
        }

    }

    /**
     * 迭代器
     */
    private class Itr implements Iterator<T> {
        private int cursor = 0;

        /**
         * 当游标小于单链表总长度时，证明还有元素尚未遍历
         * @return cursor < size;
         */
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * 当遍历到链表尾部时，继续使用itr.next时返回 null
         * @return 元素值
         */
        @Override
        public T next() {
            if (cursor >=size) {
                return null;
            }
            return get(cursor++);
        }
    }
}

```







