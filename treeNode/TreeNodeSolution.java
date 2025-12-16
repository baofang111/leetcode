package treeNode;

import java.util.LinkedList;
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
     *  二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
     *
     *  解法: 使用 递归，递归该二叉树 左右两边的最大 深度即可
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
     *  给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     *
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
     *
     * 解法：左右节点互换，然后开始递归
     *
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
     *
     * 解法：
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {

    }

}
