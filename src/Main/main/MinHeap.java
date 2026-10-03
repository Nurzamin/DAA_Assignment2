package Main.main;

public class MinHeap {

    private int[] heap;
    private int size;

    private final Metrics metrics;

    public MinHeap() {
        this(10, new Metrics());
    }

    public MinHeap(Metrics metrics) {
        this(10, metrics);
    }

    public MinHeap(int capacity, Metrics metrics) {

        if (capacity < 1) {
            throw new IllegalArgumentException(
                    "Capacity must be positive"
            );
        }

        heap = new int[capacity];

        this.metrics = metrics;
    }

    // O(log n)
    public void insert(int x) {

        if (size == heap.length) {
            grow();
        }

        int index = size;

        heap[size] = x;

        size++;

        metrics.move();

        // Bubble up
        while (index > 0) {

            int parent = (index - 1) / 2;

            metrics.compare();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(index, parent);

            index = parent;
        }
    }

    // O(1)
    public int peekMin() {

        if (size == 0) {
            throw new IllegalStateException(
                    "Heap is empty"
            );
        }

        metrics.step();

        return heap[0];
    }

    // O(log n)
    public int extractMin() {

        if (size == 0) {
            throw new IllegalStateException(
                    "Heap is empty"
            );
        }

        int result = heap[0];

        metrics.step();

        size--;

        if (size == 0) {
            return result;
        }

        heap[0] = heap[size];

        metrics.move();

        bubbleDown(0);

        return result;
    }

    private void bubbleDown(int index) {

        while (true) {

            int left = 2 * index + 1;
            int right = left + 1;

            if (left >= size) {
                return;
            }

            int smallest = left;

            // Compare left and right children
            if (right < size) {

                metrics.compare();

                if (heap[right] < heap[left]) {
                    smallest = right;
                }
            }

            metrics.compare();

            if (heap[index] <= heap[smallest]) {
                return;
            }

            swap(index, smallest);

            index = smallest;
        }
    }

    private void swap(int a, int b) {

        int temp = heap[a];

        heap[a] = heap[b];

        heap[b] = temp;

        metrics.move();
        metrics.move();
        metrics.move();
    }

    private void grow() {

        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {

            newHeap[i] = heap[i];

            metrics.move();
        }

        heap = newHeap;
    }

    public int size() {
        return size;
    }
}