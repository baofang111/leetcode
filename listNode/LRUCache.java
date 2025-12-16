package listNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 146 - LRU 缓存
 *
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * 解法：最简单的 使用 HashMap + 双向链表
 * -- 为什么不用单链表？
 *  删除任意节点是 O(n)
 *  LRU 要 频繁移动节点
 * -- 为什么不用 Tree / Queue？
 *  时间复杂度 不满足 0（1）
 *
 *  解法：这题的核心就是 使用 HashMap + 双向链表，因为 LRU 当容量满的时候，会删除最久那个没有使用的
 *  所以这里我们就需要多几个操作，
 *  一个是 addToHead( 放入到头节点 )
 *  一个是 moveToHead( 移动到头节点 )
 *  一个是 removeTail( 删除尾节点，用来删除最久没使用的数据 )
 *  这样，只要我们取数据 或者 添加数据的时候，把该数据 移动到 头节点即可
 */
class LRUCache {

    private int capacity;

    private Map<Integer, LinkedNode> map;

    // 定义头尾节点
    private LinkedNode head, tail;



    /**
     *  以 正整数 作为容量 capacity 初始化 LRU 缓存
     * @param capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;

        map = new HashMap<>();

        head = new LinkedNode(0, 0);
        tail = new LinkedNode(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    /**
     * 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
     * @param key
     * @return
     */
    public int get(int key) {
        LinkedNode node = map.get(key);
        if (node == null) {
            return -1;
        }
        // 我们要移动到头部位置
        moveToHead(node);
        return node.value;
    }

    /**
     * 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        // 先判断该 LRU 缓存中有没有的话 使用 addToHead ，如果有的话，使用 moveToHead
        LinkedNode node = map.get(key);
        if (node == null) {
            node = new LinkedNode(key, value);
            map.put(key, node);
            addToHead(node);
        } else {
            node.value = value;
            moveToHead(node);
        }

        // 如果超过 capacity 那么久需要删除尾节点
        if (map.size() > capacity) {
            LinkedNode tailNode = removeTail();
            map.remove(tailNode.key);
        }
    }

    /**
     * 添加到头部节点
     * head - 1 - 2 - 3 - tail
     * --->
     * head - node - 1 - 2 - 3 - tail
     *
     * @param node
     */
    private void addToHead(LinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    /**
     * 移动到头部节点
     * @param node
     */
    private void moveToHead(LinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void removeNode(LinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * 删除尾部节点
     * @return
     */
    private LinkedNode removeTail() {
        LinkedNode node = tail.prev;
        removeNode(node);
        return node;
    }


    public static class LinkedNode {

        int key;
        int value;
        LinkedNode prev;
        LinkedNode next;

        LinkedNode(int k, int v) {
            key = k;
            value = v;
        }
    }

}