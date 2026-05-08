package hot.hot100V2;

import java.util.HashMap;
import java.util.Map;

/**
 * 146. LRU 缓存
 *
 * 题目意思：让我们设定一个 LRU 缓存，注意我们需要 get put 需要 0（1）的平均时间复杂度
 *
 * 题目解析：我们使用一个 双向链表，head - node - node - tail ，这样我们就可以 用空间换时间了
 *
 * @summary LRUCache
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/30 17:33:15
 */
public class LRUCache {

    public int capacity;
    public Map<Integer, ListNode> map;
    public ListNode head;
    public ListNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new ListNode(0, 0);
        tail = new ListNode(0, 0);

        // 简历指针关系
        head.next = tail;
        tail.pre = head;

    }

    /**
     * 获取值，map 中获取值之后，我们需要将这个 key 的 node ,放在前面，放置他被删除
     * @param key
     * @return
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        ListNode listNode = map.get(key);
        // 刚刚操作的节点，需要将节点移动到 头节点的位置
        moveToHead(listNode);
        return listNode.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // 包含
            ListNode node = map.get(key);
            node.value = value;
            map.put(key, node);
            moveToHead(node);
        } else {
            // 本身没有
            ListNode node = new ListNode(key, value);
            map.put(key, node);
            addToHead(node);
        }

        if (map.size() > capacity) {
            // 这里需要删除尾部元素
            ListNode node = removeTail();
            // 注意这里 map 里面的也一定要移除
            map.remove(node.key);
        }

    }

    //////////////////////// 链表操作相关 ////////////////////////

    private void moveToHead(ListNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 1-node-2
     * @param node
     */
    private void removeNode(ListNode node) {
        ListNode pre = node.pre;
        ListNode next = node.next;

        pre.next = next;
        next.pre = pre;
    }

    /**
     * 添加到 头节点
     *
     * head - 1 - 2 - 3 - tail
     * head - node - 1 - 2 - 3 - tail
     *
     * @param node
     */
    private void addToHead(ListNode node) {
        ListNode first = head.next;

        // 先添加 node 和其他节点的指针
        node.next = first;
        node.pre = head;

        // 再添加其他节点和 node 的指针
        head.next = node;
        first.pre = node;
    }

    /**
     * 移除尾部元素
     */
    private ListNode removeTail() {
        ListNode node = tail.pre;
        removeNode(node);
        return node;
    }


    public static class ListNode{

        public int key;
        public int value;

        public ListNode pre;
        public ListNode next;

        public ListNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }


}
