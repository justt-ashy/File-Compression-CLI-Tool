//Represents a node in linked list for storing character data,its Huffman code(bit_size), and frequency.
public class CharNode {
    CharNode next;
    char ch;
    String bit_size;
    int frequency = 0;

    CharNode(final char ch, final String code, final int frequency){
        this.ch = ch;
        this.bit_size = code;
        this.frequency = frequency;
        next = null;
    }
}
