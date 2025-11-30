package hash;

import java.util.*;

/**
 * 哈希表相关
 *
 * @summary HashSolution
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/30 15:02:11
 */
public class HashSolution {


    /**
     * 383. 赎金信
     *给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * 如果可以，返回 true ；否则返回 false 。
     * magazine 中的每个字符只能在 ransomNote 中使用一次。
     *
     * 解法：直接暴力遍历 即可，将 ransomNote
     *
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();

        // 遍历
        for (Character c : magazine.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // 再次遍历 ransomNote
        for (Character c : ransomNote.toCharArray()) {
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            }
            map.put(c, map.get(c) - 1);
        }

        return true;
    }

    /**
     * 205. 同构字符串
     * 定两个字符串 s 和 t ，判断它们是否是同构的。
     * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
     *
     * s→t 映射：保证同一个 s 字符映射到同一个 t 字符
     * t→s 映射：保证同一个 t 字符只能对应一个 s 字符
     * 双向检查，才能保证 “一对一” 映射关系。
     *
     * 适用双 hashMap 即可
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        // ST 必须要互相对应才行
        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        int n = s.length();
        for (int i = 0; i < n; i++) {
            char x = s.charAt(i);
            char y = t.charAt(i);

            if (mapST.containsKey(x)) {
                if (mapST.get(x) != y) {
                    return false;
                }
            } else {
                mapST.put(x, y);
            }

            if (mapTS.containsKey(x)) {
                if (mapTS.get(y) != x) {
                    return false;
                }
            } else {
                mapTS.put(y, x);
            }
        }
        return true;
    }

    /**
     * 290. 单词规律
     * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。具体来说：
     * pattern 中的每个字母都 恰好 映射到 s 中的一个唯一单词。
     * s 中的每个唯一单词都 恰好 映射到 pattern 中的一个字母。
     * 没有两个字母映射到同一个单词，也没有两个单词映射到同一个字母。
     *
     * 解法：这题和 205 差不多，一个是字符直接的互相匹配，一个是 字符和 单词直接的互相匹配
     *
     * @param pattern
     * @param s
     * @return
     */
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }

        Map<Character, String> mapPS = new HashMap<>();
        Map<String, Character> mapSP = new HashMap<>();

        int length = words.length;

        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            if (mapPS.containsKey(c)) {
                if (!mapPS.get(c).equals(word)) {
                    return false;
                }
            } else {
                mapPS.put(c, word);
            }

            if (mapSP.containsKey(word)) {
                if (!mapSP.get(word).equals(c)) {
                    return false;
                }
            } else {
                mapSP.put(word, c);
            }
        }

        return true;
    }

    /**
     * 242. 有效的字母异位词
     *
     *  解法： 同样和 205 差不多，我们适用 一个数组，存储 每个单词的 出现次数
     *  ，如果 st 两个出现的次数不一样，那么就不是，出现的次数一样，就是
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] count = new int[26];

        // 对 S 中出现的次数 + 1
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }

        // 对 T 中出现的次数 - 1
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']--;
        }

        // 如果存在 != 0 的数据，代表他们的数量就是不一样的
        for (int num : count) {
            if (num != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * 49. 字母异位词分组
     *
     * 解法： 暴力解法，判断是否是 异位词
     * 我们适用 map 然后将 排序之后的 作为 key ,未排序的作为 value
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {

            // 获取排序后的 value
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);

            String sortStr = new String(charArray);

            map.computeIfAbsent(sortStr,k -> new ArrayList<>()).add(str);
//            if (map.containsKey(sortStr)) {
//                map.get(sortStr).add(str);
//            } else {
//                List<String> list = new ArrayList<>();
//                list.add(str);
//                map.put(sortStr, list);
//            }
        }

        return new ArrayList<>(map.values());
    }

    /**
     * 1. 两数之和
     * 解法：map 双指针 都可以，这里我们适用 map
     *
     * 注意，这里的 nums 不一定是有序的
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                return new int[]{i, map.get(diff)};
            }
            map.put(nums[i], i);
        }

        return null;
    }

    /**
     * 202. 快乐数
     *
     * 解法： 适用 一个 hash 来判断 是否循环即可
     *
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        Set<Integer> map = new HashSet<>();
        while (n != 1) {
            n = sumSquareDigits(n);
            if (!map.add(n)) {
                // 出现了循环
                return false;
            }
        }
        return true;
    }

    public int sumSquareDigits(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            sum += d * d;
            n = n / 10;
        }
        return sum;
    }


    /**
     * 219. 存在重复元素 II
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
     * 满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false
     *
     * 解法：使用一个 map 记录一下 上一个该值 所在的 index 位置即可
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            if (map.containsKey(num)) {
                int lastIndex = map.get(num);
                if (i - lastIndex <= k) {
                    return true;
                }
            }
            map.put(num, i);
        }
        return false;
    }

    /**
     * 128. 最长连续序列
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     *
     * 解答：适用 hashMap ，记录我们的 nums 所有数据，
     * 然后遍历，判断有没有 + 1 有值的数据，有 我们的 count + 1
     * 然后找到我们的最大 max
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        // 使用一个 set 存储 nums 里面所有的值，然后遍历判断，有没有 + 1 或者 -1 的值，然后获取最大的长度
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int max = 0;
        // 注意 这里使用 numSet 遍历，减少遍历量，否则会出现 超出时间限制
        for (int num : numSet) {
            // 我们需要找到顺序的开始位置 进行循环遍历，获取最大长度
            // 为什么这里是 不包含 而不是包含，因为 包含的话，就是 1234 中的 234 ，不是我们要找的开头 1 的未知
            if (!numSet.contains(num - 1)) {
                int x = num - 1;
                while (numSet.contains(x)) {
                    x++;
                }

                max = Math.max(max, x - num);
            }
        }

        return max;
    }

}
