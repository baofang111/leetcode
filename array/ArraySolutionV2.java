package array;

import com.sun.tools.javac.Main;

import java.util.Arrays;

/**
 * 刷题 第二版
 *
 * @summary ArraySolutionV2
 * @author: bf
 * @Copyright (c) 2025/11/11, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/11 10:05
 */
public class ArraySolutionV2 {

    public static void main(String[] args) {
        ArraySolutionV2 solutionV2 = new ArraySolutionV2();

        int[] ints = {7, 1, 5, 3, 6, 4};
        solutionV2.maxProfit(ints);
    }

    /**
     * 合并两个有序数组
     * <p>
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素
     * <p>
     * 解法： 使用 后插入法
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 将数据放入到 nums1 当中

        int i = nums1.length;
        while (n > 0) {
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[i - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[i - 1] = nums2[n - 1];
                n--;
            }
            i--;
        }
    }

    /**
     * 27 移除元素
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     * <p>
     * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
     * 输出：5, nums = [0,1,4,0,3,_,_,_]
     * 解释：你的函数应该返回 k = 5，并且 nums 中的前五个元素为 0,0,1,3,4。
     * <p>
     * 解法： 使用快慢指针 快指针往前找 不等于 val 的值，慢指针用来存储 快指针 不等于的哪个值
     */
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        while (fast < length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            // 当 fast 的值 = val 的时候，快指针往前面继续走，寻找下一个节点
            fast++;
        }

        return slow;
    }


    /**
     * 26 - 删除有序数组中的重复项
     * <p>
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 输出：5, nums = [0,1,2,3,4,_,_,_,_,_]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 思路：
     * 同上一题，同样使用指针来完成改题操作。因为已经排好序了，所以可以安心的使用 快慢指针，当数据一致的时候，移动快指针即可，
     * 然后将 快指针数据，放到慢指针上面即可
     * <p>
     * 这题和 27 题 基本一样
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        int slow = 0;
        // 快指针需要向前一步
        int fast = 1;

        while (fast < length) {
            if (nums[slow] != nums[fast]) {
                // 需要吧 慢指针的前一个位置 放成 快指针的值。所以这里需要 先 slow++
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }

        // 输出位置 所以要 指针+1
        return slow + 1;
    }

    /**
     * 80 - 删除有序数组中的重复项 Ⅱ
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 输入：nums = [0,0,1,1,1,1,2,3,3]
     * 输出：7, nums = [0,0,1,1,2,3,3]
     * 解释：函数应返回新长度 length = 7, 并且原数组的前七个元素被修改为 0, 0, 1, 1, 2, 3, 3。不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 对比 26 题，第一眼想到的也是 使用 快慢指针去操作，但是这里操作有一个不同的就是 需要存储 有且进
     *
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        // 和 26 题 最大的区别就是 这里面 每一步是 2 步，所以我们快慢指针 直接 按照 2步 2步走，其他和 26 基本一样
        if (nums == null) {
            return 0;
        }

        int length = nums.length;
        if (length < 2) {
            return length;
        }

        int slow = 2;
        int fast = 2;
        while (fast < length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        return slow;
    }

    /**
     * 169 - 多数元素
     * <p>
     * 这题 直接排序之后，取中间位置的值就行了
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 189 - 轮转数组
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * <p>
     * 题解，不断翻转即可
     * 先翻转全部 在翻转前面，再翻转后面
     * step1: [7,6,5,4,3,2,1]
     * step2: [5,6,7,4,3,2,1]
     * step3: [5,6,7,1,2,3,4]
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return;
        }

        int length = nums.length;

        // 因为可能 k 的大于 length 的，所以需要对 K 进行取模
        k %= length;

        swap(nums, 0, length - 1);
        // 这边因为翻转之后，所以位置就在 k-1 的地方了
        swap(nums, 0, k - 1);
        swap(nums, k, length - 1);

    }

    private void swap(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i++] = nums[j];
            nums[j--] = temp;
        }
    }

    /**
     * 121 - 买卖股票的最佳时机
     * <p>
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票
     *
     * 解法：按照顺序 获取最大
     */
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }

        // 分别取最大和最小 去计算即可
        int profit = 0;
        int cost = Integer.MAX_VALUE;

        for (int price : prices) {
            cost = Math.min(cost, price);
            profit = Math.max(profit, price - cost);
        }

        return profit;
    }

    /**
     * 双指针版本
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null) {
            return 0;
        }

        int maxProfit = 0;
        // 低价格位置
        int minPriceIndex = 0;
        int index = 1;

        while (index < prices.length) {
            if (prices[minPriceIndex] < prices[index]) {
                int profit = prices[index] - prices[minPriceIndex];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                // 注意 这里一定要 替换 最小价格的位置
                minPriceIndex = index;
            }
            index++;
        }

        return maxProfit;
    }

    /**
     * 122 - 买卖股票的最佳时机 2
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。然而，你可以在 同一天 多次买卖该股票，但要确保你持有的股票不超过一股。
     *
     *  可以同样使用 maxProfit2 的双指针方法
     *  也可以使用贪心算法，每次上涨的时候 给他卖了，那么他一定就是总收益最大的时候
     *
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        if (prices == null) {
            return 0;
        }

        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }

        return maxProfit;
    }

    /**
     * 55 - 跳跃游戏
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标
     *
     * 其实就是判断能不能跳跃到 最后一个表
     *
     * 解法：
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums == null) {
            return false;
        }
        // 直接遍历所有点，如果当前点比前面所有点可以达到的点的最大距离还要大就返回false，如果能遍历到最后一个点，说明最后一个点可以到达，返回true
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                // 这代表我们跳跃已经跳出去了
                return false;
            }
            // 这一步代表 i 位置到 i 能走的最大距离，注意 tim
            max = Math.max(max, i + nums[i]);
        }

        return true;
    }


}
