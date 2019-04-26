package mavensimpledungeongenerator;

public class Room {
    
    private Square parentSquare;
    private int height;
    private int width;
    
    public Room(Square parentSquare, int height, int width) { 
        this.parentSquare = parentSquare;                           
        this.height = height;
        this.width = width;
    }
    
    public Square getParentSquare() {
        return this.parentSquare;
    }

    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
}