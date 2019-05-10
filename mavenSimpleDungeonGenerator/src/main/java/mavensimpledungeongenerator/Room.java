package mavensimpledungeongenerator;

/**
 *
 * @author AM
 */
public class Room {
    
    private Square parentSquare;
    private int height;
    private int width;
    
    /**
     *
     * @param parentSquare
     * @param height
     * @param width
     */
    public Room(Square parentSquare, int height, int width) { 
        this.parentSquare = parentSquare;                           
        this.height = height;
        this.width = width;
    }
    
    /**
     *
     * @return
     */
    public Square getParentSquare() {
        return this.parentSquare;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return this.width;
    }
}