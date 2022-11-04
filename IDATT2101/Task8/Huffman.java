package IDATT2101.Task8;

import java.io.DataOutputStream;
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

    public int compress(byte[] compressedBytes, String outPath) {
        for (int i = 0; i < compressedBytes.length; i++) {
            int b = compressedBytes[i];
            // Can't have a negative number of occurrences
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
        if (queue.sizeMoreThanOne()) {
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
        // Recursively changes children as well
        defineValues(top.left, s + "0");
        defineValues(top.right, s + "1");
    }
}
