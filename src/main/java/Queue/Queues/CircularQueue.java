package Queue.Queues;

/**
 * 循环队列接口定义
 * @param <T> 循环队列中储存元素的元素类型
 */
public interface CircularQueue<T> {


    /**
     * 向循环队列中插入元素
     * @param element 要插入的元素
     * @return 如果插入成功返回true，否则返回false
     */
    boolean enqueue(T element);

    /**
     * 从循环队列中移除并返回元素
     * @return 循环队列中移除的元素，如果队列为空返回 null
     */
    T dequeue();

    /**
     * 获取队首元素（不移除）
     * @return 队首元素，如果队列为空则返回 null
     */
    T getFront();

    /**
     * 获取队尾元素（不移除）
     * @return 队尾元素，如果队列为空返回 null
     */
    T getRear();

    /**
     * 判断循环队列是否为空
     * @return 如果循环队列为空返回 true，否则返回 false
     */
    boolean isEmpty();

    /**
     * 判断循环队列是否已满
     * @return 如果循环队列已满返回 true，否则返回 false
     */
    boolean isFull();

    /**
     * 获取循环队列的大小
     * @return 循环队列的大小
     */
    int size();
}
