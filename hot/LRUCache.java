package hot;

import java.util.HashMap;
import java.util.Map;

/**
 * 146. LRU 缓存
 * <p>
 * 题目意思：我们设置一个 LRU 缓存，最晚使用的那个节点需要被删除，且 get put 必须以 O（1） 的平均时间复杂度
 * <p>
 * 题目意思：我们直接使用一个双向链表即可，同时存储 head + tail 首位节点，这样 就可以快递定位到节点，并且快速的对首位节点进行操作
 * <p>
 * 所以我们首先要定义一个 富含 next pre 的链表
 * 然后我们再需要设置一个 map，用来存储 具体的 key + node （ 这样，我们就可以快点进行 该位置的节点操作 ）
 *
 */
class LRUCache {

    int capacity;
    Map<Integer, ListNode> map;
    ListNode head;
    ListNode tail;

    /**
     * 构建 head -> <- tail
     * @param capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new ListNode(0, 0);
        tail = new ListNode(0, 0);

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
        moveToHead(listNode);
        return listNode.v;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            ListNode listNode = map.get(key);
            moveToHead(listNode);
            listNode.v = value;
        } else {
            ListNode listNode = new ListNode(key, value);
            addToHead(listNode);
            map.put(key, listNode);
        }

        // 超出数量 需要进行删除
        if (map.size() > capacity) {
            ListNode listNode = removeTail();
            map.remove(listNode.k);
        }
    }


    private void moveToHead(ListNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * head-1-2-3
     * head-node-1-2-3
     */
    private void addToHead(ListNode node) {
        ListNode first = head.next;

        // 将 node 插入到 head - first 中间
        node.pre = head;
        node.next = first;

        // 再讲 head 和 first 的指针进行修改
        head.next = node;
        first.pre = node;
    }

    private void removeNode(ListNode node) {
        ListNode pre = node.pre;
        ListNode next = node.next;

        pre.next = next;
        next.pre = pre;
    }

    private ListNode removeTail() {
        ListNode lastNode = tail.pre;
        removeNode(lastNode);
        return lastNode;
    }

    public static class ListNode {
        int k;
        int v;
        ListNode pre;
        ListNode next;

        ListNode(int k, int v) {
            this.k = k;
            this.v = v;
        }
    }

}