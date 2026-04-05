package trie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @summary TrieSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/05 19:46:26
 */
class TrieSolutionV3 {

    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        String word;
    }

    TrieNode root = new TrieNode();
    List<String> result = new ArrayList();

    /**
     * 212. 单词搜索 II
     * <p>
     * 【题目描述】
     * 给定一个二维字符矩阵 board，和一个单词数组 words
     * 需要从矩阵中找出所有存在于 words 数组中、且可在矩阵里通过「上下左右相邻字符」拼接而成的单词
     * <p>
     * 【解题思路】
     * 核心解法：前缀树（字典树）+ DFS 回溯
     * 1. 构建前缀树：将 words 中的所有单词插入前缀树，用于快速匹配和剪枝
     * 2. 深度搜索：遍历矩阵的每一个单元格作为起点，向上下左右四个方向递归搜索
     * 3. 匹配校验：搜索过程中同步匹配前缀树，匹配失败则直接剪枝，匹配到完整单词则记录结果
     * <p>
     * 【算法优势】
     * 利用前缀树的特性，匹配不到前缀时直接终止当前路径搜索，大幅减少无效遍历，提升执行效率
     *
     * @param board 二维字符矩阵
     * @param words 待查找的单词数组
     * @return 矩阵中能找到的所有目标单词
     */
    public List<String> findWords(char[][] board, String[] words) {
        for(String word: words){
            this.insertTrie(word);
        }

        // 遍历矩阵，然后通过前缀树，寻找我们需要的值
        int n = board.length;
        int m = board[0].length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                // 通过 dfs 遍历 寻找我们需要的值
                this.dfs(i, j, board, root);
            }
        }

        return result;
    }

    /**
     DFS 寻找的步骤
     1：判断有没有走过，有，直接返回，进行遍历剪枝
     2：寻找 board[i][j] 在前缀树的下一个节点
     3：如果 下一个节点不存在，那么肯定没有，直接返回，
     4：判断下一个节点中的 cur.word 有没有值，有值，肯定是找到了，加入我们的结果集当中即可
     5：做 遍历完 标识
     6：上下左右 进行扩散巡展
     7：回溯 还原 board[i][j] 的值
     */
    private void dfs(int i, int j, char[][] board, TrieNode cur){
        if(cur == null){
            return;
        }

        char c = board[i][j];

        if(c == '#'){
            return;
        }

        cur = cur.child[c - 'a'];
        if(cur == null){
            return;
        }

        if(cur.word != null){
            result.add(cur.word);
            // 一定要设置为空，防止出现重复数据
            cur.word = null;
        }

        board[i][j] = '#';

        if(i > 0){
            dfs(i - 1, j, board, cur);
        }
        if(i < board.length - 1){
            dfs(i + 1, j, board, cur);
        }
        if(j > 0){
            dfs(i, j - 1, board, cur);
        }
        if(j < board[0].length - 1){
            dfs(i, j + 1, board, cur);
        }

        board[i][j] = c;
    }

    /**
     * 将单词插入前缀树
     */
    private void insertTrie(String word){
        int length = word.length();

        TrieNode cur = root;
        for(int i = 0; i < length; i++){
            int charIndex = word.charAt(i) - 'a';
            if(cur.child[charIndex] == null){
                cur.child[charIndex] = new TrieNode();
            }
            cur = cur.child[charIndex];
        }

        cur.word = word;
    }

}
