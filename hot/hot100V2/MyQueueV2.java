package hot.hot100V2;

import java.util.Stack;

/**
 * 用数组实现队列
 * 用数组实现的话，主要就是需要有两个指针，一个头 head,一个 尾 tail ,这样我们就能知道 从那个地方开始拿元素，还有我们的
 * 元素走到了什么位置
 *
 * 队列：先进先出
 *
 *
 */
class MyQueueV2 {

    private int[] arr;
    private int head;
    private int tail;
    private int size;

    public MyQueueV2(int capacity) {
        arr = new int[capacity + 1];
        this.head = 0;
        this.tail = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == arr.length;
    }

    public void offer(int value) {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        }
        arr[tail] = value;
        tail = (tail + 1) % arr.length;
        size++;
    }

    public int poll() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        int value = arr[head];
        head = (head + 1) % arr.length;
        size--;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return arr[head];
    }

    public int size() {
        return size;
    }

}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */