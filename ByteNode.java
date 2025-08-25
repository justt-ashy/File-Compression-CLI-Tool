public  class ByteNode implements Comparable<ByteNode> {
    Byte data;
    int frequency;
    ByteNode left;
    ByteNode right;

    //constructs a new ByteNode with given byte and frequency
    public ByteNode(Byte data, int weight){
        this.data = data;
        this.frequency = weight;
    }
    
    //compares this node to another by frequency
    public int compareTo(ByteNode o){
        
        return this.frequency - o.frequency;
    }
}
