package hot.hot100V2;

import listNode.ListNode;
import treeNode.TreeNode;

import java.util.*;

/**
 * hot 100 再次练习
 *
 * @summary Hot100V2
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/05/10 09:29:48
 */
public class Hot100V2 {


    /**
     * 3. 无重复字符的最长子串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int length = s.length();

        int left = 0;
        int ans = 0;

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // TODO 这里需要使用 left 进行位置记录
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }

            map.put(c, i);

            ans = Math.max(ans, i - left + 1);
        }

        return ans;
    }

    /**
     * 560. 和为 K 的子数组
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int length = nums.length;
        int sum = 0;
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        // 一定要初始化
        map.put(0, 1);

        for (int i = 0; i < length; i++) {
            sum += nums[i];

            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    /**
     * 239. 滑动窗口最大值
     * <p>
     * 使用一个最大单调栈来✅该题
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int length = nums.length;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int index = 0;
        int[] result = new int[length - k + 1];

        for (int i = 0; i < length; i++) {
            int curNum = nums[i];
            while (!stack.isEmpty() && curNum > nums[stack.peekLast()]) {
                stack.pollLast();
            }

            stack.offerLast(i);

            // 查询有没有超出窗口
            int leftWindow = i - k + 1;
            while (leftWindow > stack.peekFirst()) {
                stack.pollFirst();
            }

            // 达到窗口长度，我们添加结果值
            if (i >= k - 1) {
                result[index++] = nums[stack.peekFirst()];
            }
        }

        return result;
    }

    /**
     * 76. 最小覆盖子串
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        if (tLength > sLength) {
            return "";
        }

        // 需要两个窗口
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 窗口判断需要的 参数
        int valid = 0;
        int left = 0;

        int start = 0;
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < sLength; i++) {
            char c = s.charAt(i);
            window.put(c, window.getOrDefault(c, 0) + 1);

            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 这里已经寻找到了我们要的结果值，在里面缩小窗口继续寻找
            while (valid == need.size()) {
                if (i - left + 1 < length) {
                    start = left;
                    length = i - left + 1;
                }

                char d = s.charAt(left);
                left++;

                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return length == Integer.MAX_VALUE ? "" : s.substring(start, start + length);
    }

    /**
     * 56. 合并区间
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int length = intervals.length;
        if (length == 0) {
            return intervals;
        }

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        ArrayList<int[]> result = new ArrayList<>();

        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < length; i++) {
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            if (nextStart > end) {
                // 可以进行合并
                result.add(new int[]{start, end});

                start = nextStart;
                end = nextEnd;
            } else {
                // 有交叉
                end = Math.max(end, nextEnd);
            }
        }

        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
    }

    /**
     * 25. K 个一组翻转链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        while (end != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }

            // 开始旋转 拼接
            ListNode next = end.next;
            end.next = null;
            ListNode start = pre.next;

            pre.next = reverse(start);
            start.next = next;

            pre = start;
            end = start;
        }

        return dummy.next;
    }

    private ListNode reverse(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }


    /**
     * 148. 排序链表
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = getMid(head);
        ListNode next = mid.next;
        mid.next = null;
        ListNode start = head;

        ListNode l1 = sortList(start);
        ListNode l2 = sortList(next);

        return mergeListNode(l1, l2);
    }

    private ListNode getMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    private ListNode mergeListNode(ListNode node1, ListNode node2) {
        ListNode l1 = node1;
        ListNode l2 = node2;

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
     * 543. 二叉树的直径
     *
     * @param root
     * @return
     */
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        diameterOfBinaryTreeDFS(root);
        return max;
    }

    private int diameterOfBinaryTreeDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = diameterOfBinaryTreeDFS(root.left);
        int right = diameterOfBinaryTreeDFS(root.right);

        // 拐点
        max = Math.max(max, left + right);

        // 选择点
        return 1 + Math.max(left, right);
    }


    /**
     * 114. 二叉树展开为链表
     *
     * @param root
     */
    TreeNode pre = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        if (pre != null) {
            pre.right = root;
            pre.left = null;
        }

        pre = root;

        TreeNode left = root.left;
        TreeNode right = root.right;

        flatten(left);
        flatten(right);
    }

    /**
     * 437. 路径总和 III
     * <p>
     * 找出所有路径和 = targetSum 的数据
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        return pathSumDFS(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    // TODO 这里一定要是 long 类型去接收
    public int pathSumDFS(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        if (root.val == targetSum) {
            count++;
        }

        count += pathSumDFS(root.left, targetSum - root.val);
        count += pathSumDFS(root.right, targetSum - root.val);

        return count;
    }

    /**
     * 124. 二叉树中的最大路径和
     *
     * @param root
     * @return
     */
    int max2 = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathSumDFS(root);
        return max2;
    }

    public int maxPathSumDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 寻找左右两个的最大路径和,注意需要和 0 进行一下比较
        int leftPart = Math.max(0, maxPathSumDFS(root.left));
        int rightPart = Math.max(0, maxPathSumDFS(root.right));

        // 拐点
        max2 = Math.max(max2, root.val + leftPart + rightPart);

        // 选择点
        return root.val + Math.max(leftPart, rightPart);
    }

    /**
     * 39. 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        combinationSumDFS(candidates, 0, 0, target, new ArrayList<>(), result);
        return result;
    }

    public void combinationSumDFS(int[] candidates,
                                  int index,
                                  int total,
                                  int target,
                                  List<Integer> path,
                                  List<List<Integer>> result) {
        if (total == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (total > target) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            int num = candidates[i];
            if (num > target) {
                continue;
            }

            total += num;
            path.add(num);

            // TODO 注意这里是 i
            combinationSumDFS(candidates, i, total, target, path, result);

            path.removeLast();
            total -= num;
        }
    }

    /**
     * 131. 分割回文串
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        partitionDFS(s, 0, new ArrayList<>(), result);
        return result;
    }

    public void partitionDFS(String s, int index, List<String> path, List<List<String>> result) {
        if (index == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            // TODO 这里是 index - i + 1
            String word = s.substring(index, i + 1);
            if (isHW(word)) {
                path.add(word);

                partitionDFS(s, i + 1, path, result);

                path.removeLast();
            }
        }
    }

    private boolean isHW(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                // 最小值肯定在这边
                left = mid + 1;
            } else {
                // 慢慢寻找
                right = mid;
            }
        }

        return nums[left];
    }

    /**
     * 4. 寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if (n > m) {
            // 保证使用最小边进行操作
            return findMedianSortedArrays(nums2, nums1);
        }

        int left = 0;
        int right = n;

        // 我们需要寻找 L1 R1 L2 R2 四个值
        while (left <= right) {
            int mid1 = left + (right - left) / 2;
            // 保证左边多一个
            int mid2 = (n + m + 1) / 2 - mid1;

            int L1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int R1 = mid1 == n ? Integer.MAX_VALUE : nums1[mid1];
            int L2 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int R2 = mid2 == m ? Integer.MAX_VALUE : nums2[mid2];

            if (L1 <= R2 && L2 <= R1) {
                // 满足条件，根据奇偶数拿到结果值
                if ((n + m) % 2 == 1) {
                    return Math.max(L1, L2);
                } else {
                    return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
                }
            } else if (R1 < L2) {
                left = mid1 + 1;
            } else {
                right = mid1 - 1;
            }
        }

        return 0.0;
    }

    /**
     * 394. 字符串解码
     * <p>
     * 根据 数字 + 字母 + [ + ] 进行不同的处理即可
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        int length = s.length();
        if (length == 0) {
            return "";
        }

        StringBuilder path = new StringBuilder();
        int num = 0;
        ArrayDeque<Object> stack = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // 数字
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // [ 入栈
                stack.push(num);
                stack.push(path.toString());

                path = new StringBuilder();
                num = 0;
            } else if (c == ']') {
                // ] 出栈，开始计算结果值
                String pre = (String) stack.poll();
                int times = (int) stack.poll();

                String word = path.toString();
                path = new StringBuilder(pre);
                for (int j = 0; j < times; j++) {
                    path.append(word);
                }
            } else {
                path.append(c);
            }
        }

        return path.toString();
    }

    /**
     * 84. 柱状图中最大的矩形
     * <p>
     * 最小单调栈，注意临界条件
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        if (length == 0) {
            return 0;
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int ans = 0;

        for (int i = 0; i <= length; i++) {
            int curNum = i == length ? -1 : heights[i];
            // 0 1 2 3 4 5
            // 2 1 5 6 2 3
            // 遇到比栈里面小的就开始重新计算结果值
            while (!stack.isEmpty() && curNum < heights[stack.peekLast()]) {
                // 取出来一个
                Integer index = stack.pollLast();
                int height = heights[index];

                // 计算长度
                int lastIndex = stack.isEmpty() ? -1 : stack.peekLast();
                int width = i - lastIndex - 1;
                ans = Math.max(ans, height * width);
            }

            stack.offerLast(i);
        }

        return ans;
    }

    /**
     * 45. 跳跃游戏 II
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int max = 0;
        int end = 0;
        int count = 0;

        // 注意这里一定是 length - 1,因为只要走到最后一个元素，那么他就算走出去了
        for (int i = 0; i < length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                count++;
                end = max;
            }
        }

        return count;
    }

    /**
     * 763. 划分字母区间
     * <p>
     * 这题和跳跃游戏非常类似，只不过是一个变种，就是我们要找每个元素的 最后位置
     * 用这个来进行判断
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> result = new ArrayList<>();
        int length = s.length();
        if (length == 0) {
            return result;
        }

        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        int start = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            max = Math.max(max, lastIndex[s.charAt(i) - 'a']);
            if (i == max) {
                result.add(i - start + 1);
                // TODO 这里的起始位置 不要搞错了
                start = i + 1;
            }
        }

        return result;
    }

    /**
     * 118. 杨辉三角
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 0) {
            return result;
        }

        for (int i = 0; i < numRows; i++) {
            List<Integer> subResult = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    subResult.add(1);
                } else {
                    subResult.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                }
            }
            result.add(subResult);
        }

        return result;
    }

    /**
     * 279. 完全平方数
     * <p>
     * dp[11] = dp[10] + 1 * 1
     * dp[11] = dp[7] + 2 * 2
     * dp[11] = dp[2] + 3 * 3
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        if (n == 0) {
            return 0;
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];
    }

    /**
     * 322. 零钱兑换
     * <p>
     * 完全背包问题
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int length = coins.length;
        if (length == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        // 这题也是最小
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 139. 单词拆分
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int length = s.length();
        if (length == 0) {
            return true;
        }

        boolean[] dp = new boolean[length + 1];
        dp[0] = true;

        for (int i = 1; i <= length; i++) {
            // TODO 注意这里，因为 substring 的左闭右开的问题，所以 i<=length, j 一定要从 0 开始
            for (int j = 0; j <= i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                }
            }
        }

        return dp[length];
    }

    /**
     * 300. 最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int[] dp = new int[length];
        int ans = 0;

        for (int i = 0; i < length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // 出现了一个递增子序列
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    /**
     * 152. 乘积最大子数组
     * <p>
     * 当前值 * 最大值
     * 当前值 * 最小值
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int max = nums[0];
        int min = nums[0];
        int ans = nums[0];

        for (int i = 1; i < length; i++) {
            int num = nums[i];

            int tempMax = max;
            int tempMin = min;

            min = Math.min(num, Math.min(num * tempMin, num * tempMax));
            max = Math.max(num, Math.max(num * tempMin, num * tempMax));

            ans = Math.max(ans, max);
        }

        return ans;
    }

    /**
     * 416. 分割等和子集
     * <p>
     * 0 / 1 背包问题
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return false;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 == 1) {
            return false;
        }

        int target = sum / 2;
        // 到这里变成了 nums 凑成 target 的 0/1 背包问题
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                // 如果能组成，就是 true
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];
    }

    /**
     * 32. 最长有效括号
     * <p>
     * 直接使用栈来解答该题，就会比较简单
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        int length = s.length();
        if (length == 0) {
            return 0;
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        // TODO 这里 一定要 加这个 -1
        stack.push(-1);

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                stack.poll();
                if (stack.isEmpty()) {
                    // 针对 )() 这种情况
                    stack.push(i);
                } else {
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        }

        return ans;
    }

    /**
     * 5. 最长回文子串
     * <p>
     * 使用中心扩散法
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int length = s.length();
        if (length == 0) {
            return "";
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < length; i++) {
            // 根据奇偶数来共同判断
            int max1 = getHwLength(s, i, i);
            int max2 = getHwLength(s, i, i + 1);

            int max = Math.max(max1, max2);

            if (max > (end - start)) {
                start = i - (max - 1) / 2;
                end = i + (max) / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private Integer getHwLength(String s, int left, int right) {
        while (right < s.length() && left >= 0 && right >= 0 && left < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return (right - 1) - (left + 1) + 1;
    }

    /**
     * 72. 编辑距离
     *
     * min(插入 + 删除 + 替换) + 1
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;

        // 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    ) + 1;
                }
            }
        }

        return dp[n][m];
    }

    /**
     * 75. 颜色分类
     *
     * 使用指针不断的变换位置即可
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return;
        }

        int red = 0;
        int blue = length - 1;
        int cur = 0;

        while (cur <= blue) {
            int num = nums[cur];
            if (num == 0) {
                swap(nums, cur, red);
                cur++;
                red++;
            } else if (num == 1) {
                cur++;
            } else {
                swap(nums, cur, blue);
                blue--;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 31. 下一个排列
     *
     * 核心就是 找到 第一个下降的元素，然后再从后面找到第一个比下降元素大的元素
     * 然后 start end 替换
     * start + 1 后面的进行反转
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return;
        }

        int satrt = -1;
        for (int i = length - 2; i >= 0 ; i--) {
            if (nums[i] < nums[i + 1]) {
                satrt = i;
                break;
            }
        }

        if (satrt >= 0) {
            // 找到了
            int end = -1;
            int firstMinNum = nums[satrt];
            for (int i = length - 1; i >= 0 ; i--) {
                if (nums[i] > firstMinNum) {
                    end = i;
                    break;
                }
            }

            swap2(nums, satrt, end);
        }

        reverse(nums, satrt + 1, length - 1);
    }


    private void swap2(int[] nums, int i, int j) {
        if (j >= 0) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 287. 寻找重复数
     *
     * 将数组看成链表，然后按照寻找循环链表来解答该题
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int slow = nums[0];
        int fast = nums[nums[0]];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        // TODO 注意重新寻找的时候 一定是 slow = 0 ,而不是 nums[0]
        slow = 0;

        int p = slow;
        int q = fast;
        while (p != q) {
            p = nums[p];
            q = nums[q];
        }

        return p;
    }

}
