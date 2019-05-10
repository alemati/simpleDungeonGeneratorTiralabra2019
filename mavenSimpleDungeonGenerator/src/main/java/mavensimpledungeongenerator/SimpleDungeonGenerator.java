package mavensimpledungeongenerator;

import java.util.Scanner;

public class SimpleDungeonGenerator {

    public static void main(String[] args) {
        
        if (args.length == 0) {                                 // If program was triggered by jar file without parameters
            Scanner user = new Scanner(System.in);              // or was started in NetBeans, program should collect info first
            
            System.out.println("Hello! Give map parameters:");  // gathering information
            System.out.print("Height: ");
            int mapHeight = Integer.parseInt(user.nextLine());
            System.out.print("Width: ");
            int mapWidth = Integer.parseInt(user.nextLine());
            System.out.print("Min room size: ");
            int roomSizeMin = Integer.parseInt(user.nextLine());
            System.out.print("Max room size: ");
            int roomSizeMax = Integer.parseInt(user.nextLine());
            System.out.print("Room placing attempts: ");
            int placeAttempts = Integer.parseInt(user.nextLine());
            advancedSimulation(mapHeight, mapWidth, roomSizeMax, roomSizeMin, placeAttempts);
        } else {
            int mapHeight = Integer.parseInt(args[0]);          // if code was triggered by jar file with parameters
            int mapWidth = Integer.parseInt(args[1]);           // program uses them
            int roomSizeMin = Integer.parseInt(args[2]);
            int roomSizeMax = Integer.parseInt(args[3]);
            int placeAttempts = Integer.parseInt(args[4]);
            advancedSimulation(mapHeight, mapWidth, roomSizeMin, roomSizeMax, placeAttempts);
        }
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
        int roomsPlaced = map.getMapRoomsPlaced();
        int connectedRooms = map.numberOfSuccessfullyConnectedRooms();
        long after = System.currentTimeMillis();
        map.showMap();
        System.out.println("There is " + roomsPlaced + " rooms on the map and " + connectedRooms + " of them are connected.");
        System.out.println("Generation took " + (after - before) + " ms");
    }

    /**
     * This method creates map based on given n. Map height and width will be
     * equal to n, roomMinSize ~2% of n, roomSizeMax ~5% of n, room placing
     * attempts ~ n/2. This method was used in testing.
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
                    random.nextInt((roomSizeMax - roomSizeMin + 2)) + roomSizeMin,
                    random.nextInt((roomSizeMax - roomSizeMin + 2)) + roomSizeMin);
            map.addRoom(room);
        }
        map.floodFill();
        map.removeDeadEnds();
        int roomsPlaced = map.getMapRoomsPlaced();
        int connectedRooms = map.numberOfSuccessfullyConnectedRooms();
        long after = System.currentTimeMillis();
        map.showMap();
        System.out.println("There is " + roomsPlaced + " rooms on the map and " + connectedRooms + " of them are connected.");
        System.out.println("Generation took " + (after - before) + " ms");
    }
}
