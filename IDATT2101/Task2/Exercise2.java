package IDATT2101.Task2;

import java.util.Date;

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
     * @param x double
     */
    public static void testTime(double x) {
        int n;
        long time;

        int rounds = 0;
        long end;
        long start = System.nanoTime();

        System.out.println("Method1:");

        n = 100;
        do {
            double result = powMethod1(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 1000;
        do {
            double result = powMethod1(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 10000;
        do {
            double result = powMethod1(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        System.out.println();
        System.out.println("Method2:");
        rounds = 0;
        start = System.nanoTime();

        n = 1000;
        do {
            double result = powMethod2(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 10000;
        do {
            double result = powMethod2(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 100000;
        do {
            double result = powMethod2(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        System.out.println();
        System.out.println("Method3:");
        rounds = 0;
        start = System.nanoTime();

        n = 1000;
        do {
            double result = powJava(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 10000;
        do {
            double result = powJava(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");

        rounds = 0;
        start = System.nanoTime();
        n = 100000;
        do {
            double result = powJava(x, n);
            end = System.nanoTime();
            rounds++;
        } while (end-start < 1000000000);
        time = (long) (end - start) / rounds;
        System.out.println("n:" + n);
        System.out.println(rounds + " runs in 1 second, " + time + "ns per run");
    }

    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");
        testMethods();
        System.out.println();
        System.out.println("----- RUNTIME-TESTS -----");
        testTime(4532);
    }
}