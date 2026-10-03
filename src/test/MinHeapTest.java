package test;

import Main.main.MinHeap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void heapReturnsSmallest() {

        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);

        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(8, heap.extractMin());
    }


    @Test
    void peekDoesNotRemove() {

        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(1);

        assertEquals(1, heap.peekMin());

        // Размер всё ещё 2
        assertEquals(2, heap.size());
    }


    @Test
    void emptyHeap() {

        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                () -> heap.peekMin()
        );

        assertThrows(
                IllegalStateException.class,
                () -> heap.extractMin()
        );
    }
}