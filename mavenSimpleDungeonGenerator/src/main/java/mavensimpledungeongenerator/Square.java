
package mavensimpledungeongenerator;

public class Square {
    
    private int xHeight;
    private int yWidth;
    private String status;
    private String symbol;
    private String region;
    private Square parentRoomSq;
    private Boolean isParentRoomSq;
    private Boolean isDoor;
    private Boolean partOfRoom;
    
    public Square(int x, int y) {
        this.xHeight = x;
        this.yWidth = y;
        this.status = "open";
        this.symbol = "░";
        this.region = "non";
        this.parentRoomSq = null;
        this.isParentRoomSq = false;
        this.partOfRoom = false;
        this.isDoor = false;
    }
    public void setAsNotAPartOfRoom() {
        this.partOfRoom = false;
        this.parentRoomSq = null;
    }
    public void setAsPartOfRoom() {
        this.partOfRoom = true;
    }
    public boolean getIsPartOfRoomOrNot() {
        return this.partOfRoom;
    }
    
    public void setToBeDoor() {
        this.isDoor = true;
        this.symbol = "+";
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
    
    public int getxHeight(){
        return this.xHeight;
    }
    
    public boolean getIsDoor(){
        return this.isDoor;
    }
    
    public int getyWidth(){
        return this.yWidth;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public String getSymbol(){
        return this.symbol;
    }
    
    public String getRerion(){
        return this.region;
    }
    
    public void setRegion(String newRegion){
        this.region = newRegion;
    }
    
    public void setSymbol(String newSymbol){
        this.symbol = newSymbol;
    }
    
    public void setStatus(String newStatus){
        this.status = newStatus;
    }
}
