package treeNode;

import com.sun.tools.javac.Main;

import javax.sound.midi.MidiFileFormat;
import java.util.*;

/**
 * 双指针相关 算法
 *
 * @summary TreeNodeSolution
 * @author: bf
 * @Copyright (c) 2025/12/16, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/16 11:30
 */
public class TreeNodeSolution {

    /**
     * 104. 二叉树的最大深度
     * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 解法: 使用 递归，递归该二叉树 左右两边的最大 深度即可
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 1 代表当前遍历的深度
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 我们也可以使用 栈 去进行统计
     *
     * @param root
     * @return
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 将 root 添加到队列中，然后遍历即可
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int dept = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            dept++;
        }

        return dept;
    }

    /**
     * 100. 相同的树
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     * <p>
     * 解法：很简单，还是地柜，左右判断 是否全部一样
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 226. 翻转二叉树
     * 左右翻转二叉树
     * <p>
     * 解法：左右节点互换，然后开始递归
     * <p>
     * 注意：所有的递归下，我们都需要注意 退出条件，防止死循环
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 左右节点互换
        TreeNode right = root.right;
        root.right = root.left;
        root.left = right;

        // 递归
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * 101 - 对称二叉树
     * <p>
     * 解法：我们分别取 左边和右边判断是否相等即可，和 相同二叉树 一样
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSame(root.left, root.right);
    }

    private boolean isSame(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }

        return isSame(left.right, right.left) && isSame(left.left, right.right);
    }

    /**
     * 105 从前序与中序遍历序列构造二叉树
     * 前序遍历：根 - 左 - 右
     * 中序遍历：左 - 根 - 右
     * 后续遍历：左 - 右 - 根
     * 前中后 这种遍历，其实意思 根节点什么时候 进行遍历
     * <p>
     * 解题思路：我们根据 前序 中序 可以很方便找到
     * 规律1：前序遍历的 第一个节点 一定是 根节点
     * 规律2：在中序遍历中 根节点在中间，左右两边分别是 左节点 和 右节点
     * <p>
     * 按照这个思路 直接便利即可，我们将 中序的 inorder 数组里面的数据 + index 放入一个 map 当中，
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        int inLength = inorder.length;
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        return buildTreeNode(preorder, 0, 0, inLength - 1, map);

    }

    /**
     * @param preorder  前序遍历集合
     * @param rootIndex 根节点位置
     * @param left      inorder 的左节点位置
     * @param right     inorder 的右节点的位置
     * @param map       inorder 值, index 的 map
     * @return
     */
    private TreeNode buildTreeNode(int[] preorder, int rootIndex, int left, int right, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        int rootValue = preorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);

        // 我们需要获取到 rootValue 在 inorder 中序遍历的具体位置，这样可以更好的切粉左右叶子结点
        Integer inRootIndex = map.get(rootValue);

        // 开始构建左右节点
        root.left = buildTreeNode(preorder, rootIndex + 1, left, inRootIndex - 1, map);
        root.right = buildTreeNode(preorder, rootIndex + inRootIndex - left + 1, inRootIndex + 1, right, map);

