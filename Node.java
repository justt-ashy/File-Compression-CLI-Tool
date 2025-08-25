//Represents a node in Huffman tree, storing a character,its frequency and child nodes
public class Node implements Comparable<Node>{
    Node left;
    Node right;
    char character;
    int frequency = 0;

    //constructs a node with given character, frequency and children
    Node(final char character, final int frequency, final Node left, final Node right){
        this.character = character;
        if(frequency<0) throw new IllegalArgumentException("Error: Frequency must be >= 0");
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    //returns true if node is a leaf (no children)
    public boolean isLeaf(){
        return this.left == null && this.right == null;
    }

    //compares this node to another by frequency 
    public int compareTo(final Node that){
        final int frequencyCompare = Integer.compare(this.frequency, that.frequency);
        if(frequencyCompare !=0){
            return frequencyCompare;
        }
        return Integer.compare(this.character, that.character);
    }

    //returns a string representation of the node
    public String toString(){
        String str = "" + character;
        return str;
    }
}
