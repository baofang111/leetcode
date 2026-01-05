package fenzhi;

import listNode.ListNode;
import treeNode.TreeNode;

/**
 * 分治 相关算法
 *
 * @summary FenZhiSolution
 * @author: bf
 * @Copyright (c) 2026/1/4, © 拜耳作物科学（中国）有限公司
 * @since: 2026/1/4 11:18
 */
public class FenZhiSolution {


    /**
     * 108. 将有序数组转换为二叉搜索树
     * <p>
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
     * <p>
     * 解法思路：因为已经排序后了，所以我们直接 (end - start) / 2 就可以找到 root 节点，然后开始循环
     * ，循环的时候 不断的 对 左右节点 进行 获取即可
     * <p>
     * 升序，天然满足 中序遍历
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTDfs(nums, 0, nums.length - 1);
    }


    private TreeNode sortedArrayToBSTDfs(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        // 这个因为有后半部分 1-2-3-4-5 --> (5-3)/2 = 1，但是这并不是我们 4 的位置，所以我们需要 1 + start(3) 才 = 4
        int mid = start + (end - start) / 2;
        // 构建根节点
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBSTDfs(nums, start, mid - 1);
        root.right = sortedArrayToBSTDfs(nums, mid + 1, end);

        return root;
    }


    /**
     * 148. 排序链表
     * <p>
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     * <p>
     * 解法思路：
     * 因为要求 时间复杂度 O(n log n)，空间复杂度尽量 O(1)
     * 1：所以这里不能使用 暴力求解，转数组 再排序
     * 2：不能用 冒泡 + 插入排序，时间复杂度 O(n²)
     * <p>
     * 所以这里使用 并归排序
     * <p>
     * 所以解题思路就是：
     * - 1：找中点 （ 使用快慢指针 ），注意需要断开
     * - 2：左右两边分别进行排序
     * - 3：左右两边进行合并
     * <p>
     * 注意： 这里的递归结束条件  一定要是 head == null || head.next == null，因为 并归排序中必须要保证一个点，就是，每一层，子问题必须减少，
     * 当你链表长度 = 1 的时候，也就是 此时 head 是有值的，那么直接返回，就行
     * 但是当你 只判断 head == null 的话，head 长度只有一个的时候，还会往下走，然后就会进入 无限循环
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // step1: 找到中点，然后我们，分别 拿到 left + right ，进行排序，然后合并就行，这就是 并归排序
        ListNode mid = findMid(head);
        ListNode right = mid.next;
        // 这里一定要断开
        mid.next = null;

        // step2: 分别对左右两边进行排序
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(right);

        return merge(l1, l2);
    }

    /**
     * 使用快慢指针 找到中点
     *
     * @return
     */
    public ListNode findMid(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * 合并两个有序链表
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode merge(ListNode l1, ListNode l2) {
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

        // 存在没结束的情况
        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }

        return dummy.next;
    }

    /**
     * 427. 建立四叉树
     * <p>
     * 注意：四叉树拆的不是具体的值，而是拆成 四个区域, 所以这题的思路就出来了
     * <p>
     * - 1：先判断 int[][] grid 是否里面的值 全部一样。如果全部一样，那么 isLeaf = true, 否则 isLeaf = false, 且需要查费4快
     * - 2：拆分的时候 我们以 length / 2 = half 为界， 然后分别分治 添加 上下左右即可
     *
     * @param grid
     * @return
     */
    public Node construct(int[][] grid) {
        return buildNode(grid, 0, 0, grid.length);
    }

    private Node buildNode(int[][] grid, int x, int y, int len) {
        if (isSame(grid, x, y, len)) {
            return new Node(grid[x][y] == 1, true);
        }

        // 寻找 中心 mid 点位
        int half = len / 2;

        // 开始分治 构建 四叉树的 上下左右
        Node topLeft = buildNode(grid, x, y, half);
        Node topRight = buildNode(grid, x, y + half, half);
        Node bottomLeft = buildNode(grid, x + half, y, half);
        Node bottomRight = buildNode(grid, x + half, y + half, half);

        // 开始组合 构建
        return new Node(true, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    /**
     * 判断 grid 里面的值 是否全部相同
     *
     * @param grid 源数据
     * @param x    x 坐标
     * @param y    y 坐标
     * @param len  x y 分别距离 len 的距离
     * @return grid 是否相同 的值
     */
    private boolean isSame(int[][] grid, int x, int y, int len) {
        int value = grid[x][y];
        for (int i = x; i < x + len; i++) {
            for (int j = y; j < y + len; j++) {
                if (grid[i][j] != value) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 23. 合并 K 个升序链表
     * <p>
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * <p>
     * 解法思路：题目中的一个隐藏 条件是 lists 中的每一个链表都是 升序的，这样就很容易相出来，
     * 我们遍历 lists 进行 两两排序并合并，直到结束
     * <p>
     * 典型的 归并排序， 和 148 排序链表 写法基本一模一样
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        return mergeKLists(lists, 0, lists.length - 1);
    }


    private ListNode mergeKLists(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }

        int mid = left + (right - left) / 2;
        ListNode l1 = mergeKLists(lists, left, mid);
        ListNode l2 = mergeKLists(lists, mid + 1, right);

        return mergeListNode(l1, l2);
    }

    private ListNode mergeListNode(ListNode l1, ListNode l2) {
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
            // 当前指针一定要往下走
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

}
