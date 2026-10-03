package test;
import Main.main.DynamicArray;
import Main.main.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void basicOperations() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(1, 15);

        assertEquals(3, array.size());

        assertEquals(10, array.get(0));
        assertEquals(15, array.get(1));
        assertEquals(20, array.get(2));

        assertEquals(20, array.remove(2));

        assertTrue(array.contains(15));

        assertFalse(array.contains(99));
    }

    @Test
    void duplicatesAndResize() {

        DynamicArray array =
                new DynamicArray(1,new Metrics());

        array.add(5);
        array.add(5);
        array.add(7);

        assertEquals(3, array.size());

        assertTrue(array.contains(5));
    }

    @Test
    void invalidIndexes() {

        DynamicArray array = new DynamicArray();

        array.add(1);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(2, 5)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(1)
        );
    }
}