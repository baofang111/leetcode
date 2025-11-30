package array;

import com.sun.tools.javac.Main;

import java.util.*;

/**
 * 刷题 第二版
 *
 * @summary ArraySolutionV2
 * @author: bf
 * @Copyright (c) 2025/11/11, © 拜耳作物科学（中国）有限公司
 * @since: 2025/11/11 10:05
 */
public class ArraySolutionV2 {

    public static void main(String[] args) {
        ArraySolutionV2 solutionV2 = new ArraySolutionV2();

//        int[] ints = {7, 1, 5, 3, 6, 4};
//        solutionV2.maxProfit(ints);

        solutionV2.lengthOfLastWord("   fly me   to   the moon  ");
    }

    /**
     * 合并两个有序数组
     * <p>
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素
     * <p>
     * 解法： 使用 后插入法
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 将数据放入到 nums1 当中

        int i = nums1.length;
        while (n > 0) {
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[i - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[i - 1] = nums2[n - 1];
                n--;
            }
            i--;
        }
    }

    /**
     * 27 移除元素
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     * <p>
     * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
     * 输出：5, nums = [0,1,4,0,3,_,_,_]
     * 解释：你的函数应该返回 k = 5，并且 nums 中的前五个元素为 0,0,1,3,4。
     * <p>
     * 解法： 使用快慢指针 快指针往前找 不等于 val 的值，慢指针用来存储 快指针 不等于的哪个值
     */
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        if (length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        while (fast < length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            // 当 fast 的值 = val 的时候，快指针往前面继续走，寻找下一个节点
            fast++;
        }

        return slow;
    }


