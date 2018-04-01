package cps2002.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.swing.*;
import java.awt.*;


public class GameTest {

    private Game g;

    @Before
    public void setup(){
        g = new Game();
    }



    @Test
    public void testSetNumOfPlayersWithTrueCondition(){

        boolean ans = g.setNumPlayers(4);

        boolean expected = true;

        assertEquals(expected, ans);
    }

    @Test
    public void testSetNumOfPlayersWithFalseCondition(){

        boolean ans = g.setNumPlayers(-1);
        boolean expected = false;

        assertEquals(expected, ans);
    }

    @Test
    public void testSetNumOfPlayersWithFalseConditionBecauseOfTooManyPlayers(){

        boolean ans = g.setNumPlayers(10);
        boolean expected = false;

        assertEquals(expected, ans);
    }

    @Test
    public void setPlayersTest(){

        boolean ans = g.setPlayers("-1");


        assertEquals(true, ans);
    }

    @Test
    public void setPlayersTestWithGivenInput(){

        boolean ans = g.setPlayers("5");


        assertEquals(true, ans);
    }

    @Test
    public void testMain(){

        Game.main(new String[] {""});
        assertEquals(0, g.numOfPlayers);
    }

    @Test
    public void testStartGamePlayerFrames(){
        g.numOfPlayers = 4;
        g.boardSize = 7;
        g.setFrames();
        int size = g.playerFrames.size();
        assertEquals(4,size);
    }



    @After
    public void teardown(){
        g = null;
    }
}
