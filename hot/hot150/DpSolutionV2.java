package hot.hot150;

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
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
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
     * <p>
     * 题目解释：和 不同路径有区别的就是
     * 1：中间会有障碍物 obstacleGrid[i][j] = 1
     * 2: 该题要的是 可以走到 n m 位置的 最多的路径
     * <p>
     * 题目解法：那么 状态转移方程就很明显了
     * dp[i][j] = 走到 i j 位置的 最多路径
     * dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
     * <p>
     * 但是这里有个特殊情况，就是 假如 i,j 中 obstacleGrid[i][j] = 1 有障碍物的情况下 就需要将这边设置成 0
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 如果开头就是障碍物
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;

        // 初始化 DP
        int[][] dp = new int[n][m];

        // 先初始化,作为
        dp[0][0] = 1;

        // 初始化 第一行 & 第一列
        for (int i = 1; i < n; i++) {
            // 如果 第一行中间有障碍，就设置 0
            dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : dp[i - 1][0];
        }

        // 第一列
        for (int j = 1; j < m; j++) {
            // 如果 第一行中间有障碍，就设置 0
            dp[0][j] = obstacleGrid[0][j] == 1 ? 0 : dp[0][j - 1];
        }

        // 开始遍历
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int val = obstacleGrid[i][j];
                if (val == 1) {
                    // 遇到了障碍物
                    dp[i][j] = 0;
                } else {
                    // 正常状态转移方程
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[n - 1][m - 1];
    }

    /**
     * 5. 最长回文子串
     * <p>
     * 题目解释：找到 s 的最长的回文串，比如 babad -> bab
     * <p>
     * 题目解法：直接使用 中心扩散法就行了，找一个中心点，往左右两边扩散，找到最长的 回文串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int start = 0;
        int end = 0;
        int n = s.length();

        // 开始遍历
        for (int i = 0; i < n; i++) {
            // 我们分别寻找 奇数 or 偶数的 最长回文长度
            int len1 = getCenter(s, i, i);

            int len2 = getCenter(s, i, i + 1);

            // 找到最大值
            int len = Math.max(len1, len2);

            // 如果超过了 ，重新统计
            if (len > (end - start)) {
                start = i - (len + 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }


    private int getCenter(String s, int left, int right) {
        int length = s.length();
        while (right < length && left >= 0 && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return (right - 1) - (left + 1) + 1;
    }

    /**
     * 97. 交错字符串
     * <p>
     * 题目意思：S3 是否是由 S1 S2 交错组成的
     * <p>
     * 题目解法：题目有明显的状态，典型的一题 dp 问题
     * <p>
     * dp[n][m] 表示  n m 位置 能否组成 S3
     * 那么他的状态转移方程 dp[n][m] = 就很明显了
     * 要不是 n m 的位置 不是 s1 的 n 位置组成 就是 s2 的 m 位置组成
     * <p>
     * 那么 dp[i][j] =
     * dp[i-1][j] = true && s3[i+j-1]  = s1[i-1]
     * or
     * dp[i][j-1] = true && s3[i+j-1] = s1[i-1]
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        // 注意我们的 DP 的状态转移方程
        int n = s1.length();
        int m = s2.length();

        if (n + m != s3.length()) {
            return false;
        }

        // 初始化DP
        boolean[][] dp = new boolean[n + 1][m + 1];
        // 设置 DP 递推起点
        dp[0][0] = true;

        // 只有 S1 组成
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        // 只有 S2 组成
        for (int j = 1; j <= m; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        // 开始遍历
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                        ||
                        (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }

        return dp[n][m];
    }


    /**
     * 72. 编辑距离
     * <p>
     * 题目意思：我们将 word1 转换成 word2 所需要的最小操作数，注意，这里有个重要的点就是 所有的操作必须在 word1 上操作
     * 且，只能进行 插入 删除 替换的操作
     * <p>
     * 题目解法：带有状态，这一题是很明显的 DP 动态规划，所以我们想到的动态规划的
     * 方程就是 dp[i][j] 将 word1  的 i 位置，转换成 word2 的 j 位置，所需要的最小操作数
     * 那么 变到 do[i][j] 就需要进行 插入 删除 替换 等三个操作
     * 那么 完整的状态转移方程就是
     * dp[i][j] = min（ 插入，删除，替换 ）
     * 插入 = dp[i][j-1] + 1 = 我们需要在 i 位置插入一个元素才能满足 j word2 的需求，那么 就需要在 j-1 上 + 一个操作
     * 删除 = dp[i-1][j] + 1 = 删除很好理解，就是我们需要删除掉 word1 上的一个元素,那么就是 i - 1
     * 替换 = dp[i-1][j-1] + 1 = 替换 其实就相当于 i j 现在都不满足我们的要求了，那么我们需要操作一个 元素，然后 i j 都要往前走一位，就是 i-1, j-1
     * dp[i][j] = min(dp[i][j-1] + 1,dp[i-1][j] + 1 , dp[i-1][j-1] + 1)
     * <p>
     * 其中需要注意
     * 假如 i j 位置上面的 元素一样，那么我们是不需要操作的，那么直接 dp[i][j] = 0
     * <p>
     * 初始化也需要注意，举个例子：
     * abc -> '' 需要三个操作 就是 全部删除，所以 dp[i][0] = i
     * '' -> abc 需要三个操作 就是 全部插入，所以 dp[0][j] = j
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // 初始化 DP
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            dp[i][j - 1] + 1,
                            Math.min(dp[i - 1][j] + 1, dp[i - 1][j - 1] + 1)
                    );
                }
            }
        }

        return dp[n][m];
    }

    /**
     * 221. 最大正方形
     * <p>
     * 题目意思：找到 二维矩阵里面 都是 1 的
     * <p>
     * 题目解法：和 最小路劲和 和 不同路径 题目很类似，我们定义一个 dp[i][j] 表示当前能组成的 最大正方形 的边长
     * 那么 dp[i][j] 的 组成部分 就是 上 左 左上 三个位置,且 因为正方形 需要取 最小的边，所以 就是 min
     * <p>
     * dp[i][j] = min (dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     * + 1 表示当前是1 的 话，边长需要 + 1
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n][m];
        int length = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 如果是 1 那么就 重新判断 正方形，如果是 0 ，那么就不能组成 正方形 dp = 0
                char val = matrix[i][j];
                if (val == '1') {
                    if (i == 0 || j == 0) {
                        // 边界条件
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(
                                dp[i - 1][j],
                                Math.min(dp[i][j - 1], dp[i - 1][j - 1])
                        ) + 1;
                    }
                }

                // 每次判断完，需要重新统计最大的 length
                length = Math.max(length, dp[i][j]);
            }
        }

        return length * length;
    }


    /**
     * 123. 买卖股票的最佳时机 III
     * <p>
     * 题目意思：给定一个数组，设计一个算法来计算 我们能拿到的最大利润，且最多可以完成 2 笔 交易
     * <p>
     * 题目解法：数组 prices, 且存在状态转移，所以我们很容易联想到 动态规划
     * 结合题目的意思，我们的 动态转移方程 就是 dp[i][k][n] 第 i 天，多少次交易，持不持有股票 能拿到的最大利润
     * 其中：
     * i: 第 i 天，
     * k: 多少次交易
     * n: 0/1 是否持有股票
     * 那么他的状态转移方程就很明显了，
     * dp[i][k][n] = 持有股票 or 不持有股票
     * 持有股票：
     * dp[i][k][1] = 前一天也持有 + 今天买入
     * 不持有股票：
     * dp[i][k][0] = 前一天也不持有 + 今天卖出
     * <p>
     * 其中，我们的状态转移方程的时候，k 没有默认值，所以默认值 我们遍历 k 得到 持有 或者 不持有股票的结果即可
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;

        // 初始化 DP
        int[][][] dp = new int[n][3][2];

        for (int k = 0; k <= 2; k++) {
            // 不持有股票
            dp[0][k][0] = 0;

            // 持有股票
            dp[0][k][1] = -prices[0];
        }

        // 开始遍历
        for (int i = 1; i < n; i++) {
            for (int k = 1; k < 3; k++) {
                // 持有股票 = 今天不动 or 今天买入 今日买入，那么 k 代表已经交易过一次了，需要 k-1
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);

                // 不持有股票 = 今天不动 or 今日卖出
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
            }
        }

        return dp[n - 1][2][0];
    }

    /**
     * 188. 买卖股票的最佳时机 IV
     * <p>
     * 题目意思：该题和 123 买股票的最佳时期一样，只不过一个是 最多 2次交易，这个是做多 K 次交易
     * <p>
     * 题目解法：同 123
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        // 这里有个有点点，如果 k > n/2，那么他可以每次都能买卖，
        if (k > n / 2) {
            int ans = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    ans += (prices[i] - prices[i - 1]);
                }
            }
            return ans;
        }

        // 初始化 dp
        int[][][] dp = new int[n][k + 1][2];

        for (int i = 0; i <= k; i++) {
            dp[0][i][0] = 0;

            dp[0][i][1] = -prices[0];
        }

        // 开始遍历
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                // 持有股票 = 今天不动 or 今天买入，今日买入 需要 k-1 减去一次交易
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);

                // 不持有股票 = 今天不动 or 今天卖出
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
            }
        }

        return dp[n - 1][k][0];
    }

}
