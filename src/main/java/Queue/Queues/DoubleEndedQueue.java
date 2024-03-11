package Queue.Queues;


/**
 * 双端队列接口定义
 * @param <T> 双端队列存储的元素类型
 */
public interface DoubleEndedQueue<T> {
    /**
     * 在队头插入元素
     * @param element 要插入的元素
     */
    void addFirst(T element);

    /**
     * 在队尾插入元素
     * @param element 要插入的元素
     */
    void addLast(T element);

    /**
     * 从队头移除并返回元素
     * @return 从队头移除的元素
     */
    T removeFirst();

    /**
     * 从队尾移除并返回元素
     * @return 从队尾移除的元素
     */
    T removeLast();

    /**
     * 获取队头元素（不移除）
     * @return 队头元素
     */
    T peekFirst();

    /**
     * 获取队尾元素（不移除）
     * @return 队尾元素
     */
    T peekLast();

    /**
     * 判断双端队列是否为空
     * @return 如果双端队列为空返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 获取双端队列的大小
     * @return 双端队列的大小
     */
    Integer size();
}
