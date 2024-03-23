package Heap;



public class Heap {

    private final Integer[] data; // 存放堆数据的数组

    private Integer size; // 堆中元素的个数

    private final Integer capacity; // 堆的容量
    private final HeapType heapType;


    /**
     * 构造一个堆对象。
     * @param capacity 堆的容量，即堆能够存储的最大元素数量。
     *
     */
    public Heap(Integer capacity, HeapType heapType) {
        this.capacity = capacity;
        this.size = 0;
        this.data = new Integer[capacity];
        this.heapType = heapType;
    }


    /**
     * 获取父节点的索引。
     * @param index 子节点的索引。
     * @return 父节点的索引。
     */
    private Integer parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * 计算给定索引的左子节点的索引。
     *
     * @param index 父节点的索引。
     * @return 左子节点的索引。如果左子节点不存在，则返回相应的计算值，可能超出实际数组范围。
     */
    private Integer leftChild(int index) {
        return 2 * index + 1;
    }
    /**
     * 计算给定索引的右子节点的索引。
     *
     * @param index 父节点的索引。
     * @return 右子节点的索引。如果右子节点不存在，则返回相应的计算值，可能超出实际数组范围。
     */
    private Integer rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * 向堆中插入一个元素。
     * 抛出运行时异常，如果堆已满。
     *
     * @param element 要插入堆中的元素。
     */
    public void insert(Integer element) {
        // 检查堆是否已满
        if (size >= capacity) {
            throw new RuntimeException("Heap is Full!");
        }

        int idx = size;
        data[idx] = element; // 将元素插入到堆的末尾
        size++; // 更新堆的大小
        heapifyUp(idx); // 调整堆，保持其结构
    }

    /**
     * 将指定元素向上调整以维持堆的性质。
     * @param idx 需要向上调整的元素的索引。
     * 该方法不返回任何值。
     */
    private void heapifyUp(int idx) {
        while (idx > 0 && compare(data[parent(idx)], data[idx])) {
            swap(idx, parent(idx));
            idx = parent(idx);
        }
    }



    /**
     * 交换数组中指定位置的元素。
     * @param i 第一个要交换的元素的索引
     * @param j 第二个要交换的元素的索引
     * 该方法不返回任何内容。
     */
    private void swap(int i, int j) {
        // 使用临时变量存储第一个元素，以避免覆盖
        Integer temp = data[i];
        // 将第二个元素的值赋给第一个位置
        data[i] = data[j];
        // 将之前保存的第一个元素的值赋给第二个位置
        data[j] = temp;
    }

    /**
     * 将指定索引的元素下沉以维护堆的性质。此方法用于调整堆，确保从指定索引开始的子树满足最大堆或最小堆的条件。
     * @param idx 需要下沉的元素的索引。
     */
    private void heapifyDown(int idx) {
        int selectedChild = idx;
        int left = leftChild(idx);
        int right = rightChild(idx);

        if (left < size && compare(data[left], data[selectedChild])) {
            selectedChild = left;
        }

        if (right < size && compare(data[right], data[selectedChild])) {
            selectedChild = right;
        }

        if (selectedChild != idx) {
            swap(idx, selectedChild);
            heapifyDown(selectedChild);
        }
    }

    private boolean compare(int a, int b) {
        return heapType == HeapType.MAX_HEAP ? a < b : a > b;
    }


    /**
     * 从堆中提取最大元素（根元素）并返回该元素。
     * 提取元素后，堆大小减一，并重新调整堆以保持其性质。
     * 如果堆为空，则抛出运行时异常。
     * @return 堆中的最大元素（根元素）。
     * @throws RuntimeException 如果堆为空。
     */
    public int extract(){
        // 检查堆是否为空
        if (size <= 0) {
            throw new RuntimeException("Heap is Empty!");
        }
        int root = data[0];  // 保存根元素

        // 将最后一个元素移动到根位置，以替代被提取的根元素
        data[0] = data[size - 1];

        size--;  // 堆大小减一
        // 重新调整新根元素以满足堆的性质
        heapifyDown(0);
        return root;  // 返回提取的根元素
    }

    /**
     * 返回堆中的最大元素（根元素）。
     * 如果堆为空，则抛出运行时异常。
     * @return 堆中的最大元素（根元素）。
     * @throws RuntimeException 如果堆为空。
     */
    public int peek() {
        // 检查堆是否为空
        if (size <= 0) {
            throw new RuntimeException("Heap is Empty!");
        }

        return data[0];
    }

    /**
     * 检查堆是否为空。
     * @return 堆是否为空。
     */
    public boolean isEmpty() {
        return size == 0;
    }

}
