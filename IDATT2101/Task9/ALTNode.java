package IDATT2101.Task9;

import java.util.Comparator;
import java.util.Objects;

public class ALTNode implements Comparator<ALTNode> {
    public int node;
    public int runtime;         //
    public double latitude;     //Breddegrad
    public double longitude;    //Lengdegrad
    public int toGoal;

    /**
     * Empty constructor for nodes.
     */
    public ALTNode() {}

    /**
     * Constructor for nodes.
     * @param node is the index of the node.
     * @param runtime is the shortest route to this Node from the starting Node.
     */
    public ALTNode(int node, int runtime, double latitude, double longitude) {
        this.node = node;
        this.runtime = runtime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Method for comparing the weight of two nodes.
     * @param node1 is the first node to be compared.
     * @param node2 is the second node to be compared.
     * @return -1 if node1 < node2, 0 if node1 = node2 and 1 if node1 > node2.
     */
    @Override
    public int compare(ALTNode node1, ALTNode node2) {
        return Integer.compare(node1.runtime, node2.runtime);
    }

    /**
     * Method for checking if two Nodes share the same ID
     * @param o other node
     * @return if the nodes share the id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ALTNode altNode = (ALTNode) o;
        return node == altNode.node;
    }
}
