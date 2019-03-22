/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledungeongenerator;


public class Room {
    private Square[][] squares;
    private Square upLeft;
    private Square bottomRight;
    private int height;
    private int width;
    
//    public Room(Square upLeft, Square bottomRight, int height, int width) {
//        this.upLeft = upLeft;
//        this.bottomRight = bottomRight;
//        this.height = height;
//        this.width = width;
//    }
    
    public Room(Square upLeft, int height, int width) {
        this.upLeft = upLeft;
        this.height = height;
        this.width = width;
    }
    
    public Square getUpLeft() {
        return this.upLeft;
    }
//    public Square getbottomRight() {
//        return this.bottomRight;
//    }
    public int getHeight() {
        return this.height;
    }
    public int getWidth() {
        return this.width;
    }
}
