package dui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * =========================
 * 算法中的堆（Heap / Priority Queue）
 * =========================
 * <p>
 * 1. 堆是什么？
 * 堆是一种可以「快速获取最大值或最小值」的数据结构，
 * 本质是一棵「完全二叉树」，常用于动态维护最值。
 * <p>
 * 2. 堆的核心性质
 * - 完全二叉树：除了最后一层，其余层都是满的
 * - 只保证父子关系，不保证整体有序
 * * 小顶堆：父节点 <= 子节点（堆顶是最小值）
 * * 大顶堆：父节点 >= 子节点（堆顶是最大值）
 * <p>
 * 3. 时间复杂度
 * - 取堆顶（最小/最大）：O(1)
 * - 插入元素：O(log n)（上浮）
 * - 删除堆顶：O(log n)（下沉）
 * <p>
 * 4. 数组表示（下标从 0 开始）
 * - 父节点： (i - 1) / 2
 * - 左孩子： 2 * i + 1
 * - 右孩子： 2 * i + 2
 * <p>
 * 5. Java 中的堆
 * - Java 使用 PriorityQueue 实现堆
 * - 默认是小顶堆
 * PriorityQueue<Integer> minHeap = new PriorityQueue<>();
 * - 大顶堆需要自定义比较器
 * PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
 * <p>
 * 6. 典型应用场景
 * - 合并 K 个有序链表（LeetCode 23）
 * - Top K 问题（第 K 大 / 前 K 个元素）
 * - 数据流中的中位数（双堆）
 * - 动态维护最大值或最小值
 * <p>
 * 7. 使用堆的判断标准
 * - 需要「频繁插入 + 频繁取最值」
 * - 数据是动态变化的
 * - 不要求整体有序，只关心最值
 * <p>
 * 总结：
 * 堆 = 动态维护最值的利器，
 * 当问题中出现 “最大 / 最小 / Top K / 实时变化” 时，优先考虑堆。
 */
public class DuiSolution {

    /**
     * 215. 数组中的第K个最大元素
     * <p>
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * <p>
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * 输入: [3,2,1,5,6,4], k = 2
     * 输出: 5
     * <p>
     * 解法： 最简单，最直观的方法
     * Arrays.sort(nums);
     * return nums[nums.length - k];
     * 优点：简单
     * 缺点：不必要的排了全部
     * <p>
     * 解法2：使用 堆，
     * 维护一个 大小为 k 的小顶堆
     * 堆中始终保存「当前最大的 k 个数」
     * <p>
     * 为什么是 小顶堆？
     * 堆里只放 k 个数
     * 堆顶是这 k 个数中 最小的
     * 一旦来了更大的数，就可以把堆顶踢掉
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        // 创建一个 K 个元素的 最小堆，使用这个插入数据之后，堆顶就是我们要找的 第K 个最大元素
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (Integer num : nums) {
            if (minHeap.size() < k) {
                // 直接添加
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                // 当前值 大于最小堆里面的某个值，添加进去, 先删 再 加
                minHeap.poll();
                minHeap.offer(num);
            }
        }
        return minHeap.peek();
    }


    /**
     * 502. IPO
     * <p>
     * 思路：
     * 1：你只能做“当前资金 w ≥ capital[i]”的项目
     * 2：每做完一个项目：
     * -- 你会立刻拿到 profits[i]
     * -- 资金 w += profits[i]
     * 3：每个项目 只能做一次
     * 4：最多只能做 k 个项目
     * 5：目标是：最终资金最大化
     * <p>
     * 使用两个堆
     * 1：小顶堆 按资金解锁项目，大顶堆按照贪心做选择
     * 2：每一步都选当前能做的最赚钱的项目，最大化最终资金
     *
     * @param k       可以 最多做 k 个项目 来赚钱
     * @param w       启动资金
     * @param profits 做完这个项目 可以赚到的钱
     * @param capital 做这个项目 需要的启动资金
     * @return
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 我们使用最小堆来保存 启动资金，最大堆，来保存可以拿到的利润，PriorityQueue 里面的 int[0] 存的是 capital int[1] 存的是 profits

        // 最小堆 + 最大堆
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // 比较数组 第一个元素
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        // 先插入最小堆，因为我们查询的时候 需要使用
        for (int i = 0; i < capital.length; i++) {
            minHeap.offer(new int[]{capital[i], profits[i]});
        }

        // 开始遍历 K 个数，用来找 K 次之后 能拿到的最大利润
        for (int i = 0; i < k; i++) {
            // 如果这个项目我们可以做的话，那么我们就将他放入最大推中，注意这里的 whild + 下面的 w+= + foreach 一定是联合使用的
            // 为什么用 while  因为只要我们能做的项目，都要放进去
            while (!minHeap.isEmpty() && minHeap.peek()[0] <= w) {
                // minHeap.peek()[0] <= w 表示这个项目 我们可以做，这时候将 这个项目的利润放入最大堆里面，
                // 这样 2 3 的利润都放进去，下面取 最大堆的 poll 拿到的就是最大的利润
                maxHeap.offer(minHeap.poll());
            }

            // 一个项目都不能做 直接返回
            if (maxHeap.isEmpty()) {
                break;
            }

            // 选择当前利润最大的项目
            w += maxHeap.poll()[1];
        }

        return w;
    }

    /**
     * 373. 查找和最小的 K 对数字
     * <p>
     * 输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     * 输出: [1,2],[1,4],[1,6]
     * 解释: 返回序列中的前 3 对数：
     * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
     * <p>
     * 解法思路：这也是一道典型的 最小堆的问题，我们将 nums[i] + nums2[j] 的和 放入最小堆里面
     * 而且这题还有一个隐藏的逻辑情况，就是 对于 nums1 + nums2  他都是升序的
     * 所以这样 往上加就行了
     * (nums1[0], nums2[0])
     * (nums1[1], nums2[0])
     * (nums1[2], nums2[0])
     * ...
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // 使用最小堆 从 i j 开始 不断的往前添加 就能得到我们 所要的值
        List<List<Integer>> result = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return result;
        }

        // 创建最小推
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(a -> (nums1[a[0]] + nums2[a[1]]))
        );

        // 初始化我们的最小推 根据 i,0 i+1,0 i+2,0 去初始化
        for (int i = 0; i < nums1.length; i++) {
            minHeap.offer(new int[]{i, 0});
        }

        // 获取最小的 K 的组合
        while (k-- > 0 && !minHeap.isEmpty()) {
            int[] cur = minHeap.poll();
            int i = cur[0];
            int j = cur[1];
            result.add(List.of(nums1[i], nums2[j]));

            // 开始遍历 右边区间，将值放入最小堆
            if (j + 1 < nums2.length) {
                minHeap.offer(new int[]{i, j + 1});
            }
        }

        return result;
    }

}
