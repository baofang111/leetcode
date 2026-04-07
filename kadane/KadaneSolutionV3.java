package kadane;

/**
 * kadane 算法 连续
 * <p>
 * 什么是 kadane 算法：
 * 如果当前累计和为负数，则丢弃当前子数组，从当前位置重新开始计算。
 *
 * @summary KadaneSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/07 19:49:23
 */
public class KadaneSolutionV3 {


    /**
     * 53. 最大子数组和
     * <p>
     * 题目意思： 给定一个 nums ,我们要找出 最大的子数组和
     * <p>
     * 题目解析：遍历 nums, 判断 如果当前值，添加上，让结果值变小了，那么久不要她，
     * 所以我们需要两个结果集来存储我们计算所需要的值
     * max: 最大值
     * cur: 当前值
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 使用两个变量进行存储 计算结果值
        int cur = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // 如果当前值加上 cur 值，没有 当前值大，那么cur 就更新成当前值
            cur = Math.max(nums[i], cur + nums[i]);

            max = Math.max(max, cur);
        }

        return max;
    }


    /**
     * 918. 环形子数组的最大和
     *
     * 题目意思：给定的是一个环形子数组，我找到结尾之后，我还能再从头部开始找，然后让我们找到他的最大 子数组的和
     *
     * 题目解析：该题目是 53. 最大子数组和 的变种，我们要得到这个值的话
     * 就需要 拿到3个值
     * total: nums 的总累加值
     * 连续的最大和：
     * 连续的最小和： 其中 如果连续的话 total - minSum = 就是 最大和，但是这里会有一个问题，就是 假如都是 负数的情况，计算就会变成 0
     * 所以我们还需要计算 连续的最大和，用来比对
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxCur = nums[0];
        int maxSum = nums[0];

        int minCur = nums[0];
        int minSum = nums[0];

        int total = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxCur = Math.max(maxCur + nums[i], nums[i]);
            maxSum = Math.max(maxSum, maxCur);

            minCur = Math.min(minCur + nums[i], nums[i]);
            minSum = Math.min(minSum, minCur);

            total += nums[i];
        }

        if (maxSum < 0) {
            return maxSum;
        }

        return Math.max(maxSum, total - minSum);
    }


}
