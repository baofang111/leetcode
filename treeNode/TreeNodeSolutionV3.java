package treeNode;

import java.util.*;

/**
 * 二叉树 相关算法练习 V3
 *
 * @summary TreeNodeSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/02 15:59:07
 */
public class TreeNodeSolutionV3 {


    /**
     * 104. 二叉树的最大深度
     * <p>
     * 题目意思：给定一个二叉树，我们 获取它的 最大深度
     * <p>
     * 题目解析：该题有两种解法，
     * 1: 一种是直接递归，计算 最大深度 Max(maxDepth(left), maxDepth(right))
     * 2: 一种是直接使用 栈 的数据结构，模拟 递归的操作，不断的 将 root 和 每一层进行插入，然后遍历 然后 depth + 1
     *
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> stack = new LinkedList<>();
        stack.offer(root);

        int depth = 0;
        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = stack.poll();
                if (node == null) {
                    break;
                }

                if (node.left != null) {
                    stack.offer(node.left);
                }

                if (node.right != null) {
                    stack.offer(node.right);
                }

            }

            depth++;
        }

        return depth;
    }

    /**
     * 递归方法
     *
     * @param root
     * @return
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth2(root.left), maxDepth2(root.right));
    }


    /**
     * 100. 相同的树
     * <p>
     * 题目意思：判断 两个 二叉树 p q 是不是 相同的树
     * <p>
     * 题目解析：直接递归，去判断 p q 的左右节点是不是都一样即可
     *
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 判断是否对称 or 结果值 是否相等
        if (p == null && q == null) {
            // 两棵树均已遍历✅
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
     * <p>
     * 题目意思：将 一个 二叉树 进行反转
     * <p>
     * 题目解析：依旧递归，我们 先将 左右节点进行反转，然后不断的对 二叉树的 左右两个节点 进行递归迭代即可
     *
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * 101. 对称二叉树
     * <p>
     * 题目意思：判断一个 二叉树 是不是对称的 二叉树
     * <p>
     * 题目解析：我们只需要判断 左结点的 左节点 和 右节点的 右节点 && 左节点的右节点 和 右节点的 左结点 是否一样即可
     *
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSame(root.left, root.right);
    }

    /**
     * 判断是否是一样的 节点
     */
    private boolean isSame(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSame(p.left, q.right) && isSame(p.right, q.left);
    }


    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * <p>
     * 题目意思：给定了一个 前序遍历的数组 + 中序遍历的数组，我们 使用这两个数组，构建出一个 完整的 二叉树
     * <p>
     * 题目解析：首先我们需要知道，什么是前序遍历 中序遍历 后序遍历，这里面的 前 中 后，其实代表的是 root 的位置在哪里
     * 这样的话，我们就很明显知道改题目了，其实就是 一个 寻找 root + left + right 的过程
     * 所以在这个过程中，我们需要使用 前序 或者 后序数组作为寻找的基准，然后 将 中序数组 构建一个 < 值，index > 的 map
     * 这样的话，我们在 寻找到 根节点之后，就能从 中序数组里面找到根节点的位置，然后进行下次的 节点构建
     *
     *
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 该题构建的核心就是通过 前序 + 中序 进行 位置的寻找，先寻找 root ，在寻找 left + right
        Map<Integer, Integer> map = new HashMap<>();
        int inLength = inorder.length;
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        return buildPreTree(preorder, 0, 0, inLength - 1, map);
    }

    public TreeNode buildPreTree(int[] preorder, int rootIndex, int left, int right, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        // 递归构建 根节点 + left + right
        int rootValue = preorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);

        // left ---- inRootIndex ----- right  ,左边是左结点，右边是右节点
        Integer inRootIndex = map.get(rootValue);

        // 这个是用来寻找 右节点的 根节点用的
        int leftSize = inRootIndex - left;

        root.left = buildPreTree(preorder, rootIndex + 1, left, inRootIndex - 1, map);
        root.right = buildPreTree(preorder, rootIndex + leftSize + 1, inRootIndex + 1, right, map);

        return root;
    }

    /**
     * 106. 从中序与后序遍历序列构造二叉树
     * <p>
     * 题目意思：该题和 105 题目一样，只不过一个是 前序，一个是 后序
     * <p>
     * 题目解析：和 105 一样，前序是按照 0 开始寻找 根节点，后序的话是 从 length - 1 作为根节点开始
     *
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        int inLength = inorder.length;
        for (int i = 0; i < inLength; i++) {
            map.put(inorder[i], i);
        }

        return buildPostTree(postorder, inLength - 1, 0, inLength - 1, map);
    }

    private TreeNode buildPostTree(int[] postorder, int rootIndex, int left, int right, Map<Integer, Integer> map) {
        if (left > right) {
            return null;
        }

        int rootValue = postorder[rootIndex];
        TreeNode root = new TreeNode(rootValue);

        // 前序和后序的不同点，就是 对于寻找下一个 rootIndex 不一样，  一前一后
        Integer inRootIndex = map.get(rootValue);
        Integer rightSize = right - inRootIndex;

        root.right = buildPostTree(postorder, rootIndex - 1, inRootIndex + 1, right, map);
        root.left = buildPostTree(postorder, rootIndex - rightSize - 1, left, inRootIndex - 1, map);

        return root;
    }

    /**
     * 117. 填充每个节点的下一个右侧节点指针 II
     * <p>
     * 题目意思：给定一个 二叉树，我们将 二叉树的 左右节点 进行 链接，最右边的节点，就指向 null
     * <p>
     * 题目解析：直接使用 二叉树的 程序遍历，然后每次我们需要记录 pre 元素，pre.next = cur 即可
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Deque<Node> stack = new LinkedList<>();
        stack.offer(root);

        while (!stack.isEmpty()) {
            Node pre = null;
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                Node cur = stack.poll();
                if (pre != null) {
                    pre.next = cur;
                }

                // 记住更新我们的 pre
                pre = cur;

                // 从左往右
                if (cur.left != null) {
                    stack.offer(cur.left);
                }
                if (cur.right != null) {
                    stack.offer(cur.right);
                }
            }

        }

        return root;
    }

    /**
     * 114. 二叉树展开为链表
     * <p>
     * 题目意思： 将二叉树 展开成为 链表，其中 先放 左边，再放右边，
     * 注意： 需要原地变更哦
     * <p>
     * 题目解析：根据题目的意思，我们很容易想到，我们先 left left 将所有的数据 先放入，然后右边的有数据的话，先入栈，然后在拿出来
     * 先左 后右
     * <p>
     * 我们使用 递归，不断的 将
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
            pre.left = null;
        }

        // 一定要进行替换
        pre = root;

        // 开始递归 左右节点

        TreeNode left = root.left;
        TreeNode right = root.right;

        flatten(left);
        flatten(right);
    }

    /**
     * 112. 路径总和
     * <p>
     * 题目意思：从 root 二叉树中寻找，看有没有一个路径总和 = targetSum 的路线
     * <p>
     * 题目解析：已经递归 每次值 我们需要 targetSum - root.value 判断最后左右边有没有走完，
     * 题目的意思是 必须从 根节点 到 叶子结点 才算完整路径
     *
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        // 判断到叶子节点的情况
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        return hasPathSum(root.left, targetSum - root.val)
                ||
                hasPathSum(root.right, targetSum - root.val);
    }


    /**
     * 129. 求根节点到叶节点数字之和
     * <p>
     * 题目意思：给定一个 二叉树，计算出每条路线的 值，213 就是 213，然后对值 进行相加
     * <p>
     * 题目解析：该题有个核心，就是我们需要找到 root - 叶子节点的 所有值，并将这些值 进行 total 计算相加
     * total = root.val * 10 + val = total * 10 + root.val
     * 然后再判断什么时候 走到叶子节点，当 root,left && root.right 都 == null 的时候就走到节叶子节点，我们返回这个 total 即可
     *
     *
     */
    public int sumNumbers(TreeNode root) {
        return sumNumber(root, 0);
    }

    public int sumNumber(TreeNode root, int total) {
        if (root == null) {
            return 0;
        }

        total = total * 10 + root.val;

        // 走到了叶子节点
        if (root.left == null && root.right == null) {
            return total;
        }

        // 左右节点的总和
        return sumNumber(root.left, total) + sumNumber(root.right, total);
    }


    /**
     * 124. 二叉树中的最大路径和
     * <p>
     * 题目意思：给定一个二叉树，判断 某个线路 的最大路径和
     * <p>
     * 题目解析：因为判断 最大路径和，所以我们需要多个判断
     * 1：判断之前的结果值 是否 > 0 ,如果 大于 0 ，参与计算，如果小于 0 ，不参与计算
     * 2：进行递归累计求和 max = max(left) + root.val + max(right)
     *
     */
    int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        sumMaxPathSum(root);
        return max;
    }

    public int sumMaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 判断左右节点分叉计算出来的值
        int leftPart = Math.max(0, sumMaxPathSum(root.left));
        int rightPart = Math.max(0, sumMaxPathSum(root.right));

        // 当前节点 root 作为头部节点 计算它的最大值
        max = Math.max(max, root.val + leftPart + rightPart);

        // 这里我们还需要结算，当前节点 作为分叉节点所能得到的最大值，比如 3-（4，5） 这条路线只能选4 或者 5 的最大值
        return root.val + Math.max(leftPart, rightPart);
    }

    /**
     * 222. 完全二叉树的节点个数
     * <p>
     * 题目意思：统计 二叉树的 所有的节点个数
     * <p>
     * 题目解析：因为是 完全二叉树，所以这里有个小优化，就是 就是 如何 左右高度一样，那么它的节点数 就是 2^高度 - 1
     *
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        int total = 0;
        while (!stack.isEmpty()) {
            int size = stack.size();
            total += size;

            for (int i = 0; i < size; i++) {
                TreeNode cur = stack.pop();

                if (cur.left != null) {
                    stack.push(cur.left);
                }
                if (cur.right != null) {
                    stack.push(cur.right);
                }
            }
        }

        return total;
    }

    /**
     * 236. 二叉树的最近公共祖先
     * <p>
     * 题目意思：给定一个 完整二叉树 root,然后给两个 root 下面的节点 p & q，寻找p q 的最近公共祖先
     * <p>
     * 题目解析：依旧使用 递归，当我们 找到 p 或者 q 的话，那么就返回，所以我们就需要递归
     * root 的左结点 + 右节点，去找
     * 如果 都找到了，那么返回 root
     * 如果只有一个地方找到了，那么返回 找打的那一边
     *
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            // 节点不存在 or 已经找到 p 或者 q 节点，那么就返回该节点
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 左右都找到了，那么它肯定是 在 root 上面
        if (left != null && right != null) {
            return root;
        }

        return left == null ? right : left;
    }

    /**
     * 199. 二叉树的右视图
     * <p>
     * 题目意思：给定一个 二叉树 我们 按照 从 右 往左边看，得到一个数组，举个例子 1 -（ 2，3），因为 2 被 3 遮住了，所以得到的结果就是 1，3
     * <p>
     * 题目解析：典型的一道 二叉树 层序遍历的题目，我们只需要 层序遍历的时候，判断是不是最后一个元素，然后把他加入我们的结果集当中即可
     * <p>
     * 注意：这里一定要使用队列 Deque offer poll ，因为队列是 先进先出，这样可以保证 层序遍历✅
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

        while (!stack.isEmpty()) {
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = stack.poll();

                // 一定要先左再右
                if (cur.left != null) {
                    stack.offer(cur.left);
                }
                if (cur.right != null) {
                    stack.offer(cur.right);
                }

                // 最右边的需要加入结果集
                if (i == size - 1) {
                    res.add(cur.val);
                }
            }
        }

        return res;
    }

    /**
     * 637. 二叉树的层平均值
     * <p>
     * 题目意思：给定一个二叉树，我们计算出 二叉树的 每一层的 值的平均值
     * <p>
     * 题目解析：依旧 二叉树的层序遍历，计算一层 结果值 total,然后 （double） total / size 即可
     *
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            long total = 0;
            int size = deque.size();

            for (int i = 0; i < size; i++) {
                TreeNode cur = deque.poll();

                if (cur.left != null) {
                    deque.offer(cur.left);
                }
                if (cur.right != null) {
                    deque.offer(cur.right);
                }

                total += cur.val;
            }

            res.add((double) total / size);
        }

        return res;
    }

    /**
     * 102. 二叉树的层序遍历
     * <p>
     * 题目意思：将一个完整的 二叉树，进行层序遍历
     * <p>
     * 题目解析：使用一个队列，对二叉树每一层进行遍历之后，将结果值放入结果集当中
     *
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            List<Integer> subRes = new ArrayList<>();

            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = deque.poll();

                if (cur.left != null) {
                    deque.offer(cur.left);
                }
                if (cur.right != null) {
                    deque.offer(cur.right);
                }

                subRes.add(cur.val);
            }

            res.add(subRes);
        }

        return res;
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     * <p>
     * 题目意思：依旧是一个二叉树，我们需要 头 左 右 右 左 左 右 这样 Z 字形的 对二叉树进行遍历
     * <p>
     * 题目解析：依旧使用 一个 队列，对每一层的数据进行处理，其中 Z 字性处理，我们使用一个 tag 状态去标识
     * 然后做 头部 或者 尾部 插入的操作
     *
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        // 定义一个队列，存储每层的数据
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        boolean leftToRight = false;

        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> subRes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = deque.poll();

                if (cur.left != null) {
                    deque.offer(cur.left);
                }
                if (cur.right != null) {
                    deque.offer(cur.right);
                }

                if (leftToRight) {
                    subRes.addFirst(cur.val);
                } else {
                    subRes.addLast(cur.val);
                }
            }

            res.add(subRes);

            leftToRight = !leftToRight;
        }

        return res;
    }


    /**
     * 530. 二叉搜索树的最小绝对差
     * <p>
     * 题目意思：给定一个二叉树，判断 该 二叉树中，两个节点中的 最小差值
     * <p>
     * 题目解析：我们使用 中序或者各种 前序后序遍历整个二叉树，然后使用 一个 pre 存储上一个值，然后依次 计算 pre - cur 的最小值即可
     *
     */
    int min = Integer.MAX_VALUE;

    //    TreeNode pre = null;
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }

        getMinimumDifference(root.left);

        if (pre != null) {
            min = Math.min(min, root.val - pre.val);
        }

        pre = root;

        getMinimumDifference(root.right);

        return min;
    }

    /**
     * 230. 二叉搜索树中第 K 小的元素
     * <p>
     * 题目意思：给定一个顺序的 二叉树，我们找到 第K 小的元素
     * <p>
     * 题目解析：因为题目本身的限定，他是一个 顺序的 二叉搜索树，所以我们直接中序遍历，然后 每次查询 step + 1 ,当 step = k 的时候
     * 就是我们要的那个结果值
     *
     */
    int step = 0;
    int value = 0;
    public int kthSmallest(TreeNode root, int k) {
        kthSmallestBFS(root, k);
        return value;
    }

    public void kthSmallestBFS(TreeNode root, int k) {
        if (root == null) {
            return;
        }

        kthSmallestBFS(root.left, k);

        step++;
        if (step == k) {
            value = root.val;
        }

        kthSmallestBFS(root.right, k);
    }


    /**
     * 98. 验证二叉搜索树
     *
     * 题目意思：给定一个二叉树，判断它是不是 二叉搜索树，什么是 二叉搜索树？就是 左边的比根节点小，右边的 比根节点大
     *
     * 题目解析：依旧我们使用 中序遍历，去判断，依旧使用 一个 pre, 必须保证 pre 的值 一定小于 cur,不然就不是 二叉搜索树
     */

    boolean isValidBST = true;
    public boolean isValidBST(TreeNode root) {
        TreeNode pre = null;

        isValidBSTBFS(root);

        return isValidBST;
    }

    public void isValidBSTBFS(TreeNode root) {
        if (root == null) {
            return;
        }

        isValidBSTBFS(root.left);

        if (pre != null && pre.val >= root.val) {
            isValidBST = false;
            return;
        }

        pre = root;

        isValidBSTBFS(root.right);

    }



}
