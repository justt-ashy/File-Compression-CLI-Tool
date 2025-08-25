public class huffmanEncoder {
    private String text;
    private Message msgObject;
    private HuffmanTree huffmanTree;
    private CharLinkedList charset;
    private boolean hasBeenCompressed;
    
    //Constructor a huffmanEncoder with the given text
    public huffmanEncoder(String text){
        this(new Message(text));
    }

    //Constructor a huffmanEncoder with given Message object
    public huffmanEncoder(Message msg){
        this.msgObject = msg;
        this.text = msgObject.getMessage();
        huffmanTree = new HuffmanTree(msgObject);
        charset = new CharLinkedList();
        hasBeenCompressed = false;
    }

    //Compress the message using Huffman encoding
    public void compress(){
        lookUp(huffmanTree.getRoot(), "");
        hasBeenCompressed = true;
    }

    //Recursively building a lookUp table from a huffman tree
    public void lookUp(Node node, String s){
        if(!node.isLeaf()){
            lookUp(node.left, s+ "0");
            lookUp(node.right, s + "1");
        }
        else{
            charset.add(node.character,s, node.frequency);
        }
    }

    //Returns the percentage of decrease in the size of message
    public double howMuchCompressed(){
        if(!hasBeenCompressed)
            throw new RuntimeException("Error: Message has not been compressed");
        return ((msgObject.getSize() - getSizeOfSequence()) / (double) msgObject.getSize()) * 100; 
    }

    //Returns the character set with Huffman Codes
    public CharLinkedList get_charset(){
        return charset;
    }

    //Returns the binary code for each character in the message
    public String[] compressedBinaryCode(){
        if(!hasBeenCompressed)
            throw new RuntimeException("Error: Message has not been compressed!");
        String[] d = new String[text.length()];
        for(int i=0; i < text.length(); i++){
            d[i] = charset.getCharNode(text.charAt(i)).bit_size;
        }
        return d;
    }

    //Prints the huffman code for each character in the charset
    public void indivSequence(){
        if(!hasBeenCompressed)
            throw new RuntimeException("Error: Message has not been compressed!");
        CharNode trav = charset.getLink();
        while(trav != null){
            System.out.println(trav.ch + ": " + trav.bit_size);
            trav = trav.next;
        }
    }

    //return the total size of the encoded message
    public int getSizeOfSequence(){
        if(!hasBeenCompressed)
            throw new RuntimeException("Error: Message has not been compressed!");
        int totalSize = 0;
        int bits = 0;
        CharNode n = charset.getLink();
        while(n != null){
            totalSize += n.frequency * n.bit_size.length();
            bits += n.bit_size.length();
            n = n.next;
        }
        totalSize += (Message.CHAR_SIZE * charset.size()) + bits;
        return totalSize;
    }
}
