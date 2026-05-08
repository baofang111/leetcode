package hot.hot100;

import listNode.ListNode;
import listNode.Node;
import treeNode.TreeNode;

import java.util.*;

/**
 *
 * @summary Hot100
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/15 17:46:40
 */
public class Hot100 {


    /**
     * 1. 两数之和
     * <p>
     * 题目解析：给定一个数组，判断里面 有没有 值 = target 的， 有的话，就返回下标
     */
    public int[] twoSum(int[] nums, int target) {
        // 使用 map 来记录所有的值，然后当 一个值，还有另外一个值 target - num 也在 map 里面的话，就找到了我们要的结果
        Map<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }

            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * 49. 字母异位词分组
     * <p>
     * 题目解析：使用 map, 然后遍历的时候记录下 排序后的值和原始值即可
     *
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);

            String sortStr = String.valueOf(charArray);

            map.putIfAbsent(sortStr, new ArrayList<>());
            map.get(sortStr).add(str);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * 128. 最长连续序列
     * <p>
     * 题目解析：我们把所有数据全部放入到 set 当中，一保证了去重，而，我们可以 用 判断是否存在来进行遍历
     * 加入 12345,我们先找 1 的开始位置，然后往前找，直到找到不存在的位置，找 1，就是 num -1 不存在，
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int ans = 0;

        // 这里一定要使用 set 进行遍历，去重，提高效率
        for (Integer num : set) {
            int total = 1;
            if (!set.contains(num - 1)) {
                // 这个位置 代表就是 顺序递增的起始位置
                int next = num + 1;
                while (set.contains(next)) {
                    next++;
                    total++;
                }
            }
            ans = Math.max(ans, total);
        }

        return ans;
    }


    /////////////////////////////////////////// 双指针 /////////////////////////////////////

    /**
     * 283. 移动零
     * <p>
     * 题目意思：给定一个 数组 nums, 将里面的 0 全放后面去
     * <p>
     * 题目解析：双指针，我们使用 left + right 指针，如果 出现0 ，就将他们位置替换
     * 同时 我们还不能更改 nums 原有的顺序
     *
     *
     */
    public void moveZeroes(int[] nums) {
        // 使用双指针，快慢指针，用来更新结果集
        int length = nums.length;
        if (length == 0) {
            return;
        }

        int slow = 0;
        int fast = 0;
        while (fast < length) {
            if (nums[fast] != 0) {
                // 往前加
                nums[slow++] = nums[fast];
            }
            fast++;
        }

        // 后面的补位 0
        while (slow < length) {
            nums[slow++] = 0;
        }
    }


    /**
     * 11. 盛最多水的容器
     * <p>
     * 题目意思：给定一个数组，我们在里面放水，看能存的最大的水是多少
     * <p>
     * 题目解析：我们需要使用双指针，然后判断 最小边，每次操作的水 都等于 (right - left) * min(left, right)
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int length = height.length;
        if (length == 0) {
            return 0;
        }

        // 我们一个指针，一个指针的移动，不能一下移动两个指针
        int left = 0;
        int right = length - 1;
        int max = 0;

        while (left < right) {
            int min = Math.min(height[left], height[right]);
            max = Math.max(max, (right - left) * min);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }


    /**
     * 15. 三数之和
     * <p>
     * 题目意思：给定一个 nums ,判断有那些值 他们的和 加起来 = 0
     * <p>
     * 题目解析：我们使用 三指针，固定一个指针，然后另外两个指针，一个 从 i+1 出发，一个从 right 出来，不断的往里面收缩。
     * 注意，一定要对 nums 进行排序，而且会有重复元素，如果遇到重复元素的话，一定要进行 过滤，不然结果集会变多
     *
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        if (length == 0) {
            return result;
        }

        // 一定要排序
        Arrays.sort(nums);

        for (int i = 0; i < length; i++) {
            // 如果有重复的，直接过滤
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            if (nums[i] > 0) {
                // 已经没有结果值了
                break;
            }

            int left = i + 1;
            int right = length - 1;

            while (left < right) {
                int target = nums[i] + nums[left] + nums[right];
                if (target == 0) {
                    // 找到了其中一个结果
                    List<Integer> subResult = Arrays.asList(nums[i], nums[left], nums[right]);
                    result.add(subResult);

                    // 这里需要考虑 left right 后面的重复元素,然后注意临界条件
                    while (left < length - 1 && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (right > 0 && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (target > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        return result;
    }


    /**
     * 42. 接雨水
     * <p>
     * 题目意思：给定一个数组，每个数组里面的元素，代表柱子高度，加入柱子是 101  那么就代表 0 的位置 可以放 1 L的雨水
     * <p>
     * 题目解析：这依旧是一题 双指针的问题，且，能放多少水，是 最低边导致的，所以我们需要两个值，一个是 leftMax 一个是 rightMax,
     * 然后 每个位置 能放多少水的公式就是 water = min(leftMax, rightMax) - height[i]
     * 那么用双指针就
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        // 每个地方的水位，都是用最大边来计算的
        int length = height.length;

        int left = 0;
        int right = length - 1;

        int leftMax = 0;
        int rightMax = 0;

        int water = 0;

        // 双指针开始寻找
        while (left < right) {
            // 如果左边更小，那么先计算左边
            if (height[left] < height[right]) {
                if (height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    // 开始计算水位
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    // 开始计算水位
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }


    ////////////////////////////////////// 滑动窗口 ///////////////////////////////////////////


    /**
     * 3. 无重复字符的最长子串
     * <p>
     * 题目意思：给定一个 字符串 s, 判定 里面 没有重复字符串的最长子串是多长
     * <p>
     * 题目解析：滑动窗口，当我们找到了有重复项的话，我们开始滑动 left ,然后再次寻找我们要的值，同时我们使用一个 Map 来记录所有出现的值
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        // 使用滑动窗口，固定 left ,每次往前面找，如果有重复的，那么就更新 left 的值，继续寻找
        Map<Character, Integer> map = new HashMap<>();

        int length = s.length();
        int left = 0;
        int ans = 0;

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // 如果有重复元素，那么指针往前移动
            if (map.containsKey(c) && map.get(c) >= left) {
                // 这个left 指针 不能后退, 然后left 指针往前移动一下
                left = map.get(c) + 1;
            }

            map.put(c, i);
            // abcabc
            ans = Math.max(ans, i - left + 1);
        }

        return ans;
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * <p>
     * 题目意思：给定 两个字符串 s + p，s 是长字符串，p 是短字符串，其中 长包含了 0 个 或者 N 个 p, 让我们找出 所有包含的位置
     * acb 也相当于 包含 abc
     * <p>
     * 题目解析：依旧是滑动窗口，我们找到 p 的长度，然后 从 s 的 0 的位置，往前移动，找 子字符串和 p 一样的，然后再不断的收缩窗口即可
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        // 我们使用 两个 数组 来存储 移动到的 s p 的位置然后使用 Arrays.equals 来判断是否相等
        List<Integer> result = new ArrayList<>();

        int n = s.length();
        int m = p.length();

        if (m > n) {
            // 不存在
            return result;
        }

        // 使用两个数组来存储 p 位置的单词，然后用来判断 两个地方是否相等
        int[] sArray = new int[26];
        int[] pArray = new int[26];

        // 开始比较
        for (int i = 0; i < m; i++) {
            sArray[s.charAt(i) - 'a']++;
            pArray[p.charAt(i) - 'a']++;
        }

        if (Arrays.equals(sArray, pArray)) {
            // 开头就相等
            result.add(0);
        }

        // 再次遍历，1--abc--2 我们不断的从 1 进 2 ，然后2++ 1-- 的去判断比较
        for (int i = m; i < n; i++) {
            sArray[s.charAt(i) - 'a']++;
            sArray[s.charAt(i - m) - 'a']--;
            if (Arrays.equals(sArray, pArray)) {
                result.add(i - m + 1);
            }
        }

        return result;
    }


    ////////////////////////////////////// 子串 //////////////////////////////////////////

    /**
     * 560. 和为 K 的子数组
     * <p>
     * 题目意思：给定一个整数数组 nums, 还有一个 k ,让我们判断 nums 里面有几个子数组 可以组合成 k，
     * 注意，一定要连续的子数组
     * <p>
     * 题目解析：我们使用 前缀和来解答该题，举个例子
     * k = 7
     * 1 3 4 7 10 那么 3 4 7 都是我们要的答案
     * 我们模拟前缀和
     * 1 4 8 15 25
     * 其中，只要 8 - k = 1 在 前缀和里面出现过，那么就代表这段连续子数组是存在 k 的值的
     * 那么答案就出来了
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        // map<前缀和，前缀和出现的次数>
        Map<Integer, Integer> map = new HashMap<>();
        // 初始化
        map.put(0, 1);

        int preSum = 0;
        int count = 0;

        for (int num : nums) {
            // 计算前缀和
            preSum += num;

            // 如果 前缀和 - K 在 map 中有，那么就代表找到了一个结果值
            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k);
            }

            // 插入前缀和
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }

    /**
     * 239. 滑动窗口最大值
     * <p>
     * 题目意思：给定一个整数数组 nums ,还有一个 大小 = k 的滑动窗口，我们按照 k 去滑动，每次得到的窗口的最大的值 进行汇总
     * <p>
     * 题目解析：简单的暴力解法每次需要算最大值，我们使用窗口滑动， 然后不断的寻找每个窗口的最大值即可，但是这种解法他的 时间复杂度 很高
     * 是 O(n * k)
     * 所以我们使用了一种巧妙的解法，我们使用一个双端队列，记住，这个队列里面存的 永远是 大 > 小
     * 当再次进入一个元素的时候，且在窗口内但是 小于 队列里面的任意 一个值，那么就将这个值丢弃
     * <p>
     * 然后我们还需要两个判断
     * 1：是否超过 当前窗口，超过需要给他删除
     * 2：如果达到 窗口 条件，那么我们就将结果放入
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 该题的核心就是使用 双端队列，如果 入队的数 > 队列里面的数，就将队列里面的比他小的删掉，然后始终保证 对头是最大的数据
        // 这样 我们就可以直接通过滑动窗口，判断窗口的大小，进行位置删除 + 最大值入结果集了
        int length = nums.length;
        int[] result = new int[length - k + 1];
        int index = 0;

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            // 如果当前值，nums[i] 大于队列里面的数据，就将队列里面的数据删除
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }

            // 大的值，入队
            deque.offerLast(i);

            // 如果超出窗口范围，那么就进行出队,记住，出队也一定要 前面的先出
            int leftWindow = i - k + 1;
            while (deque.peekFirst() < leftWindow) {
                deque.pollFirst();
            }

            // 如果满足窗口大小，添加我们的结果集
            if (i >= k - 1) {
                result[index++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }


    /**
     * 76. 最小覆盖子串
     * <p>
     * 题目意思：给定了两个 字符串，S + T，我们要得到 S 中 最小覆盖了 T 的最小子串是什么
     * <p>
     * 题目解析：这题我们依旧使用 滑动窗口，只不过这次窗口比较的是 T 中每个字母出现的次数
     * 当 S 中的子串中的字母出现次数 和 t 中出现的次数一样的话，呢么就是一个覆盖子串，
     * 这时候 记录他的位置，并且往前移动，left ++
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();

        // 创建两个窗口
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();

        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 我们需要一个计数器，记录 window 中单词出现的次数，用来和 need 大小进行比较
        int valid = 0;

        // 开始位置
        int start = 0;
        int length = Integer.MAX_VALUE;

        // 设置窗口指针
        int left = 0;

        for (int i = 0; i < sLength; i++) {
            char c = s.charAt(i);

            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判断符合 need 窗口的单词数量符合了几个
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 当 valid == need.size 就代表我们找到了第一个
            while (valid == need.size()) {
                // 开始记录我们要的数据,找到了更小值
                if (i - left + 1 < length) {
                    start = left;
                    length = i - left + 1;
                }

                // 移动 window 左边位置
                char d = s.charAt(left);
                left++;

                // 移动 d 一定要更改窗口里面的值
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        // 这里相当于 我将窗口里面的有效值 减少了 1 ，需要把这个值进行更新
                        valid--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }

        }

        return length == Integer.MAX_VALUE ? "" : s.substring(start, start + length);
    }

    /**
     * 53. 最大子数组和
     * <p>
     * 题目意思：给定一个数组  nums，我们要得到他的 最大的子数组的和，注意，子数组要连续的子数组
     * <p>
     * 题目解析：这是一道典型的 kadane 题目，最关键的点就是，我们在遍历 nums 的时候，如果当前值 nums[i] 比 cur + nums[i] 大，那么当前最大值
     * 就是 nums[i]
     *
     */
    public int maxSubArray(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int cur = nums[0];
        int max = nums[0];

        for (int i = 1; i < length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            max = Math.max(max, max + cur);
        }

        return max;
    }

    /**
     * 56. 合并区间
     * <p>
     * 题目意思：给定一个二维数组，我们按照 交叉给他进行合并，比如 13 26 -> 16
     * <p>
     * 题目解析：已经我们初始化一个开始数组，intervals[0] 然后我们拿到 start + end ，然后遍历，寻找
     * nextStart nextEnd 不断的 和 start + end 进行比较，如果 在范围内，就合并，不然就下一个
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        // 核心就是 使用 区间，不断的去比较，如果 nextStart > end 那么就是我们需要找的结果值
        int length = intervals.length;
        if (length == 0) {
            return null;
        }

        // intervals 一定要进行排序，且对 [0] 进行排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> result = new ArrayList<>();

        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < length; i++) {
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            if (nextStart > end) {
                // 已经不是交叉区间了，进行合并，合并完成之后，记住更新 start end 的值
                result.add(new int[]{start, end});
                start = nextStart;
                end = nextEnd;
            } else {
                // 更新 end
                end = Math.max(end, nextEnd);
            }
        }

        // 再将 start + end 放入结果集当中
        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
    }


    /**
     * 189. 轮转数组
     * <p>
     * 题目意思：给定一个数组 nums , 123456 ，然后给定一个 k ,让我们得到  456123
     * <p>
     * 题目解析，就是给点数组，然后旋转，我们只需要先 旋转这个数组，然后 旋转 0-length-1, 0 - k-1, k - length-1 即可
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        if (length == 0) {
            return;
        }

        k = k % length;
        swap(nums, 0, length - 1);
        swap(nums, 0, k - 1);
        swap(nums, k, length - 1);
    }

    private void swap(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[right];
            nums[right] = nums[left];
            nums[left] = temp;
            left++;
            right--;
        }
    }


    /**
     * 238. 除了自身以外数组的乘积
     * <p>
     * 题目意思：给定一个数组，每个位置 都是其他位置的 所有乘积
     * <p>
     * 题目解析：我们使用两个数组，分别是 left + right 数组，分别计算 左边 + 右边的 累计乘积
     * 这样我们的结果值 就是 left * right ，就是当前位置的结果集了
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];

        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        // 为什么这里可以左右相乘 等到我们所需要的值，因为，左右的乘积都不包括自己本身，所以相乘 正好是我们要的值
        for (int i = 0; i < n; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }

    /**
     * 41. 缺失的第一个正数
     * <p>
     * 题目意思：给定一个 未排序的数组 nums, 找出其中没出现的最小正整数
     * <p>
     * 题目解析：我们将 nums 放入 一个 set 当中，然后从 1 开始寻找就行，第一个找不到的，就是我们需要的值
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // 从 1 开始找，第一个没找到的值就是我们的答案
        int result = 1;
        while (set.contains(result)) {
            result++;
        }

        return result;
    }


    /**
     * 矩阵置零
     * <p>
     * 题目意思：给定一个二维矩阵，如果 某个元素是 0 的话，那么他的同一行和同一列都要设置为 0
     * <p>
     * 题目解析：我们需要使用 第一行 和 第一列，来存储，中间矩阵是否为 0 的数据值，如果 中间为 0 ，那么就在对应的 第一行 or 第一列
     * 这样我们就可以原地进行 矩阵置零的 改动了，然后我们还需要记录 第一行 和 第一列本身是否有0 ，作为信息回溯
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return;
        }

        // 第一行和第一列的 为 0 标识
        boolean firstRow = false;
        boolean firstCol = false;

        // 第一列
        for (int i = 0; i < n; i++) {
            if (matrix[i][0] == 0) {
                firstCol = true;
                break;
            }
        }

        // 第一行
        for (int j = 0; j < m; j++) {
            if (matrix[0][j] == 0) {
                firstRow = true;
                break;
            }
        }

        // 开始从 中间位置开始遍历
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // 再次开始遍历
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    // 只要第一行和第一列有 0 ，那么就把他下面的所有的设置成 0
                    matrix[i][j] = 0;
                }
            }
        }

        if (firstRow) {
            for (int j = 0; j < m; j++) {
                matrix[0][j] = 0;
            }
        }

        if (firstCol) {
            for (int i = 0; i < n; i++) {
                matrix[i][0] = 0;
            }
        }

    }


    /**
     * 54. 螺旋矩阵
     * <p>
     * 题目意思：给定一个二维矩阵，我们要求 从 左-右，从上-下，从右-左，从下-上 的这样 顺时针来遍历这个二维矩阵，并将他的遍历后的结果返回
     * <p>
     * 题目解析：我们使用首先记录四个边的位置，top down left right，然后按照顺时针走，走到头，就将 边的位置进行一次 ++ -- 的变更，
     * 直到我们遍历完整个矩阵
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int total = n * m;

        // 定义4个边的位置
        int top = 0;
        int down = n - 1;
        int left = 0;
        int right = m - 1;

        List<Integer> result = new ArrayList<>();

        while (total > 0) {
            // 左 - 右
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
                total--;
            }
            top++;
            if (total == 0) {
                break;
            }

            // 上 - 下
            for (int i = top; i <= down; i++) {
                result.add(matrix[i][right]);
                total--;
            }
            right--;
            if (total == 0) {
                break;
            }

            // 右 - 左
            for (int i = right; i >= left; i--) {
                result.add(matrix[down][i]);
                total--;
            }
            down--;
            if (total == 0) {
                break;
            }

            // 下 - 上
            for (int i = down; i >= top; i--) {
                result.add(matrix[i][left]);
                total--;
            }
            left++;
        }

        return result;
    }

    /**
     * 48. 旋转图像
     * <p>
     * 题目意思：给定一个 二维矩阵，我们需要对他进行 选择
     * <p>
     * 题目解析：我们先进行置换，然后在进行 swap 旋转，既能得到我们想要的答案
     * 什么是 置换，就是 matrix[i][j] 和 matrix[j][i] 进行交换
     * swap 就是 matrix[i] 进行 左右位置替换
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return;
        }

        // 先置换,注意 只需要置换一半即可
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < m; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 再次旋转
        for (int i = 0; i < n; i++) {
            swap(matrix[i]);
        }

    }

    private void swap(int[] matrix) {
        int left = 0;
        int right = matrix.length - 1;
        while (left < right) {
            int temp = matrix[right];
            matrix[right] = matrix[left];
            matrix[left] = temp;
            left++;
            right--;
        }
    }

    /**
     * 240. 搜索二维矩阵 II
     * <p>
     * 题目意思：给定一个二维矩阵，然后给定我们一个 target ,让我们判断这个 target 在不在这个二维矩阵里面。
     * 注意这个矩阵，每行 每列都是升序的，但是不是整体有序
     * <p>
     * 题目解析：使用二分法，先确定 在哪几个列，可能存在，然后再判断在哪个位置，如果全部有序，我们直接全部二分即可
     * 但是 这种二分因为顺序的不确定性，所以他的时间 复杂度还是 O（n*m）还是很高
     * 所以我们这里才有 一种 取巧的方法，我们从 右上节点开始往下面找，如果 target < 下面的值，就再往左边找，否则往右边找
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int row = matrix.length;
        int col = matrix[0].length;

        // 我们使用 右上角 遍历方法，就和寻找树的节点一样
        int top = 0;
        int right = col - 1;

        // 不断的寻找
        while (top < row && right >= 0) {
            int x = matrix[top][right];
            if (x == target) {
                return true;
            } else if (x > target) {
                // 当前值 大于 target ,代表我们还需要再往左进行寻找
                right--;
            } else {
                // 小于 target 值，代表我们开始需要往下进行寻找了
                top++;
            }
        }

        return false;
    }


    /////////////////////////////////////////// 链表相关算法 /////////////////////////////////////////


    /**
     * 160. 相交链表
     * <p>
     * 题目意思：给定两个链表 A + B，如果 他俩相交的话，就输出相交的那个点，如果不相交的话，就返回 null
     * <p>
     * 题目解析：使用双指针的方式，我们 假定 相交链表
     * A - C - D
     * B - C - D
     * 这里 C 是相交的点，我们使用两个指针，分别从 A 和 B 开始走，AC + CD ，这里走完需要换个头从 B 开始走 + BC
     * 同样 BC + CD + AC 他们是相等的，只要有相交的节点，那么他们一定会相遇，不然就都走到 空
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;

        // 不断的开始遍历
        while (p1 != p2) {
            if (p1 == null) {
                p1 = headB;
            } else {
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

    /**
     * 206. 反转链表
     * <p>
     * 题目意思：给定一个链表，1-2-3-4-5 我们要将他进行反转成 5-4-3-2-1
     * <p>
     * 题目解析：使用 反插法机械能反转 pre <- 1 <-
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        // 使用翻转法进行旋转，我们使用 一个 pre, 每次将当前节点 指向 pre,就完成了 反转

        ListNode p = head;
        ListNode pre = null;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;

            pre = p;
            p = next;
        }

        return pre;
    }

    /**
     * 234. 回文链表
     * <p>
     * 题目意思：给定一个链表，判断他是不是回文链表， 1-2-2-1 这种就属于 回文链表，
     * <p>
     * 题目解析：简单做法是将链表都放入 数组当中，但是这样，他的 时间复杂度 和 空间复杂度 都比较高
     * 所以我们使用 寻找中间的方式，我们找到 中点位置 ，然后分割一下链表，下半部分我们需要进行一次反转
     * 然后判断 head 和下半部分反转之后的链表，是不是每一个位置的值都是一样的，如果都是一样的 就是 回文链表
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        ListNode mid = findMidListNode(head);
        ListNode next = mid.next;

        // 一定要断开
        mid.next = null;

        // 反转下半部分节点
        ListNode reversedNext = reverseListNode(next);

        // 开始一个一个判断
        ListNode p = head;
        ListNode q = reversedNext;

        while (p != null && q != null) {
            if (p.val != q.val) {
                return false;
            }

            p = p.next;
            q = q.next;
        }

        return true;
    }

    /**
     * 使用快慢指针寻找链表中间位置
     */
    private ListNode findMidListNode(ListNode node) {
        ListNode fast = node;
        ListNode slow = node;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 使用 翻转法，对链表进行翻转
     *
     * @param node
     * @return
     */
    private ListNode reverseListNode(ListNode node) {
        ListNode p = node;
        ListNode pre = null;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;

            pre = p;
            p = next;
        }
        return pre;
    }

    /**
     * 141. 环形链表
     * <p>
     * 题目意思：给定一个链表，判断是否是环形链表
     * <p>
     * 题目解析：我们依旧使用快慢指针，如果 两个相遇了，那么他肯定就是环形链表，不然就不是
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    /**
     * 142. 环形链表 II
     * <p>
     * 题目意思：和 环形链表 1 一样，只不过 1 是判断是否是环形链表，2 是如果有环形链表的话 返回入口的第一个节点（ 也就是环形链表的交叉点 ）
     * 如果没有的话，返回 null
     * <p>
     * 题目解析：和 环形链表 1 的解法一模一样
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                // 相遇后，再次从头开始走，
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }


    /**
     * 21. 合并两个有序链表
     * <p>
     * 题目意思：合并两个有序链表，给定的两个链表 l1 l2 都是有序链表
     * <p>
     * 题目解析：我们只需要遍历，往后加就行了
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;

        ListNode l1 = list1;
        ListNode l2 = list2;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                // 先放 l1
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null) {
            p.next = l1;
        }
        if (l2 != null) {
            p.next = l2;
        }

        return dummy.next;
    }

    /**
     * 2. 两数相加
     * <p>
     * 题目意思：给定两个链表，让我们给他们进行相加，得到一个新的链表，1+9 = 10 1需要往前面进一位
     * <p>
     * 题目解析:直接遍历相加即可，我们使用一个 pre 来记录 相加后是否 >= 10 的 进一位的 1 的值
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int pre = 0;

        ListNode dummy = new ListNode();
        ListNode p = dummy;

        ListNode p1 = l1;
        ListNode p2 = l2;

        while (p1 != null || p2 != null) {
            int v1 = p1 == null ? 0 : p1.val;
            int v2 = p2 == null ? 0 : p2.val;
            int value = v1 + v2 + pre;

            if (value >= 10) {
                pre = 1;
                p.next = new ListNode(value - 10);
            } else {
                pre = 0;
                p.next = new ListNode(value);
            }

            if (p1 != null) {
                p1 = p1.next;
            }
            if (p2 != null) {
                p2 = p2.next;
            }

            p = p.next;
        }

        // pre 还有值得话，需要添加上
        if (pre > 0) {
            p.next = new ListNode(pre);
        }

        return dummy.next;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * <p>
     * 题目意思：给定一个链表 head, 和一个整数 n, 让我们删除倒数 第N个节点
     * <p>
     * 题目解析：我们要删除倒数 第N 个节点，首先就需要 找到 他的前一个节点
     * 这样 pre.next = cur.next 就将该节点删除了
     * 所以寻找这个节点的话，我们需要 找到 链表的总长度 length
     * length - n - 1 就是我们要找的那个位置的 pre 点位
     * 1-2-3-4-5-6 n=2
     * length = 6 ,我们要找 4
     * length - n = 6 - 2 - 1 = 3, index = 3 就是我们要找的这个 4 的位置
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0) {
            return head;
        }

        // 先记录 length
        int length = 0;
        ListNode cur = head;
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        // 判断要删那个位置
        int step = length - n;
        // 如果是开头位置，直接删除
        if (step == 0) {
            return head.next;
        }

        ListNode pre = head;
        // 找到要删除的前一个位置,这里 一定是 1 开始，因为不满足条件的时候 又进行了一次 pre = pre.next
        for (int i = 1; i < step; i++) {
            pre = pre.next;
        }

        pre.next = pre.next.next;

        return head;
    }


    /**
     * 24. 两两交换链表中的节点
     * <p>
     * 题目意思：给定一个链表，比如 12345 ，我们要将它 进行两两旋转得到 21435
     * <p>
     * 题目解析：我们定义几个指针，pre first second ，然后每次将这几个节点进行旋转即可
     * pre - first - second - third
     * ---
     * pre.next = first.next | pre - next
     * second.next = first | pre - second - first
     * second.next = temp (third)
     * first.next = temp | pre - second - first - third
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        // 其实我们要操作的就是 pre - first - second - third 之间的指针变化

        // 使用虚拟头结点当 pre
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        ListNode first = head;

        // 判断还有没有两个节点，有两个点，就进行旋转
        while (first != null && first.next != null) {
            ListNode second = first.next;
            ListNode third = second.next;

            // 开始变换位置
            pre.next = second;
            second.next = first;
            first.next = third;

            // 指针往前移动
            pre = first;
            first = third;
        }

        return dummy.next;
    }


    /**
     * 25. K 个一组翻转链表
     * <p>
     * 题目意思：给定一个链表，然后和 24 不一样的点是，24 题是固定的 2 2 翻转，但是这题是 每K 个反转
     * <p>
     * 题目解析：2个2个 旋转，我们直接 first second 这样就行了，但是 K 个旋转，这样就不行，所以我们需要找到几个点
     * 0 - k 个的 开始 和 结束位置，这样 我们就能够
     * 对 0 - K 的位置旋转之后，在放入到 next 上面，直到完成全部旋转
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 该题的核心，就是 寻找 pre + 我们要旋转到的 end, 这样，我们就可以将链表拆分，一部分旋转，一部分连接上，然后继续旋转
        if (head == null || k == 0) {
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.next = head;

        // 初始化 pre + end 的点位
        ListNode pre = dummy;
        ListNode end = dummy;

        // 开始不断的往下寻找
        while (true) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }

            // 找完了
            if (end == null) {
                break;
            }

            // 开心旋转 + 拼接
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;

            // 对第一个 0 - k 进行旋转
            pre.next = reverse(start);
            // 注意，这里一定要接回 next ,不然就断了
            start.next = next;

            // 开始移动位置，继续寻找
            pre = start;
            end = start;
        }

        return dummy.next;
    }


    private ListNode reverse(ListNode head) {
        ListNode p = head;
        ListNode pre = null;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;

            pre = p;
            p = next;
        }
        return pre;
    }

    /**
     * 138. 随机链表的复制
     * <p>
     * 题目意思：给定一个链表，里面带有  random 的随机指针，要求我们对他进行一个 深拷贝，重新复制一个
     * <p>
     * 题目解析：我们使用简单的 map 存放 老 - 新 链表节点，然后再次 设置 next + random 的节点即可
     *
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

        // 重新再次复制 next + random
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }

    /**
     * 48. 排序链表
     * <p>
     * 题目意思：给定一个链表，里面的值是随机的链表，然后让我们讲该链表进行一次排序
     * <p>
     * 题目解析：典型的一道 归并排序的 题目，我们拆开 拆开之后，不断的合并
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            // 为空 or 不需要排序
            return head;
        }

        ListNode mid = getMid(head);

        // 分出左右两边
        ListNode right = mid.next;
        // 一定要断开链表
        mid.next = null;
        ListNode left = head;

        ListNode l1 = sortList(left);
        ListNode l2 = sortList(right);

        return mergeTwoLists(l1, l2);
    }


    /**
     * 寻找 链表的 mid 节点
     */
    public ListNode getMid(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 合并两个有序链表
     */
    public ListNode mergeListNode(ListNode l1, ListNode l2) {
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
     * <p>
     * 题目意思：给定多个 有序的 链表，让我们将这些链表进行排序
     * <p>
     * 题目解析：依旧 归并排序，我们可以使用 BFS 或者是 DFS 进行操作
     * DFS 使用递归
     * BFS 使用队列
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        Deque<ListNode> deque = new ArrayDeque<>();

        for (ListNode node : lists) {
            if (node != null) {
                deque.offer(node);
            }
        }

        // 开始合并
        while (deque.size() >= 2) {
            ListNode l1 = deque.poll();
            ListNode l2 = deque.poll();
            ListNode l3 = mergeListNode(l1, l2);

            deque.offer(l3);
        }

        return deque.poll();
    }

    /**
     * 我们使用第二种方式 DFS 去做，也是
     *
     * @param lists
     * @return
     */
    public ListNode mergeKListsV2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        int length = lists.length;
        return mergeKListsV2(lists, 0, length - 1);
    }


    public ListNode mergeKListsV2(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }

        int mid = left + (right - left) / 2;

        ListNode l1 = mergeKListsV2(lists, left, mid);
        ListNode l2 = mergeKListsV2(lists, mid + 1, right);

        return mergeListNode(l1, l2);
    }

    /////////////////////////////// 二叉树相关题目 /////////////////////////////////


    /**
     * 94. 二叉树的中序遍历
     * <p>
     * 题目意思：给定一个二叉树，让我们中序 遍历这个二茶树
     * <p>
     * 题目解析：什么是中序遍历 ，左 - 中 - 右， 这就是中序遍历，我们使用 DFS 递归遍历即可
     *
     * @param root
     * @return
     */
    List<Integer> result = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }

        inorderTraversal(root.left);

        result.add(root.val);

        inorderTraversal(root.right);

        return result;
    }

    /**
     * 104. 二叉树的最大深度
     * <p>
     * 题目意思：给定一个二叉树，我们要得到他的最大的深度
     * <p>
     * 题目解析：已经可以使用 DFS + BFS 来解答该题
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }


    /**
     * 226. 翻转二叉树
     * <p>
     * 题目意思：给定一个二叉树，我们对他的左右节点 进行翻转
     * <p>
     * 题目解析：使用 DFS 左右节点进行替换即可
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;

        // 然后再次翻转省下的节点
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * 101. 对称二叉树
     * <p>
     * 题目意思：给定一个二叉树，我们判断他是否是对称二叉树
     * <p>
     * 题目解析：什么是对称二叉树，极速 left.left = right.tight left.right = right.left
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSame(root.left, root.right);
    }

    public boolean isSame(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }

        return isSame(left.left, right.right) && isSame(left.right, right.left);
    }

    /**
     * 543. 二叉树的直径
     * <p>
     * 题目意思：什么是二叉树的直径，就是 一条路径上，二叉树最大有多少个边
     * <p>
     * 题目解析：该题目和 二叉树的最大路径和 是一个意思，只不过一个是路径和 一个是 直径
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

        // 查看最有两边的最大直径
        int left = diameterOfBinaryTreeDFS(root.left);
        int right = diameterOfBinaryTreeDFS(root.right);

        // 已当前节点为拐点的直径
        max = Math.max(max, left + right);

        // 当前点 作为一个选择点
        return Math.max(left, right) + 1;
    }

    /**
     * 102. 二叉树的层序遍历
     * <p>
     * 题目意思：就是给个二叉树，让我们一层一层的遍历
     * <p>
     * 题目解析：给一个 队列，一层 一层的往里面添加元素即可
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            List<Integer> subResult = new ArrayList<>();

            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();

                if (node.left != null) {
                    deque.offer(node.left);
                }
                if (node.right != null) {
                    deque.offer(node.right);
                }

                subResult.add(node.val);
            }
            result.add(subResult);
        }

        return result;
    }


    /**
     * 108. 将有序数组转换为二叉搜索树
     * <p>
     * 题目意思：将有序数组 转换成 二叉搜索树
     * <p>
     * 题目解析：我们找中间点，他就是 root ,然后左右两边分别是 他的左右几点，这题就知道怎么解了
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null) {
            return null;
        }

        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
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
     * 98. 验证二叉搜索树
     * <p>
     * 题目意思：给定一个 二叉搜索树，我们判断 他是不是一个正常的二叉搜索树
     * <p>
     * 题目解析：我们只需要 存储他的 pre 点位，然后每次判断 pre.value 是不是 小于当前的值，只要一个不是小于，那么就不是 二叉搜索树
     * 且一定要使用 中序遍历进行遍历
     *
     * @param root
     * @return
     */
    TreeNode pre = null;
    Boolean valid = true;

    public boolean isValidBST(TreeNode root) {
        isValidBSTBST(root);
        return valid;
    }

    public void isValidBSTBST(TreeNode root) {
        if (root == null) {
            return;
        }

        isValidBSTBST(root.left);

        if (pre != null && pre.val >= root.val) {
            valid = false;
            return;
        }
        pre = root;

        isValidBSTBST(root.right);
    }

    /**
     * 230. 二叉搜索树中第 K 小的元素
     * <p>
     * 题目意思：给定Lee一个二叉搜索树，我们要找第K 小的元素
     * <p>
     * 题目解析：和判断 是否是 二叉搜索树一样，我们只要 按照中序遍历，一个一个找就行
     *
     * @param root
     * @param k
     * @return
     */
    int step = 0;
    int value = 0;

    public int kthSmallest(TreeNode root, int k) {
        step = k;
        kthSmallestDFS(root);
        return value;
    }

    public void kthSmallestDFS(TreeNode root) {
        if (root == null) {
            return;
        }

        kthSmallestDFS(root.left);

        step--;
        if (step == 0) {
            value = root.val;
        }

        kthSmallestDFS(root.right);
    }

    /**
     * 199. 二叉树的右视图
     * <p>
     * 题目意思：给定一个二叉树，我们找到他的 最右边的视图
     * <p>
     * 题目解析：依旧是一道典型的 二叉树层序遍历的题目，我们拿到每一层的最后一个元素即可，记得，插入元素的顺序不能搞乱了
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();

                if (node.left != null) {
                    deque.offer(node.left);
                }
                if (node.right != null) {
                    deque.offer(node.right);
                }

                if (i == size - 1) {
                    result.add(node.val);
                }
            }
        }

        return result;
    }

    /**
     * 114. 二叉树展开为链表
     * <p>
     * 题目意思：给定了一个二叉树，我们要将它全部展开 成为一个 只有右节点的链表
     * <p>
     * 题目解析：其实就是 root.left 有值的话，就将它 变成右节点，依次循环,注意，这个替换，我们一定要记录上一个元素的值
     * 因为我们是要把当前的位置，放入到 上一个节点位置的 右节点当中
     * <p>
     * 其实就是一个 将当前节点 放入到上一个节点的 right 位置的过程，然后将上一个节点的 left 断开
     *
     * @param root
     */
