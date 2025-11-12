package listNode;

/**
 * 单链表相关操作
 *
 * @summary ListNodeTest
 * @author: bf
 * @Copyright (c) 2025/11/7, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/7 09:40
 */
public class ListNodeTest {

    /**
     * 输入一个数组，转换为一条单链表
     *
     * @param arr
     * @return
     */
    ListNode createLinkedList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
        return head;
    }

    /**
     * 头部插入
     */
    private void insertHead(int val) {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        ListNode newNode = new ListNode(val);

        newNode.next = head;

        head = newNode;
    }


    /**
     * 尾部插入
     */
    private void insertTail(int val) {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        ListNode newNode = new ListNode(val);

        // 获取到尾部点位
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
        }

        p.next = newNode;
    }

    /**
     * 中间插入
     */
    private void insert(int index, int val) {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        ListNode newNode = new ListNode(val);
        ListNode p = head;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }

        // P 就是我们要插入的 前面那个节点
        newNode.next = p.next;
        p.next = newNode;
    }

    /**
     * 头部删除
     */
    private void removeHead() {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        head = head.next;
    }

    /**
     * 尾部删除
     */
    private void removeTail() {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        // 获取倒数第二个节点
        ListNode p = head;
        while (p.next.next != null) {
            p = p.next;
        }

        // 将 倒数第二个节点 后面链接设置成空
        p.next = null;
    }

    /**
     * 中间删除
     */
    private void remove(int index) {
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});

        // 获取第 index 个节点
        ListNode p = head;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }

        // 现在要删除该节点即可
        p.next = p.next.next;

    }

}
