package erfen;

/**
 * 二分查找相关
 * <p>
 * 二分查找是一种基于有序数组的高效查找算法，
 * 通过不断比较中间元素并缩小搜索区间来定位目标值，
 * 每一步都会排除一半不可能的区间，时间复杂度为 O(log n)。
 * <p>
 * 伪代码：
 * left = 0, right = n - 1
 * while left <= right:
 * mid = left + (right - left) / 2
 * if nums[mid] == target:
 * return mid
 * else if nums[mid] < target:
 * left = mid + 1
 * else:
 * right = mid - 1
 * return -1
 *
 * @summary ErfenSolution
 * @author: bf
 * @Copyright (c) 2024/5/16, © 拜耳作物科学（中国）有限公司
 * @since: 2024/5/16 09:59
 */
public class ErfenSolution {

    /**
     * 35 -  索引插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * 示例 1:
     * <p>
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     * 示例 2:
     * <p>
     * 输入: nums = [1,3,5,6], target = 2
     * 输出: 1
     * <p>
     * 解法思路：直接二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        // 典型的二分查找
        int left = 0;
        int right = nums.length - 1;

        // 开始二分查找
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 找到了
                return mid;
            } else if (nums[mid] < target) {
                // 中间位置比 目标更小，代表在右边
                left = mid + 1;
            } else {
                // 反之
                right = mid - 1;
            }
        }

        // 为什么这里 是 返回 left 呢，举个例子，1234 target = 7 那么全部大于 1234 ，结果就是我们要的 left = 4 + 1 = 5 的位置
        return left;
    }

    /**
     * 74. 搜索二维矩阵
     *
     * 每行中的整数从左到右按非严格递增顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     *
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
     *
     * 解法: 同样典型的 二分查找，只不过我们二分的是 整个 二维数组
     *
     * 所以 我们 left = 0, right = n * m - 1
     *
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int left = 0;
        int right = n * m - 1;

        // 开始二分查找
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // 通过这个，我们找到 具体的 row + col, 自己画个图就知道为什么这样取值了
            int row = mid / m;
            int col = mid % m;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * 62. 寻找峰值
     * 峰值元素是指其值严格大于左右相邻值的元素。
     *
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     *
     * 输入：nums = [1,2,1,3,5,6,4]
     * 输出：1 或 5
     * 解释：你的函数可以返回索引 1，其峰值元素为 2；
     *      或者返回索引 5， 其峰值元素为 6。
     *
     * 解法思路：假定我这个值 index ,的前后 都小于这个值得话，那么他就是一个峰值
     * 但是 该题有个限制条件，就是 时间复杂度 必须是 O(log n)，所以还是使用 二分查找
     *
     * 注意该题一定要注意边界处理  nums[-1] = nums[n] = -∞ ，所以 第一个 和最后一个 都可能是峰值
     *
     * 所以我们不是在找具体的值，而是在找 趋势 变化
     *
     * ，当 / / / / ---> 1 2 3 4 这种，一直走的话，这里面肯定会有一个是峰值 所以这题的核心就是 num[i] < mid[i + 1]
     *  不断的遍历，找到最后一条 num[i] < mid[i + 1] 就是我们的答案
     *  1 2 3 4 3 2， 这个 4 就是我们要的答案
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                // 注意 这里一定要是 mid ,如果是 mid - 1 可能会丢数据，会将 mid 是峰值的数据给丢失掉
                right = mid;
            }
        }

        return left;
    }


    /**
     * 33. 搜索旋转排序数组
     *
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     *
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题
     *
     * 解法思路：如果排序后直接二分，那么很容易能解决该问题，但是他要的时间复杂度是 O(log n)  所以 我们用 二分法去解答该题
     *
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     *
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     *
     * 解法思路：数组中，至少有一半是有序的，
     * 那么我们判断 nums[left] <= nums[mid] 那么就可以表示 左边一定有序
     * 反之，右边一定有序
     * 为什么呢？
     * 因为他只是经过了翻转，举个例子 4,5,6,7,0,1,2  mid = 7, left = 4 这时候 4 - 7 就肯定有有序的，
     * 反之 假如 mid = 1 < left = 4 ,那么他一定是 右半边一定是有序的，4 5 6 7 0，左半边不有序， 右半边 1 2 是有序的
     *
     * 那么解法就出来了，我们只要在 左右两边 有序的路径上面找就行了
     *
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
       // 寻找 左右两边的有序区间来完成该题，左区间有序，那么就从 左区间上面找，右区间有序，那么就从 右区间上面找
        int left = 0;
        int right = nums.length - 1;

        // 开始二分
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                // 左边有序
                // 左边有序的话，我们需要判断 target 是否在 left - mid 之间，如果在的话，我们减少搜索范围 right = mid -1，
                // 反之不在，直接从 mid + 1 作为 left 继续查找
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右边有序
                // 情况判断和 左边相反，判断 target 在不在 mid - right 之间
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }


    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     *
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     *
     * 解法思路：直接二分，寻找 左边界 + 寻找右边界，就可以完成我们的要求
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int left = findLeft(nums, target);

        if (left == nums.length || nums[left] != target) {
            // 左区间 都没有找到
            return new int[]{-1, -1};
        }

        int right = findRight(nums, target);

        return new int[]{left, right};
    }

    private int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private int findRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > target) {
                // 往左边逼
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 和左区间不同的是，返回 右边的 最近的一个数据
        return left - 1;
    }



}
