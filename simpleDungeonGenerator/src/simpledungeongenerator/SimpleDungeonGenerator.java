package simpledungeongenerator;

import java.util.Random;
import java.util.Scanner;

public class SimpleDungeonGenerator {

    public static void main(String[] args) {
        Scanner user = new Scanner(System.in);
        System.out.println("Hello! Give map paremeters:");
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

        Map map = new Map(mapHeight, mapWidth, roomSizeMin, roomSizeMax);

        Random random = new Random();

        for (int i = 0; i < placeAttemps; i++) {
            Room room = new Room(new Square(random.nextInt(mapHeight) + 1, random.nextInt(mapWidth) + 1),
                    random.nextInt((roomSizeMax - roomSizeMin)) + roomSizeMin,
                    random.nextInt((roomSizeMax - roomSizeMin)) + roomSizeMin);
            map.addRoom(room);
        }

        map.showMap();
    }

}
