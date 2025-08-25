import java.util.*;

//Implements a min-heap priority queue for comparable types
public class MinPriorityQueue<T extends Comparable<T>> {
    private int size = 0;
    private List<T> list;

    //constructs an empty priority queue
    MinPriorityQueue(){
        list = new ArrayList<>();
    }

    //returns the number of elements in queue
    public int len(){return size;}

    //checks if queue is empty
    public boolean isEmpty(){ return size==0;}
    
    //removes and returns smallest element
    public T poll(){return poll(0);}

    //adds an element to queue and maintains heap order
    public void add(T elem){
        list.add(elem);
        siftUp(size);
        size++;
    }
    
    //removes and returns element at specified index, then maintains heap order
    public T poll(int i){
        if(size==1){
            size--;
            T data = list.get(0);
            list.clear();
            return data;
        }
        size--;
        T data_to_return = list.get(i);
        swap(i,size);
        list.remove(list.get(size));
        siftDown(i);
        return data_to_return;
    }

    //moves element at index k down to maintain heap order
    private void siftDown(int k){
        while(true){
            int left = (k*2) + 1;
            int right = (k*2) + 2;
            int smallest = left;
            if(right<size){
                if(isLess(right,left)) smallest = right;
            }
            if(smallest < size){
                if(isLess(k, smallest)) break;
                swap(k,smallest);
                k = smallest;
            }
            else break;
        }
    }

    //moves element at index k up to maintain heap order
    private void siftUp(int k){
        int parent_idx = (k-1) / 2;
        while(k>0 && isLess(k,parent_idx)){
            swap(k, parent_idx);
            k = parent_idx;
            parent_idx = (k-1)/2;
        }
    }

    //returns true if element at index i is less than or equal to at index j
    private boolean isLess(int i, int j){
        T elem0 = list.get(i);
        T elem1 = list.get(j);

        return elem0.compareTo(elem1) <=0;
    }

    //swaps elements at indices i and j
    private void swap(int i, int j){
        T elem_i = list.get(i);
        T elem_j = list.get(j);

        list.set(i, elem_j);
        list.set(j, elem_i);
    }
    
    //returns string representation of queue
    public String toString(){
        return list.toString();
    }
}
