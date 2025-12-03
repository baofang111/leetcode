package Stack;

import listNode.ListNodeTest;

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
     * 解法：（ gaiti ）
     *  核心其实就是几点：
     *  1：我们顺序扫描，用两个变量存储 当前的运算状态
     *   - result: 当前的累计的结果
     *   - sign: 当前符号 （1 表示 + ，-1 表示 -1）
     *  2：当遇到 （ 用栈保存状态
     *      推入当前 result 和 sign, 重置计算环境
     *      1 - （4 + 5）
     *  3：遇到 ）弹栈恢复括号前的环境
     *      括号内结果计算完毕，比例：
     *          （4 + 5）= 9
     *   栈顶包含
     *      上一层 result
     *      上一层 sign
     *   结合还原
     *      result = pre_result + pre_sign * 当前括号结果
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        s = s.replace(" ", "");

        // 该题目的核心 就是 我们不断的遍历 针对 + - （ ） 做分别的处理，且一定要有三个数据去记录我们计算的中途结果，num result sign

        // 解析到的 数字
        int num = 0;
        // 当前计算到的结果 统计
        int result = 0;
        // 当前的计算编号 + 1 - -1
        int sign = 1;
        Deque<Integer> stack = new ArrayDeque<>();

        char[] charArray = s.toCharArray();
        for (Character c : charArray) {
            if (Character.isDigit(c)) {
                // 处理数字
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                //  + 号的话，我们需要记录 该 + 号状态，并且重置 sign 和 num 的数据
                result += sign * num;
                num = 0;
                sign = 1;
            }else if (c == '-') {
                // 和 - 相反
                result += sign * num;
                num = 0;
                sign = -1;
            }else if (c == '(') {
                // 这里我们需要 把上面记录的数据 进行存储到栈里面，方便后面的计算
                // 举个例子 1 + （3 + 4） 我们要把 1 + 记录下来，然后 在 ) 处理好 3+4 的时候再和他进行计算
                stack.push(result);
                stack.push(sign);

                // 需要重置环境
                result = 0;
                sign = 1;

                // 注意 这里面是不需要计算的，只需要将 之前的计算结果 进行 入栈
            }else if (c == ')') {
                // 我们 需要对 （ ） 中间的数据 进行汇总,也就是 不断累加 （ ）中的 result ，然后对 1 + 进行重新累加计算
                result += sign * num;
                num = 0;

                Integer preSign = stack.pop();
                Integer preResult = stack.pop();

                result = result + preResult * preSign;
            }
        }

        return result + num * sign;
    }


    public static void main(String[] args) {
        StackSolution2 solution = new StackSolution2();

        String[] tokens = {"2", "1", "+", "3", "*"};


        int i = solution.calculate("1 + 1");
        System.out.println(i);
    }

}


