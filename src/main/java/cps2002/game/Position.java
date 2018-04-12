package cps2002.game;

public class Position {

    // private x coordinate
    private int x;

    // private y coordinate
    private int y;

    /* Position constructor
    * Parameters: x-> sets the x coordinate
    *             y-> sets the y coordinate*/
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /* getter for the x coordinate
    * Returns: int-> Position's x coordinate*/
    public int getx(){
        return x;
    }

    /* getter for the y coordinate
     * Returns: int-> Position's y coordinate*/
    public int gety(){

        return y;
    }
}
