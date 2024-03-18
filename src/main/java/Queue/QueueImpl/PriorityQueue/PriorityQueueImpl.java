package Queue.QueueImpl.PriorityQueue;

import Queue.Queues.PriorityQueue;

import java.util.Arrays;

/**
 * 优先队列的实现类，基于二叉堆
 * @param <T> 优先队列中存储的元素类型，要求元素类型实现 Comparable 接口
 */
public class PriorityQueueImpl<T extends Comparable<T>> implements PriorityQueue<T> {
    // 二叉堆数组
    private PriorityQueueNode<T>[] heap; //TODO 后续写到二叉树的时候补上
    // 优先队列的大小
    private static Integer size;

    /**
     * 构造方法，初始化优先队列
     * @param capacity 初始容量
     */
    public PriorityQueueImpl(int capacity) {
        heap = new PriorityQueueNode[capacity];
        size = 0;
    }

    /**
     * 默认构造方法，初始化优先队列
     */
    public PriorityQueueImpl() {
        this(10); // 默认初始容量为10
    }

    @Override
    public void insert(T element) {
        if (size == heap.length - 1) {
            resizeHeap();
        }

        // 创建新节点并将其插入到末尾
        PriorityQueueNode<T> node = new PriorityQueueNode<>(element, Integer.MIN_VALUE);
        heap[++size] = node;

        // 上滤操作，维护堆
        percolateUp(size);

    }

    private void percolateUp(Integer index) {
        PriorityQueueNode<T> temp = heap[index];

        while (index > 1 && temp.getData().compareTo(heap[index / 2].getData()) < 0) {
            heap[index] = heap[index / 2];
            index /= 2;
        }

        heap[index] = temp;
    }

    /**
     * 扩容
     */
    private void resizeHeap() {
        PriorityQueueNode<T>[] newHeap = new PriorityQueueNode[heap.length * 2];
        System.arraycopy(heap, 1, newHeap, 1, size);
        heap = newHeap;
    }

    @Override
    public T extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("The Priority Queue is null");
        }

        // 获取数据
        T data = heap[1].getData();

        // 赋值最后一个节点的值给根节点
        heap[1] = heap[size--];

        // 下滤操作
        percolateDown(1);

        return data;
    }

    private void percolateDown(int index) {
        int child;
        PriorityQueueNode<T> temp = heap[index];

        while (index * 2 <= size){
            child = index * 2;

            // 找到较小子节点
            if (child != size && heap[child + 1].getData().compareTo(heap[child].getData()) < 0) {
                child++;
            }

            // 如果较小子节点小于父节点，交换操作
            if (heap[child].getData().compareTo(temp.getData()) < 0) {
                heap[index] = heap[child];
            } else {
                break;
            }

            index = child;
        }

        heap[index] = temp;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new IllegalStateException("The Priority Queue is null");
        }

        return heap[1].getData();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void print() {
        for (int i = 1; i <= size; i++) {
            System.out.print(heap[i].getData() + " ");
        }
        System.out.println();
    }

}
