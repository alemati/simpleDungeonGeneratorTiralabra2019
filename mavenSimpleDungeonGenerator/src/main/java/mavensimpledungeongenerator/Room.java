
package mavensimpledungeongenerator;


public class Room {
    private Square[][] squares;
    private Square upLeft;
    private Square bottomRight;
    private int height;
    private int width;
    
    public Room(Square upLeft, int height, int width) { 
        this.upLeft = upLeft;                           
        this.height = height;
        this.width = width;
    }
    
    public Square getUpLeft() {
        return this.upLeft;
    }

    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
}