package listNode;

/**
 * 链表2
 *
 * @summary ListNodeSolution2
 * @author: bf
 * @Copyright (c) 2025/12/3, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/3 11:39
 */
public class ListNodeSolution2 {


    /**
     * 141 - 环形链表
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * <p>
     * 解法：使用快慢指针，判断有没有重复即可
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        // 快慢指针，判断是否有相交即可
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2. 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 该题的核心是 如果 4 + 6  = 10 的话，超过了一位数，那么我们需要将 1 往前设置，0放入当前位置节点

        ListNode res = new ListNode(0);
        ListNode current = res;

//        ListNode p1 = l1;
//        ListNode p2 = l2;
        int preValue = 0;

        // 开始遍历 l1 l2 ，一直到遍历完
        while (l1 != null || l2 != null) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;

            int sum = v1 + v2 + preValue;
            if (sum >= 10) {
                // 大于 10，比如 13，我们需要拿到 1 和 3 然后当前 val 放 3 下一个位置 + 1
                int i = sum & 10;
                current.next = new ListNode(i);
                // 要不断移动
                current = current.next;
                preValue = sum / 10;
            } else {
                // 小于10 直接添加即可
                current.next = new ListNode(sum);
                // 要不断移动
                current = current.next;
                // 添加完 要重置 preValue
                preValue = 0;
            }
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        // 可能存在 l1 l2 没有遍历完的情况
        if (preValue > 0) {
            // 需要添加上去
            res.next = new ListNode(preValue);
        }

        return res.next;
    }

    public static void main(String[] args) {
        System.out.println(10 / 10);
    }

}
