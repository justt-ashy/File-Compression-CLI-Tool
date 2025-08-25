public class CharLinkedList {
    private CharNode head = null, tail = null;
    private int size=0;


    //checks if linked list is empty
    public boolean isEmpty(){
        return head==null;
    }

    //returns the number of elements in the list
    public int size(){
        return size;
    }

    //adds a new character node with code and frequency to end of list
    public void add(char ch, String code,int frequency){
        CharNode newest = new CharNode(ch,code,frequency);
        if(isEmpty()) head = tail = newest;
        else{
            tail.next = newest;
            tail = newest;
        }
        size++;
    }

    //returns the node containing the specified character, or null if found
    public CharNode getCharNode(char ch){
        if(isEmpty()) return null;
        CharNode trav = head;
        while(trav != null){
            if(trav.ch == ch) break;
            trav = trav.next;
        }
        return trav;
    }

    //return the head of linked list
    public CharNode getLink(){
        return head;
    }
}
