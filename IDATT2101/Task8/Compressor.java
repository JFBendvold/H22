package IDATT2101.Task8;

import java.io.IOException;

/**
 * Class for compressing of files. Uses LZ77 and huffman
 */
public class Compressor {

    /**
     * Empty constructor
     */
    public Compressor(){}

    /**
     * Compress method
     * @param inPath is the path of the file we want to compress
     * @param outPath is the path of the compressed file
     * @throws IOException if something went wrong
     */
    public void compress(String inPath, String outPath) throws IOException {
        LZ77 lz = new LZ77();
        byte[] compressedFile = lz.compress(inPath);

        Huffman huffman = new Huffman();
        int i = huffman.compress(compressedFile, outPath);
        System.out.println(i);
    }
}
