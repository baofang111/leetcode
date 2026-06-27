package hot.hot100V2;

import java.util.HashMap;
import java.util.Map;

/**
 * 146. LRU 缓存
 * <p>
 * 题目意思：让我们设定一个 LRU 缓存，注意我们需要 get put 需要 0（1）的平均时间复杂度
 * <p>
 * 题目解析：我们使用一个 双向链表，head - node - node - tail ，这样我们就可以 用空间换时间了
 *
 * @summary LRUCache
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/30 17:33:15
 */
public class LRUCacheV2 {

    private int capacity;
    private Map<Integer, ListNode> map;
    private ListNode head;
    private ListNode tail;


    public LRUCacheV2(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new ListNode(0, 0);
        tail = new ListNode(0, 0);

        head.next = tail;
        tail.pre = head;

    }

    /**
     * 获取值，map 中获取值之后，我们需要将这个 key 的 node ,放在前面，放置他被删除
     *
     * @param key
     * @return
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        ListNode node = map.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            ListNode node = map.get(key);
            node.value = value;
            moveToHead(node);
            map.put(key, node);
        } else {
            ListNode node = new ListNode(key, value);
            addToHead(node);
            map.put(key, node);
        }

        // 如果我们超出了大小范围，那么需要删除尾部元素
        if (map.size() > capacity) {
            ListNode node = removeTail();
            map.remove(node.key);
        }

    }

    /// ///////////////////// 链表操作相关 ////////////////////////


    private void moveToHead(ListNode head) {
        removeNode(head);
        addToHead(head);
    }

    private void removeNode(ListNode node) {
        ListNode pre = node.pre;
        ListNode next = node.next;

        pre.next = next;
        next.pre = pre;
    }

    /**
     * head - 1 - 2 - 3 - tail
     * head - node - 1 - 2 - 3 - tail
     *
     * @param node
     */
    private void addToHead(ListNode node) {
        ListNode first = head.next;

        node.next = first;
        node.pre = head;

        head.next = node;
        first.pre = node;
    }

    private ListNode removeTail() {
        ListNode deleteNode = tail.pre;
        removeNode(deleteNode);
        return deleteNode;
    }


    public static class ListNode {
        private int key;
        private int value;

        private ListNode pre;
        private ListNode next;

        public ListNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }


}