    /**
     * 26 - 删除有序数组中的重复项
     * <p>
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 输出：5, nums = [0,1,2,3,4,_,_,_,_,_]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 思路：
     * 同上一题，同样使用指针来完成改题操作。因为已经排好序了，所以可以安心的使用 快慢指针，当数据一致的时候，移动快指针即可，
     * 然后将 快指针数据，放到慢指针上面即可
     * <p>
     * 这题和 27 题 基本一样
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        int slow = 0;
        // 快指针需要向前一步
        int fast = 1;

        while (fast < length) {
            if (nums[slow] != nums[fast]) {
                // 需要吧 慢指针的前一个位置 放成 快指针的值。所以这里需要 先 slow++
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }

        // 输出位置 所以要 指针+1
        return slow + 1;
    }

    /**
     * 80 - 删除有序数组中的重复项 Ⅱ
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 输入：nums = [0,0,1,1,1,1,2,3,3]
     * 输出：7, nums = [0,0,1,1,2,3,3]
     * 解释：函数应返回新长度 length = 7, 并且原数组的前七个元素被修改为 0, 0, 1, 1, 2, 3, 3。不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 对比 26 题，第一眼想到的也是 使用 快慢指针去操作，但是这里操作有一个不同的就是 需要存储 有且进
     *
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        // 和 26 题 最大的区别就是 这里面 每一步是 2 步，所以我们快慢指针 直接 按照 2步 2步走，其他和 26 基本一样
        if (nums == null) {
            return 0;
        }

        int length = nums.length;
        if (length < 2) {
            return length;
        }

        int slow = 2;
        int fast = 2;
        while (fast < length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        return slow;
    }

    /**
     * 169 - 多数元素
     * <p>
     * 这题 直接排序之后，取中间位置的值就行了
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 189 - 轮转数组
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * <p>
     * 题解，不断翻转即可
     * 先翻转全部 在翻转前面，再翻转后面
     * step1: [7,6,5,4,3,2,1]
     * step2: [5,6,7,4,3,2,1]
     * step3: [5,6,7,1,2,3,4]
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return;
        }

        int length = nums.length;

        // 因为可能 k 的大于 length 的，所以需要对 K 进行取模
        k %= length;

        swap(nums, 0, length - 1);
        // 这边因为翻转之后，所以位置就在 k-1 的地方了
        swap(nums, 0, k - 1);
        swap(nums, k, length - 1);

    }

    private void swap(int[] nums, int i, int j) {
        while (i < j) {
            int temp = nums[i];
            nums[i++] = nums[j];
            nums[j--] = temp;
        }
    }

    /**
     * 121 - 买卖股票的最佳时机
     * <p>
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票
     * <p>
     * 解法：按照顺序 获取最大
     */
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }

        // 分别取最大和最小 去计算即可
        int profit = 0;
        int cost = Integer.MAX_VALUE;

        for (int price : prices) {
            cost = Math.min(cost, price);
            profit = Math.max(profit, price - cost);
        }

        return profit;
    }

    /**
     * 双指针版本
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        if (prices == null) {
            return 0;
        }

        int maxProfit = 0;
        // 低价格位置
        int minPriceIndex = 0;
        int index = 1;

        while (index < prices.length) {
            if (prices[minPriceIndex] < prices[index]) {
                int profit = prices[index] - prices[minPriceIndex];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                // 注意 这里一定要 替换 最小价格的位置
                minPriceIndex = index;
            }
            index++;
        }

        return maxProfit;
    }

    /**
     * 122 - 买卖股票的最佳时机 2
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。然而，你可以在 同一天 多次买卖该股票，但要确保你持有的股票不超过一股。
     * <p>
     * 可以同样使用 maxProfit2 的双指针方法
     * 也可以使用贪心算法，每次上涨的时候 给他卖了，那么他一定就是总收益最大的时候
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        if (prices == null) {
            return 0;
        }

        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }

        return maxProfit;
    }

    /**
     * 55 - 跳跃游戏
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标
     * <p>
     * 其实就是判断能不能跳跃到 最后一个表
     * <p>
     * 解法：
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums == null) {
            return false;
        }
        // 直接遍历所有点，如果当前点比前面所有点可以达到的点的最大距离还要大就返回false，如果能遍历到最后一个点，说明最后一个点可以到达，返回true
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                // 这代表我们跳跃已经跳出去了
                return false;
            }
            // 这一步代表 i 位置到 i 能走的最大距离，注意 tim
            max = Math.max(max, i + nums[i]);
        }

        return true;
    }

    /**
     * 28 = 找出字符串中 第一个匹配项的下标
     * <p>
     * 通过拆解去判断
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }

        int length = haystack.length();
        int needLength = needle.length();

        for (int i = 0; i <= length - needLength; i++) {
            if (haystack.substring(i).startsWith(needle)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 68 - 文本左右对齐
     * 给定一个单词数组 words 和一个长度 maxWidth ，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
     * <p>
     * 你应该使用 “贪心算法” 来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
     * <p>
     * 输入: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
     * 输出:
     * [
     * "This    is    an",
     * "example  of text",
     * "justification.  "
     * ]
     * <p>
     * 思路，我们啊按照题目的意思，大概可以将该题的思路进行一下拆分
     * 1: 遍历 words ,当前位置 或者 + 下一个位置，不断的 while 迭代，找到 第一个 地方 可以装的最大的 单词位置
     * 2：我们需要 判断出来 这个地方 总共可以 装 多少单词
     * 3：有了 可以装多少单词，我们就可以计算出来 需要多少空格 总单词数量 - 1 （ 这是个临界条件 一定需要判断）
     * 4：这里有了分支判断
     * 4-1：如果单词 需要的空格 = 0，或者是 已经到了最后一个单词了的华，直接邹题目逻辑的 最左原则，后续补全即可
     * 4-2： 如果不是最后一个未知 或者 gap ！= 0，那么我们邹的是中间的逻辑，这时候需要注意的点就是 我们的空格 需要均分给 每个单词
     * 举个例子： 6个空格，4个单词，我们每一个单词之间的空格需要  6 /（4 - 1）= 2 个，如果有 7个空格，4 个单词，我们需要把
     * 多的那个空格，按照顺序 分掉，这个空格不能放后面
     * 5：移动我们需要找的下一个未知，再次遍历
     *
     * @param words
     * @param maxWidth
     * @return
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        if (words == null) {
            return null;
        }

        List<String> res = new LinkedList<>();

        int n = words.length;
        int index = 0;

        while (index < n) {
            // 我们需要获取到 当前位置 最大可以存储多少个单词
            int currentWidth = words[index].length();
            // 下一个位置
            int last = index + 1;
            // 还可以往里面添加
            while (last < n && (currentWidth + 1 + words[last].length()) <= maxWidth) {
                currentWidth += 1 + words[last].length();
                last++;
            }

            // 当前 index - last 就是这波 可以添加的 单词前后位置
            // 单词数量
            int wordCount = last - index;
            // 这些单词所需要的最少 空格数
            int gap = wordCount - 1;

            StringBuilder sb = new StringBuilder();

            // 走到第四分支，是否是最后一个位置，或者是 最后一个单词，不需要中间空格的
            if (last == n || gap == 0) {
                // 按照顺序 单词 + 空格 依次添加
                for (int i = index; i < last; i++) {
                    sb.append(words[i]);
                    if (i != last - 1) sb.append(" ");
                }

                // 不够长度的话，需要填充空格
                int rightGap = maxWidth - sb.toString().length();
                sb.append(" ".repeat(Math.max(0, rightGap)));
            } else {
                // gap 需要均分给每一个位置, 走 4.2 的分支逻辑
                int totalWordSize = 0;
                for (int i = index; i < last; i++) {
                    totalWordSize += words[i].length();
                }

                // TODO 这里有个问题，就是 这是均分，但是他其实要必须保证 最后一个单词 在最后的位置，不能 最后一个单词 + 空格

                // 获取总共需要多少个 空格
                int totalGap = maxWidth - totalWordSize;

                // 空格只能填在 gap1 和 gap2，不能填在单词后面去创造额外的 gap！ 所以 这里 除以 gap 而不是 wordCount
                // 获取 均分下来 每一个单词之间需要多少空格
                int avgGap = totalGap / gap;
                // 均分下来 可能有多的,就取模 依次放入
                int overGap = totalGap % gap;

                for (int i = index; i < last - 1; i++) {
                    sb.append(words[i]).append(" ".repeat(Math.max(0, avgGap)));
                    if (overGap > 0) {
                        sb.append(" ");
                        overGap--;
                    }
                }

                // 添加最后一个单词
                sb.append(words[last - 1]);
            }

            res.add(sb.toString());

            // 替换位置，继续执行操作
            index = last;
        }

        return res;
    }

    /**
     * 45 - 跳跃有限 Ⅱ
     * <p>
     * 解法 还是使用 贪心法
     * 使用一个值，记录步数
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        /*

        题目说明：假设你总是可以到达数组的最后一个位置。
        即一定存在一条路线能够到达最后一个位置，而不是说中间没有 0 ，只是存在可以越过 0 的路线

        贪心算法：找能跳的最远的
        使用 k 记录目前能够跳到的最高位置
        使用 end 记录这次跳跃的边界，到达边界就跳跃次数 + 1

        过程解析：
        最开始遍历 i = 0, end = 0,因此 step 会进行 step ++，我们可以认为，这是开始起跳，因为必定会落下，因此跳跃次数 + 1
        而 nums[0] 这个数限制了你只能在落脚在某个范围内，假如 nums[0] = 4，那么你只能选择落脚在 [1, 4] 位置，而如果到了边界，那么肯定是一次新的起跳，因此次数需要再 + 1

        从 0 位置开始起跳，你落脚的必定是 [1, 4] 位置中能够跳得更远的，因为根据贪心思想，这样做能够尽可能的减少跳跃次数，因为更加接近最后一个位置
        而在这个过程遍历 [1, 4] 过程中一直记录着最远位置 k，而你落地在 [1, 4] 之间，落地的那个点也就是 [1, 4] 之间最能够跳得远的那个位置，因此当到达边界的时候，将 end 更新为 k

        注意：[1, 4] 跳得最远的位置必定不会在 [1, 4] ，因为如果在 [1, 4] ，那么表示根本就出不去 [1, 4] 这个圈
        当然不会是 [4,1,1,1,0,1,2] 这种的，因为如果是这种的，压根过不去这个 0，因此必定第一次起跳必定能够跳出 [1, 4] 这个范围，比如 [4,1,1,1,1,1,0]
        */
        int k = 0;
        //记录跳跃的次数
        int step = 0;
        int end = 0;
        //这里有个小细节，因为是起跳的时候就 + 1 了，如果最后一次跳跃刚好到达了最后一个位置，那么遍历到最后一个位置的时候就会再次起跳，这是不允许的，因此不能遍历最后一个位置
        for (int i = 0; i < nums.length - 1; i++) {
            //一定跳得到，因此不存在 k < i

            k = Math.max(k, i + nums[i]);
            //第一次起跳 或 到达跳跃的边界
            if (i == end) {
                //再次起跳
                step++;
                //更新边界
                end = k;
            }
        }

        return step;
    }


    /**
     * 274 - H 指数
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
     * <p>
     * 输入：citations = [3,0,6,1,5]
     * 输出：3
     * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
     * 由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        return citations[citations.length / 2];
    }


    /**
     * 134 - 加油站
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     * <p>
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     * <p>
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
     * <p>
     * 解法：
     * 核心思想 全局油量 > 全局花费 才能饶一圈
     * 若从某个点出发 如果初始油量 < 0 那么不能从这边出发，需要更换出发点
     *
     * @param gas  第 i 个加油站有汽油 gas[i] 升
     * @param cost 第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 总油量
        int total = 0;
        // 当前油量
        int current = 0;
        // 起始位置
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            int dif = gas[i] - cost[i];
            total += dif;
            current += dif;

            // 如果当前油量 < 0 ,代表从 start - i 走不完全程，所以一定需要换个位置
            if (current < 0) {
                start = i + 1;
                // 这里 当前油量一定要重置，因为换起点了
                current = 0;
            }
        }

        return total >= 0 ? start : -1;
    }

    /**
     * 135 - 分发糖果
     * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     * <p>
     * 你需要按照以下要求，给这些孩子分发糖果：
     * <p>
     * 每个孩子至少分配到 1 个糖果。
     * 相邻两个孩子中，评分更高的那个会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
     * <p>
     * 示例 1：
     * <p>
     * 输入：ratings = [1,0,2]
     * 输出：5
     * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
     * <p>
     * 解法：左右扫描只满足一边
     * 取最大值 - 同时满足两边
     * 可以理解为：每个孩子的糖果需求 = “左规则要求的最少糖果”和“右规则要求的最少糖果”的最大值
     * <p>
     * 所以可以用 两遍扫描 或者 动态规划来保存结果
     *
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        if (ratings == null) {
            return 0;
        }

        int length = ratings.length;

        int[] left2Right = new int[length];
        int[] right2Left = new int[length];

        // 填充 最小需要糖果
        Arrays.fill(left2Right, 1);
        Arrays.fill(right2Left, 1);

        // 从左向右
        for (int i = 1; i < length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left2Right[i] = left2Right[i - 1] + 1;
            }
        }

        // 从右向左
        for (int i = length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right2Left[i] = right2Left[i + 1] + 1;
            }
        }

        // 取最大值
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Math.max(left2Right[i], right2Left[i]);
        }

        return sum;
    }

    /**
     * 42 - 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * <p>
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
     * <p>
     * 使用 双指针 解法，我们的核心目标就是 i 的位置 最多可以装多少水，有了这个思路，这题就好解决了
     * <p>
     * Math.min(左边界, 右边界) - height[i] 当前位置的高度，
     * <p>
     * 有这个思路之后，我们就可以使用双指针，遍历，一条一条 的去 判断 计算 Math.min(左边界, 右边界) - height[i] 然后累加 即可
     * <p>
     * 为什么 可以直接 左右 各个相加，因为 height[left] < height[right] 类似于这种判断，已经将 最小 min 给限制了
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        // 左右指针
        int left = 0;
        int right = height.length - 1;

        // 左右最大值
        int leftMax = 0;
        int rightMax = 0;

        // 总水位
        int water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // 左边更小
                if (height[left] > leftMax) {
                    // 更新 左边的 最大边，用来继续判断
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                // 右边更小
                if (height[right] > rightMax) {
                    // 更新 右边的 最大边，用来继续判断
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }


    /**
     * 13 - 罗马数字 转整数
     * <p>
     * 这题看似看不懂，其实注意一个点就行了
     * 当前值 < 下一位 就是 -
     * 当前值 > 下一位 就是 +
     * <p>
     * 所以我们只需要一次遍历即可
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int value = 0;

        int length = s.length();
        for (int i = 0; i < length; i++) {
            int current = symbolValues.get(s.charAt(i));
            if (i < length - 1 && current < symbolValues.get(s.charAt(i + 1))) {
                value -= current;
            } else {
                value += current;
            }
        }

        return value;
    }

    /**
     * 12 - 整数 转 罗马数字
     * <p>
     * 例子： 3749 将他 转变成 3000 + 700 + 40 + 9 然后将他转换成 对应的 罗马数字即可
     * <p>
     * 3000 如何转换呢？ 1000 + 1000 + 1000 = MMM
     * 700 = 500 + 100 + 100 = DCC
     * 40 = 10 + 50 = XL
     * 9 = 10 - 1 = IX
     * <p>
     * 解法，列举出来所有的 罗马数字和其对应关系，一定要按照顺序进行排列，
     * 然后遍历即可算出
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }

        return sb.toString();
    }


    /**
     * 58 - 最后一个单词的长度
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        if (s == null) {
            return 0;
        }
        String[] sArrays = s.trim().split(" ");

        return sArrays[sArrays.length - 1].length();
    }


    /**
     * 14 - 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * <p>
     * 使用横向 扫描
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 初始化第一个单词
        String prefix = strs[0];

        // 横向对比
        for (int i = 0; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    /**
     * 151 - 反转字符串中的单词
     * <p>
     * 示例 1：
     * <p>
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     * 示例 2：
     * <p>
     * 输入：s = "  hello world  "
     * 输出："world hello"
     * 解释：反转后的字符串中不能存在前导空格和尾随空格。
     * <p>
     * 很直白的逻辑，取出所有单词 然后对单词的数组进行反转
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        // 简单的过滤翻转
        String[] words = s.trim().split(" ");

        StringBuilder sb = new StringBuilder();

        // 倒序查
        for (int i = words.length - 1; i >= 0; i--) {
            if (words[i].equals("")) {
                continue;
            }
            sb.append(words[i]).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * 6 - Z 字形变换
     * <p>
     * 首先我们需要知道 什么是 Z 字形变化 ，然后我们按照轨迹去 存 对应的每一行数据即可，
     * 然后按照 第一行 第二行 .... 将数据拼接起来即可
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (s == null || numRows == 1) {
            return s;
        }

        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            sb[i] = new StringBuilder();
        }

        /*
            flag 代表方向 1 正方向 -1 反方向，这道题的核心就行 不断的 判断 numRows 往下遍历，达到 numRows ；零界值 就开始调转方向
            记住，正反向 反方向 调转方向的 目的 都是为了将 数据 存储到对应行上面的 sb 当中

            flag 代表方向 row 代表我们在哪一行，从 0 开始 0 - numRows  --> numRows - 0
         */
        int flag = 1;
        int row = 0;
        int length = s.length();

        for (int i = 0; i < length; i++) {
            sb[row].append(s.charAt(i));

            // 这两种情况的时候 开始转换方向,并且第二次才翻转
            if ((row == 0 || row == numRows - 1) && i > 0) {
                flag = -flag;
            }

            // 去确定具体哪一行
            row += flag;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder stringBuilder : sb) {
            result.append(stringBuilder.toString());
        }

        return result.toString();
    }


    /**
     * 15 - 三数之和
     *
     * 解法：和 有序 两数之和有点类似，我们首先对 数组惊喜排序，
     * 然后 适用 双指针，固定柱中间，然后 我们适用双指针移动 左右两段，去判断即可
     * 就是一题变种的 双数只喝
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            // 肯定已经判定完了
            if (nums[i] > 0) {
                continue;
            }
            // 相同数据，继续往下走
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                // 开始进行值判断
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 注意 假如这里面 left 和 right 的前后值相等，我们也需要进行跳过,这一步非常非常重要
                    while (left < right && nums[left] == nums[left+1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right-1]) {
                        right--;
                    }

                    // 跑完之后 这里需要改变指针，不然就会死循环
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }

        }

        return res;

    }

}
