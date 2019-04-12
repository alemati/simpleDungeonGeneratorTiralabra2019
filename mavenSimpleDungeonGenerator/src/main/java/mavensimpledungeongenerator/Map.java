package mavensimpledungeongenerator;

import java.util.LinkedList;
import java.util.Random;
import java.util.*;

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
                if (i == 0 || i >= this.height - 1 || j == 0 || j >= this.width - 1) {
                    Square newSquare = new Square(i, j);
                    this.squares[i][j] = newSquare;
                    this.squares[i][j].setStatus("wall");
                    this.squares[i][j].setSymbol("#");
                } else {
                    Square newSquare = new Square(i, j);
                    this.squares[i][j] = newSquare;
                }
            }
        }
    }

    /**
     * This method prints dungeon map in console
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
            Square parentSquare = room.getUpLeft();
            for (int i = room.getUpLeft().getxHeight() - 1; i < (room.getUpLeft().getxHeight() + room.getHeight()) + 1; i++) {
                for (int j = room.getUpLeft().getyWidth() - 1; j < (room.getUpLeft().getyWidth() + room.getWidth()) + 1; j++) {
                    if (i == room.getUpLeft().getxHeight() - 1 || j == room.getUpLeft().getyWidth() - 1
                            || i == (room.getUpLeft().getxHeight() + room.getHeight())
                            || j == (room.getUpLeft().getyWidth() + room.getWidth())) {
                        if (!this.squares[i][j].getStatus().equals("wall")) {   // Each and every room is
                            this.squares[i][j].setSymbol("#");                  // surrounded by walls.
                            this.squares[i][j].setStatus("wall");               // Wall is not a part of any room.
                            // One wall can separate two rooms.

                            // following chunk of code will be refactored later on.
                            if ((i == room.getUpLeft().getxHeight() - 1 && j == room.getUpLeft().getyWidth() - 1)
                                    || (i == room.getUpLeft().getxHeight() - 1 && j == room.getUpLeft().getyWidth() + room.getWidth() + 1)
                                    || (i == room.getUpLeft().getxHeight() + room.getHeight() + 1 && j == room.getUpLeft().getyWidth() - 1)
                                    || (i == room.getUpLeft().getxHeight() + room.getHeight() + 1 && j == room.getUpLeft().getyWidth() + room.getWidth() + 1)) {
                                int s = 5;
                            } else {
                                this.squares[i][j].setParentRoomSq(parentSquare);
                                this.squares[i][j].setAsPartOfRoom();
                            }
                        }
                    } else if (i == parentSquare.getxHeight() && j == parentSquare.getyWidth()) {
                        this.squares[i][j].setToBeParentRoomSq();
                        this.squares[i][j].setSymbol(" ");
                        this.squares[i][j].setStatus("room");
                    } else {
                        this.squares[i][j].setSymbol(" ");
                        this.squares[i][j].setStatus("room");
                        this.squares[i][j].setParentRoomSq(parentSquare);
                    }
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
        for (int i = room.getUpLeft().getxHeight() - 1; i < (room.getUpLeft().getxHeight() + room.getHeight() + 1); i++) {
            for (int j = room.getUpLeft().getyWidth() - 1; j < (room.getUpLeft().getyWidth() + room.getWidth() + 1); j++) {
                if (i >= this.height - 1 || j >= this.width - 1 || i < 1 || j < 1) {
                    return false;
                }
                if (!this.squares[i][j].getStatus().equals("open")) {
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
                if (this.squares[i][j].getStatus().equals("open")) {
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
        StackSM myStack = new StackSM();
        myStack.push(sq);

        while (!myStack.isEmpty()) {
            Square square = (Square) myStack.pop();

            if (canBeAPartOfPassageway(square)) {
                square.setStatus("maze");
                square.setSymbol(" ");

                Square nr = neigbourRoomIsNotConnected(square);
                if (nr != null) {
                    Square parent = nr.getParentRoomSq();
                    parent.setRegion("main");
                    this.squares[parent.getxHeight()][parent.getyWidth()].setSymbol(" ");
                    this.squares[parent.getxHeight()][parent.getyWidth()].setRegion("main");
                    nr.setToBeDoor();
                }

                Square[] randomizer = new Square[4];
                if (!outOfBounds(this.squares[square.getxHeight() + 1][square.getyWidth()])) {
                    randomizer[0] = this.squares[square.getxHeight() + 1][square.getyWidth()];
                } else {
                    randomizer[0] = null;
                }
                if (!outOfBounds(this.squares[square.getxHeight() - 1][square.getyWidth()])) {
                    randomizer[1] = this.squares[square.getxHeight() - 1][square.getyWidth()];
                } else {
                    randomizer[1] = null;
                }
                if (!outOfBounds(this.squares[square.getxHeight()][square.getyWidth() + 1])) {
                    randomizer[2] = this.squares[square.getxHeight()][square.getyWidth() + 1];
                } else {
                    randomizer[2] = null;
                }
                if (!outOfBounds(this.squares[square.getxHeight()][square.getyWidth() - 1])) {
                    randomizer[3] = this.squares[square.getxHeight()][square.getyWidth() - 1];
                } else {
                    randomizer[3] = null;
                }

                shuffleArray(randomizer);
                for (int i = 0; i < 4; i++) {
                    if (randomizer[i] != null) {
                        myStack.push(randomizer[i]);
                    }
                }
            }
        }

    }
    
    /**
     * This method checks if given square is within map borders.
     *
     *
     * @param sq Square
     */
    public boolean outOfBounds(Square sq) {
        if (sq.getxHeight() >= this.height - 1 || sq.getxHeight() <= 0
                || sq.getyWidth() >= this.width - 1 || sq.getyWidth() <= 0) {
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
        if (!sq.getStatus().equals("open")) {
            return false;
        }
        if (this.squares[sq.getxHeight() + 1][sq.getyWidth()].getStatus().equals("maze")) {
            n++;
        }
        if (this.squares[sq.getxHeight() - 1][sq.getyWidth()].getStatus().equals("maze")) {
            n++;
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() + 1].getStatus().equals("maze")) {
            n++;
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() - 1].getStatus().equals("maze")) {
            n++;
        }
        if (n >= 2) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if a neighbour square is a wall that surrounds
     * unconnected room.
     *
     *
     * @param sq Square
     */
    private Square neigbourRoomIsNotConnected(Square sq) {
        if (this.squares[sq.getxHeight() + 1][sq.getyWidth()].getIsPartOfRoomOrNot()
                && this.squares[sq.getxHeight() + 1][sq.getyWidth()].getParentRoomSq().getRerion().equals("non")) {
            return this.squares[sq.getxHeight() + 1][sq.getyWidth()];
        }
        if (this.squares[sq.getxHeight() - 1][sq.getyWidth()].getIsPartOfRoomOrNot()
                && this.squares[sq.getxHeight() - 1][sq.getyWidth()].getParentRoomSq().getRerion().equals("non")) {
            return this.squares[sq.getxHeight() - 1][sq.getyWidth()];
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() + 1].getIsPartOfRoomOrNot()
                && this.squares[sq.getxHeight()][sq.getyWidth() + 1].getParentRoomSq().getRerion().equals("non")) {
            return this.squares[sq.getxHeight()][sq.getyWidth() + 1];
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() - 1].getIsPartOfRoomOrNot()
                && this.squares[sq.getxHeight()][sq.getyWidth() - 1].getParentRoomSq().getRerion().equals("non")) {
            return this.squares[sq.getxHeight()][sq.getyWidth() - 1];
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
        Square sq = findDeadEnd();
        while (sq != null) {
            while (sq != null) {
                if (!squareIsDeadEnd(sq)) {
                    break;
                }
                sq.setSymbol("#");
                sq.setStatus("wall");
                Square previousSq = previousSquareInPassageway(sq);
                sq = previousSq;
            }
            sq = findDeadEnd();
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
                if (this.squares[i][j].getStatus().equals("maze") && squareIsDeadEnd(this.squares[i][j])) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

    /**
     * This method checks all neighbour squares of given square and if one of
     * them is a part of passageway, method returns it.
     *
     *
     * @param sq Square
     */
    public Square previousSquareInPassageway(Square sq) {
        Square previous = null;
        if (this.squares[sq.getxHeight() + 1][sq.getyWidth()].getStatus().equals("maze")) {
            previous = this.squares[sq.getxHeight() + 1][sq.getyWidth()];
        } else if (this.squares[sq.getxHeight() - 1][sq.getyWidth()].getStatus().equals("maze")) {
            previous = this.squares[sq.getxHeight() - 1][sq.getyWidth()];
        } else if (this.squares[sq.getxHeight()][sq.getyWidth() + 1].getStatus().equals("maze")) {
            previous = this.squares[sq.getxHeight()][sq.getyWidth() + 1];
        } else if (this.squares[sq.getxHeight()][sq.getyWidth() - 1].getStatus().equals("maze")) {
            previous = this.squares[sq.getxHeight()][sq.getyWidth() - 1];
        }
        return previous;
    }

    /**
     * This method checks if given square is dead end (surrounded by at least 3 walls).
     *
     *
     * @param sq Square
     */
    public boolean squareIsDeadEnd(Square sq) {
        int n = 0;
        if (sq.getxHeight() >= 1 && sq.getxHeight() <= this.height - 1
                && sq.getyWidth() >= 1 && sq.getyWidth() <= this.width - 1) {
            if (this.squares[sq.getxHeight() + 1][sq.getyWidth()].getSymbol().equals("#")
                    && !this.squares[sq.getxHeight() + 1][sq.getyWidth()].getIsDoor()) {
                n++;
            }
            if (this.squares[sq.getxHeight() - 1][sq.getyWidth()].getSymbol().equals("#")
                    && !this.squares[sq.getxHeight() - 1][sq.getyWidth()].getIsDoor()) {
                n++;
            }
            if (this.squares[sq.getxHeight()][sq.getyWidth() + 1].getSymbol().equals("#")
                    && !this.squares[sq.getxHeight()][sq.getyWidth() + 1].getIsDoor()) {
                n++;
            }
            if (this.squares[sq.getxHeight()][sq.getyWidth() - 1].getSymbol().equals("#")
                    && !this.squares[sq.getxHeight()][sq.getyWidth() - 1].getIsDoor()) {
                n++;
            }
            if (n >= 3) {
                return true;
            }
        }
        return false;
    }

}
