package dp_1;

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

}
