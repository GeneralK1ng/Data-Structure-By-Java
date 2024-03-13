package Set.SetsImpl;

import Set.Sets.Set;

public class HashSetImpl<E> implements Set<E> {

    private Node<E>[] table;
    private int size;
    private int threshold;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1 << 30;


    private int getHash(E e) {
        return e.hashCode();
    }

    public HashSetImpl() {
        this.table = new Node[DEFAULT_CAPACITY];
        this.size = 0;
        this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    public HashSetImpl(int initialCapacity) {
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

    public HashSetImpl(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.table = new Node[initialCapacity];
        this.size = 0;
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    public HashSetImpl(Set<? extends E> set) {
        this(Math.max((int) (set.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_CAPACITY));
        addAll(set);
    }

    private void addAll(Set<? extends E> set) {
        int numKeysToBeAdded = set.size();
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
            if (newCapacity > MAXIMUM_CAPACITY) {
                newCapacity = MAXIMUM_CAPACITY;
            }
            doubleCapacity();
        }
    }

    private void doubleCapacity() {
        Node<E>[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node<E>[] newTable = new Node[oldCapacity << 1];
        for (int j = 0; j < oldCapacity; j++) {
            Node<E> e = oldTable[j];
            if (e != null) {
                oldTable[j] = null;
                do {
                    Node<E> next = e.next;
                    int i = e.hash & (oldCapacity - 1);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    @Override
    public boolean add(E e) {
        int hash = getHash(e);
        int i = hash & (table.length - 1);
        Node<E> node = table[i];
        if (node == null) {
            table[i] = new Node<>();
            table[i].key = e;
            table[i].hash = hash;
            size++;
            return true;
        }
        Node<E> prev = null;
        while (node != null) {
            if (node.key.equals(e)) {
                return false;
            }
            prev = node;
            node = node.next;
        }
        prev.next = new Node<>();
        prev.next.key = e;
        prev.next.hash = hash;
        size++;
        return true;
    }

    @Override
    public boolean remove(E e) {
        int hash = getHash(e);
        int i = hash & (table.length - 1);
        Node<E> node = table[i];
        if (node == null) {
            return false;
        }
        if (node.key.equals(e)) {
            table[i] = node.next;
            size--;
            return true;
        }
        Node<E> prev = node;
        node = node.next;
        while (node != null) {
            if (node.key.equals(e)) {
                prev.next = node.next;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean contains(E e) {
        int hash = getHash(e);
        int i = hash & (table.length - 1);
        Node<E> node = table[i];
        while (node != null) {
            if (node.key.equals(e)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<K> {
        private K key;
        private Node<K> next;
        private int hash;
    }
}
