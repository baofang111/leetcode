package hot.hot100V2;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 *
 * @summary Hot100V3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/05/11 13:48:46
 */
public class Hot100V3 {

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
        int sum = 0;
        int count = 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int num : nums) {
            sum += num;
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
     * 使用最大堆
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int length = nums.length;
        int[] result = new int[length - k + 1];
        int index = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peekLast()]) {
                stack.pollLast();
            }

            stack.offerLast(i);

            // 缩小窗口
            int leftWindow = i - k + 1;
            while (leftWindow > stack.peekFirst()) {
                stack.pollFirst();
            }

            // 填入结果值
            if (i >= k - 1) {
                result[index++] = nums[stack.peekLast()];
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

        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int valid = 0;

        int start = 0;
        int length = Integer.MAX_VALUE;
        int left = 0;

        for (int i = 0; i < sLength; i++) {
            char c = s.charAt(i);
            window.put(c, window.getOrDefault(c, 0) + 1);

            if (window.get(c).equals(need.get(c))) {
                valid++;
            }

            while (valid == need.size()) {
                if (length > i - left + 1) {
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
     * 594. 最长和谐子序列
     *
     * 题目意思：这个子序列里面，最大值和最小值的差值只能 是 1 ，所以里面只能是 X 还有 X + 1  的数据
     *
     * 题目解析：直接使用 map 记录即可
     *
     * @param nums
     * @return
     */
    public int findLHS(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int ans = 0;
        for (int num : nums) {
            if (map.containsKey(num + 1)) {
                ans = Math.max(ans, map.get(num) + map.get(num + 1));
            }
        }

        return ans;
    }

}
