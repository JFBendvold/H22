package IDATT2101.Task8;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanNode implements Comparator<HuffmanNode> {
    int charFreq;
    char c;
    HuffmanNode left, right;

    public HuffmanNode(){}

    public HuffmanNode(int charFreq, char c) {
        this.charFreq = charFreq;
        this.c = c;
        this.left = null;
        this.right = null;
    }
    
    public static HuffmanNode createHuffmanTree(PriorityQueue<HuffmanNode> pq) {
        HuffmanNode tree = new HuffmanNode();
        if (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode top = new HuffmanNode(getSum(left, right), '\0'); //\0 = null

            top.left = left;
            top.right = right;

            pq.add(top);
            tree = top;
        }
        return tree;
    }
    
    public static int getSum(HuffmanNode t, HuffmanNode n) {
        return t.charFreq + n.charFreq;
    }

    @Override
    public int compare(HuffmanNode h1, HuffmanNode h2) {
        return h1.charFreq - h2.charFreq;
    }
}
