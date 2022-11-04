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
    private final int MIN_SIZE_POINTER = 3;
    private char[] data;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

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

        for (int i = 0; i < data.length; i++) {
            Pointer pointer = getPointer(i);
            if (pointer != null) {  //If a pointer was found
                if (incompressible.length() != 0) {
                    compressedBytes.add((byte) (incompressible.length()));  //Writes length of sequence of incompressible bytes
                    for (int j = 0; j < incompressible.length(); j++) {     //Add the incompressibles
                        compressedBytes.add((byte) incompressible.charAt(j));
                    }
                    incompressible = new StringBuilder();
                }

                compressedBytes.add((byte) ((pointer.getDistance() >> 4) | (1 << 7)));
                compressedBytes.add((byte) (((pointer.getDistance() & 0x0F) << 4) | (pointer.getLength() - 1)));
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

    private Pointer getPointer(int currIndex){
        Pointer pointer = new Pointer();
        int maxIndex = currIndex + POINTERSIZE;
        if (maxIndex > data.length -1) maxIndex = data.length -1;

        int minIndex = currIndex - BUFFERSIZE;
        if (minIndex < 0) minIndex = 0;

        char[] buffer = Arrays.copyOfRange(data, minIndex, currIndex);

        int i = currIndex + MIN_SIZE_POINTER -1;

        outerWhile:
        while(i <= maxIndex){
            char[] keyWord = Arrays.copyOfRange(data, currIndex, i +1);
            int j = 0;
            while (keyWord.length + j <= buffer.length){
                int k = keyWord.length -1;
                while (k <= 0 && keyWord[k] == buffer[j+k]){
                    k--;
                }
                if (k < 0){
                    pointer.setDistance(buffer.length-j);
                    pointer.setLength(keyWord.length);
                    i++;
                    continue outerWhile;
                }
                else {
                    int l = k-1;
                    while (l >= 0 && keyWord[l] != buffer[j+k]){
                        l--;
                    }
                    j += k-l;
                }
            }
            break;
        }
        if (pointer.getLength() > 0 && pointer.getLength() > 0){
            return pointer;
        }
        return null;
    }



    private class Pointer{
        private int length;
        private int distance;

        /**
         * Constructor
         */
        public Pointer(){
            this(-1,-1);
        }

        public Pointer(int matchLength, int matDistance){
            super();
            this.length = matchLength;
            this.distance = matDistance;
        }

        public int getLength(){
            return length;
        }

        public int getDistance(){
            return distance;
        }

        public void setLength(int matchLength){
            this.length = matchLength;
        }

        public void setDistance(int matDistance){
            this.distance = matDistance;
        }

    }

}
