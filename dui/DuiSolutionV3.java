package dui;

import java.util.*;

/**
 * 堆（Heap）相关算法练习
 *
 * <p>堆分类：最大堆、最小堆，基于完全二叉树实现
 * <p>核心解决问题：
 * 1. TopK 极值问题
 * 2. 数据流中位数（双堆拆分）
 * 3. 贪心算法最优选择（IPO、最小和数对）
 * 4. 动态维护最大/最小值
 *
 * @summary 堆算法实战练习，包含大小顶堆、双堆、贪心+堆的经典题型
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/11 11:18:51
 */
public class DuiSolutionV3 {

    /**
     * 215. 数组中的第K个最大元素
     * <p>
     * 题目意思：给定一个 整数数组 nums, 让我们返回 数组中 第 K 个的最大元素
     * 例如：nums = 2 1 3, K = 2 结果等于 123中的 2
     * <p>
     * 题目解析：第几个最大 或者 最小的题目，一看就是 使用 堆来解决问题
     * 该题，我们可以使用一个最小堆，如果 当前值 > 最小值，那么就放到最小堆里面，这样的话，堆顶就是我们需要的结果
     *
     */
    public int findKthLargest(int[] nums, int k) {
        // 注意，java 中使用最大堆和最小堆使用 PriorityQueue 类
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // 解题核心就是 创建了一个最小堆，然后如果当前值 大于最小值，就取出来一个，然后再放进去
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }
        return minHeap.peek();
    }


    /**
     * 502. IPO
     * <p>
     * 题目意思：我们有一批项目，其中 capital 是参与这些项目所需要的初始资本，profits 是参与这些项目所能得到的纯利润，
     * 我们最多只能完成 K 个项目，W 是我们的初始资金，只有初始资金 >= 项目所需资本，才能承接该项目，
     * 每完成一个项目，纯利润会直接叠加到初始资金中，求完成 K 个项目后，能获得的最大总资金
     * <p>
     * 解题思路：贪心算法 + 双堆（最小堆+最大堆）
     * 核心：每次都选择【当前资金能承接】的项目中【利润最高】的，局部最优推导出全局最优
     * <p>
     * 双堆分工：
     * 1. 最小堆：按【项目所需资本】从小到大排序，负责筛选出当前资金能承接的所有项目
     * 2. 最大堆：按【项目利润】从大到小排序，负责存放可承接的项目，保证每次能拿到利润最大的项目
     * <p>
     * 核心处理流程：
     * 1. 先将所有项目（资本+利润）存入最小堆，按启动资本自动排序
     * 2. 循环 K 次，每次做一个最优项目：
     * ① 解锁项目：遍历最小堆，将所有【资金足够承接】的项目，全部移入最大堆
     * ② 无项目可做：如果最大堆为空，直接退出循环
     * ③ 选择最优：取出最大堆堆顶（利润最大的项目），将利润叠加到当前资金
     * 3. 最终返回累加后的总资金
     * <p>
     * 关键注意：profits 是纯利润，承接项目只看资金门槛，不扣除成本，利润直接累加
     *
     * @param k       最大可以完成 K 个项目
     * @param w       初始资金（只要初始资金 > 该 IPO 成本），那么我就可以参与这个项目
     * @param profits 这个是每个项目的 纯利润 纯利润，一定别搞错了
     * @param capital 参与这个项目所以需要的 初始资本
     * @return 完成 K个项目之后，所能得到的最大利润
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 使用两个堆来存储我们的数据，最大堆（存储利润） + 最小堆（存储成本，用来判断是否可以做）
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        // 初始化最小堆，将我们所需要的成本放进去
        int length = capital.length;
        for (int i = 0; i < length; i++) {
            minHeap.offer(new int[]{capital[i], profits[i]});
        }

        // 遍历 K 次，判断能不能做 + 赚到多少利润
        for (int i = 0; i < k; i++) {
            // 先判断最小堆里面的成本能不能做
            while (!minHeap.isEmpty() && w >= minHeap.peek()[0]) {
                // 只要能做，就放入到 最大堆当中
                maxHeap.offer(minHeap.poll());
            }

            // 没有可做的项目，直接返回
            if (maxHeap.isEmpty()) {
                break;
            }

            // 将最大堆的头部，也就是当前能获取到的最大利润，放入到 我们的成本当中，用来进行下一次项目能不能做的判断
            w += maxHeap.poll()[1];
        }

        return w;
    }

    /**
     * 373. 查找和最小的 K 对数字
     * <p>
     * 题目意思：给定两个**升序排列**的数组 nums1 和 nums2，从两个数组中各取一个数字组成数对 (nums1[i], nums2[j])
     * 要求找出 总和最小 的前 k 个数对
     * <p>
     * 题目解析：求最小/最大的 K 个结果，是堆的经典应用场景
     * 本题需要找**和最小**的 K 对，因此使用【最小堆】，堆顶永远是当前和最小的数对
     * 核心利用条件：两个数组都是**升序**的，这是我们简化算法的关键
     * <p>
     * 解题思路（贪心 + 最小堆）：
     * 1. 最小堆存储下标 [i,j]，排序规则：按照两数之和 nums1[i]+nums2[j] 升序排列
     * 2. 初始化堆：因为数组升序，nums2[0] 是 nums2 中最小的元素，所以先将所有 nums1[i] 与 nums2[0] 组合入堆
     * 这些是所有候选数对中最小的一批，(0,0) 一定是全局最小值
     * 3. 循环取 K 次结果：
     * ① 弹出堆顶（当前和最小的数对），加入结果集
     * ② 保持 i 不变，将 j+1 位置的数对入堆（数组升序，下一个最小候选一定是同 i，j 后移一位）
     * 4. 天然无重复，无需去重，效率最高
     *
     * @param nums1 升序排列的数组1
     * @param nums2 升序排列的数组2
     * @param k     需要获取的最小数对数量
     * @return 和最小的 k 个数对
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return res;
        }

        // 定义 nums1[i] + nums2[j] 和的最小堆,所以最小堆里面我们存的是 nums1-i nums2-j
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> (nums1[a[0]] + nums2[a[1]])));

        // 初始化最小堆
        int length = nums1.length;
        for (int i = 0; i < length; i++) {
            minHeap.offer(new int[]{i, 0});
        }

        // 开始拿我们的结果值
        while (!minHeap.isEmpty() && k-- > 0) {
            int[] index = minHeap.poll();
            int i = index[0];
            int j = index[1];
            res.add(Arrays.asList(nums1[i], nums2[j]));

            // 再次寻找 j
            if (j + 1 < nums2.length) {
                minHeap.offer(new int[]{i, j + 1});
            }
        }

        return res;
    }

}
