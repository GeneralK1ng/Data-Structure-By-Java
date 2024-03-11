package Queue.Queues;

/**
 * 优先队列接口定义
 *
 * TODO 后续加入键值对或其他泛型
 * @param <T> 优先队列中存储的元素类型，要求元素类型实现 Comparable 接口
 */
public interface PriorityQueue<T extends Comparable<T>> {

    /**
     * 向优先队列中插入元素
     * @param element 要插入的元素
     */
    void insert(T element);

    /**
     * 获取并移除优先队列中的最小元素
     * @return 优先队列中的最小元素
     * @throws java.util.NoSuchElementException 如果优先队列为空
     */
    T extractMin();

    /**
     * 获取优先队列中的最小元素，但不移除
     * @return 优先队列中的最小元素
     * @throws java.util.NoSuchElementException 如果优先队列为空
     */
    T peekMin();

    /**
     * 判断优先队列是否为空
     * @return 如果优先队列为空返回 true，否则返回 false
     */
    boolean isEmpty();

    /**
     * 获取优先队列的大小
     * @return 优先队列的大小
     */
    int size();

}
