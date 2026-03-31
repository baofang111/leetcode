package hot.hot150;

import listNode.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 链表 相关算法 练习
 *
 * @summary ListNodeSolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/25, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/25 17:36
 */
public class ListNodeSolutionV3 {

    /**
     * 141. 环形链表
     * <p>
     * 题目意思：判断 该链表 是否是 环形
     * <p>
     * 题目解析：我们直接使用 快慢指针即可 ,因为只要有 环形链表，那么 快慢指针 肯定会相遇
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;

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
     * <p>
     * 题目意思：给定两个 链表，将他们进行相加，注意 如果值 > 10 需要 在下一位 进 1  123  922 -> 055
     * <p>
     * 题目解析：我们遍历两个 l1 l2 链表即可，然后创建一个新的链表，如果 值 大于 10 那么就存储一下 pre 的值
     * 这样 每次 相加值 val = p1.val + p2.val + pre 即可
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        ListNode p1 = l1;
        ListNode p2 = l2;

        int pre = 0;

        while (p1 != null || p2 != null) {
            int val1 = p1 == null ? 0 : p1.val;
            int val2 = p2 == null ? 0 : p2.val;

            int num = val1 + val2 + pre;
            if (num >= 10) {
                int val = num - 10;
                cur.next = new ListNode(val);
                pre = 1;
            } else {
                pre = 0;
                // 直接插入
                cur.next = new ListNode(num);
            }

            // 移动 cur 和 p1 p2 未知
            cur = cur.next;
            if (p1 != null) {
                p1 = p1.next;
            }
            if (p2 != null) {
                p2 = p2.next;
            }
        }

        if (pre > 0) {
            cur.next = new ListNode(pre);
        }

        return dummy.next;
    }

    /**
     * 21. 合并两个有序链表
     * <p>
     * 题目意思：将 两个有序的链表，再次按照顺序 进行 合并
     * <p>
     * 题目解析：按照题目意思，遍历，然后比较大小，然后合并即可，这题和 两个链表相加的业务逻辑差不多
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 根据 L1 L2 的大小 进行合并即可
        if (list2 == null) {
            return list1;
        }
        if (list1 == null) {
            return list2;
        }

        // 设置头结点和当前指针
        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        // 开始遍历
        while (list1 != null || list2 != null) {
            int val1 = list1 == null ? Integer.MAX_VALUE : list1.val;
            int val2 = list2 == null ? Integer.MAX_VALUE : list2.val;

            if (val1 < val2) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }

        return dummy.next;
    }


    /**
     * 138. 随机链表的复制
     * <p>
     * 题目意思：简单来说，就是 对 Node 链表进行一次 深拷贝，其中 Node 链表中 还有一个 random 的随机指向
     * <p>
     * 题目解析：深拷贝
     * 解法1: 我们使用 一个 Map, 记录下来，<老节点，新节点>，这样的话，我们第一遍遍历，将所有的节点全部放入到 map 当中，
     * 第二遍，直接遍历 放入 random 即可
     * <p>
     * 解法2：原地更改，减少了 一个 map，使用 A A' B B' 的这种形式
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();

        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        // 开始重新遍历，进行拼装，注意拼接的时候需要 萍姐  random + next
        cur = head;
        while (cur != null) {
            // 设置 next 和 random
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }

    /**
     * 92. 反转链表 II
     * <p>
     * 题目意思：给定一个链表，然后我们在 left - right 的位置上面进行 反转，其他位置不变
     * <p>
     * 题目解析：举个例子  12345678 我们现在要将 3456 进行翻转，得到 12 6543 78 那么我们很容易得到一个结论就是
     * 我们先 走到 left 的位置，也就是 3，这时候 pre = 2 start = 3
     * 然后 我们通过 right - left = step 得到我们需要反转的链表具体位置，将这块反转即可
     * <p>
     * 1：遍历 left 找到 start
     * 2: 计算 right - left = step 得到需要翻转的具体链表长度
     * 3：通过头插法，进行该位置的 链表反转
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        /*
            思路，我们先通过 left 移动到我们需要翻转的 前置位置 pre
            然后通过 pre 拿到需要翻转的 start 开始，然后通过 start.next = cur 来得到当前需要反转的点
            我们每次将 cur 使用头插法 反转过来即可
         */

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;

        // 找到 pre 的 位置
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        // 后续的位置 使用头插法进行反转
        ListNode start = pre.next;
        // 需要反转过来的元素的值，2-3-4 这时候的 cur 就是 3，然后我们给她变成 3-2-4
        ListNode cur = start.next;
        int step = right - left;

        for (int i = 0; i < step; i++) {
            // step1：2-3-4 先摘抄3，得到 3 2-4
            start.next = cur.next;
            // step2: 将 cur 3 链接 到 start 当中，得到 3-2-4
            cur.next = pre.next;
            // step3: 链接 pre 和 cur 的关联关系，得到 1-3-2-4
            pre.next = cur;

            // 移动 cur 位置，逐步遍历
            cur = start.next;
        }

