package hot.hot150;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 最小栈：
 * <p>
 * 题目意思：设计一个 最小栈，要求 常数时间内 O（1）时间拿到 min 的值
 * <p>
 * 题目解析：很明显的 栈的问题，然后就是 如何存储 这个最小值的问题了，我们可以用空间换时间的思路，将最小值存起来，
 * 这里因为有一个问题 假如 有些东西出栈了，而出栈的正好是最小值，所以这个最小值需要进行更新，所以这个最小值需要放入 和 栈的数据一起
 *
 * @summary MinStack3
 * @author: bf
 * @Copyright (c) 2026/3/25, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/25 14:54
 */
public class MinStack3 {

    Deque<int[]> stack;

    public MinStack3() {
        stack = new ArrayDeque<>();
    }

    /**
     * 放入数据
     *
     * @param val
     */
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            // 每一个的 第二个位置，放的都是当前的最小值
            int min = Math.min(getMin(), val);
            stack.push(new int[]{val, min});
        }

    }

    /**
     * 删除栈顶数据
     */
    public void pop() {
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    /**
     * 获取栈顶数据
     *
     * @return
     */
    public int top() {
        return stack.peek()[0];
    }

    /**
     * 获取 栈中的最小值
     *
     * @return
     */
    public int getMin() {
        return stack.peek()[1];
    }

}
