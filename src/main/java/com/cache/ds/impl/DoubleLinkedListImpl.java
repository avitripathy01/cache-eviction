package com.cache.ds.impl;

import com.cache.ds.DoubleLinkedList;

public final class DoubleLinkedListImpl<E> implements DoubleLinkedList<E> {

    int size;
    int availableElements;
    DoubleLinkedListImpl.Node<E> first, last;

    private DoubleLinkedListImpl() {
    }

    /**
     * Static factory for initialisation
     *
     * @param listSize define the size when creating an instance
     * @return a new instance of com.cache.ds.impl.DoubleLinkedListImpl with a size specified by the given input param
     */
    public static DoubleLinkedListImpl getInstanceOf(int listSize) {
        var dll = new DoubleLinkedListImpl();
        dll.size = listSize;
        dll.availableElements = 0;
        dll.first = null;
        dll.last = null;
        return dll;
    }


    private boolean isStorageFull() {
        return this.availableElements == this.size;
    }

    private boolean isEmptyStorage() {
        return this.availableElements == 0;
    }

    /**
     * Add an element to the head of the linked list.
     *
     * @param e add an input parameter to the Linked List
     * @return the instance of this list
     */
    @Override
    public boolean addFirst(E e) {
        boolean elementAdded = false;

        if (!isStorageFull()) {

            if (this.first == null) {
                this.first = new Node<E>(e, null, null);
                this.last = this.first;
            } else {
                DoubleLinkedListImpl.Node firstNode = new Node<E>(e, this.first, null);
                this.first.setPrevious(firstNode);
                this.first = firstNode;
            }
            this.availableElements++;
            elementAdded = true;
        }
        return elementAdded;
    }

    /**
     * Delete the last element from the linked list
     */
    @Override
    public void removeLast() {
        if (!isEmptyStorage()) {
            var last = this.last;
            this.last = this.last.prev;
            last = null;
            this.availableElements = availableElements - 1;
            if (this.availableElements == 0)
                this.first = null;
        }

    }

    /**
     * Move an element from its current to the first position in the data structure when accessed
     *
     * @param e element that has been accessed recently
     */
    @Override
    public void moveFirst(E e) {
        if (availableElements > 1) {
            var firstNode = new Node<>(e, this.first, null);
            this.first.setPrevious(firstNode);
            this.first = firstNode;
            deleteNode(e);
        }
    }

    private void deleteNode(E e) {
        Node elementNode = this.first.next;
        while (elementNode != null) {
            if (elementNode == e) {
                elementNode.prev.setNext(elementNode.next);
                if (elementNode.next != null)
                    elementNode.next.setPrevious(elementNode.prev);
                elementNode = null;
                break;
            }
            elementNode = elementNode.next;
        }
    }

    @Override
    public String toString() {
        return "com.cache.ds.impl.DoubleLinkedListImpl{" +
                "size=" + size +
                ", availableElements=" + availableElements +
                ", first=" + first +
                ", last=" + last +
                '}';
    }

    public static class Node<E> {
        E element;
        DoubleLinkedListImpl.Node<E> next;
        DoubleLinkedListImpl.Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public void setPrevious(Node<E> e) {
            this.prev = e;
        }

        public void setNext(Node<E> e) {
            this.next = e;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }
    }
}