        return dummy.next;
    }


    /**
     * 25. K 个一组翻转链表
     * <p>
     * 题目意思：一个链表 每 K 个一组进行反转，比如 K = 2 那么 1 2 3 4 5 6 7 的链表就会变成 2 1 4 3 6 5 7
     * <p>
     * 题目解析：我们分为几个步骤
     * step1: = K 我们进行反转
     * step2: <K 我们保存不动
     * step3: 对于反转之后的链表，我们需要进行拼接 举个例子
     * <p>
     * 1 2 3 4 5 6 7  我们 反转 2
     * 找到 1-2
     * 然后断开 2 - 3 的链接，同时我们需要一个参数 保存 3-4-5-6-7 的数据
     * 然后反转 1-2 ----> 2-1
     * 然后在进行拼接
     * 然后在进行递归推进 即可
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        /*
            核心就是 如果 = K 那么就反转
            如果 < k 那么就保持不变
         */
        if (head == null || k == 1) {
            return head;
        }

        // 创建虚拟头结点 + pre end 节点，用于后续的反转 ，pre - k - end
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        // 开始遍历 进行反转
        while (true) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }

            // 遍历完毕
            if (end == null) {
                break;
            }

            //  pre-1-2-3-4 --> pre-1-2 3-4 ---> pre-reverse(2-1) - 3-4 ---- 移动 pre end

            ListNode next = end.next;
            ListNode start = pre.next;

            // 一定要截断
            end.next = null;

            pre.next = reverse(start);

            // 再次拼接
            start.next = next;

            // 移动位置
            pre = start;
            end = start;
        }

        return dummy.next;
    }

    /**
     * 反转链表 使用头插法
     *
     * pre-1-2-3-4-5
     * 2-1 3-4-5
     *
     *
     * @param head
     * @return
     */
    public ListNode reverse(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;

        // 依旧头插法
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;

            // 移动位置
            pre = cur;
            cur = next;
        }

        return pre;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     *
     *  题目意思：很简单的 删除 倒数 K 个元素
     *
     *  题目解析：这题很简单，我们只需要先拿到 head 的长度，然后通过这个 length 去找到 倒数 K 个点
     *  然后 cur.next = cur.next.next  删除即可
     *
     *
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 该题目的目的 就是 寻找 倒数第 K 个点的前一个节点，然后 pre.next = pre,next.next 即可
        if (head == null || n == 0) {
            return head;
        }

        int length = 0;
        ListNode cur = head;
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        // 这个位置的前一个位置 就是我们要找的点
        int k = length - n;
        if (k == 0) {
            return head.next;
        }

        ListNode pre = head;
        for (int i = 1; i < k; i++) {
            pre = pre.next;
        }

        pre.next = pre.next.next;

        return head;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     *
     * 题目意思：删除链表中的重复元素 比如 1 223356 变成 1 5 6 ，只要是重复的 就进行删除
     *
     * 题目解析：我们不断的遍历 cur.val = cur.next.val 是否一样，一样就继续往下找，当不一样的时候，就讲值放入我们要的结果集当中
     *
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

       // 核心 就是判断 当前值 和 下一个 值 是否相等
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        ListNode cur = head;

        while (cur != null) {
            boolean same = false;
            while (cur.next != null && cur.val == cur.next.val) {
                // 是重复元素
                cur = cur.next;
                same = true;
            }

            if (same) {
                // 是重复元素，我们要删除这个 cur
                pre.next = cur.next;
            } else {
                // 不是重复元素
                pre = pre.next;
            }

            cur = cur.next;
        }

        return dummy.next;
    }


    /**
     * 61. 旋转链表
     *
     *  题目意思：给定一个 链表，我们将链表 向后移动 K 个位置
     *      12345 - 2 34512 length = 5 K = 2
     *
     *  题目解析：看到上面的题目意思，其实就相当于 使用一个 环形链表，然后  我们 找到 倒数 length - K 的点
     *  这个就是我们重新的 起点，然后 断开链表即可
     */
    public ListNode rotateRight(ListNode head, int k) {
        // 该题目的核心是 构建环形链表，然后寻找我们需要的值的 pre 位置
        if (head == null || k == 0) {
            return head;
        }

        // 寻找尾节点
        int length = 1;
        ListNode end = head;
        while (end.next != null) {
            length++;
            end = end.next;
        }

        // 构建环形链表
        end.next = head;

        k = k % length;
        int step = length - k - 1;
        ListNode cur = head;
        while (step > 0) {
            cur = cur.next;
            step--;
        }

        ListNode res = cur.next;

        // 断开环形链表
        cur.next = null;

        return res;
    }

    /**
     * 86. 分隔链表
     *
     * 题目意思：给定一个 链表 head ,和 一个 数字 X ，将 小于 X 的放前面，大于 X 的放后面
     *
     * 题目解析：我们遍历 创建两个链表，一个专门放 小于 x 的 元素 ，一个专门方 大于 X 的元素
     * 然后将 两个链表合并即可
     *
     * [1,4,3,2,5,2]  x=3
     * --->
     * [1,2,2,4,3,5]
     *
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null) {
            return head;
        }

        ListNode l1 = new ListNode();
        ListNode l2 = new ListNode();

        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode cur = head;
        while (cur != null) {
            if (cur.val < x) {
                p1.next = cur;
                p1 = p1.next;
            } else {
                p2.next = cur;
                p2 = p2.next;
            }
            cur = cur.next;
        }

        p2.next = null;

        // 合并链表
        p1.next = l2.next;

        return l1.next;
    }


    public static void main(String[] args) {
        System.out.println(12 % 10);
    }

}
