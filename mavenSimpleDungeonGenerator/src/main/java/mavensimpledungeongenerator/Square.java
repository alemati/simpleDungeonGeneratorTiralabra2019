package mavensimpledungeongenerator;

enum SquareStatus {
    Wall, Room, Open, Maze, Door;
}

public class Square {

    /*Strings to desplay colors*/
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private int HeightX;
    private int WidthY;
    private Boolean isConnected;
    private Square parentRoomSq;
    private Boolean isParentRoomSq;
    private Boolean partOfRoom;
    private Boolean checked;

    private SquareStatus status;

    public Square(int x, int y) {
        this.HeightX = x;
        this.WidthY = y;
        this.status = SquareStatus.Open;
        this.parentRoomSq = null;
        this.isConnected = false;
        this.isParentRoomSq = false;
        this.partOfRoom = false;
        this.checked = false;
    }
    
    public void setChecked() {
        this.checked = true;
    }
    
    public boolean getChecked() {
        return this.checked;
    }
    
    public void setStatus(SquareStatus sqSt) {
        this.status = sqSt;
    }

    public SquareStatus getStatus() {
        return this.status;
    }

    public String getSymbol() {
        if (this.status.equals(SquareStatus.Wall)) {
            return "#";
        } else if (this.status.equals(SquareStatus.Maze)) {
            return " ";
        } else if (this.status.equals(SquareStatus.Door)) {
            return "+";
        } else if (this.status.equals(SquareStatus.Room)) {
            return " ";
        }
        return "#"; // this one has to be "Open" -> #
    }
    
    public String getColoredSymbol() {
        if (this.isParentRoomSq == true && this.checked == false) {
            return "█";
        }
        if (this.status.equals(SquareStatus.Wall)) {
            return RED + "#";
        } else if (this.status.equals(SquareStatus.Maze)) {
            return GREEN + " ";
        } else if (this.status.equals(SquareStatus.Door)) {
            return GREEN + "+";
        } else if (this.status.equals(SquareStatus.Room)) {
            return GREEN + " ";
        }
        return BLACK + "#"; // this one has to be "Open" -> #
    }

    public boolean isConnectedToMaze() {
        return this.isConnected;
    }

    public void connectToMaze() {
        this.isConnected = true;
    }

    public void connectToRoom() {
        this.partOfRoom = true;
    }

    public boolean isPartOfRoom() {
        return this.partOfRoom;
    }

    public void setParentRoomSq(Square parentSq) {
        this.parentRoomSq = parentSq;
    }

    public Square getParentRoomSq() {
        return this.parentRoomSq;
    }

    public void setToBeParentRoomSq() {
        this.isParentRoomSq = true;
    }

    public boolean isParentRoomSq() {
        return this.isParentRoomSq;
    }

    public int getHeightX() {
        return this.HeightX;
    }

    public int getWidthY() {
        return this.WidthY;
    }

}
