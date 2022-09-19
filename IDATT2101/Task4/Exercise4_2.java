package IDATT2101.Task4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for task 2 in exercise
 */
public class Exercise4_2 {

    /**
     * Class for generating a binary tree consisting of words
     */
    public static class WordTree {
        public WordTree() {}

        public static ArrayList<String> tree = new ArrayList<>();

        public void swapWords(int posA, int posB) {
            String wordB = tree.get(posB);
            tree.set(posB, tree.get(posA));
            tree.set(posA, wordB);
        }

        public void sortTree() {



        }

        /**
         * Adds a word to the binary-tree
         * @param word Word to be added to the tree
         */
        public void addWord(String word) {
            tree.add(word);
        }

        /**
         * Method for retrieving the depth of the tree
         * @param n is the amount of words in the tree
         * @return
         */
        public int getDepth(int n) {
            return (int)(Math.log(n) / Math.log(2));
        }

        /**
         * Creates a string of n number of spaces
         * @param n Number of spaces
         * @return returns a String of spaces
         */
        public String space(int n) {
            StringBuilder spaces = new StringBuilder();
            spaces.append(" ".repeat(Math.max(0, n)));
            return spaces.toString();
        }

        /**
         * Creates a visual representation of the tree,
         * only generates for depth 0,1,2 and 3
         * @return String of the tree
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String line1 = "";
            String line2 = "";
            String line3 = "";
            String line4 = "";

            if (tree.isEmpty()) {
                return "The tree currently has no nodes.";
            }

            for (int i = 0; i < tree.size(); i++) {
                int currentDepth = getDepth(i+1);
                if (currentDepth == 0) {
                    line1 += space((32-tree.get(i).length()/2)) + tree.get(i) + space((32-tree.get(i).length()/2));
                }
                else if(currentDepth == 1){
                    line2 += space((16-tree.get(i).length()/2)) + tree.get(i) + space((16-tree.get(i).length()/2));
                }
                else if(currentDepth == 2){
                    line3 += space((8-tree.get(i).length()/2)) + tree.get(i) + space((8-tree.get(i).length()/2));
                }
                else if(currentDepth == 3){
                    line4 += space((4-tree.get(i).length()/2)) + tree.get(i) + space((4-tree.get(i).length()/2));
                }
            }

            sb.append(line1).append("\n").append(line2).append("\n").append(line3).append("\n").append(line4).append("\n");
            sb.append("Number of words: ").append(tree.size()).append("\n");
            sb.append("Depth: ").append(getDepth(tree.size()));
            return sb.toString();
        }
    }

    /**
     * main
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("----- EXERCISE 2 PART 2 -----\n");
        Scanner sc = new Scanner(System.in);
        String input = "";
        WordTree tree = new WordTree();

        do {
            System.out.println("Enter 0 for Exit");
            System.out.println("Enter 1 to print the Tree");
            System.out.println("Or enter a word to add:");
            input = sc.next();

            while(input.length() > 8){
                System.out.println("The word has to be less than 9 characters");
                System.out.println("Enter a word:");
                input = sc.next();
            }

            if (input.equals("1") || input.equals("0")) {
                System.out.println("Current tree:");
                System.out.println(tree);
            } else {
                tree.addWord(input);
                System.out.println(input + " was added.");
            }

            System.out.println();
        } while (!input.equals("0"));
    }
}
