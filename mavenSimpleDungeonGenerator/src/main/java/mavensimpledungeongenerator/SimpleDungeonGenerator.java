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
        System.out.print("Room placing attemps: ");
        int placeAttemps = Integer.parseInt(user.nextLine());

        long before = System.currentTimeMillis();
        Map map = new Map(mapHeight, mapWidth); // initialization of empty map

        MyRandom random = new MyRandom();

        for (int i = 0; i < placeAttemps; i++) {    // placing rooms
            Room room = new Room(new Square(random.nextInt(mapHeight) + 1, random.nextInt(mapWidth) + 1),
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin,
                    random.nextInt((roomSizeMax - roomSizeMin + 1)) + roomSizeMin);
            map.addRoom(room);
        }

        map.floodFill();
        map.removeDeadEnds();
        long after = System.currentTimeMillis();
        System.out.println("Success! Generation time: " + (after - before) + " ms");
        map.showColoredMap(); // looking at result

    }

}
