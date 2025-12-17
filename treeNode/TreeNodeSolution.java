package treeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
        // 记录 中序遍历的 [值，值index] 用来遍历寻找左右节点
        Map<Integer, Integer> map = new HashMap<>();

        int inLength = inorder.length;
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        // 开始构建 二叉树
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
            // 已经遍历完成了
            return null;
        }

        // 创建根节点
        int rootValue = preorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);

        // 我们有根节点之后，我们只需要在 map 中找到该根节点 在 中序遍历里面的 具体位置，然后 他的左右 就是 左右节点
        Integer inRootIndex = map.get(rootValue);

        // 构建根节点的左右节点

        // 左节点的构建条件是, 我们将 inRootIndex 所有左边的，也就是 inRootIndex - 1 和 left 的位置全部归根在左节点上面
        // rootIndex + 1 一定是左节点的第一个 第一个节点
        root.left = buildTreeNode(preorder, rootIndex + 1, left, inRootIndex - 1, map);
        // 右节点的构建条件是：我们将过滤掉所有的左边节点，也就是新的 rootIndex = 当前根节点位置 - 左边的所有的位置长度 + 往前移动一位也就是前序遍历的第一个
        root.right = buildTreeNode(preorder, rootIndex + inRootIndex - left + 1, inRootIndex + 1, right, map);

        return root;
    }
}
