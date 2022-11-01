package IDATT2101.Task8;

import java.util.PriorityQueue;

public class Heap {
    int length;
    PriorityQueue<HuffmanNode> queue;

    public Heap(int length) {
        this.length = length;
        this.queue = new PriorityQueue<>(new HuffmanNode());
    }

    public Heap buildTree(int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) continue;

            char currentChar = (char) i;
            int frequency = values[i];
            HuffmanNode hn = new HuffmanNode(frequency, currentChar);
            queue.add(hn);
        }
        return this;
    }

    int above(int i) {
        return (i - 1) >> 1;
    }

    int left(int i) {
        return (i << 1) + 1;
    }

    int right(int i) {
        return (i +1) << 1;
    }

    public boolean sizeIsOne() {
        return queue.size() == 1;
    }

    public HuffmanNode getMin() {
        return queue.poll();
    }

    public void insert(HuffmanNode top) {
        queue.add(top);
    }
}
