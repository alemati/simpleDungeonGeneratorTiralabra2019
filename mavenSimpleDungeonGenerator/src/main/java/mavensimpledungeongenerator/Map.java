package mavensimpledungeongenerator;

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private int roomSizeMin;
    private int roomSizeMax;
    private Square[][] squares;

    public Map(int height, int width, int roomSizeMin, int roomSizeMax) {
        this.height = height;
        this.width = width;
        this.roomSizeMax = roomSizeMax;
        this.roomSizeMin = roomSizeMin;
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
     * This method tries to place given room onto map if it is possible.
     *
     *
     * @param room Room
     */
    public void addRoom(Room room) {
        if (checkAreaForRoom(room)) {
            for (int i = room.getUpLeft().getxHeight() - 1; i < (room.getUpLeft().getxHeight() + room.getHeight()) + 1; i++) {
                for (int j = room.getUpLeft().getyWidth() - 1; j < (room.getUpLeft().getyWidth() + room.getWidth()) + 1; j++) {
                    if (i == room.getUpLeft().getxHeight() - 1 || j == room.getUpLeft().getyWidth() - 1
                            || i == (room.getUpLeft().getxHeight() + room.getHeight())
                            || j == (room.getUpLeft().getyWidth() + room.getWidth())) {
                        if (!this.squares[i][j].getStatus().equals("wall")) {   // Each and every room is
                            this.squares[i][j].setSymbol("#");                  // surrounded by walls.
                            this.squares[i][j].setStatus("wall");               // Wall is not a part of any room.
                        }                                                       // One wall can separate two rooms.
                    } else {
                        this.squares[i][j].setSymbol(" ");
                        this.squares[i][j].setStatus("room");
                    }
                }
            }
        }
    }

    /**
     * This method checks if area, that room wants to be on, is available.
     *
     *
     * @param room Room
     */
    public boolean checkAreaForRoom(Room room) {
        for (int i = room.getUpLeft().getxHeight(); i < (room.getUpLeft().getxHeight() + room.getHeight()); i++) {
            for (int j = room.getUpLeft().getyWidth(); j < (room.getUpLeft().getyWidth() + room.getWidth()); j++) {
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
     * This method finds square on map that is "open" and can be used as wall or
     * room or maze.
     *
     *
     */
    public Square findOpenSquare() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.squares[i][j].getStatus().equals("open")) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

    /**
     * This method triggers flood fill alghoritm to fill all open space with
     * maze.
     *
     *
     */
    public void floodFill() {
        while (true) {
            Square sq = findOpenSquare();
            if (sq == null) {
                break;
            }
            fill(sq);
        }
    }

    /**
     * This method is floodFill recursive method. It checks if square can be a
     * passageway and it can't, turns it into wall.
     *
     *
     * @param sq Square
     */
    private void fill(Square sq) {
        if (sq.getxHeight() < 0 || sq.getxHeight() >= this.height
                || sq.getyWidth() < 0 || sq.getyWidth() >= this.width || !sq.getStatus().equals("open")) {
            return;
        }
        if (checkSquareNeighbours(sq)) {
            sq.setStatus("maze");
            sq.setSymbol(" ");
        } else {
            return;
        }

        Square[] recursiveCalls = new Square[4];        // this part garantes that maze is randomized
        recursiveCalls[0] = this.squares[sq.getxHeight() + 1][sq.getyWidth()];
        recursiveCalls[1] = this.squares[sq.getxHeight() - 1][sq.getyWidth()];
        recursiveCalls[2] = this.squares[sq.getxHeight()][sq.getyWidth() + 1];
        recursiveCalls[3] = this.squares[sq.getxHeight()][sq.getyWidth() - 1];
        shuffleArray(recursiveCalls);
        for (int i = 0; i < 4; i++) {
            fill(recursiveCalls[i]);
        }

        surroundSquareByWalls(sq);
    }

    /**
     * This method is implementation of Fisher–Yates shuffle algorithm. Shuffles
     * array
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
     * This method surrounds all "open" squares around given square with walls.
     *
     *
     * @param sq Square
     */
    public void surroundSquareByWalls(Square sq) {
        if (this.squares[sq.getxHeight() - 1][sq.getyWidth()].getStatus().equals("open")) {
            this.squares[sq.getxHeight() - 1][sq.getyWidth()].setStatus("wall");
            this.squares[sq.getxHeight() - 1][sq.getyWidth()].setSymbol("#");
        }
        if (this.squares[sq.getxHeight() + 1][sq.getyWidth()].getStatus().equals("open")) {
            this.squares[sq.getxHeight() + 1][sq.getyWidth()].setStatus("wall");
            this.squares[sq.getxHeight() + 1][sq.getyWidth()].setSymbol("#");
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() - 1].getStatus().equals("open")) {
            this.squares[sq.getxHeight()][sq.getyWidth() - 1].setStatus("wall");
            this.squares[sq.getxHeight()][sq.getyWidth() - 1].setSymbol("#");
        }
        if (this.squares[sq.getxHeight()][sq.getyWidth() + 1].getStatus().equals("open")) {
            this.squares[sq.getxHeight()][sq.getyWidth() + 1].setStatus("wall");
            this.squares[sq.getxHeight()][sq.getyWidth() + 1].setSymbol("#");
        }
    }

    /**
     * This method checks if given square is in touch with maze and returns true
     * if it is in touch with only one "maze" square, meaning given square can
     * be a part of maze to.
     *
     *
     * @param sq Square
     */
    public boolean checkSquareNeighbours(Square sq) {
        int n = 0;
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
    
    public Square findRoomSquare() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.squares[i][j].getStatus().equals("room")) {
                    return this.squares[i][j];
                }
            }
        }
        return null;
    }

//    public Square findRoomSquare() {
//        for (int i = 0; i < this.height; i++) {
//            for (int j = 0; j < this.width; j++) {
//                if (this.squares[i][j].getStatus().equals("room")) {
//                    return this.squares[i][j];
//                }
//            }
//        }
//        return null;
//    }

//    public void smallFloodFill() {
//        Square sq = findRoomSquare();
//        smallFill(sq);
//    }
//
//    private void smallFill(Square sq) {
//        if (sq.getxHeight() < 0 || sq.getxHeight() >= this.height
//                || sq.getyWidth() < 0 || sq.getyWidth() >= this.width || sq.getStatus().equals("wall")) {
//            return;
//        }
//        if (sq.getStatus().equals("room") && sq.getRerion().equals("non")) {
//            sq.setRegion("main");
//            sq.setSymbol("€");
//            System.out.println(sq.getxHeight() + ", " + sq.getyWidth());
//        }
//        smallFill(this.squares[sq.getxHeight() + 1][sq.getyWidth()]);
//        smallFill(this.squares[sq.getxHeight() - 1][sq.getyWidth()]);
//        smallFill(this.squares[sq.getxHeight()][sq.getyWidth() + 1]);
//        smallFill(this.squares[sq.getxHeight()][sq.getyWidth() - 1]);
//    }

    

}
