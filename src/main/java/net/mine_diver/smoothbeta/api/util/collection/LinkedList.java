package net.mine_diver.smoothbeta.api.util.collection;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<LinkedList.Node<T>> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public void addFirst(Node<T> node) {
        this.checkNoParent(node);

        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        } else {
            Node<T> nodenext = this.first;
            node.setNext(nodenext);
            nodenext.setPrev(node);
            this.first = node;
        }

        node.setParent(this);
        ++this.size;
    }

    public void addLast(Node<T> node) {
        this.checkNoParent(node);

        if (this.isEmpty())
            this.first = node;
        else {
            Node<T> nodeprev = this.last;
            node.setPrev(nodeprev);
            nodeprev.setNext(node);
        }
        this.last = node;

        node.setParent(this);
        ++this.size;
    }

    public void addAfter(Node<T> nodePrev, Node<T> node) {
        if (nodePrev == null)
            this.addFirst(node);
        else if (nodePrev == this.last)
            this.addLast(node);
        else {
            this.checkParent(nodePrev);
            this.checkNoParent(node);
            Node<T> nodenext = nodePrev.getNext();
            nodePrev.setNext(node);
            node.setPrev(nodePrev);
            nodenext.setPrev(node);
            node.setNext(nodenext);
            node.setParent(this);
            ++this.size;
        }
    }

    public Node<T> remove(Node<T> node) {
        this.checkParent(node);
        Node<T> prev = node.getPrev();
        Node<T> node1 = node.getNext();

        if (prev != null)
            prev.setNext(node1);
        else
            this.first = node1;

        if (node1 != null)
            node1.setPrev(prev);
        else
            this.last = prev;

        node.setPrev(null);
        node.setNext(null);
        node.setParent(null);
        --this.size;
        return node;
    }

    private void checkParent(Node<T> node) {
        if (node.parent != this)
            throw new IllegalArgumentException("Node has different parent");
    }

    private void checkNoParent(Node<T> node) {
        if (node.parent != null)
            throw new IllegalArgumentException("Node already has a parent");
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            Node<T> node = LinkedList.this.getFirst();

            public boolean hasNext() {
                return this.node != null;
            }

            public Node<T> next() {
                Node<T> node = this.node;
                if (this.node != null)
                    this.node = this.node.next;
                return node;
            }
        };
    }

    public Node<T> getFirst() {
        return this.first;
    }

    public Node<T> getLast() {
        return this.last;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size <= 0;
    }

    public boolean contains(Node<T> node) {
        return node != null && node.parent == this;
    }

    public static class Node<T> {
        private final T item;
        private Node<T> prev;
        private Node<T> next;
        private LinkedList<T> parent;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
            return this.item;
        }

        public Node<T> getPrev() {
            return this.prev;
        }

        public Node<T> getNext() {
            return this.next;
        }

        private void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        private void setParent(LinkedList<T> parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "" + this.item;
        }
    }
}
