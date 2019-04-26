package mavensimpledungeongenerator;

/**
 * Selfmade Stack.
 *
 */
public class MyStack {

    private Object[] spots;
    private int indexOfLastObject;
    private int size;

    public MyStack() {
        this.spots = new Object[10];
        this.size = 10;
        this.indexOfLastObject = -1;
    }

    /**
     * Returns last object from the stack.
     *
     */
    public Object pop() {
        if (this.indexOfLastObject >= 0) {
            Object obj = this.spots[this.indexOfLastObject];
            this.spots[this.indexOfLastObject] = null;
            this.indexOfLastObject--;
            return obj;
        }
        return null;
    }
    
    /**
     * Pushes given object into the stack.
     * 
     *
     *  @param obj Object
     */
    public void push(Object obj) {
        if (this.indexOfLastObject + 1 >= this.size - 1) {
            addSize();
        }
        this.spots[this.indexOfLastObject + 1] = obj;
        this.indexOfLastObject++;
    }

    /**
     * Increases stack capacity.
     * 
     *
     */
    public void addSize() {
        int newSize = this.size * 2;
        this.size = newSize;
        Object[] newSpots = new Object[newSize];
        for (int i = 0; i <= this.indexOfLastObject; i++) {
            newSpots[i] = this.spots[i];
        }
        this.spots = newSpots;
    }

    public boolean isEmpty() {
        if (this.indexOfLastObject == -1) {
            return true;
        }
        return false;
    }

}
