package hot.hot150;

import java.util.*;

/**
 * 区间相关算法题 练习
 *
 * @summary QujianSolutionV2
 * @author: bf
 * @Copyright (c) 2026/3/24, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/24 14:24
 */
public class QujianSolutionV3 {


    /**
     * 228. 汇总区间
     * <p>
     * 题目意思：就是 假如 我又 0 1 2 3 这种连续的递增顺序出现的数据的话，那么就将他汇总成 0 -> 3
     * <p>
     * 题目解析：该题目有个重要的点就是，本身 nums 是 无重复数据，且有序的，那么，其实我们是同一个区间的 公式就出来了
     * nums[i + 1] == nums[i] + 1,
     * 那么 我们遍历 nums ，如果 满足 上面的公式，记录 start + end ，直到遍历结束即可
     */
    public List<String> summaryRanges(int[] nums) {
        int length = nums.length;
        if (length == 0) {
            return Collections.emptyList();
        }

        List<String> res = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int start = nums[i];

            // 下面开始寻找 end
            while (i + 1 < length && nums[i + 1] == nums[i] + 1) {
                i++;
            }

            int end = nums[i];

            if (start == end) {
                res.add(String.valueOf(start));
            } else {
                res.add(start + "->" + end);
            }
        }

        return res;
    }

    /**
     * 56. 合并区间
     * <p>
     * 题目意思：将 重叠的 区间 进行合并，比如 1-6 2-8 因为 6 > 2 ，那么就将他们合并成 1-8
     * <p>
     * 题目解析：思路很清晰，我们将 intervals 进行排序，然后遍历，假如 下一个的 开头 > 上一个的结尾，那么 他们就需要合并
     * int start
     * int end
     * if (nextStart <= preEnd){
     * 这里需要更新 end,当不满足的时候，就开始进行合并
     * }
     * <p>
     * 举个例子： 18 24 37 9-12 -- 当 9 > 8  的时候，我们才需要合并之前的所有在同一区间的 区间数值
     */
    public int[][] merge(int[][] intervals) {
        int length = intervals.length;
        if (length == 0) {
            return new int[][]{};
        }

        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // 锚定第一个结果集
        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < length; i++) {
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];

            // 开始判断是否是 需要合并的合并区间
            if (nextStart > end) {
                // 到这里，需要进行合并了
                res.add(new int[]{start, end});

                // 更新 start + end
                start = nextStart;
                end = nextEnd;
            } else {
                // 这里还是有需要合并的区间,继续往下一个区间进行寻找
                end = Math.max(end, nextEnd);
            }
        }

        // 需要补充最后一个
        res.add(new int[]{start, end});

        return res.toArray(new int[res.size()][]);
    }


    /**
     * 57. 插入区间
     * <p>
     * 题目意思：和 56 合并区间基本一样，只不过是 将 newInterval 插入到 intervals 中
     * <p>
     * 题目解析：
     * 1 - 简单来说，我直接将 newInterval 插入到 intervals 中，然后排个序，然后他就变成了 56 题
     * 2 - 我们 遍历 intervals 去判断，哪些不交叉，哪些交叉，相当于 前 - 交叉 - 后
     * <p>
     * 注意 intervals 是有序的，
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();

        int index = 0;
        int length = intervals.length;

        int start = newInterval[0];
        int end = newInterval[1];

        // pre
        while (index < length && intervals[index][1] < start) {
            res.add(intervals[index]);
            index++;
        }

        // 交叉部分 - 这里的部分 和 合并区间的逻辑一样, 我们取最大和最小即可
        while (index < length && intervals[index][0] <= end) {
            start = Math.min(start, intervals[index][0]);
            end = Math.max(end, intervals[index][1]);
            index++;
        }

        res.add(new int[]{start, end});

        // 尾部
        while (index < length) {
            res.add(intervals[index]);
            index++;
        }

        return res.toArray(new int[res.size()][]);
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * <p>
     * 题目意思：依旧是 合并区间的变种，只要算 有重合的地方，都是一支箭，当 start > preEnd 那么就需要新的一支箭了
     * <p>
     * 题目解析：合并区间变种题，我们依旧需要先进行排序
     */
    public int findMinArrowShots(int[][] points) {
        int length = points.length;
        if (length == 0) {
            return 0;
        }

        // 这里排序有 越界问题，因为这个是 在哪射箭，我们需要找 end 的值去判断，而不是 找开头
        Arrays.sort(points, Comparator.comparing(a -> a[1]));

        int res = 1;

        // 依旧固定一个锚点
        int start = points[0][0];
        int end = points[0][1];

        for (int i = 1; i < length; i++) {
            int nextStart = points[0][0];
            int nextEnd = points[0][1];

            if (nextStart > end) {
                // 另外一个区间了
                res++;

                start = nextStart;
                end = nextEnd;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        QujianSolutionV3 solutionV2 = new QujianSolutionV3();

        int[][] intervals = {
                {1, 2},
                {2, 3},
                {3, 4},
                {4, 5}
        };

        solutionV2.findMinArrowShots(intervals);
    }

}
