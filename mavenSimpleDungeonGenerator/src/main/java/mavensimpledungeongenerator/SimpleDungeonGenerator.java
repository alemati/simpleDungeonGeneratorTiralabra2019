package mavensimpledungeongenerator;

import java.util.Random;
import java.util.Scanner;

public class SimpleDungeonGenerator {

    public static void main(String[] args) {
        
        Scanner user = new Scanner(System.in);
        System.out.println("Hello! Give map parameters:");  // gathering information
        System.out.print("Height: ");
        int mapHeight = Integer.parseInt(user.nextLine());
        System.out.print("Width: ");
        int mapWidth = Integer.parseInt(user.nextLine());
        System.out.print("Minimal room size: ");
        int roomSizeMin = Integer.parseInt(user.nextLine());
        System.out.print("Maximal room size: ");
        int roomSizeMax = Integer.parseInt(user.nextLine());
        System.out.print("Room placing attempts: ");
        int placeAttempts = Integer.parseInt(user.nextLine());
        
        advancedSimulation(mapHeight, mapWidth, roomSizeMax, roomSizeMin, placeAttempts);
        
    }
    
    /**
     * This method creates map based on given parameters. 
     *
     * @param height int
     * @param width int
     * @param minSizeRoom int
     * @param maxSizeRoom int
     * @param attempts int
     */
    public static void advancedSimulation(int height, int width, int minSizeRoom, int maxSizeRoom, int attempts) {
        long before = System.currentTimeMillis();
        Map map = new Map(height, width); // initialization of empty map
        int roomSizeMin = minSizeRoom; 
        int roomSizeMax = maxSizeRoom; 
        int placeAttemps = attempts;
        MyRandom random = new MyRandom();
        for (int i = 0; i < placeAttemps; i++) {    // placing rooms
            Room room = new Room(new Square(random.nextInt(height) + 1, random.nextInt(width) + 1),
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin,
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin);
            map.addRoom(room);
        }
        map.floodFill();
        map.removeDeadEnds();
        map.numberOfSuccessfullyConnectedRooms();
        long after = System.currentTimeMillis();
        System.out.println("Generation time was: " + (after - before) + " ms");
        map.showMap();
    }
    
    /**
     * This method creates map based on given n. 
     * Map height and width will be equal to n, roomMinSize ~2% of n, roomSizeMax  ~5% of n,
     * room placing attempts  ~ n/2.
     *
     * @param n int
     */
    public static void simpleSimulation(int n) {
        long before = System.currentTimeMillis();
        Map map = new Map(n, n); // initialization of empty map
        int roomSizeMin = (int) n / 100 * 2; // 2%
        int roomSizeMax = (int) n / 100 * 5; // 5%
        int placeAttempts = (int) n / 2;
        MyRandom random = new MyRandom();
        for (int i = 0; i < placeAttempts; i++) {    // placing rooms
            Room room = new Room(new Square(random.nextInt(n) + 1, random.nextInt(n) + 1),
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin,
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin);
            map.addRoom(room);
        }
        map.floodFill();
        map.removeDeadEnds();
        map.numberOfSuccessfullyConnectedRooms();
        long after = System.currentTimeMillis();
        System.out.println("Generation time was: " + (after - before) + " ms");
        map.showMap();
    }
}
