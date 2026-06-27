package hot.hot100V2;

import java.util.ArrayDeque;

/**
 * 225. 用队列实现栈
 *
 * 队列：FIFO 先进先出
 * 栈：后进先出
 */
class MyStack {

    ArrayDeque<Integer> deque;

    public MyStack() {
        deque = new ArrayDeque<>();
    }
    
    public void push(int x) {
        deque.offer(x);
        int size = deque.size();
        for (int i = 0; i < size - 1; i++) {
            deque.offer(deque.poll());
        }
    }
    
    public int pop() {
        return deque.poll();
    }
    
    public int top() {
        return deque.peek();
    }
    
    public boolean empty() {
        return deque.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */