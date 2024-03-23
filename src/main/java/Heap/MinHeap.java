package Heap;

public class MinHeap {
    private final Heap heap;

    /**
     * 构造一个最小堆对象。
     *
     * @param capacity 堆的容量，即堆能够存储的最大元素数量。
     */
    public MinHeap(Integer capacity) {
        // 使用指定容量和最小堆策略创建堆对象
        this.heap = new Heap(capacity, HeapType.MIN_HEAP);
    }

    /**
     * 向堆中插入一个元素。
     * @param element 需要插入的元素。
     */
    public void insert(Integer element) {
        // 将元素插入到堆中
        heap.insert(element);
    }

    /**
     * 从堆中提取最小值。
     * 该方法会移除堆中的最小元素，并返回该元素的值。
     * 注意：此方法依赖于heap对象的extract()方法来实现具体的功能。
     *
     * @return 返回从堆中提取的最小值。
     */
    public int extractMin() {
        return heap.extract(); // 从heap中提取最小值并返回
    }

    /**
     * 获取最小值
     * 该方法不接受参数。
     *
     * @return 返回堆中最小的元素。如果堆为空，则行为未定义。
     */
    public int getMin() {
        // 返回堆顶元素，即最小值
        return heap.peek();
    }

    /**
     * 判断堆是否为空。
     *
     * @return 如果堆为空，则返回true，否则返回false。
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
