package slidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 滑动窗口相关题目
 *
 * @summary SlidingWindowSolution
 * @author: bf
 * @Copyright (c) 2025/3/18, © 拜耳作物科学（中国）有限公司
 * @since: 2025/3/18 11:24
 */
public class SlidingWindowSolution {


    /**
     * 76 - 最小覆盖子串
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
     * <p>
     * 1： 暴力解法，遍历两个 s t ，判断条件，然后获取到我们需要的值 （ 这个时间复杂度太高了 ）
     * 2： 使用 滑动窗口：
     * 思路： 1：构建 need 表，也就是 T 中 所有字符出现的次数
     * 2：初始化 left right = 0 , 也就是初始化 windows· 窗口为空
     * 3：right 指针不断的向右扩展，并且不断的更新 windows 判断是否满足 need 中的所有条件
     * 4：检查是否完美覆盖 valid == need.size, 如果完全满足，那么就移动 left 缩小窗口，更新最短答案
     * 5：取出最短的答案
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        // 这题的核心是我们适用 need + window 两个窗口，窗口里面设置的是 值，出现的数量，然后我们判断 是否全部满足，并且移动指针来获取我们的值即可
        Map<Character, Integer> need = new HashMap<>();
        for (Character c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 设置窗口
        Map<Character, Integer> window = new HashMap<>();

        // 设置左右指针
        int left = 0;
        int right = 0;
        // 设置 开始位置和满足条件的最大长度
        int start = 0;
        int length = Integer.MAX_VALUE;
        // 设置 我们 window 中满足 need 条件的次数，当 valid = need.size 的时候 代表当前 window 满足条件，这个非常重要
        int valid = 0;

        // 开始遍历
        while (right < s.length()) {
            // 取出 right 的值，并且移动 右边指针
            char c = s.charAt(right);
            right++;

            // 设置窗口
            window.put(c, window.getOrDefault(c, 0) + 1);

            // 判断 window 和 need 中是否全部都有该元素，并且次数相等
            if (window.get(c).equals(need.get(c))) {
                // 有一个元素满足要求了
                valid++;
            }

            // 当 valid = need.size 代表 window 里面已经包含了所有满足条件的数据了，这时候我们尝试去 移动左指针，寻找最小范围
            while (valid == need.size()) {
                // 更新最小未知
                if (right - left < length) {
                    start = left;
                    length = right - left;
                }

                // 左指针往前移动，移动之前取出 当前 left 的值，进行下一步判断
                char d = s.charAt(left);
                left++;

                // 如果 need 中包含 d, 那么我们需要 对 window 的数据记录 进行 d 的次数 - 1，且之前，需要 对 valid 进行判断并--，
                // 不然 valid 永远不会更新，窗口就失效了
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }

                    window.put(d, window.get(d) - 1);
                }
            }
        }

        // 返回我们需要的值即可
        return length == Integer.MAX_VALUE ? "" : s.substring(start, start + length);
    }


    /**
     * 30. 串联所有单词的子串
     * 这题和 76 有点像，不过这题应为 words 是 固定长度，所以 是固定长度的窗口滑动去判断
     *
     *
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        // 特殊情况处理
        if (s == null || s.isEmpty() || words == null || words.length == 0) {
            return res;
        }

        // 拿到 words 的 单词 + 出现次数的 map
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.put(word, need.getOrDefault(word, 0) + 1);
        }

        // 滑动窗口，我们每次需要按照 words 的单词长度去滑动
        int count = words[0].length();
        int wordLength = words.length;

        // 开始遍历 左右指针找满足条件的 数据了
        for (int i = 0; i < wordLength; i++) {
            int left = i;
            int right = i;

            // 当前窗口
            Map<String, Integer> window = new HashMap<>();
            // 当前窗口满足条件的 数量
            int valid = 0;

            // 右边指针不断的遍历
            while (right + wordLength <= s.length()) {
                String word = s.substring(right, right + wordLength);
                right += wordLength;

                // 判断是否包含
                if (need.containsKey(word)) {
                    // 更新窗口
                    window.put(word, window.getOrDefault(word, 0) + 1);
                    valid++;

                    // 如果当前单词在窗口中出现的次数大于我们需要的次数，那么我们就收缩窗口
                    while (window.get(word) > need.get(word)) {
                        // 左边要移出的单词
                        String leftWord = s.substring(left, left + wordLength);
                        // window 次数 - 1
                        window.put(leftWord, window.getOrDefault(leftWord, 0) - 1);
                        // 移动左指针
                        left += wordLength;
                        // 数量 count 需要更新
                        valid--;
                    }

                    // 判断 need 和 window 中 同样包含的有多少，是否满足 valid == count
                    if (valid == count) {
                        // 我们找到了第一个满足条件的位置，这时候我们记录左指针的未知
                        res.add(left);
                    }
                } else {
                    // 不包含，我们就需要窗口重置
                    window.clear();
                    valid = 0;
                    // 移动我们的 left 指针
                    left = right;
                }
            }

        }

        return res;
    }

}
