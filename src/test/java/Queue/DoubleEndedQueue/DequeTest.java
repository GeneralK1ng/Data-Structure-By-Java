package Queue.DoubleEndedQueue;

import Queue.QueueImpl.DoubleEndedQueue.DequeImpl;

public class DequeTest {
    public static void main(String[] args) {
        DequeImpl<Integer> deque = new DequeImpl<>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);

        deque.print();

        System.out.println(deque.removeFirst());
        deque.print();

        System.out.println(deque.removeLast());
        deque.print();

        System.out.println(deque.peekFirst());
        System.out.println(deque.peekLast());
    }
}
