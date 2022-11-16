package IDATT2101.Task8;

import java.util.PriorityQueue;

/**
 * Class representing the priority queue
 */
public class OurPriorityQueue {
    int length;
    PriorityQueue<HuffmanNode> queue;

    /**
     * Constructor
     * @param length is the length of the queue
     */
    public OurPriorityQueue(int length) {
        this.length = length;
        this.queue = new PriorityQueue<>(new HuffmanNode());
    }

    /**
     * Method for building the tree
     * @param frequencies is frequency of the chars value
     */
    public void buildTree(int[] frequencies) {
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] == 0) continue;

            char currentChar = (char) i;
            int frequency = frequencies[i];
            HuffmanNode hn = new HuffmanNode(currentChar, frequency);
            queue.add(hn);
        }
    }

    public boolean sizeIsOne() {
        return queue.size() == 1;
    }

    public HuffmanNode getLowest() {
        return queue.poll();
    }

    public void addValue(HuffmanNode top) {
        queue.add(top);
    }
}