        return root;
    }

    /**
     * 106. 从中序与后序遍历序列构造二叉树
     * 中序：左 - 根 - 右
     * 后序：左 - 右 - 根
     * <p>
     * 解法 和 105 题目 基本一样 我们使用不同的便利方式即可
     * 首要目标，就是 找到这个根，前序遍历，根在第一个，后序遍历在 最后一个，所以我们倒序操作就行了
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        // 和 105 题目差不多，关键点同样是找根节点，只不过根节点，一个在最前面，一个在最后面
        Map<Integer, Integer> map = new HashMap<>();
        int inLength = inorder.length;
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        return buildTreeNode2(postorder, inLength - 1, 0, inLength - 1, map);
    }

    private TreeNode buildTreeNode2(int[] postorder, int rootIndex, int left, int right, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        // 构建根节点
        int rootValue = postorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);
        Integer inRootIndex = map.get(rootValue);

        // 构建左右节点，注意因为 后序，根节点的前面一个节点，一定是右节点，所以我们要先构建右节点，然后再构建左节点，顺序不能错
        root.right = buildTreeNode2(postorder, rootIndex - 1, inRootIndex + 1, right, map);
        root.left = buildTreeNode2(postorder, rootIndex - 1 - (right - inRootIndex), left, inRootIndex - 1, map);

        return root;
    }

    /**
     * 117. 填充每个节点的下一个右侧节点指针 II
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL 。
     * <p>
     * 思路，使用 队列，一层一层的 获取，然后重新构建即可
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        // 使用队列 一层一层遍历，然后添加到他的 next 就行
        if (root == null) {
            return null;
        }

        // 使用队列存储
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        // 开始遍历
        while (!queue.isEmpty()) {
            int size = queue.size();
            // 这里一定需要保存上一个节点，并且先做后右的去遍历我们的树，这样，我们就可以直接使用 pre.next = cur 即可
            Node pre = null;
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                if (pre != null) {
                    pre.next = cur;
                }

                // 一定要进行替换
                pre = cur;
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }

        return root;
    }

    /**
     * 附带 下一个在指向的
     */
    public class Node {
        int val;
        Node left;
        Node right;
        Node next;
    }

    /**
     * 114. 二叉树展开为链表
     * <p>
     * 思路： 不断的遍历 一直找到 左节点 = null, 这时候在往上面找 该左节点的根节点，然后找他的右节点
     * 使用 前序遍历 根 - 左 - 右 然后 left 全部设置为空，使用 right 指针
     * <p>
     * 1：使用前序遍历
     * 2：使用一个 pre 指针，这个非常重要
     * 3：每访问一个节点，就把 当前节点放到 pre.right = cur 后面
     *
     * @param root
     */
    TreeNode pre = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        if (pre != null) {
            pre.right = root;
            // 左节点一定要设置 为 空
            pre.left = null;
        }

        // 前序遍历 这里千万 要将 root.left 和 root.right 拿出来，不然 递归的时候，会有问题
        pre = root;
        TreeNode left = root.left;
        TreeNode right = root.right;

        flatten(left);
        flatten(right);
    }

    /**
     * 112. 路径总和
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，
     * 这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
     * <p>
     * 解法：一定是不断的 递归，然后遍历该 二叉树，然后去判断是否是我们需要的值
     * 我们遍历 左右节点，每遍历一遍，我们就 通过 targetSum 减去 然后判断， 能不能减完
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        // 遍历完该分支所有节点，达到罪尾部叶子节点，然后再进行判断
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        // 开始遍历左右节点，进行递归判断
        return hasPathSum(root.left, targetSum - root.val)
                ||
                hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 129. 求根节点到叶节点数字之和
     * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
     * 每条从根节点到叶节点的路径都代表一个数字：
     * <p>
     * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
     * 计算从根节点到叶节点生成的 所有数字之和 。
     * <p>
     * 解法：依旧是递归遍历，但是这个的递归遍历 每次的数据 需要 进行拼接，而不是 直接的相加
     * 核心: 从根节点开始 root--> cur.val ，那么每向下一层的话，他的值，就是 curValue = curValue * 10 + root.value
     * 然后和 112 这种解法差不多即可
     * <p>
     * 注意 每一条路径 都是一个独立的数字
     *
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        // 左右两边节点进行递归
        return sumNumbers(root, 0);
    }

    public int sumNumbers(TreeNode root, int total) {
        if (root == null) {
            return 0;
        }

        // 这就是该题的核心不断的 1 2 3 -> 12 123
        total = total * 10 + root.val;

        // 遍历到最后一个节点，返回该路径创建的所有值的总和
        if (root.left == null && root.right == null) {
            return total;
        }

        // 左右两边节点进行递归
        return sumNumbers(root.left, total) + sumNumbers(root.right, total);
    }

    /**
     * 124. 二叉树中的最大路径和
     * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     * 路径和 是路径中各节点值的总和。
     * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
     * <p>
     * 解法：其实都差不多，不断的递归，找到每一条的 值，然后 进行 Math.max 进行判断
     * 但是该题的一个重要的点，就是 不需要遍历完整的一条路线，如果遍历完整的一条路径，就是 直接递归判断即可，129 的翻版
     * 所以这题的核心就是 找这条路径上 相加 最大的值，然后去判断
     *
     * @param root
     * @return
     */
    private int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathSumDfs(root);
        return max;
    }

    // 核心就是判断 当前值 + Math.max(left, right), 且如果遍历的下面 和 0 进行比较，< 0 就不进行相加
    public int maxPathSumDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 判断左右两边剩下的节点总和的大小
        int leftPart = Math.max(0, maxPathSumDfs(root.left));
        int rightPart = Math.max(0, maxPathSumDfs(root.right));

        // 当前位置的 总和，去更新 max 的最大路径和，这事该题的核心，就是可以是任意路径节点之间的相加
        int value = root.val + leftPart + rightPart;
        max = Math.max(max, value);

        // 选出左右两边 最大的值 去递归得到我们要的结果
        return root.val + Math.max(leftPart, rightPart);
    }


    /**
     * 222. 完全二叉树的节点个数
     * <p>
     * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
     * 并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层（从第 0 层开始），则该层包含 1~ 2h 个节点。
     * <p>
     * 解法：和二叉树深度一样，一个是深度，一个是 个数
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int total = 0;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);

        while (!stack.isEmpty()) {
            int size = stack.size();
            total += size;
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pop();
                if (node.left != null) {
                    stack.offer(node.left);
                }
                if (node.right != null) {
                    stack.offer(node.right);
                }
            }
        }
        return total;
    }


    /**
     * 更快的一种方式
     *
     * @param root
     * @return
     */
    public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = getLeftHeight(root.left);
        int rightHeight = getRightHeight(root.right);

        // 满 二叉树，直接 2 ^ 高度 - 1 就是我们要的高度
        if (leftHeight == rightHeight) {
            return (1 << leftHeight) - 1;
        }

        return 1 + leftHeight + rightHeight;
    }

    private int getLeftHeight(TreeNode root) {
        int count = 0;
        while (root != null) {
            count++;
            root = root.left;
        }
        return count;
    }

    private int getRightHeight(TreeNode root) {
        int count = 0;
        while (root != null) {
            count++;
            root = root.right;
        }
        return count;
    }


    /**
     * 236. 二叉树的最近公共祖先
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。、
     * <p>
     * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出：3
     * 解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
     * <p>
     * 思路：我们分别判断 p q 在 root 的左边 或者 右边 ，然后递归即可
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        //  分别从 左右两边找
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // p q 分别在左右节点中，那么就是 root
        if (left != null && right != null) {
            return root;
        }

        // 否则肯定是 左右两边找到的哪个
        return left != null ? left : right;
    }

    /**
     * 199. 二叉树的右视图
     * <p>
     * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * <p>
     * 思路：该题是 二叉树层序遍历的 另外一种思考形式
     * 就是 层序遍历的话，我们通过 栈 或者队列，对 树的每一层 进行 入栈，出栈，这样每次遍历的 都是 有一层一层的遍历
     * 然后对于该题，我们 只需要在层序遍历中，将 最后一个 节点 放入我们的 结果集当中即可
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);

        // 开始层序遍历
        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pop();
                // 一定要先左后右的进行添加，这样我们才能准确的找到 最右边的那个节点
                if (node.left != null) {
                    stack.offer(node.left);
                }
                if (node.right != null) {
                    stack.offer(node.right);
                }

                // 这里是 核心，当我们遍历到最后一个节点的时候，就将该节点放入结果集，那么我们看到的就是 我们需要的结果
                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }

        return res;
    }

    /**
     * 637. 二叉树的层平均值
     * <p>
     * 给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10-5 以内的答案可以被接受。
     * <p>
     * 解法：同样层序遍历之后 value = totalValue / size
     *
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);

        while (!stack.isEmpty()) {
            int size = stack.size();
            // 注意 int 临界值 相加之后会变成 负数，所以这里一定要适用 long 进行存储
            long total = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pop();

                total += node.val;

                if (node.left != null) {
                    stack.offer(node.left);
                }
                if (node.right != null) {
                    stack.offer(node.right);
                }
            }

            res.add((double) total / size);
        }

        return res;
    }

    /**
     * 使用  BFS 进行 解答
     *
     * @param root
     * @return
     */
    List<Long> nums = new ArrayList<>();
    List<Integer> sizes = new ArrayList<>();

    public List<Double> averageOfLevels2(TreeNode root) {
        averageOfLevelsBfs(root, 0);

        List<Double> res = new ArrayList<>();

        int size = nums.size();
        for (int i = 0; i < size; i++) {
            res.add((double) nums.get(i) / sizes.get(i));
        }

        return res;
    }

    /**
     * 使用 BFS 遍历 该二叉树，并且计算 nums + count
     *
     * @param root
     */
    private void averageOfLevelsDfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }

        // 一定要进行初始化
        if (depth == sizes.size()) {
            nums.add(0L);
            sizes.add(0);
        }

        nums.set(depth, nums.get(depth) + root.val);
        sizes.set(depth, sizes.get(depth) + 1);

        averageOfLevelsDfs(root.left, depth + 1);
        averageOfLevelsDfs(root.right, depth + 1);
    }

    /**
     * 102. 二叉树的层序遍历
     * <p>
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     * <p>
     * 解法：同样层序遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);

        while (!stack.isEmpty()) {
            int size = stack.size();
            // 先左后右
            List<Integer> subRes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pop();
                if (node.left != null) {
                    stack.offer(node.left);
                }
                if (node.right != null) {
                    stack.offer(node.right);
                }
                subRes.add(node.val);
            }
            res.add(subRes);
        }

        return res;
    }

    /**
     * 使用 DFS
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelOrderDfs(root, 0, res);
        return res;
    }

    public void levelOrderDfs(TreeNode root, int depth, List<List<Integer>> res) {
        if (root == null) {
            return;
        }

        // 初始化每一层
        if (depth == res.size()) {
            res.add(new ArrayList<>());
        }

        res.get(depth).add(root.val);

        // 开始左右 DFS 遍历

        levelOrderDfs(root.left, depth + 1, res);
        levelOrderDfs(root.right, depth + 1, res);
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     * <p>
     * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * <p>
     * 解法：层级遍历的一种，先 左右 后 右左，我们只需要 适用一个 变量来存储 左右 右左状态，然后根据不通的状态，插入尾部 或者插入头部即可
     * <p>
     * 左 -> 右: 正常插入尾部
     * 右 -> 左：插入头部
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offer(root);

        int leftToRight = 1;

        while (!stack.isEmpty()) {
            int size = stack.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.pop();
                if (leftToRight == 1) {
                    level.addLast(node.val);
                } else {
                    level.addFirst(node.val);
                }

                if (node.left != null) {
                    stack.offer(node.left);
                }
                if (node.right != null) {
                    stack.offer(node.right);
                }
            }

            leftToRight = -leftToRight;
            res.add(level);
        }

        return res;
    }


    /**
     * 530. 二叉搜索树的最小绝对差
     * <p>
     * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
     * <p>
     * 解法：使用 BFS 遍历，然后存储上一个节点 然后计算当前节点和 上一个节点的差值 即可，注意是 当前节点 - 上一个节点
     * 和 114 二叉树展开为链表 有点像
     *
     * @param root
     * @return
     */
    int min = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        getMinimumDifferenceBFS(root);
        return min;
    }

    public void getMinimumDifferenceBFS(TreeNode root) {
        if (root == null) {
            return;
        }

        // 一定要使用 中序遍历，左 中 右，这样他才是有顺序的
        getMinimumDifferenceBFS(root.left);

        if (pre != null) {
            min = Math.min(min, root.val - pre.val);
        }
        pre = root;

        getMinimumDifferenceBFS(root.right);
    }

    /**
     * 230. 二叉搜索树中第 K 小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 小的元素（k 从 1 开始计数）。
     * <p>
     * 解法：和 530 类似，我们只需要使用 BFS 然后中序遍历即可，这样他的遍历顺序就是 从小到大，然后取出遍历到的 第 K 的值即可
     *
     * @param root
     * @param k
     * @return
     */
    int value;
    int step = 0;
    public int kthSmallest(TreeNode root, int k) {
        kthSmallestBFS(root, k);
        return value;
    }

    public void kthSmallestBFS(TreeNode root, int k) {
        if (root == null) {
            return;
        }

        // 开始中序便利
        kthSmallestBFS(root.left, k);

        step++;
        // 这就是我们要找的数据
        if (step == k) {
            value = root.val;
        }

        kthSmallestBFS(root.right, k);
    }

    /**
     * 98. 验证二叉搜索树
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 有效 二叉搜索树定义如下：
     * <p>
     * 节点的左子树只包含 严格小于 当前节点的数。
     * 节点的右子树只包含 严格大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 解法：同样使用 BFS 去 校验 ,保存上一个节点 然后使用 上一个节点 和 当前节点 进行判断 是否有序即可
     *
     * @param root
     * @return
     */
    boolean isValidBST = true;
    public boolean isValidBST(TreeNode root) {
        isValidBSTBFS(root);
        return isValidBST;
    }

    public void isValidBSTBFS(TreeNode root) {
        if (root == null) {
            return;
        }

        // 继续 中序遍历
        isValidBSTBFS(root.left);

        if (pre != null && pre.val >= root.val) {
            isValidBST = false;
            return;
        }
        pre = root;

        isValidBSTBFS(root.right);
    }

}
