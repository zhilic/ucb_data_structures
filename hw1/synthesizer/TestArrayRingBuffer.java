package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertEquals(5, arb.capacity());
        assertEquals(4, arb.fillCount());
        assertFalse(arb.isEmpty());
        assertFalse(arb.isFull());

        int oldFirst = (int) arb.dequeue();
        assertEquals(1, oldFirst);
        assertEquals(2, arb.peek());

        arb.enqueue(5);
        arb.enqueue(6);
        assertTrue(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
