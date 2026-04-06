package huisu;

import java.util.*;

/**
 * 回溯相关算法练习
 * <p>
 * 回溯算法：一种暴力搜索的通用算法，本质就是深度优先搜索
 * 核心逻辑：做出选择 - 递归深入 - 撤销选择（回溯）
 * 适用场景：组合，排列，子集，括号生成 等等所有需要枚举所有可能的问题
 *
 * @summary HuiSuSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/05 21:26:08
 */
public class HuiSuSolutionV3 {

    private static String[] MAPPING = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };


    /**
     * 17. 电话号码的字母组合
     * <p>
     * 题目意思：给定一个仅由数字 2-9 组成的字符串 digits，每个数字对应手机九宫格键盘上的一组字母
     * 例如：2=abc，3=def，要求输出所有数字能拼接出的字母组合
     * <p>
     * 题目解析：典型的回溯（DFS）枚举问题
     * 核心逻辑：按数字顺序，逐个选择字母进行拼接，递归搜索所有可能的组合
     * 终止条件：当前拼接的字符串长度 = 输入数字串的长度，即为一个合法组合
     * 执行流程：固定当前选择的字母 → 递归处理下一个数字 → 回溯撤销选择，尝试下一个字母
     */
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null) {
            return result;
        }

        letterCombinations(0, digits, new StringBuilder(), result);

        return result;
    }


    private void letterCombinations(int index, String digits, StringBuilder path, List<String> result) {
        if (path.length() == digits.length()) {
            result.add(path.toString());
            return;
        }

        // 寻找得到 2-= - abc
        int charIndex = digits.charAt(index) - '0';
        String word = MAPPING[charIndex];
        int length = word.length();

        // 开始以 2-abc 为基准，进行寻找，寻找下一个数，3-def，所以递归dfs 需要 使用 index + 1
        for (int i = 0; i < length; i++) {
            // 添加元素
            path.append(word.charAt(i));

            this.letterCombinations(index + 1, digits, path, result);

            // 回溯
            path.deleteCharAt(word.charAt(i));

        }
    }


    /**
     * 77. 组合
     * <p>
     * 题目意思：给定一个 n 和 k, 其中 n 表示可以取 1-n 中的所有整数，K 表示 K个个数的组合
     * 例如：n=4,k=2,我们要找的就是类似于 1,2 1,3 1,4 这种组合
     * <p>
     * 题目解析：典型的回溯 DFS 题目
     * 核心逻辑：知道N 的话，我们就固定 1 开始，从 后面不断的往我们的结果集中添加元素
     * 终止条件：直到 path.size = k 就满足条件了
     * 执行流程：固定 1 开始不断的 往下面找
     *
     *
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (n == 0 || k == 0) {
            return result;
        }

        // 回溯，固定 1 开心寻找 2 | 3 | 4
        combineDFS(1, n, k, new ArrayList<>(), result);

        return result;
    }


    private void combineDFS(int start, int n, int k, List<Integer> path, List<List<Integer>> result) {
        // 满足条件的终止条件
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= n; i++) {
            path.add(i);

            // 因为我们是固定 1 寻找 2 ，所以这里是 i + 1
            combineDFS(i + 1, n, k, path, result);

            // 回溯
            path.removeLast();
        }
    }

    /**
     * 46. 全排列
     * <p>
     * 题目意思：给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
     * 例如：数组 [1,2] 的全排列为 [1,2]、[2,1]
     * <p>
     * 题目解析：典型的回溯算法问题，与组合问题的选择规则不同
     * 核心特点：结果中每个数字只能使用一次，因此需要 used 数组标记元素是否被选中
     * 遍历规则：每次可遍历整个数组，仅选择未被使用的元素，保证排列的所有顺序
     * <p>
     * 终止条件：当前路径长度 path.size() == 数组长度 nums.length
     * <p>
     * 核心区别：
     * 组合问题：只能向后选择，不回头，使用 start 控制起始位置
     * 全排列：可任意选择未使用的元素，允许回头，使用 used 数组去重
     */
    public List<List<Integer>> permute(int[] nums) {
        // 该题的核心，就是使用一个 used 判断这个元素有没有走过，用来过重复过滤 + 递归剪枝
        // 然后每次我们寻找 都是 0 - length 开始，满足的是长度，举个例子 1-4，我们从 3 开始找，3 - 4 因为长度不够，所以会继续寻找
        // 然后 继续冲 1 开始遍历，然后 2，得到 3-4-1-2 ,也就是我们需要寻找的一个值

        List<List<Integer>> result = new ArrayList<>();
        if (nums == null) {
            return result;
        }

        // 一定要使用一个 已使用过标识，来得到我们要求的结果值
        boolean[] used = new boolean[nums.length];

        permuteDFS(used, nums, new ArrayList<>(), result);

        return result;
    }

    private void permuteDFS(boolean[] used, int[] nums, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 寻找所有的值，所以要 从 0 开始找
        for (int i = 0; i < used.length; i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            path.add(nums[i]);

            permuteDFS(used, nums, path, result);

            path.removeLast();
            used[i] = false;
        }
    }


    /**
     * 39. 组合总和
     * <p>
     * 题目意思：给定一个 无重复元素的 数组 candidates，然后一个 目标整数 target，
     * 找出 candidates 能组合成 target 的所有元素的集合，注意 元素可以重复
     * <p>
     * 题目解析：该题的回溯和其他有点不同的是，可以使用相同的元素，所以我们在 DFS 的时候，其实还是使用自己
     * 使用一个 num 去相加所有得到的值，然后去和 target 判断
     * 然后终止条件 num = target
     * 计算剪枝：如果 num > target 就直接返回
     * <p>
     * 所以上述的流程中，我们一定要保证 candidates 是有序的
     *
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null) {
            return result;
        }

        Arrays.sort(candidates);

        combinationSumDFS(0, 0, target, candidates, new ArrayList<>(), result);

        return result;
    }

    private void combinationSumDFS(int index,
                                   int total,
                                   int target,
                                   int[] candidates,
                                   List<Integer> path,
                                   List<List<Integer>> result) {
        if (total == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (total > target) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            int num = candidates[i];
            if (num > target) {
                // 计算剪枝
                break;
            }

            path.add(num);
            total += num;

            combinationSumDFS(i, total, target, candidates, path, result);

            path.removeLast();
            total -= num;

        }

    }


    /**
     * 52. N 皇后 II
     * <p>
     * 题目意思：给定一个整数 n，计算 n×n 的棋盘上放置 n 个皇后的**所有合法方案数**
     * N 皇后规则：皇后之间不能同行、同列、同正对角线、同副对角线，彼此互不攻击
     * <p>
     * 题目解析：经典回溯剪枝问题
     * 核心优化：按行逐行放置皇后（每行仅放1个，天然避免行冲突）
     * 核心判断：需要标记列、主对角线、副对角线是否被占用，避免冲突
     * 本题特点：无需保存棋盘，仅统计合法方案的总数
     * <p>
     * 终止条件：所有行的皇后放置完毕，即为一种合法方案
     */
    int count = 0;

    public int totalNQueens(int n) {
        // 我们用 行 作为基准，然后 列 + 正对角线 + 斜对角线 的数组，判断有没有出现过，都没有出现过，那么就代表 这个位置可以放置
        // 当 row = n 了，其实就代表了 我们找到了一种 N 皇后的放置方法
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];

        // 第 0 row 行开始寻找
        totalNQueensDFS(1, n, col, diag1, diag2);

        return count;
    }

    private void totalNQueensDFS(int row,
                                 int n,
                                 boolean[] col,
                                 boolean[] diag1,
                                 boolean[] diag2) {
        // 终止条件，所有行都能够放置成功
        if (row == n) {
            count++;
        }

        // 固定 row,然后我们按照每一列取寻找
        for (int i = 0; i < n; i++) {
            // 对角线的值，自己 行 + 列 计算一下矩阵，就知道为什么这么写了
            int d1 = row + i;
            int d2 = row - i + (n - 1);


            // 如果 列 + 两个对角线 都有了，那么就不满足条件，直接返回
            if (col[i] || diag1[d1] || diag2[d2]) {
                continue;
            }

            col[i] = diag1[d1] = diag2[d2] = true;

            // DFS 寻找下一行
            totalNQueensDFS(row + 1, n, col, diag1, diag2);

            // 回溯
            col[i] = diag1[d1] = diag2[d2] = false;
        }
    }


    /**
     * 51. N 皇后
     * <p>
     * 题目意思：给定一个 n, 找出所有 N皇后的排列
     * <p>
     * 题目解析：这题 和 N 皇后 Ⅱ，一样，只不过多了一点，就是 51 题，只需要找到 满足 N皇后的次数，
     * 这一题是满足 N 皇后条件之后，把所有的 N 皇后返回，其中 'Q' 和 '.' 分别代表了 皇后和空位
     * <p>
     * 我们只需要在遍历寻找符合 N皇后条件的时候，对 i 的位置 设置 'Q' 即可
     *
     */
    public List<List<String>> solveNQueens(int n) {
        // 我们使用 graph 作为构建 N皇后的 结果集
        List<List<String>> result = new ArrayList<>();

        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];

        char[][] graph = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], '.');
        }

        solveNQueensDFS(0, n, graph, col, diag1, diag2, result);

        return result;
    }

    private void solveNQueensDFS(int row,
                                 int n,
                                 char[][] graph,
                                 boolean[] col,
                                 boolean[] diag1,
                                 boolean[] diag2,
                                 List<List<String>> result) {
        if (row == n) {
            List<String> subResult = new ArrayList<>();
            for (char[] rowGraph : graph) {
                subResult.add(new String(rowGraph));
            }
            result.add(subResult);
            return;
        }

        for (int i = 0; i < n; i++) {
            int d1 = row - i + (n - 1);
            int d2 = row + i;

            graph[row][i] = '.';

            if (col[i] || diag1[d1] || diag2[d2]) {
                continue;
            }

            col[i] = diag1[d1] = diag2[d2] = true;
            graph[row][i] = 'Q';

            solveNQueensDFS(row + 1, n, graph, col, diag1, diag2, result);

            graph[row][i] = '.';
            col[i] = diag1[d1] = diag2[d2] = false;
        }
    }

    /**
     * 22. 括号生成
     * <p>
     * 题目意思：给定一个 数字 n, 问我们能生成多少种 成对出现的括号
     * 例如：n = 2 -> ()()  (())
     * <p>
     * 题目解析：还是回溯问题，举个例子，我先放一个 (，然后 有两个参数 left, right
     * 这时候 left + 1, 但是 left 还是 小于 n,代表他还能 放括号。
     * 当 left = n, right = n 的时候，代表两边都放完了，这就是我们要的结果值其中之一
     * <p>
     * 然后我们在回溯，再次寻找，就能拿到我们要的值
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        generateParenthesisDFS(n, 0, 0, new StringBuilder(), result);

        return result;
    }

    private void generateParenthesisDFS(int n,
                                        int left,
                                        int right,
                                        StringBuilder path,
                                        List<String> result) {
        if (left == n && right == n) {
            result.add(path.toString());
            return;
        }

        // 左边还能放
        if (left < n) {
            path.append("(");
            generateParenthesisDFS(n, left + 1, right, path, result);
            path.deleteCharAt(path.length() - 1);
        }

        // 右边不能比左边多
        if (right < left) {
            path.append(")");
            generateParenthesisDFS(n, left, right + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /**
     * 79. 单词搜索
     * <p>
     * 题目意思：给定一个 矩阵 board，我们查询 单词 word 在不在 board 里面
     * <p>
     * 题目解析：这题和 word[] 构建前缀树，然后判断在不在 矩阵 里面 很类似，
     * 从 word[index] 开始，不断的寻找，然后 上下左右寻找，看能不能找到即可
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        // 铆钉一个点，如果在 word[index] 当中，就不断的往下找，注意，该题的意思是，只要找到一个符合条件的，就跳出循环

        int n = board.length;
        int m = board[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (existDFS(0, i, j, board, word)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existDFS(int index, int i, int j, char[][] board, String word) {
        if (index == word.length()) {
            // 找到了单词
            return true;
        }

        // 过滤临界情况
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return false;
        }

        char c = board[i][j];
        if (c != word.charAt(index)) {
            // 直接没找到
            return false;
        }

        // 走到这步，代表 index 步骤这边找到了，我们开始 上下左右 寻找下一个节点，需要做一下标识，标识已经遍历过
        board[i][j] = '#';

        // 上下左右 任何位置找到的话，就返回 true
        if (existDFS(index + 1, i - 1, j, board, word) ||
                existDFS(index + 1, i + 1, j, board, word) ||
                existDFS(index + 1, i, j - 1, board, word) ||
                existDFS(index + 1, i, j + 1, board, word)) {
            return true;
        }

        // 回溯
        board[i][j] = c;

        // 找完了 没找到
        return false;
    }


}
