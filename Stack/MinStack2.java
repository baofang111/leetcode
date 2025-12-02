package Stack;

import java.util.Stack;

/**
 * 最小栈 2
 *
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 *
 * 使用一个数组存取，每次将 最小值 放入到 int[1] 的位置当中即可
 *
 * @summary MinStack2
 * @author: bf
 * @Copyright (c) 2025/12/2, © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/2 10:43
 */
public class MinStack2 {

    Stack<int[]> stack = new Stack<>();


    public MinStack2() {

    }

    /**
     * 放入数据
     * @param val
     */
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            // 将 min 存储到 任何位置中的 第二个元素当中
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
     * @return
     */
    public int top() {
        return stack.peek()[0];
    }

    /**
     * 获取 栈中的最小值
     * @return
     */
    public int getMin() {
        return stack.peek()[1];
    }
}
