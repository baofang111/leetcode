package hot.hot150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针 练习代码
 *
 * @summary ShuangZhiZhenSolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/20, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/20 14:29
 */
public class ShuangZhiZhenSolutionV3 {

    /**
     * 125. 验证回文串
     * <p>
     * 题目意思：验证一个字符串 是不是回文串
     * <p>
     * 题目解析：直接双指针就行了
     */
    public boolean isPalindrome(String s) {
        int length = s.length();

        int left = 0;
        int right = length - 1;
        s = s.toLowerCase();

        while (left < right) {
            // 先判断 是不是 数字和字母
            while (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while (right > 0 && !Character.isLetterOrDigit(s.charAt(right))) {
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
     * 392. 判断子序列
     * <p>
     * 题目意思：判断 s 是不是在 t 的子序列 里面
     * <p>
     * 题目解析：我们按照 s 和 t 的指针，不断的往前移动，从开始匹配，到匹配结束，看能不能 完整匹配即可
     */
    public boolean isSubsequence(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        if (sLength == 0 || tLength == 0) {
            return false;
        }

        int sIndex = 0;
        for (int i = 0; i < tLength; i++) {
            if (s.charAt(sIndex) == t.charAt(i)) {
                sIndex++;
                if (sIndex == sLength) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 167. 两数之和 II - 输入有序数组
     * <p>
     * 题目意思：就是 给定一个 numbers，找到 他们中值 相加 = target 的 下标
     * <p>
     * 题目解析：我们双指针，满满往中间找即可，注意题目的意思是 numbers  是有序数组，所以直接左右指针
     */
    public int[] twoSum(int[] numbers, int target) {
        int length = numbers.length;

        int left = 0;
        int right = length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == 0) {
                return new int[]{left + 1, right + 1};
            } else if (sum > 0) {
                right--;
            } else {
                left++;
            }
        }

        return new int[]{};
    }

    /**
     * 11. 盛最多水的容器
     * <p>
     * 题目意思：给一个 height，判断能装多少水
     * <p>
     * 题目解析：依旧双指针，我们不断的往中间找，看最多能装多少水
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int length = height.length;
        if (length == 0) {
            return 0;
        }

        int left = 0;
        int right = length - 1;

        int maxWater = 0;

        while (left < right) {
            int min = Math.min(height[left], height[right]);
            maxWater = Math.max(maxWater, min * (right - left));

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxWater;
    }

    /**
     * 15. 三数之和
     * <p>
     * 题目意思：找到 nums 中，所有 组合 等于 0 的 组合，注意几个点，一个是 重复元素需要过滤掉
     * <p>
     * 题目解析：我们直接使用 三指针就行了，对于什么 四数之和 其实都一样，假如我们已 0 作为起点，然后 在用两个指针
     * i+ 1， 还有 right(length - 1) 作为另外两个指针，去不断寻找即可
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int length = nums.length;
        if (length == 0) {
            return res;
        }

        // nums 一定要有序
        Arrays.sort(nums);

        // 使用三指针，固定一个位置，然后其他几个位置 不断的变动
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (num > 0) {
                // 已经全部 大于 0 了
                break;
            }

            // 这里有个坑，就是 出现重复的元素 比如 -1 -1 ，我们在第二个 -1 的时候判断一定要给他剔除掉，不然会出现重复结果
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 固定住 i 开始移动 left + right 寻找结果
            int left = i + 1;
            int right = length - 1;

            while (left < right) {
                int sum = num + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(num, nums[left], nums[right]));

                    // 找到结果之后，我们需要再次移动 left right
                    left++;
                    right--;

                    // 这里有个问题，和上面的一样，移动之后出现重复数字怎么办，我们需要过滤掉
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }

            }
        }

        return res;
    }

}
