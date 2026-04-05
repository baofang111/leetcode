package Stack;

import java.util.*;

/**
 * 栈 相关算法练习
 *
 * @summary StackSolutionV3
 * @author: bf
 * @Copyright (c) 2026/3/25, © 拜耳作物科学（中国）有限公司
 * @since: 2026/3/25 13:34
 */
public class StackSolutionV3 {


    public static void main(String[] args) {
        StackSolutionV3 solutionV3 = new StackSolutionV3();
//        solutionV3.isValid("(]");

        String[] strings = {"4", "13", "5", "/", "+"};
        solutionV3.evalRPN(strings);

    }

    /**
     * 20. 有效的括号
     * <p>
     * 题目意思：有一堆括号 () {} [] 如果是 成双成对的，就是有效的括号，（] 这种就不是有效的括号
     * <p>
     * 题目解析：很典型的一道 栈的题目，括号 前半部分先入栈，然后 非前半部分的时候，拿去比较，如果不是成双成对那么就不是有效的括号
     */
    public boolean isValid(String s) {
        int length = s.length();
        if (length == 0 || length % 2 != 0) {
            return false;
        }

        List<Character> pre = Arrays.asList('(', '[', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (pre.contains(c)) {
                // 前半部分 入栈
                stack.push(c);
            } else {
                Character value = stack.pop();

                // 如果不是一一对应的，那么久不是有效括号
                if (c == ')' && value != '(') {
                    return false;
                }
                if (c == ']' && value != '[') {
                    return false;
                }
                if (c == '}' && value != '{') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * 71. 简化路径
     * <p>
     * 题目意思：将类似于 这种路径 /home/user/Documents/../Pictures 简化掉 /home/user/Pictures，其中 .. 需要向上走一步
     * . 就是当前目录
     * <p>
     * 题目解析：题目意思就很明显了，很明显的一题 栈的 题目。我们将 结果集 进行 / 分段，然后对于 "" "." ".." 进行相对应的处理
     * 其中 ".." 向上进一个路径，就是 删掉当前已经 入栈的数据就行
     */
    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) {
            return path;
        }

        Deque<String> stack = new ArrayDeque<>();

        String[] paths = path.split("/");

        for (String value : paths) {
            if (Objects.equals(value, "") || Objects.equals(value, ".")) {
                continue;
            }

            if (Objects.equals(value, "..")) {
                // 删掉一个路径,注意是删除最后的路径
                stack.pollLast();
                continue;
            }

            stack.addLast(value);
        }

        if (stack.isEmpty()) {
            return "/";
        }

        // 重新组织
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * 150. 逆波兰表达式求值
     * <p>
     * 题目意思：将 ["2","1","+","3","*"] 按照 ((2 + 1) * 3) 的计算得到值
     * <p>
     * 题目解析：题目的意思很明显就是 遇到 符号，那么就把前面的两个数据给他进行相应的 算数 操作，所以我们需要有几个 数据
     * 栈：存储之前得到的数据
     * result：存储当前计算完的值
     */
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        List<String> symbol = Arrays.asList("/", "+", "-", "*");

        for (String value : tokens) {
            if (symbol.contains(value)) {
                Integer value1 = stack.pollLast();
                Integer value2 = stack.pollLast();
                int num = 0;
                // 如果是数字 那么就 入栈
                if (Objects.equals(value, "+")) {
                    num = value2 + value1;
                } else if (Objects.equals(value, "/")) {
                    num = value2 / value1;
                } else if (Objects.equals(value, "-")) {
                    num = value2 - value1;
                } else {
                    num = value2 * value1;
                }

                // 计算完 入栈
                stack.addLast(num);
            } else {
                stack.addLast(Integer.parseInt(value));
            }
        }

        return stack.isEmpty() ? 0 : stack.pop();
    }


    /**
     * 224. 基本计算器
     *
     * 题目描述：
     * 实现一个简单的计算器，支持：
     * 1. 加法 +、减法 -
     * 2. 括号 ( )
     * 3. 整数（可能是多位数）
     *
     * 示例：
     * 输入："(1+(4+5+2)-3)+(6+8)"
     * 输出：23
     *
     *
     * ==============================
     * 一、核心思路（重点）
     * ==============================
     *
     * 本题本质是：
     * 👉 使用「栈」来处理“括号带来的上下文切换”
     *
     * 我们维护三个核心变量：
     *
     * 1. res   ：当前计算结果
     * 2. sign  ：当前符号（+1 或 -1）
     * 3. stack：用于保存“进入括号前的状态”
     *
     *
     * ==============================
     * 二、关键规则（必须理解）
     * ==============================
     *
     * 1️⃣ 遇到数字：
     *    👉 累加成一个完整数字（处理多位数）
     *
     * 2️⃣ 遇到 '+'：
     *    👉 先把当前数字加入结果：res += sign * num
     *    👉 更新符号 sign = 1
     *
     * 3️⃣ 遇到 '-'：
     *    👉 先结算当前数字：res += sign * num
     *    👉 更新符号 sign = -1
     *
     * 4️⃣ 遇到 '('（进入子表达式）：
     *    👉 把当前“上下文”压栈：
     *       stack.push(res)
     *       stack.push(sign)
     *
     *    👉 重置当前状态：
     *       res = 0
     *       sign = 1
     *
     *
     * 5️⃣ 遇到 ')'（结束子表达式）：
     *    👉 先结算当前括号内结果：
     *       res += sign * num
     *
     *    👉 弹出栈中的数据：
     *       sign = stack.pop()
     *       prevRes = stack.pop()
     *
     *    👉 合并结果：
     *       res = prevRes + sign * res
     *
     *
     * ==============================
     * 三、执行流程举例（重点）
     * ==============================
     *
     * 表达式：(1+(4+5+2)-3)+(6+8)
     *
     * 步骤拆解：
     *
     * 1. 遇到 '('：
     *    👉 入栈 [res=0, sign=1]
     *
     * 2. 处理 1：
     *    👉 res = 1
     *
     * 3. 遇到 '+'：
     *    👉 sign = 1
     *
     * 4. 遇到 '('：
     *    👉 入栈 [res=1, sign=1]
     *
     * 5. 处理 (4+5+2)：
     *    👉 res = 11
     *
     * 6. 遇到 ')'：
     *    👉 出栈：
     *       res = 1 + 1 * 11 = 12
     *
     * 7. 遇到 '-'：
     *    👉 sign = -1
     *
     * 8. 处理 3：
     *    👉 res = 12 - 3 = 9
     *
     * 9. 遇到 ')'：
     *    👉 出栈：
     *       res = 0 + 1 * 9 = 9
     *
     * 10. 遇到 '+'：
     *     👉 sign = 1
     *
     * 11. 处理 (6+8)：
     *     👉 res = 14
     *
     * 12. 合并：
     *     👉 res = 9 + 14 = 23
     *
     *
     * ==============================
     * 四、核心总结（面试用）
     * ==============================
     *
     * 本题关键是：
     *
     * ❗ 遇到 '(' 就“保存现场”（res + sign）
     * ❗ 遇到 ')' 就“恢复现场”
     *
     * 本质是：
     * 👉 用栈实现“递归上下文切换”
     *
     *
     * ==============================
     * 五、复杂度
     * ==============================
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)（栈）
     *
     */
    public int calculate(String s) {
        s = s.replace(" ", "");
        /*
            关键点，我们需要使用一个 result 记录之前计算的结果，比如 3+4-5 用 result 记录 3+4 的值，然后 -5 的时候 直接用 result - 5
            还有 使用一个 sign 记录正负号，用于后面的计算
            使用栈 对 数据 进行 添加，用于计算
            num  用于记录 12 这种 之前遍历到的数据
         */
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int sign = 1;
        int num = 0;

        int length = s.length();

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // 注意，这里会有一种情况就 12 是分两次进来的，我们需要 把他
            if (Character.isDigit(c)) {
                // 这里是数字的亲看过
                num = num * 10 + c - '0';
            } else {
                // 判断是 计算符号 还是 括号
                if (c == '+') {
                    result += num * sign;
                    num = 0;
                    sign = 1;
                }
                else if (c == '-') {
                    result += num * sign;
                    num = 0;
                    sign = -1;
                }
                else if (c == '(') {
                    // 注意 如果是 ( 的话，我们需要对之前的数据 进行入栈 1+(_ 其实我们要存储的激素 1 和 + 其中 1 是 result, +=sign
                    stack.push(result);
                    stack.push(sign);

                    // 设置完就需要重置
                    result = 0;
                    sign = 1;
                }
                else {
                    // 走到这里代表是 ）代表已经要开始进行计算了，比如 1+(3+4-5) = 3+4(7) -> 此时 num = 5, sign = -1, result = 7
                    // 但是我们还需要注意 如果之前有数据的话比如 1+，我们还需要对之前结果进行计算
                    result += num * sign;

                    Integer preSign = stack.pop();
                    Integer preResult = stack.pop();

                    result = preResult + preSign * result;

                    // 重置
                    num = 0;
                }
            }
        }

        return result + num * sign;
    }

}
