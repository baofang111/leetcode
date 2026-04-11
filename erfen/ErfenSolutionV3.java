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
     * <p>
     * 题目意思：给定一个二维矩阵，是有序的，然后我们判断 target 在不在二维矩阵里面
     * <p>
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
     * <p>
     * 题目意思。给定一个部分有序的 nums，找到他的峰值位置，比如 1231 ，最高峰在 3，那么他的峰值位置 就是 index = 2 的地方
     * <p>
     * 题目解析：该题是部分有序，有序 - 无序 ，这也是一道典型的 二分查找法，我们只要找到有序的边缘就行了，该题需要注意一点就是
     * 对于 开头 和 结尾，是 无穷小
     * 所以该题的解法就来到了，如何寻找到 部分有序,也就是要寻找到 下一个节点 小于 上一个节点的位置
     * 所以我们的判断逻辑是 寻找到 第一个 不满足 nums[i + 1] < nums[i] 的位置，这个 i 就是我们要找的峰值
     * <p>
     * 举个例子：
     * ///// \\  上升趋势 mid 在前面，那么峰值就需要再次往后找
     * // \\\\\  下降趋势 mid ，在后面，那么峰值就是 在 left - mid 中间，这时候，我们需要将 right = mid ，进行再次二分
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
     * <p>
     * 题目意思：给定了一个 旋转的 升序数组，（12345 - 45123），然后给定了一个 target ,让我们判断 target 在不在数组里面
     * <p>
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
     * <p>
     * 题目意思：给定一个有序的数组 nums，其中数组会包含重复元素，再给定一个 target, 让我们寻找 target 在 nums 中的位置
     * <p>
     * 题目解析：有序 + 寻找某个值，依旧是 二分查找，我们先找到第一个 left 位置，然后 往后边遍历，找到 right 即可
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

    /**
     * 153. 寻找旋转排序数组中的最小值
     * <p>
     * 题目意思：依旧是给定一个旋转的 升序 数组 nums，让我们寻找他的最小值
     * <p>
     * 题目解析：部分有效 + 寻找峰值（最大 or 最小）
     * 这题和寻找峰谷的 解法是一样的
     * 峰值：最大值：增增增 突然来一个 下降的，那么他的前一个位置 就是峰值，也就是部分最大值
     * 峰谷：最小值：增增增，突然来一个 下降的，那么下降的这个位置，就是峰谷，也是就是我们要找的最小值
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        // 我们判断 mid - right 的大小，加入 mid > right ，那么这个变小的值，一定是 在 mid - right 中间，再次遍历即可
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return nums[left];
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * <p>
     * 题目意思：给定了两个有序 数组 nums1 + nums2 ，要求我们找出 这两个正序数组 合并后的中位数
     * 例如：1-3 + 2 --- 2   1-2 3-4 ---- (2+3)/2 = 2.5
     * <p>
     * 题目解析：因为 题目的要求，时间复杂度 必须是 O(log (m+n))，所以一定是二分
     * 那么我们回到问题本身，什么是 中位数
     * 中位数就是 必须满足两个条件
     * 条件1：左边元素 = 右边元素 （ 或者左边多一个 ）
     * 条件2：左半边的所有元素的值 < 右半部分所有元素的值
     * <p>
     * 所以我们根据这个条件，就可以得到 ，假定给了两个数组
     * -------L1 | R1 -------
     * -------L2 | R2 -------
     * 只要 L1 < R2 && L2 < R1 那么这个位置就是我们要找二中位数
     * <p>
     * 所以我们按照一个数组作为基准，不断的寻找这个位置即可，可以用更小的那个去作为基准去寻找
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if (m < n) {
            return findMedianSortedArrays(nums2, nums1);
        }

        // 现在按照 nums1 作为基准开始二分，寻找满足条件的点位，注意这里二分的是分割线，所以 right = n
        int left = 0;
        int right = n;

        while (left <= right) {
            // 分别来寻找 nums1 的中间位置，和 需要满足 条件1 的 nums2 的位置
            int i = left + (right - left) / 2;
            int j = (n + m + 1) / 2 - i;

            // 寻找 L1 R1 L2 R2 的位置
            int L1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int R1 = i == n ? Integer.MAX_VALUE : nums1[i];

            int L2 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
            int R2 = j == m ? Integer.MAX_VALUE : nums2[j];

            // 判断是否符合条件
            if (L1 <= R2 && L2 <= R1) {
                // 满足条件了，直接返回值
                // 奇数
                if ((n + m) % 2 == 1) {
                    return Math.max(L1, L2);
                } else {
                    // 左边最大 + 右边最小 / 2
                    return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
                }
                // 偶数
            } else if (L2 > R1) {
                // 上面的选择小了，需要上面的再往前寻找一下
                left = i + 1;
            } else {
                // 上面的选择大了，往下找找
                right = i - 1;
            }
        }

        return 0.0;
    }


}
