package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 数组 刷题 第三版
 *
 * @summary ArraySolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/18, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/18 17:20
 */
public class ArraySolutionV3 {


    /**
     * 88. 合并两个有序数组
     * <p>
     * 题目意思：将 nums2 中的数据，按照 排序的方式 融入到 nums1
     * <p>
     * 题目解析：我们只需要遍历 nums2，然后去和 nums1 进行判断即可
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 我们按照顺序 从大到小 放入到 nums1 上面即可

        int i = nums1.length;

        while (n > 0) {
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[i - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[i - 1] = nums2[n - 1];
                n--;
            }
            i--;
        }
    }

    /**
     * 27. 移除元素
     * <p>
     * 题目意思：将 nums 中 值 = val 的数据移除
     * <p>
     * 题目解法：直接用快慢指针即可，当 val 值，我们指针就往前面走
     * <p>
     * 3223 3
     * 22__
     */
    public int removeElement(int[] nums, int val) {
        int n = nums.length;

        int slow = 0;
        int fast = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] != val) {
                nums[slow] = nums[i];
                slow++;
            }
            fast++;
        }

        return slow;
    }

    /**
     * 26. 删除有序数组中的重复项
     * <p>
     * 题目意思：和 27 题类似，只不过这个是 删除 nums 里面的重复元素，注意题目已经是严格递增了，不然是需要排序的
     * <p>
     * 题目解法：依旧双指针，但是因为这个是 nums 自遍历，所以 快指针 开始需要1 ，慢是 0 ，然后的判断方法和 27 题一样
     */
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        int slow = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                // 这里为什么是 先 slow++ 在
                slow++;
                nums[slow] = nums[i];
            }
        }

        return slow + 1;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     * <p>
     * 题目意思：和  26 题类似，只不过这个 nums 里面可以放 2 个相同的元素
     * <p>
     * 题目解法：那么 依旧指针，遍历即可
     */
    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }

        int slow = 2;

        for (int i = 2; i < n; i++) {
            if (nums[i] != nums[slow - 2]) {
                nums[slow] = nums[i];
                slow++;
            }
        }

        return slow;
    }

    /**
     * 169. 多数元素
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 189. 轮转数组
     * <p>
     * 题目解释：给定一个 nums，将数组 轮转 k 个位置
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * <p>
     * 题目解答：我们 只需要转换三次即可
     * 1,2,3,4,5,6,7
     * 7,6,5,4,3,2,1 全部翻转
     * 5,6,7 4,3,2,1 翻转前 K 个位置
     * 5,6,7,1,2,3,4 翻转后 K 个到结束的位置
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        if (length == 0 || k == 0) {
            return;
        }

        // 这里 k 可能超长了，需要 对 length 取模
        k = k % length;

        swap(nums, 0, length - 1);
        swap(nums, 0, k - 1);
        swap(nums, k, length - 1);

    }

    private void swap(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start++;
            end--;
        }
    }

    /**
     * 121. 买卖股票的最佳时机
     * <p>
     * 题目意思：从 prices 中，只买卖一次，获取能获得的最大利润
     * <p>
     * 题目解答：只买卖一次，得到的最大利润，那么很明显，我们找到最小的那个 金额，然后遍历 prices 去找最大利润即可
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int ans = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);

            // 这里再取最大利润
            ans = Math.max(ans, price - minPrice);
        }

        return ans;
    }


    /**
     * 122. 买卖股票的最佳时机 II
     * <p>
     * 题目意思：和 121 的区别就是，这次不限次数，得到可以得到的最大利润
     * <p>
     * 题目解答：我们只需要判断 下一个对比上一个有利润，那么就可以增加到我们的利润当中了
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int length = prices.length;
        if (length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 1; i < length; i++) {
            int value = prices[i] - prices[i - 1];
            if (value > 0) {
                ans += value;
            }
        }

        return ans;
    }

    /**
     * 55. 跳跃游戏
     * <p>
     * 题目意思：给定一个 nums, nums 里面的每个数字代表的就是 我走到这边之后，还能走的最大的距离，
     * 举个例子 2，1，3，4 ,我走第一步，得到 2 ，那么我最多可以走两步，那么我下一步就可以走到 1 或者 3
     * 然后看能不能走出
     * <p>
     * 题目解答：我们只需要每次走，看能不能走到 i 即可
     */
    public boolean canJump(int[] nums) {
        int length = nums.length;

        int max = 0;

        for (int i = 0; i < length; i++) {
            if (max < i) {
                // 这边的话，代表 走不出去了
                return false;
            }

            // 这里我们需要找到 下一次 能走到的最大距离，注意这里一定是 i + nums[i] 因为我们是要走的 路
            max = Math.max(max, i + nums[i]);

        }

        return true;
    }


    /**
     * 45. 跳跃游戏 II
     * <p>
     * 题目意思：依旧跳跃，只不过这次 我们需要得到的是 最小需要跳跃的 步数
     * <p>
     * 题目解析：我们需要记录最小步数
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
        int steep = 0;
        // 这里的 end 是记录 每一次走完 的结束位置
        int end = 0;

        // 注意 这里一定是 length - 1，因为我们不需要走完，走到最后一个位置的时候，已经完成任务了，这点很重要
        for (int i = 0; i < length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                // 这一步已经走完了，需要再走一次
                steep++;
                end = max;
            }
        }

        return steep;
    }


    /**
     * 274. H 指数
     * <p>
     * 题目意思：你最多可以有多少篇论文，它们的引用数都 ≥ 这个数量？
     * <p>
     * 题目解析：我有 h 篇论文，每篇至少被引用 h 次！， 这个 H 最大可以到多少，所以我们先排序，然后从最大开始，一个一个遍历即可
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);

        int h = 0;
        int right = citations.length - 1;

        while (right >= 0 && citations[right] > h) {
            right--;
            h++;
        }

        return h;
    }


    /**
     * 238. 除了自身以外数组的乘积
     * <p>
     * 题目意思：给定 1 2 3 4 ，输出，每个位置是其他位置的乘积，2*3*4，1*3*4, 1*2*4,1*2*3
     * <p>
     * 题目解释：第一眼肯定能直接感觉是，先做所有数据的相乘，然后遍历一遍做所有数据的相除
     * 但是 总乘积可能会有一个问题，假如中间有一个 0 的话，那么所有的数据就都不准了
     * <p>
     * 所以我们使用 左边乘积 * 右边乘积 然后进行 相乘
     * 所以我们使用 两个 数组，分别存 left[i] 左边乘积 right[i] 右边乘积
     * <p>
     * 这里面还有一个优化点，就是我们使用 res 直接 存储 left 的值，可以减少一定的空间利用
     *
     * @param nums
     * @return
     */
