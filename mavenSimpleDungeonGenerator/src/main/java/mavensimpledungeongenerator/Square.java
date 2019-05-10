package mavensimpledungeongenerator;

enum SquareStatus {
    Wall, Room, Open, Maze, Door;
}

public class Square {

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
