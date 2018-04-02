package cps2002.game;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class Game {

    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 2;

    int numOfPlayers = 0;

    ArrayList<JFrame> playerFrames = new ArrayList<JFrame>();

    int boardSize = 0;

    // main game loop
    public void startGame(){


            String numPlayers = (String) JOptionPane.showInputDialog(null, "Choose number of players",
                    "Number of Players", JOptionPane.QUESTION_MESSAGE);

            numOfPlayers = Integer.parseInt(numPlayers);


            while (setPlayers(numOfPlayers) == false) {

                JOptionPane.showMessageDialog(null, "Number of Players must be between 2 and 8.",
                        "Error", JOptionPane.ERROR_MESSAGE);

                numPlayers = (String) JOptionPane.showInputDialog(null, "Choose number of players",
                        "Number of Players", JOptionPane.QUESTION_MESSAGE);

                numOfPlayers = Integer.parseInt(numPlayers);
            }

            tileMap tm = new tileMap(numOfPlayers);

            String mpSize = (String) JOptionPane.showInputDialog(null, "Choose map size",
                    "Map Size", JOptionPane.QUESTION_MESSAGE);

            int mapSize = Integer.parseInt(mpSize);


            while (tm.setSize(mapSize) == false) {

                JOptionPane.showMessageDialog(null, "Invalid map size",
                        "Error", JOptionPane.ERROR_MESSAGE);

                mpSize = (String) JOptionPane.showInputDialog(null, "Choose map size",
                        "Map Size", JOptionPane.QUESTION_MESSAGE);

                mapSize = Integer.parseInt(mpSize);

            }

            tm.size = mapSize;
            tm.map = tm.setMap();

            tm.getTileType(0, 0);


            boardSize = tm.size;


            setFrames();



    }

    public void setFrames(){
        for(int i = 0; i < numOfPlayers; i++){
            int playerNum = i+1;
            JFrame playerFrame = new JFrame("Player "+ playerNum);

            Container container = playerFrame.getContentPane();
            ArrayList < JPanel > components = new ArrayList < JPanel >();

            JPanel[][] panel = new JPanel[boardSize][boardSize];

            setPanel(playerFrame, boardSize, panel, container);

            playerFrames.add(playerFrame);
        }
    }

    public boolean setPlayers(int num){


        boolean validNumber = false;

        validNumber = setNumPlayers(num);



        return validNumber;

    }

    // set up the panels
    public void setPanels(JPanel[][] panel, Container container, int size){
        JPanel temp = null;

        int counter = 0;
        for (int i = 0; i < panel.length; i++) {
            for (int j = 0; j < panel[i].length; j++) {

                temp = new JPanel(new BorderLayout());
                temp.setSize(400/size, 400/size);
                panel[i][j] = temp;
                container.add(temp);

            }


        }
    }

    // set up initial colours
    public void setInitialColours(JPanel[][] panel, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                panel[i][j].setBackground(Color.GRAY);
            }
        }

    }

    // set up the JFrame
    public void setPanel(JFrame frame, int size, JPanel panel[][], Container container){


        frame.setLayout( new GridLayout(size,size) );


        frame.setPreferredSize(new Dimension(400,400));

        setPanels(panel, container, size);


        setInitialColours(panel, size);



        frame.pack();
        frame.setVisible(true);


    }


    // set the num of players
    public boolean setNumPlayers(int n){
        if (n >= MIN_PLAYERS && n <= MAX_PLAYERS){
            return true;
        } else {
            return false;
        }
    }



    public static void main(String args[]){
        Game game = new Game();
        game.startGame();
    }

}
