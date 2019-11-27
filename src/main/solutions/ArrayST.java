package solutions;

/**
 *
 * This implementation is self organising symbol table
 * @author Ante Skoric, Timo Quednau
 *
 */
public class ArrayST<K, V> {
    private int size;
    private int cap;
    private V[] values;
    private K[] keys;

    public ArrayST(int cap) {
        this.cap = cap;
        values = (V[]) new Object[cap];
        keys = (K[]) new Object[cap];
    }

    /**
     * Puts the key and the value into the symbol table
     * @param key the key of the value
     * @param val the value
     */
    public void put(K key, V val) {
        if(key == null){
            throw new IllegalArgumentException("The key cannot be null");
        }
        if(val == null){
            delete(key);
            return;
        }
        for (int i = 0; i < size; i++) {
            if(keys[i] == key){
                values[i] = val;
                return;
            }
        }
        if (size == cap)
            resize(cap*2);
        keys[size] = key;
        values[size] = val;
        size++;
    }

    /**
     * Gets the value that is associated with the key
     * @param key the key
     * @return the value
     */
    public V get(K key) {
        if(key == null)
            throw new IllegalArgumentException("The key cannot be null");

        for(int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                if (i == 0) {
                    return values[i];
                }
                K tempKey = keys[i];
                V tempValue = values[i];
                for(int j = i; j > 0; j--) {
                    keys[j] = keys[j - 1];
                    values[j] = values[j - 1];
                }
                keys[0] = tempKey;
                values[0] = tempValue;
                return values[0];
            }
        }
        return null;
    }

    /**
     * Deletes a value that is associated with the key
     * @param key the key
     */
    public void delete(K key){
        if (key == null)
            throw new IllegalArgumentException("The key cannot be null");
        for (int i = 0; i < this.size; i++) {
            if(keys[i].equals(key)){
                //is a lot faster
                //keys[i] = keys[this.size - 1];
                //values[i] = values[this.size - 1];
                //keys[this.size - 1] = null;
                //values[this.size - 1] = null;
                keys[i] = null;
                values[i] = null;
                for(int j = i; j < this.size; j++) {
                    keys[j] = keys[j + 1];
                    values[j] = values[j + 1];
                }
                this.size--;
                break;
            }
        }
    }

    /**
     * Check if the symbol table contains the key
     * @param key key to be checked
     * @return true if the key is in the symbol table else false
     */
    public boolean contains(K key){
        for (int i = 0; i < this.size; i++) {
            if(keys[i].equals(key))
                return true;
        }
        return false;
    }

    /**
     * Number of the keys in the table
     * @return the size
     */
    public int size(){
        return this.size;
    }

    /**
     * Check if the table is empty
     * @return true if the table is empty else false
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cap; i++) {
            stringBuilder.append(values[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Resize the symbol table
     * @param newCap the new size of the symbol table
     */
    private void resize(int newCap) {
        ArrayST st = new ArrayST(newCap);
        for (int i = 0; i < this.size; i++) {
            st.put(keys[i], values[i]);
        }
        cap = newCap;
        values = (V[]) st.values;
        keys = (K[]) st.keys;
    }
}