package hash;

import java.util.*;

/**
 * Hash 相关算法练习
 *
 * @summary HashSolutionV2
 * @author: bf
 * @Copyright (c) 2026/3/23, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/23 20:59
 */
public class HashSolutionV2 {

    /**
     * 383. 赎金信
     * <p>
     * 题目意思：看  ransomNote 能不能完全有 magazine 中的字符组成
     * <p>
     * 题目解析：很简单了，我们直接 将 magazine 中的每个字符的数量进行统计，放入一个 map 里面，然后遍历 ransomNote
     * 找到一个就 - 1 ，看有没有不存在的，或者数量对不上的就行
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        int mLength = magazine.length();
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < mLength; i++) {
            char c = magazine.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int rLength = ransomNote.length();
        for (int i = 0; i < rLength; i++) {
            char c = ransomNote.charAt(i);
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            }

            map.put(c, map.get(c) - 1);
        }

        return true;
    }


    /**
     * 205. 同构字符串
     * <p>
     * 题目意思：s t 中的词 是同构字符串组成，比如 egg - add , e-a g-d 那么他就是
     * <p>
     * 题目解析：根据题目意思，我们可以很情绪的知道解题思路，就是 我们设置两个 map,mapST mapTS
     * 判断 s 和 t 是不是 互相存在即可，有任意一个不存在，那么就不是，否则就是
     */
    public boolean isIsomorphic(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();

        if (sLength != tLength) {
            return false;
        }

        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        for (int i = 0; i < sLength; i++) {
            char x = s.charAt(i);
            char y = t.charAt(i);

            if (mapST.containsKey(x)) {
                if (mapST.get(x) != y) {
                    return false;
                }
            } else {
                mapST.put(x, y);
            }

            if (mapTS.containsKey(y)) {
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
     * <p>
     * 题目意思：和 205 题基本一样，只不过 字符对字符，改成了 字符对字符串
     */
    public boolean wordPattern(String pattern, String s) {
        int pLength = pattern.length();
        String[] sArray = s.split(" ");
        int sLength = sArray.length;
        if (pLength != sLength) {
            return false;
        }

        Map<Character, String> mapPS = new HashMap<>();
        Map<String, Character> mapSP = new HashMap<>();

        for (int i = 0; i < pLength; i++) {
            char x = pattern.charAt(i);
            String y = sArray[i];

            if (mapPS.containsKey(x)) {
                if (!mapPS.get(x).equals(y)) {
                    return false;
                }
            } else {
                mapPS.put(x, y);
            }

            if (mapSP.containsKey(y)) {
                if (mapSP.get(y) != x) {
                    return false;
                }
            } else {
                mapSP.put(y, x);
            }
        }

        return true;
    }

    /**
     * 242. 有效的字母异位词
     * <p>
     * 题目意思：什么是 字母异位词 ，就是两个单词用相同的字母组合，只不过 顺序不同
     * <p>
     * 题目解析：已经统计 出现的次数，然后比对即可
     * <p>
     * 优化 也可以使用 int[26] 去记录每个字母出现的次数 也行，一个 + 一个- 然后看 有没有 ！= 0 的
     */
    public boolean isAnagram(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();

        if (sLength != tLength) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < sLength; i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < tLength; i++) {
            char c = t.charAt(i);
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            }

            map.put(c, map.get(c) - 1);
        }

        return true;
    }

    /**
     * 49. 字母异位词分组
     * <p>
     * 题目意思：这题很简单，就是 将 strs 中 是字母异或词的放一起
     * <p>
     * 题目解析：我们直接 排序 然后放一块就行
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        int length = strs.length;
        List<List<String>> res = new ArrayList<>();
        if (length == 0) {
            return res;
        }

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);

            String sortStr = Arrays.toString(charArray);

            map.computeIfAbsent(sortStr, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * 1. 两数之和
     * <p>
     * 题目意思：从 nums 里面 找到两个数的和 = target
     * <p>
     * 题目解析：我们直接使用 一个 map ,记录值 + 他的位置 index 即可，当 相加 = target 的时候 返回 index
     */
    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < length; i++) {
            int num = nums[i];
            Integer integer = map.get(target - num);
            if (integer != null) {
                return new int[]{i, integer};
            }
            map.put(num, i);
        }

