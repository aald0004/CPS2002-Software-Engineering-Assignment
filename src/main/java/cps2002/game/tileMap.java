package cps2002.game;

import javax.swing.*;

public class tileMap {

    public static final int MAX_SIZE = 50;
    public static final int MIN_SIZE_1 = 5;
    public static final int MIN_SIZE_2 = 8;


    char[][] map;

    int size;

    int numOfPlayers;



    tileMap(int numOfPlayers){

        this.numOfPlayers = numOfPlayers;


    }

    public boolean setSize(int s){


        boolean validNumber = false;

        validNumber = setMapSize(s);


        return validNumber;
    }

    public boolean setMapSize(int s){

        boolean valid = false;

        if(numOfPlayers<=4){

            if(s >= MIN_SIZE_1 && s<=MAX_SIZE){
                size = s;
                valid =  true;
            } else {
                valid = false;
            }
        }

        if(numOfPlayers<=8 && numOfPlayers>4){
            if(s >= MIN_SIZE_2 && s<=MAX_SIZE){
                valid = true;
            } else {
                valid = false;
            }
        }

        return valid;


    }

    public char[][] setMap(){

        char[][] tm = new char[size][size];

        tm[3][4] = 't';



        tm[2][3] = 'w';
        tm[4][1] = 'w';


        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                    tm[i][j] = 'g';

            }
        }

        tm[3][4] = 't';
        tm[2][3] = 'w';
        tm[4][1] = 'w';

        return tm;

    }

    public char getTileType(int x, int y){

        return map[x][y];

    }



}
