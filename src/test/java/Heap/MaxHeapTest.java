package Heap;

import java.util.Arrays;
import java.util.Random;

public class MaxHeapTest {
    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);
        MinHeap minHeap = new MinHeap(10);
        Random random = new Random();

        int[] randomArray = new int[10];

        for (int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(100);
            randomArray[i] = randomInt;
            maxHeap.insert(randomInt);
            minHeap.insert(randomInt);
        }

        Arrays.sort(randomArray);
        System.out.println("Random Array: " + Arrays.toString(randomArray));


        System.out.println(maxHeap.getMax());
        System.out.println(minHeap.getMin());
    }
}
