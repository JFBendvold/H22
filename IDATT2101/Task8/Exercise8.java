package IDATT2101.Task8;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class for Exercise 8
 */
public class Exercise8 {
    /**
     * main-method
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----\n");
        Scanner sc = new Scanner(System.in);
        String inputPath;
        String outputPath;
        String input;
        do {
            System.out.println("Enter 1 to compress a file");
            System.out.println("Enter 2 to decompress a file");
            System.out.println("or enter 0 to exit");
            input = sc.nextLine();

            if (input.equals("1") || input.equals("2")) {
                System.out.println("Enter path to file you want to compress/decompress:");
                inputPath = sc.nextLine();

                System.out.println("Enter path to new output file:");
                outputPath = sc.nextLine();

                if (input.equals("1")) {
                    Compressor compressor = new Compressor();
                    try {
                        compressor.compress(inputPath, outputPath);
                        System.out.println("Compression succeeded\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Decompressor decompressor = new Decompressor();
                    try {
                        decompressor.decompress(inputPath, outputPath);
                        System.out.println("Decompression succeeded\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (!input.equals("0"));
        System.exit(0);
    }
}
