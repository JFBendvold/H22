package IDATT2101.Task9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ALT {
    private int dist[];                             //List with the distance to each node
    ArrayList<ArrayList<Double>> nodesLocation;     //List of nodes location, 0 = latitude, 1 = longitude
    private Set<Integer> visited;                   //List of visited nodes
    private PriorityQueue<ALTNode> pq;                 //Priority queue
    private int srcNode;                            //Source node
    private int nrOfVisitedNodes;                   //Number of visited nodes
    private ArrayList<ArrayList<ALTNode>> fromlandmarkTable;
    private ArrayList<ArrayList<ALTNode>> tolandmarkTable;

    private int N;                                  //Number of nodes
    ArrayList<List<ALTNode>> adj;                      //List of adjacency's


    public ALT(String nodesFilePath, String edgesFilePath) throws IOException {
        BufferedReader nodesBr = new BufferedReader(new FileReader((nodesFilePath)));
        BufferedReader edgesBr = new BufferedReader(new FileReader((edgesFilePath)));

        StringTokenizer nodesTokenizer = new StringTokenizer(nodesBr.readLine());
        StringTokenizer edgesTokenizer = new StringTokenizer(edgesBr.readLine());

        this.N = Integer.parseInt(nodesTokenizer.nextToken());
        this.adj = new ArrayList<List<ALTNode>>();
        dist = new int[N];
        pq = new PriorityQueue<ALTNode>(N, new ALTNode());
        visited = new HashSet<Integer>();
        this.nodesLocation = new ArrayList<ArrayList<Double>>(N);

        for (int i = 0; i < this.N; i++) {
            List<ALTNode> item = new ArrayList<ALTNode>();
            adj.add(item);
            ArrayList<Double> coordinates = new ArrayList<Double>(2);
            nodesLocation.add(coordinates);

            nodesTokenizer = new StringTokenizer(nodesBr.readLine());
            int node = Integer.parseInt(nodesTokenizer.nextToken());
            double latitude = Double.parseDouble(nodesTokenizer.nextToken());
            double longitude = Double.parseDouble(nodesTokenizer.nextToken());
            nodesLocation.get(node).add(latitude);
            nodesLocation.get(node).add(longitude);
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
        adj.get(from).add(new ALTNode(to, weight, nodesLocation.get(from).get(0), nodesLocation.get(from).get(1)));
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
            ALTNode neighbourNode = adj.get(currentNode).get(i);

            if (!visited.contains(neighbourNode.node)) {
                edgeDistance = neighbourNode.runtime;
                newDistance = dist[currentNode] + edgeDistance;

                //Set distance if it's shorter than the registered one
                if (newDistance < dist[neighbourNode.node]) {
                    dist[neighbourNode.node] = newDistance;
                }

                //Add the current node to the priority queue
                pq.add(new ALTNode(neighbourNode.node, dist[neighbourNode.node], neighbourNode.latitude,
                        neighbourNode.longitude));
            }
        }
    }

    public boolean processMap(ArrayList<ALTNode> landmarks){
        for(ALTNode landmark : landmarks){
            for (ALTNode node : ALTMap.nodes){
                int distance = 0;

                if(!node.equals(landmark)){

                }
            }

        }
    }

    public boolean toLandmark(ALTNode landmark){

    }

    public boolean fromLandmark(ALTNode landmark){

    }

    /**
     * Method to measure how much it cost traverse from a node to all other nodes, if possible
     * @param srcNode ALTNode to measure the distance from
     */
    public int getShortestPath(int srcNode, int targetNode) {
        this.srcNode = srcNode;
        //Set all distances as max value
        Arrays.fill(dist,Integer.MAX_VALUE);

        //First add the source node to the priority queue
        pq.add(new ALTNode(srcNode, 0, nodesLocation.get(srcNode).get(0), nodesLocation.get(srcNode).get(1)));

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
}
