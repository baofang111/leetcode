package dui;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 *
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 *
 * 核心思想：
 * 将 数据流拆成 左右两半
 * 左边最大，右边最小，中位数就在交界处
 *
 * 所以这里使用两个堆，一个最小堆，一个最大堆
 * 最大堆：存 较小的一半，堆顶 = 左半边最大值
 * 最小堆：存 较大的一半，堆顶 = 右半边最小值
 *
 * 因为这样的话，我到时候取两个的堆顶数据，取的就是中位数了
 *
 * 这时候 中位数就一定在堆顶
 * 奇数：maxHeap.peek
 * 偶数：(minHeap.peek + maxHeap.peek) / 2
 *
 */
class MedianFinder {

    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;


    /**
     * 始化 MedianFinder 对象。
     */
    public MedianFinder() {
        // 初始化最大堆 + 最小堆
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    /**
     * 将数据流中的整数 num 添加到数据结构中。
     * @param num
     */
    public void addNum(int num) {
        // 先放最大堆
        maxHeap.offer(num);

        // 然后把最大堆的最小值放入到最小堆里面
        minHeap.offer(maxHeap.poll());

        // 保证最大堆的数量 > 最小推，这一步为了保证 当是奇数的时候 一定从 maxHeap中取
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
     * @return
     */
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }

        return (minHeap.peek() + maxHeap.peek()) / 2.0;
    }
}
