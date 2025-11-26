package slidingWindow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口
 *
 * @summary SlidingWindowSolutionV2
 * @author: bf
 * @Copyright (c) 2025/11/21, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/21 09:43
 */
public class SlidingWindowSolutionV2 {

    /**
     * 209 - 长度最小的子数组
     * <p>
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     * <p>
     * 输入：target = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     * <p>
     * 滑动窗口，滑动 left ,我们遍历 right 去执行 注意 这题的意思是找到连续的 子数组
     * <p>
     * 解法：很典型的 滑动窗口题目。 不断滑动，去寻找 math.min 的最少长度
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        // 滑动窗口去求解
        int left = 0;
        int min = Integer.MAX_VALUE;
        // 用来求和
        int sum = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            // 不断移动我们的滑动窗口 用来满足我们的 >= target 的 需求条件
            while (sum >= target) {
                min = Math.min(min, right - left + 1);

                // 移动窗口，并且剔除移除那个窗口的 数据
                sum -= nums[left];
                left++;
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 3 - 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
     *
     * 思路：使用滑动窗口
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> indexMap = new HashMap<>();

        int left = 0;
        int max = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (indexMap.containsKey(c) && indexMap.get(c) >= left) {
                left = indexMap.get(c) + 1;
            }

            indexMap.put(c, right);
            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    public int[] aaaa(Integer[] nums) {
        int start = 0;
        int end = 0;
        int tempStart = 0;

        int max = nums[0];
        int currentMax = nums[0];

        for (int i = 1; i < nums.length; i++) {

            if (currentMax < 0) {
                // 重新开始
                tempStart = i;
                currentMax = nums[i];
            }else {
                // 累加
                currentMax += nums[i];
            }

            if (currentMax > max) {
                max = currentMax;
                start = tempStart;
                end = i;
            }
        }

        return null;
    }


    /**
     * 30 - 串联所有的单词子串
     * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
     *  s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
     * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
     * 返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {

        return null;
    }


    /**
     * 76 - 最小覆盖子串
     *  给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
     *
     *  1： 暴力解法，遍历两个 s t ，判断条件，然后获取到我们需要的值 （ 这个时间复杂度太高了 ）
     *  2： 使用 滑动窗口：
     *      思路： 1：我们设置一个 左闭右开的 区间 [left, right )
     *            2：我们不断的 将 right 往前走，直到他满足我们的条件 （ 包含了 T 中的所有字符 ）
     *            3：此时 我们停止 right 的移动，开始将 left 往前移动，用来缩小指针，直到他 不满足 我们的条件，然后更新我们的数据
     *            4：重复 2 3 步骤，直到 right 达到 S 的尽头
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }

        int left = 0;
        int right = 1;
        int n = s.length();

        for (int i = o; i <n; i++) {

            while (right < n && hasWindow(s.substring(i, right), t)) {

            }

        }


        return "";
    }

    public boolean hasWindow(String subS, String t) {
        char[] charArray = subS.toCharArray();

        for (int i = 0; i < t.length(); i++) {
            if (charArray.)
        }
    }

}
