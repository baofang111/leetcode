package hot.hot100;

/**
 * 208. 实现 Trie (前缀树)
 *
 * 题目意思：实现前缀树
 *
 * 题目解析：我们构建 一个前缀树，然后不断遍历
 */
public class Trie {

    TrieNode trie;

    /**
     * 初始化前缀树
     */
    public Trie() {
        trie = new TrieNode();
    }

     /**
     * 插入前缀树
     * 
     */
    public void insert(String word) {
        int length = word.length();
        TrieNode cur = trie;
        for (int i = 0; i < length; i++) {
            int index = word.charAt(i) - 'a';
            if (cur.child[index] == null) {
                // 初始化
                cur.child[index] = new TrieNode();
            }
            cur = cur.child[index];
        }
        cur.end = true;
    }

    /**
     * 搜索前缀树
     */
    public boolean search(String word) {
        int length = word.length();
        TrieNode cur = trie;
        for (int i = 0; i < length; i++) {
            int index = word.charAt(i) - 'a';
            if (cur.child[index] == null) {
                return false;
            }
            cur = cur.child[index];
        }
        return cur.end;
    }

    /**
     * 判断前缀是否在前缀树中存在, 只要存在就行，不需要判断是否是完整的单词
     */
    public boolean startsWith(String prefix) {
        int length = prefix.length();
        TrieNode cur = trie;
        for (int i = 0; i < length; i++) {
            int index = prefix.charAt(i) - 'a';
            if (cur.child[index] == null) {
                return false;
            }
            cur = cur.child[index];
        }
        return true;
    }

    public static class TrieNode{
        TrieNode[] child = new TrieNode[26];
        boolean end;
    }


}