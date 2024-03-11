package Queue.QueueImpl.CircularQueue;

public class CircularQueueNode<T> {

    private T data; // 节点存储的元素
    private CircularQueueNode<T> next; // 指向下一个节点的引用

    /**
     * 构造方法
     * @param data 节点存储的元素
     */
    public CircularQueueNode(T data) {
        this.data = data;
        this.next = null;
    }

    /**
     * 获取节点存储的元素
     * @return 节点存储的元素
     */
    public T getData() {
        return data;
    }

    /**
     * 获取下一个节点的引用
     * @return 下一个节点的引用
     */
    public CircularQueueNode<T> getNext() {
        return next;
    }

    /**
     * 设置下一个节点的引用
     * @param next 下一个节点的引用
     */
    public void setNext(CircularQueueNode<T> next) {
        this.next = next;
    }

    /**
     * 设置节点存储的元素
     * @param data 节点存储的元素
     */
    public void setData(T data) {
        this.data = data;
    }
}
