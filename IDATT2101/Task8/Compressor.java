package IDATT2101.Task8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Compressor {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Compressor(){}

    public void compress(String inPath, String outPath) throws IOException {
        LZ77 lz = new LZ77();
        byte[] compressedFile = lz.compress(inPath);

        Huffman huffman = new Huffman();
        int i = huffman.compress(compressedFile, outPath);
        System.out.println(i);
    }
}
