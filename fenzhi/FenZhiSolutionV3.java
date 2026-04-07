package fenzhi;

import listNode.ListNode;
import treeNode.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * 分治 相关算法 练习
 *
 * @summary FenZhiSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/07 16:39:35
 */
public class FenZhiSolutionV3 {

    /**
     * 108. 将有序数组转换为二叉搜索树
     * <p>
     * 题目意思：给定一个 整数数组，nums, 这个数组是已经升序 排好序了，我们需要将它变成一个 二叉搜索树
     * 什么是二叉搜索树：就是右边一定比左边大
     * <p>
     * 题目解析：我们按照 nums 每次取中间位置，（ right + left ） / 2 这个就是他的root ,然后两边就是 left + right
     * 直接 DFS 构建即可
     *
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBST(nums, left, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, right);

        return root;
    }

    /**
     * 148. 排序链表
     * <p>
     * 题目意思：给定一个链表，我们要按照 升序的模式 给他排完序，然后返回
     * <p>
     * 题目解析：我们直接使用 归并排序
     * 不断的拆链表，拆成 left + right, 然后不断的 合并，合并的时候，就是合并有序链表的方法，到最后就能得到一个 完整的有序链表了
     * 所以该题的核心就是 寻找中间节点，然后不断的排序 + 归并
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = getMid(head);

        ListNode right = mid.next;
        // 一定要断开链表
        mid.next = null;

        ListNode l1 = sortList(head);
        ListNode l2 = sortList(right);

        return merge(l1, l2);
    }

    /**
     * 寻找链表 中间点
     *
     * @param head
     * @return
     */
    private ListNode getMid(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 合并两个有序链表
     *
     * @return
     */
    public ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        while (l1 != null || l2 != null) {
            int v1 = l1 == null ? Integer.MAX_VALUE : l1.val;
            int v2 = l2 == null ? Integer.MAX_VALUE : l2.val;

            if (v1 < v2) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        return dummy.next;
    }


    /**
     * 427. 建立四叉树
     * <p>
     * 题目意思：给定一个矩阵 grid，判断 如果他 全部一样的话，就是 四叉树中的 一个象限（左上，右上，左下，右下）
     * <p>
     * 题目解析：我们需要不断的拆分 四个象限，然后判断他是否是 里面完全一样的数据
     * 例如
     * 1100
     * 1100
     * 0000
     * 0000 这种就不是完全一样的，就需要再次拆，直到找到一个完全一样的，那么他就是我们要的 四象限中的一部分
     *
     */
    public Node construct(int[][] grid) {
        // 判断一个矩阵里面的数字是不是都一样的，一样的话，那么他就是四叉树的一个节点
        return construct(grid, 0, 0, grid.length);
    }

    public Node construct(int[][] grid, int x, int y, int length) {
        if (isSame(grid, x, y, length)) {
            // 叶子节点
            return new Node(grid[x][y] == 1, true);
        }

        int half = length / 2;

        Node topLeft = construct(grid, x, y, half);
        Node topRight = construct(grid, x, y + half, half);
        Node bottomLeft = construct(grid, x + half, y, half);
        Node bottomRight = construct(grid, x + half, y + half, half);

        return new Node(grid[x][y] == 1, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    private boolean isSame(int[][] grid, int x, int y, int length) {
        int val = grid[x][y];

        for (int i = x; i < x + length; i++) {
            for (int j = y; j < y + length; j++) {
                if (grid[i][j] != val) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 23. 合并 K 个升序链表
     * <p>
     * 题目意思：给定多个 有序链表，我们要给他 进行一个排序，合并成一个 链表
     * <p>
     * 题目解析：直接使用 归并排序即可
     *
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        return mergeKLists(lists, 0, lists.length - 1);
    }

    public ListNode mergeKLists(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }

        int mid = left + (right - left) / 2;
        ListNode l1 = mergeKLists(lists, left, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, right);

        return mergeListNode(l1, l2);
    }

    private ListNode mergeListNode(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }

        return dummy.next;
    }

    /**
     * 23. 合并 K 个升序链表
     *
     * BFS 队列版本
     *
     *
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        Deque<ListNode> deque = new ArrayDeque<>();
        for (ListNode node : lists) {
            if (node == null) {
                deque.offer(node);
            }
        }

        while (deque.size() > 1) {
            ListNode l1 = deque.poll();
            ListNode l2 = deque.poll();

            ListNode listNode = mergeListNode(l1, l2);
            deque.offer(listNode);
        }

        return deque.poll();
    }

}
