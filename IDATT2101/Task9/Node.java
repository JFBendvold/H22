package IDATT2101.Task9;

import java.util.Comparator;

public class Node implements Comparator<Node> {
    public int node;
    public int runtime;
    public double latitude;    //Breddegrad
    public double longitude;   //Lengdegrad
    public int parentNode;

    /**
     * Empty constructor for nodes.
     */
    public Node() {}

    /**
     * Constructor for nodes.
     * @param node is the index of the node.
     * @param runtime is the shortest route to this Node from the starting Node.
     */
    public Node(int node, int runtime, double latitude, double longitude) {
        this.node = node;
        this.runtime = runtime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor for nodes.
     * @param node is the index of the node.
     * @param runtime is the shortest route to this Node from the starting Node.
     */
    public Node(int node, int runtime, double latitude, double longitude, int parentNode) {
        this.node = node;
        this.runtime = runtime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parentNode = parentNode;
    }

    public int getParentNode() {
        return parentNode;
    }

    public void setParentNode(int parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Method for comparing the weight of two nodes.
     * @param node1 is the first node to be compared.
     * @param node2 is the second node to be compared.
     * @return -1 if node1 < node2, 0 if node1 = node2 and 1 if node1 > node2.
     */
    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.runtime, node2.runtime);
    }
}