//    public int[] productExceptSelf(int[] nums) {
//        int n = nums.length;
//        int[] left = new int[n];
//        int[] right = new int[n];
//        int[] res = new int[n];
//
//        //
//        left[0] = 1;
//        for (int i = 1; i < n; i++) {
//            left[i] = left[i - 1] * nums[i - 1];
//        }
//
//        right[n - 1] = 1;
//        for (int i = n - 2; i >= 0; i--) {
//            right[i] = right[i + 1] * nums[i + 1];
//        }
//
//        for (int i = 0; i < n; i++) {
//            res[i] = left[i] * right[i];
//        }
//
//        return res;
//    }
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int right = 1;
        int[] res = new int[n];

        //
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }

        return res;
    }

    /**
     * 134. 加油站
     * <p>
     * 题目意思：就是给定两个 数组，一个数组是 加油站有多少油，一个是开往下一个目的地 需要多少油，问 从那个目的地出发，可以环绕一圈
     * <p>
     * 题目解析：我们想要找的是 这个起点，起点从 0 开始，然后我们还需要记录一下 current ，这个代表以当前位置走的话，总共还有多少油量
     * 然后我们还需要记录一下我们的总油量，如果 总油量 不够走完全程 （gas 总 小于 cost 总），那么也是 -1，
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int length = gas.length;
        if (length == 0) {
            return -1;
        }

        // 记录总油量
        int total = 0;
        // 记录当前油量
        int current = 0;
        // 记录起始位置
        int start = 0;

        for (int i = 0; i < length; i++) {
            // 加的油 走完之后海参多少
            int dif = gas[i] - cost[i];
            total += dif;
            current += dif;

            if (current < 0) {
                // 当前油不够，就代表这个不是起点，那么我们就需要 重新设置 起点，重新设置起点，那么 current 一定也要重置
                start = i + 1;
                current = 0;
            }
        }

        return total >= 0 ? start : -1;
    }

    /**
     * 13. 罗马数字转整数
     * <p>
     * 遇到一样的就相加，遇到小于的就相减
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int length = s.length();
        int result = 0;

        for (int i = 0; i < length; i++) {
            int current = symbolValues.get(s.charAt(i));
            if (i < length - 1 && current < symbolValues.get(s.charAt(i + 1))) {
                result -= current;
            } else {
                result += current;
            }
        }

        return result;
    }

    /**
     * 12. 整数转罗马数字
     * <p>
     * 题目意思：和 13 题反过来
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        int length = values.length;

        for (int i = 0; i < length; i++) {
            int value = values[i];
            while (num >= value) {
                sb.append(symbols[i]);
                num -= value;
            }
        }

        return sb.toString();
    }

    /**
     * 58. 最后一个单词的长度
     * <p>
     * 题目意思：很简单，直接分割之后，取最后一个值得长度即可
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        String[] s1 = s.split(" ");
        return s1[s1.length - 1].length();
    }

    /**
     * 14. 最长公共前缀
     * <p>
     * 题目意思：我们从 0 开始，往前遍历寻找 所有的 strs 里面，找到第一个不匹配的就行
     */
    public String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        if (length == 0) {
            return "";
        }

        // 我们使用 前缀不断的 往前面找
        String prefix = strs[0];

        for (int i = 0; i < length; i++) {
            String str = strs[i];
            while (!str.startsWith(prefix)) {
                // 不包含，那么久往前进一步
                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()) {
                    // 代表走到头了，还没有找到
                    return "";
                }

            }
        }

        return prefix;
    }

    /**
     * 151. 反转字符串中的单词
     * <p>
     * 反转单词，然后
     */
    public String reverseWords(String s) {
        String[] s1 = s.split(" ");

        int length = s1.length;
        StringBuilder sb = new StringBuilder();

        for (int i = length - 1; i >= 0; i--) {
            String value = s1[i];
            if (Objects.equals(value, "")) {
                continue;

            }
            sb.append(value).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * 6. Z 字形变换
     * <p>
     * 题目意思：这题知道题目原有的意思，就很好
     */
    public String convert(String s, int numRows) {
        if (s == null || numRows == 1) {
            return s;
        }

        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            // 初始化
            sb[i] = new StringBuilder();
        }

        int length = s.length();

        int index = 0;
        int flag = 1;

        /**
         * 我们按照 numRows 的长度去遍历
         */
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            sb[index].append(c);

            // 如果走到 0 或者 尾部，需要转换方向,注意，这里第一次 index
            if ((flag == -1 && index == 0) || index == numRows - 1) {
                flag = -flag;
            }

            index += flag;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            result.append(sb[i].toString());
        }

        return result.toString();
    }

    /**
     * 28. 找出字符串中第一个匹配项的下标
     *
     * 题目意思：遍历即可
     *
     */
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }

        int length = haystack.length();
        int needLength = needle.length();

        for (int i = 0; i < length; i++) {
            if (haystack.substring(i).startsWith(needle)) {
                return i;
            }
        }

        return -1;
    }

}
