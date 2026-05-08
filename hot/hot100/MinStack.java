package hot.hot100;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 155. 最小栈
 * <p>
 * 题目意思：让我们设置一个最小栈，要求必须在常数时间内 找到
 * <p>
 * 题目意思：要求时间复杂度，那么我们就使用 空间换时间
 *
 */
class MinStack {

    Deque<int[]> stack;


    public MinStack() {
        stack = new ArrayDeque<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            int min = getMin();
            min = Math.min(min, val);
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

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */