package slidingWindow;

/**
 * 滑动窗口相关题目
 *
 * @summary SlidingWindowSolution
 * @author: bf
 * @Copyright (c) 2025/3/18, © 拜耳作物科学（中国）有限公司
 * @since: 2025/3/18 11:24
 */
public class SlidingWindowSolution {


    /**
     * 76 - 最小覆盖子串
     *  给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
     *
     *  1： 暴力解法，遍历两个 s t ，判断条件，然后获取到我们需要的值 （ 这个时间复杂度太高了 ）
     *  2： 使用 滑动窗口：
     *      思路： 1：我们设置一个 左闭右开的 区间 [left, right )
     *            2：我们不断的 将 right 往前走，直到他满足我们的条件 （ 包含了 T 中的所有字符 ）
     *            3：此时 我们停止 right 的移动，开始将 left 往前移动，用来缩小指针，直到他 不满足 我们的条件，然后更新我们的数据
     *            4：重复 2 3 步骤，直到 right 达到 S 的尽头
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {

        return "";
    }

}
