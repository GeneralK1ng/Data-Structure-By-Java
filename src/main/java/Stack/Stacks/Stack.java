package Stack.Stacks;

import List.ListsImpl.SinglyLinkedList.SinglyNode;

public interface Stack<T> {

    /**
     * 入栈
     * @param data 压入栈顶的元素
     */
    public void push(T data);

    /**
     * 出栈
     * @return  从栈顶弹出的元素
     */
    public T pop();

    /**
     * 得到栈顶元素
     * @return  栈顶的元素
     */
    public T peek();

    /**
     * 打印栈
     */
    public void print();
}
