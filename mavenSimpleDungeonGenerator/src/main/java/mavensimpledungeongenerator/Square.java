
package mavensimpledungeongenerator;

public class Square {
    
    private int xHeight;
    private int yWidth;
    private String status;
    private String symbol;
    
    public Square(int x, int y) {
        this.xHeight = x;
        this.yWidth = y;
        this.status = "open";
        this.symbol = "░";

    }
    
    public int getxHeight(){
        return this.xHeight;
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
    
    public void setSymbol(String newSymbol){
        this.symbol = newSymbol;
    }
    
    public void setStatus(String newStatus){
        this.status = newStatus;
    }
}
