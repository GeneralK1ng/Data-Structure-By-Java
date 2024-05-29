package Tree.RBTree;

import Tree.Trees.Tree;
import Tree.TreesImpl.RBTreesImpl.RedBlackTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RBTreeTest {
    public static void main(String[] args) {
        String filename = "data.txt";
        String queryKey = "268676464";

        performQueryPressureTest(filename, queryKey);
    }



    private static void performQueryPressureTest(String filename, String queryKey) {

        long readStartTime = System.currentTimeMillis();
        Tree<Integer, String> redBlackTree = readDataFromFile(filename);
        long readEndTime = System.currentTimeMillis();
        System.out.println("Read data from file " + filename + " in " + (readEndTime - readStartTime) / 1000 + " seconds");
        System.out.println("Tree size: " + redBlackTree.getSize());
        long startTime = System.currentTimeMillis();
        String result = redBlackTree.get(Integer.parseInt(queryKey));
        long endTime = System.currentTimeMillis();

        long getLevelStartTime = System.currentTimeMillis();
        Integer level = redBlackTree.getLevel(Integer.parseInt(queryKey));
        long getLevelEndTime = System.currentTimeMillis();

        System.out.println("Query result for key " + queryKey + ": " + result);
        System.out.println("Query execution time: " + (endTime - startTime) + " ms");
        System.out.println("Get level execution time: " + (getLevelEndTime - getLevelStartTime) + " ms" + "level: " + level);
    }

    private static Tree<Integer, String> readDataFromFile(String filename) {
        Tree<Integer, String> redBlackTree = new RedBlackTree<>();
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int key = Integer.parseInt(parts[0]);
                    String value = parts[1];
                    count++;
                    redBlackTree.insert(key, value);
                }
            }
            System.out.println("Read " + count + " lines from file " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return redBlackTree;
    }
}
