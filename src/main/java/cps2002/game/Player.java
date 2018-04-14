package cps2002.game;

public class Player {

    // player's current Position
    Position position;

    // holds the map size
    int mapSize = 0;

    // player's map
    Map tm;

    // player's starting Position
    Position startingPosition;

    /* Player constructor
     * Parameters: mpSize-> sets the map size
      *            numPlayers-> sets the number of players in the game*/
    public Player(int mpSize, int numPlayers){
        mapSize = mpSize;
        tm = new Map(numPlayers);
        tm.size = mapSize;

        // generate the player map
        tm.generatePlayerMap();
    }

    /* set the player's Position
    * Parameters: newPos-> the player's new position */
    public boolean setPosition(Position newPos){

        position = newPos;

        return true;
    }


}