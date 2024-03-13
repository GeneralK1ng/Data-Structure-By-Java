package Map.MapsImpl;

import Map.Maps.Map;
import Set.Sets.Set;

public class HashMapImpl<K, V> implements Map<K, V> {

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // After JDK 1.8, the linked list is transformed into a red-black tree
    // when the length of the linked list is greater than 8
    // TODO
    private static final int TREEIFY_THRESHOLD = 8;


    private int getHash(K key) {
        return key.hashCode();
    }

    public HashMapImpl() {
        this.table = new Node[DEFAULT_INITIAL_CAPACITY];
        this.size = 0;
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    public HashMapImpl(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        this.table = new Node[initialCapacity];
        this.size = 0;
        this.threshold = (int) (initialCapacity * DEFAULT_LOAD_FACTOR);
    }

    public HashMapImpl(Map<? extends K, ? extends V> map) {
        this(Math.max((int) (map.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY));
        putAllForMap(map);
    }

    private void putAllForMap(Map<? extends K, ? extends V> map) {
        int numKeysToBeAdded = map.size();
        if (numKeysToBeAdded == 0) {
            return;
        }
        if (table == null) {
            doubleCapacity();
        }
        if (numKeysToBeAdded > threshold) {
            int targetCapacity = (int) (numKeysToBeAdded / DEFAULT_LOAD_FACTOR + 1);
            if (targetCapacity > MAXIMUM_CAPACITY) {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = table.length;
            while (newCapacity < targetCapacity) {
                newCapacity <<= 1;
            }
            if (newCapacity > table.length) {
                resize(newCapacity);
            }
        }
    }

    private void doubleCapacity() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node<K, V>[] newTable = new Node[oldCapacity << 1];
        for (int j = 0; j < oldCapacity; j++) {
            Node<K, V> e = oldTable[j];
            if (e != null) {
                oldTable[j] = null;
                if (e.next == null) {
                    newTable[e.hash & (oldCapacity - 1)] = e;
                } else {
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCapacity) == 0) {
                            if (loTail == null) {
                                loHead = e;
                            } else {
                                loTail.next = e;
                            }
                            loTail = e;
                        } else {
                            if (hiTail == null) {
                                hiHead = e;
                            } else {
                                hiTail.next = e;
                            }
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTable[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTable[j + oldCapacity] = hiHead;
                    }
                }
            }
        }
        table = newTable;
        threshold = (int) (newTable.length * DEFAULT_LOAD_FACTOR);
    }

    private void resize(int newCapacity) {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node<K, V>[] newTable = new Node[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }

    private void transfer(Node<K, V>[] newTable) {
        Node<K, V>[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Node<K, V> e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Node<K, V> next = e.next;
                    int i = e.hash & (newCapacity - 1);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    @Override
    public V put(K key, V value) {
        int hash = getHash(key);
        int index = hash & (table.length - 1);
        Node<K, V> node = table[index];
        if (node == null) {
            node = new Node<>();
            node.key = key;
            node.value = value;
            node.hash = hash;
            table[index] = node;
            size++;
            return null;
        }
        Node<K, V> prev = null;
        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            prev = node;
            node = node.next;
        }
        prev.next = new Node<>();
        prev.next.key = key;
        prev.next.value = value;
        prev.next.hash = hash;
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        int hash = getHash(key);
        int index = hash & (table.length - 1);
        Node<K, V> node = table[index];
        if (node == null) {
            return null;
        }
        if (node.hash == hash && (node.key == key || node.key.equals(key))) {
            table[index] = node.next;
            size--;
            return node.value;
        }
        Node<K, V> prev = node;
        node = node.next;
        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                prev.next = node.next;
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    @Override
    public V get(K key) {
        int hash = getHash(key);
        int index = hash & (table.length - 1);
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = getHash(key);
        int index = hash & (table.length - 1);
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K, V> node : table) {
            while (node != null) {
                if (node.value == value || node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            return put(key, value);
        }
        return get(key);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;
        private int hash;
    }
}
