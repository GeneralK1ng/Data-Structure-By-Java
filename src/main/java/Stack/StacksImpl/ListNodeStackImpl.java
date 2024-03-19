package Stack.StacksImpl;

import List.ListsImpl.SinglyLinkedList.SinglyNode;
import Stack.Stacks.Stack;

import java.util.EmptyStackException;

public class ListNodeStackImpl<T> implements Stack<T> {

    //Stack top
    private SinglyNode<T> top;

    //Count of elements in stack
    private int count = 0;

    //size
    //private static Integer size = 0;

    public ListNodeStackImpl(){
        this.top = null;
    }

    //Push node into stack
    public void push(T data){
        SinglyNode<T> node = new SinglyNode<>(data);
        if (top == null){
            top = node;
        } else {
            node.setNext(top);
            top =node;
        }
        count++;
    }

    //Pop node from stack
    public T pop() {
        if (count == 0){
            throw new RuntimeException("stack is empty!");
        }
        SinglyNode <T> popNode = top;
        top =top.getNext();
        count--;
        return popNode.getData();
    }

    //Get the top element without pop it out.
    public T peek() {
        if (count == 0) throw new EmptyStackException();
        return top.getData();
    }

    public void print() {
        if (top == null){
            System.out.println("Top->[ ]->Bottom -stack is empty");
            return;
        }
        SinglyNode<T> current = top;
        System.out.print("Top->[");
        while (current != null){
            System.out.print(current.getData()+" ");
            current = current.getNext();
        }
        System.out.println("]->Bottom -size = "+count);
    }

    @Override
    public boolean empty() {
        return count == 0;
    }
}
