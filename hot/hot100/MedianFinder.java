package hot.hot100;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 *
 * 题目意思：给定一个数据流，让我们判断他的中位数
 *
 * 题目解析：依旧是 一个最大堆，一个最小堆
 * 我们使用 最大堆存 小值
 * 最小堆 存 大值
 * 这样 我们就能使用 最大堆的 堆顶 + 最小堆的堆顶 得到我们要的值
 *
 *
 * @summary MedianFinder
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/20 20:15:27
 */
public class MedianFinder {

    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;


    public MedianFinder() {
        minHeap = new PriorityQueue<>((a, b) -> a - b);
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
    }

    /**
     * 2 3 4 5
     */
    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());

        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }

        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
