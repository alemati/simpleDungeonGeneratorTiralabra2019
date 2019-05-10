
import mavensimpledungeongenerator.MyRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyRandomTest {
    
    @Test
    public void testRandomNumber() {             
        MyRandom myRandom = new MyRandom();
        boolean ans = true;
        int number = myRandom.nextInt();
        if (number < 0) {       // number should be positive
            ans = false;
        }
        assertEquals(true, ans);
    }
    
    @Test
    public void testRandomNumberWithUpperBoundary() {             
        MyRandom myRandom = new MyRandom();
        boolean ans = true;
        int number = myRandom.nextInt(100);
        if (number < 0  || number > 100) {       // number should be in range 0 - 100
            ans = false;
        }
        assertEquals(true, ans);
    }
    
}
