package IDATT2101.Task8;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for Lempel Ziv compression
 */
public class LZ77 {
    private final int BUFFERSIZE = (1 << 11) - 1;
    private final int POINTERSIZE = (1 << 4) - 1;
    private final int MIN_POINTER_SIZE = 3;
    private char[] data;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    /**
     * Empty constructor
     */
    public LZ77() {}

    public byte[] compress(String path) throws IOException {
        inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
        data = new char[inputStream.available()]; //Set the size of data-array as the size of the input stream

        ArrayList<Byte> compressedBytes = new ArrayList<>();

        //Before compressing
        // We use charset ISO 8859-1 since it's a single byte encoding
        String text = new String(inputStream.readAllBytes(), StandardCharsets.ISO_8859_1); //Read all input bytes
        data = text.toCharArray();

        //Stores all variables that can't be compressed
        StringBuilder incompressible = new StringBuilder();

        for (int i = 0; i < data.length;) {
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
                incompressible.append(data[i]);

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

        inputStream.close();
        return toByteArray(compressedBytes);
    }

    public void decompress(byte[] bytes, String outPath) throws IOException {
        outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outPath)));

        ArrayList<Byte> b = new ArrayList<>();
        int currentIndex = 0;

        int i = 0;
        while (i < bytes.length-1) {
            byte condition = bytes[i];
            if (condition >= 0) { //Number of uncompressed bytes
                System.out.println(condition);
                for (int j = 0; j < condition; j++) {
                    b.add(bytes[i+j+1]);
                }
                currentIndex += condition;
                i += condition + 1;
            }
            else { //Read a pointer
                int jump = ((condition & 127) << 4) | ((bytes[i+1] >> 4) & 15);
                int length = (bytes[i+1] & 0x0F) + 1; //Length of pointer

                for (int j = 0; j < length; j++) {
                    b.add(b.get(currentIndex - jump + j));
                }
                currentIndex += length;
                i += 2; //Pointer (2 bytes)
            }
        }
        for (i = 0; i < currentIndex; i++) {
            outputStream.write(b.get(i));
        }
        outputStream.flush();
        outputStream.close();
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
        int maxIndex = currIndex + POINTERSIZE;
        if (maxIndex > data.length -1) maxIndex = data.length -1;

        int minIndex = currIndex - BUFFERSIZE;
        if (minIndex < 0) minIndex = 0;

        char[] buffer = Arrays.copyOfRange(data, minIndex, currIndex);

        int i = currIndex + MIN_POINTER_SIZE -1;

        outerLoop:
        while(i <= maxIndex){
            char[] keyWord = Arrays.copyOfRange(data, currIndex, i + 1);
            int j = 0;
            while (keyWord.length + j <= buffer.length){
                int k = keyWord.length - 1;
                while (k >= 0 && keyWord[k] == buffer[j+k]){
                    k--;
                }
                if (k < 0){
                    pointer.setDist(buffer.length-j);
                    pointer.setLength(keyWord.length);
                    i++;
                    continue outerLoop;
                }
                else {
                    int l = k-1;
                    while (l >= 0 && keyWord[l] != buffer[j+k]){
                        l--;
                    }
                    j += k - l;
                }
            }
            break;
        }
        if (pointer.getLength() > 0 && pointer.getLength() > 0){
            return pointer;
        }
        return null;
    }


    /**
     * Class representing a pointer to a position in the array
     */
    private class Pointer{
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
