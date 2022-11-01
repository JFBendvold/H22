package IDATT2101.Task7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class for Exercise 7
 */
public class Exercise7 {

    /**
     * Class representing a graph
     */
    public static class Graph {
        private int dist[];                 //List with the distance to each node
        private Set<Integer> visited;       //List of visited nodes
        private PriorityQueue<Node> pq;     //Priority queue
        private int srcNode;                //Source node

        private int N;                      //Number of nodes
        ArrayList<List<Node>> adj;          //List of adjacency's

        /**
         * Constructor for graph that reads the graph from a file
         * @param br BufferedReader
         * @throws IOException if an error occurred
         */
        public Graph(BufferedReader br) throws IOException {
            StringTokenizer st = new StringTokenizer(br.readLine());
            this.N = Integer.parseInt(st.nextToken());
            this.adj = new ArrayList<List<Node>>();
            dist = new int[N];
            pq = new PriorityQueue<Node>(N, new Node());
            visited = new HashSet<Integer>();

            for (int i = 0; i < this.N; i++) {
                List<Node> item = new ArrayList<Node>();
                adj.add(item);
            }

            int E = Integer.parseInt(st.nextToken());
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                addEdge(from, to, weight);
            }
        }

        /**
         * Method to add an Edge between two Nodes
         * @param from the node the Edge starts in
         * @param to the node the Edge ends at
         * @param weight the cost of traversing this edge
         */
        public void addEdge(int from, int to, int weight) {
            adj.get(from).add(new Node(to, weight));
        }

        /**
         * Method to measure how much it cost traverse from a node to all other nodes, if possible
         * @param srcNode Node to measure the distance from
         */
        public void dijkstra(int srcNode) {
            this.srcNode = srcNode;
            //Set all distances as max value
            for (int i = 0; i < N; i++) {
                dist[i] = Integer.MAX_VALUE;
            }

            //First add the source node to the priority queue
            pq.add(new Node(srcNode, 0));

            dist[srcNode] = 0; //Distance to the source node should be 0

            while (visited.size() != N) {
                //Exit when the priority queue is empty
                if (pq.isEmpty()) {
                    return;
                }

                //Remove the node with the lowest distance
                int currentNode = pq.remove().node;

                //Setting the node as visited when distance is finalized
                if (visited.contains(currentNode)) {
                    continue;
                }
                visited.add(currentNode);

                checkNeighbours(currentNode);
            }
        }

        /**
         * Method to go through all neighboring nodes, calculating travel cost, and changing it if applicable
         * @param currentNode the node we want to change
         */
        private void checkNeighbours(int currentNode) {
            int edgeDistance = -1;
            int newDistance = -1;

            //Loop through every neighbour
            for (int i = 0; i < adj.get(currentNode).size(); i++) {
                Node neighbourNode = adj.get(currentNode).get(i);

                if (!visited.contains(neighbourNode.node)) {
                    edgeDistance = neighbourNode.weight;
                    newDistance = dist[currentNode] + edgeDistance;

                    //Set distance if it's shorter than the registered one
                    if (newDistance < dist[neighbourNode.node]) {
                        dist[neighbourNode.node] = newDistance;
                    }

                    //Add the current node to the priority queue
                    pq.add(new Node(neighbourNode.node, dist[neighbourNode.node]));
                }
            }

        }

        /**
         * Method for printing all distances from the source node
         */
        public void printDistances() {
            System.out.println("The shortest path from source node:");
            for (int i = 0; i < this.dist.length; i++) {
                String distance = "" + this.dist[i];
                if (distance.equals(""+Integer.MAX_VALUE)) {    //If distance is inf, then the node is unreachable
                    distance = "unreachable";
                }
                System.out.println(srcNode + " to " + i + " is " + distance);
            }
        }
    }

    /**
     * Class for representing a node in the graph, implementing the Comparator Interface.
     */
    public static class Node implements Comparator<Node> {
        public int node;
        public int weight;

        /**
         * Empty constructor for nodes.
         */
        public Node() {}

        /**
         * Constructor for nodes.
         * @param node is the index of the node.
         * @param weight is the shortest route to this Node from the starting Node.
         */
        public Node(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        /**
         * Method for comparing the weight of two nodes.
         * @param node1 is the first node to be compared.
         * @param node2 is the second node to be compared.
         * @return -1 if node1 < node2, 0 if node1 = node2 and 1 if node1 > node2.
         */
        @Override
        public int compare(Node node1, Node node2) {
            return Integer.compare(node1.weight, node2.weight);
        }
    }

    /**
     * main-method
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----\n");
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            System.out.println("Enter filename/path to read file");
            System.out.println("or enter 0 to exit");
            input = sc.next();

            if (!input.equals("0")) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader((input)));
                    Graph graph = new Graph(br);
                    int srcNode = -1;
                    do {
                        System.out.println("Enter source node(0-" + graph.adj.size() + "):");
                        input = sc.next();
                        try {
                            srcNode = Integer.parseInt(input);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Enter a valid number");
                        }
                    } while (srcNode < 0 || srcNode > graph.adj.size());

                    graph.dijkstra(srcNode);
                    graph.printDistances();
                    input = "-1";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        } while (!input.equals("0")) ;
        System.exit(0);
    }
}