public class HuffmanTree {
    private Node root;
    private Message sequence;

    //constructs a Huffman tree from the give message; throws exception if empty
    HuffmanTree(Message sequence) throws RuntimeException{
        if(sequence.isEmpty()){
            throw new RuntimeException("Error: Sequence of length 0");
        }
        this.sequence = sequence;
        root = buildHuffmanTree();
    }

    //returns the message used to build the tree
    public Message getMessage(){
        return sequence;
    }

    //builds and returns the Huffman tree from the message frequencies
    public Node buildHuffmanTree(){
        MinPriorityQueue<Node> priorityQueue = populatePQ();
        if(priorityQueue.len()==1){
            priorityQueue.add(new Node('\0',1,null,null));
        }

        while(priorityQueue.len()>1){
            final Node left = priorityQueue.poll();
            final Node right = priorityQueue.poll();
            final Node parent = new Node('\0', left.frequency + right.frequency, left, right);

            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    //populates a priority queue with nodes for each character and its frequency
    private MinPriorityQueue<Node> populatePQ(){
        MinPriorityQueue<Node> pq = new MinPriorityQueue<>();
        int[] freqs = sequence.getFrequencies();
        for(char i=0; i< freqs.length; i++){
            if(freqs[i]>0){
                pq.add(new Node(i, freqs[i],null, null));
            }
    }
        return pq;
    }

    //prints the tree nodes in in-order transversal
    public void inOrder(){
        inOrder(root);
        System.out.println();
    }

    //prints the tree nodes in pre-order transversal
    public void preOrder(){
        preOrder(root);
        System.out.println();
    }

    //prints the tree nodes in post-order transversal
    public void postOrder(){
        postOrder(root);
        System.out.println();
    }

    //Helper for in-order transversal
    public void inOrder(Node node){
        if(node==null) return;
        inOrder(node.left);
        System.out.println(node.frequency + " ");
        inOrder(node.right);
    }

    //Helper for pre-order transversal
    public void preOrder(Node node){
        if(node==null) return;
        System.out.println(node.frequency + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    //Helper for post-order transversal
    public void postOrder(Node node){
        if(node != null){
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.frequency + " ");
        }
    }

    //Prints the tree nodes in level-order (breadth-first) transversal 
    public void levelOrder(){
        QueueLL<Node> q = new QueueLL<>();
        q.offer(root);
        Node trav;

        while(!q.isEmpty()){
            trav = q.poll();
            if(trav!=null){
                System.out.println(trav.frequency + " ");
                q.offer(trav.left);
                q.offer(trav.right);
            }
        }
        System.out.println();
    }

    //returns the root of Huffman Tree
    public Node getRoot(){
        return root;
    }
}  