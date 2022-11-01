package IDATT2101.Task8;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Compressor {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Compressor(){}

    public void compress(String inPath, String outPath) {
        LZ lz = new LZ();
        byte[] compressedFile = lz.compress(inPath);

        Huffman huffman = new Huffman();
        huffman.compress(compressedFile, outPath);
    }
}
