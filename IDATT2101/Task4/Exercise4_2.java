package IDATT2101.Task4;

import java.util.Locale;
import java.util.Scanner;

/**
 * Class for task 2 in exercise 4
 */
public class Exercise4_2 {

    /**
     * Class for a tree-node, took inspiration from page 116 in the book
     */
    public static class TreeNode{
        String element;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        /**
         * Constructor
         * @param element is the string value set by the user
         * @param parent is the nodes parent
         * @param left is the nodes left child
         * @param right is the nodes right child
         */
        public TreeNode(String element, TreeNode parent, TreeNode left, TreeNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        /**
         * Method for accessing the strings length
         * @return the length of the string as an int
         */
        public int getLength() {
            return element.length();
        }
    }

    /**
     * Class for a binary search tree, took inspiration from page 117 and 123 from the book
     */
    public static class Tree {
        TreeNode root;
        String[] treeString = new String[4];

        /**
         * Constructor
         */
        public Tree() {
            root = null;
        }

        /**
         * Checks if the Tree has a root-node
         * @return True if root is not set
         */
        public boolean empty() {
            return root==null;
        }

        /**
         * Method used to compare two strings alphabetically
         * @param a is the first string
         * @param b is the second string
         * @return true if a comes before b alphabetically
         */
        public boolean compareStrings(String a, String b) {
            if (a.isBlank()) {
                return true;
            } else if (b.isBlank()) {
                return false;
            }

            char aChar = a.toUpperCase(Locale.ROOT).charAt(0);
            int aValue;
            if (aChar == 'Æ') {                                     //Æ, Ø and Å must be specified
                aValue = 100;
            } else if (aChar == 'Ø') {
                aValue = 101;
            } else if (aChar == 'Å') {
                aValue = 102;
            } else {
                aValue = aChar;
            }

            char bChar = b.toUpperCase(Locale.ROOT).charAt(0);
            int bValue;
            if (bChar == 'Æ') {
                bValue = 100;
            } else if (bChar == 'Ø') {
                bValue = 101;
            } else if (bChar == 'Å') {
                bValue = 102;
            } else {
                bValue = bChar;
            }

            if (aValue == bValue) {
                StringBuilder sb1 = new StringBuilder(a);
                StringBuilder sb2 = new StringBuilder(b);
                return compareStrings(sb1.deleteCharAt(0).toString(), sb2.deleteCharAt(0).toString());
            } else {
                return aValue < bValue;
            }
        }

        /**
         * Inserts a node into the tree with the
         * correct correspondents
         * @param e Element to create a node for
         */
        public void insert(String e) {
            TreeNode n = root;
            if (root == null) {
                root = new TreeNode(e, null, null, null);
                return;
            }

            TreeNode f = null;
            while (n != null) {
                f = n;
                if (compareStrings(e, n.element)) n = n.left;
                else n = n.right;
            }

            if (compareStrings(e, f.element)){
                f.left = new TreeNode(e, f, null, null);
            }
            else {
                f.right = new TreeNode(e,f,null,null);
            }
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
            if (empty()) {
                return "The tree currently has no nodes.";
            }

            treeString = new String[]{"", "", "", ""};
            writeTree(root, 0);
            System.out.println(treeString[0]);
            System.out.println(treeString[1]);
            System.out.println(treeString[2]);
            System.out.println(treeString[3]);
            return "";
        }

        /**
         * Creates a visual representation of the tree,
         * only generates for depth 0,1,2 and 3
         * @param node Node to write
         * @param depth Node's currently depth level
         */
        public void writeTree(TreeNode node, int depth) {
            if (depth == 4) {
                return;
            }
            if (node == null) {
                treeString[depth] += space((int) (64/Math.pow(2,depth)));
                writeTree(null, depth+1);
                writeTree(null, depth+1);
            }
            else {
                treeString[depth] += space((int) (64/Math.pow(2,depth))/2 - node.getLength()/2) + node.element + space((int) (64/Math.pow(2,depth))/2 - node.getLength()/2);
                writeTree(node.left, depth+1);
                writeTree(node.right, depth+1);
            }
        }
    }

    /**
     * main-method
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("----- EXERCISE 2 PART 2 -----\n");
        Scanner sc = new Scanner(System.in);
        String input;
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
        System.exit(0);
    }
}
