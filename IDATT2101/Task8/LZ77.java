package IDATT2101.Task8;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for LempelZiv77 compression
 */
public class LZ77 {
    private char[] charData;

    /**
     * Empty constructor
     */
    public LZ77() {}

    /**
     * Method for compressing a file with LZ77
     * @param path Path to the file that's going to be compressed
     * @return Bytearray of compressed bytes
     * @throws IOException If file reading went wrong
     */
    public byte[] compress(String path) throws IOException {
        DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
        charData = new char[inStream.available()]; //Set the size of data-array as the size of the input stream

        ArrayList<Byte> compressedBytes = new ArrayList<>();

        //Before compressing
        // We use charset ISO 8859-1 since it's a single byte encoding
        String text = new String(inStream.readAllBytes(), StandardCharsets.ISO_8859_1); //Read all input bytes
        charData = text.toCharArray();

        //Stores all variables that can't be compressed
        StringBuilder incompressible = new StringBuilder();

        int i = 0;
        while (i < charData.length) {
            Pointer pointer = getPointer(i);
            if (pointer != null) {  //If a pointer was found
                if (incompressible.length() != 0) {
                    compressedBytes.add((byte) (incompressible.length()));  //Writes length of sequence of incompressible bytes
                    for (int j = 0; j < incompressible.length(); j++) {     //Add the incompressibles
                        compressedBytes.add((byte) incompressible.charAt(j));
                    }
                    incompressible = new StringBuilder();
                }

                compressedBytes.add((byte) ((pointer.getDist() >> 4) | (1 << 7)));
                compressedBytes.add((byte) (((pointer.getDist() & 0x0F) << 4) | (pointer.getLength() - 1)));
                i += pointer.getLength();
            }
            else {                  //If no pointer was found
                incompressible.append(charData[i]);

                if (incompressible.length() == 127) {   //If the size becomes 127 (111 1111)
                    compressedBytes.add((byte) (incompressible.length()));  //Writes length of sequence of incompressible bytes
                    for (int j = 0; j < incompressible.length(); j++) {     //Add the incompressibles
                        compressedBytes.add((byte) incompressible.charAt(j));
                    }
                    incompressible = new StringBuilder();
                }
                i += 1;
            }
        }
        if (incompressible.length() != 0) {
            compressedBytes.add((byte) (incompressible.length()));  //Writes length of sequence of incompressible bytes
            for (int j = 0; j < incompressible.length(); j++) {     //Add the incompressibles
                compressedBytes.add((byte) incompressible.charAt(j));
            }
        }

        inStream.close();
        return toByteArray(compressedBytes);
    }

    /**
     * Method for decompressing using LZ77
     * @param bytes Bytes to decompress
     * @param outPath Path to output file
     * @throws IOException If file writing/reading went wrong
     */
    public void decompress(byte[] bytes, String outPath) throws IOException {
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outPath)));

        ArrayList<Byte> b = new ArrayList<>();
        int currentIndex = 0;

        int i = 0;
        if (i < bytes.length - 1) {
            do {
                byte condition = bytes[i];
                if (condition >= 0) { //Number of uncompressed bytes
                    System.out.println(condition);
                    for (int j = 0; j < condition; j++) {
                        b.add(bytes[i + j + 1]);
                    }
                    currentIndex += condition;
                    i += condition + 1;
                } else { //Read a pointer
                    int jump = ((condition & 127) << 4) | ((bytes[i + 1] >> 4) & 15);
                    int distance = (bytes[i + 1] & 0x0F) + 1; //Distance of pointer

                    for (int j = 0; j < distance; j++) {
                        b.add(b.get(currentIndex - jump + j));
                    }
                    currentIndex += distance;
                    i += 2; //Pointer skips 2 bytes
                }
            } while (i < bytes.length - 1);
        }
        for (i = 0; i < currentIndex; i++) {
            outStream.write(b.get(i));
        }
        outStream.flush();
        outStream.close();
    }

    /**
     * Method for converting a list to a byte-array
     * @param list List to convert to a byte-array
     * @return byte-array
     */
    public byte[] toByteArray(ArrayList<Byte> list) throws IOException{
        byte[] byteArray = new byte[list.size()];
        for (int i = 0; i < list.size(); i++){
            byteArray[i] = list.get(i);
        }
        return byteArray;
    }

    /**
     * Method for finding the pointer for the position of the current index in the array
     * @param currIndex is the current index
     * @return the pointer
     */
    private Pointer getPointer(int currIndex){
        Pointer pointer = new Pointer();

        int POINTERSIZE = 15; // 4 bits
        int maxIndex = currIndex + POINTERSIZE;
        if (maxIndex > charData.length -1) maxIndex = charData.length -1;
        
        int BUFFERSIZE = 2047; // 11 bits
        int minIndex = currIndex - BUFFERSIZE;
        if (minIndex < 0) minIndex = 0;

        char[] searchBuffer = Arrays.copyOfRange(charData, minIndex, currIndex);

        int MIN_POINTER_SIZE = 3;
        int i = currIndex + MIN_POINTER_SIZE -1;

        outerLoop:
        while(i <= maxIndex){
            char[] keyWord = Arrays.copyOfRange(charData, currIndex, i + 1);
            int j = 0;
            if (keyWord.length + j <= searchBuffer.length) {
                do {
                    int k = keyWord.length - 1;
                    while (k >= 0 && keyWord[k] == searchBuffer[j + k]) {
                        k--;
                    }
                    if (k < 0) {
                        pointer.setDist(searchBuffer.length - j);
                        pointer.setLength(keyWord.length);
                        i++;
                        continue outerLoop;
                    } else {
                        int l = k - 1;
                        while (l >= 0 && keyWord[l] != searchBuffer[j + k]) {
                            l--;
                        }
                        j += k - l;
                    }
                } while (keyWord.length + j <= searchBuffer.length);
            }
            break;
        }
        if (pointer.getLength() > 0){
            return pointer;
        }
        return null;
    }


    /**
     * Class representing a pointer to a position in the array
     */
    private static class Pointer {
        private int length; //The length of the text we want to compress
        private int dist; //The distance from the current position

        /**
         * Constructor setting the initial pointer with values as -1
         */
        public Pointer(){
            this(-1,-1);
        }

        /**
         * Constructor, setting the length and distance
         * @param length is the length of the text
         * @param distance distance from the current position
         */
        public Pointer(int length, int distance){
            super();
            this.length = length;
            this.dist = distance;
        }

        public int getLength(){
            return length;
        }

        public int getDist(){
            return dist;
        }

        public void setLength(int length){
            this.length = length;
        }

        public void setDist(int distance){
            this.dist = distance;
        }
    }
}
