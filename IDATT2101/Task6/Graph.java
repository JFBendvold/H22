package IDATT2101.Task6;

// Kosaraju's algorithm to find strongly connected components in Java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.LinkedList;

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    // Create a graph
    Graph(int s) {
        V = s;
        adj = new LinkedList[s];
        for (int i = 0; i < s; ++i)
            adj[i] = new LinkedList();
    }

    /**
     * Constructor which reads graph from file
     * @param br File to read
     * @throws IOException If an error occurred
     */
    public Graph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.V = Integer.parseInt(st.nextToken());
        this.adj = new LinkedList[this.V];
        for (int i = 0; i < this.V; i++) {
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

    // Add edge
    void addEdge(int s, int d) {
        adj[s].add(d);
    }

    // DFS
    void DFSUtil(int s, boolean visitedVertices[]) {
        visitedVertices[s] = true;
        System.out.print(s + " ");
        int n;

        Iterator<Integer> i = adj[s].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visitedVertices[n])
                DFSUtil(n, visitedVertices);
        }
    }

    // Transpose the graph
    Graph Transpose() {
        Graph g = new Graph(V);
        for (int s = 0; s < V; s++) {
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(s);
        }
        return g;
    }

    void fillOrder(int s, boolean visitedVertices[], Stack stack) {
        visitedVertices[s] = true;

        Iterator<Integer> i = adj[s].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visitedVertices[n])
                fillOrder(n, visitedVertices, stack);
        }
        stack.push(new Integer(s));
    }

    // Print strongly connected component
    void printSCC() {
        Stack stack = new Stack();

        boolean visitedVertices[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visitedVertices[i] = false;

        for (int i = 0; i < V; i++)
            if (visitedVertices[i] == false)
                fillOrder(i, visitedVertices, stack);

        Graph gr = Transpose();

        for (int i = 0; i < V; i++)
            visitedVertices[i] = false;

        while (stack.empty() == false) {
            int s = (int) stack.pop();

            if (visitedVertices[s] == false) {
                gr.DFSUtil(s, visitedVertices);
                System.out.println();
            }
        }
    }

    public static void main(String args[]) {
        Graph g;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("IDATT2101/Task6/o6g"+ 6 +".txt")));
            g = new Graph(br);
            System.out.println("Strongly Connected Components:");
            g.printSCC();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
