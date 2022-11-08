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
            compressor.compress("C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\org.lyx",
                    "C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\comp");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Decompressor decompressor = new Decompressor();
        try {
            decompressor.decompress("C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\comp",
                    "C:\\Users\\jfben\\OneDrive - NTNU\\Documents\\Project Lucy\\H22\\IDATT2101\\Task8\\decomp.lyx");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
