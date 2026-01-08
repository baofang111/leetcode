package erjinzhi;

/**
 * 二进制 相关题目
 *
 * @summary ErJinZhiSolution
 * @author: bf
 * @Copyright (c) 2026/1/8, © 拜耳作物科学（中国）有限公司
 * @since: 2026/1/8 16:07
 */
public class ErJinZhiSolution {


    /**
     * 67. 二进制求和
     *
     * 解法思路：按位 遍历 a 和 b，然后进行相加
     * 然后因为是 二进制 所以我们需要
     * a + b 假如都是 1 + 1 的情况，1 + 1 = 2 = 2%2 = 0 + 放到下一个位置的 2/2=1 = 10
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int last = 0;

        StringBuilder sb = new StringBuilder();

        while (i >= 0 || j >= 0 || last != 0) {
            // 当前值 = 上一个值多的值
            int sum = last;

            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            sb.append(sum % 2);
            last = sum / 2;
        }

        // 因为我们是从开头开始算的，所以需要反转返回
        return sb.reverse().toString();
    }

    /**
     * 190. 颠倒二进制位
     *
     * 颠倒给定的 32 位有符号整数的二进制位。
     *
     * 输入：n = 2147483644
     * 输出：1073741822
     * 解释：
     * 整数	二进制
     * 2147483644	01111111111111111111111111111100
     * 1073741822	00111111111111111111111111111110
     *
     * 解法思路：
     * 因为是给定的 32 位，所以 如何不到 32 位 需要补 0 ，然后再翻转
     *
     * 所以 我们伪代码就是 foreach 0 - 32 位，然后通过不断的 左移动 右移动 + 最关键的一步 result != (n & 1)
     *
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            // result 左移一位 进行该位置的添加
            result <<= 1;

            // 将该位置 添加到 result
            // |= 是“把某一位点亮且不破坏其它位”的最安全方式，n & 1 是拿 最后一位的方法， 011101001 & 000000001 = 000000001,其余 因为 1 前面全是 0 所以 都变成了 0
            result |= (n & 1);

            // 弄完之后，n 右移一位，进行下一位的添加（ 这里一定要做无符号右移 ） 010010 ---> 01001
            n >>>= 1;
        }

        return result;
    }

    /**
     * 191. 位1的个数
     *
     * 给定一个正整数 n，编写一个函数，获取一个正整数的二进制形式并返回其二进制表达式中 设置位 的个数（也被称为汉明重量）。
     *
     * 输入：n = 128
     * 输出：1
     * 解释：输入的二进制串 10000000 中，共有 1 个设置位。
     *
     * 解题思路： n 按照 32 位，每次移动一位，然后去 和 & 1 去判断最后一位 是不是 1
     * n & 1 = 01000101 & 00000001 ( 这就是 1 )
     * 所以 遍历 32 去寻找即可
     *
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                count++;
            }
            // 一定要往右 无符号 右移一位
            n >>>= 1;
        }
        return count;
    }

    /**
     * 136. 只出现一次的数字
     *
     * 输入：nums = [2,2,1]
     * 输出：1
     *
     * 解法思路：
     * a ^ a = 0
     * a ^ 0 = a
     * 疑问该题是 某个元素出现了一次，但是其余元素珺出现两次，所以 a ^ a 之后 肯定是 0 ，所以可以用 ^ 直接得到结果
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
     * 137. 只出现一次的数字 II
     *
     * 解法思路：和 136 不同的是，这里 是 只出现一次，其余的情况都是出现3次
     * 所以该题 不能使用 异或，我们就 按位统计，然后对 3  取模
     * 因为 二进制的每一位是独立的，当 index = i 的位置出现了 3次的数，那么他一定是 1+1+1得到的 如果是1，那么和3 取模之后 1 % 3 会等于 1
     *
     * 二进制（4 位）
     * 3 = 0011
     * 1 = 0001
     *
     * 按位统计
     * 位	3(×3)	1	总和	%3	结果位
     * bit0	1+1+1	1	4	1	1
     * bit1	1+1+1	0	3	0	0
     * bit2	0	0	0	0	0
     * bit3	0	0	0	0	0
     *
     *
     * 所以该题的框架雏形就出来了
     * foreach i -> 32
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        int result = 0;

        for (int i = 0; i < 32; i++) {
            int count = 0;
            for (int num : nums) {
                // 这是 num 这个数字 在 移动 i 位置之后，和 1 判断，是不是该位置 是 1
                if (((num >> i) & 1) == 1) {
                    count++;
                }
            }

            // 到这里判断 count 是不是 3 那么该位置就是我们要找的地方
            if (count % 3 != 0) {
                //  result = result | (1 << i) 的意思就是 0000 | 0100 = 0100， | 的意思就是 二进制中，所有带有 1 的 | 完还是 1
                result |= (1 << i);
            }
        }

        return result;
    }

    /**
     * 201. 数字范围按位与
     *
     * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
     *
     * 输入：left = 5, right = 7
     * 输出：4
     *
     * 题目意思：
     * 给定两个 left right,然后让我们计算 result =  left & (left + 1) & (left + 2) & ... & right
     *
     * 但是因为 & 有一个重要的特性就是 不管多少个数 多少次的 & ，只要 出现过一次 0，那么他就肯定是 0
     * 再换句话说，只有 在 整个区间 全是 1 ，这个 1 才能被保存下来
     * 这样该题的解题思路就出来了
     *
     *  解法思路：
     *
     * @param left
     * @param right
     * @return
     */
    public int rangeBitwiseAnd(int left, int right) {
        int count = 0;

        // 因为 & 只要出现过一次的特性，所以 left - right 的中间数据肯定是帮不上忙的，因为必定发生会翻转
        // 所以只有公共前缀是有参考 意义的，还是用 11011010 11010011 这两个举例，不管这两个数据中间隔了多少个数据，都只是 1101 这个公共前缀具有参考意义，其他全是 0
        while (left != right) {
            // 这里面的意思是 11011010  11010011 在 1101 的后面肯定存在不一样的数据，那么 & 怎么都是 0 ，
            // 所以我们直接取出来 1101 然后 将后面 全部 补 0 就是我们要求的答案
            left >>= 1;
            right >>= 1;
            count++;
        }

        return left << count;
    }

    public static void main(String[] args) {
        ErJinZhiSolution solution = new ErJinZhiSolution();


        solution.hammingWeight(11);
    }

}
