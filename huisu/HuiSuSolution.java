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
     *
     * 和 39. 组合总和 一样，只不过同一个元素不能重复使用，
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(candidates);
        combinationSum2Dfs(candidates, target, 0, 0, new ArrayList<>(), result);

        // 这里会重复

        return result.stream().distinct().toList();
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

            path.add(num);

            // 和 39 不一样的是 这里不能重复使用，所以 往前走一步
            combinationSum2Dfs(candidates, target, i+1, sum + num, path, result);
            path.remove(path.size() - 1);
        }

    }


}
