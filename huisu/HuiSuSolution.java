package huisu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 回溯相关算法
 *
 * @summary HuiSuSolution
 * @author: bf
 * @Copyright (c) 2025/12/31, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/31 10:08
 */
public class HuiSuSolution {

    private static final String[] MAPPING = {
            "", // 0
            "", // 1
            "abc", // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    private List<String> result = new ArrayList<>();

    /**
     * 17. 电话号码的字母组合
     * <p>
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * <p>
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * <p>
     * 解法：很标准的回溯解法，通过 DFS 不断的 迭代 digits 中的每个元素，然后组成的元素
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return result;
        }

        // 开始 DFS 递归
        letterCombinationsDfs(digits, 0, new StringBuilder());

        return result;
    }

    private void letterCombinationsDfs(String digits, int index, StringBuilder path) {
        // 回溯的达成条件，当我们的 index 步数满足了 digits 的长度，就添加结果，并返回
        if (index == digits.length()) {
            result.add(path.toString());
            return;
        }

        // 取出当前 位置的所有可选择 单词项
        String subWords = MAPPING[digits.charAt(index) - '0'];

        // 开始遍历  添加结果 并回溯
        for (char c : subWords.toCharArray()) {
            // 添加结果
            path.append(c);
            // 遍历探索
            letterCombinationsDfs(digits, index + 1, path);
            // 撤销选择
            path.deleteCharAt(path.length() - 1);
        }
    }

    /**
     * 77. 组合
     * <p>
     * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
     * <p>
     * 解法：该题也是回溯经典题目 foreach -> n , 然后  1 - n 不断的遍历出数量是 k 的结果
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        // 从 1 开始 往后遍历 1 -> 2 3 4 , 2 -> 3 4 ,使用 dfs + 剪枝 + 回溯
        combineDfs(1, n, k, new ArrayList<>(), result);

        return result;
    }


    private void combineDfs(int start, int n, int k, List<Integer> path, List<List<Integer>> result) {
        if (start > n - k) {
            return;
        }

        // 添加结束条件
        if (k == path.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 开始往后遍历
        for (int i = start; i <= n; i++) {
            // 添加结果
            path.add(i);
            // DFS 搜索
            combineDfs(i + 1, n, k, path, result);
            // 回溯
            path.remove(path.size() - 1);
        }
    }

    /**
     * 46 - 全排列
     * <p>
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * <p>
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * <p>
     * 解法：和 77 组合类似，但是他的限定条件不一样
     * 1 - 2, 3  2 - 1,3 3 - 2,1
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // 同时还要使用一个 已经使用的 来进行数据过滤
        boolean[] used = new boolean[nums.length];

        permuteDfs(nums, used, new ArrayList<>(), result);

        return result;
    }

    private void permuteDfs(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 注意 这里每次 都需要从 0 开始 遍历
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            path.add(nums[i]);

            permuteDfs(nums, used, path, result);

            // 同时回溯两个条件
            path.remove(path.size() - 1);
            used[i] = false;

        }
    }


    /**
     * 39. 组合总和
     * <p>
     * 输入：candidates = [2,3,6,7], target = 7
     * 输出：[[2,2,3],[7]]
     * <p>
     * 解法：和 77 组合类似，但是区别的是 candidates 中的数据可以重复使用，且 达成条件不一样
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(candidates);
        combinationSumDfs(candidates, target, 0, 0, new ArrayList<>(), result);

        return result;
    }

    private void combinationSumDfs(int[] candidates, int target, int start, int sum, List<Integer> path, List<List<Integer>> result) {
        // 结束条件
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            int num = candidates[i];
            if (sum + num > target) {
                break;
            }

            path.add(num);

            // 这里因为 2 2 需要重复使用，所以 start 传 i, 如果不重复使用，就要 i+1 了
            combinationSumDfs(candidates, target, i, sum + num, path, result);
            path.remove(path.size() - 1);
        }

    }

    /**
     * LCR 082. 组合总和 II
     * <p>
     * 和 39. 组合总和 一样，只不过同一个元素不能重复使用，
     * <p>
     * 核心思想
     * 排序：让重复元素相邻，方便去重
     * 使用 回溯（DFS） 枚举组合
     * 同一层递归中去重
     * -- if (i > start && candidates[i] == candidates[i - 1]) continue;
     * 因为数组已排序，若 candidates[i] > target，可以直接剪枝
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(candidates);
        combinationSum2Dfs(candidates, target, 0, 0, new ArrayList<>(), result);

        // 这里会重复, 也可以使用 i > start && candidates[i] == candidates[i - 1] 这个进行去重
//        return result.stream().distinct().toList();
        return result;
    }

    private void combinationSum2Dfs(int[] candidates, int target, int start, int sum, List<Integer> path, List<List<Integer>> result) {
        // 结束条件
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            int num = candidates[i];
            if (sum + num > target) {
                break;
            }

            // 同层去重
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            path.add(num);

            // 和 39 不一样的是 这里不能重复使用，所以 往前走一步
            combinationSum2Dfs(candidates, target, i + 1, sum + num, path, result);
            path.remove(path.size() - 1);
        }

    }

    /**
     * 52. N 皇后 II
     * <p>
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 皇后攻击规则：
     * - 同一行 ❌
     * - 同一列 ❌
     * - 同一主对角线 ❌
     * - 同一副对角线 ❌
     * <p>
     * <p>
     * 解法思路: 回溯 + 剪枝
     * <p>
     * 1️⃣ 为什么按「行」递归？
     * 每一行 必须且只能放 1 个皇后
     * 递归深度 = n
     * 每一层尝试所有列
     * 👉 把二维问题转成：
     * 第 row 行，皇后放在哪一列 col
     * <p>
     * 2️⃣ 冲突判定怎么做？
     * ❌ 不能每次扫描棋盘（O(n)）
     * 会超时
     * ✅ 用 3 个一维数组 / 集合
     * 冲突类型	公式	数量
     * 列	col[col]	n
     * 主对角线 \	row - col + n - 1	2n-1
     * 副对角线 /	row + col	2n-1
     *
     * @param n
     * @return
     */
    int count = 0;

    public int totalNQueens(int n) {
        // 我们使用 列 + 正对角戏 + 副对角线 是否被使用过，来判断能不能放入该皇后
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];

        // 我们已行为标准，去遍历列，来判断
        totalNQueensDfs(0, n, col, diag1, diag2);

        return count;
    }

