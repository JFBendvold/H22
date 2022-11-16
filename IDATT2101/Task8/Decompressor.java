package IDATT2101.Task8;

import java.io.IOException;

/**
 * Class for decompressing of files
 */
public class Decompressor {

    /**
     * Empty constructor
     */
    public Decompressor(){}

    /**
     * Decompress method
     * @param inPath is the path of the file we want to decompress
     * @param outPath is the path of the decompressed file
     * @throws IOException if something went wrong
     */
    public void decompress(String inPath, String outPath) throws IOException {
        Huffman huffman = new Huffman();
        byte[] decompressedFile = huffman.decompress(inPath);
        LZ77 lz = new LZ77();
        lz.decompress(decompressedFile, outPath);
    }
}
