package List.SkipList;

import List.ListsImpl.SkipList.KVPair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class KVPairGenerator {

    public static void main(String[] args) {
        generateAndWriteData("data.txt", 30000000); // 生成100万条数据
    }

    private static void generateAndWriteData(String filename, int dataSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Random random = new Random();
            for (int i = 0; i < dataSize; i++) {
                String value = "value_" + random.nextInt(dataSize); // 随机值
                KVPair<Integer, String> kvPair = new KVPair<>(i * (random.nextInt(20) + 1), value);
                writer.write(kvPair.getKey() + "," + kvPair.getValue() + "\n");
            }
            System.out.println("Data generation complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
