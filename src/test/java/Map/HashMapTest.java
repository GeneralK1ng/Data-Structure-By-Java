package Map;

import Map.Maps.Map;
import Map.MapsImpl.HashMapImpl;

public class HashMapTest {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMapImpl<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map.put(4, 5);
        map.put(5, 6);
        System.out.println(map.size());
        System.out.println(map.containsKey(1));
        System.out.println(map.containsKey(2));
        System.out.println(map.containsKey(3));
        System.out.println(map.containsKey(4));
        System.out.println(map.containsKey(5));
        System.out.println(map.containsKey(6));
        map.put(1, 3);
        System.out.println(map.get(1));
        map.putIfAbsent(1, 2);
        System.out.println(map.get(1));
        map.remove(1);
        System.out.println(map.size());
        System.out.println(map.containsValue(2));
        System.out.println(map.containsValue(3));
        map.clear();
        System.out.println(map.isEmpty());
    }
}
