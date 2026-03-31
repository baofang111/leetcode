package listNode;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU 练习
 *
 * 题目意思: 创建一个简单的 LRU 算法，保证 get 和 put 都是 时间复杂度 O（1） 运行
 *
 * 题目解析：时间复杂度 O（1）,我们很容易就想到了用 空间换时间，Map + 双向链表 进行操作
 *  因为双向链表当中 有 head + tail ,这样我们在操作元素 删除元素的是还好，直接操作 头 or 尾部 就能满足我们的题目要求
 *
 *
 *
 * @summary LRUCacheV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/31 20:09:49
 */
public class LRUCacheV3 {

    private int capacity;

    private Map<Integer, LinkedNode> map;

    private LinkedNode head, tail;

    /**
     * 定义初始 容量 capacity
     * @param capacity
     */
    public LRUCacheV3(int capacity) {
        this.capacity = capacity;

        map = new HashMap<>(capacity);

        head = new LinkedNode(0, 0);
        tail = new LinkedNode(0,0 );

        head.next = tail;
        tail.prev = head;

    }

    /**
     * 存在返回值
     * 不存在 返回 -1
     *
     * 时间复杂度 O（1）
     *
     * @param key
     * @return
     */
    public int get(int key) {
        if (map.containsKey(key)) {
            LinkedNode val = map.get(key);
            moveToHead(val);
            return val.value;
        } else {
            return -1;
        }
    }

    /**
     * 如果存在 变更值
     * 如果不存在，直接插入
     *
     * 注意这里 如果 容量超过了 capacity ，那么就 逐出最久没使用的 值
     *
     * 时间复杂度 O（1）
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        LinkedNode node = map.get(key);
        if (node == null) {
            node = new LinkedNode(key, value);
            map.put(key, node);
            addHead(node);
        } else {
            // 变更至
            node.value = value;
            moveToHead(node);
        }

        // 判断是否超出容量
        if (map.size() > capacity) {
            LinkedNode tail = removeTail();
            map.remove(tail.key);
        }
    }

    /**
     * 这里是核心，我们需要几个方法
     * addHead
     * moveToHead
     * removeNode
     * removeTail
     */

    /**
     * head - 1 -2 -3 - 4
     *
     * head - node - 1 - 2 - 3 - 4
     *
     */
    private void addHead(LinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next = node;
        head.next.prev = node;
    }

    /**
     * 先移动 在放到头部
     * @param node
     */
    private void moveToHead(LinkedNode node) {
        removeNode(node);
        addHead(node);
    }

    /**
     * head - 1-2-3-4-5
     *
     * head - 1-2-4-5
     */
    private void removeNode(LinkedNode node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private LinkedNode removeTail() {
        LinkedNode prev = tail.prev;
        removeNode(prev);
        return prev;
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
