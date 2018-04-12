package cps2002.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* this class tests the functionality of the Player class */

public class PlayerTest {

    private Player player;


    // set up a new player with map size 5 and number of players 2
    @Before
    public void setup(){
        player = new Player(5, 2);
    }

    // test that setting a player's position functions correctly
    @Test
    public void testSetPosition(){
        Position pos = new Position(4,5);
        boolean ans = player.setPosition(pos);

        boolean expected = true;

        assertEquals(expected, ans);
    }

    // set the player to null
    @After
    public void teardown(){
        player = null;
    }
}
