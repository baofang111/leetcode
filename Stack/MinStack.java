package Stack;

import java.util.Map;
import java.util.Stack;

/**
 * 最小栈，我们使用 一个数组栈，每次添加 push 的时候 添加一个 最小值 放到 栈顶的 数组 【1】位置当中，然后 getMin 直接取即可
 *
 * @summary MinStack
 * @author: bf
 * @Copyright (c) 2024/3/7, © 拜耳作物科学（中国）有限公司
 * @since: 2024/3/7 10:40
 */
public class MinStack {

    Stack<int[]> stack = new Stack<>();

    public MinStack() {

    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            // 不等于 null 的时候 需要将 min 放入
            stack.push(new int[]{val, Math.min(val, stack.peek()[1])});
        }
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }

}
