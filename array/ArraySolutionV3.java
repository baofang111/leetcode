package array;

import java.util.Arrays;

/**
 * 数组 刷题 第三版
 *
 * @summary ArraySolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/18, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/18 17:20
 */
public class ArraySolutionV3 {


    /**
     * 88. 合并两个有序数组
     * <p>
     * 题目意思：将 nums2 中的数据，按照 排序的方式 融入到 nums1
     * <p>
     * 题目解析：我们只需要遍历 nums2，然后去和 nums1 进行判断即可
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 我们按照顺序 从大到小 放入到 nums1 上面即可

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
     * 27. 移除元素
     * <p>
     * 题目意思：将 nums 中 值 = val 的数据移除
     * <p>
     * 题目解法：直接用快慢指针即可，当 val 值，我们指针就往前面走
     * <p>
     * 3223 3
     * 22__
     */
    public int removeElement(int[] nums, int val) {
        int n = nums.length;

        int slow = 0;
        int fast = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] != val) {
                nums[slow] = nums[i];
                slow++;
            }
            fast++;
        }

        return slow;
    }

    /**
     * 26. 删除有序数组中的重复项
     * <p>
     * 题目意思：和 27 题类似，只不过这个是 删除 nums 里面的重复元素，注意题目已经是严格递增了，不然是需要排序的
     * <p>
     * 题目解法：依旧双指针，但是因为这个是 nums 自遍历，所以 快指针 开始需要1 ，慢是 0 ，然后的判断方法和 27 题一样
     */
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        int slow = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                // 这里为什么是 先 slow++ 在
                slow++;
                nums[slow] = nums[i];
            }
        }

        return slow + 1;
    }

    /**
     * 80. 删除有序数组中的重复项 II
     * <p>
     * 题目意思：和  26 题类似，只不过这个 nums 里面可以放 2 个相同的元素
     * <p>
     * 题目解法：那么 依旧指针，遍历即可
     */
    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }

        int slow = 2;

        for (int i = 2; i < n; i++) {
            if (nums[i] != nums[slow - 2]) {
                nums[slow] = nums[i];
                slow++;
            }
        }

        return slow;
    }

    /**
     * 169. 多数元素
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 189. 轮转数组
     * <p>
     * 题目解释：给定一个 nums，将数组 轮转 k 个位置
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * <p>
     * 题目解答：我们 只需要转换三次即可
     * 1,2,3,4,5,6,7
     * 7,6,5,4,3,2,1 全部翻转
     * 5,6,7 4,3,2,1 翻转前 K 个位置
     * 5,6,7,1,2,3,4 翻转后 K 个到结束的位置
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        if (length == 0 || k == 0) {
            return;
        }

        // 这里 k 可能超长了，需要 对 length 取模
        k = k % length;

        swap(nums, 0, length - 1);
        swap(nums, 0, k - 1);
        swap(nums, k, length - 1);

    }

    private void swap(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start++;
            end--;
        }
    }

    /**
     * 121. 买卖股票的最佳时机
     *
     * 题目意思：从 prices 中，只买卖一次，获取能获得的最大利润
     *
     * 题目解答：只买卖一次，得到的最大利润，那么很明显，我们找到最小的那个 金额，然后遍历 prices 去找最大利润即可
     *
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int ans = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);

            // 这里再取最大利润
            ans = Math.max(ans, price - minPrice);
        }

        return ans;
    }


    /**
     * 122. 买卖股票的最佳时机 II
     *
     * 题目意思：和 121 的区别就是，这次不限次数，得到可以得到的最大利润
     *
     * 题目解答：我们只需要判断 下一个对比上一个有利润，那么就可以增加到我们的利润当中了
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int length = prices.length;
        if (length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 1; i < length; i++) {
            int value = prices[i] - prices[i - 1];
            if (value > 0) {
                ans += value;
            }
        }

        return ans;
    }

    /**
     * 55. 跳跃游戏
     *
     *  题目意思：给定一个 nums, nums 里面的每个数字代表的就是 我走到这边之后，还能走的最大的距离，
     *  举个例子 2，1，3，4 ,我走第一步，得到 2 ，那么我最多可以走两步，那么我下一步就可以走到 1 或者 3
     *  然后看能不能走出
     *
     *  题目解答：我们只需要每次走，看能不能走到 i 即可
     *
     */
    public boolean canJump(int[] nums) {
        int length = nums.length;

        int max = 0;

        for (int i = 0; i < length; i++) {
            if (max < i) {
                // 这边的话，代表 走不出去了
                return false;
            }

            // 这里我们需要找到 下一次 能走到的最大距离，注意这里一定是 i + nums[i] 因为我们是要走的 路
            max = Math.max(max, i + nums[i]);

        }

        return true;
    }


    /**
     * 45. 跳跃游戏 II
     *
     * 题目意思：依旧跳跃，只不过这次 我们需要得到的是 最小需要跳跃的 步数
     *
     * 题目解析：我们需要记录最小步数
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int max = 0;
        int steep = 0;
        // 这里的 end 是记录 每一次走完 的结束位置
        int end = 0;

        // 注意 这里一定是 length - 1，因为我们不需要走完，走到最后一个位置的时候，已经完成任务了，这点很重要
        for (int i = 0; i < length - 1; i++) {
            max = Math.max(max, i + nums[i]);
            if (i == end) {
                // 这一步已经走完了，需要再走一次
                steep++;
                end = max;
            }
        }

        return steep;
    }



}
