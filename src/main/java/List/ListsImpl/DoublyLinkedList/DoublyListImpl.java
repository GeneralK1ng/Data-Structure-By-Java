package List.ListsImpl.DoublyLinkedList;

import List.Lists.FuckLinkedList;
import Queue.Queues.NormalQueue;

public class DoublyListImpl<T> implements FuckLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private static Integer size = 0;

    public DoublyListImpl() {
        head = null;
        tail = null;
    }

    // Inserting nodes at the end of a linked table
    public void append(T data) {
        if (head == null) {
            head = new DoublyNode<>(data);
            tail = head;
        } else {
            DoublyNode<T> node = new DoublyNode<>(data);
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

    // Inserting nodes at the head of a linked list
    public void prepend(T data) {
        if (head == null) {
            head = new DoublyNode<>(data);
            tail = head;
        } else {
            DoublyNode<T> node = new DoublyNode<>(data);
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        size++;
    }

    // Delete the first occurrence of the specified value
    public void removeByData(T data) {
        DoublyNode<T> node = head;
        while (node != null) {
            if (node.getData().equals(data)) {
                if (node.getNext() == null && node.getPrev() == null) {
                    // Single node in the list
                    head = null;
                    tail = null;
                } else if (node.getNext() == null) {
                    // Last node in the list
                    node.getPrev().setNext(null);
                    tail = node.getPrev();
                } else if (node.getPrev() == null) {
                    // First node in the list
                    node.getNext().setPrev(null);
                    head = node.getNext();
                } else {
                    // Middle node in the list
                    node.getNext().setPrev(node.getPrev());
                    node.getPrev().setNext(node.getNext());
                }
                size--;
                break;
            }
            node = node.getNext();
        }
    }


    // Deletes the node at the specified position
    public void removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.getNext();
            head.setPrev(null);
        } else if (index == size - 1) {
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            DoublyNode<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
        size--;
    }

    // Adds an element after the specified index
    public void insertAfter(T data, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            DoublyNode<T> node = new DoublyNode<>(data);
            node.setNext(head);
            head.setPrev(node);
            head = node;
        } else if (index == size - 1){
            append(data);
        } else {
            DoublyNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            DoublyNode<T> node = new DoublyNode<>(data);
            node.setNext(temp.getNext());
            node.setPrev(temp);
            temp.setNext(node);
        }
        size++;
    }

    @Override
    public void insertBefore(T data, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            prepend(data);
        } else if (index == size - 1) {
            tail.setNext(new DoublyNode<>(data));
            tail.getNext().setPrev(tail);
            tail = tail.getNext();
        } else {
            DoublyNode<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            DoublyNode<T> node = new DoublyNode<>(data);
            node.setNext(temp.getNext());
            node.setPrev(temp);
            temp.setNext(node);
        }
        size++;
    }

    @Override
    public void replace(T data, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head.setData(data);
        } else if (index == size - 1) {
            tail.setData(data);
        } else {
            DoublyNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            temp.setData(data);
        }
    }

    @Override
    public void print() {
        DoublyNode<T> node = head;
        while (node != null) {
            System.out.print(node.getData().toString() + " ");
            node = node.getNext();
        }
        System.out.println();
    }

    @SafeVarargs
    public static <T> DoublyListImpl<T> listOf(T... elements) {
        DoublyListImpl<T> newList = new DoublyListImpl<>();

        for (T element : elements) {
            newList.append(element);
        }

        return newList;
    }

    public Integer getSize() {
        return size;
    }

}
