package IDATT2101.Task8;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanNode implements Comparator<HuffmanNode> {

    int charFrequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode() {
    }

    public HuffmanNode(char character, int charFrequency) {
        this.character = character;
        this.charFrequency = charFrequency;
        this.left = null;
        this.right = null;
    }


    public static HuffmanNode makeHuffmanTree(PriorityQueue<HuffmanNode> pq) {
        HuffmanNode tree = new HuffmanNode();
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode top = new HuffmanNode('\0', findSum(left, right));

            top.left = left;
            top.right = right;

            pq.add(top);
            tree = top;
        }
        return tree;

    }

    public boolean hasLeft(){
        return left!=null;
    }

    public boolean hasRight(){
        return right!=null;
    }

    private static int findSum(HuffmanNode t, HuffmanNode n) {
        return t.charFrequency + n.charFrequency;
    }

    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        return o1.charFrequency - o2.charFrequency;
    }
}