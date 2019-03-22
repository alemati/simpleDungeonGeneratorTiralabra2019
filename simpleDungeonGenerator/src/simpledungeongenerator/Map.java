package simpledungeongenerator;

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

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Square newSquare = new Square(i, j);
                this.squares[i][j] = newSquare;
            }
        }
    }

    public void showMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.squares[i][j].getSymbol());
            }
            System.out.println("");
        }
    }

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

    public void addRoom(Room room) {
        if (checkAreaForRoom(room)) {
            for (int i = room.getUpLeft().getxHeight() - 1; i < (room.getUpLeft().getxHeight() + room.getHeight()) + 1; i++) {
                for (int j = room.getUpLeft().getyWidth() - 1; j < (room.getUpLeft().getyWidth() + room.getWidth()) + 1; j++) {
                    if (i == room.getUpLeft().getxHeight() - 1 || j == room.getUpLeft().getyWidth() - 1
                            || i == (room.getUpLeft().getxHeight() + room.getHeight())
                            || j == (room.getUpLeft().getyWidth() + room.getWidth())) {
                        if (!this.squares[i][j].getStatus().equals("wall")) {
                            this.squares[i][j].setSymbol("#");
                            this.squares[i][j].setStatus("wall");
                        }
                    } else {
                        this.squares[i][j].setSymbol(" ");
                        this.squares[i][j].setStatus("room");
                    }
                }
            }
        }
    }

}
