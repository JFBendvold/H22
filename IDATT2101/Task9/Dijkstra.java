package IDATT2101.Task9;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Dijkstra {
    private int dist[];                             //List with the distance to each node
    ArrayList<Node> nodesArray;     //List of nodes location, 0 = latitude, 1 = longitude
    private Set<Integer> visited;                   //List of visited nodes
    private PriorityQueue<Node> pq;                 //Priority queue
    private int srcNode;                            //Source node
    public int nrOfVisitedNodes;                    //Number of visited nodes

    private int N;                                  //Number of nodes
    ArrayList<List<Node>> adj;                      //List of adjacency's


    public Dijkstra(String nodesFilePath, String edgesFilePath) throws IOException {
        BufferedReader nodesBr = new BufferedReader(new FileReader((nodesFilePath)));
        BufferedReader edgesBr = new BufferedReader(new FileReader((edgesFilePath)));

        StringTokenizer nodesTokenizer = new StringTokenizer(nodesBr.readLine());
        StringTokenizer edgesTokenizer = new StringTokenizer(edgesBr.readLine());

        this.N = Integer.parseInt(nodesTokenizer.nextToken());
        this.adj = new ArrayList<List<Node>>();
        dist = new int[N];
        pq = new PriorityQueue<Node>(N, new Node());
        visited = new HashSet<Integer>();
        this.nodesArray = new ArrayList<Node>(N);

        for (int i = 0; i < this.N; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
            Node emptyNode = new Node();
            nodesArray.add(emptyNode);

            nodesTokenizer = new StringTokenizer(nodesBr.readLine());
            int node = Integer.parseInt(nodesTokenizer.nextToken());
            double latitude = Double.parseDouble(nodesTokenizer.nextToken());
            double longitude = Double.parseDouble(nodesTokenizer.nextToken());
            nodesArray.set(node, new Node(0, 0, latitude, longitude));
        }

        int E = Integer.parseInt(edgesTokenizer.nextToken());

        for (int i = 0; i < E; i++) {
            edgesTokenizer = new StringTokenizer(edgesBr.readLine());
            int from = Integer.parseInt(edgesTokenizer.nextToken());
            int to = Integer.parseInt(edgesTokenizer.nextToken());
            int runtime = Integer.parseInt(edgesTokenizer.nextToken());
            addEdge(from, to, runtime);
        }
    }

    /**
     * Method to add an Edge between two Nodes
     * @param from the node the Edge starts in
     * @param to the node the Edge ends at
     * @param weight the cost of traversing this edge
     */
    public void addEdge(int from, int to, int weight) {
        adj.get(from).add(new Node(to, weight, nodesArray.get(from).latitude, nodesArray.get(from).longitude));
    }

    /**
     * Method to go through all neighboring nodes, calculating travel cost, and changing it if applicable
     * @param currentNode the node we want to change
     */
    private void checkNeighbours(int currentNode) {
        int edgeDistance = -1;
        int newDistance = -1;
        nrOfVisitedNodes++;

        //Loop through every neighbour
        for (int i = 0; i < adj.get(currentNode).size(); i++) {
            Node neighbourNode = adj.get(currentNode).get(i);

            if (!visited.contains(neighbourNode.node)) {
                edgeDistance = neighbourNode.runtime;
                newDistance = dist[currentNode] + edgeDistance;

                //Set distance if it's shorter than the registered one
                if (newDistance < dist[neighbourNode.node]) {
                    dist[neighbourNode.node] = newDistance;
                }

                //Add the current node to the priority queue
                pq.add(new Node(neighbourNode.node, dist[neighbourNode.node], neighbourNode.latitude,
                        neighbourNode.longitude, currentNode));

                nodesArray.get(neighbourNode.node).setParentNode(currentNode);

            }
        }
    }

    /**
     * Method to measure how much it cost traverse from a node to all other nodes, if possible
     * @param srcNode Node to measure the distance from
     */
    public int getShortestPath(int srcNode, int targetNode) {
        this.srcNode = srcNode;
        //Set all distances as max value
        Arrays.fill(dist,Integer.MAX_VALUE);

        //First add the source node to the priority queue
        pq.add(new Node(srcNode, 0, nodesArray.get(srcNode).latitude, nodesArray.get(srcNode).longitude));

        dist[srcNode] = 0; //Distance to the source node should be 0

        while (visited.size() != N) {
            //Exit when the priority queue is empty
            if (pq.isEmpty()) {
                return -1; //Couldn't find a way
            }

            //Remove the node with the lowest distance
            int currentNode = pq.remove().node;

            if (currentNode == targetNode) {
                return dist[currentNode];
            }

            //Setting the node as visited when distance is finalized
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);

            checkNeighbours(currentNode);
        }
        return -1; //Couldn't find a way
    }

    public void writeNavigationToFile(int startNode, int targetNode, String outputFile) {
        ArrayList<Node> travelRoute = new ArrayList<>();

        int currentNode = targetNode;
        while (nodesArray.get(currentNode).getParentNode() != startNode) {
            travelRoute.add(nodesArray.get(currentNode));
            currentNode = nodesArray.get(currentNode).parentNode;
        }
        travelRoute.add(nodesArray.get(startNode));
        Collections.reverse(travelRoute);

        try {
            FileWriter myWriter = new FileWriter(outputFile);
            StringBuilder output = new StringBuilder();
            for (Node node : travelRoute) {
                output.append(node.latitude).append(",").append(node.longitude).append("\n");
            }
            myWriter.write(output.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
