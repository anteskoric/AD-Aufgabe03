package solutions;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Vector;

/**
 * The main class for execution of the code
 */
public class Main {
    public static void main(String[] args) {
        ArrayST<Integer,String> symbolTable = new ArrayST<>(10);
        symbolTable.put(21,"Ante");
        symbolTable.put(120,"Timo");
        symbolTable.put(65,"Tester");
        System.out.println(symbolTable);
        symbolTable.delete(21);
        System.out.println(symbolTable);
        System.out.println(symbolTable.get(120));

        Vector vector = new Vector(1,2,3);
        Distance distance = new Distance(vector);
        Distance.makeVectors(100);
        //The smallest element, distance is the comparator
        MinPQ<Vector> minPQ = Distance.readVectors(100,distance);
        for (Vector element : minPQ) {
            System.out.println("Distance to vector: " + vector.distanceTo(element));
        }

        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap(20);
        minMaxHeap.insert(10);
        System.out.println(minMaxHeap);
        minMaxHeap.insert(90);
        System.out.println(minMaxHeap);
        minMaxHeap.insert(8);
        System.out.println(minMaxHeap);
        minMaxHeap.insert(19);
        System.out.println(minMaxHeap);
        minMaxHeap.insert(37);
        minMaxHeap.insert(5);
        minMaxHeap.insert(1);
        System.out.println(minMaxHeap);
        System.out.println(minMaxHeap.max());
        System.out.println(minMaxHeap.min());
        minMaxHeap.delMin();
        System.out.println(minMaxHeap);
        minMaxHeap.delMax();
        System.out.println(minMaxHeap);
    }
}
