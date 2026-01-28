package dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一维动态规划
 *
 * @summary DpSolution
 * @author: bf
 * @Copyright (c) 2026/1/20, © 拜耳作物科学（中国）有限公司
 * @since: 2026/1/20 21:18
 */
public class DpSolution {

    /**
     * 70. 爬楼梯
     * <p>
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * <p>
     * 解题思路：典型的 DP 题目
     * <p>
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
     * <p>
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
     * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 解题思路：这也是一道典型的 动态规划题目，状态转移方程
     * 前一个房子 偷 或者不偷
     * 当前房子 偷的话，dp[i] = dp[i - 2] + num[i]
     * 不偷当前的房子 dp[i] = dp[i - 1]
     * 所以完整的状态转移方程就是 dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
     * <p>
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

    /**
     * 139. 单词拆分
     * <p>
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     * <p>
     * 就是看 单词字典 wordDict 能不能拼接成 单词 s
     * <p>
     * 解法思路：使用  DP ，我们将 s 做切割，i j, 看 i j 之间的单词 有没有在 字典中存在，如果存在，就更新 dp 为 true
     * 然后我们直到遍历完，看 dp[j] 是 true 还是 false
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null) {
            return false;
        }

        int length = s.length();

        // 先 字典去重
        Set<String> dict = new HashSet<>(wordDict);

        // 创建 dp
        boolean[] dp = new boolean[length + 1];

        dp[0] = true;

        // 初始化刚开始的时候,注意 一定要 从 0 开始
        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i; j++) {
                // 从 j - i 之间判断
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[length];
    }

    /**
     * 322. 零钱兑换
     * <p>
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * <p>
     * 解题思路：这题和单词拆分是一个意思 coins 随意组合，最少能组合成 amount 的个数
     * <p>
     * 状态转移方程：
     * dp[i] = 凑出 金额 i 所需要的最少硬币数
     * <p>
     * 所以 dp[i] = min(dp[i], dp[i-coin] + 1)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        // 初始化
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    // 可以用钱来凑了
                    // 这里的 dp[i - coin] 很关键，就相当于 我 1 2 5 ，我到 6 的时候， 看有没有 dp[5] + 1
                    dp[i] = Math.max(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }


    /**
     * 300. 最长递增子序列
     * <p>
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * <p>
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * <p>
     * 解题思路：这题和 322 题目又是有点类似
     * <p>
     * 状态转移方程
     * dp[i] = 以 nums[i] 结尾的最长递增子序列
     * <p>
     * 如果是 i > j 的情况
     * dp[i] = max(dp[i], d[j] + 1)
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        int[] dp = new int[length];

        int ans = 1;

        for (int i = 0; i < length; i++) {
            // 这个初始化 一定要是 1, 且一定要在里面初始化，每次都要 重新初始化 以便计算
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // 有递增，开始更新我们的 dp
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }


    /**
     * 120. 三角形最小路径和
     * <p>
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     * <p>
     * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
     * 也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     * <p>
     * 解法思路：dp[i] 当前位置 + 下面一层 dp[i] + dp[i+1] 的最小值
     * <p>
     * 每一层的选择 只依赖上一层
     * 每个位置 (i, j) 的最优值 = 上一层某些位置的最优值 + 自己
     * <p>
     * 我们定义 dp[i][j] = 从顶部走到 i行 j列 位置的最小路径和
     * <p>
     * 注意两个临界条件 从最左边 or 最右边 开始走
     * 最左边： dp[i][0] = dp[i-1][0] + triangle[i][0]
     * 最右边:  dp[i][i] = dp[i-1][i-1] + triangle[i-1][i-1]
     * 中间：   dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], triangle[i][j])
     * <p>
     * 我们使用 二维 DP 数组求解,然后走到最后一行就结束
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        // 先初始化, 三角形 顶点点位
        dp[0][0] = triangle.get(0).get(0);

        // 开始遍历 从 第二行开始
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                Integer val = triangle.get(i).get(j);
                if (j == 0) {
                    // 最左边
                    dp[i][0] = dp[i - 1][0] + val;
                } else if (j == i) {
                    // 最右边
                    dp[i][j] = dp[i - 1][j - 1] + val;
                } else {
                    // 中间位置
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + val;
                }
            }
        }

        // 我们取最后一行的最小值就行
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }

        return ans;
    }

    /**
     * 64. 最小路径和
     * <p>
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     * <p>
     * 解题思路：和 120 题目一样，这题不一样的点是 每次只能向下 或者 想右移动一步
     * <p>
     * dp 表示
     * dp[i][j] = 从 0，0 位置 走到 i,j 位置的最小路径和
     * 然后因为智能 向下 or 向右移动，所以状态转移方程就出来了
     * dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     * <p>
     * 然后我们注意 第一行 + 第一列的零界点就行
     * 第一行：只从左边来 dp[0][j] = dp[0][j-1] + val
     * 第一列：只能从上边来  dp[i][0] = dp[i-1][0] + val
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // 初始化
        dp[0][0] = grid[0][0];

        // 然后分别初始化 第一行 + 第一列
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // 开始遍历,一定要从1开始
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 63. 不同路径 II
     * <p>
     * 给定一个 m x n 的整数数组 grid。一个机器人初始位于 左上角（即 grid[0][0]）。机器人尝试移动到 右下角（即 grid[m - 1][n - 1]）。机器人每次只能向下或者向右移动一步。
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。机器人的移动路径中不能包含 任何 有障碍物的方格。
     * 返回机器人能够到达右下角的不同路径数量。
     * <p>
     * 解法思路：
     * dp[i][j] = 从 0，0 走到 i j 的不同的路劲数
     * 然后需要注意的点激素 如果 obstacleGrid[i][j] = 1 的话，那么代表这个是障碍物 dp[i][j] = 0
     * 然后和 64 题目基本一样，我们同样初始化 第一行 + 第一列，遇到石头 设置 0
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        // 开头就遇到石头了
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        dp[0][0] = 1;

        // 第一行
        for (int i = 1; i < n; i++) {
            dp[0][i] = obstacleGrid[0][i] == 1 ? 0 : dp[0][i - 1];
        }

        // 第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : dp[i - 1][0];
        }

        // 开始遍历
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    // 左边和上边来的 路径可能性 相加
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }


    /**
     * 5. 最长回文子串
     * <p>
     * 方法1：中心扩散法
     * 方法2：动态规划
     * <p>
     * 我们先用中心扩散法
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            // 奇数
            int len1 = getCenter(s, i, i);

            // 偶数
            int len2 = getCenter(s, i, i + 1);

            // 开始计算
            int len = Math.max(len1, len2);

            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    /**
     * 获取 s 符合条件的 长度
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private int getCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }


    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }

        int length = s.length();

        // 我们分别用 奇数 or 偶数 来找到
        int start = 0;
        int maxLen = 0;

        for (int i = 0; i < length; i++) {
            // 奇数
            int left = i;
            int right = i;

            while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
                // 如果另外一个 长度超过了，就重新计算
                if (right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                    start = left;
                }

                left--;
                right++;
            }

            // 偶数
            left = i;
            right = i + 1;

            while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
                // 如果另外一个 长度超过了，就重新计算
                if (right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                    start = left;
                }

                left--;
                right++;
            }
        }

        return s.substring(start, start + maxLen);
    }


    /**
     * 97. 交错字符串
     * <p>
     * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
     * <p>
     * 注意：因为是交错，所以 字符串的顺序是不能变的
     * <p>
     * 解题思路：
     * S3 的每一个字符，一定来自 S1 或者 S2
     * 所以我们必须记住，S1 用了多少，S2 用了多少，这天然就是一个二维的状态
     * 所以我们的状态方程为：
     * dp[i][j] = s1 的前 i 个字符 和 s2 的前 j 个字符，能否交错组成 s3 的 前 i + j 个字符
     * 所以：
     * dp[i][j] = dp[i-1][j] == true && s1[i-1] == s3[i + j - 1]
     * or
     * dp[i][j] = dp[i][j-1] == true && s2[j-1] == s3[i + j - 1]
     * 然后 初始化
     * dp[0][0] = true
     * 第一行 只用 S2
     * dp[0][j] = dp[0][j-1] && s2[j-1] == s3[j-1];
     * 第一列 只用 S1
     * dp[i][0] = dp[i-1][0] && s1[i-1] == s3[i-1];
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        // 思路，dp[i][j] = 前面组成之后，这个字符从S1 或者 S2 能匹配到
        int m = s1.length();
        int n = s2.length();

        boolean[][] dp = new boolean[m + 1][n + 1];

        // 初始化
        dp[0][0] = true;

        // 初始化 第一行，因为第一行 dp[0][j] 所以只能用 S2 进行初始化
        for (int i = 1; i <= n; i++) {
            dp[0][i] = dp[0][i - 1] && s2.charAt(i - 1) == s3.charAt(i - 1);
        }

        // 第一列
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        // 开始遍历
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                        ||
                        (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }

        return dp[m][n];
    }


    /**
     * 72. 编辑距离
     * <p>
     * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
     * 你可以对一个单词进行如下三种操作：
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * <p>
     * 解题思路：dp[i][j] = 把 word1 的前 i 个字符 变成 word2的 前 j个字符 所需的最小操作
     * <p>
     * 状态转移方程：
     * word1[i] == word2[j]
     * 两个相等，梭鱼不需要操作。dp[i][j] = dp[i-1][j-1]
     * word1[i] != word2[j]
     * 不一样的话，就需要三个操作，插入 + 删除 + 替换
     * 插入，需要从 word1 往前一个继续匹配： dp[i][j] = dp[i - 1][j] + 1
     * 删除：dp[i][j] = dp[i][j - 1] + 1
     * 替换：替换，i j，都需要往前找，所以 dp[i][j] = dp[i - 1][j - 1] + 1
     * <p>
     * 所以状态转移方程 =
     * dp[i][j] = min（ 插入，删除，替换 ）
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        // 初始化
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i <= n; i++) {
            dp[0][i] = i;
        }

        // 开始遍历
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 不需要变
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            // 插入 + 删除 + 替换
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + 1
                    );
                }
            }
        }

        return dp[m][n];
    }

    /**
     * 123. 买卖股票的最佳时机 III
     * <p>
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * <p>
     * 解题思路：动态规划 + 状态机
     * 每天只需要关系，现在是第几次交易，手里还有没有股票
     * <p>
     * 我们用 dp[i][k][0/1]
     * i: 第 i 天
     * k: 是否还能完成第 K 次交易，k = 0/1/2
     * 0/1: 是否持有股票
     * 0：不持有
     * 1： 持有
     * <p>
     * 状态定义：
     * dp[i][k][0] = 第 i 天结束后，最多 k 次交易，不持股的最大利润
     * dp[i][k][1] = 第 i 天结束后，最多 k 次交易，持股的最大利润
     * <p>
     * 所以 状态转移方程：就是 今天要不买，要不不买
     * 持股： 今天买入 + 什么也不做 （因为今天买入了一次，所以金额 是 - price ,且做了一次买入动作，k 需要 - 1 ）
     * dp[i][k][1] = max( dp[i-1][k][1], dp[i-1][k-1][0] - prices[i] )
     * <p>
     * 不持股： 今天卖出 + 什么也不多
     * dp[i][k][0] = max( dp[i-1][k][0], dp[i-1][k][0] + prices[i] )
     * <p>
     * 初始化：
     * 第一天 不买
     * dp[0][k][0] = 0
     * 第一天买入，买入需要计算成本
     * dp[0][k][1] = - prices[0]
     * <p>
     * 为什么结束是:  dp[n-1][2][0]
     * 因为结束 不能持股 + 最多两次交易
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        // 创建状态转移方程
        int[][][] dp = new int[n][3][2];

        // 初始化
        for (int k = 0; k <= 2; k++) {
            dp[0][k][0] = 0;
            dp[0][k][1] = -prices[0];
        }

        // 状态转移
        for (int i = 1; i < n; i++) {
            for (int k = 1; k <= 2; k++) {
                // 不持有 = 啥也不干 + 今天卖出
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                // 持有 = 啥也不干 + 今天买入
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }

        return dp[n - 1][2][0];
    }

    /**
     * 188. 买卖股票的最佳时机 IV
     * <p>
     * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
     * <p>
     * 解题思路：该题的解题思路和 123 最多2次的思路 一模一样，只不过 2 次换成了 K 次
     * 且 还有个小优化的点，就是 k > n/2 代表可以无限次买卖股票，直接 遇到下一天 > 上一天 的价格，然后利润高累加 即可
     *
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        // 可以 prices 内，无限次买卖股票
        if (k > n / 2) {
            int total = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    total += prices[i] - prices[i - 1];
                }
            }
            return total;
        }

        // 走到这里，代表不能无限次买卖股票，那么我们就使用 dp 去进行状态转移
        // dp[i][k][flag] i: 第几天 k: 最多多少次交易 flag: 持有股票的状态 0-不持有 1-持有
        int[][][] dp = new int[n][k + 1][2];

        // 初始化,初始化 第0天，持有 or 不持有股票的 初始化值,注意，初始化，第几次 K 是否持有股票 全部进行初始化
        for (int i = 0; i <= k; i++) {
            // 不持有股票的 成本是 0
            dp[0][i][0] = 0;
            // 持有股票的 成本是 当前天的 股票价格
            dp[0][i][1] = -prices[0];
        }

        // 状态转移
        for (int i = 1; i < n; i++) {
            // 第1次交易开始
            for (int j = 1; j <= k; j++) {
                // 持有股票 = 前一天持有 却什么不操作 or 今天持有买入(今天买入的话，前一天一定要不持有，且做了一次交易操作，j 一定是需要 -1 )
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);

                // 不持有股票 = 前一天不持有 or 昨天持有 + 今天卖出
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
            }
        }

        // 最后一天 一定是 不持有股票的
        return dp[n - 1][k][0];
    }

    /**
     * 221. 最大正方形
     * <p>
     * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
     * <p>
     * 解题思路：固定 1 之后，往 下 or  右 进行 1 的寻找
     * <p>
     * dp: 以 i，j 作为右下角的最大正方形的边长是多少
     * <p>
     * dp[i][j] = 以 i, j 为右下角的 最大正方形的 边长是多少
     * <p>
     * 为阿妈选择 右下角，因为 右下角同时受 左 上 左上 约束
     * <p>
     * 所以状态转移方程为：
     * 初始化：
     * dp[0][0] = 0
     * 右下角：
     * dp[i][j] = min(
     * dp[i-1][j]  上
     * dp[i][j-1]  左
     * dp[i-1][j-1]  左上
     * ) + 1
     * 因为是正方形，所以要取最小值
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        // 使用 二维 dp 进行 操作
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];
        int maxLength = 0;

        // 开始遍历
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 只有 = 1 的时候再进行寻找
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        // 边界情况，直接 + 1
                        dp[i][j] = 1;
                    } else {
                        // 使用状态转移方程，寻找
                        dp[i][j] = Math.min(
                                Math.min(dp[i - 1][j], dp[i][j - 1]),
                                dp[i - 1][j - 1]
                        ) + 1;
                    }
                }

                maxLength = Math.max(maxLength, dp[i][j]);
            }
        }

        return maxLength * maxLength;
    }

}
