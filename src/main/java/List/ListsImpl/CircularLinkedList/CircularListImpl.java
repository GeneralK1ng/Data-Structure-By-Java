package List.ListsImpl.CircularLinkedList;

import List.Lists.FuckLinkedList;

public class CircularListImpl<T> implements FuckLinkedList<T> {
    private CircularNode<T> head;
    private CircularNode<T> tail;
    private static Integer size = 0;

    public CircularListImpl() {
        head = null;
        tail = null;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public T get(int index) {
        return null;
    }

    // 静态方法，用于直接创建链表
    @SafeVarargs
    public static <T> CircularListImpl<T> listOf(T... elements) {
        CircularListImpl<T> newList = new CircularListImpl<>();

        for (T element : elements) {
            newList.append(element);
        }

        return newList;
    }

    @Override
    public void append(T data) {
        if (head == null) {
            head = new CircularNode<>(data);
            tail = head;
            head.setNext(tail);
            head.setPrev(tail);
            tail.setNext(head);
            tail.setPrev(head);
        } else {
            CircularNode<T> node = new CircularNode<>(data);
            tail.setNext(node);
            node.setPrev(tail);
            node.setNext(head);
            tail = node;
            head.setPrev(tail); // 保证头节点的prev指向尾节点
        }
        size++;
    }


    @Override
    public void prepend(T data) {
        if (head == null) {
            // 如果链表为空，创建一个新节点，并将其设置为头尾节点
            head = new CircularNode<>(data);
            tail = head;
            head.setNext(tail);
            head.setPrev(tail);
            tail.setNext(head);
            tail.setPrev(head);
        } else {
            // 如果链表不为空，创建一个新节点
            CircularNode<T> node = new CircularNode<>(data);

            // 更新链接，将新节点插入到链表开头
            node.setNext(head);
            node.setPrev(tail);
            head.setPrev(node);
            tail.setNext(node);

            // 更新头节点为新节点
            head = node;
        }
        size++;
    }


    @Override
    public void removeByData(T data) {
        if (head == null) {
            // 如果链表为空，无需删除
            return;
        }

        // 查找包含指定数据的节点
        CircularNode<T> current = head;
        do {
            if (current.getData().equals(data)) {
                // 找到了匹配的数据，进行删除操作
                if (current == head) {
                    // 如果匹配的是头节点，更新头尾节点的链接
                    head = current.getNext();
                    tail.setNext(head);
                    head.setPrev(tail);
                } else if (current == tail) {
                    // 如果匹配的是尾节点，更新头尾节点的链接
                    tail = current.getPrev();
                    tail.setNext(head);
                    head.setPrev(tail);
                } else {
                    // 如果匹配的是中间节点，更新前后节点的链接
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                size--;
                return; // 删除完成后直接返回
            }
            current = current.getNext();
        } while (current != head); // 循环直到回到头节点

        // 如果循环结束仍未找到匹配的数据，说明链表中不存在该数据
        // 可在此处添加相应的处理逻辑，比如抛出异常或输出提示信息
        throw new RuntimeException("The data is not found in the list");
    }


    @Override
    public void removeByIndex(int index) {
        if (head == null || index < 0 || index >= size) {
            // 如果链表为空或索引无效，无需删除
            throw new RuntimeException("The index is invalid");
        }

        if (index == 0) {
            // 删除头节点
            if (size == 1) {
                // 如果链表只有一个节点，直接置空头尾节点
                head = null;
                tail = null;
            } else {
                // 更新头尾节点的链接
                head = head.getNext();
                tail.setNext(head);
                head.setPrev(tail);
            }
        } else if (index == size - 1) {
            // 删除尾节点
            tail = tail.getPrev();
            tail.setNext(head);
            head.setPrev(tail);
        } else {
            // 删除中间节点
            CircularNode<T> current = getNodeByIndex(index);
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }

        size--;
    }

    private CircularNode<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            // 如果索引无效，抛出异常
            throw new RuntimeException("The index is invalid");
        }

        // 从头节点开始，循环找到指定索引的节点
        CircularNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }


    @Override
    public void insertAfter(T data, int index) {
        if (index < 0 || index >= size) {
            // 如果索引无效，无法插入
            throw new RuntimeException("The index is invalid");
        }

        CircularNode<T> newNode = new CircularNode<>(data);

        if (index == size - 1) {
            // 在尾节点后插入新节点
            newNode.setNext(head);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            head.setPrev(newNode);
            tail = newNode; // 更新尾节点为新节点
        } else {
            // 在中间节点后插入新节点
            CircularNode<T> current = getNodeByIndex(index);
            newNode.setNext(current.getNext());
            newNode.setPrev(current);
            current.getNext().setPrev(newNode);
            current.setNext(newNode);
        }

        size++;
    }


    @Override
    public void insertBefore(T data, int index) {
        if (index < 0 || index >= size) {
            // 如果索引无效，无法插入
            throw new RuntimeException("The index is invalid");
        }

        CircularNode<T> newNode = new CircularNode<>(data);

        if (index == 0) {
            // 在头节点前插入新节点
            newNode.setNext(head);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            head.setPrev(newNode);
            head = newNode; // 更新头节点为新节点
        } else {
            // 在中间节点前插入新节点
            CircularNode<T> current = getNodeByIndex(index);
            newNode.setNext(current);
            newNode.setPrev(current.getPrev());
            current.getPrev().setNext(newNode);
            current.setPrev(newNode);
        }

        size++;
    }

    @Override
    public void replace(T data, int index) {
        if (index < 0 || index >= size) {
            // 如果索引无效，无法替换
            throw new RuntimeException("The index is invalid");
        }

        CircularNode<T> current = getNodeByIndex(index);
        current.setData(data);
    }


    @Override
    public void print() {
        if (head == null) {
            System.err.println("The list is empty.");
            return;
        }

        CircularNode<T> current = head;
        do {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        } while (current != head);
        System.out.println();
    }

    public void reversePrint(CircularListImpl<T> list) {
        if (list == null) {
            System.out.println("The list is empty.");
            return;
        }

        CircularNode<T> current = tail; // 从尾节点开始遍历

        do {
            System.out.print(current.getData() + " ");
            current = current.getPrev();
        } while (current != tail);
        System.out.println();
    }

}
