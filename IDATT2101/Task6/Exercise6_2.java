package IDATT2101.Task6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Exercise6_2 {

    /**
     * Class representing an edge
     */
    public static class Edge {
        Edge next;
        Node to;
        public Edge(Node n, Edge next) {
            this.to = n;
            this.next = next;
        }
    }

    /**
     * Class representing a node
     */
    public static class Node {
        public Edge edge1;
        public Object data;           //Node data
    }

    /**
     * Class representing a graph
     */
    public static class Graph {
        public int N, E;           //Number of: Nodes, Edges
        public Node[] nodes;

        public void initPredecessor(Node node) {
            for (int i = N; i-->0;) {
                nodes[i].data = new Predecessor();
            }
            ((Predecessor)node.data).distance = 0;
        }

        public void initDfs() {
            for (int i = N; i-->0;) {
                nodes[i].data = new DfsPredecessor();
            }
            DfsPredecessor.resetTime();
        }

        public void dfSearch(Node node) {
            DfsPredecessor nodeData = (DfsPredecessor)node.data;
            nodeData.timeFound = DfsPredecessor.readTime();

            for (Edge edge = node.edge1; edge != null; edge = edge.next) {
                DfsPredecessor mData = (DfsPredecessor)edge.to.data;
                if (mData.timeFound == 0) {
                    mData.predecessor = node;
                    mData.distance = nodeData.distance + 1;
                    dfSearch(edge.to);
                }
            }
            nodeData.timeDone = DfsPredecessor.readTime();
        }

        public void dfs(Node s){
            initDfs();
            ((DfsPredecessor)s.data).distance = 0;
            dfSearch(s);
        }

        /**
         * Method to find a graphs opposite
         * This means the same structure, but the edges are reversed
         * @return
         */
        public Node[] getTransposedGraph() {
            Node[] transposedList = new Node[nodes.length];
            for (int i = 0; i < N; i++) {
                //for (int j = 0; j < nodes[i].size(); j++) {

                //}
            }

            return transposedList;
        }

        /**
         * Reads graph from file
         * @param br File to read
         * @throws IOException If an error occurred
         */
        public void readGraph(BufferedReader br) throws IOException {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            nodes = new Node[N];

            for (int i = 0; i < N; i++) {
                nodes[i] = new Node();
            }

            E = Integer.parseInt(st.nextToken());
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                Edge e = new Edge(nodes[to], nodes[from].edge1);
                nodes[from].edge1 = e;
            }
        }

        public void findSCC() {
            Node[] sortedList = new Node[nodes.length];
            for (Node node : nodes) {
                dfs(node);
                System.out.println();
            }
        }


    }

    /**
     * Class representing
     */
    public static class Predecessor {
        public int distance;
        public Node predecessor;
        static int inf = 1000000000;

        public int getDistance() {
            return distance;
        }

        public Node getPredecessor() {
            return predecessor;
        }

        public Predecessor() {
            distance = inf;
        }
    }

    public static class DfsPredecessor extends Predecessor {
        public int timeFound, timeDone;
        static int time;

        static void resetTime() {
            time = 0;
        }

        static int readTime() {
            return time++;
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
        String[] filePaths = {"IDATT2101/Task6/o6g1.txt", "IDATT2101/Task6/o6g2.txt",
        "IDATT2101/Task6/o6g5.txt", "IDATT2101/Task6/o6g6.txt"};

        do {
            System.out.println("Enter 1 to read o6g1.txt");
            System.out.println("Enter 2 to read o6g2.txt");
            System.out.println("Enter 5 to read o6g5.txt");
            System.out.println("Enter 6 to read o6g6.txt");
            System.out.println("Enter 0 to exit");
            input = sc.next();

            if (input.equals("1") || input.equals("2") || input.equals("5") || input.equals("6")) {
                Graph graph = new Graph();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("IDATT2101/Task6/o6g"+ input +".txt")));
                    graph.readGraph(br);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("No file selected.");
            }

            System.out.println();
        } while (!input.equals("0"));
        System.exit(0);
    }
}
