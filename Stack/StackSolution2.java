package Stack;

import java.util.*;

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
     * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为 更加简洁的规范路径。
     * <p>
     * 在 Unix 风格的文件系统中规则如下：
     * 一个点 '.' 表示当前目录本身。
     * 此外，两个点 '..' 表示将目录切换到上一级（指向父目录）。
     * 任意多个连续的斜杠（即，'//' 或 '///'）都被视为单个斜杠 '/'。
     * 任何其他格式的点（例如，'...' 或 '....'）均被视为有效的文件/目录名称。
     * <p>
     * 解法：使用栈，我们先将 path 使用 / 进行拆分
     * 然后开始遍历判断
     * 1：遇到 空串 "." 直接跳过
     * 2：遇到 ".." 栈不空的时候 弹出来 一个 pop
     * 3: 最后使用
     *
     * @param path
     * @return
     */
    public String simplifyPath(String path) {
        if (path == null) {
            return "";
        }

        // 思路很简单，拆分之后 判断是 "" "." ".." 还是其他情况，分别对栈进行不同的操作即可
        Deque<String> stark = new ArrayDeque<>();
        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.equals("") || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stark.isEmpty()) {
                    stark.pollLast();
                }
            } else {
                stark.addLast(part);
            }
        }

        // 如果为空 那么直接返回 /
        if (stark.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        while (!stark.isEmpty()) {
            sb.append("/").append(stark.pop());
        }
        return sb.toString();
    }

    /**
     * 150. 逆波兰表达式求值
     * <p>
     * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
     * 请你计算该表达式。返回一个表示表达式值的整数。
     * <p>
     * 注意：
     * <p>
     * 有效的算符为 '+'、'-'、'*' 和 '/' 。
     * 每个操作数（运算对象）都可以是一个整数或者另一个表达式。
     * 两个整数之间的除法总是 向零截断 。
     * 表达式中不含除零运算。
     * 输入是一个根据逆波兰表示法表示的算术表达式。
     * 答案及所有中间计算结果可以用 32 位 整数表示。
     * <p>
     * 解法：
     * 逆波兰表达式 = 后缀表达式：运算符在后面
     * 计算方式 = 数字入栈；遇到符号就弹两个数字运算然后压回栈
     *
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        // 该题的核心就是理解题目，题目大概的意思是 逆波兰表达式 = 后缀表达式：运算符在后面
        // 且计算方式，就是 数字入栈，遇到符号就弹出两个数字进行运算然后压回栈中

        List<String> symbol = Arrays.asList("/", "+", "-", "*");

        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            if (symbol.contains(token)) {
                Integer last = stack.pollLast();
                Integer first = stack.pollLast();
                Integer num = null;
                if (token.equals("/")) {
                    num = first / last;
                } else if (token.equals("+")) {
                    num = first + last;
                } else if (token.equals("-")) {
                    num = first - last;
                } else if (token.equals("*")) {
                    num = first * last;
                }
                if (num != null) {
                    stack.addLast(num);
                }
            } else {
                int num = Integer.parseInt(token);
                stack.addLast(num);
            }
        }

        if (stack.isEmpty()) {
            return 0;
        }

        return stack.peek();
    }

    /**
     * 224 - 基本计算器
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
     *
     * 输入：s = "(1+(4+5+2)-3)+(6+8)"
     * 输出：23
     *
     * 解法：
     *  核心其实就是几点：
     *  1：我们不断的 将字符往 栈上面添加
     *  2：当 遇到 ")" 的时候 我们要遍历，找到 最近的一个上一次的对应的 "(" 然后计算他们的值，然后将他们重新入栈
     *  3：不断计算 就可以得到我们要的结果
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        s = s.replace(" ", "");

        char[] charArray = s.toCharArray();
        Deque<String> stack = new ArrayDeque<>();

        for (Character c : charArray) {
            if (')' == c) {
                // 开始不断的往前找
            } else {
               // 也有可能没有
            }
        }

        return 0;
    }


    public static void main(String[] args) {
        StackSolution2 solution = new StackSolution2();

        String[] tokens = {"2", "1", "+", "3", "*"};

        int i = solution.evalRPN(tokens);
        System.out.println(i);
    }

}


