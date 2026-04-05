package tu;

import java.util.*;

/**
 * 图相关 算法题
 *
 * @summary TuSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/03 17:38:25
 */
public class TuSolutionV3 {

    /**
     * 200. 岛屿数量
     * <p>
     * 题目意思：给定一个 grid ，其中 '1' 代表的就是陆地，'0' 代表的是水域，只要 连续的 '1' 那么就代表连续的一块陆地，询问 grid 这块岛屿
     * 中，总共有几块陆地
     * <p>
     * 题目解析：我们可以 从 0，0 开始遍历，当遇到一块陆地的时候，就不断的 往下面遍历，找到连续的 1 ，如果找到的话，就给他设置 成 '0'
     * 这样不影响 下次其他陆地的寻找，这样，遍历✅，我们就能找到总共有几块陆地了
     *
     */
    public int numIslands(char[][] grid) {
        if (grid == null) {
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;

        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    numIslandsBFS(i, j, grid);
                    ans++;
                }
            }
        }

        return ans;
    }

    /**
     * 从 i j 开始，寻找该位置下的 所有导的位置
     */
    public void numIslandsBFS(int i, int j, char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        if (i < 0 || j < 0 || i >= n || j >= m) {
            return;
        }

        if (grid[i][j] == '0') {
            // 已经不是陆地了
            return;
        }

        // 走到这里 需要将陆地变成水，防止影响后续的计算逻辑
        grid[i][j] = '0';

        // 开始扩散寻找
        numIslandsBFS(i - 1, j, grid);
        numIslandsBFS(i, j - 1, grid);
        numIslandsBFS(i + 1, j, grid);
        numIslandsBFS(i, j + 1, grid);
    }

    /**
     * 130. 被围绕的区域
     * <p>
     * 题目意思：给定一个 区域 board，由 X O 组成，其中 'X' 是用来包围 'O' 的，其中如果 O被 X 完全包围，那么就代表 这块的 O 出不去了
     * 这时候也需要将它设置成 X ，如果 O 能出去，那么它就不用被设置成 X
     * <p>
     * 题目解析：该题有个逆向思维，我们需要 从边缘开始遍历，第一行 + 最后一行，第一列 + 最后一列，如果边缘行有 O，那么代表他一定是可以出去的
     * 不需要被设置成 X，，那么我们就从这四个 边缘行去遍历，如果边缘行走不到的地方，那么肯定需要是被包围的，直接设置成 X 就行了
     * <p>
     * 所以我们就需要两个步骤
     * 步骤1：边缘行 O 可达的区域，设置成 # ,用于标识
     * 步骤2：重新遍历 board，如果是 # 就设置回 O,其他全部设置成 X
     *
     */
    public void solve(char[][] board) {
        if (board == null) {
            return;
        }

        int n = board.length;
        int m = board[0].length;

        // 第一行 + 最后一行 遍历
        for (int i = 0; i < n; i++) {
            solveBFS(i, 0, board);
            solveBFS(i, m - 1, board);
        }

        for (int j = 0; j < m; j++) {
            solveBFS(0, j, board);
            solveBFS(n - 1, j, board);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void solveBFS(int i, int j, char[][] board) {
        int n = board.length;
        int m = board[0].length;

        if (i < 0 || j < 0 || i >= n || j >= m) {
            return;
        }

        if (board[i][j] != 'O') {
            return;
        }

        // 设置标识
        board[i][j] = '#';

        solveBFS(i, j - 1, board);
        solveBFS(i, j + 1, board);
        solveBFS(i - 1, j, board);
        solveBFS(i + 1, j, board);
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
     * <p>
     * 题目意思：给定一个 node, 注意 这个 node 里面可能有环形结构  neighbors，我们将它深拷贝成另外的一个 Node
     * <p>
     * 题目解析：因为 node,neighbors 里面有环形，所以我们的解法就是 和 深拷贝 链表一样，使用 一个 map,然后 通过创建节点
     * 不断的 递归遍历即可，可以使用 BFS or DFS 去解答
     * <p>
     * 解法1：我们使用 Map<老节点，新节点>，注意，因为有环形结构，所以我们在 map 中再次遇到的时候，一定要直接返回，不然回造成
     * 解法2：我们可以使用 DFS 队列 进行构建
     *
     * @param node
     * @return
     */
    Map<Node, Node> map = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node newNode = new Node(node.val);
        map.put(node, newNode);

        for (Node subNode : node.neighbors) {
            newNode.neighbors.add(cloneGraph(subNode));
        }

        return newNode;
    }


    /**
     * 399. 除法求值
     * <p>
     * 题目意思：给定一个 equations ，里面大概是 A/B B/C C/D , 然后再给定一个 values，它代表的是 equations 中每个位置 计算的结果集
     * 然后让我们 通过 equations + values 计算它的 queries 结果集，比如 通过 A/B B/C 的值，得到 A/C or C/A 的值
     * <p>
     * 题目解析：该题的核心是我们需要创建一个 结果集的图，比如 我们通过 A/B=2 的值，得到两个结果集
     * 1： A/B = 2
     * 2: B/A = 1/2
     * 这样的话，我们就可以根据这个正反结果集，得到我们想要的答案，比如说
     * A/C = A/B * B/C
     * C/A = C/B * B/A
     * 这样的话，该题的核心就变成了，初始化结果集图 + 队列DFS 进行 不断的入队，去寻找到我们需要的结果集值
     *
     *
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Map<A, Map<B, 2>> --- Map<B, Map<A, 1/2>>
        Map<String, Map<String, Double>> map = new HashMap<>();
        int length = values.length;

        // 构建结果图 map
        for (int i = 0; i < length; i++) {
            double value = values[i];

            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            map.putIfAbsent(a, new HashMap<>());
            map.putIfAbsent(b, new HashMap<>());

            map.get(a).put(b, value);
            map.get(b).put(a, 1 / value);
        }

        // 通过构建的结果集图，去寻找需要计算的结果值 a/c -- a/b * b/c
        int size = queries.size();
        double[] res = new double[size];

        for (int i = 0; i < size; i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            res[i] = calcEquationDFS(start, end, map);
        }

        return res;
    }

    private double calcEquationDFS(String start, String end, Map<String, Map<String, Double>> map) {
        // 两个计算的结果值都不在 图内，肯定算不出来
        if (!map.containsKey(start) || !map.containsKey(end)) {
            return -1;
        }

        // 构建队列，一定要先进先出，进行计算
        Deque<String> deque = new ArrayDeque<>();
        deque.offer(start);

        // 同时需要定义一个 计算结果值的 队列，用来统计计算结果,并进行初始化
        Deque<Double> valueDeque = new ArrayDeque<>();
        valueDeque.offer(1.0);

        // 定一个一个已经计算过的结果集，防止出现 A/B B/A 的无限死循环
        Set<String> existed = new HashSet<>();

        while (!deque.isEmpty()) {
            String cur = deque.poll();
            Double value = valueDeque.poll();

            // 计算完成了
            if (cur.equals(end)) {
                return value;
            }

            // 我们遍历整个图，寻找我们要的结果集，a/c -- 从图中 a/b b/a c/a 等，找到 a 开头的计算结果及
            Set<String> keys = map.get(cur).keySet();
            for (String next : keys) {
                if (!existed.contains(next)) {
                    existed.add(next);
                    // 作为下一个计算结果， a/c - a/b（b/c）这时候找到了这个b
                    deque.offer(next);
                    // 存储上一个结果值 a/c - a/b（b/c），valueDeque 此时 = 1 * (a*b)
                    valueDeque.offer(value * map.get(cur).get(next));
                }
            }
        }

        //没找到
        return -1;
    }

    /**
     * 994. 腐烂的橘子
     * <p>
     * 题目意思：给定一个 网格 grid,其中 值 0 = 空单元格，1 = 新鲜的橘子，2 = 腐烂的橘子，其中，每分钟 腐烂的橘子就会将周围的橘子进行腐蚀
     * 也就是 1=2，如果 新鲜橘子旁边没有任何腐烂的橘子的话，他就不会收到影响，那么它就不会被腐烂，问我们 能不能将所有的橘子都腐烂掉，如果行的话
     * 返回 腐烂的时间，如果不行的话 返回 -1
     * <p>
     * 题目解析：一道典型的图扩散的题目，我们需要先统计出来所有 腐烂橘子的位置，注意，这里需要将他入队，这样的话，入队出队，可以进行后续的腐烂扩散，
     * 然后 我们对于 腐烂的每个位置，进行 -1 - 1 的周围扩散，直到所有的都扩散完毕，然后我们判断 是不是所有的新鲜橘子都被腐烂了，
     * 是 返回时间，不是返回 -1
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        // 核心就是队列记录 腐烂的橘子 + 记录所有的新鲜橘子，已取保判断是否还有新鲜的橘子，来得到我们的结果集
        if (grid == null) {
            return 0;
        }

        int n = grid.length;
        int m = grid[0].length;

        // 腐烂橘子队列
        Deque<int[]> deque = new ArrayDeque<>();

        // 新鲜橘子总数
        int freshCount = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    // 腐烂橘子入队
                    deque.offer(new int[]{i, j});
                    continue;
                }
                if (grid[i][j] == 1) {
                    // 新鲜橘子计数
                    freshCount++;
                }
            }
        }

        // 完全没有新鲜的橘子
        if (freshCount == 0) {
            return 0;
        }

        // 耗时
        int minute = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // 遍历所有腐烂的橘子，开始进行腐败处理
        while (!deque.isEmpty() && freshCount > 0) {
            minute++;

            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();

                // 对当前位置 进行 上下左右的腐烂处理
                for (int[] dir : dirs) {
                    int x = cur[0] + dir[0];
                    int y = cur[1] + dir[1];

                    // 临界条件判断
                    if (x >= 0 && y >= 0 && x < n && y < m && grid[x][y] == 1) {
                        freshCount--;
                        grid[x][y] = 2;
                        // 腐败的橘子进入队列，准备下一次腐败处理
                        deque.offer(new int[]{x, y});
                    }
                }
            }
        }


        return freshCount > 0 ? -1 : minute;
    }


    /**
     * 207. 课程表
     * <p>
     * 题目意思：给定了一个 课程表 prerequisites，其中的数据表示，【1，0】学完课程 1 之前必须要先学课程 0 ，
     * 如果是 【1，0】【0，1】那么就表示 学完 1 之前必须先学 0，学 0 之前，必须先学 1，那么他就构成了一个死循环，那么肯定不能学完
     * 其中 numCourses 是必须学完的课程
     * <p>
     * 题目解析：根据题目意思，我们可以将它简单做一个理解，极速 对于 【1，0】 的话，我们需要创建 图，
     * 图：【1，0】 ---> 0 -> 1 ，代表如果 学 1 ，必须先学 0
     * 还需要对 1 进行 学这门课的时候，需要提前学多少门课，进行一个统计，这里我们称之为 入度 count[1]++
     * <p>
     * 然后我们就可以遍历 我们的所有的课程，找到 入度 = 0 的数据，进行入队 （入度 = 0 就代表我们不需要前置条件，也可以学的课程）
     * 然后遍历 队列，学完了 就是 入度--，如果还有 入度 = 0 的，那么就又可以学了，依次循环，直到得到我们想要的结果集，就是该条件下，能学多少课程
     *
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null) {
            return true;
        }

        // 构建 课程学习流程图
        List<List<Integer>> list = new ArrayList<>();

        // 初始化所有的学习流程图
        for (int i = 0; i < numCourses; i++) {
            list.add(new ArrayList<>());
        }

        int[] count = new int[numCourses];

        for (int[] p : prerequisites) {
            int a = p[0];
            int b = p[1];
            list.get(b).add(a);

            // 对学习某个课程，需要前置学习多少课程 进行统计
            count[a]++;
        }

        Deque<Integer> deque = new ArrayDeque<>();

        // 寻找可以不需要前置学习条件的 课程，开始进行学习
        for (int i = 0; i < numCourses; i++) {
            if (count[i] == 0) {
                deque.offer(i);
            }
        }

        int finishCount = 0;

        // 开始进行学习
        while (!deque.isEmpty()) {
            // 可以进行学习
            finishCount++;

            // b-a b-c 我们 对 B 学完之后，需要对 a 和 c 的入度 进行 - 1，用来计算下一个学习状态
            Integer cur = deque.poll();

            List<Integer> nexts = list.get(cur);
            if (!nexts.isEmpty()) {
                for (Integer next : nexts) {
                    count[next]--;
                    if (count[next] == 0) {
                        deque.offer(next);
                    }
                }
            }
        }

        return finishCount == numCourses;
    }


}
