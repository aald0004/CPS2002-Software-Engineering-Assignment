package cps2002.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/* this class tests the functionality of the Map class */

public class MapTest {

    private Map tm;
    private Map tm2;

    // set two instances of a map. The first one with 3 players
    // and the second one with 8 players
    @Before
    public void setup(){
        tm = new Map(3);
        tm2 = new Map(8);
    }


    // test that set map functions correctly
    @Test
    public void testSetMap(){

        // set the map size
        tm.size = 5;

        // generate the map
        char[][] map = tm.setMap();

        // create an expected map
        char[][] expectedMap = new char[tm.size][tm.size];

        expectedMap[0][0] = 'g';
        expectedMap[0][1] = 'g';
        expectedMap[0][2] = 'g';
        expectedMap[0][3] = 'g';
        expectedMap[0][4] = 'g';

        expectedMap[1][0] = 'g';
        expectedMap[1][1] = 'g';
        expectedMap[1][2] = 'g';
        expectedMap[1][3] = 'g';
        expectedMap[1][4] = 'g';

        expectedMap[2][0] = 'g';
        expectedMap[2][1] = 'g';
        expectedMap[2][2] = 'g';
        expectedMap[2][3] = 'g';
        expectedMap[2][4] = 'g';

        expectedMap[3][0] = 'g';
        expectedMap[3][1] = 'g';
        expectedMap[3][2] = 'g';
        expectedMap[3][3] = 'g';
        expectedMap[3][4] = 'g';

        expectedMap[4][0] = 'g';
        expectedMap[4][1] = 'g';
        expectedMap[4][2] = 'g';
        expectedMap[4][3] = 'g';
        expectedMap[4][4] = 'g';

        expectedMap[3][4] = 'g';
        expectedMap[4][1] = 'g';

        assertArrayEquals(expectedMap,map);

    }

    // test addWaterTiles()
    @Test
    public void testWaterTiles(){

        // set the map size
        tm.size = 5;

        // generate the map
        tm.map = tm.setMap();

        // add the water tiles to the map
        tm.addWaterTiles();

        assertEquals('w', tm.map[4][1]);
    }

    // test addTreasureTile()
    @Test
    public void testTreasureTile(){

        // set the map size
        tm.size = 5;

        // generate the map
        tm.map = tm.setMap();

        // add the water tiles to the map
        tm.addWaterTiles();

        // add the treasure tile to the map
        tm.addTreasureTile();

        boolean treasureTileFound = false;


        // check if there exists a treasure tile in the map
        for(int i = 0; i < tm.size;i++){

            for(int j = 0;j < tm.size;j++){
                if(tm.getTileType(j,i) == 't'){
                    treasureTileFound = true;
                    break;
                }

                if(treasureTileFound == true){
                    break;
                }
            }

        }

        assertEquals(true, treasureTileFound);
    }

    // test that get the tile type when the tile is a treasure tile works
    @Test
    public void testGetTileTypeTreasure(){

        // set the map size
        tm.size = 5;

        // generate the map
        tm.map = tm.setMap();

        tm.addTreasureTile();

        char type = tm.getTileType(tm.getTreasurex(),tm.getTreasurey());
        assertEquals('t', type);
    }

    // test that get the tile type when the tile is a water tile works
    @Test
    public void testGetTileTypeWater(){

        // set the map size
        tm.size = 5;

        // generate the map
        tm.map = tm.setMap();
        tm.addWaterTiles();

        char type = tm.getTileType(1,4);
        assertEquals('w', type);
    }

    // test that get the tile type when the tile is a grey tile works
    @Test
    public void testGetTileTypeGrey(){

        // set the map size
        tm.size = 5;

        // generate the map
        tm.map = tm.setMap();

        char type = tm.getTileType(4,4);
        assertEquals('g', type);
    }

    // test that the number of players variable is being set correctly
    @Test
    public void testNumberOfPlayers(){

        int num = tm.numOfPlayers;
        assertEquals(3,num);
    }

    // test that the setSize function works
    @Test
    public void testSetSize(){
        assertEquals(true, tm.setSize(6));
    }

    // test that the setSize function works when the constraints fail
    @Test
    public void testSetSizeFail(){
        assertEquals(false, tm.setSize(4));
    }

    // test set map size when number of players is less than 5. Expecting true
    @Test
    public void testSetMapSizeWithTrueConditionWhenPlayerIsLessThan5(){

        boolean ans = tm.setMapSize(6);
        boolean expected = true;
        assertEquals(expected, ans);
    }

    // test set map size when number of players is less than 9. Expecting true
    @Test
    public void testSetMapSizeWithTrueConditionWhenPlayerIsLessThan9(){

        boolean ans = tm2.setMapSize(10);
        boolean expected = true;
        assertEquals(expected, ans);
    }

    // test set map size when number of players is less than 9. Expecting false
    @Test
    public void testSetMapSizeWithFalseConditionWhenPlayerIsLessThan9(){

        boolean ans = tm2.setMapSize(7);
        boolean expected = false;
        assertEquals(expected, ans);
    }

    // test set map size when number of players is less than 5. Expecting false
    @Test
    public void testSetMapSizeWithFalseConditionWhenPlayerIsLessThan5(){

        boolean ans = tm.setMapSize(3);
        boolean expected = false;
        assertEquals(expected, ans);
    }

    // test that the generatePlayerMap function's map is full of grey tiles
    @Test
    public void testGeneratePlayerMap(){
        tm.size = 2;
        tm.generatePlayerMap();
        char[][] m = new char[2][2];
        m[0][0] = 'g';
        m[0][1] = 'g';
        m[1][0] = 'g';
        m[1][1] = 'g';
        assertArrayEquals(m, tm.map);

    }

