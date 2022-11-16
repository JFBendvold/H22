package IDATT2101.Task8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Huffman {
    private static final int MAX_BYTE_VALUE = 256;
    private final List<Byte> bytes;
    private final int[] frequencies;
    private final String[] bitstrings;
    DataOutputStream outData;

    /**
     * Constructor for a class representing a Huffman compressor
     */
    public Huffman() {
        this.frequencies = new int[MAX_BYTE_VALUE];
        this.bitstrings = new String[MAX_BYTE_VALUE];
        bytes = new ArrayList<>();
    }

    /**
     * Method to compress the text
     * @param compressedBytes Bytes to compress
     * @param outpath Path to where to write compressed file
     * @return Filesize
     * @throws IOException If file-reading went wrong
     */
    public int compress(byte[] compressedBytes, String outpath) throws IOException {
        for (int b : compressedBytes) {
            if (b < 0)
                frequencies[MAX_BYTE_VALUE + b]++;
            else frequencies[b]++;
        }
        HuffmanNode topNode = buildHuffmanTree();
        defineValues(topNode, "");
        writeToFile(outpath, compressedBytes);

        return outData.size();
    }

    /**
     * Method to build a huffmantree from the values in the priorityQueue
     * @return the top node of the tree
     */
    private HuffmanNode buildHuffmanTree() {
        OurPriorityQueue queue = new OurPriorityQueue(frequencies.length);
        queue.buildTree(frequencies);

        HuffmanNode topNode = new HuffmanNode();

        while (!queue.sizeIsOne()) {
            // Get the two lowest nodes of the queue
            HuffmanNode left = queue.getLowest();
            HuffmanNode right = queue.getLowest();

            // Create a node over the values, with a combined frequency value
            HuffmanNode top = new HuffmanNode('\0', freqSum(left, right));

            // Add the nodes to the combined node
            top.left = left;
            top.right = right;

            // Put the top node back in the queue
            queue.addValue(top);
            topNode = top;
        }
        // Return the top node of the tree
        return topNode;
    }

    /**
     * Method to find the sum of two node frequencies
     * @param node1 the first node
     * @param node2 the second node
     * @return Sum of two node frequencies
     */
    public int freqSum(HuffmanNode node1, HuffmanNode node2){
        return node1.charFrequency + node2.charFrequency;
    }

    /**
     * Recursive method to define the values of the different HuffmanTree positions [001, 01, 100101, ... ]
     * @param topNode the top node of the Huffman tree
     * @param s the string representing the previous nodes value, will be empty on the top node
     */
    private void defineValues(HuffmanNode topNode, String s) {
        // If a Node is at the bottom of a tree, this node represents a letter
        if (topNode.isBottom()) {
            // Gives letter a value in the compressed tree
            bitstrings[topNode.character] = s;
            return;
        }
        // If node has nodes under it, it is not a letter
        // Repeats method recursively to nodes further down
        defineValues(topNode.left, s + "0");
        defineValues(topNode.right, s + "1");
    }

    /**
     * Method to write to a specified file
     * @param outpath the path of where we want our file to be / the file we want to overwrite
     * @param compressedBytes the bytes of the message
     * @throws IOException if any of the methods fail
     */
    private void writeToFile(String outpath, byte[] compressedBytes) throws IOException {
        // Create DataOutputStream
        outData = new DataOutputStream(new FileOutputStream(outpath));

        // Write down compressed message to file
        for (int i : frequencies)
            outData.writeInt(i);

        // Write out last byte
        int lastByte = parseBitString(compressedBytes);
        outData.writeInt(lastByte);

        // Write bytes and close the stream
        writeBytes();
        outData.close();
    }

    /**
     * Method to write down all the bytes in the bytes array
     * @throws IOException if the write funciton fails
     */
    private void writeBytes() throws IOException {
        for (Byte s : bytes) {
            outData.write(s);
        }
    }

    /**
     * Method to parse through the BitStrings, will also return the last byte of the row
     * @param compressedBytes the bytes we want to parse through
     * @return the last valid byte
     */
    private int parseBitString(byte[] compressedBytes ) {
        int input;
        int i = 0;
        int j;
        int k;
        long currentByte = 0L;
        // We go through all the compressed bytes
        for (k = 0; k < compressedBytes.length; k++) {
            input = compressedBytes[k];
            if (input < 0)
                input += MAX_BYTE_VALUE;

            String bitString = bitstrings[input];

            j = 0;
            while (j < bitString.length()) {
                if (bitString.charAt(j) == '0')
                    currentByte = (currentByte << 1);
                else
                    currentByte = ((currentByte << 1) | 1); // times 2 + 1

                ++j;
                ++i;

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

    /**
     * Method to make a list of nodes using the frequencies of the letters
     * @param frequencies the frequencies of the letters of the text
     * @return an ArrayList of HuffmanNodes relating to the frequencies
     */
    private static ArrayList<HuffmanNode> makeNodeList(int[] frequencies) {
        ArrayList<HuffmanNode> nodeList = new ArrayList<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                nodeList.add(new HuffmanNode((char) i, frequencies[i]));
            }
        }
        return nodeList;
    }

    /**
     * Method to decompress a compressed file
     * @param inputFile the path of the file we want to decompress
     * @return a list of bytes representing letters of the decompressed text
     * @throws IOException if any of the methods fail during execution
     */
    public byte[] decompress(String inputFile) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(inputFile));
        int[] frequencies = new int[256];
        for (int i = 0; i < frequencies.length; i++) {
            int freq = in.readInt();
            frequencies[i] = freq;
        }
        ArrayList<Byte> out = new ArrayList<>();

        int lastByte = in.readInt();
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(256, (a, b) -> a.charFrequency - b.charFrequency);
        pq.addAll(makeNodeList(frequencies));
        HuffmanNode tree = HuffmanNode.makeHuffmanTree(pq);
        byte ch;
        byte[] bytes = in.readAllBytes();
        in.close();
        int length = bytes.length;
        BitString h = new BitString(0, 0);
        if (lastByte > 0) length--;
        for (int i = 0; i < length; i++) {
            ch = bytes[i];
            BitString b = new BitString(8, ch);
            h = BitString.combine(h, b);
            h = writeChar(tree, h, out);
        }
        if (lastByte > 0) {
            BitString b = new BitString(lastByte, bytes[length] >> (8 - lastByte));
            h = BitString.combine(h, b);
            writeChar(tree, h, out);
        }
        in.close();

        return toArray(out);
    }

    /**
     * Convert an ArrayList of Bytes into an Array of bytes
     * @param byteList the list we want to convert
     * @return an Array of Bytes
     */
    public byte[] toArray(ArrayList<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++){
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

    /**
     * Method used when writing for writing a character during the decryption process
     * @param tree the top node of the Huffmantree of the different letters
     * @param bitstring the BitString value used
     * @param decompressedBytes a list of the bytes
     * @return the Bitstring after having completet the write
     */
    private static BitString writeChar(HuffmanNode tree, BitString bitstring, ArrayList<Byte> decompressedBytes) {
        HuffmanNode tempTree = tree;
        int c = 0;
        for (long j = 1L << bitstring.len - 1; j != 0; j >>= 1) {
            c++;
            if ((bitstring.bits & j) == 0)
                tempTree = tempTree.left;
            else
                tempTree = tempTree.right;

            if (tempTree.isBottom()) {
                long cha = tempTree.character;
                decompressedBytes.add((byte) cha);
                long temp = ~(0);
                bitstring.bits = (bitstring.bits & temp);
                bitstring.len = bitstring.len - c;
                c = 0;
                tempTree = tree;
            }
        }
        return bitstring;
    }

    static class BitString {
        int len;
        long bits;

        /**
         * Empty constructor for an object representing a BitString
         */
        BitString() {
        }

        /**
         * Constructor for an object representing a BitString
         * @param len the lenght of the BitString
         * @param bits the bits in the object (8 bits = 1 byte)
         */
        BitString(int len, long bits) {
            this.len = len;
            this.bits = bits;
        }

        /**
         * Constructor for an object representing a BitString
         * @param len the lenght of the BitString
         * @param b the byte in the object (8 bits = 1 byte)
         */
        BitString(int len, byte b) {
            this.len = len;
            this.bits = convertByte(b, len);
        }

        /**
         * Method to convert Byte into a number
         * @param b the byte we want to convert
         * @param length the length of the byte
         * @return the value of the byte as a long
         */
        public long convertByte(byte b, int length) {
            long temp = 0;
            for (long i = 1L << length - 1; i != 0; i >>= 1) {
                if ((b & i) == 0) {
                    temp = (temp << 1);
                } else temp = ((temp << 1) | 1);
            }
            return temp;
        }

        /**
         * Method to combine two BitStrings
         * @param string1 the first bitstring
         * @param string2 the second bitstring
         * @return a BitString with both input's values
         */
        static BitString combine(BitString string1, BitString string2) {
            BitString newBitString = new BitString();
            newBitString.len = string1.len + string2.len;
            // If combining the BitStrings will exceed capacity, we throw an exception
            if (newBitString.len > 64) throw new IllegalArgumentException("Bitstring is too long!");
            // If not, we combine and return them
            newBitString.bits = string2.bits | (string1.bits << string2.len);
            return newBitString;
        }
    }
}