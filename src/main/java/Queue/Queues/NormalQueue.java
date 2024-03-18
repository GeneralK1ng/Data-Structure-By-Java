package Queue.Queues;

public interface NormalQueue<T> {
    /**
     * 入队
     * @param data 数据
     * @return 是否成功
     */
    boolean add(T data);

    /**
     * 出队
     * @return 出队元素
     */
    T remove();

    /**
     * 获取队首元素（不出队）
     * @return 队首元素
     */
    T peek();

    int size();

    boolean isEmpty();
}