//    TreeNode pre = null;
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
     * 105. 从前序与中序遍历序列构造二叉树
     * <p>
     * 题目意思；给定了我们一个 前序和中序的数组，让我们构建一个 完整的 二叉树
     * <p>
     * 题目解析：前序 = 根-左-右 中序 = 左 - 根 - 右， 这样我们就可以根据 先通过 中序为基准 然后不断的去找他的根节点
     * 然后区分左右节点，然后再分别寻找根节点，这样得到我们需要的值,
     * 这题和 后序构建一样，其实就是我们使用一个基准（ 前序 or  后序 ）然后一个一个去寻找我们的 根节点。中序集合用来辅助即可
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 核心就是 通过 pre 寻找根节点，然后通过 inorder 区分 左右两边，用来构建 左右节点
        Map<Integer, Integer> inMap = new HashMap<>();
        int length = inorder.length;
        for (int i = 0; i < length; i++) {
            inMap.put(inorder[i], i);
        }

        // 开始构建
        return buildTree(preorder, 0, 0, length - 1, inMap);
    }

    private TreeNode buildTree(int[] preorder, int rootIndex, int left, int right, Map<Integer, Integer> inMap) {
        if (left > right) {
            return null;
        }

        int rootValue = preorder[rootIndex];
        Integer inRootIndex = inMap.get(rootValue);
        int leftSize = inRootIndex - left;

        TreeNode root = new TreeNode(rootValue);
        root.left = buildTree(preorder, rootIndex + 1, left, inRootIndex - 1, inMap);
        root.right = buildTree(preorder, rootIndex + leftSize + 1, inRootIndex + 1, right, inMap);

        return root;
    }

    /**
     * 437. 路径总和 III
     * <p>
     * 题目意思：给定一个二叉树，我们判断路径和 = targetSum 的 总共会有多少条
     * <p>
     * 题目解析：我们使用暴力解法， 遍历 root + left + right
     * 然后没走一步 targetSum - 当前val ,然后 能够满足多少次
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        return pathSumDFS(root, targetSum) +
                pathSum(root.left, targetSum) +
                pathSum(root.right, targetSum);
    }

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
     * 236. 二叉树的最近公共祖先
     * <p>
     * 题目意思：给定一个二叉树 root, 让我们判断 p 和 q 的公共祖先是哪个
     * <p>
     * 题目解析：直接 DFS寻找即可
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 左右两边都找到了，那么就代表 root 是他们的公共祖先
        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;
    }


    /**
     * 124. 二叉树中的最大路径和
     * <p>
     * 题目意思：给定一个二叉树，让我们求他最大的路径和
     * <p>
     * 题目解析：这题和 直径那题有点像，就是 加入我当前 root 点，已 root 点作为拐点，那么 最大是多少
     * root 下面，只能分别选 左边 或者 右边，那么他的最大和又是多少？
     *
     * @param root
     * @return
     */
