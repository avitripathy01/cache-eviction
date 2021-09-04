package com.cache.ds;

/**
 * This datastructure would store the data in a Double Linked List storage in FIFO fashion
 *
 * @param <E>
 */
public interface DoubleLinkedList<E> {
    boolean addFirst(E e);

    void removeLast();

    void moveFirst(E e);
}
