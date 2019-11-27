package solutions;

/**
 * Will be thrown if the heap is empty
 * @author Ante Skoric, Timo Quednau
 */
public class EmptyHeapException extends RuntimeException {
    public EmptyHeapException(){
        super();
    }
    public EmptyHeapException(String message){
        super(message);
    }
}
