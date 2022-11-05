package IDATT2101.Task8;

import java.io.IOException;

public class Decompressor {

    public Decompressor(){}

    public void decompress(String inPath, String outPath) throws IOException {
        Huffman huffman = new Huffman();
        byte[] decompressedFile = huffman.decompress(inPath);
        LZ77 lz = new LZ77();
        lz.decompress(decompressedFile, outPath);
    }
}
