package dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DP 动态规划 题目练习 2
 *
 * @summary DpSolutionV2
 * @author: bf
 * @Copyright (c) 2026/3/17, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/17 15:15
 */
public class DpSolutionV2 {

    /**
     * 70. 爬楼梯
     * <p>
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * <p>
     * 解法：状态方程，dp[i] = 要不爬一个台阶 + 要不爬两个台阶
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

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
     * <p>
     * 还是一个典型的 DP 问题，核心是 1：不能连续偷，2：偷到的最大金额
     * <p>
     * 所以该题目的状态转移方程就是 dp[i] 表示能偷的最大金额
     * <p>
     * dp[i] = max ( 偷前一家，不偷前一家 )
     * dp[i] = max(dp[i-1], dp[i-2] + nums[i-1])
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        // 初始化 DP
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2], dp[i - 1] + nums[i]);
        }

        return dp[n - 1];
    }

    /**
     * 139. 单词拆分
     * <p>
     * 题目解释：wordDict 能不能组成 单词 s
     * <p>
     * 解题思路：已经 DP， 已经状态方程，dp[i] 表示 在 单词 S 的 第 i 个字符，能不能被 wordDict 组成
     * 那么 状态转移方程就 dp[i] 判断 s 中的 ij 能否被 wordDict 包含，有就改成 true
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 我们使用 j - i 看在不在 wordDict 里面，然后再使用 dp 方程进行判断即可
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();

        // 初始化DP 和 DP的初始化值
        boolean[] dp = new boolean[n + 1];
        // 注意，这一定要是 true,他是一切的起点，不然全部都是 false了
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    /**
     * 322. 零钱兑换
     * <p>
     * 题目解释：给了一个整数数组 coins，还有 一个总金额 amount，使用 coins 中的金额凑成 最小硬币个数
     * <p>
     * 截图思路：已经 DP, dp[i] 其中 i 是 amount 的金额，表示 凑成 amount 需要的最小硬币个数
     * <p>
     * dp[i] = 凑出金额 i 所需要的最小金额数
     * 状态转移方程 dp[i] = min(dp[i], dp[i - coin] + 1)
     * 注意：方程中的 do[i - coin] 很关键， 他表示 要凑出 i
     * = 先凑出 (i - coin)
     * + 再加 1 个 coin
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        // 初始化 DP
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        // 初始化 DP ，作为递推的起点
        dp[0] = 0;

        // 开始遍历
        for (int i = 0; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin) {
                    // 状态转移方程
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 300. 最长递增子序列
     * <p>
     * 题目解释：给个 数组 nums，判断他 最长的递增 子序列
     * <p>
     * 题目解法：依旧 DP ，DP 表示 当前位置 最长的 递增子序列
     * <p>
     * 那么他的解法就呼之欲出了
     * 状态转移方程
     * <p>
     * 如果 nums[i] > nums[j] 那么
     * dp[i] = max(dp[i],dp[j] + 1 )
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            // 注意，这里一定要从 0 开始走，因为 dp[0] = 1 相当于初始化了 dp 的起始位置
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            // 重新记录结构
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    /**
     * 120. 三角形最小路径和
     * <p>
     * 题目解释：我们从 三角形走 找出 最小的路劲和
     * <p>
     * 题目解法：dp[i][j] 表示 i 高 j 列的最小路径和
     * 那么他的状态转移方程 =
     * dp[i][j] = min(dp[i-1][j-1], dp[i-1][j]) + val;
     * 然后其中，我们考虑 第一列 + 最后一列的 状态数据
     * 第一列 dp[i][0] = dp[i-1][0] + val
     * 最后一列： dp[i][j] = dp[i-1][j-1] + val
     * <p>
     * 然后我们取 最后一行的数据，取最小值即可
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        // 初始化 DP
        int n = triangle.size();
        int m = triangle.get(n - 1).size();
        int[][] dp = new int[n][m];

        dp[0][0] = triangle.get(0).get(0);

        // 开始遍历，注意初始化过了，这里直接从 1 开始
        for (int i = 1; i < n; i++) {
            int size = triangle.get(i).size();
            for (int j = 0; j < size; j++) {
                Integer val = triangle.get(i).get(j);

                // 开始走 状态转移方程
                if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + val;
                } else if (j == size - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + val;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + val;
                }
            }
        }

        // 我们需要从 最后一列找到我们的最小值
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }

        return ans;
    }

    /**
     * 64. 最小路径和
     * <p>
     * 题目解释：和 120 一样，只不过 是二维数组，且是 从 0，0 走到 n m
     * <p>
     * 题目解法：我们的状态转移方程是
     * dp[i][j] = 走到 i j 位置时候的 最小路径和
     * 那么 dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + val
     * 因为只能从 左边 或者 上边走
     * <p>
     * 再考虑下 边界条件，第一行，第一列
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];

        // dp 起始位置
        dp[0][0] = grid[0][0];

        // 初始化 第一行
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // 初始化 第一例
        for (int j = 1; j < m; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // 开始遍历 从 1 1 开始
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int val = grid[i][j];
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + val;
            }
        }

        return dp[n - 1][m - 1];
    }

    /**
     * 63. 不同路径 II
     *
     * 题目解释：和 不同路径有区别的就是
     *  1：中间会有障碍物 obstacleGrid[i][j] = 1
     *  2: 该题要的是 可以走到 n m 位置的 最多的路径
     *
     * 题目解法：那么 状态转移方程就很明显了
     * dp[i][j] = 走到 i j 位置的 最多路径
     * dp[i][j]
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

    }

}
