
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import mavensimpledungeongenerator.Map;
import mavensimpledungeongenerator.Room;
import mavensimpledungeongenerator.Square;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest {

    @Test
    public void testMapShow() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Map map = new Map(3, 3);
//      map should look in console like that
//      ###
//      ###
//      ###
        map.showMap();
        assertEquals("###\n###\n###\n", outContent.toString());
    }


    @Test
    public void testRoomAndMazeAdded() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Map map = new Map(7, 7);
        Room room = new Room(new Square(3, 3), 1, 1);
        map.addRoom(room);
        map.floodFill();
//        #######     
//        #     #     after floodFilling square 2,2 should be a door
//        ##+## #     because floodFill starts from 1,1 
//        # # # #
//        # ### #
//        #     #
//        #######
        Square[][] squares = map.getMapSquares();
        String ans = squares[2][2].getSymbol();
        assertEquals("+", ans);
    }

    @Test
    public void testDeadEndCheck() {
        Map map = new Map(3, 3);
//      ###
//      ###
//      ###
        Square sq = new Square(1, 1); // middel square surrounded by 4 walls -> should be a deadEnd
        boolean answer = map.squareIsDeadEnd(sq);
        assertEquals(true, answer);
    }

    @Test
    public void testRemoveDeadEnd() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Map map = new Map(8, 8);

        Room room = new Room(new Square(3, 3), 1, 1);
        map.addRoom(room);
        Room room2 = new Room(new Square(5, 5), 1, 1);
        map.addRoom(room2);
        map.floodFill();
        map.removeDeadEnds();
        Square deadEnd = map.findDeadEnd(); //after removing all dead ends this should be null
        assertEquals(null, deadEnd);

    }

}
