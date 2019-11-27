package solutions;

import java.lang.*;
import java.util.Arrays;

/**
 * Implementation of the min-max heap
 *
 * @author Ante Skoric, Timo Quednau
 * with the help of Phu Binh Dang(Dabi)
 */


public class MinMaxHeap<Key> {

    private Key[] heap;
    private int size;


    public MinMaxHeap(){
        this.heap = (Key[]) new Object[10];
    }
    public MinMaxHeap(int cap){
        this.heap = (Key[]) new Object[cap + 1];
    }

    /**
     * Insert a key into the Heap
     * O(logN)
     * @param key to be inserted
     */
    public void insert(Key key){
        if(this.size == this.heap.length - 1)
            resize(this.size * 2);
        this.heap[++this.size] = key;
        pushUp(this.size);
    }

    /**
     * Get the min key in the heap
     * O(1)
     * @return the min key
     */
    public Key min(){
        return this.heap[1];
    }

    /**
     * Get the max key in the heap
     * O(1)
     * @return the max key
     */
    public Key max(){
        return this.heap[greatestChild(1)];
    }

    /**
     * Deletes the max key in the heap
     * O(logN)
     */
    public void delMax(){
        if(isEmpty())
            throw new EmptyHeapException("The heap is empty");
        int maxIndex = greatestChild(1);
        this.heap[maxIndex] = this.heap[this.size];
        this.heap[this.size] = null;
        this.size--;
        pushDown(maxIndex);
    }

    /**
     * Deletes the min key in the heap
     * O(logN)
     */
    public void delMin(){
        if(isEmpty())
            throw new EmptyHeapException("The heap is empty");
        this.heap[1] = this.heap[this.size];
        this.heap[this.size] = null;
        this.size--;
        pushDown(1);
    }

    /**
     * Check if the heap is empty
     * @return true if empty else false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    public String toString(){
        return Arrays.toString(this.heap);
    }

    private void pushDown(int index){
        if(isMinLevel(index))
            pushDownMin(index);
        else
            pushDownMax(index);
    }

    private boolean isMinLevel(int index) {
        return ((int)(Math.log(index) / Math.log(2))) % 2 == 0;
    }

    /**
     * Pushes the key down after deleting
     * @param index the index of the key
     */
    private void pushDownMin(int index){
        if(hasChild(index)){
            int m = smallestSuccessor(index);
            if(isGrandChild(m,index)){
                if(smaller(m,index)){
                    exch(m,index);
                    if(greater(m,parent(m))){
                        exch(m,parent(m));
                    }
                    pushDownMin(m);
                }
            }else{
                if(smaller(m,index)){
                    exch(m,index);
                }
            }
        }

    }

    /**
     * Pushes the key down after deleting
     * @param index the index of the key
     */
    private void pushDownMax(int index){
        if(hasChild(index)){
            int m = greatestSuccessor(index);
            if(isGrandChild(m,index)){
                if(greater(m,index)){
                    exch(m,index);
                    if(smaller(m,parent(m))){
                        exch(m,parent(m));
                    }
                    pushDownMax(m);
                }
            }else{
                if(smaller(m,index)){
                    exch(m,index);
                }
            }
        }
    }

    /**
     * Pushes the key up after insertion
     * @param index the index of the key
     */
    private void pushUp(int index) {
        if (index != 1){
            int parent = parent(index);
            if(isMinLevel(index)){
                if(greater(index,parent)){
                    exch(index,parent);
                    pushUpMax(parent);
                }else{
                    pushUpMin(index);
                }
            }else{
                if(smaller(index,parent)){
                    exch(index,parent);
                    pushUpMin(parent);
                }else{
                    pushUpMax(index);
                }
            }
        }
    }

    private void pushUpMin(int index){
        if(hasGrandParent(index) && smaller(index,grandParent(index))){
            exch(index,grandParent(index));
            pushUpMin(grandParent(index));
        }
    }

    private void pushUpMax(int index){
        if(hasGrandParent(index) && greater(index,grandParent(index))){
            exch(index,grandParent(index));
            pushUpMax(grandParent(index));
        }
    }

    /**
     * Resize the array
     * @param newCap the new capacity of the array
     */
    private void resize(int newCap) {
        Key[] newHeap = (Key[]) new Object[newCap];
        if (this.size >= 0) System.arraycopy(this.heap, 0, newHeap, 0, this.size);
        this.heap = newHeap;
    }

    /**
     * Find index of the smallest child or grandchild of index
     */
    private int smallestSuccessor(int index){
        int smallest;
        if(hasOneChild(index)){
            smallest = 2 * index;
        }else{
            smallest = (smaller(2*index,2*index+1))? 2*index : 2*index + 1;

            if (hasChild(2 * index))
                smallest = smaller(smallest, smallerChild(2 * index))? smallest : smallerChild(2 * index);

            if(hasChild(2 * index + 1))
                smallest = smaller(smallest, smallerChild( 2 * index + 1))? smallest : smallerChild(2 * index + 1);
        }
        return smallest;
    }

    /**
     * Find index of the greatest child or grandchild of index
     */
    private int greatestSuccessor(int index) {
        int greatest;
        if(hasOneChild(index)){
            greatest = 2*index;
        }else{

            greatest= (greater(2*index,2*index+1))? 2*index : 2*index + 1;

            if(hasChild(2*index)){
                greatest = greater(greatest,greatestChild(2*index))? greatest : greatestChild(2*index);
            }
            if(hasChild(2*index + 1)){
                greatest = greater(greatest,greatestChild(2*index +1))? greatest : greatestChild(2*index +1);
            }
        }
        return greatest;

    }

    /**
     * Get the greater child of the key
     * @param i index of the key
     * @return the index of greater child
     */
    private int greatestChild(int i) {
        int child;
        if(hasOneChild(i))
            child = 2 * i;
        else
            child = (greater(2 * i, 2 * i + 1)) ? 2 * i : 2 * i + 1;
        return child;
    }

    /**
     * Get the smaller child of the key
     * @param i index of the key
     * @return the index of smaller child
     */
    private int smallerChild(int i){
        int smallest;
        if(hasOneChild(i))
            smallest = 2*i;
        else
            smallest = (smaller(2*i,2*i+1))? 2*i : 2*i + 1;
        return smallest;
    }


    private boolean greater(int indexOne, int indexTwo) {
        return ((Comparable<Key>)this.heap[indexOne]).compareTo(this.heap[indexTwo]) > 0;
    }

    private boolean smaller(int indexOne, int indexTwo){
        return ((Comparable<Key>)this.heap[indexOne]).compareTo(this.heap[indexTwo]) < 0;
    }

    private boolean hasChild(int i) {
        return 2 * i <= this.size;
    }

    private boolean hasOneChild(int i){
        return 2*i <= this.size && 2*i +1 > this.size;
    }

    private boolean hasGrandParent(int i){
        return i/4 > 0;
    }
    private int grandParent(int i){
        return i/4;
    }
    private int parent(int i) {
        return i / 2;
    }

    private boolean isGrandChild(int j, int i){
        return (j <= this.size && j >= 4*i && j<= 4*i + 3);
    }

    /**
     * Changes the value of the element on position indexOne to the value of the element on the position indexTwo
     * and the value of the element on position indexTwo to the value of the element on the position indexOne
     * @param indexOne
     * @param indexTwo
     */
    private void exch(int indexOne, int indexTwo) {
        Key swap = this.heap[indexOne];
        this.heap[indexOne] = this.heap[indexTwo];
        this.heap[indexTwo] = swap;
    }

}