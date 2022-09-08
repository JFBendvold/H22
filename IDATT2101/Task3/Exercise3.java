package IDATT2101.Task3;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Exercise3 {
    public static int[] testArray = {24, 8, 42, 75, 29, 77, 38, 57};
    public static int[] expectedArray = {8, 24, 29, 38, 42, 57, 75, 77};
    public static int[] duplicateArray = {8, 24, 8, 24, 8, 24, 8, 24, 8, 24};
    private static final String TIME_FORMAT = "%-8s%-20s%-15s%n";

    public static class RunTimeTestClass {
        public static void runTimeTest(int[] array) {
            int n;
            int[] array2 = array.clone();

            System.out.println("n:" + array.length);
            Date end;
            System.out.println("Dual pivot Quicksort:");
            Date start = new Date();
            SortingClass.dualPivotQuickSort(array, 0, array.length - 1);
            end = new Date();
            double time = (double) (end.getTime() - start.getTime());
            System.out.println(time + "ms runtime.");

            System.out.println("Simple Quicksort:");
            start = new Date();
            SortingClass.quicksort(array2, 0, array2.length-1);
            end = new Date();
            time = (double) (end.getTime() - start.getTime());
            System.out.println(time + "ms runtime.");
        }
    }

    /**
     * Generates a random array of integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createRandomArray(int size) {
        int[] array = new int[size];
        Random r = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = (r.nextInt((110000 - 1) + 1) - 10000);
        }
        return array;
    }

    /**
     * Generates a sorted array of integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    /**
     * Generates an array containing two alternating integers
     * @param size Number of integers to generate
     * @return list of integers
     */
    public static int[] createAlternatingArray(int size, int var1, int var2) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                array[i] = var1;
            } else {
                array[i] = var2;
            }
        }
        return array;
    }

    /**
     *
     */
    public static void testSorting() {
        SortingClass.dualPivotQuickSort(testArray, 0, 7);
        if (Arrays.equals(testArray, expectedArray)) {
            System.out.println("Test succeeded");
        } else {
            System.out.println("Test failed");
        }
    }

    public static class SortingClass {
        public static void quicksort(int[] t, int v, int h) {
            if (h - v > 2) {
                int delepos = split(t, v, h);
                quicksort(t, v, delepos - 1);
                quicksort(t, delepos + 1, h);
            } else median3sort(t, v, h);
        }

        private static int split(int[] t, int v, int h) {
            int iv, ih;
            int m = median3sort(t, v, h);
            int dv = t[m];
            swap(t, m, h - 1);
            for (iv = v, ih = h - 1; ; ) {
                while (t[++iv] < dv) ;
                while (t[--ih] > dv) ;
                if (iv >= ih) break;
                swap(t, iv, ih);
            }
            swap(t, iv, h - 1);
            return iv;
        }

        private static int median3sort(int[] t, int v, int h) {
            int m = (v + h) / 2;
            if (t[v] > t[m]) swap(t, v, m);
            if (t[m] > t[h]) {
                swap(t, m, h);
                if (t[v] > t[m]) swap(t, v, m);
            }
            return m;
        }

        static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


        static void dualPivotQuickSort(int[] arr, int low, int high) {
            if (low < high) {
                // piv[] stores left pivot and right pivot.
                // piv[0] means left pivot and
                // piv[1] means right pivot
                int[] piv = partition(arr, low, high);

                dualPivotQuickSort(arr, low, piv[0] - 1);
                dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
                dualPivotQuickSort(arr, piv[1] + 1, high);
            }
        }

        static int[] partition(int[] arr, int low, int high) {
            swap(arr, low, low + (high - low) / 3);
            swap(arr, high, high - (high - low) / 3);

            if (arr[low] > arr[high])
                swap(arr, low, high);

            // p is the left pivot, and q
            // is the right pivot.
            int j = low + 1;
            int g = high - 1, k = low + 1,
                    p = arr[low], q = arr[high];

            while (k <= g) {
                // If elements are less than the left pivot
                if (arr[k] < p) {
                    swap(arr, k, j);
                    j++;
                }

                // If elements are greater than or equal
                // to the right pivot
                else if (arr[k] >= q) {
                    while (arr[g] > q && k < g)
                        g--;

                    swap(arr, k, g);
                    g--;

                    if (arr[k] < p) {
                        swap(arr, k, j);
                        j++;
                    }
                }
                k++;
            }
            j--;
            g++;

            // Bring pivots to their appropriate positions.
            swap(arr, low, j);
            swap(arr, high, g);

            // Returning the indices of the pivots
            // because we cannot return two elements
            // from a function, we do that using an array.
            return new int[]{j, g};
        }
    }

    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");
        testSorting();

        System.out.println("\nTesting random arrays:");

        int[] randomArray = createRandomArray(1000000);
        RunTimeTestClass.runTimeTest(randomArray);
        System.out.println();
        randomArray = createRandomArray(10000000);
        RunTimeTestClass.runTimeTest(randomArray);
        System.out.println();
        randomArray = createRandomArray(100000000);
        RunTimeTestClass.runTimeTest(randomArray);

        System.out.println("\nTesting semi-sorted arrays:");
        int[] alternatingArray = createAlternatingArray(1000000, 4, 8);
        RunTimeTestClass.runTimeTest(alternatingArray);
        System.out.println();
        alternatingArray = createAlternatingArray(10000000, 4, 8);
        RunTimeTestClass.runTimeTest(alternatingArray);
        System.out.println();
        alternatingArray = createAlternatingArray(100000000, 4, 8);
        RunTimeTestClass.runTimeTest(alternatingArray);

        System.out.println("\nTesting sorted arrays:");

        int[] sortedArray = createSortedArray(1000000);
        RunTimeTestClass.runTimeTest(sortedArray);
        System.out.println();
        sortedArray = createSortedArray(10000000);
        RunTimeTestClass.runTimeTest(sortedArray);
        System.out.println();
        sortedArray = createSortedArray(100000000);
        RunTimeTestClass.runTimeTest(sortedArray);
        System.out.println();
    }
}

