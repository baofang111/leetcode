package hot.hot100V2;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 155. 最小栈
 *
 * 题目意思：设计一个 支持 push, pop, top 等操作，并 有 getMin 方法的最小栈
 *
 * 题目解析：这种，依旧是 空间换时间的思路
 *
 * @summary MinStack
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/05/08 17:51:42
 */
public class MinStack {

    // 注意 一定是空间换时间
    Deque<int[]> stack;


    public MinStack() {
        stack = new ArrayDeque<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            int min = Math.min(val, getMin());
            stack.push(new int[]{val, min});
        }
    }

    /**
     * 删除堆栈顶部的元素。
     */
    public void pop() {
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }

}
