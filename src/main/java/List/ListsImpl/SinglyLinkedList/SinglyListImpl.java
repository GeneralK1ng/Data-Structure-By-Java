package List.ListsImpl.SinglyLinkedList;


import List.Lists.FuckLinkedList;

public class SinglyListImpl<T> implements FuckLinkedList<T> {

    // head node
    private SinglyNode<T> head;

    // size
    private static Integer size = 0;

    public SinglyListImpl() {
        this.head = null;
    }

    // Inserting nodes at the end of a linked table
    public void append(T data) {
        SinglyNode<T> node = new SinglyNode<>(data);
        if (head == null) {
            head = node;
        } else {
            SinglyNode<T> temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(node);
        }
        size++;
    }

    // Inserting nodes at the head of a linked list
    public void prepend(T data) {
        SinglyNode<T> node = new SinglyNode<>(data);
        node.setNext(head);
        head = node;
        size++;
    }

    // Delete the first occurrence of the specified value
    public void removeByData(T data) {
        if (head == null) {
            return;
        }
        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return;
        }
        SinglyNode<T> temp = head;
        while (temp.getNext() != null) {
            if (temp.getNext().getData().equals(data)) {
                temp.setNext(temp.getNext().getNext());
                size--;
                return;
            }
            temp = temp.getNext();
        }
    }

    // Deletes the node at the specified position
    public void removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.getNext();
            size--;
            return;
        }
        SinglyNode<T> temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.getNext();
        }
        temp.setNext(temp.getNext().getNext());
        size--;
    }

    // Adds an element after the specified index
    public void insertAfter(T data, int index) {
        if (index < 0 || index >= size) {
            return;
        }

        SinglyNode<T> node = new SinglyNode<>(data);

        if (index == 0) {
            node.setNext(head);
            head = node;
            size++;
            return;
        }

        SinglyNode<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        node.setNext(temp.getNext());
        temp.setNext(node);
        size++;
    }

    // Adds an element before the specified index
    public void insertBefore(T data, int index) {
        if (index < 0 || index >= size) {
            return;
        }

        SinglyNode<T> node = new SinglyNode<>(data);

        if (index == 0) {
            node.setNext(head);
            head = node;
            size++;
            return;
        }

        SinglyNode<T> temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.getNext();
        }

        node.setNext(temp.getNext());
        temp.setNext(node);
        size++;
    }

    // Replaces the element at the specified index position
    public void replace(T data, int index) {
        if (index < 0 || index >= size) {
            return;
        }
        SinglyNode<T> node = new SinglyNode<>(data);
        if (index == 0) {
            node.setNext(head);
            head = node;
            size++;
            return;
        }

        SinglyNode<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        temp.setData(data);
    }


    // print a linked list
    public void print() {
        if (head == null) {
            return;
        }
        SinglyNode<T> temp = head;
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    // listOf
    @SafeVarargs
    public static <T> SinglyListImpl<T> listOf(T... elements) {
        SinglyListImpl<T> newList = new SinglyListImpl<>();

        for (T element : elements) {
            newList.append(element);
        }

        return newList;
    }


    public Integer getSize() {
        return size;
    }

}
