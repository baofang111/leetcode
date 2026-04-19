package erjinzhi;

/**
 * 二进制题目相关练习
 *
 * @summary ErJinZhiSolutionV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/15 11:49:32
 */
public class ErJinZhiSolutionV3 {


    /**
     * 67. 二进制求和
     * <p>
     * 题目意思：给定两个二进制数 a='1000101' b='0102010'，让我们要求计算他 a + b 之后等于多少
     * <p>
     * 题目解析：这题和 链表的相加非常类似。我们使用一个 pre 去累加 相加之后的值，如果 1+1 = 2 = 10 那么当前就是 0 pre 就是 1
     *
     */
    public String addBinary(String a, String b) {
        int pre = 0;

        int i = a.length() - 1;
        int j = b.length() - 1;

        StringBuilder sb = new StringBuilder();

        while (i >= 0 || j >= 0 || pre != 0) {
            int result = pre;
            if (i >= 0) {
                result += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                result += b.charAt(j) - '0';
                j--;
            }

            sb.append(result % 2);
            pre = result / 2;
        }

        // 反转返回
        return sb.reverse().toString();
    }

    /**
     * 190. 颠倒二进制位
     *
     * 题目意思：给定一个 二进制 n='0010101010' 我们要将他实现倒转
     *
     *
     * @param n
     * @return
     */
    public int reverseBits(int n) {

        return 0;
    }


    /**
     * 191. 位1的个数
     *
     * 题目意思：给定一个二进制 n ,我们要判断他有多少个1
     *
     * 题目解析：int = 32 未，不断的往前移动，然后判断末尾 & 1 是不是 = 1
     *
     */
    public int hammingWeight(int n) {
        int count = 0;

        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }

    /**
     * 136. 只出现一次的数字
     *
     * 题目意思：给定一个数组 nums，里面有重复数据 + 单次出现的数据
     *
     * 题目解析：a ^ b ^ b = a ，两个一样的数据 a^a=0 这就是本题答案
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * 201. 数字范围按位与
     *
     * 题目意思：给定两个数字，left + right ，我们要找出 包含他俩相同的位置的点
     * 例如：把这个区间里的所有数字，挨个做 按位与（&）运算，最后算出结果。
     *
     * 题目解析：这题使用二进制有个偷懒的点，就是当一个元素 & 任意元素之后，只要出现任意一个 0 ，那么他就是0 ，
     * 只有当所有的都是 1 的情况下，它才是 1
     *
     * 所以我们找公共前缀即可，当 所有的数据 的公共前缀都一样，那么后面补 0 就行
     *
     * @param left
     * @param right
     * @return
     */
    public int rangeBitwiseAnd(int left, int right) {
        int count = 0;
        // 为什么这里只使用 left + right 就行了，因为 left - right 中间一定会发生翻转，0-1 1-0 所以默认忽略也没有什么问题
        while (left != right) {
            left = left >> 1;
            right = right >> 1;
            count++;
        }

        return left << count;
    }



}
