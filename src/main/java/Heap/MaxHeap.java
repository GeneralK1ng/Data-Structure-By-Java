package Heap;


public class MaxHeap {
    private final Heap heap;

    public MaxHeap(Integer capacity) {
        this.heap = new Heap(capacity, HeapType.MAX_HEAP);

    }

    public void insert(Integer element) {
        this.heap.insert(element);
    }

    public void extractMax() {
        this.heap.extract();
    }

    public Integer getMax() {
        return this.heap.peek();
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }
}
