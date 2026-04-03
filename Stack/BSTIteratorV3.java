package Stack;

import treeNode.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 173. 二叉搜索树迭代器
 *
 * 题目意思：初始化了 一个 二叉树，让我们实现两个方法，一个是 next, 一个是 hasNext，
 * 注意，该二叉树 从 左 往右 是 依次变大的
 *
 * next: int next()将指针向右移动，然后返回指针处的数字。
 * hasNext: 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
 *
 * 题目解析：因为 题目提供的  TreeNode 是 从左往右 数值越来越大的
 * 所以我们就可以将 TreeNode 的左结点 （ 比较小的一批节点 ）先入栈
 * 然后 查询的时候 遍历栈，next 查询的时候，如果这时候 还有 right,就将 right 再次入栈
 *
 *
 * @summary BSTIteratorV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/02 20:54:00
 */
public class BSTIteratorV3 {

    Deque<TreeNode> stack;

    /**
     * 因为 root 的特殊结构，我们先讲 left 进行入栈
     */
    public BSTIteratorV3(TreeNode root) {
        stack = new ArrayDeque<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public int next() {
        // 因为之前入栈过，所以我们出栈，判断有没有值 即可，有 右子节点的话，再次入栈即可
        TreeNode node = stack.pop();
        // 右节点要全部入栈
        TreeNode cur = node.right;
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }

        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

}

