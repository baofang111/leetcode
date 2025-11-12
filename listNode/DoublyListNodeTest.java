package listNode;

/**
 * 双链表测试
 *
 * @summary DoublyListNodeTest
 * @author: bf
 * @Copyright (c) 2025/11/10, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/10 14:54
 */
public class DoublyListNodeTest {

    DoublyListNode createDoublyLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        DoublyListNode head = new DoublyListNode(arr[0]);
        DoublyListNode cur = head;
        // for 循环迭代创建双链表
        for (int i = 1; i < arr.length; i++) {
            DoublyListNode newNode = new DoublyListNode(arr[i]);
            cur.next = newNode;
            newNode.prev = cur;
            cur = cur.next;
        }
        return head;
    }

    /**
     * 头部插入
     */
    private void insertHead(int val) {
        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        DoublyListNode newHead = new DoublyListNode(val);

        newHead.next = head;
        head.prev = newHead;
        head = newHead;

    }


    /**
     * 尾部插入
     */
    private void insertTail(int val) {
        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        // 找到尾部节点
        DoublyListNode p = head;
        while (p.next != null) {
            p = p.next;
        }

        // 尾部插入元素
        DoublyListNode newTail = new DoublyListNode(val);

        p.next = newTail;
        newTail.prev = p;

        // 更新节点引用
        p = newTail;
    }

    /**
     * 中间插入
     */
    private void insert(int index, int val) {
        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        // 中间插入，我们需要 找到 该 index 前一个点位
        DoublyListNode p = head;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }

        DoublyListNode newNode = new DoublyListNode(val);
        // 将 newNode 放入
        newNode.next = p.next;
        newNode.prev = p;

        // 还需要把该 节点 放入进去
        p.next.prev = newNode;
        p.next = newNode;
    }

    /**
     * 头部删除
     */
    private void removeHead() {
        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        DoublyListNode toDelete = head;
        head = head.next;
        head.prev = null;

        // 设置成空
        toDelete.next = null;
    }

    /**
     * 尾部删除
     */
    private void removeTail() {
        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        DoublyListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }

        // 断开链接
        tail.prev.next = null;
        tail.prev = null;

    }

    /**
     * 中间删除
     */
    private void remove(int index) {

        DoublyListNode head = createDoublyLinkedList(new int[]{1, 2, 3, 4, 5});

        // 中间插入，我们需要 找到 该 index 前一个点位
        DoublyListNode p = head;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }

        // p 节点的下一个节点
        DoublyListNode toDelete = p.next;

        p.next = toDelete.next;
        toDelete.next.prev = p;

        // 删除节点 全部设置成空
        toDelete.next = null;
        toDelete.prev = null;

    }

}
