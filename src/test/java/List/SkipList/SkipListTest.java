package List.SkipList;

import List.Lists.FuckSkipList;
import List.ListsImpl.SkipList.KVPair;
import List.ListsImpl.SkipList.SkipListImpl;

public class SkipListTest {
    public static void main(String[] args) {
        SkipListImpl<Integer, String> skipList = new SkipListImpl<>();
        skipList.add(1, "1");
        skipList.add(2, "2");
        skipList.add(3, "3");
        skipList.add(4, "4");
        skipList.add(5, "5");

        skipList.print();

        skipList.remove(2);

        skipList.print();

        System.out.println(skipList.get(5).getValue());

        SkipListImpl<Integer, String> list = FuckSkipList.listOf(
                new KVPair<>(1, "hello"),
                new KVPair<>(2, "skip"),
                new KVPair<>(3, "list"),
                new KVPair<>(4, "test"),
                new KVPair<>(5, "test"),
                new KVPair<>(6, "test"),
                new KVPair<>(7, "test"),
                new KVPair<>(8, "test"),
                new KVPair<>(9, "test"),
                new KVPair<>(10, "test"),
                new KVPair<>(11, "test"),
                new KVPair<>(12, "test"),
                new KVPair<>(13, "fuck")
        );
        list.print();
        list.remove(13);
        list.print();

        list.forEach( kv -> System.out.println(kv.getKey() + " " + kv.getValue()));
    }
}
