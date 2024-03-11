package Queue.QueueImpl.PriorityQueue;

/**
 * 优先队列节点类
 * @param <T> 节点存储的元素类型
 */
public class PriorityQueueNode<T extends Comparable<T>> {

    private T data; // 节点存储的元素
    private int priority; // 节点的优先级

    /**
     * 构造方法
     * @param data 节点存储的元素
     * @param priority 节点的优先级
     */
    public PriorityQueueNode(T data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    /**
     * 获取节点存储的元素
     * @return 节点存储的元素
     */
    public T getData() {
        return data;
    }

    /**
     * 获取节点的优先级
     * @return 节点的优先级
     */
    public int getPriority() {
        return priority;
    }

    /**
     * 设置节点的优先级
     * @param priority 节点的优先级
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

