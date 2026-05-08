package hot.hot100V2;

import hot.hot150.Node;
import listNode.ListNode;
import treeNode.TreeNode;

import java.util.*;

/**
 *
 * @summary Hot100
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/29 09:57:18
 */
public class Hot100 {

    /**
     * 1. 两数之和
     * <p>
     * 题目意思：找出 nums 里面 两数之和 = target 的值
     * <p>
     * 题目解析：使用 map 记录即可
     *
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
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
     * 题目意思：给定一组数组，如果数组里面单词都是相同的，那么就将他们放一起
     * <p>
     * 题目解析：初始化一个 map，如果一样的话，就给他放一块去
     *
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String word : strs) {
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);

            String sortWord = String.valueOf(charArray);

            map.putIfAbsent(sortWord, new ArrayList<>());
            map.getOrDefault(sortWord, new ArrayList<>()).add(word);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * 128. 最长连续序列
     * <p>
     * 题目意思；给定一个数组，判断里面连续的子序列的最长序列
     * <p>
     * 题目解析：我们使用一个 set 进行存储，然后每次判断 +1 值有没有就行了，这样就能找到对应的 递增子序列
     *
     */
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> map = new HashSet<>();
        for (int num : nums) {
            map.add(num);
        }

        int max = 0;

        // 这里需要提高效率，这里需要直接使用 map 进行 遍历
        for (Integer num : map) {
            int total = 1;
            // 我们需要找到 第一个开头的元素，也就是 !map.contain(num - 1)
            if (!map.contains(num - 1)) {
                int next = num + 1;
                while (map.contains(next)) {
                    total++;
                    next++;
                }
            }
            max = Math.max(max, total);
        }

