package Set;

import Set.Sets.Set;
import Set.SetsImpl.HashSetImpl;


public class HashSetTest {
    public static void main(String[] args) {
        Set<Integer> set = new HashSetImpl<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        System.out.println(set.size());
        System.out.println(set.contains(1));
        System.out.println(set.contains(2));
        System.out.println(set.contains(3));
        System.out.println(set.contains(4));
        System.out.println(set.contains(5));
        System.out.println(set.contains(6));
        set.remove(1);
        set.remove(2);
        set.remove(3);
        System.out.println(set.contains(1));
        System.out.println(set.contains(2));
        System.out.println(set.contains(3));
        System.out.println(set.contains(4));
        System.out.println(set.contains(5));
        System.out.println(set.contains(6));
        System.out.println(set.isEmpty());
        set.clear();
        System.out.println(set.isEmpty());
    }
}
