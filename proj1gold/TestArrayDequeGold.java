/**
 * @author zhilic created on 8/12/19
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentVsSolution() {
        StudentArrayDeque<Integer> studentAD = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionAD = new ArrayDequeSolution<>();
        String message = "";

        for (int i = 0; i < 7; i++) {
            Integer randomInt = StdRandom.uniform(10);
            studentAD.addLast(randomInt);
            solutionAD.addLast(randomInt);
            message += "addLast(" + randomInt + ")\n";
        }

        message += "removeFirst()\n";
        assertEquals(message, solutionAD.remove(), studentAD.removeFirst());

        message += "removeLast()\n";
        assertEquals(message, solutionAD.removeLast(), studentAD.removeLast());

    }

}
