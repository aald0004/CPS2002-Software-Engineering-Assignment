package cps2002.task1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator c;

    @Before
    public void setup(){
        c = new Calculator();
    }

    @Test
    public void testAddition(){
        int sum = c.add(1,2);
        assertEquals(3,sum);
    }

    @After
    public void teardown(){
        c = null;
    }

}