    private void totalNQueensDfs(int row, int n, boolean[] col, boolean[] diag1, boolean[] diag2) {
        // 遍历完所有条件之后，代表有了一种可能性
        if (row == n) {
            count++;
            return;
        }

        // 我们已行作为基准，所以遍历的时候就从列开始遍历
        for (int i = 0; i < n; i++) {
            // 正对角线：row - col = 有一个相同的值，所以我们使用一个 d1 表示这个对角线有没有被放入过值，就可以满足我们该对角线的判断逻辑
            int d1 = row - i + (n - 1); // n-1 是因为会出现负数，做一个角标平移
            int d2 = row + i;

            if (col[i] || diag1[d1] || diag2[d2]) {
                // 已经放入皇后了
                continue;
            }

            // 放入皇后
            col[i] = diag1[d1] = diag2[d2] = true;

            // 开始遍历
            totalNQueensDfs(row + 1, n, col, diag1, diag2);

            // 撤销皇后
            col[i] = diag1[d1] = diag2[d2] = false;
        }
    }


    /**
     * 51 - N 皇后
     * <p>
     * 和 上一题一样，只不过这一题我们需要不把所有的可能性结果拿出来
     * <p>
     * 解法：遍历操作的结果和 52 不同，其他都相同
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        // 我们使用 列 + 正对角戏 + 副对角线 是否被使用过，来判断能不能放入该皇后
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];

        // 适用一个 数组，来记录第 row 行的皇后，应该放在 第几列
        int[] queens = new int[n];

        solveNQueensDfs(0, n, col, diag1, diag2, queens, result);

        return result;
    }

    private void solveNQueensDfs(int row,
                                 int n,
                                 boolean[] col,
                                 boolean[] diag1,
                                 boolean[] diag2,
                                 int[] queens,
                                 List<List<String>> result) {
        if (row == n) {
            // 和 52 不一样的是，这里需要构建 棋盘
            result.add(buildBoard(n, queens));
            return;
        }

        for (int i = 0; i < n; i++) {
            int d1 = row - i + (n - 1);
            int d2 = row + i;

            if (col[i] || diag1[d1] || diag2[d2]) {
                continue;
            }

            col[i] = diag1[d1] = diag2[d2] = true;

            // 记录 queens 的 第几行的 第 i 列，是皇后未知
            queens[row] = i;

            solveNQueensDfs(row + 1, n, col, diag1, diag2, queens, result);

            col[i] = diag1[d1] = diag2[d2] = false;

        }
    }

    /**
     * 构建结果，我们从 行 --> 列 来进行构建
     *
     * @param n
     * @param queens
     * @return
     */
    private List<String> buildBoard(int n, int[] queens) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            result.add(new String(row));
        }

        return result;
    }

    /**
     * 22. 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * <p>
     * 解法：开头只能是 ( , 当 ( 后面可以添加 ( or )
     * 所以我们使用两个变量，一个 left, 一个 right ,分别记录，左边和右边分别放了多少 (  or )
     * 这样，当 left < n ，代表左边还可以放，那么就进行放入 + DFS + 回溯
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        generateParenthesisDfs(n, 0, 0, new StringBuilder(), result);

        return result;
    }

    private void generateParenthesisDfs(int n, int left, int right, StringBuilder path, List<String> result) {
        // 临界条件，两个 left or right 都满足了我们的要求 = n
        if (left == n && right == n) {
            result.add(path.toString());
            return;
        }

        // 注意 这里必须 先在左后右, 如果我们 直接使用 left < n or right < n  判断，他是不能区分 ( ) 的先后顺序的
        // 只有 right < left 表示已经有过 left 了，就可以满足要求了

        // 左边还有位置
        if (left < n) {
            path.append("(");
            generateParenthesisDfs(n, left + 1, right, path, result);
            path.deleteCharAt(path.length() - 1);
        }

        // 右边还有位置
        if (right < left) {
            path.append(")");
            generateParenthesisDfs(n, left, right + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }


    /**
     * 79. 单词搜索
     * <p>
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * <p>
     * 解法思路：简单想法来说，
     * - 1：我们遍历 board ，然后找到 word 的开头，
     * - 2: 记录该位置 已经被使用，
     * - 3: 然后从 该位置，上下左右进行遍历需要下一个位置
     * - 4：看能不能找完全程
     * <p>
     * 这里我们永原地更改做一个小优化，不使用 已被使用的 单独空间来存储该 数据
     * <p>
     * 所以 DFS 的核心就出来了，从 word 的 index = 0 的位置开始寻找，直到找到 index = word.length - 1 ，那么就代表我们找到了该值
     *
     * @param board
     * @param word
     * @return
     */

    public boolean exist(char[][] board, String word) {
        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (existDfs(0, word, i, j, board)) {
                    // DFS 寻找满足条件的 word
                    return true;
                }
            }
        }

        return false;
    }

    private boolean existDfs(int index, String word, int i, int j, char[][] board) {
        // 遍历✅，找到了完整的 word 单词
        if (index == word.length()) {
            return true;
        }

        // 判断 i j 的临界条件，防止越界
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }

        // 判断 当前 board 中的位置，是不是 word 当前单词的位置，index = 0 的时候就是找开头
        if (board[i][j] != word.charAt(index)) {
            return false;
        }

        // 原地更改值，防止从该点出发的时候，再次走到该位置，注意我们一定要将当前值拿出来，以便后续的回溯
        char temp = board[i][j];
        board[i][j] = '#';

        // 从该点出发，上下左右进行 DFS 遍历寻找，上下左右有任何一个满足 index + 1 的条件，就返回true,进行下一个值的寻找
        if (existDfs(index + 1, word, i + 1, j, board)
                || existDfs(index + 1, word, i - 1, j, board)
                || existDfs(index + 1, word, i, j + 1, board)
                || existDfs(index + 1, word, i, j - 1, board)) {
            return true;
        }

        // 回溯
        board[i][j] = temp;

        // 结束了还没找到 就是 false
        return false;
    }

}
