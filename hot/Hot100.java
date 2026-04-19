package hot;

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
     * 这样我们的结果值 极速 left * right ，就是当前位置的结果集了
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
     *
     * 题目意思：给定一个二叉树，我们判断路径和 = targetSum 的 总共会有多少条
     *
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
     *
     * 题目意思：给定一个二叉树 root, 让我们判断 p 和 q 的公共祖先是哪个
     *
     * 题目解析：直接 DFS寻找即可
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
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

}
