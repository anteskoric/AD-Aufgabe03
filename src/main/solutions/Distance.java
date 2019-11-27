package solutions;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Vector;

import java.io.*;
import java.util.Comparator;
import java.util.Random;

public class Distance implements Comparator<Vector> {

    Vector vector;

    public Distance(Vector vector) {
        this.vector = vector;
    }

    public static void makeVectors(int size) {
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vectors"))) {
            for (int i = 0; i < size; i++) {
                Vector vector = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble());
                writer.write(vector.toString());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MinPQ<Vector> readVectors(int cap,Distance base) {
        MinPQ<Vector> minPQ = new MinPQ(cap,base);
        try (BufferedReader reader = new BufferedReader(new FileReader("vectors"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split("\\s+");
                Vector vector = new Vector(Double.valueOf(part[0]), Double.valueOf(part[1]), Double.valueOf(part[2]));
                minPQ.insert(vector);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return minPQ;
    }

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p>
     * The implementor must ensure that {@code sgn(compare(x, y)) ==
     * -sgn(compare(y, x))} for all {@code x} and {@code y}.  (This
     * implies that {@code compare(x, y)} must throw an exception if and only
     * if {@code compare(y, x)} throws an exception.)<p>
     * <p>
     * The implementor must also ensure that the relation is transitive:
     * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
     * {@code compare(x, z)>0}.<p>
     * <p>
     * Finally, the implementor must ensure that {@code compare(x, y)==0}
     * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
     * {@code z}.<p>
     * <p>
     * It is generally the case, but <i>not</i> strictly required that
     * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."<p>
     * <p>
     * In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param vectorOne the first object to be compared.
     * @param vectorTwo the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */
    @Override
    public int compare(Vector vectorOne, Vector vectorTwo) {
        double distanceToOne = this.vector.distanceTo(vectorOne);
        double distanceToTwo = this.vector.distanceTo(vectorTwo);
        if ((distanceToOne - distanceToTwo) > 0)
            return 1;
        else if ((distanceToOne - distanceToTwo) < 0)
            return -1;
        else
            return 0;
    }
}
