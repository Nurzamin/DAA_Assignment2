package Main.main;

public class MyLinkedList {

    private static class Node {

        int value;
        Node next;

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    private int size;

    private final Metrics metrics;

    public MyLinkedList() {
        this(new Metrics());
    }

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
    }

    public void add(int x) {

        Node node = new Node(x, null);

        if (head == null) {

            head = node;
            tail = node;

        } else {

            tail.next = node;

            metrics.move();

            tail = node;
        }

        size++;
    }

    // O(n)
    public void add(int index, int x) {

        checkPosition(index);

        // Insert at beginning
        if (index == 0) {

            head = new Node(x, head);

            if (tail == null) {
                tail = head;
            }

            metrics.move();

            size++;

            return;
        }

        Node previous = nodeAt(index - 1);

        Node node = new Node(x, previous.next);

        previous.next = node;

        metrics.move();

        if (node.next == null) {
            tail = node;
        }

        size++;
    }

    // O(n)
    public int remove(int index) {

        checkElement(index);

        // Remove first element
        if (index == 0) {

            int value = head.value;

            metrics.step();

            head = head.next;

            metrics.move();

            size--;

            if (size == 0) {
                tail = null;
            }

            return value;
        }

        Node previous = nodeAt(index - 1);

        Node removed = previous.next;

        int value = removed.value;

        metrics.step();

        previous.next = removed.next;

        metrics.move();

        size--;

        if (removed == tail) {
            tail = previous;
        }

        return value;
    }

    // O(n)
    public int get(int index) {

        checkElement(index);

        return nodeAt(index).value;
    }

    // O(n)
    public boolean contains(int x) {

        Node current = head;

        while (current != null) {

            metrics.step();
            metrics.compare();

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    private Node nodeAt(int index) {

        Node current = head;

        for (int i = 0; i < index; i++) {

            current = current.next;

            metrics.step();
        }

        return current;
    }

    private void checkElement(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkPosition(int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public int size() {
        return size;
    }
}