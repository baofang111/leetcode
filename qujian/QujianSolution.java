package qujian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 区间 相关 算法
 *
 * @summary QujianSolution
 * @author: bf
 * @Copyright (c) 2025/12/1, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/1 09:53
 */
public class QujianSolution {

    public static void main(String[] args) {
        QujianSolution solution = new QujianSolution();

        int[] nums = {0, 2, 3, 4, 6, 8, 9};
        solution.summaryRanges(nums);
    }

    /**
     * 228. 汇总区间
     * 给定一个  无重复元素 的 有序 整数数组 nums 。
     * 区间 [a,b] 是从 a 到 b（包含）的所有整数的集合。
     * <p>
     * 输入：nums = [0,1,2,4,5,7]
     * 输出：["0->2","4->5","7"]
     * 解释：区间范围是：
     * [0,2] --> "0->2"
     * [4,5] --> "4->5"
     * [7,7] --> "7"
     * <p>
     * 解法：题目是有序的，那么这题就很简单了
     * <p>
     * [0,2,3,4,6,8,9]
     *
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        // 思路 遍历，然后判断是否和上一个有序的条件来判断我们需要的条件即可
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        // 记录开头位置，用来和后续的数字进行比对
        int start = nums[0];

        // 开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 不连续了，代表进入了新的区间 0,1,2,4,5,7 这时候到了 4
            if (nums[i] != nums[i - 1] + 1) {
                if (nums[i - 1] == start) {
                    // 这是只有一个连续数据的情况
                    res.add(String.valueOf(start));
                } else {
                    // 拼接上一次 连续的尾部节点
                    res.add(start + "->" + nums[i - 1]);
                }
                // 更新起点
                start = nums[i];
            }
        }

        // 因为 我们遍历中只判断了 不等于的条件，所以我们这里面需要补全尾部区间, 比如我们的数据是 0 1 2 ，那么上面没有数据进入的
        if (start == nums[nums.length - 1]) {
            res.add(String.valueOf(start));
        } else {
            res.add(start + "->" + nums[nums.length - 1]);
        }

        return res;
    }


    /**
     * 56. 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * <p>
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * <p>
     * 解法：和 228 题目差不多，我们记录 第一个节点的 intervals[0][0] 的起始位置
     * 然后 往下面遍历开始判断
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        // 和 228 一样，我们需要不断的使用 当前值 和 上一个 last 值去判断，是否包含关系，如果不包含直接添加，如果包含直接重组
        List<int[]> res = new ArrayList<>();

        // 一定要对 开始节点进行排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // 开始遍历
        for (int i = 0; i < intervals.length; i++) {
            // 开始和结束
            int start = intervals[i][0];
            int end = intervals[i][1];

            // 第一个节点添加
            if (res.isEmpty()) {
                res.add(new int[]{start, end});
                continue;
            }

            // 开始和 上一个节点的 end 进行判断, 如果当前节点的 start 已经大于前一个节点的 last 了，那么不会交叉 直接加入即可
            if (start > res.get(res.size() - 1)[1]) {
                res.add(new int[]{start, end});
            } else {
                // 1,3 -- 2,6 存在交叉节点 --> 1 - 6
                int[] last = res.get(res.size() - 1);
                last[1] = Math.max(last[1], end);
            }
        }

        return res.toArray(new int[res.size()][]);
    }

    /**
     * 57 - 插入区间
     * <p>
     * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
     * 输出：[[1,5],[6,9]]
     * <p>
     * 解法：和 56 基本一样，只不过判断添加 从本身，换成了 newInterval
     * 正常做法：
     *  三步走 1：我们首先判断 intervals 哪些小于 newInterval 的开头，直接加入
     *        2：判断重叠的部分 进行合并
     *        3：再次添加 所有 start 本身就大于 newInterval 结尾的数据，再次加入
     *
     * 和 56 题差不多的做法：在下面
     *
     * intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();

        // 不断走的游标
        int i = 0;
        int length = intervals.length;

        int checkStart = newInterval[0];
        int checkEnd = newInterval[1];

        // 第一步 全部小于
        while (i < length && intervals[i][1] < checkStart) {
            res.add(intervals[i]);
            i++;
        }

        // 第二步，有交叉
        while (i < length && intervals[i][0] <= checkEnd) {
            checkStart = Math.min(checkStart, intervals[i][0]);
            checkEnd = Math.max(checkEnd, intervals[i][1]);
            i++;
        }

        res.add(new int[]{checkStart, checkEnd});

        // 第三部 全部大于
        while (i < length) {
            res.add(intervals[i]);
            i++;
        }

        return res.toArray(new int[res.size()][]);
    }

    /**
     * 57 偷懒做法
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert2(int[][] intervals, int[] newInterval) {
        // 把 newInterval 插入到 intervals 中，然后合并区间
        List<int[]> list = new ArrayList<>(Arrays.asList(intervals));
        list.add(newInterval);

        // 排序
        list.sort((a, b) -> a[0] - b[0]);

        List<int[]> res = new ArrayList<>();
        for (int[] nums : list) {
            if (res.isEmpty()) {
                res.add(nums);
                continue;
            }

            int start = nums[0];
            int end = nums[1];
            if (start > res.get(res.size() - 1)[1]) {
                res.add(nums);
            } else {
                // 更新 nums 的 last
                int[] last = res.get(res.size() - 1);
                last[1] = Math.max(last[1], end);
            }
        }

        return res.toArray(new int[res.size()][]);
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
     * 输出：2
     * 解释：气球可以用2支箭来爆破:
     * -在x = 6处射出箭，击破气球[2,8]和[1,6]。
     * -在x = 11处发射箭，击破气球[10,16]和[7,12]。
     *
     * 思路：对 points 排序 后, 我们初始 一个箭
     * 然后一定要拍戏，当我们的 下一个 开始 > 上一个结束的话，那么就需要额外的一个箭
     * 以此类推
     *
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        // 一定要排序
        // TODO 这里有有溢出风险 如果我的数据是这样的华 [[-2147483646,-2147483645],[2147483646,2147483647]]
        // 应该改成这样写 Comparator.comparingInt(a -> a[1])  内部实现是这个 return (x < y) ? -1 : ((x == y) ? 0 : 1); 不会溢出
//        Arrays.sort(points, (a, b) -> a[0] - b[0]);
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        int res = 1;
        int curEnd = points[0][1];

        // 我们从第二个位置开始遍历
        for (int i = 1; i < points.length; i++) {
            int start = points[i][0];
            int end = points[i][1];

            // 如果当前 开始大于上一个结束的话，那么需要多一把箭
            if (start > curEnd) {
                res++;
                // 记住更新我们的 curEnd
                curEnd = end;
            }
        }

        return res;
    }

}
