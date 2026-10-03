package Main.benchmark;

import Main.main.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark{

    public static void main(String[] args) throws IOException {

        int[] sizes = {100, 1000, 10000, 100000};

        new File("results").mkdirs();

        FileWriter file = new FileWriter("results/results.csv");

        file.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

        for (int n : sizes) {

            System.out.println("n = " + n);

            w1(n, file);
            w2(n, file);
            w3(n, file, 0);
            w3(n, file, n / 2);
            w4(n, file);
        }

        file.close();

        System.out.println("Done!");
    }

    static void w1(int n, FileWriter file) throws IOException {

        int[] data = makeData(n);

        int[] indexes = new int[10000];
        Random random = new Random(42);

        for (int i = 0; i < 10000; i++) {
            indexes[i] = random.nextInt(n);
        }

        Metrics m = new Metrics();
        DynamicArray array = new DynamicArray(m);

        for (int x : data)
            array.add(x);

        long time = measureGet(array, indexes, m);

        write(file, "W1", "-", "DynamicArray", n, time, m);

        m = new Metrics();
        MyLinkedList list = new MyLinkedList(m);

        for (int x : data)
            list.add(x);

        time = measureGet(list, indexes, m);

        write(file, "W1", "-", "MyLinkedList", n, time, m);
    }

    static long measureGet(
            DynamicArray array,
            int[] indexes,
            Metrics m) {

        for (int index : indexes)
            array.get(index);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int index : indexes)
                array.get(index);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static long measureGet(
            MyLinkedList list,
            int[] indexes,
            Metrics m) {

        for (int index : indexes)
            list.get(index);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int index : indexes)
                list.get(index);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static void w2(int n, FileWriter file) throws IOException {

        int[] data = makeData(n);

        int[] queries = new int[1000];

        for (int i = 0; i < 500; i++)
            queries[i] = data[i % n];

        for (int i = 500; i < 1000; i++)
            queries[i] = -i;

        Metrics m = new Metrics();
        DynamicArray array = new DynamicArray(m);

        for (int x : data)
            array.add(x);

        long time = measureContains(array, queries, m);

        write(file, "W2", "-", "DynamicArray", n, time, m);

        m = new Metrics();
        MyLinkedList list = new MyLinkedList(m);

        for (int x : data)
            list.add(x);

        time = measureContains(list, queries, m);

        write(file, "W2", "-", "MyLinkedList", n, time, m);
    }

    static long measureContains(
            DynamicArray array,
            int[] queries,
            Metrics m) {

        for (int x : queries)
            array.contains(x);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int x : queries)
                array.contains(x);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static long measureContains(
            MyLinkedList list,
            int[] queries,
            Metrics m) {

        for (int x : queries)
            list.contains(x);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int x : queries)
                list.contains(x);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static void w3(
            int n,
            FileWriter file,
            int index) throws IOException {

        Metrics m = new Metrics();
        DynamicArray array = new DynamicArray(m);

        for (int i = 0; i < n; i++)
            array.add(i);

        long time = measureInsertRemove(array, index, m);

        String variant = index == 0 ? "head" : "middle";

        write(file, "W3", variant,
                "DynamicArray", n, time, m);

        m = new Metrics();
        MyLinkedList list = new MyLinkedList(m);

        for (int i = 0; i < n; i++)
            list.add(i);

        time = measureInsertRemove(list, index, m);

        write(file, "W3", variant,
                "MyLinkedList", n, time, m);
    }

    static long measureInsertRemove(
            DynamicArray array,
            int index,
            Metrics m) {

        for (int i = 0; i < 1000; i++)
            array.add(index, i);

        for (int i = 0; i < 1000; i++)
            array.remove(index);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++)
                array.add(index, i);

            for (int i = 0; i < 1000; i++)
                array.remove(index);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static long measureInsertRemove(
            MyLinkedList list,
            int index,
            Metrics m) {

        for (int i = 0; i < 1000; i++)
            list.add(index, i);

        for (int i = 0; i < 1000; i++)
            list.remove(index);

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++)
                list.add(index, i);

            for (int i = 0; i < 1000; i++)
                list.remove(index);

            times[run] = System.nanoTime() - start;
        }

        return median(times);
    }

    static void w4(
            int n,
            FileWriter file) throws IOException {

        int[] data = makeData(n);

        Metrics m = new Metrics();

        MinHeap warmup = new MinHeap(m);

        for (int x : data)
            warmup.insert(x);

        for (int i = 0; i < n; i++)
            warmup.extractMin();

        long[] times = new long[5];

        for (int run = 0; run < 5; run++) {

            m.reset();

            MinHeap heap = new MinHeap(m);

            long start = System.nanoTime();

            for (int x : data)
                heap.insert(x);

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                int current = heap.extractMin();

                if (current < previous)
                    throw new RuntimeException("Heap error");

                previous = current;
            }

            times[run] = System.nanoTime() - start;
        }

        write(file, "W4", "-", "MinHeap",
                n, median(times), m);
    }

    static int[] makeData(int n) {

        int[] data = new int[n];

        Random random = new Random(42);

        for (int i = 0; i < n; i++)
            data[i] = random.nextInt(1000000);

        return data;
    }

    static long median(long[] a) {

        java.util.Arrays.sort(a);

        return a[2];
    }

    static void write(
            FileWriter file,
            String workload,
            String variant,
            String structure,
            int n,
            long time,
            Metrics m) throws IOException {

        file.write(
                workload + "," +
                        variant + "," +
                        structure + "," +
                        n + "," +
                        time / 1_000_000.0 + "," +
                        m.getSteps() + "," +
                        m.getMoves() + "," +
                        m.getComparisons() + "\n"
        );
    }
}
