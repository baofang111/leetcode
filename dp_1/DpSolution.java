package dp_1;

/**
 * 一维动态规划
 *
 *
 * @summary DpSolution
 * @author: bf
 * @Copyright (c) 2026/1/20, © 拜耳作物科学（中国）有限公司
 * @since: 2026/1/20 21:18
 */
public class DpSolution {

    /**
     * 70. 爬楼梯
     *
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 解题思路：典型的 DP 题目
     *
     * 假如我在 第 N 个台阶，那么我只可能从 第 N-1 or N-2 个台阶上走过来，那么该题目答案就有了
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        // 因为我的状态转移方程是 n-1 n-2 所以我们要初始化两个起始点位
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }


    /**
     * 198. 打家劫舍
     *
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
     * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 解题思路：这也是一道典型的 动态规划题目，状态转移方程
     * 前一个房子 偷 或者不偷
     * 当前房子 偷的话，dp[i] = dp[i - 2] + num[i]
     * 不偷当前的房子 dp[i] = dp[i - 1]
     * 所以完整的状态转移方程就是 dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
     *
     * 边界情况 0 只能偷
     * 1 可偷 可不偷
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n < 1) {
            return nums[0];
        }

        // 我们初始化 前面数据
        int[] dp = new int[n];
        dp[0] = nums[0];
        // 1 位置 取最大的
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
    }

}
