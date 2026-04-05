package trie;

/**
 * 211. 添加与搜索单词 - 数据结构设计
 *
 * 题目意思：该题其实 和 208 前缀树差不多，只不过多了一个条件，就是前缀树 必须完全匹配，单词完整匹配
 * 但是该题有个特殊情况，就是 加入 该题遇到 . ，那么就代表他是个通配符，可以匹配所有的节点
 *
 * 题目解析：根据题目意思，我们很容易就能想到一种解法，就是 其他和 前缀树一样，但是遇到 。 的时候，我们需要遍历
 * child 下面所有的节点，这样，就是一个天然的 dfs,当 child 下面的任意一个节点满足我们的 search 条件的话，那么他也满足条件
 *
 * @summary WordDictionaryV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/05 19:45:29
 */
public class WordDictionaryV3 {

    TrieNode root;

    public WordDictionaryV3() {
        root = new TrieNode();
    }

    /**
     * 添加单词
     * @param word 单词
     */
    public void addWord(String word) {
        int length = word.length();

        TrieNode cur = root;
        for (int i = 0; i < length; i++) {
            int charIndex = word.charAt(i) - 'a';
            if (cur.child[charIndex] == null) {
                cur.child[charIndex] = new TrieNode();
            }
            cur = cur.child[charIndex];
        }

        cur.end = true;
    }

    /**
     * 有 . 的存在，天然 DFS or BFS 的模式去寻找
     * @param word
     * @return
     */
    public boolean search(String word) {
        return dfs(0, word, root);
    }

    public boolean dfs(int index, String word, TrieNode cur) {
        if (cur == null) {
            // 遍历完前缀树，都没找到
            return false;
        }

        // 已经找到结尾了
        if (index == word.length()) {
            return cur.end;
        }

        char c = word.charAt(index);

        if (c != '.') {
            // 正常的单词路径，继续往前缀树的后续节点寻找即可
            return dfs(index + 1, word, cur.child[c - 'a']);
        }

        // 这里是 . 的情况，我们需要对 child 节点最全部的遍历
        TrieNode[] child = cur.child;
        for (TrieNode node : child) {
            // 只要 child 有任何一条路径满足，那么他就是能满足情况了
            if (dfs(index + 1, word, node)) {
                return true;
            }
        }

        // 找完了，都没有找到
        return false;
    }


    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        boolean end;
    }

}
