package hot.hot100V2;

/**
 * 208. 实现 Trie (前缀树)
 *
 * 题目意思：实现一个前缀树
 *
 * 题目解析：我们使用前缀树的专用数据结构，这题就好写了

 *
 * @summary Trie
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/05/08 11:45:13
 */
public class Trie {

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * 插入前缀树
     *
     */
    public void insert(String word) {
        int length = word.length();
        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int c = word.charAt(i) - 'a';
            if (cur.child[c] == null) {
                cur.child[c] = new TrieNode();
            }
            cur = cur.child[c];
        }
        cur.end = true;
    }

    /**
     * 搜索前缀树
     */
    public boolean search(String word) {
        int length = word.length();
        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int c = word.charAt(i) - 'a';
            if (cur.child[c] == null) {
                return false;
            }
            cur = cur.child[c];
        }
        return cur.end;
    }

    /**
     * 判断前缀是否在前缀树中存在, 只要存在就行，不需要判断是否是完整的单词
     */
    public boolean startsWith(String prefix) {
        int length = prefix.length();
        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int c = prefix.charAt(i) - 'a';
            if (cur.child[c] == null) {
                return false;
            }
            cur = cur.child[c];
        }
        return true;
    }

    /**
     * 前缀树实例
     */
    public static class TrieNode{
        TrieNode[] child = new TrieNode[26];
        boolean end = false;
    }

}
