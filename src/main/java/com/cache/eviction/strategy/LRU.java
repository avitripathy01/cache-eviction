package com.cache.eviction.strategy;

import java.util.HashMap;
import java.util.Objects;

/**
 * A LRU implementation that holds the most recently accessed/used cache in key, value pairs
 *
 * @param <K>
 * @param <V>
 */
public class LRU<K, V> {

    int DEFAULT_CAPACITY = 2;
    int capacity;
    private DLLClient<CachedKeyValComposite<K, V>> values;
    private HashMap<K, DLLClient.DLL<CachedKeyValComposite<K, V>>> map = new HashMap<>();

    public LRU(int capacity) {
        this.capacity = capacity;
        values = new DLLClient<>(capacity);
    }

    public LRU() {
        values = new DLLClient<>(DEFAULT_CAPACITY);
    }

    public V get(K k) {
        DLLClient.DLL<CachedKeyValComposite<K, V>> cachedNode = map.get(k);
        if (cachedNode != null) {
            values.remove(cachedNode);
            CachedKeyValComposite<K, V> composite = cachedNode.getT();
            values.addFirst(new CachedKeyValComposite<>(k, composite.getValue()));
            return composite.getValue();
        }
        return null;
    }

    public V put(K k, V v) {
        DLLClient.DLL<CachedKeyValComposite<K, V>> cachedNode = map.get(k);
        if (cachedNode != null) {
            values.remove(cachedNode);
        }
        if (values.getSize() == values.getCapacity()) {//remove the last node from Doubly Link list
            DLLClient.DLL<CachedKeyValComposite<K, V>> node =
                    values.remove(values.getTrailer().getPrev());
            K key = node.getT().getKey();
            map.remove(key);
        }
        CachedKeyValComposite<K, V> cachedComposite = new CachedKeyValComposite<>(k, v);
        cachedNode = values.addFirst(cachedComposite);
        map.put(k, cachedNode);
        return cachedComposite.getValue();
    }

    public void reset() {
        if (capacity == 0)
            this.values = new DLLClient<>(DEFAULT_CAPACITY);
        else
            this.values = new DLLClient<>(capacity);
        this.map = new HashMap<>();
    }

    /**
     *  print the content in cache, not necessarily in order
     */
    public void printCache() {
        if (map.size() == 0) {
            System.out.println("Cache is empty");
        }
        map.forEach((k, cachedKeyValCompositeDLL) ->
                System.out.println("Key: " + k + " Value: " + cachedKeyValCompositeDLL.getT().getValue()));
    }

    private static class CachedKeyValComposite<K, V> {
        K key;
        V value;

        public CachedKeyValComposite(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CachedKeyValComposite)) return false;
            CachedKeyValComposite<?, ?> that = (CachedKeyValComposite<?, ?>) o;
            return getKey().equals(that.getKey()) && getValue().equals(that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }
    }

    /**
     * A doubly Linked list client leveraging the {@code DLL} class internally.
     * This would store the {@code CachedKeyValComposite} objects in a doubly linked list fashion
     *
     * @param <T>
     */
    private static class DLLClient<T> {
        DLL<T> header, trailer;
        int size = 0;
        int capacity;


        public DLLClient(int capacity) {
            this.header = new DLL<>(null, null, null);
            this.trailer = new DLL<>(null, null, header);
            this.capacity = capacity;
        }

        public int getSize() {
            return size;
        }

        public int getCapacity() {
            return capacity;
        }

        public DLL<T> getTrailer() {
            return trailer;
        }

        DLL<T> addFirst(T t) {
            DLL<T> node = new DLL<>(t, header.getNext(), header);
            if (header.getNext() != null)
                header.getNext().setPrev(node);
            header.setNext(node);
            if (size == 0) {
                trailer.setPrev(node);
            }
            size++;
            return node;
        }

        public DLL<T> remove(DLL<T> node) {
            DLL<T> prev = node.getPrev();
            DLL<T> next = node.getNext();
            if (prev != null)
                prev.setNext(node.getNext());
            if (next != null)
                next.setPrev(node.getPrev());
            if (prev == header)
                prev.setNext(next);
            if (next == trailer)
                trailer.setPrev(prev);

            size--;
            return node;
        }


        /**
         * Doubly Linked List ADT
         *
         * @param <T>
         */
        private static class DLL<T> {
            T t;
            DLL<T> next, prev;

            public DLL(T t, DLL<T> next, DLL<T> prev) {
                this.t = t;
                this.next = next;
                this.prev = prev;
            }

            public T getT() {
                return t;
            }

            public DLL<T> getNext() {
                return next;
            }

            public void setNext(DLL<T> next) {
                this.next = next;
            }

            public DLL<T> getPrev() {
                return prev;
            }

            public void setPrev(DLL<T> prev) {
                this.prev = prev;
            }


        }

    }
}