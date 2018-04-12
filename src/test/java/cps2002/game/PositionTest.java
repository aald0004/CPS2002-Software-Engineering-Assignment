package cps2002.game;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/* This class is used to test the functionality of the Position class */

public class PositionTest {

    private Position pos;

    // set a new position with x coordinate 5 and y coorindate 4
    @Before
    public void setup(){
        pos = new Position(5,4);
    }

    // test that the getx function works
    @Test
    public void testGetX(){

        int x = pos.getx();
        assertEquals(5,x);
    }

    // test that the gety function works
    @Test
    public void testGetY(){
        int y = pos.gety();
        assertEquals(4,y);
    }

    // set the position to null
    @After
    public void teardown(){
        pos = null;
    }
}
