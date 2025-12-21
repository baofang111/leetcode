package listNode;

import javax.sound.midi.MidiFileFormat;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 138 - 随机链表的复制
     * <p>
     * 解法1：使用 Map 记录具体的 位置+节点，遍历完成之后，再次遍历一次 添加 random 即可
     * 解法2：原地更改，使用 原节点 + 新节点 + 原节点 + 新节点 的方式，先拼接 再删除
     * 原地解法的核心在于 A + A' + B + B' + C + C'
     * 我已 A.random = C 来获取 A' 的 random
     * A.random = C ---> A.next.random = A.random.next 这样就可以找到 A'的 random 的值
     * 然后我们 在 删除 原有节点即可
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        // 解法2 原地更改，先拼接 再 删除
        if (head == null) {
            return null;
        }

        Node p = head;

        // step1: 原地拼接 A B -> A A' B B'
        while (p != null) {
            Node copy = new Node(p.val);
            copy.next = p.next;
            p.next = copy;
            p = copy.next;
        }

        // step2: 处理 random
        p = head;
        while (p != null) {
            Node random = p.random;
            if (random != null) {
                p.next.random = p.random.next;
            }
            // 跨一步 遍历
            p = p.next.next;
        }

        // step3: 删除 多余节点 A A' B B' --> A' B'
        p = head;
        Node newHead = head.next;
        while (p != null) {
            // 还原 p 链表
            Node copy = p.next;
            p.next = copy.next;

            if (copy.next != null) {
                // 链接新的链表
                copy.next = copy.next.next;
            }
            p = p.next;
        }

        return newHead;
    }

    /**
     * 使用 解法1
     * 时间 空间 复杂度更高
     *
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {
        // 使用第一种方式， Map 存数据
        if (head == null) {
            return null;
        }

        Node newNode = new Node(0);
        Node current = newNode;
        Map<Node, Node> map = new HashMap<>();

        // 创建指针
        Node p = head;
        while (p != null) {
            Node node = new Node(p.val);
            current.next = node;
            map.put(p, node);

            p = p.next;
            current = current.next;
        }

        // 再次遍历
        current = newNode.next;
        p = head;
        while (p != null) {
            // 存在 为空的情况
            current.random = p.random == null ? null : map.get(p.random);
            p = p.next;
            current = current.next;
        }

        return newNode.next;
    }

    /**
     * 92 - 反转链表 Ⅱ
     * 输入：head = [1,2,3,4,5], left = 2, right = 4
     * 输出：[1,4,3,2,5]
     * <p>
     * 解法：按照我们的常规思维，我们分段进行翻转即可
     * 1 2 3 4 5
     * 1->4
     * 2->5
     * 234 -> 432
     * 1: 所以解题思路我们就很容易结构出来，先找到 left 的前一个点位 pre = left - 1
     * 2: 然后 反转 left right
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 解法，我们找到 left 之前的数据 pre, 然后将 pre 作为头，left - right 之间的数据，使用 头插法翻转 即可实现要求
        if (head == null || left == right) {
            return head;
        }

        // 使用 虚拟头结点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;

        // step1: 找到 pre, 找到 left 的前一个节点，这个要从 index = 1 开始
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        // step2: 使用头插法进行翻转

        // 头插法 - 开始节点
        ListNode start = pre.next;
        // 头插法 - 当前节点
        ListNode cur = start.next;
        // 需要翻转的步数
        int step = right - left;
        // 开始翻转
        for (int i = 0; i < step; i++) {
            // 1 - 摘除当前 cur 节点
            start.next = cur.next;
            // 2 - 将当前 cur 节点添加到 头后面
            cur.next = pre.next;
            // 3 - 将 cur 再接入到 pre 链表当中
            pre.next = cur;
            // 4 - 移动 cur 节点
            cur = start.next;
        }

        return dummy.next;
    }

    /*
    头插法

    🔍一步一步理解指针变化

    初始状态（例子：1→2→3→4→5 翻 2~4）

    dummy → 1(pre) → 2(start) → 3(cur) → 4 → 5

    第 1 步：摘掉 cur
    start.next = cur.next;


    变成：

    dummy → 1(pre) → 2(start) → 4 → 5
    cur → 3  (被摘出来了)

    第 2 步：cur 插回 pre 后面

    👇这句的含义就是让 cur 去接住原来 pre.next 的链（也就是 start）

    cur.next = pre.next;


    此时变成：

    cur → 3 → 2(start) → 4 → 5

    第 3 步：pre 指向 cur
    pre.next = cur;


    最终结构：

    dummy → 1(pre) → 3(cur) → 2(start) → 4 → 5


    💡是不是达到了预期：
    3 被插入了 1 的后面
     */


    /**
     * 25. K 个一组翻转链表
     *
     * 解法：和 92 翻转链表其实有一点点像，我们 使用头插法 + 虚拟头结点 + 分段处理 即可
     * 这题的难点就在于 头插法的翻转 还有 如何分段处理的 临界条件当中
     *
     * step1: 每个 K 节点 进行 头插法翻转
     * step2: 不足 K 的长度 保持原样
     * step3: 返回反转后的 表头
     *
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 该题的核心就是 我们 K 个 K 个进行链表翻转，当不足 K 的时候，不需要翻转，直接返回
        if (head == null || k <= 1) {
            return head;
        }

        // 创建虚拟头结点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // 创建两个节点 pre 和 end 节点，用于链表的翻转
        ListNode pre = dummy;
        ListNode end = dummy;

        // 开始进行翻转
        while (true) {
            // 找到一直到 K 的 end 节点
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            // end = null 了 代表翻转到了结束了，直接返回
            if (end == null) {
                break;
            }

            // 我们要将 下一阶段 的数据 保留下来
            ListNode next = end.next;
            ListNode start = pre.next;
            // 一定要 链接进行折断，防止影响到后续的链表
            end.next = null;

            // 翻转后的 链表 放入到 开始节点之后，进行拼接
            pre.next = reverse(start);
            // 将 翻转后的 链表 最后一个阶段 链接到 下一个链表当中 1-2-3 4-5-6 ---> 1 -> 4-5-6 变成这种
            start.next = next;

            pre = start;
            end = pre;

        }
        return dummy.next;
    }

    /**
     * 翻转链表
     * 1 - 2 - 3
     * ----
     * 1 - null 2 - 3
     * ----
     * 2 - 1 - null 3
     * ----
     * 3 - 2 -1
     *
     *
     * @param head
     * @return
     */
    public ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 解法：
     * 很简单的逻辑，就是 我们要找到  n 的 前一个节点（pre） 和 后一个节点(next)
     * 然后 将 pre.next = next 即可
     * 这个逻辑错了，这个是删除 第 N 个节点，而并不是倒数 第几个节点
     *
     * 正确解法： 删除倒数第 N 个，即：要找到 倒数第 N+1 个 节点，这样才能拿到它的 next 去删除。
     *
     * 创建虚拟头结点 dummy，指向 head（避免 head 被删导致问题）
     * fast 指针先走 n 步
     * slow 和 fast 同时走，直到 fast 到链表末尾
     * 此时 slow 指向的是 倒数第 n+1 个节点
     * 删除 slow.next
     * 返回 dummy.next
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        // step1: 先让 fast 先走 n+ 步奏，让 slow 指向要删除的前一个几点
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // 删除 slow.next
        slow.next = slow.next.next;

        return dummy.next;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     *
     * 输入：head = [1,2,3,3,4,4,5]
     * 输出：[1,2,5]
     *
     * 解法：注意 该题 是已经排好序的，如果 我们便利的时候 遇到 重复的，我们就需要找到 该重复的，那么就不断遍历，找到
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 该题思路很简单，就是我们通过不断的遍历，如果重复的就丢弃，非重复的就添加
        if (head == null) {
            return null;
        }

        // 创建虚拟头结点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        // 开始遍历
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                // 重复的需要剔除，直到找到不重复的为止
                int val = cur.next.val;
                // 不断的遍历
                while (cur.next != null && val == cur.next.val) {
                    // 注意 跳过的话，一定是要 cur.next 往后面跳
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }

        return dummy.next;
    }

    /**
     * 61. 旋转链表
     * 输入：head = [1,2,3,4,5], k = 2
     * 输出：[4,5,1,2,3]
     *
     * 思路：我们找到 倒数 第 K 个节点 ，然后将他做头头，在和 原来的头进行拼接即可
     * 所以这题的解法也显而易见了
     * step1: 计算 head 的长度
     * step2: length - k = 等于我们要找的节点
     * step3: 开始拼接
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        // 找到 倒数 K 个点 我们就能实现我们的需求
        if (head == null || k == 0) {
            return null;
        }

        // step1: 计算 head 的总长度
        ListNode cur = head;
        int length = 1;
        while (cur.next != null) {
            cur = cur.next;
            length++;
        }

        // step2: 构建成环，这点非常非常重要
        cur.next = head;

        // step2: 计算我们的新的尾部节点 1-2-3-4-5 --- 4-5 1-2-3 我们要找到这个 3
        k = k % length;
        int steps = length - k;
        while (steps > 0) {
            cur = cur.next;
            steps--;
        }

        // step3: 到这边的话，变成了 cur=3-4-5-1-2-3-4-5... 所以这里直接获取到 cur.next 就是我们需要的结果，但是需要断开环形链表
        ListNode newNode = cur.next;
        // 断开链表
        cur.next = null;

        return newNode;
    }

    /**
     * 86 - 分隔链表
     *  给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     *
     *  输入：head = [1,4,3,2,5,2], x = 3
     *  输出：[1,2,2,4,3,5]
     *
     *  解法：我们使用两个虚拟头结点，分别存储 小于 X 和 大于 X 的，然后 将这两个进行合并即可
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0);

        ListNode p = head;

        ListNode d1 = dummy1;
        ListNode d2 = dummy2;

        while (p != null) {
            int val = p.val;
            if (val < x) {
                d1.next = p;
                d1 = d1.next;
            } else {
                d2.next = p;
                d2 = d2.next;
            }

            // 指针往后移动，但是这里我们需要注意的是，我们要把指针掐断
            ListNode temp = p.next;
            // 这里不能使用 temp.next = null, 因为会造成后续的链表丢失
            p.next = null;
            p = temp;
        }

        // 合并两个链表
        d1.next = dummy2.next;

        return dummy1.next;
    }

    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     *
     * 解法：很简单，直接遍历，比大小即可，而且因为两个本来就是有序的数组
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode result = new ListNode(0);
        ListNode current = result;
        ListNode p1 = list1;
        ListNode p2 = list2;

        while (p1 != null || p2 != null) {
            int v1 = p1 == null ? Integer.MAX_VALUE : p1.val;
            int v2 = p2 == null ? Integer.MAX_VALUE : p2.val;

            if (v1 < v2) {
                current.next = p1;
                p1 = p1.next;
            } else {
                current.next = p2;
                p2 = p2.next;
            }
            current = current.next;
        }

        return result.next;
    }


    public static void main(String[] args) {
        System.out.println(10 / 10);
    }

}
