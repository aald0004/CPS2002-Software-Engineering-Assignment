package cps2002.game;

public class Map {

    // constraints for map size
    public static final int MAX_SIZE = 50;
    public static final int MIN_SIZE_1 = 5;
    public static final int MIN_SIZE_2 = 8;

    // char 2D-array to store the map
    char[][] map;

    // size of the map
    int size;


    // number of players in the game
    int numOfPlayers;


    /* Map constructor
    * Parameters: numOfPlayers-> sets the number of players in the game*/
    Map(int numOfPlayers){

        this.numOfPlayers = numOfPlayers;


    }

    /* check if the map size fits the constraints
    * Parameters: s-> size of map
    * Returns: boolean-> returns true if map size is valid*/
    public boolean setSize(int s){


        boolean validNumber = false;

        validNumber = setMapSize(s);


        return validNumber;
    }

    /* determines if the map size obeys the constraints
    * Parameters: s-> map size
    * Returns: boolean-> returns true if the size obeys the constraints*/
    public boolean setMapSize(int s){

        boolean valid = false;

        // check if the number of players is less than 5
        if(numOfPlayers<=4){

            // check if the map size is in the required range
            if(s >= MIN_SIZE_1 && s<=MAX_SIZE){
                size = s;
                valid =  true;
            } else {
                valid = false;
            }
        }

        // check if the number of players is between 5 and 8
        if(numOfPlayers<=8 && numOfPlayers>4){

            // check if the map size is in the required range
            if(s >= MIN_SIZE_2 && s<=MAX_SIZE){
                valid = true;
            } else {
                valid = false;
            }
        }

        return valid;


    }

    /* generate game map
    * Returns: char[][]-> returns a 2D-array containing the game map.
    * g symbolises a grey tile
    * t symbolises a treasure tile
    * w symbolises a water tile */
    public char[][] setMap(){

        // declare a new 2D char array
        char[][] tm = new char[size][size];

        // fill the array with grey tiles
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                tm[i][j] = 'g';

            }
        }

        // set the treasure tile
        tm[3][4] = 't';

        // set the water tile
        tm[4][1] = 'w';

        return tm;

    }

    /* get the tile type
    * Parameters: x-> the x coordinate of the tile
    *             y-> the y coordinate of the tile
    * Returns: char-> the char representing the type of tile*/
    public char getTileType(int x, int y){

        return map[y][x];

    }

    // generate the initial player map - all grey tiles
    public void generatePlayerMap(){

        map = new char[size][size];

        // generate a map filled of grey tiles
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                map[i][j] = 'g';

            }
        }

    }


    /* set the map tile to a different tile (update the player map to reflect discovered tiles)
    * Parameters: x-> x coordinate of tile
    *             y-> y coordinate of tile
    *             colour-> the new colour of the tile*/
    public void revealColour(int x, int y, char colour){

        map[y][x] = colour;
    }

}
