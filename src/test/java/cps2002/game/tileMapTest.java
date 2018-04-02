package cps2002.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class tileMapTest {

    private tileMap tm;
    private tileMap tm2;

    @Before
    public void setup(){
        tm = new tileMap(3);
        tm2 = new tileMap(8);
    }


    @Test
    public void testSetMap(){
        tm.size = 5;
        char[][] map = tm.setMap();

        char[][] expectedMap = new char[tm.size][tm.size];
        for(int i = 0; i < tm.size;i++){
            for(int j = 0; j < tm.size;j++){
                expectedMap[i][j] = 'g';
            }
        }

        expectedMap[3][4] = 't';
        expectedMap[2][3] = 'w';
        expectedMap[4][1] = 'w';

        assertArrayEquals(expectedMap,map);

    }

    @Test
    public void testGetTileTypeTreasure(){
        tm.size = 5;
        tm.map = tm.setMap();

        char type = tm.getTileType(3,4);
        assertEquals('t', type);
    }

    @Test
    public void testGetTileTypeWater(){
        tm.size = 5;
        tm.map = tm.setMap();

        char type = tm.getTileType(2,3);
        assertEquals('w', type);
    }

    @Test
    public void testGetTileTypeGrass(){
        tm.size = 5;
        tm.map = tm.setMap();

        char type = tm.getTileType(1,4);
        assertEquals('g', type);
    }

    @Test
    public void testNumberOfPlayers(){

        int num = tm.numOfPlayers;
        assertEquals(3,num);
    }

    @Test
    public void testSetSize(){
        assertEquals(true, tm.setSize(6));
    }

    @Test
    public void testSetSizeFail(){
        assertEquals(false, tm.setSize(4));
    }

    @Test
    public void testSetMapSizeWithTrueConditionWhenPlayerIsLessThan5(){

        boolean ans = tm.setMapSize(6);
        boolean expected = true;
        assertEquals(expected, ans);
    }

    @Test
    public void testSetMapSizeWithTrueConditionWhenPlayerIsLessThan9(){

        boolean ans = tm2.setMapSize(10);
        boolean expected = true;
        assertEquals(expected, ans);
    }

    @Test
    public void testSetMapSizeWithFalseConditionWhenPlayerIsLessThan9(){

        boolean ans = tm2.setMapSize(7);
        boolean expected = false;
        assertEquals(expected, ans);
    }


    @Test
    public void testSetMapSizeWithFalseConditionWhenPlayerIsLessThan5(){

        boolean ans = tm.setMapSize(3);
        boolean expected = false;
        assertEquals(expected, ans);
    }

    @After
    public void teardown(){
        tm = null;
        tm2 = null;
    }
}
