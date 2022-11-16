package IDATT2101.Task9;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class for Exercise 8
 */
public class Exercise9 {
    /**
     * main-method
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----\n");
        Scanner sc = new Scanner(System.in);
        String nodesPath;
        String edgesPath;
        String input;

        int startNode;
        int targetNode;
        String outputPath;

        do {
            System.out.println("Enter 1 to use Dijkstra");
            System.out.println("Enter 2 to use ALT");
            System.out.println("or enter 0 to exit");
            input = sc.nextLine();

            if (input.equals("1") || input.equals("2")) {
                System.out.println("Enter path to the file containing nodes:");
                nodesPath = sc.nextLine();

                System.out.println("Enter path to the file containing edges:");
                edgesPath = sc.nextLine();

                if (input.equals("1")) {
                    try {
                        System.out.println("Loading nodes and edges...");
                        Dijkstra dijkstra = new Dijkstra(nodesPath, edgesPath);

                        System.out.println("Enter start node:");
                        startNode = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter target node:");
                        targetNode = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter output-path for directions:");
                        outputPath = sc.nextLine();

                        System.out.println();
                        System.out.println("Distance: " + dijkstra.getShortestPath(startNode, targetNode));
                        System.out.println("Number of visited nodes: " + dijkstra.nrOfVisitedNodes);
                        dijkstra.writeNavigationToFile(startNode, targetNode, outputPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } while (!input.equals("0"));
        System.exit(0);
    }
}
