package Queue.QueueImpl.CircularQueue;

import Queue.Queues.CircularQueue;

import java.util.Objects;

public class CircularQueueImpl<T> implements CircularQueue<T> {

    private CircularQueueNode<T> front; // 队首节点
    private CircularQueueNode<T> rear; // 队尾节点
    private  Integer size; // 循环队列的大小
    private final Integer capacity; // 循环队列的容量

    /**
     * 构造方法，初始化循环队列
     * @param capacity 循环队列的容量
     */
    public CircularQueueImpl(int capacity) {
        this.capacity = capacity;
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public boolean enqueue(T element) {
        CircularQueueNode<T> newNode = new CircularQueueNode<>(element);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
            size++;
        } else if (isFull()) {
            // 队列满时，新元素覆盖旧元素
            front.setData(element);
            // 移动 front 和 rear 指针
            front = front.getNext();
            rear = rear.getNext();
        } else {
            rear.setNext(newNode);
            rear = newNode;
            rear.setNext(front); // 队尾指向队首，形成循环
            size++;
        }
        return true;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T removedElement = front.getData();
        if (front == rear) {
            // 循环队列中只有一个元素
            front = null;
            rear = null;
        } else {
            front = front.getNext();
            rear.setNext(front); // 更新 rear 指向
        }
        size--;
        return removedElement;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            return null;
        }
        return front.getData();
    }

    @Override
    public T getRear() {
        if (isEmpty()) {
            return null;
        }
        return rear.getData();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return Objects.equals(size, capacity);
    }

    @Override
    public int size() {
        return size;
    }

    public void print() {
        CircularQueueNode<T> current = front;
        for (int i = 0; i < size; i++) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
}
