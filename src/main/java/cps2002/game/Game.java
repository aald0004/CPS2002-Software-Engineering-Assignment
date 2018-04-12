package cps2002.game;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.Scanner;


/* This class functions as the main game class. It contains the main game loop, generates
the HTML files, and sets up the initial settings.
 */

public class Game {

    // used to read user input
    Scanner sc = new Scanner(System.in);

    // set the constraints for choosing the number of players
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 2;

    // holds the number of players
    int numOfPlayers = 0;

    // holds the board size
    int boardSize = 0;

    // stores the players
    ArrayList<Player> players = new ArrayList<Player>();


    // boolean to store whether the treasure has been found
    boolean treasureFound = false;

    // stores the moves in the current turn
    ArrayList<String> currentSetOfMoves = new ArrayList<String>();

    // holds the move command
    String move = "";

    // random function for setting the players' starting positions
    Random rand = new Random();

    // variable containing the game map
    Map tm;



    // main game loop
    public void startGame(){

        /* choose the number of players
        if the number of players does not obey the constraints, output an error
        message and ask the user to enter the number of players again
        repeat until constraints are satisfied */
        System.out.println("Choose the number of players");
        String numPlayers = sc.nextLine();

        numOfPlayers = Integer.parseInt(numPlayers);


        while (setPlayers(numOfPlayers) == false) {


            System.out.println("ERROR:Number of Players must be between 2 and 8.");

            System.out.println("Choose the number of players");
            String newNumPlayers = sc.nextLine();

            numOfPlayers = Integer.parseInt(newNumPlayers);
        }

        tm = new Map(numOfPlayers);


        /* choose the map size
        if the size does not obey the constraints, output an error
        message and ask the user to enter the size again
        repeat until constraints are satisfied */
        System.out.println("Choose map size");
        String mpSize = sc.nextLine();

        int mapSize = Integer.parseInt(mpSize);


        while (tm.setSize(mapSize) == false) {


            System.out.println("ERROR:Invalid map size.");


            System.out.println("Choose map size");
            String newMapSize = sc.nextLine();

            mapSize = Integer.parseInt(newMapSize);

            }

        // set the map size
        tm.size = mapSize;

        // generate the map
        tm.map = tm.setMap();

        //tm.getTileType(0, 0);

        // set the board size
        boardSize = tm.size;

        // add the players to the array list
        addPlayers();

        // set the players' initial positions
        setInitialPos();

        // reveal the tile colour of the player's current position
        for(int i = 0; i < numOfPlayers; i++){
            revealColour(players.get(i).position.getx(),players.get(i).position.gety(),i);
        }

        // run the game loop
        gameLoop();




    }



    public void setInitialPos(){
        // loop through all the players and set their starint position
        for(int i = 0; i < players.size(); i++){
            // create a new Position
            Position startPos = startingPos();

            // set the player's starting position
            players.get(i).startingPosition = startPos;

            // set the player's current position
            players.get(i).position = startPos;
        }
    }

