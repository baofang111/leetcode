package hot.hot100V2;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 *
 * 题目意思：给定一个数据流，让我们计算他的中位数
 *
 * 题目解析：这里我们需要使用 两个堆，一个最小堆，一个最大堆
 * 最小堆 存的都是更大的数据
 * 最大堆 存的都是更小的数据
 * 我按照 1 2 3 4 5 6 来举例子
 * 最小堆： 6 5 4
 * 最大堆： 1 2 3
 * 这样，当 最大堆数量 = 最小堆数量的时候，直接都取顶部元素 （num1 + num2）/ 2 即可
 * 如果是奇数的情况 那么我们将 最小堆或者最大堆 多放一个，比如
 * 最小堆：7 6 5 4
 * 最大堆：1 2 3
 * 这样，我们直接取 最小堆里面的数据就行了
 *
 *
 *
 * @summary MedianFinder
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/05/08 20:24:49
 */
public class MedianFinder {

    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;


    public MedianFinder() {
        minHeap = new PriorityQueue<>((a, b) -> a - b);
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
    }

    /**
     * 先放最小堆
     * 再放最大堆
     */
    public void addNum(int num) {
        minHeap.offer(num);
        maxHeap.offer(minHeap.poll());

        if (maxHeap.size() > minHeap.size()) {
            // 将最大堆的一个数据，再放入最小堆当中
            minHeap.offer(maxHeap.poll());
        }
    }

    public double findMedian() {
       // 判断是奇数还是偶数
        if (minHeap.size() > maxHeap.size()) {
            return minHeap.peek();
        }

        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }

}
