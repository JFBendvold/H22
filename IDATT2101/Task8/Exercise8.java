package IDATT2101.Task8;

import java.io.IOException;

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
        Compressor compressor = new Compressor();
        try {
            compressor.compress("C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\opg8-kompr.pdf",
                    "C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\comp.scb");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Decompressor decompressor = new Decompressor();
        try {
            decompressor.decompress("C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\comp.scb",
                    "C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\decomp.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
