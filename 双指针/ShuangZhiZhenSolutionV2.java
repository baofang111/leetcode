package 双指针;

import java.util.List;

/**
 * 双指针 相关 算法题
 *
 * @summary ShuangZhiZhenSolutionV2
 * @author: bf
 * @Copyright (c) 2025/11/20, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/20 10:52
 */
public class ShuangZhiZhenSolutionV2 {

    public static void main(String[] args) {
        System.out.println(1);
    }


    /**
     * 125 - 验证会问子串
     *
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     *
     * 双指针，左右遍历，如果是数字和字母，就进行比较
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        s = s.toLowerCase();

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


    /**
     * 392 - 判断子序列
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     *
     * 直接双指针 不断的 遍历 s + t 判断是否可以遍历完即可，遍历完，代表 s 完全在 t 里面
     *
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) {
            return true;
        }

        int sIndex = 0;
        int tIndex = 0;

        // 双指针开始遍历
        while (sIndex < s.length() && tIndex < t.length()) {
            // 有一样的 S 指针往前面走
            if (s.charAt(sIndex) == t.charAt(tIndex)) {
                sIndex++;
            }
            // 不管什么情况 t 指针都要往前走
            tIndex++;
        }

        return sIndex == s.length();
    }


    /**
     * 167 - 两数之和Ⅱ - 输入的是有序数组
     *
     * 示例 1：
     *
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
     *
     * 题目意思也很简单，通过双指针
     *
     * 示例 2：
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }
            if (sum > target) {
                right--;
            }
            if (sum < target) {
                left++;
            }
        }

        return new int[]{-1, -1};
    }


    /**
     * 11 - 盛最多水的容器
     *
     * 看到题目，第一反应就是双指针，然后解法类似于 接雨水哪一题，
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int maxArea = 0;

        while (left < right) {
           // 这里有一个要点，就是我们需要寻找下一块 更高的板子，这样才可能 总水量最大
            int min = Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, min * (right - left));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }


}
