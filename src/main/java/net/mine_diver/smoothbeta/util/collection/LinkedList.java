package net.mine_diver.smoothbeta.util.collection;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;

    public boolean isEmpty() {
        return first == null;
    }

    public Node<T> getFirst() {
        return first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void addLast(Node<T> node) {
        if (last == null) {
            first = last = node;
            node.prev = null;
            node.next = null;
        } else {
            last.next = node;
            node.prev = last;
            node.next = null;
            last = node;
        }
    }

    public void remove(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        node.prev = null;
        node.next = null;
    }

    public boolean contains(Node<T> node) {
        Node<T> current = first;
        while (current != null) {
            if (current == node)
                return true;
            current = current.next;
        }
        return false;
    }

    public static class Node<T> {
        private final T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public Node<T> getNext() {
            return next;
        }
    }
}
