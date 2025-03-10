package array;

/**
 * 数组相关 的算法题
 *
 * @summary ArraySolution
 * @author: bf
 * @Copyright (c) 2025/3/10, © 拜耳作物科学（中国）有限公司
 * @since: 2025/3/10 09:54
 */
public class ArraySolution {


    /**
     * 26 - 删除有序数组中的重复项
     * 思路：
     * 因为是已经排序过的，并且已经排好序了，所以 我们遍历的话，遇到重复的 拿出，在放 好位置
     * <p>
     * 所以这里使用 快慢指针 慢指针走，快指针往前走，如果 快慢指针的数据不相同的话，那么将 快指针的数据给慢指针
     * 那么我就可以保证
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        while (fast < nums.length) {
            // 当快慢指针往前面走的时候，当 他俩数据不一样的时候，那么就将 快指针的数据 赋值给 慢指针
            if (nums[slow] != nums[fast]) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }

        return slow + 1;
    }

    /**
     * 27 -  移除元素 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     * <p>
     * 这题 和 26 其实是一样的，一样的快慢指针，只不过 这道题 比的不是本数组的其他元素，而是和 val 进行比对
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        return slow;
    }

    /**
     * 283 - 移动 0
     * [0,1,0,3,12] --> [1,3,12,0,0]
     * <p>
     * 题解，和 26 27 的题目意思其实还是都一样
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) {
            return;
        }

        int slow = 0;
        int fast = 0;

        while (fast < nums.length) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        // 后面需要补 0
        while (slow < nums.length) {
            nums[slow++] = 0;
        }
    }

    /**
     * 167 两数之和Ⅱ - 输入有序数组
     * 示例 1：
     * <p>
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
     * 示例 2：
     * <p>
     * 输入：numbers = [2,3,4], target = 6
     * 输出：[1,3]
     * 解释：2 与 4 之和等于目标数 6 。因此 index1 = 1, index2 = 3 。返回 [1, 3] 。
     * <p>
     * 解答： 这种题目，也是一眼双指针，先固定一个指针，另外一个指针往前走，如果找到了就停下
     * 然后 开始的指针继续往前走 继续执行他的流程
     * <p>
     * 或者使用 双指针，前面一个指针，后面一个指针，前面的往前面走，后面的往后面走
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sumNum = numbers[left] + numbers[right];
            if (sumNum == target) {
                // 注意 这里是 物理位置，并不是索引位置
                return new int[]{left + 1, right + 1};
            }
            if (sumNum < target) {
                left++;
            }
            if (sumNum > target) {
                right--;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * 344 - 反转数组
     *
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     *
     * 解答：方法很简单，直接使用 两个指针，替换他们元素的位置即可
     *
     * @param s
     */
    public void reverseString(char[] s) {
        if (s.length == 0) {
            return;
        }

        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

}
