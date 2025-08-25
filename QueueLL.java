//Implements a queue using singly linked list
public class QueueLL<T> {
    private SinglyLinkedList<T> list;

    //constructs an empty queue
    QueueLL(){
        list = new SinglyLinkedList<>();

    }

    //checks if queue is empty
    public boolean isEmpty(){
        return list.isEmpty();
    }

    //returns number of elements in queue
    public int size(){
        return list.size();
    }

    //adds an element to end of queue
    public void offer(T elem){
        list.addLast(elem);
    }

    //removes and returns element at front of queue
    public T poll(){
        return list.removeFirst();
    }

    //returns element at front of queue without removing it
    public T peek(){
        return list.getHeadData();
    }
}
