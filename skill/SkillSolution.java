package skill;

import java.util.Arrays;

/**
 * 技巧
 *
 * @summary SkillSolution
 * @author: bf
 * @Copyright (c) 2024/7/12, © 拜耳作物科学（中国）有限公司
 * @since: 2024/7/12 10:16
 */
public class SkillSolution {

    /**
     * 136: 只出现一次的数字
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
     *
     *  暴力解：放 map or list集合 里面，但是 限制是 该算法只能使用 常量的额外空间
     *     所以我们使用 位运算
     *
     * 使用 异或
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }


    /**
     * 169: 多数元素
     *  给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     *  你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     *  尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题
     *
     *  方法：
     *      1： map 统计法
     *      2： 数组排序法，中间位置的 一定就是
     *      3： 摩尔投票法 核心理念为 票数正负抵消
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    /**
     * 75: 颜色分类
     *    给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * <p>
     * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
     *
     * 解法：要不排序，要不使用 指针 进行位置替换
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        return;
    }

}
