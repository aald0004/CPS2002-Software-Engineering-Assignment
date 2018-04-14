package cps2002.game;

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


/* This class functions as the main game class. It contains the main game loop, generates
the HTML files, and sets up the initial settings.
 */

public class Game {

    // used to read user input
    Scanner sc = new Scanner(System.in);

    // set the constraints for choosing the number of players
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 1;

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

    int mapSize = 0;

    // variable containing the game map
    Map tm;




    // main game loop
    public void startGame(){

        // delete the html files in HTMLFiles folder
        File dir = new File("HTMLFiles");

        for(File file: dir.listFiles())
            if (!file.isDirectory() && !(file.getName().equals("Chess_pdt60.png")))
                file.delete();

        choosePlayers(sc);
        chooseMapSize(sc);

        // set the map size
        tm.size = mapSize;

        // generate the map
        tm.map = tm.setMap();
        tm.addWaterTiles();
        tm.addTreasureTile();


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
        gameLoop(sc);





    }


    /* choose the map size
     * Parameters: scanner-> used to read user input*/
    public void chooseMapSize(Scanner scanner){

        tm = new Map(numOfPlayers);


         /* choose the map size
        if the size does not obey the constraints, output an error
        message and ask the user to enter the size again
        repeat until constraints are satisfied */
        System.out.println("Choose map size");
        String mpSize = scanner.nextLine();

        mapSize = Integer.parseInt(mpSize);


        while (tm.setSize(mapSize) == false) {


            System.out.println("ERROR:Invalid map size.");


            System.out.println("Choose map size");
            String newMapSize = scanner.nextLine();

            mapSize = Integer.parseInt(newMapSize);

        }
    }



    /* choose the number of players
    * Parameters: scanner-> used to read user input*/
    public void choosePlayers(Scanner scanner){

        /* choose the number of players
        if the number of players does not obey the constraints, output an error
        message and ask the user to enter the number of players again
        repeat until constraints are satisfied */
        System.out.println("Choose the number of players");
        String numPlayers = scanner.nextLine();

        numOfPlayers = Integer.parseInt(numPlayers);


        while (setPlayers(numOfPlayers) == false) {


            System.out.println("ERROR:Number of Players must be between 2 and 8.");

            System.out.println("Choose the number of players");
            String newNumPlayers = scanner.nextLine();

            numOfPlayers = Integer.parseInt(newNumPlayers);
        }
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
    public void gameLoop(Scanner scanner){
        int nextPlayerIndex = -1;
        // repeat the main functionality until the treasure is found
        while(treasureFound == false && currentSetOfMoves.size() == 0){


            /* for loop to generate the HTML file of each player and ask the user to enter their
            next move */
            for(int i = 0; i < players.size(); i++) {
                try {
                    generateHTMLFiles(htmlString(i), i);

                } catch(IOException e){
                    e.printStackTrace();
                }

                // ask the user to enter their move
                System.out.println("Enter Move");
                String choice = scanner.nextLine();

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
        for(int i = 0; i < players.size(); i++) {
            try {
                generateHTMLFiles(htmlString(i), i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*public void printMap(){



        for(int i = 0; i < tm.size; i ++){
            for(int j = 0; j < tm.size; j++){

                System.out.print(tm.map[i][j]);
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

            revealColour(x,y,index);
            player.setPosition(player.startingPosition);

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

        // set treasure found to true
        if(tileType == 't'){
            treasureFound = true;
        }

    }

    /* generate the html string
    * Parameters: num-> index of the player in the array, to include which player in the file*/
    public String htmlString(int num) {
        num++;

        // the html file contains the styling
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n\n" +
                "/* set the display type to a table */\n" +
                ".table{\n" +
                "display: table;\n" +
                "width: 100%;\n" +
                "color: white;\n"+
                "}\n\n" +
                "/* set the display type to a table row */\n" +
                ".tableRow {\n" +
                "display: table-row;\n" +
                "}\n\n" +
                "/* set the grass tile, treasure tile and water tile to have a border,\n" +
                "padding and set their display type */\n" +
                ".grassTile, .treasureTile, .waterTile, .greyTile {\n" +
                "border: 1px solid #999999;\n" +
                "display: table-cell;\n" +
                "padding: 10px 10px;\n" +
                "}\n\n" +
                "/* set the grass tile colour to green */\n" +
                ".grassTile{\n" +
                "background-color:green;\n" +
                "}\n\n" +
                ".greyTile{\n" +
                "background-color:grey;\n" +
                "}\n\n" +
                "/* set the treasure tile colour to yellow */\n" +
                ".treasureTile{\n" +
                "background-color:yellow;\n" +
                "}\n\n" +
                "/* set the water tile colour to blue */\n" +
                ".waterTile{\n" +
                "background-color:blue;\n" +
                "}\n\n"+
                ".tableBody {\n"+
                "display: table-row-group;\n"+
                "}\n\n"+
                "/* set the font size and width of the title */\n"+
                ".title{\n"+
                "font-size: 17px;\n"+
                "width: 25%;\n"+
                "}\n\n"+
                "</style\n\n>"+
                "</head>\n\n"+
                "<body>\n"+
                "<div class = \"title\">\n"+
                "<h3> Player "+num+" Map</h3>\n\n"+
                "<br>\n\n"+
                "<div class=\"table\"> \n"+
                "<div class=\"tableBody\">\n";



        // add the rows and the different tiles based on the player's map
        String tileType = "";
        num--;
        //printMap();
        for(int i = 0; i < tm.size; i++){

            html = html + "<div class=\"tableRow\">\n\n";


            // set the tile type for the html table
            for(int j = 0; j < tm.size; j++){

                if(players.get(num).tm.getTileType(j,i) == 'g'){
                    tileType = "greyTile";
                } else if(players.get(num).tm.getTileType(j,i) == 'w'){
                    tileType = "waterTile";
                } else if(players.get(num).tm.getTileType(j,i) == 't'){
                    tileType = "treasureTile";
                } else {
                    tileType = "grassTile";
                }

                // add the player image to its current position
                if(i == players.get(num).position.gety() && j == players.get(num).position.getx()){

                    html = html + "<div class=\""+tileType+"\" " +
                            "style = \"background-position:center;" +
                            "background-image:url('./Chess_pdt60.png')\">" +
                            "</div>\n";

                } else {


                    html = html + "<div class=\""+tileType+"\">&nbsp;</div>\n";
                }
            }

            html = html + "</div>\n\n";

        }

        html = html + "</div>\n"+
                "</div>\n"+
                "</div>\n"+
                "</body>\n"+
                "</html>";

        return html;

    }

    /* save the html file
    * Parameters: html-> html string to be saved
    *             num-> index of the player in the array, used for the file name
    * Throws exception if error occurs in creating and saving file*/
    public void generateHTMLFiles(String html, int num) throws IOException{
        BufferedWriter bw = null;

        try {
            num++;


            // create the html file
            File file = new File("HTMLFiles/map_player_"+num+".html");
            file.createNewFile();;

            bw = new BufferedWriter(new FileWriter(file));


            // write the string to the buffered writer
            bw.write(html);

            bw.flush();



        } catch(IOException e) {

            // if I/O error occurs print the stack trace
            e.printStackTrace();
        } finally {

            // close the buffered writer
            if(bw!=null)
                bw.close();
        }

    }





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
