
import org.junit.Test;
import static org.junit.Assert.*;
import mavensimpledungeongenerator.MyStack;

/**
 *
 * @author AM
 */
public class MyStackTest {
    
    /**
     *
     */
    @Test
    public void testPushAndPop() {             
        MyStack myStack = new MyStack();
        int number1 = 11;
        int number2 = 22;
        myStack.push(number1);
        myStack.push(number2);
        int ans = (int) myStack.pop();
        assertEquals(number2, ans);
    }
    
    /**
     *
     */
    @Test
    public void testNotEmpty() {             
        MyStack myStack = new MyStack();
        int number1 = 11;
        myStack.push(number1);
        boolean isEmpty = myStack.isEmpty();
        assertEquals(false, isEmpty);
    }
    
    /**
     *
     */
    @Test
    public void testIsEmpty() {             
        MyStack myStack = new MyStack();
        boolean isEmpty = myStack.isEmpty();
        assertEquals(true, isEmpty);
    }
    
    /**
     *
     */
    @Test
    public void testSizeGrows() {    
        // stacks default size is 10
        // this test checks if myStacks size grows
        MyStack myStack = new MyStack();
        for (int i = 1; i <= 101; i++) {
            myStack.push(i);
        }
        int ans = (int) myStack.pop();
        assertEquals(101, ans);
    }
    
}
