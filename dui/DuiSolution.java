package dui;

import java.util.PriorityQueue;

/**
 * =========================
 * 算法中的堆（Heap / Priority Queue）
 * =========================
 *
 * 1. 堆是什么？
 *    堆是一种可以「快速获取最大值或最小值」的数据结构，
 *    本质是一棵「完全二叉树」，常用于动态维护最值。
 *
 * 2. 堆的核心性质
 *    - 完全二叉树：除了最后一层，其余层都是满的
 *    - 只保证父子关系，不保证整体有序
 *      * 小顶堆：父节点 <= 子节点（堆顶是最小值）
 *      * 大顶堆：父节点 >= 子节点（堆顶是最大值）
 *
 * 3. 时间复杂度
 *    - 取堆顶（最小/最大）：O(1)
 *    - 插入元素：O(log n)（上浮）
 *    - 删除堆顶：O(log n)（下沉）
 *
 * 4. 数组表示（下标从 0 开始）
 *    - 父节点： (i - 1) / 2
 *    - 左孩子： 2 * i + 1
 *    - 右孩子： 2 * i + 2
 *
 * 5. Java 中的堆
 *    - Java 使用 PriorityQueue 实现堆
 *    - 默认是小顶堆
 *      PriorityQueue<Integer> minHeap = new PriorityQueue<>();
 *    - 大顶堆需要自定义比较器
 *      PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
 *
 * 6. 典型应用场景
 *    - 合并 K 个有序链表（LeetCode 23）
 *    - Top K 问题（第 K 大 / 前 K 个元素）
 *    - 数据流中的中位数（双堆）
 *    - 动态维护最大值或最小值
 *
 * 7. 使用堆的判断标准
 *    - 需要「频繁插入 + 频繁取最值」
 *    - 数据是动态变化的
 *    - 不要求整体有序，只关心最值
 *
 * 总结：
 *    堆 = 动态维护最值的利器，
 *    当问题中出现 “最大 / 最小 / Top K / 实时变化” 时，优先考虑堆。
 */
public class DuiSolution {

    /**
     * 215. 数组中的第K个最大元素
     *
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     *
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     *
     * 输入: [3,2,1,5,6,4], k = 2
     * 输出: 5
     *
     * 解法： 最简单，最直观的方法
     *  Arrays.sort(nums);
     *  return nums[nums.length - k];
     *  优点：简单
     *  缺点：不必要的排了全部
     *
     * 解法2：使用 堆，
     *  维护一个 大小为 k 的小顶堆
     *  堆中始终保存「当前最大的 k 个数」
     *
     *  为什么是 小顶堆？
     * 堆里只放 k 个数
     * 堆顶是这 k 个数中 最小的
     * 一旦来了更大的数，就可以把堆顶踢掉
     *
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

}
