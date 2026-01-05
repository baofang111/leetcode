package kadane;

/**
 * 什么是 Kadane 算法
 *
 * Kadane 算法是一种用于解决「最大连续子数组和」问题的一维动态规划算法。
 * 核心思想是：维护“以当前位置结尾的最大子数组和”，
 * 如果当前累计和为负数，则丢弃当前子数组，从当前位置重新开始计算。
 *
 * 递推公式：
 * dp[i] = max(nums[i], dp[i - 1] + nums[i])
 *
 * 通过滚动变量优化空间复杂度，可在 O(n) 时间、O(1) 空间内完成计算。
 *
 * @summary KadaneSolution
 * @author bf
 * @since 2026/1/4 18:50
 */
public class KadaneSolution {


    /**
     * 53. 最大子数组和
     *
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组是数组中的一个连续部分。
     *
     * 解法思路：该题就是典型的 Kadane 算法，
     * 核心思路：如果当前子数组的和已经小于 0，那么它对后续结果只会产生负贡献，应当直接舍弃，从当前位置重新开始。
     *
     * 使用 当前最大 + 历史最大 去解答该题，结果就很清晰，如果当前最大 < 0,就丢弃
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int length = nums.length;

        // cur 以 nums[i-1] 结尾的最大连续子数组和
        int cur = nums[0];
        int max = nums[0];

        for (int i = 1; i < length; i++) {
            // 判断当前值和 cur 谁最大，如果 num[i] > 0 就保存，否则丢弃, 所以我们直接 cur + nums[i] 判断即可，这样 < 0 就会被自动过滤掉
            cur = Math.max(nums[i], cur + nums[i]);
            max = Math.max(max, cur);
        }

        return max;
    }

    /**
     * 918. 环形子数组的最大和
     *
     * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
     * 环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
     * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
     *
     * 解法思路：该题和 53 题最大的区别就是 一个是 环形 一个非环形，53 直接遍历 然后 计算 cur + max 就可以得到我们要的结果
     * 该题
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {

    }

}
