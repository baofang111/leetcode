package erfen;

/**
 *
 * 二分查找法 相关练习
 *
 * @summary ErfenSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/07 20:15:54
 */
public class ErfenSolutionV3 {


    /**
     * 35. 搜索插入位置
     * <p>
     * 题目意思：给定了一个 有序的数组 nums，然后我们找到 target 在 nums 里面的索引位置
     * <p>
     * 题目解析：典型的一道二分算法题
     *
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        // 开始二分
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int num = nums[mid];

            if (target == num) {
                return mid;
            } else if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     * 74. 搜索二维矩阵
     *
     * 题目意思：给定一个二维矩阵，是有序的，然后我们判断 target 在不在二维矩阵里面
     *
     * 题目解析：有序 ，寻找某个值，典型的一道 二分查找题目
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        // 开始二分
        int left = 0;
        int right = n * m - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int row = mid / m;
            int col = mid % m;

            int num = matrix[row][col];

            if (num == target) {
                return true;
            } else if (num < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }


    /**
     * 162. 寻找峰值
     *
     * 题目意思。给定一个部分有序的 nums，找到他的峰值位置，比如 1231 ，最高峰在 3，那么他的峰值位置 就是 index = 2 的地方
     *
     * 题目解析：该题是部分有序，有序 - 无序 ，这也是一道典型的 二分查找法，我们只要找到有序的边缘就行了，该题需要注意一点就是
     * 对于 开头 和 结尾，是 无穷小
     * 所以该题的解法就来到了，如何寻找到 部分有序,也就是要寻找到 下一个节点 小于 上一个节点的位置
     * 所以我们的判断逻辑是 寻找到 第一个 不满足 nums[i + 1] < nums[i] 的位置，这个 i 就是我们要找的峰值
     *
     * 举个例子：
     *  ///// \\  上升趋势 mid 在前面，那么峰值就需要再次往后找
     *  // \\\\\  下降趋势 mid ，在后面，那么峰值就是 在 left - mid 中间，这时候，我们需要将 right = mid ，进行再次二分
     *
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        // 开始二分
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                // 中点位置，依旧是上升趋势，我们需要往后继续找
                left = mid + 1;
            } else {
                // 中点位置是下降趋势，证明 峰值在 left - mid 的位置当中，所以我们需要移动 right 的位置
                right = mid;
            }
        }

        return left;
    }

    /**
     * 33. 搜索旋转排序数组
     *
     * 题目意思：给定了一个 旋转的 升序数组，（12345 - 45123），然后给定了一个 target ,让我们判断 target 在不在数组里面
     *
     * 题目解析：该题 依旧是部分有序，依旧是在部分有序中 寻找 target，所以第一想法还是 二分查找
     * 我们需要判定两个条件，一个是 左边有序，一个是右边有序
     * 如果是 左边有序，那么我们就在左边进行二分查找，如果是右边有序，那么我们就在右边进行二分查找
     * 然后搜索区间，继续二分
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int num = nums[mid];
            if (num == target) {
                // 找到了结果
                return mid;
            }

            // 我们判断那边有序，注意这里因为需要找到 具体的 值，所以这里一定要 <=
            if (nums[left] <= nums[mid]) {
                // 左边有序，从左边找,我们判断 target 在不在 left - mid 中间
                if (nums[left] <= target && target < num) {
                    // 收缩范围
                    right = mid - 1;
                } else {
                    // 不在这个范围，那么肯定在 另外一边，直接更新 left 即可
                    left = mid + 1;
                }
            } else {
                // 右边有序，从右边找，判断 target 在不在 mid - right 中间
                if (num < target && target <= nums[right]) {
                    // 收缩范围
                    left = mid + 1;
                } else {
                    // 不在这个范围，那么肯定在 另外一边，直接更新 right 即可
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * 题目意思：给定一个有序的数组 nums，其中数组会包含重复元素，再给定一个 target, 让我们寻找 target 在 nums 中的位置
     *
     * 题目解析：有序 + 寻找某个值，依旧是 二分查找，我们先找到第一个 left 位置，然后 往后边遍历，找到 right 即可
     *
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int left = findLeft(nums, target);
        if (left >= nums.length || nums[left] != target) {
            return new int[]{-1, -1};
        }

        int right = left;
        for (int i = left; i < nums.length; i++) {
            if (nums[i] == target) {
                right = i;
            } else {
                break;
            }
        }

        return new int[]{left, right};
    }

    private int findLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 还没找到 left 位置
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

}
