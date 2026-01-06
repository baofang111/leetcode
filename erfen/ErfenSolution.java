package erfen;

import java.util.Arrays;

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
     * <p>
     * 每行中的整数从左到右按非严格递增顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * <p>
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
     * <p>
     * 解法: 同样典型的 二分查找，只不过我们二分的是 整个 二维数组
     * <p>
     * 所以 我们 left = 0, right = n * m - 1
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
     * <p>
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * <p>
     * 输入：nums = [1,2,1,3,5,6,4]
     * 输出：1 或 5
     * 解释：你的函数可以返回索引 1，其峰值元素为 2；
     * 或者返回索引 5， 其峰值元素为 6。
     * <p>
     * 解法思路：假定我这个值 index ,的前后 都小于这个值得话，那么他就是一个峰值
     * 但是 该题有个限制条件，就是 时间复杂度 必须是 O(log n)，所以还是使用 二分查找
     * <p>
     * 注意该题一定要注意边界处理  nums[-1] = nums[n] = -∞ ，所以 第一个 和最后一个 都可能是峰值
     * <p>
     * 所以我们不是在找具体的值，而是在找 趋势 变化
     * <p>
     * ，当 / / / / ---> 1 2 3 4 这种，一直走的话，这里面肯定会有一个是峰值 所以这题的核心就是 num[i] < mid[i + 1]
     * 不断的遍历，找到最后一条 num[i] < mid[i + 1] 就是我们的答案
     * 1 2 3 4 3 2， 这个 4 就是我们要的答案
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
     * <p>
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * <p>
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题
     * <p>
     * 解法思路：如果排序后直接二分，那么很容易能解决该问题，但是他要的时间复杂度是 O(log n)  所以 我们用 二分法去解答该题
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     * <p>
     * 解法思路：数组中，至少有一半是有序的，
     * 那么我们判断 nums[left] <= nums[mid] 那么就可以表示 左边一定有序
     * 反之，右边一定有序
     * 为什么呢？
     * 因为他只是经过了翻转，举个例子 4,5,6,7,0,1,2  mid = 7, left = 4 这时候 4 - 7 就肯定有有序的，
     * 反之 假如 mid = 1 < left = 4 ,那么他一定是 右半边一定是有序的，4 5 6 7 0，左半边不有序， 右半边 1 2 是有序的
     * <p>
     * 那么解法就出来了，我们只要在 左右两边 有序的路径上面找就行了
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
     * <p>
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     * <p>
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     * <p>
     * 解法思路：直接二分，寻找 左边界 + 寻找右边界，就可以完成我们的要求
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        // 我们通过二分法 分别找到 左右两边的值 就行
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int left = findLeft(nums, target);
        // 如果左边都没找到，那么我们就直接返回
        if (left == nums.length || nums[left] != target) {
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
                // 中间值还没找到 就 left 缩小查询范围
                left = mid + 1;
            } else {
                // 注意，这里的 right 不能 mid - 1 ,我们要找的是 最左边，所以要往左边靠  12 2 2 2 34
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
                // 中间值还没找到 就 right 缩小查询范围
                right = mid;
            } else {
                // 注意，这里的 right 不能 mid - 1 ,我们要找的是 最左边，所以要往左边靠  12 2 2 2 34
                left = mid + 1;
            }
        }

        // 走到这里 代表 走到了 mid + 1 了，满足条件的情况就需要 - 1
        return left - 1;
    }


    /**
     * 153. 寻找旋转排序数组中的最小值
     * <p>
     * 输入：nums = [4,5,6,7,0,1,2]
     * 输出：0
     * <p>
     * 解法：这题和 寻找峰值一样的道理，一个是找峰值，一个是找 峰谷
     * 如果 增 增 增 增 突然出现一个 数 小于前一个数，那么他就是 最小值
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        // 该题的意思就是，我们找到一个 num[i] > num[i+1] 只要后面出现小于的，那么他就是最小点
        // 我们直接使用二分 进行查找即可

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                // 右边的纯在更小的值，那么 最小值肯定在 mid - right 之间，我们更新左指针，继续寻找
                left = mid + 1;
            } else {
                // mid <= right  证明 mid 在右段 有序，或者最小值就是 mid, 所以我们缩小范围 left - mid 在开始找，所以 right = mid 继续遍历
                right = mid;
            }
        }

        // 此时 left = right 跳出循环，这两个都是最小值
        return nums[left];
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * <p>
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2，
     * 请你找出并返回这两个正序数组的中位数。
     * <p>
     * 要求时间复杂度为 O(log(m + n))。
     * <p>
     * ------------------------------------------------------------
     * 解题核心思想（非常重要）：
     * <p>
     * ❌ 不是去“找中位数这个值”
     * ✅ 而是去“找一个切分位置”，把两个数组划分成左右两部分
     * <p>
     * 使得：
     * 1）左半部分的元素个数 == 右半部分（或多一个）
     * 2）左半部分所有元素 <= 右半部分所有元素
     * <p>
     * ------------------------------------------------------------
     * 为什么不能直接合并？
     * <p>
     * 合并后再取中位数的时间复杂度是 O(m + n)，
     * 不满足题目要求的 O(log(m + n))。
     * <p>
     * ------------------------------------------------------------
     * 切分思路（画面化理解）：
     * <p>
     * nums1: [ ... L1 | R1 ... ]
     * nums2: [ ... L2 | R2 ... ]
     * <p>
     * 只需要关注 4 个边界值：
     * L1：nums1 左边最大值
     * R1：nums1 右边最小值
     * L2：nums2 左边最大值
     * R2：nums2 右边最小值
     * <p>
     * 只要满足：
     * L1 <= R2 && L2 <= R1
     * <p>
     * 就说明：
     * 左边整体 <= 右边整体，切分是正确的
     * <p>
     * ------------------------------------------------------------
     * 为什么可以用二分查找？
     * <p>
     * - 只在较短的数组 nums1 上进行切分（保证稳定性）
     * - 切分位置 i 的取值范围是 [0, nums1.length]
     * - 如果：
     * L1 > R2  → nums1 左边切多了，i 要左移
     * L2 > R1  → nums1 左边切少了，i 要右移
     * <p>
     * 切分位置具有单调性 → 可以使用二分查找
     * <p>
     * ------------------------------------------------------------
     * 中位数的计算：
     * <p>
     * - 如果 (m + n) 为奇数：
     * 中位数 = max(L1, L2)
     * <p>
     * - 如果 (m + n) 为偶数：
     * 中位数 = (max(L1, L2) + min(R1, R2)) / 2
     * <p>
     * ------------------------------------------------------------
     * 本题本质总结：
     * <p>
     * 这是一个「边界二分 + 不变式维护」的经典题目，
     * 难点不在代码，而在于：
     * <p>
     * 👉 把“找中位数”转化为“找合法切分点”
     * <p>
     * 一旦切分画面理解清楚，代码只是对这个过程的翻译。
     * <p>
     * ------------------------------------------------------------
     *
     * @param nums1 第一个正序数组（较短）
     * @param nums2 第二个正序数组
     * @return 两个正序数组的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 该题的核心是 对 nums1 + nums2 进行 i j 的切分，当我们满足该要求的时候， L1 <= R2 && L2 <= R1 ( 数据有交叉的地方 )，这时候就是我们要找的数据
        int length1 = nums1.length;
        int length2 = nums2.length;

        // 用小范围的 做遍历标准
        if (length2 < length1) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int left = 0;
        int right = length1;

        // 开始二分遍历
        while (left <= right) {
            // 先寻找 nums1 的中点
            int i = left + (right - left) / 2;
            // 然后在找 j的位置，举个例子 nums1 有5个数据，nums2 有7 个数据，我这是  i 是在4 的位置，那么 j ,
            // 我不能走太远，只能走中位数 （length1 + length2 + 1）/ 2,所以下面的就是这个了
            int j = (length1 + length2 + 1) / 2 - i;

            // 找到 i j 附近的两个点 L1 R1 L2 R2
            int L1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int R1 = (i == length1) ? Integer.MAX_VALUE : nums1[i];
            int L2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int R2 = (j == length2) ? Integer.MAX_VALUE : nums2[j];

            // 判断是否满足条件
            if (L1 <= R2 && L2 <= R1) {
                // 已经满足条件了，直接返回
                if ((length1 + length2) % 2 == 1) {
                    // 奇数：奇数使用 两边的最大值 L1 L2
                    return Math.max(L1, L2);
                } else {
                    // 偶数: 左最大 + 右最小 / 2
                    return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
                }
            } else if (L1 > R2) {
                // 左边切多了，
                right = i - 1;
            } else {
                // 右边切多了
                left = i + 1;
            }
        }

        // 一般不会走到这边
        return 0.0;
    }

}
