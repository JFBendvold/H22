  package IDATT2101.Task8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Huffman {
    private static final int BYTE_MAX_VALUE = 256;

    private List<Byte> bytes;
    private int[] frequencies;
    private String[] bitStrings;
    DataOutputStream outData;

    public Huffman() {
        this.frequencies = new int[BYTE_MAX_VALUE];
        this.bitStrings = new String[BYTE_MAX_VALUE];
        bytes = new ArrayList<>();
    }

    //[00110101, 10100101, 00101101] = F R E
    public int compress(byte[] compressedBytes, String outPath) throws IOException {
        for (int i = 0; i < compressedBytes.length; ++i) {
            int b = compressedBytes[i];
            if (compressedBytes[i] < 0) {
                frequencies[BYTE_MAX_VALUE + b]++;
            } else {
                frequencies[b]++;
            }
        }
        HuffmanNode root = buildHuffmanTree();
        defineValues(root, "");
        writeToFile(outPath, compressedBytes);
        return outData.size();
    }

    public HuffmanNode buildHuffmanTree() {
        OurPriorityQueue queue = new OurPriorityQueue(frequencies.length);
        queue.buildTree(frequencies);

        HuffmanNode tree = new HuffmanNode();
        while (queue.sizeMoreThanOne()) {
            // Get the two lowest nodes of the queue
            HuffmanNode left = queue.getLowest();
            HuffmanNode right = queue.getLowest();

            // Create a node over the values, with a combined frequency value
            HuffmanNode top = new HuffmanNode(freqSum(left, right), '\0'); //\0 = null

            // Add the nodes to the combined node
            top.left = left;
            top.right = right;

            // Put the top node back in the queue
            queue.addValue(top);

            // Update tree
            tree = top;
        }
        return tree;
    }

    public int freqSum(HuffmanNode node1, HuffmanNode node2){
        return node1.charFreq + node2.charFreq;
    }

    private void defineValues(HuffmanNode top, String s) {
        // If a Node is at the bottom of a tree, this node represents a letter
        if (top.left == null && top.right == null) {
            // Gives letter a value in the compressed tree
            bitStrings[top.c] = s;
            return;
        }
        // If node has nodes under it, it is not a letter
        // Repeats method recursively to nodes further down
        defineValues(top.left, s + "0");
        defineValues(top.right, s + "1");
    }

    private void writeToFile(String outpath, byte[] compressedBytes) throws IOException {
        // Create DataOutputStream
        outData = new DataOutputStream(new FileOutputStream(outpath));

        // Write down compressed message to file
        for (int i : frequencies)
            outData.writeInt(i);

        // Write out last byte
        int lastByte = parseBitStringsAndGetLastByte(compressedBytes);
        outData.writeInt(lastByte);


        // s
        for (Byte s : bytes) {
            outData.write(s);
        }

        outData.close();
    }

    // The Great wall of Uncertainty

    private int parseBitStringsAndGetLastByte(byte[] compressedBytes ) throws IOException {
        int input;
        int i = 0;
        int j;
        int k;
        long currentByte = 0L;

        for (k = 0; k < compressedBytes.length; k++) {
            input = compressedBytes[k];
            // Can't have a negative value
            if (input < 0) {
                input += BYTE_MAX_VALUE;
            }

            String bitString = bitStrings[input];

            j = 0;
            while (j < bitString.length()) {
                // Position says 0
                if (bitString.charAt(j) == '0')
                    currentByte = (currentByte << 1);
                // Position says 1
                else
                    currentByte = ((currentByte << 1) | 1); // times 2 + 1
                j++;
                i++;

                if (i == 8) {
                    bytes.add((byte) currentByte);
                    i = 0;
                    currentByte = 0L;
                }
            }
        }

        int lastByte = i;
        while (i < 8 && i != 0) {
            currentByte = (currentByte << 1);
            ++i;
        }
        bytes.add((byte) currentByte);

        return lastByte;
    }

    private static ArrayList<HuffmanNode> makeNodeList(int[] frequensies) {
        ArrayList<HuffmanNode> nodeList = new ArrayList<>();
        for (int i = 0; i < frequensies.length; i++) {
            if (frequensies[i] != 0) {
                nodeList.add(new HuffmanNode(i, (char) frequensies[i]));
            }
        }
        return nodeList;
    }

    public byte[] decompress(String inputFile) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(inputFile));
        int[] frequencies = new int[256];
        for (int i = 0; i < frequencies.length; i++) {
            int freq = in.readInt();
            frequencies[i] = freq;
        }
        ArrayList<Byte> outData = new ArrayList<>();

        int lastByte = in.readInt();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(256, (a, b) -> a.charFreq - b.charFreq);
        pq.addAll(makeNodeList(frequencies));
        HuffmanNode tree = buildHuffmanTree();
        byte ch;
        byte[] bytes = in.readAllBytes();
        in.close();
        int length = bytes.length;
        Bitstring h = new Bitstring(0, 0);
        if (lastByte > 0) length--;
        for (int i = 0; i < length; i++) {
            ch = bytes[i];
            Bitstring b = new Bitstring(8, ch);
            h = Bitstring.concat(h, b);
            h = writeChar(tree, h, outData);
        }
        if (lastByte > 0) {
            Bitstring b = new Bitstring(lastByte, bytes[length] >> (8 - lastByte));
            h = Bitstring.concat(h, b);
            writeChar(tree, h, outData);
        }
        in.close();

        return toByteArray(outData);
    }

    public byte[] toByteArray(ArrayList<Byte> list) throws IOException {
        byte[] byteArray = new byte[list.size()];
        for (int i = 0; i < list.size(); i++){
            byteArray[i] = list.get(i);
        }
        return byteArray;
    }

    private static Bitstring writeChar(HuffmanNode tree, Bitstring bitstring, ArrayList<Byte> decompressedBytes) throws IOException {
        HuffmanNode tempTree = tree;
        int c = 0;
        
        for (long j = 1 << bitstring.len - 1; j != 0; j >>= 1) {
            c++;
            if ((bitstring.bits & j) == 0)
                tempTree = tempTree.left;
            else
                tempTree = tempTree.right;

            if (tempTree.left == null) {
                long cha = tempTree.c;
                decompressedBytes.add((byte) cha);
                long temp = (long) ~(0);
                bitstring.bits = (bitstring.bits & temp);
                bitstring.len = bitstring.len - c;
                c = 0;
                tempTree = tree;
            }
        }
        return bitstring;
    }

    static class Bitstring {
        int len;
        long bits;

        Bitstring() {
        }

        Bitstring(int lenght, long bits) {
            this.len = lenght;
            this.bits = bits;
        }

        Bitstring(int lenght, byte b) {
            this.len = lenght;
            this.bits = convertByte(b, lenght);
        }

        static Bitstring concat(Bitstring bitstring, Bitstring other) {
            Bitstring ny = new Bitstring();
            ny.len = bitstring.len + other.len;

            if (ny.len > 64)
                throw new IllegalArgumentException("For lang bitstreng, går ikke! " + ny.bits + ", lengde=" + ny.len);

            ny.bits = other.bits | (bitstring.bits << other.len);
            return ny;
        }

        public long convertByte(byte b, int length) {
            long temp = 0;
            for (long i = 1 << length - 1; i != 0; i >>= 1) {
                if ((b & i) == 0) {
                    temp = (temp << 1);
                } else temp = ((temp << 1) | 1);
            }
            return temp;
        }

        public void remove() {
            this.bits = (bits >> 1);
            this.len--;
        }
    }
}

