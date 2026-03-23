package slidingWindow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口 算法练习
 *
 * @summary SlidingWindowSolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/23, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/23 13:26
 */
public class SlidingWindowSolutionV3 {

    /**
     * 209. 长度最小的子数组
     *
     * 题目意思：给定一个 含有 n 个正整数的数组 和 一个正整数，找到 最小的子数组的长度
     *
     * 题目解析：我们直接使用 滑动窗口即可，选中一个开头，不断的往前，如果大于 target 的话，就移动窗口的左边位置
     * 注意，移动的时候，需要 减去 移动掉那个位置的值
     *
     */
    public int minSubArrayLen(int target, int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int sum = 0;
        int min = Integer.MAX_VALUE;
        int left = 0;
        for (int right = 0; right < length; right++) {
            sum += nums[right];
            // 主要这里一定要是 while
            while (sum >= target) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 3. 无重复字符的最长子串
     *
     * 题目意思：给定一个 字符串 s, 找出其中不含有重复字符的 最长子串的 长度
     *
     * 题目解析：和 209 最小的子数组 一样，只不过我们的判断条件改成了 重复字符，我们使用 一个 Map 来保存已经保存过的字符
     *
     */
    public int lengthOfLongestSubstring(String s) {
        int length = s.length();
        if (length == 0) {
            return 0;
        }

        // 依旧滑动窗口,滑动窗口最重要的就是 选用两个指针，不断的去找到符合我们条件的值
        int left = 0;
        int max = Integer.MIN_VALUE;

        // 使用一个 map 记录不同 字符出现的位置
        Map<Character, Integer> map = new HashMap<>();

        for (int right = 0; right < length; right++) {
            char c = s.charAt(right);

            // 这里是关键，当出现重复字符的时候，我们需要将 left 往前移动，以寻找最小的
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }

            map.put(c, right);
            max = Math.max(max, right - left + 1);
        }

        return max;
    }

    /**
     * 30. 串联所有单词的子串
     *
     * TODO 太复杂了
     *
     */
    public List<Integer> findSubstring(String s, String[] words) {

        return null;
    }

    /**
     * 76. 最小覆盖子串
     *
     * 题目意思：给定 两个字符串，S 是大的字符串，t 是部分满足 S 中的字符串，我们要找到一个 S 中最小的字符串，比如 T 是 ABC ，S 中有 ABDC
     * 那么 ABDC 就是最小覆盖子串
     *
     * 题目解析：该题其实可以理解是 无重复覆盖字符的 一种变种，只不过判断方式不一样，这题是最小覆盖子串，不是完全覆盖，所以我们需要一个字段
     * 用来判断，该位置，已经和 覆盖了 S 中的那几个数据了，当完全覆盖，也就是 存在 ABC 中 三个元素了，那么就进行元素的移动
     *
     * 其中 我们使用 valid 用来标注，有多少个字符 已经满足我们 need 窗口的条件了
     */
    public String minWindow(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();

        // 创建两个窗口，一个 window 表示当前正在移动的，need 表示我们要满足条件的窗口，这个很重要，用来判断使用
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();

        // 初始化 need
        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 设置窗口指针
        int left = 0;

        // 满足条件的位置 起始位置 + 长度
        int start = 0;
        int length = Integer.MAX_VALUE;

        // 满足条件的 字符串长度，这个是最重要的
        int valid = 0;

        for (int right = 0; right < sLength; right++) {
            char c = s.charAt(right);

            // 插入 window 的
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 如果单词满足 need 的次数，valid++
            if (need.containsKey(c) && window.get(c).equals(need.get(c))) {
                valid++;
            }

            while (valid == need.size()) {
                // 这里满足条件了，我们需要开始寻找
                if (right - left + 1 < length) {
                    start = left;
                    length = right - left + 1;
                }

                // 这里需要移动窗口，寻找最小值
                char d = s.charAt(left);
                left++;

                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        // 因为位置更新了，所以 valid 需要更新
                        valid--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return length == Integer.MAX_VALUE ? "" : s.substring(start, start + length);
    }



}
