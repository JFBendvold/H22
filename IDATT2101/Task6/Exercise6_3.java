package IDATT2101.Task6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class for Exercise 6
 */
public class Exercise6_3 {
    /**
     * Class representing a graph
     */
    public static class Graph {
        public int N;                          //Number of nodes
        public LinkedList<Integer>[] adj;      //AdjacencyList

        /**
         * Constructor for the graph
         * @param n is the number of nodes in the graph
         */
        public Graph(int n) {
            this.N = n;
            this.adj = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                this.adj[i] = new LinkedList<>();
            }
        }

        /**
         * Constructor which reads graph from file
         * @param br File to read
         * @throws IOException If an error occurred
         */
        public Graph(BufferedReader br) throws IOException {
            StringTokenizer st = new StringTokenizer(br.readLine());
            this.N = Integer.parseInt(st.nextToken());
            this.adj = new LinkedList[this.N];
            for (int i = 0; i < this.N; i++) {
                this.adj[i] = new LinkedList<>();
            }

            int E = Integer.parseInt(st.nextToken());
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                addEdge(from, to);
            }
        }

        /**
         * Method for adding an edge to the graph
         * @param from Node edge is going from
         * @param to Node edge is going to
         */
        void addEdge(int from, int to) {
            adj[from].add(to);
        }

        /**
         * Method for transposing the graph
         * @return Transposed Graph
         */
        public Graph transpose() {
            Graph tGraph = new Graph(N);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < adj[i].size(); j++) {
                    tGraph.adj[adj[i].get(j)].add(i);
                }
            }

            return tGraph;
        }

        /**
         * Depth first search
         * @param node current node being visited
         * @param vNodes list of visited nodes
         */
        public void DFS(int node, boolean[] vNodes) {
            vNodes[node] = true;
            System.out.print(node + " ");
            int n;

            for (int i = 0; i < adj[node].size(); i++) {
                n = adj[node].get(i);
                if (!vNodes[n]) {
                    DFS(n, vNodes);
                }
            }
        }

        /**
         * Method for filling the stack
         * @param node current node being visited
         * @param vNodes list of visited nodes
         * @param stack Stack to fill
         */
        public void fillStack(int node, boolean[] vNodes, Stack stack) {
            vNodes[node] = true;

            for (int i = 0; i < adj[node].size(); i++) {
                int n = adj[node].get(i);
                if (!vNodes[n]) {
                    fillStack(n, vNodes, stack);
                }
            }

            stack.push(node);
        }

        /**
         * Finds strongly connected components and prints them out.
         */
        public void findSCC() {
            Stack stack = new Stack();
            boolean[] vNodes = new boolean[N];

            for (int i = 0; i < N; i++) { //Resets visited nodes
                vNodes[i] = false;
            }

            for (int i = 0; i < N; i++) {
                if (!vNodes[i]) {
                    fillStack(i, vNodes, stack);
                }
            }

            Graph transposedGraph = transpose();

            for (int i = 0; i < N; i++) { //Resets visited nodes
                vNodes[i] = false;
            }

            while (!stack.isEmpty())  {
                int node = (int)stack.pop();

                if (!vNodes[node]) {
                    transposedGraph.DFS(node, vNodes);
                    System.out.println();
                }
            }

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
            System.out.println("Enter 1 to read ø6g1.txt");
            System.out.println("Enter 2 to read ø6g2.txt");
            System.out.println("Enter 5 to read ø6g5.txt");
            System.out.println("Enter 6 to read ø6g6.txt");
            System.out.println("Enter 0 to exit");
            input = sc.next();

            if (input.equals("1") || input.equals("2") || input.equals("5") || input.equals("6")) {
                Graph graph;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(("IDATT2101/Task6/ø6g"+ input +".txt")));
                    graph = new Graph(br);
                    System.out.println("SCC:");
                    graph.findSCC();
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