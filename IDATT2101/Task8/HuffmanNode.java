package IDATT2101.Task8;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanNode implements Comparator<HuffmanNode> {
    int charFrequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;

    /**
     * Empty constructor for class representing a Node in a Huffman Tree
     */
    public HuffmanNode() {
    }

    /**
     * Constructor for class representing a Node in a Huffman Tree
     * @param character the character associated with the node
     * @param charFrequency how many times this character appears in the text
     */
    public HuffmanNode(char character, int charFrequency) {
        this.character = character;
        this.charFrequency = charFrequency;
        this.left = null;
        this.right = null;
    }

    /**
     * Function to create a HuffmanTree using a priorityQueue of HuffmanNodes
     * @param queue a PriorityQueue of HuffmanNodes sorted by their frequency
     * @return The HuffmanNode at the top of the tree
     */
    public static HuffmanNode makeHuffmanTree(PriorityQueue<HuffmanNode> queue) {
        HuffmanNode tree = new HuffmanNode();
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode top = new HuffmanNode('\0', findSum(left, right));

            top.left = left;
            top.right = right;

            queue.add(top);
            tree = top;
        }
        return tree;

    }

    /**
     * Method to check if HuffmanNode is at the bottom of the tree
     * @return true if there aren't any child HuffMan nodes
     */
    public boolean isBottom(){
        return (left==null & right==null);
    }

    /**
     * Method to find sum between twoo huffmanNodes frequencies
     * @param t the first Node
     * @param n the secod Node
     * @return the sum of their frequencies, as an int
     */
    public static int findSum(HuffmanNode t, HuffmanNode n) {
        return t.charFrequency + n.charFrequency;
    }

    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        return o1.charFrequency - o2.charFrequency;
    }
}