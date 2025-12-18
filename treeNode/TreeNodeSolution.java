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
     *
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
            for (int i = 0; i < size ; i++) {
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
     *
     * 思路： 不断的遍历 一直找到 左节点 = null, 这时候在往上面找 该左节点的根节点，然后找他的右节点
     * 使用 前序遍历 根 - 左 - 右 然后 left 全部设置为空，使用 right 指针
     *
     *  1：使用前序遍历
     *  2：使用一个 pre 指针，这个非常重要
     *  3：每访问一个节点，就把 当前节点放到 pre.right = cur 后面
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

}
