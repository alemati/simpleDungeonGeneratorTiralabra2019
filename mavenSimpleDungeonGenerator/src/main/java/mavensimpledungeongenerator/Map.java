package mavensimpledungeongenerator;

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private Square[][] squares;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.squares = new Square[this.height][this.width];
        mapInit();
    }

    /**
     * This method initialize empty map.
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
                System.out.print(this.squares[i][j].getSymbolColored());
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
            
            for (int i = room.getParentSquare().getHeightX() - 1; i < (room.getParentSquare().getHeightX() + room.getHeight()) + 1; i++) {
                for (int j = room.getParentSquare().getWidthY() - 1; j < (room.getParentSquare().getWidthY() + room.getWidth()) + 1; j++) {

                    if (i == room.getParentSquare().getHeightX() - 1 // borders
                            || j == room.getParentSquare().getWidthY() - 1
                            || i == (room.getParentSquare().getHeightX() + room.getHeight())
                            || j == (room.getParentSquare().getWidthY() + room.getWidth())) {

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
     * This method finds square on map that is "open" and can be used as
     * wall/room/maze.
     *
     *
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

                Square neighbour = couldBeADoor(square);    // check if door can be placed nearby
                if (neighbour != null) {
                    Square parent = neighbour.getParentRoomSq();
                    parent.connectToMaze();
                    neighbour.setStatus(SquareStatus.Door);
                }

                Square[] neighbours = neighbourArray(square);   // pushing all valid neighbour squares into myStack
                for (int i = 0; i < 4; i++) {
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
     * @param sq Square
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
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Square a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /**
     * This method removes all dead ends. Dead end is a passageway squares that
     * are not a part of any room and is in touch with 3 or 4 walls.
     *
     *
     */
    public void removeDeadEnds() {
        Square deadEnd = findDeadEnd();
        while (deadEnd != null) {
            while (deadEnd != null) {
                if (!squareIsDeadEnd(deadEnd)) {
                    break;
                }
                deadEnd.setStatus(SquareStatus.Open);
                Square previousSq = previousSquareInPassageway(deadEnd);
                deadEnd = previousSq;
            }
            deadEnd = findDeadEnd();
        }

    }

    /**
     * This method finds a dead end square. Dead end is a passagewat square
     * which is surrounded by at least 3 walls.
     *
     *
     */
    public Square findDeadEnd() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.squares[i][j].getStatus().equals(SquareStatus.Maze) && squareIsDeadEnd(this.squares[i][j])) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

    /**
     * This method checks all neighbour squares of given passageway square and
     * if one of them is a part of passageway, method returns it.
     *
     *
     * @param sq Square
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
}