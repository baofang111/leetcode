package dui;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 * <p>
 * 题目意思：找到 数据流中的中位数
 * <p>
 * 题目解析：我们使用两个堆，最大堆 + 最小堆
 * 最大堆，存储 小于的那些数据，比如 12 56 最大堆存储 2 1
 * 最小堆，存储 大于的那些数据，比如 12 56 最小堆存储 5 6
 * 这样我们就好拿了，如果 最大堆.size > minHeap.size = maxHeap.peek
 * 不然的话就是 (maxHeap.peek + minHeap.peel) / 2
 *
 * @summary MedianFinderV3
 * @author: bf
 * @Copyright (c) © 拜耳作物科学（中国）有限公司
 * @since: 2026/04/11 12:39:15
 */
public class MedianFinderV3 {

    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    public MedianFinderV3() {
        minHeap = new PriorityQueue<>((a, b) -> (a - b));
        maxHeap = new PriorityQueue<>((a, b) -> (b - a));
    }

    public void addNum(int num) {
        // 举个例子，先入堆，进最小栈，然后我们从 最小堆中拿最大
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());

        // 保证最大堆的数量 > 最小堆的数量
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
