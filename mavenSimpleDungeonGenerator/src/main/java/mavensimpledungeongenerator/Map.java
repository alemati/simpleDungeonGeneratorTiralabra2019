package mavensimpledungeongenerator;

/**
 *
 * @author AM
 */
public class Map {

    private int width;
    private int height;
    private Square[][] squares;
    private MyStack deadEnds;
    private int roomsPlaced;

    /**
     *
     * @param height
     * @param width
     */
    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.squares = new Square[this.height][this.width];
        this.deadEnds = new MyStack();
        this.roomsPlaced = 0;
        mapInit();
    }

    /**
     *
     * @return
     */
    public int getMapRoomsPlaced() {
        return this.roomsPlaced;
    }

    /**
     *
     * @return
     */
    public int getMapHeight() {
        return this.height;
    }

    /**
     *
     * @return
     */
    public int getMapWidth() {
        return this.width;
    }

    /**
     *
     * @return
     */
    public Square[][] getMapSquares() {
        return this.squares;
    }

    /**
     * This method initializes empty map.
     *
     *
     */
    public void mapInit() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (i == 0 || i >= this.height - 1 || j == 0 || j >= this.width - 1) { // making map borders
                    Square newSquare = new Square(i, j);
                    this.squares[i][j] = newSquare;
                    this.squares[i][j].setStatus(SquareStatus.Wall);
                } else {
                    Square newSquare = new Square(i, j);
                    this.squares[i][j] = newSquare;
                }
            }
        }
    }

    /**
     * This method prints dungeon map in console.
     *
     */
    public void showMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.squares[i][j].getSymbol());
            }
            System.out.println("");
        }
    }

    /**
     * This method tries to place given room on map if it is possible.
     *
     *
     * @param room Room
     */
    public void addRoom(Room room) {
        if (checkAreaForRoom(room)) {
            Square parentSquare = room.getParentSquare();
            this.roomsPlaced++;
            for (int i = room.getParentSquare().getHeightX() - 1; i < (room.getParentSquare().getHeightX() + room.getHeight()) + 1; i++) {
                for (int j = room.getParentSquare().getWidthY() - 1; j < (room.getParentSquare().getWidthY() + room.getWidth()) + 1; j++) {
                    if (squareIsOnRoomBorder(room, this.squares[i][j])) {           // room borders
                        if (squareIsOnRoomAngle(room, this.squares[i][j])) {        // room angle -> can't be a door 
                            this.squares[i][j].setStatus(SquareStatus.Wall);        // -> is not a part of the room
                            continue;
                        }
                        this.squares[i][j].setStatus(SquareStatus.Wall);
                        this.squares[i][j].setParentRoomSq(parentSquare);
                        this.squares[i][j].connectToRoom();
                        continue;
                    }
                    // room space
                    if (i == parentSquare.getHeightX() && j == parentSquare.getWidthY()) {
                        this.squares[i][j].setToBeParentRoomSq();
                    }
                    this.squares[i][j].setStatus(SquareStatus.Room);
                }
            }
        }
    }

    /**
     * This method checks if suggested area is available for the given room.
     *
     *
     * @param room Room
     * @return 
     */
    public boolean checkAreaForRoom(Room room) {
        for (int i = room.getParentSquare().getHeightX() - 2; i < (room.getParentSquare().getHeightX() + room.getHeight() + 2); i++) {
            for (int j = room.getParentSquare().getWidthY() - 2; j < (room.getParentSquare().getWidthY() + room.getWidth() + 2); j++) {
                if (i >= this.height - 1 || j >= this.width - 1 || i < 1 || j < 1) {
                    return false;
                } else if (!this.squares[i][j].getStatus().equals(SquareStatus.Open)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks if given square is on room border.
     *
     *
     * @param room Room
     * @param sq Square
     * @return 
     */
    public boolean squareIsOnRoomBorder(Room room, Square sq) {
        int i = sq.getHeightX();
        int j = sq.getWidthY();
        if (i == room.getParentSquare().getHeightX() - 1
                || j == room.getParentSquare().getWidthY() - 1
                || i == (room.getParentSquare().getHeightX() + room.getHeight())
                || j == (room.getParentSquare().getWidthY() + room.getWidth())) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if given square is on room angle.
     *
     *
     * @param room Room
     * @param sq Square
     * @return 
     */
    public boolean squareIsOnRoomAngle(Room room, Square sq) {
        int i = sq.getHeightX();
        int j = sq.getWidthY();
        if (i == room.getParentSquare().getHeightX() - 1 && j == room.getParentSquare().getWidthY() - 1) {
            this.squares[i][j].setStatus(SquareStatus.Wall);
            return true;
        }
        if (i == room.getParentSquare().getHeightX() - 1 && j == room.getParentSquare().getWidthY() + room.getWidth()) {
            this.squares[i][j].setStatus(SquareStatus.Wall);
            return true;
        }
        if (i == room.getParentSquare().getHeightX() + room.getHeight() && j == room.getParentSquare().getWidthY() - 1) {
            this.squares[i][j].setStatus(SquareStatus.Wall);
            return true;
        }
        if (i == room.getParentSquare().getHeightX() + room.getHeight() && j == room.getParentSquare().getWidthY() + room.getWidth()) {
            this.squares[i][j].setStatus(SquareStatus.Wall);
            return true;
        }
        return false;
    }

    /**
     * This method finds "open" square that can be used as wall/room/maze.
     *
     *
     * @return 
     */
    public Square findOpenSquare() {
        for (int i = 1; i < this.height; i++) {
            for (int j = 1; j < this.width; j++) {
                if (this.squares[i][j].getStatus().equals(SquareStatus.Open)) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

    /**
     * This method triggers iterative flood fill alghoritm to fill all open
     * space with passageway.
     *
     */
    public void floodFill() {
        Square sq = findOpenSquare();
        if (!this.squares[sq.getHeightX() + 1][sq.getWidthY()].isPartOfRoom() // check if first square will be a deadend
                && !this.squares[sq.getHeightX() - 1][sq.getWidthY()].isPartOfRoom()
                && !this.squares[sq.getHeightX()][sq.getWidthY() + 1].isPartOfRoom()
                && !this.squares[sq.getHeightX()][sq.getWidthY() - 1].isPartOfRoom()) {
            this.deadEnds.push(sq);
        }
        if (sq != null) {
            iterativeFloodFill(sq);
        }
    }

    /**
     * Iterative flood fill alghoritm. Starts from the given square.
     *
     *
     * @param sq Square
     */
    public void iterativeFloodFill(Square sq) {
        MyStack myStack = new MyStack();
        myStack.push(sq);

        while (!myStack.isEmpty()) {
            Square square = (Square) myStack.pop();

            if (canBeAPartOfPassageway(square)) {
                square.setStatus(SquareStatus.Maze);

                Square doorSquare = couldBeADoor(square);    // check if door can be placed nearby
                if (doorSquare != null) {
                    Square parent = doorSquare.getParentRoomSq();
                    parent.connectToMaze();
                    doorSquare.setStatus(SquareStatus.Door);
                }

                if (willBeADeadEnd(square)) {               // program keeps track of all dead ends 
                    this.deadEnds.push(square);             // in order to delete them quickly later on
                }

                Square[] neighbours = neighbourArray(square);   // pushing all valid neighbour squares into myStack
                for (int i = 0; i < 4; i++) {                   // for continuing building a passageway
                    if (neighbours[i] != null) {
                        myStack.push(neighbours[i]);
                    }
                }
            }
        }

    }

    /**
     * This method returns array that contains neighbours of given square.
     *
     *
     * @param square
     * @return 
     */
    public Square[] neighbourArray(Square square) {
        Square[] neighbours = new Square[4];
        // square can have only 4 neighbours
        if (!outOfBounds(this.squares[square.getHeightX() + 1][square.getWidthY()])) {
            neighbours[0] = this.squares[square.getHeightX() + 1][square.getWidthY()];
        } else {
            neighbours[0] = null;
        }
        if (!outOfBounds(this.squares[square.getHeightX() - 1][square.getWidthY()])) {
            neighbours[1] = this.squares[square.getHeightX() - 1][square.getWidthY()];
        } else {
            neighbours[1] = null;
        }
        if (!outOfBounds(this.squares[square.getHeightX()][square.getWidthY() + 1])) {
            neighbours[2] = this.squares[square.getHeightX()][square.getWidthY() + 1];
        } else {
            neighbours[2] = null;
        }
        if (!outOfBounds(this.squares[square.getHeightX()][square.getWidthY() - 1])) {
            neighbours[3] = this.squares[square.getHeightX()][square.getWidthY() - 1];
        } else {
            neighbours[3] = null;
        }
        shuffleArray(neighbours);
        return neighbours;
    }

    /**
     * This method checks if given square is within map borders.
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean outOfBounds(Square sq) {
        if (sq.getHeightX() >= this.height - 1 || sq.getHeightX() <= 0
                || sq.getWidthY() >= this.width - 1 || sq.getWidthY() <= 0) {
            return true;

        }
        return false;
    }

    /**
     * This method checks if given square is in touch with passageway and
     * returns true if it is in touch with only one "maze" square, meaning given
     * square can be a part of passageway too.
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean canBeAPartOfPassageway(Square sq) {
        int n = 0;
        if (!sq.getStatus().equals(SquareStatus.Open)) {
            return false;
        }
        if (this.squares[sq.getHeightX() + 1][sq.getWidthY()].getStatus().equals(SquareStatus.Maze)) {
            n++;
        }
        if (this.squares[sq.getHeightX() - 1][sq.getWidthY()].getStatus().equals(SquareStatus.Maze)) {
            n++;
        }
        if (this.squares[sq.getHeightX()][sq.getWidthY() + 1].getStatus().equals(SquareStatus.Maze)) {
            n++;
        }
        if (this.squares[sq.getHeightX()][sq.getWidthY() - 1].getStatus().equals(SquareStatus.Maze)) {
            n++;
        }
        if (n >= 2) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if give square could be a door (i.e. square is
     * attached to unconnected room). Returns it if it was found.
     *
     *
     * @param sq Square
     */
    private Square couldBeADoor(Square sq) {
        if (this.squares[sq.getHeightX() + 1][sq.getWidthY()].isPartOfRoom()
                && !this.squares[sq.getHeightX() + 1][sq.getWidthY()].getParentRoomSq().isConnectedToMaze()) {
            return this.squares[sq.getHeightX() + 1][sq.getWidthY()];
        } else if (this.squares[sq.getHeightX() - 1][sq.getWidthY()].isPartOfRoom()
                && !this.squares[sq.getHeightX() - 1][sq.getWidthY()].getParentRoomSq().isConnectedToMaze()) {
            return this.squares[sq.getHeightX() - 1][sq.getWidthY()];
        } else if (this.squares[sq.getHeightX()][sq.getWidthY() + 1].isPartOfRoom()
                && !this.squares[sq.getHeightX()][sq.getWidthY() + 1].getParentRoomSq().isConnectedToMaze()) {
            return this.squares[sq.getHeightX()][sq.getWidthY() + 1];
        } else if (this.squares[sq.getHeightX()][sq.getWidthY() - 1].isPartOfRoom()
                && !this.squares[sq.getHeightX()][sq.getWidthY() - 1].getParentRoomSq().isConnectedToMaze()) {
            return this.squares[sq.getHeightX()][sq.getWidthY() - 1];
        }
        return null;
    }
    
    /**
     * This method is an implementation of Fisher–Yates shuffle algorithm.
     * Shuffles array.
     *
     *
     * @param ar Square[]
     */
    static void shuffleArray(Square[] ar) {
        int n = 4;
        for (int i = n-1; i > 0; i--) { 
              
            // Pick a random index from 0 to i 
            long index = System.nanoTime();
            index = index % (i + 1);
            int intIndex = (int) index;
              
            // Swap ar[i] with the element at random index 
            Square temp = ar[i]; 
            ar[i] = ar[intIndex]; 
            ar[intIndex] = temp; 
        } 
    }
    
    /**
     * This method checks all neighbour squares of given passageway square and
     * if one of them is a part of passageway, method returns it.
     *
     *
     * @param sq Square
     * @return 
     */
    public Square previousSquareInPassageway(Square sq) {
        Square previous = null;
        if (this.squares[sq.getHeightX() + 1][sq.getWidthY()].getStatus().equals(SquareStatus.Maze)) {
            previous = this.squares[sq.getHeightX() + 1][sq.getWidthY()];
        } else if (this.squares[sq.getHeightX() - 1][sq.getWidthY()].getStatus().equals(SquareStatus.Maze)) {
            previous = this.squares[sq.getHeightX() - 1][sq.getWidthY()];
        } else if (this.squares[sq.getHeightX()][sq.getWidthY() + 1].getStatus().equals(SquareStatus.Maze)) {
            previous = this.squares[sq.getHeightX()][sq.getWidthY() + 1];
        } else if (this.squares[sq.getHeightX()][sq.getWidthY() - 1].getStatus().equals(SquareStatus.Maze)) {
            previous = this.squares[sq.getHeightX()][sq.getWidthY() - 1];
        }
        return previous;
    }

    /**
     * This method checks if given square is dead end (surrounded by at least 3
     * walls).
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean squareIsDeadEnd(Square sq) {
        int n = 0;
        if (sq.getHeightX() >= 1 && sq.getHeightX() <= this.height - 1
                && sq.getWidthY() >= 1 && sq.getWidthY() <= this.width - 1) {
            if (this.squares[sq.getHeightX() + 1][sq.getWidthY()].getSymbol().equals("#")
                    && !this.squares[sq.getHeightX() + 1][sq.getWidthY()].getStatus().equals(SquareStatus.Door)) {
                n++;
            }
            if (this.squares[sq.getHeightX() - 1][sq.getWidthY()].getSymbol().equals("#")
                    && !this.squares[sq.getHeightX() - 1][sq.getWidthY()].getStatus().equals(SquareStatus.Door)) {
                n++;
            }
            if (this.squares[sq.getHeightX()][sq.getWidthY() + 1].getSymbol().equals("#")
                    && !this.squares[sq.getHeightX()][sq.getWidthY() + 1].getStatus().equals(SquareStatus.Door)) {
                n++;
            }
            if (this.squares[sq.getHeightX()][sq.getWidthY() - 1].getSymbol().equals("#")
                    && !this.squares[sq.getHeightX()][sq.getWidthY() - 1].getStatus().equals(SquareStatus.Door)) {
                n++;
            }
            if (n >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method tells if given passageway square is going to be a deadend.
     *
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean willBeADeadEnd(Square sq) {
        int n = 0;
        if (canBeAPartOfPassageway(this.squares[sq.getHeightX() + 1][sq.getWidthY()])) {
            n++;
        } else if (canBeAPartOfPassageway(this.squares[sq.getHeightX() - 1][sq.getWidthY()])) {
            n++;
        } else if (canBeAPartOfPassageway(this.squares[sq.getHeightX()][sq.getWidthY() + 1])) {
            n++;
        } else if (canBeAPartOfPassageway(this.squares[sq.getHeightX()][sq.getWidthY() - 1])) {
            n++;
        }
        if (n > 0) {
            return false;
        }
        if (isInTouchWithDoor(sq)) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if square is in touch with door.
     *
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean isInTouchWithDoor(Square sq) {
        if (sq.getHeightX() >= 1 && sq.getHeightX() <= this.height - 1
                && sq.getWidthY() >= 1 && sq.getWidthY() <= this.width - 1) {
            if (this.squares[sq.getHeightX() + 1][sq.getWidthY()].getSymbol().equals("+")) {
                return true;
            }
            if (this.squares[sq.getHeightX() - 1][sq.getWidthY()].getSymbol().equals("+")) {
                return true;
            }
            if (this.squares[sq.getHeightX()][sq.getWidthY() + 1].getSymbol().equals("+")) {
                return true;
            }
            if (this.squares[sq.getHeightX()][sq.getWidthY() - 1].getSymbol().equals("+")) {
                return true;
            }

        }
        return false;
    }

    /**
     * This method removes all deadends. Deadend is a passageway squares that
     * are not a part of any room and is in touch with 3 or 4 walls.
     *
     *
     */
    public void removeDeadEnds() {
        while (!this.deadEnds.isEmpty()) {
            Square deadEnd = (Square) this.deadEnds.pop();

            while (true) {
                if (deadEnd == null) {
                    break;
                }
                deadEnd.setStatus(SquareStatus.Open);
                Square previousSq = previousSquareInPassageway(deadEnd);
                if (previousSq != null && squareIsDeadEnd(previousSq)) {
                    deadEnd = previousSq;
                    continue;
                }
                deadEnd = null;
            }
        }
    }

    /**
     * This method counts how many rooms are connected to the passageway.
     *
     *
     * @return 
     */
    public int numberOfSuccessfullyConnectedRooms() {
        int roomCount = 0;
        MyStack myStack = new MyStack();
        Square sq = findMazeOrRoomSquare();
        if (sq != null) {
            myStack.push(sq);
            while (!myStack.isEmpty()) {
                Square square = (Square) myStack.pop();
                if (!square.getChecked()) {
                    if (square.isParentRoomSq()) {
                        roomCount++;
                    }
                }
                square.setChecked();
                Square[] neighbours = neighbourArray(square);
                for (int i = 0; i < 4; i++) {
                    if (valid(neighbours[i])) {
                        myStack.push(neighbours[i]);
                    }
                }
            }
            return roomCount;
        }
        return 0;
    }

    /**
     * This method finds square on map that is "open" and can be used as
     * wall/room/maze.
     *
     *
     * @return 
     */
    public Square findMazeOrRoomSquare() {
        for (int i = 1; i < this.height; i++) {
            for (int j = 1; j < this.width; j++) {
                if (this.squares[i][j].getStatus().equals(SquareStatus.Maze)) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

    /**
     * This method insures that given square can be a part of passageway.
     *
     *
     * @param sq Square
     * @return 
     */
    public boolean valid(Square sq) {
        if (sq != null && !sq.getChecked() && (sq.getStatus().equals(SquareStatus.Maze)
                || sq.getStatus().equals(SquareStatus.Room)
                || sq.getStatus().equals(SquareStatus.Door))) {
            return true;
        }
        return false;
    }
}