    /* run the main functionality of the game.
     * generate the player HTML file
     * run the moves in the current turn
     * repeat until treasure is found */
    public void gameLoop(){

        // repeat the main functionality until the treasure is found
        while(treasureFound == false){


            /* for loop to generate the HTML file of each player and ask the user to enter their
            next move */
            for(int i = 0; i < players.size(); i++) {
               /* try {
                    generateHTMLFiles(htmlString(i), i);

                } catch(IOException e){
                    e.printStackTrace();
                }*/

                // ask the user to enter their move
                System.out.println("Enter Move");
                String choice = sc.nextLine();

                // add the user's move to the array list containing the moves
                currentSetOfMoves.add(choice);
            }

            // loop through the players executing the moves
            for(int j = 0; j < numOfPlayers; j++){

                // get the move of the current player
                move = currentSetOfMoves.get(j);

                // execute the move
                executeMove(move, players.get(j),j);

                // reveal the tile colour of the newly discovered tile
                revealColour(players.get(j).position.getx(), players.get(j).position.gety(), j);



            }

            // empty the moves
            for(int k = 0; k<numOfPlayers;k++){
                currentSetOfMoves.remove(0);
            }

        }

        // generate the HTML file for each player
        /*for(int i = 0; i < players.size(); i++) {
            try {
                generateHTMLFiles(htmlString(i), i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }


    /*public void printMap(){

        System.out.println("Player map");

        for(int i = 0; i < tm.size; i ++){
            for(int j = 0; j < tm.size; j++){

                System.out.print(players.get(0).tm.map[i][j]);
            }
            System.out.println("\n");
        }

        System.out.println("*********************");
    }*/

    /* generate a random Position
    * Returns: Position-> player's starting position*/
    public Position startingPos(){
        int x;
        int y;

        // generate random x and y coordinates within the map's size
        x = rand.nextInt(tm.size - 1);
        y = rand.nextInt(tm.size - 1);



        // check that the generated Position is not a water tile position
        while(tm.getTileType(x,y) == 'w') {
            x = rand.nextInt(tm.size+1);
            y = rand.nextInt(tm.size+1);
        }


        Position pos = new Position(x,y);

        return pos;

    }

    // add players to array list
    public void addPlayers(){
        /* loop through the number of players
        and add an instance of the player class to the array list */
        for(int i = 0; i < numOfPlayers; i++){

            Player p = new Player(tm.size,numOfPlayers);
            players.add(p);
        }

    }

    /* execute the move
    * Parameters: moveCommand-> the current move command (U/D/L/R)
    *             player-> the current player
    *             index-> the index in the players array list of the current player*/
    public void executeMove(String moveCommand, Player player, int index){

        // retrieve the x and y coordinate of the player's current position
        int x = player.position.getx();
        int y = player.position.gety();


        /* if the move command is U, move the player up
        * if the player goes out of bounds, leave the player in their current position*/
        if(moveCommand.equals("U")){
            // decrement the y coordinate
            y--;

            // check if the player is out of bounds
            if(y < 0 || y>=tm.size){

                // restore the y coordinate
                y++;

                // output an error message if out of bounds
                System.out.println("Invalid Move. Out of bounds");
            }

        /* if the move command is D, move the player down
        * if the player goes out of bounds, leave the player in their current position*/
        } else if(moveCommand.equals("D")){
            // increment the y coordinate
            y++;

            // check if the player is out of bounds
            if(y < 0 || y>=tm.size){

                // restore the y coordinate
                y--;

                // output an error message if out of bounds
                System.out.println("Invalid Move. Out of bounds");

            }

        /* if the move command is L, move the player left
        * if the player goes out of bounds, leave the player in their current position*/
        } else if(moveCommand.equals("L")){

            // decrement the x coordinate
            x--;

            // check if the player is out of bounds
            if(x < 0 || x>=tm.size){

                // increment the x coordinate
                x++;


                // output an error message if out of bounds
                System.out.println("Invalid Move. Out of bounds");

            }

        /* if the move command is R, move the player right
        * if the player goes out of bounds, leave the player in their current position*/
        } else {

            // increment the x coordinate
            x++;

            // check if the player is out of bounds
            if(x < 0 || x>=tm.size){

                // decrement the x coordinate
                x--;


                // output an error message if out of bounds
                System.out.println("Invalid Move. Out of bounds");

            }

        }


        // create a new Position with the new x and y values
        Position newPos = new Position(x,y);


        // if the new position is a water tile, set the player's position to its starting position
        if(tm.getTileType(x,y) == 'w'){
            player.setPosition(player.startingPosition);

            //revealColour(x,y,index, prevx, prevy);
        } else {

            // set the player's position to the new position
            player.setPosition(newPos);


        }

    }


    /* reveal the colour of the player's current position and
     * update the player's map to reflect the new tile
     * Parameters: x-> player's x-coordinate
     *             y-> player's y-coordinate
     *             index-> the index in the array list of the current player*/
    public void revealColour(int x, int y, int index){

        // retrieve the tile type
        char tileType = tm.getTileType(x,y);

        // set the tile type on the player's map
        if(tileType == 'g'){
            players.get(index).tm.revealColour(x,y,'G');

        } else {
            players.get(index).tm.revealColour(x,y,tileType);
        }

    }

    /*public String htmlString(int num){
        num++;
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "<style>\n" +
                "* {\n" +
                "    box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "img {\n"+
                "max-width: 100%;\n"+
                "max-height: 100%;\n"+
                "}\n" +
                ".column {\n" +
                "    float: left;\n" +
                "    width: 10%;\n" +
                "    padding: 10px;\n" +
                "}\n" +
                "\n" +
                "\n" +
                ".row:after {\n" +
                "    content: \"\";\n" +
                "    display: table;\n" +
                "    clear: both;\n" +
                "}\n" +
                "\n" +
                ".title{\n" +
                "\t\n" +
                "    font-size: 17px;\n" +
                "    width: 25%;\n" +
                "}\n" +
                "\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class = \"title\">\n"+
                "<h3> Player " + num + " Map</h3>\n"+
                "<br>\n" +
                "\n";
                String colour = "";
                num--;
                printMap();
                for(int i = 0; i < tm.size; i++){

                    html = html + "<div class=\"row\">\n" +
                            "\n";

                    for(int j = 0; j < tm.size; j++){

                        if(players.get(num).tm.getTileType(j,i) == 'g'){
                            colour = "#aaa";
                        } else if(players.get(num).tm.getTileType(j,i) == 'w'){
                            colour = "#0000FF";
                        } else if(players.get(num).tm.getTileType(j,i) == 't'){
                            colour = "#FFFF00";
                        } else {
                            colour = "#008000";
                        }

                        if(i == players.get(num).position.gety() && j == players.get(num).position.getx()){
                            html = html + "  <div class=\"column\" style=\"background-color:" + colour + ";\">\n"+
                            "<img src = \"./Chess_pdt60.png\">\n" +
                                    "  </div>\n" +
                                    "\n";
                        } else {

                            html = html + "  <div class=\"column\" style=\"background-color:" + colour + ";\">\n" +
                                    "  </div>\n" +
                                    "\n";
                        }
                    }

                    html = html + "</div>";
                }
                html = html +
                "\n" +
                "\n" +
                "</div>\n"+
                "</body>\n" +
                "</html>";

        return html;

    }

    public void generateHTMLFiles(String html, int num) throws IOException{
        BufferedWriter bw = null;

        try {
            num++;
            File file = new File("map_player "+num+".html");
            file.createNewFile();

            bw = new BufferedWriter(new FileWriter(file));


            bw.write(html);

            bw.flush();



        } catch(IOException e) {

            // if I/O error occurs
            e.printStackTrace();
        } finally {

            if(bw!=null)
                bw.close();
        }

    }*/





    /* return whether the number of players is valid
    * Parameters: num-> number of players entered*/
    public boolean setPlayers(int num){


        boolean validNumber = false;

        validNumber = setNumPlayers(num);



        return validNumber;

    }




    /* set the num of players
    * Parameters: n-> number of players entered
    * Return: boolean-> returns if the number of players entered fits the constraints (true)*/
    public boolean setNumPlayers(int n){

        // check if the number of players entered obeys the constraints
        if (n >= MIN_PLAYERS && n <= MAX_PLAYERS){
            return true;
        } else {
            return false;
        }
    }





    // main method
    public static void main(String args[]){

        // create a game instance
        Game game = new Game();

        // start the game
        game.startGame();
    }

}
