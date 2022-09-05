package IDATT2101.Task2;

/**
 * Exercise 2 program
 * @version 1.0
 * @author johnfb
 * @since 05.09.22
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

    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");
        testMethods();
    }
}