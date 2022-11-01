package IDATT2101.Task8;

public class Decompressor {

    public Decompressor(){}

    public void decompress(String inPath, String outPath) {
        Huffman huffman = new Huffman();
        byte[] decompressedFile = huffman.decompress(inPath);
        LZ lz = new LZ();
        lz.decompress(decompressedFile, outPath);
    }
}
