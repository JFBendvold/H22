package IDATT2101.Task2;

/**
 * Exercise 2 program
 * @version 1.0
 * @author johnfb
 * @since 06.09.22
 */
public class Exercise2 {
    public static double powMethod1(double x, int n) {
        if (n == 0) return 1;
        else if (n > 0) {
            return x * powMethod1(x, n-1);
        }
        return 0;
    }

    public static double powMethod2(double x , int n) {
        if (n == 0) return 1;
        else if (n % 2 == 1) {      //Odd
            return x * powMethod2(x*x, (n-1)/2);
        } else if (n % 2 == 0) {    //Even
            return powMethod2(x*x, n/2);
        }
        return 0;
    }

    //Java's own built in pow-method
    public static double powJava(double x, int n) {
        return Math.pow(x, n);
    }

    /**
     * Method for testing if all pow-methods
     * work as intended
     */
    public static void testMethods() {
        System.out.println("Testing every method:");
        double x = 2;
        int n = 12;
        double expectedResult = 4096;

        if (powMethod1(x, n) == expectedResult) {
            System.out.println("Method1 was successful.");
        } else {
            System.out.println("Method1 was unsuccessful.");
        }

        if (powMethod2(x, n) == expectedResult) {
            System.out.println("Method2 was successful.");
        } else {
            System.out.println("Method2 was unsuccessful.");
        }

        if (powJava(x, n) == expectedResult) {
            System.out.println("JavaMethod was successful.");
        } else {
            System.out.println("JavaMethod was unsuccessful.");
        }

        x = 3;
        n = 14;
        expectedResult = 4782969;

        if (powMethod1(x, n) == expectedResult) {
            System.out.println("Method1 was successful.");
        } else {
            System.out.println("Method1 was unsuccessful.");
        }

        if (powMethod2(x, n) == expectedResult) {
            System.out.println("Method2 was successful.");
        } else {
            System.out.println("Method2 was unsuccessful.");
        }

        if (powJava(x, n) == expectedResult) {
            System.out.println("JavaMethod was successful.");
        } else {
            System.out.println("JavaMethod was unsuccessful.");
        }
    }

    /**
     * Method for measuring each function runtime
     * @param instances Number of test-instances
     */
    public static void testTime(int instances) {
        double x = 54358;
        int n = 4447;

        System.out.println("n: " + instances);

        double result1;
        long startTime1;
        long endTime1;
        long totalTime1 = 0;
        for (int i = 0; i < instances; i++) {
            startTime1 = System.nanoTime();
            result1 = powMethod1(x,n);
            endTime1 = System.nanoTime();
            totalTime1 += (endTime1 - startTime1);
        }
        totalTime1 /= instances;
        System.out.println("PowMethod1: " + totalTime1 + "ns");

        double result2;
        long startTime2;
        long endTime2;
        long totalTime2 = 0;
        for (int i = 0; i < instances; i++) {
            startTime2 = System.nanoTime();
            result2 = powMethod2(x,n);
            endTime2 = System.nanoTime();
            totalTime2 += (endTime2 - startTime2);
        }
        totalTime2 /= instances;
        System.out.println("PowMethod2: " + totalTime2 + "ns");

        double result3;
        long startTime3;
        long endTime3;
        long totalTime3 = 0;
        for (int i = 0; i < instances; i++) {
            startTime3 = System.nanoTime();
            result3 = powJava(x,n);
            endTime3 = System.nanoTime();
            totalTime3 += (endTime3 - startTime3);
        }
        totalTime3 /= instances;
        System.out.println("PowMethod3: " + totalTime3 + "ns");
    }

    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");
        testMethods();
        System.out.println();
        testTime(1000);
        System.out.println();
        testTime(10000);
        System.out.println();
        testTime(100000);
    }
}