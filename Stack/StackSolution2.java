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
 * -------------------
 *
 * ArrayDeque 它同时具备队列（FIFO）、双端队列、栈（LIFO） 三种数据结构的功能，官方推荐优先用它替代 Stack 和 LinkedList。
 *
 * // 头部添加
 * deque.addFirst("A");    // 满了抛异常
 * deque.offerFirst("B");  // 满了返回 false（推荐）
 *
 * // 尾部添加
 * deque.addLast("C");
 * deque.offerLast("D");
 *
 * // 头部删除
 * deque.removeFirst();    // 空了抛异常
 * deque.pollFirst();      // 空了返回 null（推荐）
 *
 * // 尾部删除
 * deque.removeLast();
 * deque.pollLast();
 *
 * // 查看元素（不删除）
 * deque.getFirst();       // 空抛异常
 * deque.peekFirst();      // 空返回 null（推荐）
 * deque.getLast();
 * deque.peekLast();
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
        /*
            该题的核心 需要④个主要元素
            result: 记录每次计算的结果值 比如 3+4+5 我们需要记录每次 计算的值，3+4 = 7 用于后续计算
            sign: 当前的 计算符号是什么
            num: 目前遍历 解析到的 数字，注意需要注意 12 这种情况，所以 num = num * 10 + c - '0'
            stack: 我们计算的时候，会有这种情况 1+（3+4-5）我们先需要计算括号里面的数据，所以就需要将之前的 1（result） +(sign) 进行
                入栈存储
         */

        s = s.replace(" ", "");

        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0;
        int result = 0;
        int sign = 1;

        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else {
                // 判断 + - （ ） 的各种情况
                if (c == '+') {
                    // + 需要处理的情况 3+4+5 我们需要 result + 当前的 num,比如 第二个 + 号，这时候就是 result += num
                    result += num * sign;

                    // 注意 重置相关状态
                    sign = 1;
                    num = 0;
                } else if (c == '-') {
                    result += num * sign;

                    // 注意 重置相关状态
                    sign = -1;
                    num = 0;
                } else if (c == '(') {
                    // （ 的情况是类似于这样的情况 1+( 走到 ( 的时候我们需要将 1（result） +(sign) 进行存储
                    stack.push(result);
                    stack.push(sign);

                    // 重置
                    result = 0;
                    sign = 1;
                } else {
                    // ) 需要计算 栈里面的 preResult + 当前的 result 的累加值了
                    result += num * sign;

                    Integer preSign = stack.pop();
                    Integer preResult = stack.pop();

                    result = preResult + result * preSign;

                    // 计算完需要重置 num
                    num = 0;
                }
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


