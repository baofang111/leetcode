package shuxue;

import java.util.HashMap;

/**
 * 数学相关算法 练习
 *
 * @summary ShuXueSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/15 15:49:20
 */
public class ShuXueSolutionV3 {

    /**
     * 9. 回文数
     * <p>
     * 题目意思：给定一个 x ,判断他 是不是 回文数，什么是回文数，就是类似于 121 这种
     * <p>
     * 题目解析：我们将 X 设置成 string ,然后 左右指针去判断即可
     *
     */
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 66. 加一
     * <p>
     * 题目意思：给定一个 大整数的 整数数组 digits，例如 [1,2,3] + 1 变成[1,2,4]
     * 假如，我增加之后 是 10 ，那么需要将这个 1 再往前推进
     *
     */
    public int[] plusOne(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            digits[i] = digits[i] + 1;
            if (digits[i] < 10) {
                // 表示加完了，可以直接返回了
                return digits;
            }
            // 这里需要 变成 0 ，然后前面需要 进 1
            digits[i] = 0;
        }

        // 如果走到这里 肯定是全是 9 的情况了
        int[] result = new int[length + 1];
        result[0] = 1;
        return result;
    }

    /**
     * 172. 阶乘后的零
     *
     * 题目意思：给定一个 n, 让我们计算 n! 阶乘之后，会出现多少个 0
     *
     * 题目解析：这题其实解法很巧妙，1 2 3 4 5 6 的阶乘，因为其中有 2 * 5 所以肯定会出现一个 0，而且 偶数 2 4 都能和 5 相乘 得到 0 ，
     * 所以我们这题就变成了，我们能找到多少个 5，假如是类似于 25 的情况，25 = 5 * 5 它可以得到 2个0
     * 所以解法就是
     *
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n = n / 5;
            count += n;
        }
        return count;
    }

    /**
     * 69. x 的平方根
     *
     * 题目意思：给定一个 x ,比如 4 4的平方根 = 2 ,那么 2 就是我们要的值，但是假如给定的一个数据是 5，那么平方根之后是 2.xxx
     * 那么我们就取整数 = 2
     *
     * 题目解析：我们使用 二分，找到第一个 mid * mid > x 的 位置，就是我们要找的数据
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x < 2) {
            return x;
        }

        int left = 1;
        int right = x / 2;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid <= x / mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    /**
     * 50. Pow(x, n)
     *
     * 题目意思：给定 2个数 ，一个x ,一个 n,让我们计算 x^n X 的 N 次方
     *
     * 题目解析：x¹³ = x⁸ × x⁴ × x¹ 这就是本题答案
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        // 防止 int 过界
        long ln = n;
        if (ln < 0) {
            ln = -ln;
            x = 1 / x;
        }

        double result = 1.0;
        while (ln > 0) {
            if (ln % 2 == 0) {
                // 中间有一个 x2
                x = x * x;
                ln = ln / 2;
            } else {
                // 只剩 x1 了
                result *= x;
                ln = ln - 1;
            }
        }
        return result;
    }

    /**
     * 149. 直线上最多的点数
     *
     * 题目意思：给定一个二维数组，我们计算 直线上最多的 点位
     *
     * 题目解析：我们计算斜率即可，斜率一样的就放一块，但是其中有个点就是 1/2 和 2、4 他俩的斜率一样。
     * 所以我们还需要计算 2、4 的最大公约数，用来让他们除尽，这样就能计算都是 1/2 斜率的出现位置次数了
     *
     * @param points
     * @return
     */
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) {
            // 只有两个点位
            return n;
        }

        int ans = 0;

        // 开始往前遍历
        for (int i = 0; i < n; i++) {

            HashMap<String, Integer> map = new HashMap<>();
            int curMax = 0;

            // 当前点位
            int x1 = points[i][0];
            int y1 = points[i][1];

            for (int j = i + 1; j < n; j++) {
                // 下一个点位
                int x2 = points[j][0];
                int y2 = points[j][1];

                int dx = x2 - x1;
                int dy = y2 - y1;
                String key;

                // x y 是同一点位
                if (dx == 0) {
                    key = "Y";
                } else if (dy == 0) {
                    key = "X";
                } else {
                    if (dx < 0) {
                        // 负数情况处理
                        dx = -dx;
                        dy = -dy;
                    }

                    // 需要取 最大公约数
                    int gcd = gcd(Math.abs(dx), Math.abs(dy));

                    dx = dx / gcd;
                    dy = dy / gcd;

                    key = dx + "/" + dy;
                }

                map.put(key, map.getOrDefault(key, 1) + 1);
                curMax = Math.max(curMax, map.get(key));
            }

            // 每次遍历完成之后，重新寻找, 需要 +1 ，因为自己这个点也算
            ans = Math.max(ans, curMax + 1);
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }


}