        return max;
    }

    ////////////////////////////////////// 双指针 ////////////////////////////////////////


    /**
     * 283. 移动零
     * <p>
     * 题目意思: 给定一个数组，我们要把里面的 0 的位置，全部移动到最后面
     * <p>
     * 题目解析：我们只需要使用一个 快慢指针，满指针走 != 0 的位置
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int length = nums.length;
        int slow = 0;

        for (int num : nums) {
            if (num != 0) {
                nums[slow++] = num;
            }
        }

        // 到这里已经移动了后面的 0 了，我们需要将后续的位置进行补 0
        while (slow < length) {
            nums[slow++] = 0;
        }
    }

    /**
     * 11. 盛最多水的容器
     * <p>
     * 题目意思：给定一个数组，让我们看最多能装多少水
     * <p>
     * 题目解析：依旧双指针，然后最多水的位置 是和 左右两边最小边决定的，我们不断的 缩小我们的指针范围，寻找大水位即可
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int water = 0;

        while (left < right) {
            int min = Math.min(height[left], height[right]);
            water = Math.max(water, (right - left) * min);
            // 注意，我们需要进行边的移动
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return water;
    }


    /**
     * 15. 三数之和
     * <p>
     * 题目意思：给定一个数组，让我们找出 中间 三个数的和 = 0 的所有结果值
     * <p>
     * 题目解析：使用 三指针，我们固定一个指针，然后 left = i + 1，right = length - 1，然后不断的寻找即可
     * 这题注意的点就行，我们需要对返回的结果值，进行 去重过滤
     * 注意 因为我们需要指针寻找，所以我们这个 nums 一定要是排序好的一个状态
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;

        // 这里一定要排序
        Arrays.sort(nums);

        for (int i = 0; i < length; i++) {
            // 如果当前值 已经 大于0 了，那么就不需要再往下走了
            if (nums[i] > 0) {
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 固定剩余的两个指针
            int left = i + 1;
            int right = length - 1;

            while (left < right) {
                // 开始寻找我们要的结果值
                int target = nums[i] + nums[left] + nums[right];
                if (target == 0) {
                    List<Integer> list = Arrays.asList(nums[i], nums[left], nums[right]);
                    result.add(list);

                    // 这里也要过滤重复元素
                    while (left < length - 1 && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while (right > 0 && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // 需要继续寻找其他的
                    left++;
                    right--;
                } else if (target < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }


    /**
     * 42. 接雨水
     * <p>
     * 题目意思：给定一个 数组，数组中的每个元素代表一个高度，用这个接雨水问我们能接多少雨水
     * <p>
     * 题目解析：这题的核心就是一个点，想通了，答案的解法也就来了
     * 当前值能接到的最大的雨水 = min( 左边界，右边界 ) - height(i) ( 当前位置的高度 )
     * <p>
     * 所以我们的解法也就出来了，依旧使用双指针，去寻找每一个位置的雨水，然后累加
     * 但是这里面双指针还需要加一个东西，就是 左边界 + 右边界
     * leftMax rightMax
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int length = height.length;
        if (length == 0) {
            return 0;
        }

        // 设定我们的需要的边界值和指针条件
        int left = 0;
        int right = length - 1;

        int leftMax = 0;
        int rightMax = 0;

        int water = 0;

        // 开始遍历累加我们的水位
        while (left < right) {
            if (height[left] < height[right]) {
                // 左边界的值更小，我们就应该先算左边界，因为右边界肯定比这个大，所以这时候左边界的就是 min 的值
                if (leftMax < height[left]) {
                    // 如果出现了一个更大的，需要更新 leftMax
                    leftMax = height[left];
                } else {
                    // 添加水位
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (rightMax < height[right]) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }


    /////////////////////////////////////////// 滑动窗口 /////////////////////////////////////////////


    /**
     * 3. 无重复字符的最长子串
     * <p>
     * 题目意思：给定一个字符串，我们要拿到该字符串中间的的无重复的 最长子串的长度
     * <p>
     * 题目解析：使用 滑动窗口 + map 存字符和位置的关系，当我们遇到一个已经有的字符，就滑动窗口，顺便更新我们要求的值
     * <p>
     * abcaaddd
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int length = s.length();

        int max = 0;
        int left = 0;

        // 窗口移动
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // 这里更新窗口
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }

            map.put(c, i);
            // 更新最大值
            max = Math.max(max, i - left + 1);
        }

        return max;
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * <p>
     * 题目意思：给定两个单词，一个 s, 一个 p ，让我们找出他的所有字母异或位 的所有位置的 下标 index
     * 什么是字符异位词，就是  abc cba 这两个单词组成都一样就行
     * <p>
     * 题目解析：我们依旧可以使用滑动窗口，选定 p 长度的窗口不断的滑动
     * 然后如何判断 一样，我们使用两个 char[] 去存储每个字符，然后使用 arrays.equals 进行比较
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();

        int sLength = s.length();
        int pLength = p.length();
        if (pLength > sLength) {
            return result;
        }

        // 使用两个字符数组，放入值，然后进行比较，注意，一开始只放 p 长度大小的即可
        char[] sChars = new char[26];
        char[] pChars = new char[26];

        for (int i = 0; i < pLength; i++) {
            sChars[s.charAt(i) - 'a']++;
            pChars[p.charAt(i) - 'a']++;
        }

        // 判断起始位置是否一样
        if (Arrays.equals(sChars, pChars)) {
            result.add(0);
        }

        // 开始按照 pLength 的长度作为窗口滑动, 注意窗口要从 1 开始滑动，然后每次
        for (int i = pLength; i < sLength; i++) {
            // cabd abc cab 是第一步查找到的，所以我们需要将 sChars 窗口中的 c 移除 ，d 添加进去
            sChars[s.charAt(i - pLength) - 'a']--;
            sChars[s.charAt(i) - 'a']++;
            if (Arrays.equals(sChars, pChars)) {
                result.add(i - pLength + 1);
            }
        }

        return result;
    }


    ////////////////////////////////////////// 子串 //////////////////////////////////////////

    /**
     * 560. 和为 K 的子数组
     * <p>
     * 题目意思：给定一个 nums 数组，还有一个 k = 2 ，问我们这个数组里面 有几个可以组成 K 的子数组
     * <p>
     * 题目解析：这题的思路其实很巧妙，可以用 累加和去做
     * 遍历 nums, 使用 sum 累加每一个的和，1 2 3 = 1 3 6
     * 只要 出现 6-3 这种 = k 那么就是我们要的值，兑换成代码的话就是
     * map.contain(sum - k) 有值，那么我们的结果值就需要添加进去
     * <p>
     * k = 7
     * 1 3 4 7
     * 1 4 8 15
     * 8 - 7 = 1 存在。count+=value
     * 18 - 7 = 8 存在。coUnt+=value
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        // 累加值 出现次数
        Map<Integer, Integer> map = new HashMap<>();
        // 注意，这里一定要做一个初始化
        map.put(0, 1);

        int sum = 0;
        int total = 0;

        for (int num : nums) {
            sum += num;

            // 如果出现了结果值，那么就加进去
            if (map.containsKey(sum - k)) {
                total += map.get(sum - k);
            }

            // 注意，这里记录的是，某一个累加和出现的次数
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return total;
    }


    /**
     * 239. 滑动窗口最大值
     * <p>
     * 题目意思：给定一个数组，然后给定了一个窗口的大小 k ，让我们拿到这个 k 窗口下的 每个窗口的最大值
     * <p>
     * 题目解析：该题的思路也很巧妙 我们需要使用一个最大栈，将 数据入栈，当遇到比他大的就所有的值，然后
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 这题的核心有三个点，一个是同一个最大栈，只要新数据 大于栈里面的数据，就把里面的小的数据拿出来
        // 然后我们需要控制窗口的大小，进行窗口的 移动，还有如果满足的话，就将结果值放进去

        int length = nums.length;
        int[] result = new int[length - k + 1];
        int index = 0;

        // 注意，这里存的是 下标
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                // 当前里面有比她小的，直接丢掉
                deque.pollLast();
            }

            // 注意，这里一定要往后面放，这样可以保证顺序
            deque.offerLast(i);

            // 已经满足窗口大小了，需要移动窗口了, 这个 leftWindow 表示这个窗口下，左边界的最小值
            int leftWindow = i - k + 1;
            while (deque.peekFirst() < leftWindow) {
                deque.pollFirst();
            }

            // 走到这里可以满足窗口大小了，放入我们需要的值, 因为 窗口滑动移动过滤掉了超出的值，所以这里只要大于 窗口，就放值
            // 注意注意！！！！ 这里一定是 >=
            if (i >= k - 1) {
                // 注意，一定是放 最开始的那个位置，因为那个位置的值是最大的
                result[index++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    /*
        1 3 1 3 5 3 6 7
            3 3 5 5 6 7
        使用一个栈, 我们按照顺序对 数字里面的值进行入栈：
        1: 1
        3: 1 丢弃，栈： 3
        1： 1 保留，栈：1 3，这里需要拿第一个值，i=2
        3: 3：入栈 1 丢弃：栈内 3 这时候我们需要判断一个值，极速 栈内的数据有没有 超出 k 的范围了
     */


    /**
     * 76. 最小覆盖子串
     * <p>
     * 题目意思：给定两个字符串，一个 S ，一个 t, 问我们 s 中最小位置的 覆盖 t 的子串是什么
     * <p>
     * 题目解析：依旧是滑动窗口，但是这题有个不一样的点就是，我们如何判断 子串 覆盖了 t
     * 这里使用两个map ，里面存的是  need 里面具体某个单词的数量，如果 window 里面的数量完全满足了，这个就是我们 要找的值
     * 然后这里就可以滑动窗口，找下一个值了
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        // 这题的核心就是 我们需要使用两个 map 去存储 s t 中每个元素出现的值，然后通过里面的 数量判断 是否有相等，这样我们才方便去移动我们的窗口
        int sLength = s.length();
        int tLength = t.length();
        if (tLength > sLength) {
            return "";
        }

        // 初始化两个窗口
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 初始化我们移动窗口所需要的所有的参数
        int valid = 0;
        int start = 0;
        int length = Integer.MAX_VALUE;
        // 左指针
        int left = 0;

        // 开始移动寻找
        for (int i = 0; i < sLength; i++) {
            char c = s.charAt(i);
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判断窗口是否已经满足条件
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            // 这里要 while ，因为窗口还会动，可能还会有其他的只
            while (valid == need.size()) {
                // 判断是否需要更改结果值
                if (i - left + 1 < length) {
                    start = left;
                    length = i - left + 1;
                }

                // 移动左边窗口
                char d = s.charAt(left);
                left++;

                // 如果 need 窗口中包含这个数，需要对 valid + window 里面的数据进行操作
                if (need.containsKey(d)) {
                    // 因为这里移动了，所以需要更改 valid
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return length == Integer.MAX_VALUE ? "" : s.substring(start, start + length);
    }

    ////////////////////////////////////// 普通数组 ////////////////////////////////////////

    /**
     * 53. 最大子数组和
     * <p>
     * 题目意思：给定一个数组，判断里面最大的 子数组和是多少
     * <p>
     * 题目解析：这是一题标准的 kadane 算法，我们需要两个值，一个 cur ,一个 max
     * 然后判断 当前值 nums[i] 和 cur + nums[i] 那个大，找最大的
     * 简单来说，就是没遍历到一个节点，就用这个节点和历史数据进行判断，如果满足某种条件，就使用当前值进行替换
     *
     * @param nums
     * @return
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
            max = Math.max(max, cur);
        }

        return max;
    }

    /**
     * 56. 合并区间
     * <p>
     * 题目意思：给定一个二维数组，我们要将里面的数据进行合并，比如 15 24 -- 15 | 14 25 15
     * 也就是有交叉的范围全部进行合并
     * <p>
     * 题目解析：我们使用 下个个值得开始 和 上一个值得 结束进行比较即可
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return null;
        }

        // intervals 需要对 start 进行排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);


        List<int[]> result = new ArrayList<>();

        int start = intervals[0][0];
        int end = intervals[0][1];

        // 开始遍历
        for (int i = 1; i < n; i++) {
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            if (nextStart > end) {
                // 满足结果值了，需要如结果集
                result.add(new int[]{start, end});

                // 记住这里需要更新 start + end
                start = nextStart;
                end = nextEnd;
            } else {
                // 到这里就代表有交叉，需要进行比较合并
                end = Math.max(end, nextEnd);
            }
        }

        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
    }

    /**
     * 189. 轮转数组
     * <p>
     * 题目意思：给定一个 nums + 一个 k ,然后让我们将数组轮转 K 个位置
     * <p>
     * 题目解析，真个数组旋转，然后 0 - k-1, 然后 k - length - 1 旋转即可
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
        reverse(nums, 0, length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, length - 1);
    }

    private void reverse(int[] nums, int left, int right) {
        while (right > 0 && left < nums.length && left < right) {
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
     * 题目意思：给定一个数组，让我们拿到 每个元素 不带自己的 其他数组的 乘积
     * <p>
     * 题目解析：我们使用 两个数组，left + right ，分别计算 左边 + 右边的乘积
     * 然后结果值的时候 left * right 即可
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return new int[]{};
        }

        // 初始化三个结果集，left + right + result
        int[] left = new int[length];
        int[] right = new int[length];
        int[] result = new int[length];

        // 计算左边乘积,记住，左右两边一定要初始化
        left[0] = 1;
        for (int i = 1; i < length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        right[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < length; i++) {
            result[i] = left[i] * right[i];
        }

        return result;
    }

    /**
     * 41. 缺失的第一个正数
     * <p>
     * 题目意思：给你定一个数字，里面 有 1235 类似这种
     * 让我们找到缺失的 第一个正数，比如 1235 缺的第一个就是 4
     * <p>
     * 题目解析：我们使用一个 set 将 nums 放入，然后遍历一直 1 开始的只， ++ ，第一个没找到的值就是我们需要的值
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int result = 1;
        while (set.contains(result)) {
            result++;
        }

        return result;
    }


    //////////////////////////////////////////// 矩阵 ////////////////////////////////////////////////////


    /**
     * 73. 矩阵置零
     * <p>
     * 题目意思：给定一个二维矩阵，如果 矩阵中某一行 or 某一列有一个 0 ，那么这这行 or 这列 都要有个 0
     * <p>
     * 题目解析：我们使用原地修改，将 第一行 + 第一列 存储 0 ，然后再次遍历，赋值所有的 0 即可
     *
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return;
        }

        // 先记录下来 第一行 or 第一列 有没有 0
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

        // 遍历 将 0 存储到 第一行 or 第一列当中
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // 再次遍历所有的值
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 再次还原第一行 和 第一类
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
     * 题目意思：给定一个二维矩阵，我们按照 顺时针的方式给他 遍历出来
     * <p>
     * 题目解析：我们使用三个边界，上下左右，不断的遍历即可
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return result;
        }

        // 设定 4 个边界
        int top = 0;
        int down = n - 1;
        int left = 0;
        int right = m - 1;

        int total = n * m;

        while (total > 0) {
            // 左 - 右，收缩  top
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
                total--;
            }
            top++;
            if (total == 0) {
                break;
            }

            // 上 - 下, 收缩 right
            for (int i = top; i <= down; i++) {
                result.add(matrix[i][right]);
                total--;
            }
            right--;
            if (total == 0) {
                break;
            }

            // 右 - 左, 收缩 down
            for (int i = right; i >= left; i--) {
                result.add(matrix[down][i]);
                total--;
            }
            down--;
            if (total == 0) {
                break;
            }

            // 下 - 上，收缩 left
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
     * 题目意思：给定一个二维矩阵，让我们给他选择 顺时针旋转一次
     * <p>
     * 题目解析：我们先转置，在 旋转
     * 转置：matrix[i][j] = matrix[j][i]
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return;
        }

        // 转置
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < m; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 在旋转
        for (int i = 0; i < n; i++) {
            swap(matrix[i]);
        }
    }

    private void swap(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int temp = nums[right];
            nums[right] = nums[left];
            nums[left] = temp;
            left++;
            right--;
        }
    }


    /**
     * 240. 搜索二维矩阵 II
     * <p>
     * 题目意思：给定我们一个 二维矩阵，但是这个二维矩阵是 每行有序，每列有序，他不是一个 完全有序的矩阵
     * 让我们查找 target 在二维矩阵中的 存不存在
     * <p>
     * 题目解析：这题依旧很巧妙，有序+查找，我们第一时间想到的就是 二分查找，这题这么二分呢
     * 我们从该 二维矩阵的 右上角作为起点，开始寻找即可
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n == 0) {
            return false;
        }

        // 找 左上角的起点位置
        int top = 0;
        int right = m - 1;

        while (top < n && right >= 0) {
            int num = matrix[top][right];
            if (num == target) {
                return true;
            }

            // 从这里 我们需要开始寻找
            if (num > target) {
                // 再往左边找
                right--;
            } else {
                // 比他大了，需要再往下面找
                top++;
            }
        }

        return false;
    }

    /////////////////////////// 160. 相交链表 /////////////////////////////////


    /**
     * 160. 相交链表
     * <p>
     * 题目意思：给定两个链表，判断这两个链表是不是相交链表
     * <p>
     * 题目解析：我们只需要遍历完成之后，从另外一个链表开头再次遍历，就行了
     * A + C + B = A + B + C 这样就肯定能找到我们的 链表相交的节点
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p = headA;
        ListNode q = headB;

        // 题目的意思是肯定有相交链表
        while (p != q) {
            if (p == null) {
                p = headB;
            } else {
                p = p.next;
            }

            if (q == null) {
                q = headA;
            } else {
                q = q.next;
            }
        }

        return p;
    }

    /**
     * 206. 反转链表
     * <p>
     * 题目意思：给定一个链表，让我们将链表进行反转
     * <p>
     * 题目解析：我们使用一个pre 节点，作为 然后每次将 cur 指针指向他，更改指针就行了
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
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
     * 234. 回文链表
     * <p>
     * 题目意思：给定一个链表，让我们判断他是不是会问 链表， 1221 这样就是
     * <p>
     * 题目解析：这题是几种问题的整合
     * 1：寻找中间节点，使用快慢指针
     * 2：断开链表，拿到两个链表，前 and 后
     * 3：反转后面的链表
     * 4：开始比较
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        ListNode mid = getMid(head);

        // 拿到下半部分
        ListNode next = mid.next;
        // 注意，这里一定要断开
        mid.next = null;

        // 反转下半部分
        ListNode q = reverse(next);
        ListNode p = head;

        while (p != null && q != null) {
            if (p.val != q.val) {
                return false;
            }
            p = p.next;
            q = q.next;
        }

        return true;
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

    private ListNode getMid(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 141. 环形链表
     * <p>
     * 题目意思：给定一个链表，让我们判断是否是环形链表
     * <p>
     * 题目解析：使用快慢指针即可， 如果遇到相遇的话，肯定是环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }

        return false;
    }


    /**
     * 142. 环形链表 II
     * <p>
     * 题目意思：依旧环形链表，只不过这题是让我们能找到环形的节点
     * <p>
     * 题目解析：和 环形链表做法 一样，只不过相遇的时候，两个变成一起走，那么他肯定就能走到我们相遇的那个点
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // 走到这里，需要重新走，slow 从头开始走
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
     * 题目意思：给定两个有序链表，让我们合并成一个新的链表
     * <p>
     * 题目解析：两个链表使用一个指针，不断遍历即可
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        ListNode l1 = list1;
        ListNode l2 = list2;

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

        // 补全后面的数据
        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }

        return dummy.next;
    }


    /**
     * 2. 两数相加
     * <p>
     * 题目意思：给定两个链表，让我们把两个链表的结果数据相加起来，如果 9 + 3 = 12 这种，当前位置放 2 ，1 往下个位置进一位
     * <p>
     * 题目解析：两个指针遍历，直接相加即可，然后使用一个 pre 来记录 当前相加结果值是否有多余的 1
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;

        int pre = 0;

        while (l1 != null || l2 != null) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;
            int value = v1 + v2 + pre;
            if (value >= 10) {
                pre = 1;
                cur.next = new ListNode(value - 10);
            } else {
                pre = 0;
                cur.next = new ListNode(value);
            }
            cur = cur.next;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (pre > 0) {
            // pre 还有值，再放进去
            cur.next = new ListNode(pre);
        }

        return dummy.next;
    }


    /**
     * 19. 删除链表的倒数第 N 个结点
     * <p>
     * 题目意思：给定一个链表，让我们删除该链表的 倒数 第 N 个节点
     * <p>
     * 题目解析：这题 我们需要先 拿到 该链表的长度，然后 有了链表的长度，我们就能找到该删除的倒数第 N 个节点的 前面一个节点
     * 然后 pre.next = pre,next.next 就给他删除掉了
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0) {
            return head;
        }

        ListNode cur = head;
        int length = 0;
        while (cur != null) {
            length++;
            cur = cur.next;
        }

        // 开始计算我们要删除的具体节点长度，length=5,n=2,要删除的位置就是 5-2=3 第三个位置
        int step = length - n;
        if (step == 0) {
            // 要删除的是 开头节点
            return head.next;
        }

        // 寻找 pre 节点
        ListNode pre = head;
        for (int i = 1; i < step; i++) {
            pre = pre.next;
        }

        pre.next = pre.next.next;

        return head;
    }

    /**
     * 24. 两两交换链表中的节点
     * <p>
     * 题目意思：给定一个链表，让我们两两交换 链表中的节点
     * <p>
     * 题目解析：我们需要找到 4个核心的点，pre first second third ，然后对这4个点 进行交换处理即可
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;

        ListNode first = head;


        while (first != null && first.next != null) {
            // 两两反转，我们 first + second 一定不能为空
            ListNode second = first.next;
            ListNode third = second.next;

            // 两两替换 pre-first-second-third --> pre-second-first-third
            pre.next = second;
            second.next = first;
            first.next = third;

            // 需要移动指针
            pre = first;
            first = third;
        }

        return dummy.next;
    }


    /**
     * 25. K 个一组翻转链表
     * <p>
     * 题目意思：和 链表 两两反转的题目差不多，只不过这一题改成了 第 K 个反转
     * <p>
     * 题目解析：和 两两交换差不多，我们要找到 K 个节点的位置，依旧需要几个核心的 指针点位
     * pre + end
     * pre 前置指针，控制后续的反转
     * end 需要反转的具体结束位置，
     * 然后
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }

        // 我们需要三个指针 pre + end + start
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        // 注意这里的开始 一定要从 dummy 开始，不能从 head 开始
        ListNode end = dummy;

        while (end != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }

            // 我们需要三个位置节点，童谣这里需要拿到 start 节点，用来后续的指针移动
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;

            pre.next = reverse2(start);
            start.next = next;

            pre = start;
            end = start;
        }

        return dummy.next;
    }

    private ListNode reverse2(ListNode head) {
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
     * 138. 随机链表的复制
     * <p>
     * 题目意思：给定一个链表，让我们深拷贝这个链表
     * <p>
     * 题目解析：使用 map 存储 老，新 链表节课
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> map = new HashMap<>();

        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        // 再次遍历，这次赋值 next + random
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }


    /**
     * 148. 排序链表
     * <p>
     * 题目意思：给定一个链表，让我们给他排序一下
     * <p>
     * 题目解析：使用 归并排序即可，给 head 拆掉，拿到 0 - length-1
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode mid = getMid2(head);

        ListNode next = mid.next;
        mid.next = null;
        ListNode p = head;

        ListNode l1 = sortList(p);
        ListNode l2 = sortList(next);

        return sortList(l1, l2);
    }

    private ListNode getMid2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode sortList(ListNode p, ListNode q) {
        ListNode l1 = p;
        ListNode l2 = q;

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
     * 题目意思：就是表面上面的意思，可以使用 DFS 也可以使用 BFS
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        if (n == 0) {
            return null;
        }

        ArrayDeque<ListNode> deque = new ArrayDeque<>();
        for (ListNode node : lists) {
            if (node != null) {
                deque.offer(node);
            }
        }

        while (deque.size() >= 2) {
            ListNode l1 = deque.poll();
            ListNode l2 = deque.poll();

            ListNode node = sortList(l1, l2);
            deque.offer(node);
        }

        return deque.poll();
    }

    ///////////////////////// 二叉树的中序遍历 ///////////////////////////

    /**
     * 94. 二叉树的中序遍历
     * <p>
     * 题目意思：中序遍历二叉树
     * <p>
     * 题目解析：递归即可
     *
     * @param root
     * @return
     */
    List<Integer> result = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        inorderTraversalDFS(root);
        return result;
    }

    public void inorderTraversalDFS(TreeNode root) {
        if (root == null) {
            return;
        }

        inorderTraversalDFS(root.left);

        result.add(root.val);

        inorderTraversalDFS(root.right);
    }

    /**
     * 104. 二叉树的最大深度
     * <p>
     * 题目意思：让我们拿出 二叉树的 最大深度
     * <p>
     * 题目解析：使用递归即可，或者使用 BFS ，用一个栈去接收我们的数据
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
     * 26. 翻转二叉树
     * <p>
     * 题目意思：给定一个二叉树，让我们将它的左右节点进行反转
     * <p>
     * 题目解析：依旧递归，
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

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }


    /**
     * 101. 对称二叉树
     * <p>
     * 题目意思：给定一个二叉树，判断他是不是对称二叉树
     * <p>
     * 题目解析：依旧递归 不过判断的节点我们需要注意下
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (left.val != right.val) {
            return false;
        }

        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }


    /**
     * 543. 二叉树的直径
     * <p>
     * 题目意思: 给定一个二叉树，让我们求出它的 最大的直径
     * <p>
     * 题目解析：该题和 求 二叉树的最大路径和  一模一样，只不过 最大路径和，需要计算的是每个节点的数据累加
     * 该题要的是直径
     * 其实核心就两点，选中一个当前点 root
     * root 作为选择点，那么就是 1 + max(root.left, root.right)
     * root 作为路径点，那么就是 1 + left + right
     * 其中，我们在路径点的时候，记录这个最大值 max
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

        // 作为路径点
        max = Math.max(max, left + right);

        // 作为选择点
        return 1 + Math.max(left, right);
    }

    /**
     * 102. 二叉树的层序遍历
     * <p>
     * 题目意思：给二叉树进行层序遍历
     * <p>
     * 题目解析：使用一个 队列即可
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
            int size = deque.size();
            List<Integer> subResult = new ArrayList<>();
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
     * 题目意思：给定一个有序的数组，让我们给他转变成 二叉搜索树
     * 左 - 根 - 右，以此有序的，就是二叉搜索树
     * <p>
     * 题目解析：直接做就行
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

    private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        // TODO 注意，这里一定是 >
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        int rootValue = nums[mid];

        TreeNode root = new TreeNode(rootValue);
        root.left = sortedArrayToBST(nums, left, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, right);

        return root;
    }

    /**
     * 98. 验证二叉搜索树
     * <p>
     * 题目意思：给定一个二叉树，让我们判断他是不是 二叉搜索树
     * 什么是 二叉搜索树，左边比右边小，这就是 二叉搜索树
     * <p>
     * 题目解析：我们只需要中序遍历，判断即可，然后拿一个 pre 存储上一个节点的数据
     *
     * @param root
     * @return
     */
    boolean valid = true;
    TreeNode pre = null;

    public boolean isValidBST(TreeNode root) {
        isValidBSTDFS(root);
        return valid;
    }

    public void isValidBSTDFS(TreeNode root) {
        if (root == null) {
            return;
        }

        isValidBSTDFS(root.left);

        if (pre != null && pre.val >= root.val) {
            valid = false;
            return;
        }

        pre = root;


        isValidBSTDFS(root.right);
    }


    /**
     * 230. 二叉搜索树中第 K 小的元素
     * <p>
     * 题目意思：给定一个二叉搜索树，让我们拿出他的 第 K 个元素
     * <p>
     * 题目解析：我们直接中序遍历即可
     *
     * @param root
     * @param k
     * @return
     */
    private int step;
    private int result2;

    public int kthSmallest(TreeNode root, int k) {
        step = k;
        kthSmallestDFS(root);

        return result2;
    }

    public void kthSmallestDFS(TreeNode root) {
        if (root == null) {
            return;
        }

        kthSmallestDFS(root.left);

        step--;
        if (step == 0) {
            result2 = root.val;
            return;
        }

        kthSmallestDFS(root.right);
    }


    /**
     * 199. 二叉树的右视图
     * <p>
     * 题目意思：给定一个二叉树，让我们拿出他的右视图
     * <p>
     * 题目解析：直接使用 队列，遍历 二叉树即可
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
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
     * 题目意思：给定一个二叉树，让我们讲这个二叉树转换成 链表
     * <p>
     * 题目解析：这题理解了意思就很简单了，他就是讲二叉树所有的放到 右节点上面，所以只要左结点有值，就放入右节点
     * 其中 我们需要使用一个 pre 存储上一个节点，然后判断 上一个节点 有没有值，有值就放右节点即可
     *
     * @param root
     */
    TreeNode pre2 = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        if (pre2 != null) {
            pre2.right = root;
            pre2.left = null;
        }

        pre2 = root;

        TreeNode left = root.left;
        TreeNode right = root.right;

        flatten(left);
        flatten(right);
    }


    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * <p>
     * 题目意思：给定两个数组，一个是前序，一个是中序，我们使用这个构成成一个完整的 二叉树
     * <p>
     * 题目解析：该题就是使用 前序 和 中序的 特性来构建，前序是 根-左-右 中序是 左-根-右
     * 这样我们使用 中序存储到一个 map 里面，然后遍历 前序，通过不断的找 根 - 左 - 右 就能构建出我们的二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLength = preorder.length;
        int inLength = inorder.length;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        // 因为 前序遍历 根节点就在 0 的起始位置
        return buildTreeByPre(preorder, 0, 0, preLength - 1, map);
    }

    public TreeNode buildTreeByPre(int[] preorder, int rootIndex, int left, int right, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        int rootValue = preorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);

        // 这里是 中序遍历中的 根节点的 左右分界线，用来下面设置 左右节点的数据
        Integer inRootIndex = map.get(rootValue);

        int leftSize = inRootIndex - left;

        // 左边就是 left - inRootIndex - 1
        root.left = buildTreeByPre(preorder, rootIndex + 1, left, inRootIndex - 1, map);

        // 右边就是 inRootIndex+1 - right
        root.right = buildTreeByPre(preorder, rootIndex + leftSize + 1, inRootIndex + 1, right, map);

        return root;
    }

    /**
     * 437. 路径总和 III
     * <p>
     * 题目意思：给定一个 二叉树，还有一个 总和 targetSum
     * 让我们判断 在这个 二叉树上面，有 多少条路径 的值 = targetSum
     * <p>
     * 题目解析：我们每次遍历的时候 对 targetSum 和 当前 root.val 进行相减，然后判断有没有 0
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

    public int pathSumDFS(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        int count = 0;
        if (targetSum == root.val) {
            count++;
        }

        count += pathSumDFS(root.left, targetSum - root.val);
        count += pathSumDFS(root.right, targetSum - root.val);

        return count;
    }

    /**
     * 236. 二叉树的最近公共祖先
     * <p>
     * 题目意思：给定两个 二叉树 p q，让我们判断 他们的公共祖先是哪个
     * <p>
     * 题目解析：直接使用 递归去寻找即可，寻找左边 + 寻找右边，如果左右
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        // 分别从左右两边找
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 两边都找到了，就是当前 root
        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;
    }

    /**
     * 124. 二叉树中的最大路径和
     * <p>
     * 题目一个：给定一个二叉树，让我们寻找他最大的那条路径的 路径和
     * <p>
     * 题目解析：这题和 二叉树的直径一样，就是当前点，作为拐点 or 当前点 左右选择点的，两种不同的亲看过
     *
     * @param root
     * @return
     */
    int max2 = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        maxPathSumDFS(root);

        return max2;
    }

    public int maxPathSumDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 寻找左右两边的 最大值，这里一定要和 0 作为一个比较，万一 小于 0 呢
        int leftValue = Math.max(0, maxPathSumDFS(root.left));
        int rightValue = Math.max(0, maxPathSumDFS(root.right));

        // 作为选择点
        max2 = Math.max(max2, leftValue + rightValue + root.val);

        // 作为拐点
        return Math.max(leftValue, rightValue) + root.val;
    }

    ///////////////////////////////////////// 图论 ///////////////////////////////////////////


    /**
     * 200. 岛屿数量
     * <p>
     * 题目意思：给定一个二维数组，里面 1 代表陆地，0代表水
     * 问我们 这个 里面有多少个岛屿
     * <p>
     * 题目解析：直接遍历即可，遇到陆地，开始往外面找，遇到陆地再给他设置成 0 即可
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    isLand(grid, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    public void isLand(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }

        if (grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';

        // 上下左右扩散
        isLand(grid, i - 1, j);
        isLand(grid, i + 1, j);
        isLand(grid, i, j - 1);
        isLand(grid, i, j + 1);

    }

    /**
     * 994. 腐烂的橘子
     * <p>
     * 题目意思：给定一个 二维数组，里面 1 是新鲜橘子，2 是腐烂橘子，每次 腐烂橘子会扩散周围的新鲜橘子，问我们几次能腐烂完
     * <p>
     * 题目解析：我们需要拿到所有的腐烂橘子的位置 + 拿到所有新鲜橘子的数量，防止扩散完成之后，还有新鲜橘子
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        if (n == 0) {
            return 0;
        }

        // 初始化腐烂橘子的位置 + 新鲜橘子的数量
        Deque<int[]> deque = new ArrayDeque<>();
        int fresh = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    deque.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        // 一个新鲜橘子都没有
        if (fresh == 0) {
            return 0;
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;

        // 开始从腐烂的橘子地方 开始扩散, 注意，一定要还有新鲜橘子的时候扩散
        while (!deque.isEmpty() && fresh > 0) {
            count++;

            // 从腐烂的地方开始扩散
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] node = deque.poll();

                for (int[] dir : dirs) {
                    int x = node[0] + dir[0];
                    int y = node[1] + dir[1];

                    if (x < 0 || y < 0 || x >= n || y >= m) {
                        continue;
                    }

                    // 新鲜的橘子，给他腐烂掉
                    if (grid[x][y] == 1) {
                        // 一定要设置成 腐烂，且新鲜橘子数量要--
                        grid[x][y] = 2;
                        fresh--;
                        deque.offer(new int[]{x, y});
                    }
                }
            }
        }

        return fresh > 0 ? -1 : count;
    }

    /**
     * 207. 课程表
     * <p>
     * 题目意思：给定一个 numCourses ，表示有多少课程，然后给定一个 prerequisites 表示学 某门课之前需要学什么
     * 其中，[1,0] 表示 学习 1 之前需要 学 0
     * <p>
     * 题目解析：我们需要两个东西，一个是构建 学习 DAG 图，就比如 [1,0] 学 1 之前需要学 0 ，那么就是 0 -> 1
     * 还有一个就是入度数组，表现 学 1 之前还需要提前学多少门课，比如 [1, 0] 就代表学 1 之前还需要学 一门课
     * 只有 入度 是 0 那么才能开始学
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 初始化 DAG图 + 入度数组
        List<List<Integer>> DAG = new ArrayList<>();
        int[] pre = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            DAG.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            int a = prerequisite[0];
            int b = prerequisite[1];

            // [1,0] - [a,b] 学完B才能学A
            DAG.get(b).add(a);

            // 学 a 之前需要在学多少门课程
            pre[a]++;
        }

        // 使用一个队列，存储所有入度 = 0 ，也就是可以直接学的课程
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (pre[i] == 0) {
                deque.offer(i);
            }
        }

        // 没有能直接学的
        if (deque.isEmpty()) {
            return false;
        }

        int count = 0;
        // 开始学习
        while (!deque.isEmpty()) {
            count++;

            // 注意 课程只能一门一门的学
            Integer node = deque.poll();
            List<Integer> subNodes = DAG.get(node);
            for (Integer sub : subNodes) {
                // sub 这门课的前置课程已经学完一门了，需要将这门课的入度 --
                pre[sub]--;

                // 又可以在学了
                if (pre[sub] == 0) {
                    deque.offer(sub);
                }
            }
        }

        return count == numCourses;
    }


    ///////////////////////////////////////// 回溯 ///////////////////////////////////////////


    /**
     * 46. 全排列
     * <p>
     * 题目意思：给定一个数组 nums，让我们拿到他的全排列，不然 123 拿出 123 的所有的排列
     * <p>
     * 题目解析：回溯 + 递归，因为是全排列，所以我们需要一个 used 去判断 某个元素有没有被用过
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;

        boolean[] used = new boolean[length];
        permuteDFS(nums, 0, used, new ArrayList<>(), result);

        return result;
    }

    public void permuteDFS(int[] nums, int index, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // 递归满足条件（终止条件）
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            path.add(nums[i]);

            // 开始递归遍历
            permuteDFS(nums, index + 1, used, path, result);

            // 开始回溯
            used[i] = false;
            path.removeLast();
        }
    }

    /**
     * 78. 子集
     * <p>
     * 题目意思：给定一个数组 nums，我们拿出他的所有子集，比如 123 - null, 1 12 。。。
     * <p>
     * 题目解析：依旧 回溯 + 递归
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
        // 直接添加我们的结果集
        result.add(new ArrayList<>(path));

        // 开始遍历所有的结果
        for (int i = index; i < nums.length; i++) {
            path.add(nums[i]);

            // 这里一定是 i+1 通过 i 往前 推进
            subsetsDFS(nums, i + 1, path, result);

            // 回溯
            path.removeLast();
        }
    }


    /**
     * 17. 电话号码的字母组合
     * <p>
     * 题目意思：给定一个电话号码，数字 + 字母的组合，然后给订一个 digits，他的输入值 是 23 这种
     * 问 23能有多少种字母的组合
     * <p>
     * 题目解析：依旧 递归 + 回溯，但是这里，我们每次需要固定一个 2 然后遍历 3 的所有字母，然后再遍历 2 再次遍历 3 这样拿到我们所有的结果值
     *
     * @param digits
     * @return
     */
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

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        letterCombinationsDFS(digits, 0, new StringBuilder(), result);

        return result;
    }

    public void letterCombinationsDFS(String digits, int index, StringBuilder path, List<String> result) {
        if (path.length() == digits.length()) {
            result.add(path.toString());
            return;
        }

        // 23 - 2 - abc - abc 开始遍历，设置我们的值
        int wordIndex = digits.charAt(index) - '0';
        String word = MAPPING[wordIndex];
        int length = word.length();

        for (int i = 0; i < length; i++) {
            char c = word.charAt(i);

            path.append(c);

            // 注意 这里也一定是 index + 1, 2 3 锚定了 2 ，我们就需要对 3 进行再次添加
            letterCombinationsDFS(digits, index + 1, path, result);

            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 39. 组合总和
     * <p>
     * 题目意思：给定一个数组 candidates，还有一个 target，问我们 candidates中哪些数字组合 能组成 target
     * <p>
     * 题目解析；依旧 回溯 + 递归，该题有一个不一样的点就是 数字可以重复使用，所以递归的时候 需要递归原来的位置
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

    public void combinationSumDFS(int[] candidates, int index, int sum, int target, List<Integer> path, List<List<Integer>> result) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (sum > target) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            int num = candidates[i];
            if (num > target) {
                continue;
            }

            sum += candidates[i];
            path.add(candidates[i]);

            // 注意这里可以重复，所以传入的还是 index
            combinationSumDFS(candidates, i, sum, target, path, result);

            // 回溯
            sum -= candidates[i];
            path.removeLast();
        }

    }

    /**
     * 22. 括号生成
     * <p>
     * 题目意思：给定一个数字，让我们生成所有的括号 组合 (()) ()()
     * <p>
     * 题目解析：其实就两个条件，一个是 left < n ，一个是 right <= left
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

        // 一定要先放左边
        if (left < n) {
            path.append("(");
            generateParenthesisDFS(n, left + 1, right, path, result);
            path.deleteCharAt(path.length() - 1);
        }

        // 右边不能比左边大
        if (right < left) {
            path.append(")");
            generateParenthesisDFS(n, left, right + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 79. 单词搜索
     * <p>
     * 题目意思：给定一个二维数组，然后给定一个单词，让我们在里面搜索能不能搜到这个单词
     * <p>
     * 题目解析：依旧典型的 回溯
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
        // 已经找到结束的地方了
        if (word.length() == index) {
            return true;
        }

        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }

        char c = board[i][j];
        if (c != word.charAt(index)) {
            return false;
        }

        // 一定要设置一下，防止走回头路
        board[i][j] = '#';

        // 从上下左右开始寻找，有任何一个找到了就算找到了
        if (exist(board, word, index + 1, i + 1, j) ||
                exist(board, word, index + 1, i - 1, j) ||
                exist(board, word, index + 1, i, j + 1) ||
                exist(board, word, index + 1, i, j - 1)) {
            return true;
        }

        // 回溯
        board[i][j] = c;

        return false;
    }

    /**
     * 131. 分割回文串
     * <p>
     * 题目意思：给定一个 单词 s,让我们拿到 这个 s 的所有回文串
     * <p>
     * 题目解析：依旧回溯，依旧递归，主要就是 判断条件
     * 我们遍历条件中，我们固定一个 index 点位，不断的往前找 s,然后判断这个 s 是不是回文串即可
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
        if (s.length() == index) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            // 开始分割，这里也是这题最重要的部分
            String sub = s.substring(index, i + 1);
            if (isHW(sub)) {
                path.add(sub);

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
     * 51. N 皇后
     * <p>
     * 题目意思：给定一个数字，让我们找到 所有的 N皇后的位置
     * <p>
     * 题目解析：我们记住什么是 N 皇后即可
     * 一个皇后，同一列 同一行 同一对角线，都只能有一个
     * 我们按照 行 来进行递归迭代
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n == 0) {
            return result;
        }

        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];

        char[][] queen = new char[n][n];
        // 皇后需要先初始化
        for (int i = 0; i < n; i++) {
            Arrays.fill(queen[i], '.');
        }

        // 开始遍历
        solveNQueensDFS(n, 0, col, diag1, diag2, queen, result);

        return result;
    }

    public void solveNQueensDFS(int n, int row, boolean[] col, boolean[] diag1, boolean[] diag2, char[][] queen,
                                List<List<String>> result) {
        // 遍历完所有的行了
        if (row == n) {
            // 开始插入我们的皇后
            List<String> subResult = new ArrayList<>();
            for (char[] subQueen : queen) {
                subResult.add(String.valueOf(subQueen));
            }
            result.add(subResult);
            return;
        }

        // 从 第一列开始寻找,如果 行+列+对角线都没的话，那么就代表他是一个皇后
        for (int i = 0; i < n; i++) {
            // 注意这里的 n-1 是防止出现 负数 进行的平移
            int d1 = row - i + (n - 1);
            int d2 = row + i;
            if (col[i] || diag1[d1] || diag2[d2]) {
                continue;
            }

            col[i] = diag1[d1] = diag2[d2] = true;
            queen[row][i] = 'Q';

            solveNQueensDFS(n, row + 1, col, diag1, diag2, queen, result);

            // 开始回溯
            col[i] = diag1[d1] = diag2[d2] = false;
            queen[row][i] = '.';
        }

    }


    ///////////////////////////////////////// 二分查找 ///////////////////////////////////////////

    /**
     * 35. 搜索插入位置
     * <p>
     * 题目意思:给定一个有序的数组，然后给定一个 target，让我们找出其中的日志
     * <p>
     * 题目解析
     *
     * @param nums
     * @param target
     * @return
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
     * 题目意思：给定一个二维矩阵，然后
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int left = 0;
        int right = n * m - 1;

        // 注意，二分中所有的寻找具体的位置，都是需要 <=
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / m;
            int col = mid % m;

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
     * 题目意思：从 nums 中 寻找 target 的左边位置 + 右边位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int left = findLeft(nums, target);
        // 注意这里，一定要注意，没找到的场景，或者找到的 不是 target 的值
        if (left >= nums.length || nums[left] != target) {
            return new int[]{-1, -1};
        }

        int right = left;
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
     * 二分查找，寻找 target 最左边的位置
     *
     * @param nums
     * @param target
     * @return
     */
    private int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 往前找
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 33. 搜索旋转排序数组
     * <p>
     * 题目意思：有一个 数组 nums, 经过旋转了，让我们找到 其中的 target
     * <p>
     * 题目解析：部分有序 + 寻找某个值，依旧是 二分查找，只不过我们需要找到那个有序的边，然后进行二分查找
     * *
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        // 寻找有序边，然后二分查找
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < nums[right]) {
                // 右边有序，判断 target 在不在 mid - right 中间
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // 左边有序, 判断 target 在不在 left - mid 中间
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        return -1;
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     * <p>
     * 题目意思：给定一个寻找数字，和 旋转数字寻找 target 一样，寻找最小值
     * <p>
     * 题目解析：正常数组是递增关系，旋转的话，会出现 一段递增，然后突然下降，然后再次递增
     * 我们要找的就是这个突然下降的位置
     * 如果 mid < right ，那么代表突然下降的位置 在 左边
     * 以此类推
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            // 注意这里的标签，这里最小值就在 mid - right 中间，然后我们不断的逼近这个位置即可
            if (nums[mid] > nums[right]) {
                // 最小值在这边
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * <p>
     * 题目意思：给定两个是数组，一个 nums1，一个 nums2，让我们从中间寻找他的中位数
     * <p>
     * 题目解析：我们理解什么是中位数这题就简单了
     * 中位数需要满足两个条件：
     * 1：左边元素 = 右边元素（ 最多多一个 ）
     * 2：左边最大的 < 右边最小的
     * <p>
     * L1 | R1
     * L2 | R2
     * 满足 L1 < R2 && L2 < R1 就算找到我们的中位数了
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if (n > m) {
            // 用更小的边作为计算边
            return findMedianSortedArrays(nums2, nums1);
        }

        int left = 0;
        int right = n;

        while (left <= right) {
            int mid1 = left + (right - left) / 2;
            // TODO 注意 mid2 需要满足 左右元素均衡，注意这里需要 + 1 ，防止出现负数
            int mid2 = (n + m + 1) / 2 - mid1;

            int L1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int R1 = mid1 == n ? Integer.MAX_VALUE : nums1[mid1];
            int L2 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int R2 = mid2 == m ? Integer.MAX_VALUE : nums2[mid2];

            // 判断有没有符合条件的情况
            if (L1 <= R2 && L2 <= R1) {
                // 判断奇偶
                if ((n + m) % 2 == 1) {
                    // 奇数直接从左边拿
                    return Math.max(L1, L2);
                } else {
                    return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
                }
            } else if (L1 > R2) {
                // L1 取太大了，收缩位置
                right = mid1 - 1;
            } else {
                left = mid1 + 1;
            }
        }

        return 0.0;
    }

    ///////////////////////////////////////// 栈 ///////////////////////////////////////////

    /**
     * 20. 有效的括号
     * <p>
     * 题目意思：给定一个 s ,判断他是不是()[] 这种有效的括号
     * <p>
     * 题目解析：直接使用一个栈 ，就行个了
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s == null) {
            return true;
        }

        int length = s.length();

        List<Character> pre = Arrays.asList('(', '[', '{');
        Deque<Character> deque = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (pre.contains(c)) {
                deque.push(c);
            } else {
                if (deque.isEmpty()) {
                    return false;
                }

                Character d = deque.pop();

                if (c == ')' && d != '(') {
                    return false;
                }
                if (c == ']' && d != '[') {
                    return false;
                }
                if (c == '}' && d != '{') {
                    return false;
                }
            }
        }

        return deque.isEmpty();
    }


    /**
     * 394. 字符串解码
     * <p>
     * 题目意思：给定一个字符串 s, 他大概是 2[a] = aa 2[bc] = bcbc
     * <p>
     * 题目解析：我们只需要一个栈，然后对 数字 + [ + ] 做不同的处理即可
     * 数字：入栈
     * [: 入栈，将数字 + 字母全部入栈
     * ]: 出栈，开始拼接
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        // 核心 就是 入栈 出栈，然后计算 数字 + 字母
        if (s == null) {
            return "";
        }

        int length = s.length();

        int num = 0;
        Deque<Object> stack = new ArrayDeque<>();
        StringBuilder path = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // 这里是数字
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // 入栈
                stack.push(num);
                stack.push(path.toString());

                // 一定要重置
                num = 0;
                path = new StringBuilder();
            } else if (c == ']') {
                // 出栈
                String pre = (String) stack.pop();
                int size = (int) stack.pop();

                // 这里面是 [abc] 里面的 abc
                String word = path.toString();
                // 一定要重新设置
                path = new StringBuilder(pre);
                for (int j = 0; j < size; j++) {
                    path.append(word);
                }
            } else {
                // 这里是字母
                path.toString();
            }
        }

        return path.toString();
    }

    /**
     * 739. 每日温度
     * <p>
     * 题目意思: 给定一个数组，里面代表的是 每天的温度，问我们下一天更高的温度会在第几天出现
     * <p>
     * 题目解析：这就是一题非常典型的 最大栈 的题目
     * 我们使用一个栈，往里面放值，当有更大的值出现的话，就将之前的弹出，然后放入这个更大的值，注意，弹出 + 这次的值，就能拿到我们要的
     * 相差多少天的结果集
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;

        int[] result = new int[length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 这里出现了第一个比他大的了
                result[stack.peek()] = i - stack.pop();
            }

            stack.push(i);
        }

        return result;
    }

    /**
     * 84. 柱状图中最大的矩形
     * <p>
     * 题目意思: 给定一个数组，让我们求出 最大的矩形的面积是多少
     * <p>
     * 题目解析：这题和 每日温度一样，是一个变种题，我们不断往前找，然后更新 最大矩形的面积即可
     * <p>
     * 注意，这里使用的是 单调递减的 单调栈，为什么使用 单调递减栈
     * 举个例子
     * 5 6 2 ,走到 2 的时候，因为开始变小了，所以我需要计算 5 6 位置的 最大矩形
     * 然后 我就拿到第一个 6 ，然后看前面还有没有了，如果还有 就下一个循环找到 5
     * 计算方式
     * step1：6 * 1 = 6
     * step2：5 * 2 = 10
     * 然后拿到我们的最大值
     * 注意这里 第一次一定是 pop ,第二次 一定要 peek, 因为不能取出来
     * <p>
     * 还有需要注意临界条件 = length 的情况，
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;

        for (int i = 0; i <= length; i++) {
            // 注意临界条件
            int curHeight = i == length ? -1 : heights[i];
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                // 开始取之前的数据
                Integer preIndex = stack.pop();
                int height = heights[preIndex];

                int lastIndex = stack.isEmpty() ? -1 : stack.peek();
                int width = i - lastIndex - 1;

                max = Math.max(max, height * width);
            }

            stack.push(i);
        }

        return max;
    }


    ///////////////////////////////////////// 堆 ///////////////////////////////////////////


    /**
     * 215. 数组中的第K个最大元素
     * <p>
     * 题目解析：给定一个数组，让我们 取出来数组中 第 K 大的 元素
     * <p>
     * 题目解析：使用最小堆，当数量小于 k ，直接入堆，
     * 如果数量 = K 了，看 当前值 大于 最小堆里面的值么，
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        return minHeap.peek();
    }


    /**
     * 347. 前 K 个高频元素
     * <p>
     * 题目意思：给定一个 数组，里面 有不同的元素，比如 111 22 ,我们要找出 前 K 个 高频元素
     * <p>
     * 题目解析：我们使用一个 map 去存储 nums 里面的元素 + 数量
     * 然后使用一个 堆，最大堆 or 最小堆都行，去存储我们的结果值，按照 频次 进行堆的 大小划分
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

        // 使用最小堆
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        for (Integer key : map.keySet()) {
            // 这里一定 不要搞错了，是 map.get(key) 的 value
            Integer value = map.get(key);
            if (minHeap.size() < k) {
                minHeap.offer(new int[]{key, value});
            } else if (value > minHeap.peek()[1]) {
                minHeap.poll();
                minHeap.offer(new int[]{key, value});
            }
        }

        // 到这里 留下来的就是我们要的 频次最高的 K 个元素
        int[] result = new int[k];
        int index = 0;

        while (!minHeap.isEmpty()) {
            result[index++] = minHeap.poll()[0];
        }

        return result;
    }


    ///////////////////////////////////////// 贪心算法 ///////////////////////////////////////////


    /**
     * 121. 买卖股票的最佳时机
     * <p>
     * 题目意思：prices 是每天的股票价格，我只能买卖一次，问我能拿到的最大利润是多少
     * <p>
     * 题目意思：拿最小的值，然后每次遍历，都 当前值 - 最小值，去寻找最大即可
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int price : prices) {
            min = Math.min(min, price);
            max = Math.max(max, price - min);
        }

        return max;
    }

    /**
     * 55. 跳跃游戏
     * <p>
     * 题目意思：给定 一个 nums，里面每一个值，代表的是能跳多远，问我们从开始跳，能不能跳出去
     * <p>
     * 题目解析：贪心算法，我们只需要 拿到每一步能跳的最大值，i + nums[i] 然后看当前的最大值，能不能跳出我们的循环即可
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int max = 0;
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            if (i > max) {
                // 最大的地方 都走不出去
                return false;
            }
            max = Math.max(max, i + nums[i]);
        }

        return true;
    }

    /**
     * 45. 跳跃游戏 II
     * <p>
     * 题目意思：和跳跃游戏 1 一样，这里问的是 最小多少次 能走出来
     * <p>
     * 题目解析：依旧贪心算法，每次到头的时候 count 需要 ++
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int length = nums.length;
        int max = 0;
        int count = 0;
        int end = 0;

        // TODO 这里一定要 最后一个位置不要跳了，所以一定是 length - 1
        for (int i = 0; i < length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            // TODO 这里需要注意，一定是 i = end ，我们需要使用 一个 end 去记录 max
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
     * 题目意思：给定一个 字符串 s ,我们将所有单词 只在某个地方出现的 进行划分
     * <p>
     * 题目解析：我们需要遍历 s ,将每个字符出现的最后的位置进行记录，然后就可以按照 跳跃游戏的解法一样去完成该题了
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        int length = s.length();

        // 记录每个单词出现的最后位置，这个很重要，用于计算走到了哪里
        int[] last = new int[26];
        for (int i = 0; i < length; i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        List<Integer> result = new ArrayList<>();
        int end = 0;
        int start = 0;

        for (int i = 0; i < length; i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                // 走到头了
                result.add(end - start + 1);
                start = i + 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Hot100 hot100 = new Hot100();

//        hot100.subarraySum(new int[]{1, 2, 3}, 3);

        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        hot100.searchMatrix(matrix, 5);


    }

}
