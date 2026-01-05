package listNode;

/**
 * 单 链表
 *
 * @summary ListNode
 * @author: bf
 * @Copyright (c) 2025/2/25, © 拜耳作物科学（中国）有限公司
 * @since: 2025/2/25 13:35
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}