        return new int[]{};
    }

    /**
     * 202. 快乐数
     * <p>
     * 题目意思：判断 n 是否是 快乐树，也就是 一直下去能不能到 1 ，
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     * <p>
     * 题目解析：我们只需要将一个数据拆开 19 --- 19 % 10 = 1 / 19 / 10 = 9 然后等到一个全新的 sum ,不断计算，判断是否 得到 1，
     * 注意，如果出现重复的数据，那么就会死循环，那么他就不是 快乐数
     */
    public boolean isHappy(int n) {
        Set<Integer> map = new HashSet<>();

        // 不断的计算
        while (n != 1) {
            n = calcNum(n);
            if (map.contains(n)) {
                // 这里进入循环了，那么就不是快乐数了
                return false;
            }
            map.add(n);
        }

        return true;
    }

    private int calcNum(int num) {
        int sum = 0;
        while (num > 0) {
            int i = num % 10;
            sum += i * i;
            num = num / 10;
        }
        return sum;
    }


    /**
     * 219. 存在重复元素 II
     * <p>
     * 题目意思：我们需要找到 两个重复数，且，他们满足这样的条件， nums[i] == nums[j]  && abs(i - j) <= k
     * <p>
     * 题目解析：其实就是 寻找重复数的变种，再加上一个 abs(i - j) <= k 的条件,依旧使用 hash 做 num，index 存储就走了
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length = nums.length;

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < length; i++) {
            int num = nums[i];

            if (map.containsKey(num) && Math.abs(i - map.get(num)) <= k) {
                return true;
            }

            map.put(num, i);
        }

        return false;
    }

    /**
     * 128. 最长连续序列
     *
     * 题目意思：给定一个 数组，判断他的最长连续序列的长度，比如 3 1 2 4 100 200 ,那么他最长的序列就是 1 2 3 4
     *
     * 题目解析：我们直接将全部数据存储到 一个 map 当中，然后  遍历 nums,判断 有没有 +1 或者 -1 的，如果找到，就代表他这部分数据是连续
     * 序列，遍历 直到找不到为止，然后拿到最大值，然后不同的最大值，不断的比较即可
     */
//    public int longestConsecutive(int[] nums) {
//        Set<Integer> map = new HashSet<>();
//        int max = 0;
//        for (int num : nums) {
//            map.add(num);
//        }
//
//        // 继续遍历
//        for (int num : nums) {
//            int total = 1;
//            int next = num + 1;
//            // 这种写法，能达到要求，但是会超出时间限制，为什么，因为 假如 1234567 每一个进来，都会进行一次遍历，他的时间复杂度数 n^2
//            // 所以我们需要找 第一个满足条件的，然后去判断，比如 1234567 100 101，我们只需要寻找 1 和 100 就行，这块如何找呢，
//            // num - 1 不包含，那么久代表当前的 num 是起始位置了
//            while (map.contains(next)) {
//                total++;
//                next++;
//            }
//            max = Math.max(max, total);
//        }
//
//        return max;
//    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> map = new HashSet<>();
        int max = 0;
        for (int num : nums) {
            map.add(num);
        }

        // 继续遍历，注意因为重复元素的一些原因，这里使用 set 进行去重后的数据进行遍历，会更快
        for (int num : map) {
            int total = 1;
            int pre = num - 1;
            if (!map.contains(pre)) {
                // 不包含，那么他就是起始点
                int next = num + 1;
                while (map.contains(next)) {
                    total++;
                    next++;
                }

                max = Math.max(max, total);
            }
        }

        return max;
    }

}