//    int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        sumMaxPathSum(root);
        return max;
    }

    public int sumMaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 判断左右两个部分
        int leftPart = Math.max(0, sumMaxPathSum(root.left));
        int rightPart = Math.max(0, sumMaxPathSum(root.right));

        // 作为拐点
        max = Math.max(max, leftPart + rightPart + root.val);

        // 只能选择 左右两边的节点
        return root.val + Math.max(leftPart, rightPart);
    }


    ////////////////////////////// 图论 //////////////////////////////

    /**
     * 200. 岛屿数量
     * <p>
     * 题目意思：给定一个二维数组，链接 1 的地方 就是 一块岛屿，问我们有多少个岛屿
     * <p>
     * 题目解析：遍历二维矩阵，如果遇到 一个陆地，我们就从这块陆地开始，遍历所有为 陆地的地方，并将它设置成 0 ，然后 陆地数量 + 1
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int total = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    numIslandsBfs(grid, i, j);
                    total++;
                }
            }
        }

        return total;
    }

    private void numIslandsBfs(char[][] grid, int i, int j) {
        int n = grid.length;
        int m = grid[0].length;

        if (i < 0 || j < 0 || i >= n || j >= m) {
            return;
        }

        char c = grid[i][j];
        if (c == '0') {
            return;
        }

        grid[i][j] = '0';

        // 开始遍历
        numIslandsBfs(grid, i - 1, j);
        numIslandsBfs(grid, i + 1, j);
        numIslandsBfs(grid, i, j - 1);
        numIslandsBfs(grid, i, j + 1);
    }


    /**
     * 994. 腐烂的橘子
     * <p>
     * 题目意思：给定一个二维矩阵，1 代表新鲜橘子，2 代表腐烂的橘子，0就代表空的单元格，每次腐烂我们都只能腐烂附件的橘子，
     * 问我们几次能将所有橘子腐烂完成
     * 如果有的新鲜的橘子附件没有腐烂橘子，腐烂不到的话，那么就返回 -1
     * <p>
     * 题目解析：我们需要几个东西
     * 1：先将所有的腐烂的橘子入队，这样我们就可以根据这些腐烂的橘子进行下一次的腐烂扩散 ( 记录他的坐标 )
     * 2：我们需要记录所有的新鲜橘子的个数，这样的话，腐烂扩散完成之后，看还有没有腐烂的橘子了
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int freshCount = 0;
        Deque<int[]> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int value = grid[i][j];
                if (value == 2) {
                    // 腐烂橘子入队
                    deque.offer(new int[]{i, j});
                    continue;
                }
                if (value == 1) {
                    freshCount++;
                }
            }
        }

        // 一个新鲜橘子没有
        if (freshCount == 0) {
            return 0;
        }

        // 上下左右扩散
        int[][] dicts = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;

        while (!deque.isEmpty() && freshCount > 0) {
            count++;

            // 每次腐烂扩散都是所有的腐烂的橘子一起
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] node = deque.poll();
                int x = node[0];
                int y = node[1];

                for (int[] dict : dicts) {
                    int x1 = x + dict[0];
                    int y1 = y + dict[1];

                    // 临界条件返回
                    if (x1 < 0 || y1 < 0 || x1 >= n || y1 >= m) {
                        continue;
                    }

                    // 开始腐烂，如果这里面是 新鲜橘子，那么就给他进行腐烂处理，注意重新腐烂的一定要再次入队
                    if (grid[x1][y1] == 1) {
                        grid[x1][y1] = 2;
                        deque.offer(new int[]{x1, y1});
                        freshCount--;
                    }
                }
            }

        }

        return freshCount == 0 ? count : -1;
    }


    /**
     * 207. 课程表
     * <p>
     * 题目意思：给定一个 numCourses 和 prerequisites，问我们能不能学完所有课程
     * <p>
     * 题目解析：我们需要构建一个 关系的 DAG 图，然后我们还需要 拿到所有的 入度（学这门课需要多少前置课程）= 0 的课程，
     * 然后 必须要从这些 入度 = 0 的课程开始学习
     * 所以我们需要构建两个东西
     * 1：课程 DAG 关系图
     * 2：每个课程的 入度 数量
     *
     * @param numCourses    总共有多少课
     * @param prerequisites 课程的相互依赖关系 [1,0] 表示学习 1 之前 必须学完 0
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 该题的核心就是两个，一个是 每个课程学之前需要学什么课程的 DAG 图
        // 一个是，学某一门课程之前，我还需要提前学多少课程的 入度

        List<List<Integer>> DAG = new ArrayList<>();
        int[] preCount = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            DAG.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            int a = prerequisite[0];
            int b = prerequisite[1];

            // 学 a 之前 必须学 b
            DAG.get(b).add(a);

            // 学 a 之前需要提前学习多少门课程
            preCount[a]++;
        }

        Deque<Integer> deque = new ArrayDeque<>();

        // 判断有没有 入度 = 0 的，有的话，就入队进行后续学习，没有的话，就直接返回false 没法学习
        for (int i = 0; i < numCourses; i++) {
            if (preCount[i] == 0) {
                deque.offer(i);
            }
        }

        // 学多少次
        int count = 0;
        while (!deque.isEmpty()) {
            count++;

            Integer a = deque.poll();
            // 通过DAG图，判断 课程 a 学完之后，还能学什么课程
            List<Integer> nexts = DAG.get(a);
            for (Integer b : nexts) {
                // b 的前置课程 少了一个
                preCount[b]--;

                // b 的前置课程 完成变成 0 的时候，就代表可以再次学习，直接入队
                if (preCount[b] == 0) {
                    deque.offer(b);
                }
            }
        }

        return count == numCourses;
    }


    ////////////////////////////////// 回溯相关算法 ////////////////////////////////////


    /**
     * 46. 全排列
     * <p>
     * 题目意思：给定 一个数组 nums，让我们获取出，这些里面的数组能组成成的所有排列
     * <p>
     * 题目解析：因为我们要的是所有的值，所以我们需要一个关键的操作动作记录，就是那些值已经被用过了
     * 然后使用 DFS 进行回溯即可
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        permuteDFS(nums, used, new ArrayList<>(), result);

        return result;
    }

    private void permuteDFS(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            // 找到了一种排列，入结果集
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            int num = nums[i];
            path.add(num);

            this.permuteDFS(nums, used, path, result);

            // 进行回溯
            used[i] = false;
            path.removeLast();
        }
    }

    /**
     * 78. 子集
     * <p>
     * 题目意思：给定一个数组，让我们拿出里面的所有的子集
     * <p>
     * 题目解析：其实就是一个核心，下一个元素，要不选，要不不选，且选择之后，不能回头（回头就会出现重复的结果集）
     * 所以我们 DFS 然后循环条件 是 index + 1，代表的是下一个添加的值
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        subsetsDFS(nums, 0, new ArrayList<>(), result);

        return result;
    }


    public void subsetsDFS(int[] nums, int index, List<Integer> path, List<List<Integer>> result) {
        // 不需要判断，直接添加
        result.add(new ArrayList<>(path));

        for (int i = index; i < nums.length; i++) {
            path.add(nums[i]);

            // 从 0 1 2 的下标开始 不断的往前找i ，所以这里一定要是 i + 1
            subsetsDFS(nums, i + 1, path, result);

            path.removeLast();
        }
    }

    private static String[] MAPPING = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };

    /**
     * 17. 电话号码的字母组合
     * <p>
     * 题目意思：给定一个数字，里面的数字代表电话号码的组合，为 能得到几种组合
     * <p>
     * 题目解析：回溯 固定一个点，不断寻找下一个点即可
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        // 固定 一个 寻找下一个
        List<String> result = new ArrayList<>();
        letterCombinationsDFS(digits, 0, new StringBuilder(), result);

        return result;
    }

    private void letterCombinationsDFS(String digits, int index, StringBuilder path, List<String> result) {
        // 确定回溯满足条件的条件
        if (path.length() == digits.length()) {
            result.add(path.toString());
            return;
        }

        String word = MAPPING[digits.charAt(index) - '0'];
        int length = word.length();

        for (int i = 0; i < length; i++) {
            char c = word.charAt(i);
            path.append(c);

            letterCombinationsDFS(digits, index + 1, path, result);

            path.deleteCharAt(path.length() - 1);
        }

    }


    /**
     * 39. 组合总和
     * <p>
     * 题目意思：给定一个数组，然后再给定一个 target 目标值，我们寻找数组中，那些元素可以组成这个 target, 拿到所有的值
     * <p>
     * 题目解析：依旧回溯，但是这题不一样的点就是，值可以被重复利用，所以我们的 DFS添加 需要还是原来的值
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        combinationSumDFS(candidates, 0, target, 0, new ArrayList<>(), result);

        return result;
    }


    private void combinationSumDFS(int[] candidates,
                                   int index,
                                   int target,
                                   int total,
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

            path.add(num);
            total += num;

            // 这里因为可以使用重复元素，所以递归一定要注意这点,所以这里给的一定也是 i
            combinationSumDFS(candidates, i, target, total, path, result);

            // 回溯
            path.removeLast();
            total -= num;
        }
    }


    /**
     * 22. 括号生成
     * <p>
     * 题目意思：给定一个数 n, 让我们生成所有 种可能性的括号
     * <p>
     * 题目解析：回溯，其中核心的两个点就是
     * 1：left < n 的时候可以一直放 ( 括号
     * 2：right < left 的时候，可以一直放 ) 括号， 因为括号必须成双成对出现
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        generateParenthesisDFS(n, 0, 0, new StringBuilder(), result);

        return result;
    }

    public void generateParenthesisDFS(int n, int left, int right, StringBuilder path, List<String> result) {
        if (left == n && right == n) {
            result.add(path.toString());
            return;
        }

        if (left < n) {
            path.append("(");
            generateParenthesisDFS(n, left + 1, right, path, result);
            path.deleteCharAt(path.length() - 1);
        }

        if (right < left) {
            path.append(")");
            generateParenthesisDFS(n, left, right + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 79. 单词搜索
     * <p>
     * 题目意思: 给定一个二维矩阵，然后再给定一个 word 单词，我们判断 word 有没有在 二维矩阵里面出现过
     * <p>
     * 题目解析：依旧回溯
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (exist(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean exist(char[][] board, String word, int index, int i, int j) {
        if (index == word.length()) {
            return true;
        }

        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }

        char c = board[i][j];
        if (word.charAt(index) != c) {
            // 这里没找到 下面的单词
            return false;
        }

        // 标记一下 已经找过了，不然会陷入无限循环
        board[i][j] = '#';

        // 上下左右 任意一个位置找打，就返回 true
        if (exist(board, word, index + 1, i - 1, j) ||
                exist(board, word, index + 1, i + 1, j) ||
                exist(board, word, index + 1, i, j - 1) ||
                exist(board, word, index + 1, i, j + 1)) {
            return true;
        }

        board[i][j] = c;

        // 找完也没有找到
        return false;
    }

    /**
     * 131. 分割回文串
     * <p>
     * 题目意思：给定一个字符串 s ,让我们分割出来所有的 回文串
     * <p>
     * 题目解析：依旧回溯，注意，回溯的终止条件是 遍历完整个单词
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();

        partitionDFS(s, 0, new ArrayList<>(), result);

        return result;
    }


    private void partitionDFS(String s, int index, List<String> path, List<List<String>> result) {
        if (index == s.length()) {
            // 遍历完了完整的单词
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            String sub = s.substring(index, i + 1);
            if (isHW(sub)) {
                path.add(sub);

                partitionDFS(s, i + 1, path, result);

                path.removeLast();
            }
        }
    }

    private boolean isHW(String word) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (word.charAt(left) == word.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }


    /**
     * 51. N 皇后
     * <p>
     * 题目意思：给定一个数组 n,这个就代表是一个 n * n 的二维矩阵，判断可以组成哪几种 N皇后，并且返回 组成的类型
     * <p>
     * 题目解析：依旧回溯，我们从 行出发， 然后 列 + 正对角线 + 斜对角线 判断有没有
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        // 行 列 正对角线 斜对角线 都没有 皇后的话，那么就能组成一个完整的 N皇后
        List<List<String>> result = new ArrayList<>();

        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];

        char[][] queen = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                queen[i][j] = '.';
            }
        }

        solveNQueensDFS(n, 0, col, diag1, diag2, queen, result);

        return result;
    }

    private void solveNQueensDFS(int n,
                                 int row,
                                 boolean[] col,
                                 boolean[] diag1,
                                 boolean[] diag2,
                                 char[][] queen,
                                 List<List<String>> result) {
        if (row == n) {
            // 所有的行遍历完了，代表我们已经找到了一个 皇后
            List<String> subResult = new ArrayList<>();
            for (char[] subQueen : queen) {
                subResult.add(new String(subQueen));
            }
            result.add(subResult);
            return;
        }

        for (int i = 0; i < n; i++) {
            int d1 = row - i + (n - 1);
            int d2 = row + i;

            if (col[i] || diag1[d1] || diag2[d2]) {
                // 已经出现过了，组成不了皇后了，返回
                continue;
            }
            col[i] = diag1[d1] = diag2[d2] = true;
            // 记录皇后位置
            queen[row][i] = 'Q';

            solveNQueensDFS(n, row + 1, col, diag1, diag2, queen, result);

            queen[row][i] = '.';
            col[i] = diag1[d1] = diag2[d2] = false;
        }

    }


    //////////////////////////// 二分查找 ////////////////////////////


    /**
     * 35. 搜索插入位置
     * <p>
     * 题目意思：给定一个有序的数组，然后给定一个目标元素 target ,让我们看这个 target 在数组的什么位置
     * <p>
     * 题目解析：有序，或者部分有序，让我们找 某个元素或者找某个元素所在的位置，基本都是 二分查找法
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * 74. 搜索二维矩阵
     * <p>
     * 题目意思：给定一个 整体有序的二维矩阵，让我们从里面判断，是不是有 target 这个值
     * <p>
     * 题目解析：有序 + 寻找值，依旧是二分寻找，只不过这次寻找实在二维矩阵上面寻找
     *
     */
    public boolean searchMatrixV2(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int left = 0;
        int right = n * m - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 通过 mid 寻找到相对应的 row + col, 4 * 3 举例，现在在第二行第二列，mid = 5
            int row = mid / m;
            int col = mid % m; // 5/4 = 1

            int num = matrix[row][col];
            if (num == target) {
                return true;
            } else if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * <p>
     * 题目意思：给定一个有序数组，找出 target 在数组中的 位置，一个找不到就 返回 -1 -1
     * <p>
     * 题目解析：有序 寻找 依旧二分，我们只要通过二分，分别找出 最左边 + 最右边的值 就能得到我们的结果
     *
     */
    public int[] searchRange(int[] nums, int target) {
        int left = findLeft(nums, target);
        if (left >= nums.length || nums[left] != target) {
            return new int[]{-1, -1};
        }

        int right = 0;
        for (int i = left; i < nums.length; i++) {
            if (nums[i] == target) {
                right = i;
            } else {
                break;
            }
        }

        return new int[]{left, right};
    }

    /**
     * 寻找最左边的 符合条件的节点
     */
    private int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 我们要找第一个大于 or 等于 target 的值，所以 left 需要往前进一位
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }


    /**
     * 33. 搜索旋转排序数组
     * <p>
     * 题目意思：给定一个 被旋转后的数组 比如 123456 - 561234, 然后再给定了一个 target ，让我们寻找 target 在旋转数组中的位置
     * <p>
     * 题目解析：部分有序 + 寻找具体  target 值，依旧二分，只不过这里我们要先寻找到具体的 有序边，然后再进行二分。
     * 只能对有序边进行二分
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        // 二分 然后寻找有序边，然后判断是否在里面，然后继续二分
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            // 判断有序边
            if (nums[left] <= nums[mid]) {
                // 左边有序，判断 target 在不在 left - mid 中间
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    // 不在的话，在下一个范围，往前面找
                    left = mid + 1;
                }
            } else {
                // 右边有序，判断 target 在不在 mid - right 中间,一定要 <= right 不然会丢数据
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }


    /**
     * 153. 寻找旋转排序数组中的最小值
     * <p>
     * 题目意思：给定一个旋转后的有序数组，让我们得到 这个旋转的有序数组的最小值
     * <p>
     * 题目解析：依旧是部分有序，依旧是寻找某个点，只不过这次寻找的时候 峰谷，他和寻找峰值一样，
     * 那么这种部分有序的什么时候是峰谷呢，突然出现一个 num[i + 1] < num[i] 这个 i + 1 的位置就是峰谷
     * 且，如果数组 一直有序的边的话，他是不会出现峰谷的
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        // 寻找峰谷 or 寻找峰值 都一样，不断的去二分，寻找那个接近的点
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                // 这个代表最小值就在 mid - right 中间，我们要不断的逼近这个位置，去寻找
                left = mid + 1;
            } else {
                // 不在的话，更改 right 再次寻找
                right = mid;
            }
        }

        return nums[left];
    }


    /**
     * 4. 寻找两个正序数组的中位数
     * <p>
     * 题目意思：给定两个有序的数组，我们要找这两个有序的数组的中位数，如果是 2-3这种，就返回 （2+3）/ 2 = 2.5
     * <p>
     * 题目解析：这题的核心就是 什么是中位数，中位数必须满足两个条件
     * 1：左边元素 = 右边元素 （数量，最多左边多一个）
     * 2：左边元素的 最大值 < 右半部分的最小值
     * 所以我们可以使用这个进行处理
     * ------ L1 | R1------
     * ------ L2 | R2------
     * 我们什么时候找到  L1 < R2 && L2 < R1 的时候，就算找到了我们的中位数
     * 然后注意 奇偶数的判定
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 核心就是寻找 中位数的两个条件
        int n = nums1.length;
        int m = nums2.length;
        if (n > m) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int left = 0;
        int right = n;
        while (left <= right) {
            // 以 nums1 作为标准去寻找中位数
            int mid1 = left + (right - left) / 2;
            int mid2 = (n + m + 1) / 2 - mid1;

            int L1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int R1 = mid1 == n ? Integer.MAX_VALUE : nums1[mid1];
            int L2 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int R2 = mid2 == m ? Integer.MAX_VALUE : nums2[mid2];

            // 符合条件的话，直接开始寻找我们的中位数
            if (L1 <= R2 && L2 <= R1) {
                if ((n + m) % 2 == 1) {
                    // 奇数
                    return Math.max(L1, L2);
                } else {
                    // 偶数
                    return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
                }
            } else if (R1 < L2) {
                // 上面的选择小了，继续往前面移动
                left = mid1 + 1;
            } else {
                right = mid1 - 1;
            }
        }

        return 0.0;
    }


    /////////////////////////////////////// 栈 相关算法题 //////////////////////////////////////


    /**
     * 20. 有效的括号
     * <p>
     * 题目意思：给定一个字符串，判断是不是 有效的括号。
     * 什么是有效的括号，()[] 一对一出现就是有效的括号
     * <p>
     * 题目解析：使用一个栈，然后入栈，出栈，判断 是否成双成对即可
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s == null) {
            return true;
        }

        List<Character> symbol = Arrays.asList('(', '[', '{');
        Deque<Character> deque = new ArrayDeque<>();

        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (symbol.contains(c)) {
                deque.push(c);
            } else {
                if (deque.isEmpty()) {
                    return false;
                }

                Character d = deque.pop();

                if (d == '(' && c != ')') {
                    return false;
                }
                if (d == '[' && c != ']') {
                    return false;
                }
                if (d == '{' && c != '}') {
                    return false;
                }
            }
        }

        return deque.isEmpty();
    }

    /**
     * 394. 字符串解码
     * <p>
     * 题目意思：给定一个字符串，3[a]2[bc] ，我们给他解码，2[bc] 就相当于 bc bc
     * <p>
     * 题目解析：一道典型的 栈的题目，其中我们会遇到几种不同的情况
     * 1：数字，入栈，用来后面 foreach 我们的字母
     * 2：[: 需要入栈，将前面的字母 + 数字 前部入栈
     * 3:]：出栈 代表可以进行拼接了，将 num + 字母，全部取出来，num 控制数量，字母就是拼接的东西
     * 4：字母：不断的 append  添加
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        // 初始化 path + stack
        ArrayDeque<Object> stack = new ArrayDeque<>();
        StringBuilder path = new StringBuilder();
        int num = 0;

        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // 这里是数字
                num = 10 * num + (c - '0');
            } else if (c == '[') {
                // 这里需要 入栈,注意 入栈是 先进后出
                stack.push(num);
                stack.push(path.toString());

                // 重置
                num = 0;
                path = new StringBuilder();
            } else if (c == ']') {
                // 这里需要开始结算了，,从栈里面拿到我们的 字母 + 数字
                String pre = (String) stack.pop();
                int times = (int) stack.pop();

                // 这里 当前的 path 里面还有值
                String word = path.toString();
                path = new StringBuilder(pre);
                for (int j = 0; j < times; j++) {
                    path.append(word);
                }
            } else {
                // 走到这里只能是字母，继续 append
                path.append(c);
            }
        }

        return path.toString();
    }

    /**
     * 739. 每日温度
     * <p>
     * 题目意思：给定一个数组 temperatures ，他表示每日温度，让我们返回 当前温度下，下一天的比当前温度最高的还有多少天
     * 例如： 1 3 2 5 --- 1 2 1 0
     * 当前天下，需要多少天气温回暖，这就是 题目要的答案（ 回暖就是 温度比当前温度高 ）
     * <p>
     * 题目解析：使用单调栈，我们维护一个栈，栈里面存的是索引，当 遍历的当前值 temperatures[i] > 栈顶，那么我们就把这个最大的放进去
     * 这时候 该题目就已经被解答出来了
     * 举个列子：
     * 3 2 5:
     * 3 入栈：
     * 2 入栈，2 没有 3 大，继续入栈
     * 5：入栈，5这时候比 2 大，我们拿出来 2，拿出来 2 的 index,然后 拿 5 的 index 相减，这就是 2 位置的 结果值
     * 2 拿出来之后，继续必须栈里面的 3 ，发现 3 还是比 5 小，继续拿出来，然后执行 2 拿出来的操作
     * <p>
     * 这样我们就能得到我们所要的结果值
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] result = new int[length];

        // 单调栈
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 出现了一个更大的值了
                Integer preIndex = stack.pop();
                result[preIndex] = i - preIndex;
            }
            stack.push(i);
        }

        return result;
    }

    /**
     * 84. 柱状图中最大的矩形
     * <p>
     * 题目意思：给定一个 数组，数组中的每个元素相当于 高度，让我们求 能组成的最大的矩形的面积
     * <p>
     * 题目解析：我们计算某个位置下，能计算出来的最大矩形，其实，就是往后面找，找到 第一个 比他矮的，然后就能使用当前 高度 * 走的距离，
     * 得到一个矩形的面积了。这其实和 每日温度是一样的题目，还是用单调栈 进行解答
     * 只不过 每日温度，是递增的 单调栈，这题是递减的 单调栈。
     * 其中我们还需要注意一些 临界条件
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        // 单条递减栈 来解决该问题
        int length = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;

        for (int i = 0; i <= length; i++) {
            // 注意处理临界情况
            int curHeight = i == length ? 0 : heights[i];
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                Integer preIndex = stack.pop();
                // 这里取 6
                int height = heights[preIndex];

                // 这里是取 5 的 index
                int lastIndex = stack.isEmpty() ? -1 : stack.peek();
                // 4 - 2 - 1 = 1
                int width = i - lastIndex - 1;

                // 第一遍走 6 * 1 = 6，第二遍再进来的时候 5 * 2 = 10 就是我们要的正确答案
                max = Math.max(max, height * width);
            }

            stack.push(i);
        }
        return max;
    }


    //////////////////////////////////////// 堆 相关算法 /////////////////////////////////////


    /**
     * 215. 数组中的第K个最大元素
     * <p>
     * 题目意思：给定了一个数组 nums, 让我们求他的 第 K 的大的元素
     * <p>
     * 题目解析：使用 最小堆 来解决即可，
     * 当 堆 放入元素 < k 的时候，直接放入
     * 当 放入元素 > k 的时候，判断和 堆顶那个大，当前比堆顶 大就放进去，当前比堆顶小，就丢弃
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (minHeap.size() < k) {
                minHeap.offer(nums[i]);
                continue;
            }
            if (!minHeap.isEmpty() && nums[i] > minHeap.peek()) {
                // 注意最小堆放进去的时候，还需要进行删除，先删 再 放
                minHeap.poll();
                minHeap.offer(nums[i]);
            }
        }

        return minHeap.peek();
    }

    /**
     * 347. 前 K 个高频元素
     * <p>
     * 题目意思：给定一个数组 nums ,然后让我们拿出 里面频次最高的几个 元素
     * <p>
     * 题目解析：很简单，一个 map 用来统计 数字出现的次数 + 一个 最大堆，用来筛选 前 K 个高频元素即可
     * 需要注意的点就 最小堆 里面我们放的是 map 的 key + value
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 创建一个最大堆
//        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int[] result = new int[k];

        for (Integer num : map.keySet()) {
            Integer count = map.get(num);

            // 优化版本
            if (maxHeap.size() < k) {
                maxHeap.offer(new int[]{num, count});
            } else if (count > maxHeap.peek()[1]) {
                maxHeap.poll();
                maxHeap.offer(new int[]{num, count});
            }
        }

        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll()[0];
        }

        return result;
    }


    //////////////////////////////////////// 贪心算法 //////////////////////////////////////


    /**
     * 121. 买卖股票的最佳时机
     * <p>
     * 题目意思：哪天卖出股票赚的最多
     * <p>
     * 题目解析：贪心算法
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = 0;

        int length = prices.length;
        for (int i = 0; i < length; i++) {
            min = Math.min(min, prices[i]);
            max = Math.max(max, prices[i] - min);
        }

        return max;
    }

    /**
     * 55. 跳跃游戏
     * <p>
     * 题目意思：看能不能跳出去
     * <p>
     * 题目解析：贪心算法即可,我们使用 一个 max 记录，当前位置，能走多远，最远能走多远
     * max = i + nums[i]
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int lenght = nums.length;
        int max = 0;

        for (int i = 0; i < lenght; i++) {
            if (max < i) {
                return false;
            }
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }


    /**
     * 45. 跳跃游戏 II
     * <p>
     * 题目意思：依旧跳跃游戏，这次是跳多少次能跳出去
     * <p>
     * 题目解析：所以我门这里和 上面那题不一样的就是，我们需要记录一个 count 次数,
     * 还需要记录 我们走到了那个结束位置
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int length = nums.length;
        int max = 0;
        int count = 0;
        int end = 0;

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
     * 题目意思：
     * <p>
     * 题目解析：贪心，我们先拿到 s 中每个字符的最远位置，然后 当 所以字符都走完的话，就需要切一刀了
     * 这里的切一刀
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        // 拿到所有字母的最后一个出现的位置
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        // 开始贪心计算
        List<Integer> result = new ArrayList<>();
        int end = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);

            // 走到结束了，和 跳跃游戏 2 那个走法一样
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1;
            }
        }

        return result;
    }


    //////////////////////////////////////////////  动态规划相关  ////////////////////////////////////////////


    /**
     * 70. 爬楼梯
     * <p>
     * 题目意思：每次只能爬 一层 or 2层，问 一个 N 的 台阶，能有多少种方式能爬到楼顶
     * <p>
     * 题目解析：动态规划基础题，状态转移方程是 第 N 个台阶有多少中方式 = 要不 爬一层，也就是用上一层在加一层，再加上，爬两层，也就是爬了 N - 2 层
     * 所有 int[n] = int[n-1] + int[n-2]
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n < 2) {
            return n;
        }

        int[] result = new int[n + 2];
        result[0] = 1;
        result[1] = 1;

        for (int i = 2; i <= n; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }

        return result[n];
    }

    /**
     * 118. 杨辉三角
     * <p>
     * 题目意思：给定一个 numRows，表示多少行，让我们生成一个 杨辉三角。
     * 什么是 杨辉三角，就是 每个数都是它的左上方 和  右上方的数的 和
     * <p>
     * 题目解析：我们直接遍历，暴力解即可
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
            List<Integer> row = new ArrayList<>(i + 1);
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    // 不是左边 或者 右边的 边缘行，那么就需要进行 左上方 + 右上方的 和
                    row.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                }
            }
            result.add(row);
        }

        return result;
    }


    /**
     * 198. 打家劫舍
     * <p>
     * 题目意思：给定一个 数组 nums，里面装的相当于每一个房间的钱，然后我有一个专业的小偷，小偷有个条件，就是不能投相邻的房子的钱
     * 问：最多可以偷多少钱
     * <p>
     * 题目解析：转台转移方程，因为小偷不能偷连续的房间，所以 第 N 个房间能偷多少钱 = 要不偷 前前一个的房间，要不当前的不偷，偷前一个房间的钱
     * 然后取个最大值,注意，偷 前前一个房间的钱的话，就相当于 dp[n-2] + 偷当前房间的钱 = num[i]
     * dp[n] = max(dp[n-2] + nums[n], dp[n - 1])
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[nums.length - 1];
    }


    /**
     * 279. 完全平方数
     * <p>
     * 题目意思：给定一个 整数 n ，返回 和 为 n 的完全平方数的最小数量
     * 例如：12 = 4 + 4 + 4 = 3； 13 = 4 + 9 = 2
     * <p>
     * 题目解析：依旧动态规划，依旧状态转移方程
     * dp【i】 = min（dp[i - j * j] + 1）
     *
     * @param n
     * @return
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);

        // 初始化
        dp[0] = 0;

        // 4
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i - j * j] + 1, dp[i]);
            }
        }

        return dp[n];
    }


    /**
     * 322. 零钱兑换
     * <p>
     * 题目意思：给定一个整数数组 coins，里面的数值表示不同的硬币，然后再给我们一个 amount ，表示硬币金额，
     * 问我们 用最小的硬币个数 多少个 能凑出来 amount 金额。
     * 注意这里有个条件是 硬币的每种数量可以理解是无限的
     * <p>
     * 题目解析：典型的一题 动态规划，我们一个一个硬币去寻找 foreach - coins
     * 如果可以构成的话，就是
     * dp[i] = dp[i - coin] + 1
     * dp[i] = dp[amount] 组成 amount 需要的最小硬币数
     * dp[i - coin] = dp[amount - coin] 用当前硬币 coin 组成 amount 需要 硬币数 再 + 1
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        // 这题注意，这里必须要有一个 初始化 一个更大的数，不然 Math.min(dp[i], dp[i - coin] + 1); 里面，所有的数据都是 0 了
        Arrays.fill(dp, amount + 1);

        dp[0] = 0;

        // 按照金额 一个一个常数
        for (int i = 0; i <= amount; i++) {
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
     * <p>
     * 题目意思：给定一个单词 s ,然后再给我们一个单词的数组 wordDict，问 我们能不能用 wordDict 中的单词组成s
     * <p>
     * 题目解析：使用动态规划
     * s 的 第 N 个位置 dp[n] 表示能不能 从 wordDict 中组成
     * 然后就是判断 dp[i] 中的 i j 能否被 wordDict 所组成，
     *
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 注意这题主要是找 s 中的 i j 位置 能不能由 wordDict 中的单词组成
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                // 记住这边，一定是 从 0 再往 i 的位置上面去找
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * 300. 最长递增子序列
     * <p>
     * 题目意思：给定一个无序的数组，让我们寻找他的 最长递增 子序列
     * <p>
     * 题目解析：依旧是一题典型的动态规划的题目，我们遍历 nums，当出现 nums[i + 1] > nums[i] 的时候就代表有了一个递增子序列
     * 所以动态转移方程 =
     * 当出现 dp[i] > dp[j] 的时候
     * dp[i] = min(dp[j] + 1)
     * 这个表示 如果 我遍历 0 - i 的时候，
     * 当前 值 出现了一个 递增序列，那么我就拿前一个的递增序列 + 1
     * <p>
     * dp[i] 定义：以数组中第 i 个数字结尾的最长递增子序列是多少
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        // 该题的核心 就是 dp[i] > dp[j] 然后我们就需要更新我们的最大递增子序列
        int n = nums.length;
        int[] dp = new int[n];
        // 一定要拿这个结果值对数据进行接收，不然会出现问题
        int ans = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                // 注意这种所有的 这种自己找自己的，这题，或者，单词拆分那一题，都需要 从 0 - i 找
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // 接收结果值
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }


    /**
     * 152. 乘积最大子数组
     * <p>
     * 题目意思：给定一个数组，让我们求出，他的 乘积 最大的 子数组
     * <p>
     * 题目解析：其实就是 man(nums[i], nums[i] * min, nums[i] * max)
     * 这个公司一看就看出来了，
     * 如果我们想要拿到最大的乘积的话，只有三种情况
     * 1：当前值
     * 2：当前值 * 最小值 （ 因为有负数的情况，负负得正 ）
     * 3：当前值 * 最大值
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int min = nums[0];
        int max = nums[0];
        int ans = nums[0];

        for (int i = 1; i < n; i++) {
            int num = nums[i];
            // 这里需要将 min max 先存起来，防止覆盖，这点非常重要，不然下面 max - min 更改的时候 max 就已经被更改了
            int tempMax = max;
            int tempMin = min;

            max = Math.max(num, Math.max(tempMax * num, tempMin * num));
            min = Math.min(num, Math.min(tempMax * num, tempMin * num));

            ans = Math.max(ans, max);
        }

        return ans;
    }


    /**
     * 416. 分割等和子集
     * <p>
     * 题目意思：给定一个数组，让我们 判断，能不能将它合并成 两个 和一样的子集，使得两个子集的和是相等的
     * <p>
     * 题目解析：
     * 1:可以使用暴力解法，先排序，然后按照二分去判断，左边 小，就 left 往前，算总和，然后看有没有一个交集，
     * 2：使用 动态规划，我们先计算 nums 的所有的 结果集的和， sum, 如果 sum 是奇数，那么他肯定不能平分
     * 然后我们就要找的结果就变成了 在 nums 中，能不能找到 和 为 sum/2 的
     * 那么他的状态转移方程就出来了
     * dp[i] = dp[i] || dp[i - num] 这里面 i = target
     * 他表示的含义是，原来就能凑出 target
     * 或者通过 num 凑出 target，所以是 dp[i - num]
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        // 这是一个 典型的 0/1 背包问题，一个数只能用一次。完全背包就是一个数可以一直用
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        // 0/1背包，先拿数，在遍历，完全背包，先遍历再拿数
        for (int num : nums) {
            // 这里一定要倒序拿，防止变成完全背包，一个数拿了多长
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];
    }


    /**
     * 32. 最长有效括号
     * <p>
     * 题目意思：给定一个字符串，字符串里面都是 () 这种括号，问我们最长的 有效括号长度是多少？
     * 什么是有效的括号，（） 这种就算有效的括号
     * <p>
     * 题目解析：我们可以使用 栈的 方式 来解答该问题 ，将 s 括号中的下标 作为 入栈的 index 的值
     * 当遇到 ( 就入栈 i
     * 当遇到 ）就先出一个 （ 出过之后这里会有两种情况 ）
     * 1：一种是 之前有 （ 那么他出完之后就还会继续有值，
     * 2：出完之后，就没值了，每值之后，也需要将 当前的 i 进行入栈，这样就相当于 作为下一个 （）的起点
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        int length = s.length();

        // 栈里存“下标”，作用有两个：
        // 1️⃣ 存未匹配的 '(' 的位置
        // 2️⃣ 存“断点位置”（最近一个无法匹配的 ')')
        Deque<Integer> stack = new ArrayDeque<>();

        // 初始化一个 -1，作为“最左边的边界”
        // 👉 这样当第一个括号就匹配成功时，可以正确计算长度
        stack.push(-1);

        int ans = 0;

        for (int i = 0; i < length; i++) {
            if (s.charAt(i) == '(') {
                // 遇到 '('：
                // 👉 说明它还没匹配，先把位置记录下来
                // 👉 等后面遇到 ')' 来匹配它
                stack.push(i);
            } else {
                // 遇到 ')'：
                // 👉 尝试匹配一个 '('
                // 👉 所以先弹出栈顶（可能是 '('，也可能是边界）
                stack.pop();

                // 判断当前是否还能继续形成有效区间
                if (stack.isEmpty()) {
                    // 👉 栈空了，说明这个 ')' 没有可以匹配的 '('
                    // 👉 当前 i 成为“新的断点”

                    // 为什么要 push(i)？
                    // 👉 因为后面的长度计算：i - stack.peek()
                    // 👉 需要一个“左边界”
                    // 👉 这个 i 就是新的无效边界
                    stack.push(i);
                } else {
                    // 👉 栈不为空，说明刚才的 ')' 匹配成功了

                    // 👉 stack.peek() 表示：
                    //    “最近一个不能参与当前匹配的位置（断点）”
                    //
                    // 👉 当前有效区间就是：
                    //    (stack.peek(), i]
                    //
                    // 👉 所以长度是：
                    //    i - stack.peek()
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        }

        return ans;
    }


    //////////////////////////////////////// 多维动态规划 /////////////////////////////////////////


    /**
     * 62. 不同路径
     * <p>
     * 题目意思：给定一个 m * n 的网格，问我们从 0，0 的起点，走到 n,m 的终点，能有多少种不同的路径
     * <p>
     * 题目解析：典型的一道 动态规划的题目 其中 dp[n][m] 表示走到 n m 的位置 能有多少种路线，
     * 然后 每个 n m 的路径 都是 有 从上面来 + 左边来的路径加起来的
     * 所以 动态转移方程 = dp[n][m] = dp[n][m-1] + dp[n-1][m]
     * <p>
     * 注意：如果是 不同路径Ⅱ的话，他中间会有障碍物，遇到障碍物，我们直接 dp[i][j] = 0 即可，其他不变
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        // 我们要初始化第一行 + 第一列，这个都是只有一种路径
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 64. 最小路径和
     * <p>
     * 题目意思：给定一个 grid 的二维矩阵，问我们 走到 n m 的位置的最小路径和是多少
     * <p>
     * 题目解析：依旧是一道典型的 动态规划的题目，其中 n m 表示 走到这里的最小路径和
     * 那么 他的 动态转移方程就是
     * dp[n][m] = min（ dp[n][m - 1], dp[n - 1][m] ）
     * 但是这题，需要注意的点就是，我们要栈
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] dp = new int[n][m];
        dp[0][0] = grid[0][0];

        // 依旧需要初始化 第一行 + 第一列
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < m; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int val = grid[i][j];
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + val;
            }
        }

        return dp[n - 1][m - 1];
    }

    /**
     * 5. 最长回文子串
     * <p>
     * 题目意思：给定一个 字符串 s ,我们找出他的最长回文子串
     * <p>
     * 题目解析：可以使用中心扩散发
     * 选定两个 奇数 or 偶数 然后分别判断 i 推进之后的最长回文子串，然后不断的更新即可
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        // 我们使用 中心扩散法，注意扩散的时候，需要考虑奇偶数
        // 什么是 中心扩散法，就是我们铆钉一个点，然后向 两边扩散，寻找两边扩散之后的具体的 回文长度

        int length = s.length();
        int start = 0;
        int end = 0;

        for (int i = 0; i < length; i++) {
            int l1 = genLength(s, i, i + 1);
            int l2 = genLength(s, i, i);

            // 寻找 奇偶数下的最长回文串
            int max = Math.max(l1, l2);

            if (max > (end - start)) {
                // 这里需要更新 start + end
                start = i - (max - 1) / 2;
                end = i + max / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private int genLength(String s, int left, int right) {
        int length = s.length();
        while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return (right - 1) - (left + 1) + 1;
    }

    /**
     * 1143. 最长公共子序列
     * <p>
     * 题目意思：给定了两个字符串 text1 + text2 , 返回这两个字符串的最长公共子序列的 擦会给你度，如果不存在，就返回 0
     * <p>
     * 题目解析：这也是一题标准的 动态规划的题目，设定一个 dp[i][j] 表示 text1 的 i ,和 text2 的 j 能组成的 最长公共子序列的长度是多少
     * 他的状态转移方程就需要判断 text1 和 text2 中的 i j 单词是否一样，如果 一样的话，那么就
     * dp[i][j] = dp[i - 1][j - 1] + 1
     * 如果不一样，不一样我们就需要两个都往前面找，找到一个最大的最长子序列
     * dp[i][j] = max ( dp[i - 1][j], dp[i][j - j] )
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();

        // 初始化 DP
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m];
    }


    /**
     * 72. 编辑距离
     * <p>
     * 题目意思：给定两个单词 word1，和 word2 ，问 将 word1 转换成 word2 所需要的最小操作次数。
     * 我们能使用的操作就是 插入一个字符 + 删除一个字符 + 替换一个字符
     * <p>
     * 题目解析：依旧是一题典型的动态规划的题目，和 最长公共子序列有点像
     * 如果 i-1 j-1 的单词一样的话，那么 就不需要变动
     * dp[i][j] = dp[i-1][j-1]
     * <p>
     * 如果不一样的话，那么当前 dp 就需要 进行三次操作 拿到我们需要的值 （ 插入 or 删除 or 替换 ）
     * dp[i][j] = min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1])
     * dp[i][j-1] = 删除
     * dp[i-1][j] = 插入
     * dp[i-1][j-1] =  替换
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // 初始化 DP
        int[][] dp = new int[n + 1][m + 1];

        // 需要对 word1 和 word2 进行初始化
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 这里需要进行一次 插入 or 删除 or 替换的 操作，所以后面需要 + 1
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i][j - 1], dp[i - 1][j])
                    ) + 1;
                }
            }
        }

        return dp[n][m];
    }


    ///////////////////////////////////////// 技巧 ///////////////////////////////////////////


    /**
     * 136. 只出现一次的数字
     * <p>
     * 题目意思：一个数组里面，其他都出现了 2 次，只有一个数字出现一次，让我们能找出这个值
     * <p>
     * 题目解析：a^a =0  a^a^b = b ，答案就出来了
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }


    /**
     * 169. 多数元素
     * <p>
     * 题目意思：给定一个数组，数组中有一个元素出现了超过半数以上，问我们这个数是多少
     * <p>
     * 题目解析：直接排序之后，返回中间的位置即可
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }


    /**
     * 75. 颜色分类
     * <p>
     * 题目意思：给定一个数组，其中 0 1 2 分别代表 红色 白色 蓝色，让我们排好序展示
     * <p>
     * 题目解析：直接排序，我们这里使用三指针替换的方法
     * 铆钉 left or right 指针
     * 如果left 用来设定 0
     * right 用来设定 2
     * 当前指针移动，如果遇到 0 or 2 那么就进行位置替换
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int length = nums.length;

        int left = 0;
        int right = length - 1;

        int cur = 0;
        while (cur <= right) {
            if (nums[cur] == 0) {
                swap2(nums, cur, left);

                // 指针往前推
                left++;
                cur++;
            } else if (nums[cur] == 1) {
                cur++;
            } else {
                swap2(nums, cur, right);
                right--;
                // 这里 cur 不能往前走
            }
        }
    }

    private void swap2(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * 31. 下一个排列
     * <p>
     * 题目意思：给定一个数组，让我们拿到他的下一个排列, 比如 123 下一组就是 132。假如是 321 下一组就是 123
     * <p>
     * 题目解析：该题其实主要的是，我们要从后往前，找到 递增数序的 第一个下降的元素，然后找到这个元素之后，在从后往前继续寻找，
     * 寻找到 后面递增序列中的第一个 比这个元素大的元素，然后将他俩的位置进行替换，替换完成之后，我们还需要对后面的数，进行 一个反转
     * <p>
     * 其实核心就是一个点：在字典序中，找到比当前排列大一点点的那个数组，然后我们只需要做局部排列就行了
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        // 先寻找第一个 下降点
        int length = nums.length;
        int start = -1;
        for (int i = length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                start = i;
                break;
            }
        }

        // 再从 第一个下降点的位置，从后面找，第一个比他大的点，进行替换位置
        if (start >= 0) {
            int end = length - 1;
            while (end >= 0 && nums[end] <= nums[start]) {
                end--;
            }
            // 替换位置
            swap3(nums, start, end);
        }

        // 后面的再进行 旋转
        reveres(nums, start + 1, length - 1);
    }

    private void swap3(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    private void reveres(int[] nums, int left, int right) {
        while (left < right) {
            swap3(nums, left, right);
            left++;
            right--;
        }
    }


    /**
     * 287. 寻找重复数
     * <p>
     * 题目意思：给定一个 数组，让我们找出里面的重复数，给定了 N + 1 个位置，但是里面的数只能是 1 - N 这几个数，所以肯定有一个重复的
     *
     * 题目解析：我们将 nums 看成一个链表，这样的话，我们就可以按照循环链表，寻找相交节点来寻找出来我们要的位置了
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        // 题目的意思是肯定会有重复的元素
        int p = 0;
        int q = slow;
        while (p != q) {
            p = nums[p];
            q = nums[q];
        }

        return p;
    }

}
