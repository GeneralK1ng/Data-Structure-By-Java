package SkipList;

import List.listsImpl.SkipList.KVPair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class KVPairGenerator {

    public static void main(String[] args) {
        generateAndWriteData("data.txt", 1000000); // 生成100万条数据
    }

    private static void generateAndWriteData(String filename, int dataSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Random random = new Random();
            for (int i = 0; i < dataSize; i++) {
                int key = random.nextInt(dataSize); // 生成随机键
                String value = "value_" + i; // 生成对应的值
                KVPair<Integer, String> kvPair = new KVPair<>(key, value);
                writer.write(kvPair.getKey() + "," + kvPair.getValue() + "\n");
            }
            System.out.println("Data generation complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
