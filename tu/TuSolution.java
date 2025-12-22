package tu;

import java.util.*;

/**
 * 图 相关算法集合
 *
 * @summary TuSolution
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/22 20:16:32
 */
public class TuSolution {

    /**
     * 200. 岛屿数量
     * <p>
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     * <p>
     * 解法：这题就是 遍历整个网络，每遇到一个 还没访问到的 1 的时候，就通过 DFS/BFS 把这个岛标记到，然后 岛屿数量 + 1
     * 这样 解法就 很明显了
     * <p>
     * 如果适用 BFS 的话，就是当 我们找到岛屿之后，直接将该岛屿 变成 0 ，然后遍历走到底，也就是 = 1 的时候
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        // 该题的核心就是，我们遍历整个岛，当遇到 grid = '1' 那么就等于遇到了岛屿，然后我们岛屿数量 + 1 即可
        // 但是，当我们遇到岛屿的时候，我们想需要在该点，上下左右 不断的遍历，直到走到该岛屿的尽头，这样下次遍历
        // 就不会重复计算

        int count = 0;

        int n = grid.length;
        int m = grid[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    // 遇到了岛屿
                    count++;
                    // 以该店为中心，走完所有的岛屿附近的点
                    landDFS(grid, i, j);
                }
            }
        }

        return count;
    }


    private void landDFS(char[][] grid, int i, int j) {
        int n = grid.length;
        int m = grid[0].length;

        // 超出临界点
        if (i < 0 || i >= n || j < 0 || j >= m) {
            return;
        }

        // 已经到了岛屿的边缘
        if (grid[i][j] == '0') {
            return;
        }

        // 一定要重置当前点，表示该点已经走完了，不然会造成遍历死循环
        grid[i][j] = '0';

        // 开始扩散寻找
        landDFS(grid, i - 1, j);
        landDFS(grid, i + 1, j);
        landDFS(grid, i, j - 1);
        landDFS(grid, i, j + 1);
    }


    /**
     * 130. 被围绕的区域
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' 组成，捕获 所有 被围绕的区域：
     * <p>
     * 连接：一个单元格与水平或垂直方向上相邻的单元格连接。
     * 区域：连接所有 'O' 的单元格来形成一个区域。
     * 围绕：如果您可以用 'X' 单元格 连接这个区域，并且区域中没有任何单元格位于 board 边缘，则该区域被 'X' 单元格围绕。
     * 通过 原地 将输入矩阵中的所有 'O' 替换为 'X' 来 捕获被围绕的区域。你不需要返回任何值。
     * <p>
     * 通俗理解，就是 如果 O 被 X 完全围绕，那么就将 O 也重置为 X ，如果没有被完全围绕，比如 边缘地带等等，那么就不需要重置
     * 注意，需要原地更改
     * <p>
     * 解题思路：这题真正的“本质建模”（一句话）
     * - 不是找“被包围的 O”，
     * - 而是找“没被包围的 O”
     * 也就是： 所有和边界连通的 O，都是安全的
     * <p>
     * 三步走（核心）
     * <p>
     * 1️⃣ 从边界出发
     * 2️⃣ 把所有与边界连通的 O 标记为“安全”
     * 3️⃣ 剩下的 O，一定是被包围的 → 变 X
     * <p>
     * 为什么一定是能从边界逃走的才是安全的？ 能逃出去的只能是边界，中间的 O 也必须有链接到边界的 O 才能逃出去
     * <p>
     * 所以解法就呼之欲出了，我们从边界开始扫码，第一行 + 最后一行，第一列 + 最后一列
     * 然后使用 X 标记成 安全的，其他 O 都是不安全的，到时候直接替换成 X 就行了
     *
     * @param board
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        // n 行 m 列
        int n = board.length;
        int m = board[0].length;

        // 从 第一行 + 最后一行开始遍历
        for (int i = 0; i < n; i++) {
            solveDFS(board, i, 0);
            solveDFS(board, i, m - 1);
        }

        // 从 第一列 + 最后一列
        for (int i = 0; i < m; i++) {
            solveDFS(board, 0, i);
            solveDFS(board, n - 1, i);
        }

        // 开始重新进行替换
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                    continue;
                }
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

    }

    /**
     * 类似于岛屿那一题，就是不断的 上下左右寻找，判断有没有 出口
     */
    public void solveDFS(char[][] board, int i, int j) {
        // 判断临界条件先
        int n = board.length;
        int m = board[0].length;
        if (i < 0 || j < 0 || i >= n || j >= m) {
            return;
        }

        // 如果不是 'o' 就返回
        if (board[i][j] != 'O') {
            return;
        }

        // 走到这里已经是 可以逃出去的了，设置成 # 作为标记
        board[i][j] = '#';

        // 开始左右走
        solveDFS(board, i - 1, j);
        solveDFS(board, i + 1, j);
        solveDFS(board, i, j - 1);
        solveDFS(board, i, j + 1);
    }

    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    /**
     * 133. 克隆图
     * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
     * <p>
     * 题目解析，其实他就是一个 Node 节点的 深拷贝
     * <p>
     * 解法：注意 该题因为 neighbors 里面有环，所以不能使用递归，会导致 死循环，且一个节点可能会有对个访问路径，且不能重复克隆点
     * 所以我们只能 使用 一个 map 保存 原节点和 新节点的关系，然后再去遍历
     * <p>
     * 或者也可以适用 BFS ，使用一个 Map 存储新老节点 ，然后使用一个队列 进行 保存
     *
     * @param node
     * @return
     */
    Map<Node, Node> map = new HashMap<>();
    public Node cloneGraph(Node node) {
        // 这里适用  DFS 去解答，注意，一定要创建一个Map 去保存 新旧 node 的关系，不然在环形 node 中 会死循环
        if (node == null) {
            return null;
        }

        // 如果 map 已经遍历过该节点的华，直接返回，不然会造成死循环
        if (map.containsKey(node)) {
            return map.get(node);
        }

        // 我们开始创建新的跟节点 + 叶子节点
        Node newNode = new Node(node.val, new ArrayList<>());
        map.put(node, newNode);

        for (Node subNode : node.neighbors) {
            newNode.neighbors.add(cloneGraphBFS(subNode));
        }

        return newNode;
    }


    /**
     * 133. 克隆图
     * 同样的题目，我们适用 BFS 再次解答一下
     *
     * @param node
     * @return
     */
    public Node cloneGraphBFS(Node node) {
        if(node == null){
            return null;
        }
        // 适用 BFS 进行遍历，我们同样需要一个新老节点的 map
        Map<Node, Node> map = new HashMap<>();

        // 创建一个队列，执行 BFS 的基础
        Deque<Node> deque = new ArrayDeque<>();

        // 创建根节点
        deque.offer(node);
        map.put(node, new Node(node.val, new ArrayList<>()));

        // 开始遍历
        while (!deque.isEmpty()) {
            Node cur = deque.pop();
            for (Node subNode : cur.neighbors) {
                // 遍历下面的子节点
                if (!map.containsKey(subNode)) {
                    // 一定要加这个判断，不然会死循环
                    map.put(subNode, new Node(subNode.val, new ArrayList<>()));
                    deque.offer(subNode);
                }
                // 填充数据
                map.get(cur).neighbors.add(map.get(subNode));
            }
        }

        return map.get(node);
    }

}
