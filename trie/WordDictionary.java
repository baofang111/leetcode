package trie;

/**
 * 211. 添加与搜索单词 - 数据结构设计
 *
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
 *
 * 这题是 208 Trie 的“进阶版 + 通配符”，核心难点在 . 的处理方式。
 */
class WordDictionary {

    private TrieNode root;


    /**
     * 初始化词典对象
     */
    public WordDictionary() {
        root = new TrieNode();
    }

    /**
     * 将 word 添加到数据结构中，之后可以对它进行匹配
     *
     * 依旧是构建 前缀树
     * @param word
     */
    public void addWord(String word) {
        TrieNode node = root;
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            // 开始组装下一个 单词,一定要在当前 idx 的后面进行组装
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    /**
     * 判断数据结构中是否存在与 word 匹配的字符串。
     *
     * 规则说明：
     * - 若存在匹配字符串，返回 true；否则返回 false。
     * - word 中可能包含字符 '.'，其中：
     *   '.' 可以匹配任意一个字母（这是本题的关键点）。
     *
     * 为什么 LeetCode 208（Trie）的 search 不够用？
     *
     * 在 208 中，search 的匹配方式是：
     * - 对于 word 中的每一个字符
     * - 都必须精确匹配当前节点的某一个子节点（child[idx]）
     *
     * 但在 211 中，匹配规则发生了变化，例如：
     * - "b.." 表示：
     *   'b' + 任意字符 + 任意字符
     *
     * 当匹配过程中遇到 '.' 时：
     * - 不再是沿着一条确定的路径向下走
     * - 而是当前节点的【所有子节点】都可能成为合法路径
     *
     * 因此可以得出结论：
     * - LeetCode 211 的 search 本质上是：
     *   在 Trie 结构上进行 DFS / 回溯搜索
     *
     * ---- 这里 使用 dfs
     *
     */
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    /**
     * dfs 开始遍历
     * @param word 单词
     * @param idx 目前走到了哪里，下标指针
     * @param node 前缀树
     * @return
     */
    private boolean dfs(String word, int idx, TrieNode node) {
        if (node == null) {
            return false;
        }

        // 走完word 的所有遍历, 返回结果
        if (idx == word.length()) {
            return node.isEnd;
        }

        char c = word.charAt(idx);

        // 判断如果 是 字符 or . 的 不同的情况

        if (c != '.') {
            // 只 需要遍历前缀树的后续节点
            int index = c - 'a';
            return dfs(word, idx + 1, node.children[index]);
        }

        // 如果是 . 的情况，就需要便利 当前节点后续的 所有节点，进行 foreach
        for (TrieNode child : node.children) {
            if (child != null) {
                if (dfs(word, idx + 1, child)) {
                    // 通配符 全部返回 true
                    return true;
                }
            }
        }

        return false;
    }

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

}