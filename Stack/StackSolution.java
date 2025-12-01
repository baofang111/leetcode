package Stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 栈 solution
 *
 * @summary Solution
 * @author: bf
 * @Copyright (c) 2024/3/7, © 拜耳作物科学（中国）有限公司
 * @since: 2024/3/7 10:01
 */
public class StackSolution {

    public static void main(String[] args) {
        boolean valid = isValid("()[]{}");
        System.out.println(valid);
    }


    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     * <p>
     * 思路：使用栈，栈的先入后出的特点正好与本题括号排序特点一致，就是遇到 左括号入栈，遇到右括号将对应的左括号出站，遍历完成之后，
     * 遍历 stack 为空，那么就是有效的括号
     * 简历 哈希表，构建
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            }
            else if (c == '{') {
                stack.push('}');
            }
            else if (c == '[') {
                stack.push(']');
            } else if (stack.empty() || c!= stack.pop()) {
                return false;
            }
        }
        if (stack.empty()) {
            return true;
        }
        return false;
    }

}
