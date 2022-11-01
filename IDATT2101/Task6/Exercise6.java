package IDATT2101.Task6;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for exercise 6
 */
public class Exercise6 {
    public class Graph {
        ArrayList<Node> nodes;                 // Format:  [[Fra, Til], [Fra, Til], [Fra, Til]]
        int N;
        int K;

        public Graph(int nodes, int edges){
            this.N = nodes;
            this.K = edges;
        }

        public Graph(String lineValue, String lineValue1) {
        }

        //public int numOfSCC;


    }

    public class Node {
        Kant kant1;
        public Object d;

    }

    public class Kant {
        Kant neste;
        Node til;

        public Kant(Node n, Kant nst) {
            til = n;
            neste = nst;
        }
    }

    public class GraphReader{
        String[] files = {"o6g1.txt", "o6g2.txt", "o6g5.txt", "o6g6.txt"};
        File file;
        BufferedReader fileReader;

        public GraphReader(int fileIndex) throws IndexOutOfBoundsException, FileNotFoundException, IOException{

            // Throws error if the input is among the 4 possible graphs
            if(fileIndex < 1 | fileIndex > 4){
                throw new IndexOutOfBoundsException("The Index is out of bounds");
            }

            // Create the file-reader
            file = new File(files[fileIndex-1]);
            fileReader = new BufferedReader(new FileReader(file));

            // Transform the file into a graph
            String line = fileReader.readLine();
            String shortenedLine = line.replaceAll("\\s+", " ");        // Reduce multiple spaces to max 1
            String[] lineValues = line.split(" ");                      // Divide values into a list
            Graph graph = new Graph(lineValues[0], lineValues[1]);

            while ((line = fileReader.readLine()) != null) {
                shortenedLine = line.replaceAll("\\s+", " ");        // Reduce multiple spaces to max 1
                lineValues = line.split(" ");                      // Divide values into a list
                //graph.addEdge(Integer.parseInt(lineValues[0]), Integer.parseInt(lineValues[1]);
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
            System.out.println("Enter 1 to read o6g1.txt");
            System.out.println("Enter 2 to read o6g2.txt");
            System.out.println("Enter 3 to read o6g5.txt");
            System.out.println("Enter 4 to read o6g6.txt");
            System.out.println("Enter 0 to exit");
            input = sc.next();

            System.out.println();
        } while (!input.equals("0"));
        System.exit(0);
    }
}