package trie;

/**
 * 208. 实现 Trie (前缀树)
 *
 * 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补全和拼写检查。
 *
 * @summary Trie
 * @author: bf
 * @Copyright (c) 2025/12/30, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/30 11:34
 */
public class Trie {

    private TrieNode root;

    /**
     * 初始化前缀树对象。
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * 向前缀树中插入字符串 word 。
     * 从 root 开始
     * 对 word 的每个字符：
     * - 如果子节点不存在 → 创建
     * - 移动到子节点
     * 最后一个字符节点：
     * - isEnd = true
     */
    public void insert(String word) {
        // 将 word 构建到 root 下面的节点当中
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.child[idx] == null) {
                // 找不到该分支，再次创建一个新的 树分支
                node.child[idx] = new TrieNode();
            }
            node = node.child[idx];
        }
        node.isEnd = true;
    }

    /**
     * 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
     * 按字符向下走
     * - 中途走不下去 → false
     * 最后字符：
     * - 必须 isEnd == true
     */
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.child[idx] == null) {
                return false;
            }
            node = node.child[idx];
        }
        return node.isEnd;
    }

    /**
     *  如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
     *
     *  和 search 类似，但是不要求走到 end
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.child[idx] == null) {
                return false;
            }
            node = node.child[idx];
        }
        return true;
    }

    class TrieNode {

        /*
            只包含小写字母 a-z
            通过 ch - 'a' O(1) 定位
            比 Map 更快、更省内存（在 LeetCode 场景）
         */
        TrieNode[] child = new TrieNode[26];

        // 表示该单词 是否结束
        boolean isEnd;
    }

}
