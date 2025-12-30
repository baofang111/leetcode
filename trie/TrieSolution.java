package trie;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典树
 *
 * @summary TrieSolution
 * @author: bf
 * @Copyright (c) 2025/12/30, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/30 11:33
 */
public class TrieSolution {

    static class TrieNode {
        TrieNode[] child = new TrieNode[26];
        // 记录完整单词，用于快速 加入结果 or 去重
        String word;
    }

    private TrieNode root = new TrieNode();
    private List<String> result = new ArrayList<>();

    private void insert(String word) {
        TrieNode node = root;
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            int idx = c - 'a';
            if (node.child[idx] == null) {
                node.child[idx] = new TrieNode();
            }
            node = node.child[idx];
        }
        // 记录完整单词
        node.word = word;
    }


    /**
     * 212. 单词搜索 II
     * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
     * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
     * <p>
     * 暴力做法为什么不行？
     * - 单词多
     * - DFS 分支指数级
     * - 大量单词有相同前缀
     * 会直接爆炸
     * <p>
     * 核心思路：
     * 把所有单词放进 Trie，
     * 在 board 上 DFS 的同时，在 Trie 中同步走，
     * 一旦当前路径不是任何单词的前缀，立刻剪枝。
     *
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords(char[][] board, String[] words) {
        // 1： 开始构建 前缀树
        for (String word : words) {
            this.insert(word);
        }

        int n = board.length;
        int m = board[0].length;

        // 2：开始遍历 迭代
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dfs(board, i, j, root);
            }
        }

        return result;
    }

    /**
     * 从 board 开始 dfs 遍历
     *
     * @param board
     */
    private void dfs(char[][] board, int i, int j, TrieNode node) {

        char c = board[i][j];

        // 判断剪枝条件，如果已经访问过，或者 前缀树里面压根找不到，直接返回
        if (c == '#' || node.child[c - 'a'] == null) {
            return;
        }

        // 前缀树一定要往下走
        node = node.child[c - 'a'];

        // 当遍历到一定条件的时候，我们在前缀树中找到了一个单词的话，就代表 board 中存在这个单词
        if (node.word != null) {
            result.add(node.word);
            // 注意 这里一定要将单词置空，不然结果会多出数据
            node.word = null;
        }

        // 走到这里 表示 c 这个位置已经访问过了
        board[i][j] = '#';

        // 四个方向开始 DFS
        if (i > 0) {
            dfs(board, i - 1, j, node);
        }
        if (i < board.length - 1) {
            dfs(board, i + 1, j, node);
        }
        if (j > 0) {
            dfs(board, i, j - 1, node);
        }
        if (j < board[0].length - 1) {
            dfs(board, i, j + 1, node);
        }

        // 这里一定需要回溯，用来 第二个单词寻找的时候 也可以用上
        board[i][j] = c;
    }

}
