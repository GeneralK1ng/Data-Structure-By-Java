package SkipList;

import List.listsImpl.SkipList.SkipListImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KVPairSearchTest {

    public static void main(String[] args) {
        String filename = "data.txt";
        String queryKey = "change Ur key";

        performQueryPressureTest(filename, queryKey);
    }



    private static void performQueryPressureTest(String filename, String queryKey) {

        long readStartTime = System.currentTimeMillis();
        SkipListImpl<Integer, String> skipList = readDataFromFile(filename);
        long readEndTime = System.currentTimeMillis();
        System.out.println("Read data from file " + filename + " in " + (readEndTime - readStartTime) / 1000 + " seconds");

        long startTime = System.currentTimeMillis();
        String result = skipList.get(Integer.parseInt(queryKey)).getValue();
        long endTime = System.currentTimeMillis();

        System.out.println("Query result for key " + queryKey + ": " + result);
        System.out.println("Query execution time: " + (endTime - startTime) /1000 + " seconds");
    }

    private static SkipListImpl<Integer, String> readDataFromFile(String filename) {
        SkipListImpl<Integer, String> skipList = new SkipListImpl<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int key = Integer.parseInt(parts[0]);
                    String value = parts[1];
                    skipList.add(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skipList;
    }

}
