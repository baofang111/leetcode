package string;

/**
 * @summary StringSolution
 * @author: bf
 * @Copyright (c) 2025/3/10, © 拜耳作物科学（中国）有限公司
 * @since: 2025/3/10 11:42
 */
public class StringSolution {


    /**
     * 5 - 最长 回文子串
     *
     *  思路很简单，使用两个 指针，一个从最前面 一个从最后面，拿到他们的值，去比对，如果一样，就继续往里面走
     *  但是这种会有个问题，就是 两边一样，中间不一样，所以我们转变思路
     *
     *  我们需要从 中间 往两边进行扩散，（ 需要考虑 长度 奇偶数的情况 ）
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int start = 0;
        int maxLen = 0;

        // 从 起始位置开始 慢慢的的往前走
        for (int i = 0; i < s.length(); i++) {
            // bab 奇数
            int left = i;
            int right = i;
            // 不断的往前走，寻找 回文子串
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                    start = left;
                }

                // 开始扩散
                left--;
                right++;
            }

            // 偶数的情况 baab
            left = i;
            right = i + 1;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                    start = left;
                }

                // 开始扩散
                left--;
                right++;
            }

        }

        return s.substring(start, maxLen);
    }
}
