package IDATT2101.Task5.Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for task 1 in exercise 5
 */
public class Exercise5_1 {
    /**
     * Class representing a hash table
     */
    public static class HashTabell {
        private HashNode nodes[];
        private int length;
        private int collisions;
        private int numOfNodes;

        /**
         * Constructor for a hash table
         * @param length is the length of the table
         */
        public HashTabell(int length){
            this.length = length;
            nodes = new HashNode[length];
        }

        /**
         * Method for creating a hash value of a string
         * @param text is the string being hashed
         * @return the hash value as an int
         */
        public int Hash(String text) {
            int sum = 0;
            int n = 1;
            for (char c : text.toCharArray()) {
                sum += 7*c*n;
                n++;
            }
            return sum % length;
        }

        /**
         * Method for inserting a hashnode into the hash table. If there is a collision, the hashnode will be added
         * before the first hashnode with a link to it
         * @param data is the string being inserted into the hash table
         */
        public void insert(String data) {
            int hash = Hash(data);
            if (nodes[hash] == null) {
                nodes[hash] = new HashNode(data, null);
                numOfNodes++;
            }
            else {
                collisions++;
                System.out.println(data + " -> " + nodes[hash].data);
                nodes[hash] = new HashNode(data, nodes[hash]);
            }
        }

        /**
         * Method for finding a HashNode in the HashTabell
         * @param data to find
         * @return The HashNode with matching data
         */
        public HashNode find(String data){
            int hash = Hash(data);
            if (nodes[hash] == null) {
                return null;
            }
            else {
                HashNode node = nodes[hash];
                while (!node.data.equals(data)) {
                    if (node.next == null) {
                        return null;
                    }
                    node = node.next;
                }
                return node;
            }
        }

        /**
         * Calculates the load factor
         * @return Load factor
         */
        public double getLoadFactor(){
            return (double) numOfNodes / length;
        }

        /**
         * @return Number of collisions
         */
        public int getCollisions() {
            return collisions;
        }

        /**
         * Calculates the average collision per node
         * @return Average collision per node
         */
        public double getAverageCollisions(){
            return (double) collisions / numOfNodes;
        }
    }

    /**
     * Class representing a HashNode
     */
    public static class HashNode {
        public String data;
        public HashNode next;

        /**
         * Constructor
         * @param data Data to be stored
         * @param next Next hash for creating a linked list
         */
        public HashNode(String data, HashNode next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * main-method
     * @param args args
     */
    public static void main(String[] args) {
        HashTabell tabell = new HashTabell(150);

        try {
            BufferedReader br = new BufferedReader(new FileReader("IDATT2101/Task5/Part1/navn.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                tabell.insert(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Collisions: " + tabell.getCollisions() + ", load factor: " + tabell.getLoadFactor() +
                ", average collisions per person: " + tabell.getAverageCollisions());
        System.out.println(tabell.find("John Fredrik Bendvold").data);
        System.out.println(tabell.find("Teodor Birkeland").data);
        System.out.println(tabell.find("Christoffer Brevik").data);
        System.out.println(tabell.find("Bjørn-Johnny Bendixen").data);
    }
}
