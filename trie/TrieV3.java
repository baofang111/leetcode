package trie;

/**
 * 208. 实现 Trie (前缀树)
 * <p>
 * 题目意思：我们需要设置一个前缀树，它需要包括下面几种方法 初始化 + search + startsWith 的方法
 * <p>
 * 题目解析：该题我们可以使用一种 前缀树的数据结构来实现该题
 * 什么是前缀树
 * abd, abe 就可以通过下面的数据结构来实现
 * a --> b ----> e
 * ----> d
 * <p>
 * 这样有什么好处
 * 1：节约存储空间
 * 2：快速检索
 * 3：天生支持前缀匹配
 * <p>
 * 如何实现该题呢？
 * insert: 插入前缀树 即可
 * search：遍历前缀树，然后判断遍历完成之后 结尾是不是 end (end 代表有这个单词，并用来结尾)
 * startsWith：遍历前缀树，看能不能完整遍历 prefix，有的话，就是存在
 *
 * @summary Trie
 * @author: bf
 * @Copyright (c) 2025/12/30, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/30 11:34
 */
public class TrieV3 {

    TrieNode root;

    /**
     * 初始化前缀树
     */
    public TrieV3() {
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
            int charIndex = word.charAt(i) - 'a';
            if (cur.child[charIndex] == null) {
                cur.child[charIndex] = new TrieNode();
            }
            cur = cur.child[charIndex];
        }
        // 注意需要在结尾处 添加 end 标识，用来区分完整的单词
        cur.end = true;
    }

    /**
     * 搜索前缀树
     */
    public boolean search(String word) {
        int length = word.length();

        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int charIndex = word.charAt(i) - 'a';
            if (cur.child[charIndex] == null) {
                // 单词不存在
                return false;
            }
            cur = cur.child[charIndex];
        }

        return cur.end;
    }

    /**
     * 判断前缀是否在前缀树中存在
     */
    public boolean startsWith(String prefix) {
        int length = prefix.length();

        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int charIndex = prefix.charAt(i) - 'a';
            if (cur.child[charIndex] == null) {
                // 单词不存在
                return false;
            }
            cur = cur.child[charIndex];
        }

        return true;
    }


    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        boolean end;
    }

}
