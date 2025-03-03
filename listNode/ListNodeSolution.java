package listNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表 - 相关 算法题
 *
 * @summary ListNodeSolution
 * @author: bf
 * @Copyright (c) 2025/2/25, © 拜耳作物科学（中国）有限公司
 * @since: 2025/2/25 13:46
 */
public class ListNodeSolution {

    /**
     * 21 - 合并两个有序链表
     * - https://leetcode.cn/problems/merge-two-sorted-lists/description/
     * - 思路：直接遍历 ，谁小放前面即可
     * [1,2,4]
     * [1,3,4]
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 使用 3 个指针
        ListNode head = new ListNode(-1);
        ListNode p = head;

        // 分别用另外两个指针 P1 P2 去循环 list1 list2
        ListNode p1 = list1;
        ListNode p2 = list2;

        // 遍历
        while (p1 != null && p2 != null) {
            // 判断大小
            if (p1.val < p2.val) {
                // 赋值并且指针往后移动
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }

        // 存在还没遍历完的情况
        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }

        // 在将他返回即可
        return head.next;
    }


    /**
     * 86: 分隔链表
     * - https://leetcode.cn/problems/partition-list/
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * <p>
     * 输入：head = [1,4,3,2,5,2], x = 3
     * 输出：[1,2,2,4,3,5]
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        // 同样 使用 3 个指针，进行链表分隔，然后在合并
        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-1);

        ListNode p1 = dummy1;
        ListNode p2 = dummy2;

        // 再创建 head 的指针
        ListNode p = head;

        // p 指针 遍历 链表
        while (p != null) {
            if (p.val < x) {
                // 将数值小的放入到 p1 中, 同时 P1 指针再往后移动
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }

            // p 指针往后移动，但是这里需要注意的就是，我们需要将 P.next 的指针掐断
            ListNode temp = p.next;
            p.next = null;
            p = temp;
        }

        // 再次拼接
        p1.next = dummy2.next;

        return dummy1.next;
    }

    /**
     * 合并 K 和 升序链表
     * <p>
     * -  给你一个链表数组，每个链表都已经按升序排列。
     * - 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * <p>
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * <p>
     * 解法1： 暴力求解，遍历所有数据，然后排序
     * 解法2： 合并链表 2 2 合并 ，那么他的时间复杂度 就是 0(NK)
     * --> 优化，两两 合并 时间复杂度 O（N * logK）
     * --> 思路
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        // 进行两两合并  1 2 3 4 --->  1 2 合并后 在和 3 4 合并之后 继续合并即可，然后判断 临界条件
        while (lists.length > 1) {
            // 使用一个临时数组进行
            List<ListNode> tempListNode = new ArrayList<>();
            // 一次走两步
            for (int i = 0; i < lists.length; i = i + 2) {
                ListNode list1 = lists[i];
                // 可能是单数的情况
                ListNode list2 = null;
                if (i + 1 < lists.length) {
                    list2 = lists[i + 1];
                }
                tempListNode.add(mergeTwoList(list1, list2));
            }


            // 注意 这里是个循环，2 2 合并之后 需要再次合并，直到全部变成 一个 链表即可
            lists = tempListNode.toArray(new ListNode[0]);
        }

        // 取出第一条即可
        return lists[0];
    }

    private ListNode mergeTwoList(ListNode list1, ListNode list2) {
        ListNode p1 = list1;
        ListNode p2 = list2;

        ListNode head = new ListNode(-1);
        ListNode p = head;

        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }

        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }

        return head.next;
    }


    /**
     * 876 - 链表的中点
     *
     *  - 一眼就是快慢指针
     *
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // 快慢指针 一直往前走
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * 141 - 环形链表
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos
     * 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
     * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
     *
     * 解法： 直接快慢指针，当如果有环形链表的话，快指针会无限循环，直到和 慢指针重合，如果不重合或者不循环，那么就不是环形链表
     *
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
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
     * 相交链表
     *
     * - 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     * - 图示两个链表在节点 c1 开始相交：
     *
     *  解法：
     *    直接使用两个指针。分别从 A B 的开始节点往前走，
     *
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;

        while (p1 != p2) {
            if (p1 == null) {
                p1 = headB;
            }else {
                p1 = p1.next;
            }
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }
        }

        return p1;
    }

}
