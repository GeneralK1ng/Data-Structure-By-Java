package List.Lists;

public interface FuckLinkedList<T> {

    /**
     * 在链表末尾添加元素
     * @param data 要添加的元素
     */
    public void append(T data);

    /**
     * 在链表开头添加元素
     * @param data 要添加的元素
     */
    public void prepend(T data);

    /**
     * 根据value删除第一个出现的元素
     * @param data 要删除的元素
     */
    public void removeByData(T data);

    /**
     * 根据index删除元素
     * @param index 要删除的元素的索引
     */
    public void removeByIndex(int index);

    /**
     * 在指定索引位置插入元素，在索引之后插入
     * @param data 要插入的元素
     * @param index 要插入的元素的索引
     */
    public void insertAfter(T data, int index);

    /**
     * 在指定索引位置插入元素，在索引之前插入
     * @param data 要插入的元素
     * @param index 要插入的元素的索引
     */
    public void insertBefore(T data, int index);

    /**
     * 替换指定索引位置的元素
     * @param data 要替换的元素
     * @param index 要替换的元素的索引
     */
    public void replace(T data, int index);

    /**
     * 打印链表
     */
    public void print();

    public Integer getSize();

    public T get(int index);
}
