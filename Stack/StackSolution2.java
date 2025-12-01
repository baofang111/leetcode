package Stack;

import java.util.Stack;

/**
 * 栈 相关的算法
 * <p>
 * 栈 核心的就 几个方法
 * <p>
 * push 入栈，将数据放入栈顶
 * peek 查看栈顶 元素 但不删除
 * pop 出栈 查看栈顶元素，并删除元素 和 peek 的不通
 *
 * @summary StackSolution2
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2025/12/1 21:27:22
 */
public class StackSolution2 {

    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * <p>
     * 输入：s = "()"
     * 输出：true
     * <p>
     * 解法：使用一个栈 进出 进出 进出，看是否对称
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        // 我们使用一个栈， 遇到左半边就
        Stack<Character> stack = new Stack<>();

        char[] charArray = s.toCharArray();
        for (Character c : charArray) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // 走到了另外一边
                // 不对称
                if (stack.isEmpty()) {
                    return false;
                }
                // 取出栈中的是护具
                char top = stack.pop();

                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }

        return stack.isEmpty();
    }

    /**
     * 71. 简化路径
     * @param path
     * @return
     */
    public String simplifyPath(String path) {

        return "";
    }

}
