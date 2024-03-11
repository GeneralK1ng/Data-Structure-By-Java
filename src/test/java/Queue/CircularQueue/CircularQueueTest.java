package Queue.CircularQueue;

import Queue.QueueImpl.CircularQueue.CircularQueueImpl;

public class CircularQueueTest {
    public static void main(String[] args) {
        CircularQueueImpl<Integer> queue = new CircularQueueImpl<>(6);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        queue.print();

        System.out.println("Front: " + queue.getFront());
        System.out.println("Rear: " + queue.getRear());

        System.out.println("Is Full: " + queue.isFull());

        System.out.println("Is Empty: " + queue.isEmpty());
        System.out.println("Size: " + queue.size());

        queue.enqueue(50);
        queue.print();
        queue.enqueue(40);
        queue.print();
    }
}
