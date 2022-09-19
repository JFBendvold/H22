package IDATT2101.Task4;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class for task 2 in exercise
 */
public class Exercise4 {

    public static class TreeNode{
        String element;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(String element, TreeNode parent, TreeNode left, TreeNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public int getKey() {
            char firstLetter = element.toUpperCase(Locale.ROOT).charAt(0);
            return Character.getNumericValue(firstLetter);
        }

        public int getLength() {
            return element.length();
        }
    }

    public static class Tree {
        TreeNode root;

        public Tree() {
            root = null;
        }

        public boolean empty() {
            return root==null;
        }

        public void insert(String e) {
            char firstLetter = e.toUpperCase(Locale.ROOT).charAt(0);
            int key = Character.getNumericValue(firstLetter);
            TreeNode n = root;
            if (root == null) {
                root = new TreeNode(e, null, null, null);
                return;
            }

            int keyToCompare = 0;
            TreeNode f = null;
            while (n != null) {
                f = n;
                keyToCompare = n.getKey();
                if (key < keyToCompare) n = n.left;
                else n = n.right;
            }

            if (key < keyToCompare) f.left = new TreeNode(e, f, null, null);
            else f.right = new TreeNode(e,f,null,null);
        }

        /**
         * Creates a string of n number of spaces
         * @param n Number of spaces
         * @return returns a String of spaces
         */
        public String space(int n) {
            return " ".repeat(Math.max(0, n));
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

            if (root == null) {
                return "The tree currently has no nodes.";
            }

            line1 = space(32 - root.getLength()/2) + root.element + space(32 - root.getLength()/2);
            line2 += space(16 - root.left.getLength()/2) + root.left.element + space(16 - root.left.getLength()/2);
            line2 += space(16 - root.right.getLength()/2) + root.right.element + space(16 - root.right.getLength()/2);

            sb.append(line1).append("\n").append(line2).append("\n").append(line3).append("\n").append(line4).append("\n");
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
        Tree tree = new Tree();

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
                tree.insert(input);
                System.out.println(input + " was added.");
            }

            System.out.println();
        } while (!input.equals("0"));
    }
}
