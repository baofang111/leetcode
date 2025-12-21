package treeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 173. 二叉搜索树迭代器
 * 实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器
 * <p>
 * 解法：把中序遍历拆开，一个一个返回，但是这题不是中序遍历完 在进行迭代，
 * 而是惰性 中序遍历，使用一个栈，去模拟中序遍历的过程
 * <p>
 * 我们适用一个栈，在初始化的是，将该 二叉树的所有 左节点 进行压栈，当 next 的时候，直接拿去该节点，然后如果有右节点，就将他继续压栈即可
 * 这样就简单实现了一个 中序遍历
 *
 * Deque 常用方法语义对照表（记住这张就够了）
 * 方法	操作位置	语义	典型用途
 * push(e)	头部	压栈	栈入栈
 * pop()	头部	出栈	栈出栈
 * peek()	头部	查看栈顶	栈
 *
 * offer(e)	尾部	入队	队列
 * poll()	头部	出队	队列
 *
 * peekFirst()	头部	查看队首	双端
 * peekLast()	尾部	查看队尾	双端
 * addFirst(e)	头部	强制加入	双端
 * addLast(e)	尾部	强制加入	双端
 * removeFirst()	头部	移除	双端
 * removeLast()	尾部	移除	双端
 *
 * 👉 一句话记忆：
 *
 * push/pop/peek = 栈
 * offer/poll = 队列
 *
 */
class BSTIterator {

    Deque<TreeNode> stack = new ArrayDeque<>();


    /**
     * 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。
     * 指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
     *
     * @param root
     */
    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    /**
     * 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
     * <p>
     * 从 栈中取出数据，然后如果该节点有 有右节点的话，那么就将右节点入栈，那么他就实现了 惰性中序遍历
     */
    public int next() {
        TreeNode root = stack.pop();
        if (root.right != null) {
            // 将整棵右子树 全部入栈
            pushLeft(root.right);
        }
        return root.val;
    }

    /**
     * 将指针向右移动，然后返回指针处的数字。
     */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * 所有左节点入栈
     */
    private void pushLeft(TreeNode root) {
        while (root != null) {
            stack.offer(root);
            root = root.left;
        }
    }
}