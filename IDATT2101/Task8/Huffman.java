package IDATT2101.Task8;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Huffman {
    private final int BYTE_MAX_VALUE = 256;

    private List<Byte> bytes;
    private int[] frequincies;
    private String[] bitStrings;
    DataOutputStream outData;

    public Huffman() {
        this.frequincies = new int[BYTE_MAX_VALUE];
        this.bitStrings = new String[BYTE_MAX_VALUE];
        bytes = new ArrayList<>();
    }

    public int compress(byte[] compressedBytes, String outPath) {
        for (int i = 0; i < compressedBytes.length; i++) {
            int b = compressedBytes[i];
            if (compressedBytes[i] < 0) {
                frequincies[BYTE_MAX_VALUE + b]++;
            } else {
                frequincies[b]++;
            }
        }
        HuffmanNode root = buildHuffmanTree();
        parseCodes(root, "");
        writeToFile(outPath, compressedBytes);

        return outData.size();
    }
}