    // test that revelColour works
    @Test
    public void testRevealColour(){
        // set map size
        tm.size = 2;

        // generate player map
        tm.generatePlayerMap();

        // set the tile to water tile at position x = 0,y = 0
        tm.revealColour(0,0,'w');

        char[][] map = new char[2][2];

        map[0][0] = 'w';
        map[0][1] = 'g';
        map[1][1] = 'g';
        map[1][0] = 'g';

        assertArrayEquals(map, tm.map);

    }


    // test getx
    @Test
    public void testGetx(){

        int expected_x = tm.getTreasurex();

        assertEquals(-1,expected_x);
    }

    // test gety
    @Test
    public void testGety(){

        int expected_y = tm.getTreasurey();

        assertEquals(-1,expected_y);
    }

    // test if more than one water tile is found
    @Test
    public void testNumOfWaterTiles(){

        // set the map size
        tm.size = 20;

        // generate the map
        tm.map = tm.setMap();
        tm.addWaterTiles();
        tm.addTreasureTile();

        int counter = 0;

        boolean morethan2found = false;

        for(int i = 0; i < tm.size;i++){

            for(int j = 0;j < tm.size;j++){
                if(tm.getTileType(j,i) == 'w'){
                    counter++;
                }

                if(counter > 2){
                    morethan2found = true;
                    break;
                }
            }

        }

        assertEquals(true, morethan2found);


    }

    // test to check if valid water tile, middle section, with valid tile
    @Test
    public void testValidWaterTileMiddleSection(){


        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        assertEquals(true, tm.checkIfValidWaterPosition(3,2));


    }

    // test to check if valid water tile, middle section, with invalid tile
    @Test
    public void testInvalidTileInMiddle(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[1][2] = 3;


        assertEquals(false, tm.checkIfValidWaterPosition(2,2));
    }


    // test to check if valid water tile, corners
    @Test
    public void checkValidWaterTileCorners(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        assertEquals(true, tm.checkIfValidWaterPosition(0,0));

    }

    // test to check if valid water tile, top row, valid
    @Test
    public void testCheckIfValidWaterTileTopRow(){
        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[1][2] = 2;

        assertEquals(true, tm.checkIfValidWaterPosition(2,0));

    }

    // test to check if valid water tile, top row, false
    @Test
    public void testCheckValidWaterTileTopRow(){
        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[1][2] = 3;

        assertEquals(false, tm.checkIfValidWaterPosition(2,0));

    }


    // test to check if valid water tile, bottom row, valid
    @Test
    public void testCheckIfValidWaterTileBottomRow(){
        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[3][2] = 2;

        assertEquals(true, tm.checkIfValidWaterPosition(2,4));
    }

    // test to check if valid water tile, bottom row, false
    @Test
    public void testCheckValidWaterTileBottomRow(){
        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[3][2] = 3;

        assertEquals(false, tm.checkIfValidWaterPosition(2,4));

    }

    // test to check if valid water tile, first column, valid
    @Test
    public void testIfValidWaterTileFirstColumn(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        assertEquals(true, tm.checkIfValidWaterPosition(0,2));

    }


    // test to check if valid water tile, first column, false
    @Test
    public void testIfInvalidWaterTileFirstColumn(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[2][1] = 3;

        assertEquals(false, tm.checkIfValidWaterPosition(0,2));

    }




    // test to check if valid water tile, last column, valid
    @Test
    public void testIfValidWaterTileLastColumn(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        assertEquals(true, tm.checkIfValidWaterPosition(4,2));

    }


    // test to check if valid water tile, last column, false
    @Test
    public void testIfInvalidWaterTileLastColumn(){

        tm.size = 5;
        tm.map = tm.setMap();

        int[][] numOfBlueNeigh = new int[tm.size][tm.size];
        numOfBlueNeigh[0][0] = 1;
        numOfBlueNeigh[0][1] = 1;
        numOfBlueNeigh[0][2] = 1;
        numOfBlueNeigh[0][3] = 1;
        numOfBlueNeigh[0][4] = 1;

        numOfBlueNeigh[1][0] = 1;
        numOfBlueNeigh[1][1] = 1;
        numOfBlueNeigh[1][2] = 1;
        numOfBlueNeigh[1][3] = 1;
        numOfBlueNeigh[1][4] = 1;

        numOfBlueNeigh[2][0] = 1;
        numOfBlueNeigh[2][1] = 1;
        numOfBlueNeigh[2][2] = 1;
        numOfBlueNeigh[2][3] = 1;
        numOfBlueNeigh[2][4] = 1;

        numOfBlueNeigh[3][0] = 1;
        numOfBlueNeigh[3][1] = 1;
        numOfBlueNeigh[3][2] = 1;
        numOfBlueNeigh[3][3] = 1;
        numOfBlueNeigh[3][4] = 1;

        numOfBlueNeigh[4][0] = 1;
        numOfBlueNeigh[4][1] = 1;
        numOfBlueNeigh[4][2] = 1;
        numOfBlueNeigh[4][3] = 1;
        numOfBlueNeigh[4][4] = 1;

        tm.numberOfWaterNeighbours = numOfBlueNeigh;

        tm.numberOfWaterNeighbours[2][3] = 3;

        assertEquals(false, tm.checkIfValidWaterPosition(4,2));

    }




    // set the maps to null
    @After
    public void teardown(){
        tm = null;
        tm2 = null;
    }
}
