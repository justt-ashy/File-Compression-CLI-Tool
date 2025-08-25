// import java.util.*;
public class SinglyLinkedList<T> {
    private LLNode<T> head = null;
    private LLNode<T> tail = null;
    private int size = 0;

    
    private static class LLNode<E>{
        LLNode<E> next;
        E data;

        LLNode(E data){
            this.data = data;
            next = null;
        }
    }
 
    //To check if the list is empty
    public boolean isEmpty(){
        return head == null;
    }

    //To return the size of the list
    public int size(){
        return size;
    }

    //To return the head of the list
    public T getHeadData(){
        return (T) head.data;
    }

    //To return the tail of the list
    public T getTailData(){
        return (T) tail.data;
    }

    // Adds the specified data as a new node at the end of the linked list.
    public void addLast(T data){
        LLNode<T> newest = new LLNode<>(data);
        if(isEmpty()) head = tail = newest;
        else{
            tail.next = newest;
            tail = newest;
        }
        size++;
    }

    
    // Removes and returns the last element from the linked list.
    public T removelast(){
        if(isEmpty()){
            throw new RuntimeException("Error: List empty");
        }
        T tail_data = (T) tail.data;
        LLNode<T> tail_prev = head;
        while(tail_prev.next != tail){
            tail_prev = tail_prev.next;
        }
        tail_prev.next = null;
        tail = tail_prev;
        --size;
        return tail_data;
    }

    // Removes and returns the first element from the linked list; throws an exception if empty.
    public T removeFirst(){
        if(isEmpty()){
            throw new RuntimeException("Error: List empty");
        }
        T head_data = (T) head.data;
        --size;
        head = head.next;
        return head_data;
    }
}
