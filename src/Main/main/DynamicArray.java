package Main.main;

public class DynamicArray {

    private int[] data;
    private int size;

    private final Metrics metrics;

    public DynamicArray() {
        this(10, new Metrics());
    }

    public DynamicArray(Metrics metrics) {
        this(10, metrics);
    }

    public DynamicArray(int capacity, Metrics metrics) {

        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        data = new int[capacity];
        this.metrics = metrics;
    }

    public void add(int x) {

        if (size == data.length) {
            grow();
        }

        data[size] = x;
        metrics.move();

        size++;
    }

    public void add(int index, int x) {

        checkPosition(index);

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {

            data[i] = data[i - 1];

            metrics.move();
        }

        data[index] = x;
        metrics.move();

        size++;
    }

    public int remove(int index) {

        checkElement(index);

        int removed = data[index];

        metrics.step();

        // Move elements to the left
        for (int i = index; i < size - 1; i++) {

            data[i] = data[i + 1];

            metrics.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {

        checkElement(index);

        metrics.step();

        return data[index];
    }

    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

            metrics.step();
            metrics.compare();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    private void grow() {

        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {

            newData[i] = data[i];

            metrics.move();
        }

        data = newData;
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