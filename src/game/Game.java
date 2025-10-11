package game;

import board.Board;
import players.*;
import board.*;

public class Game {

    //Attributes
    private Board board;
    private Player white = new Player(true);
    private Player black = new Player(false);
    private boolean currentPlayer = true;
    private String move;
    public boolean checkMate = false;


    //Default constructor
    public Game() {
    }

    public void start() {
        board = new Board();
        board.displayBoard();
    }

    //Getters
    public String getMove(){
        return move;
    }

    //Setters
    public void setMove(String move){
        this.move = move;
    }
    //public void end() {}

    public void play() {
        //Todo: Check for checkmate logic
        while(!checkMate){
            boolean legal = false;
            while (!legal) {
                requestMove(); //Get move from player, initial validation
                legal = sendMove(); //Attempt to perform move
            }
            currentPlayer = !currentPlayer; //Switch players
            //Check if new current player is checkmated
            if(board.isCheck(currentPlayer)){

            }
            board.displayBoard();           //Show new board state
        }
    }

    public void requestMove() {
        if (currentPlayer) { //If white's turn
            System.out.println("Current turn: White.");
            //Check if self is in check, warn self if checkChecks tick the check checkbox
            if(board.isCheck(white.getColor())){
                System.out.println("You are in check!");
            }
            move = white.makeMove();
        }
        else { //!playerTurn //Black's turn
            System.out.println("Current turn: Black.");
            //Check if self is in check, warn self if checkChecks tick the check checkbox
            if(board.isCheck(black.getColor())){
                System.out.println("You are in check!");
            }
            move = black.makeMove();
        }
    }

    //If enemy is in check, check if it's a checkmate

    public boolean sendMove() {
        //Parse received move into square objects
        Square from = new Square(Character.getNumericValue(move.charAt(1)),colLetterToCol(Character.toUpperCase(move.charAt(0))));
        Square to = new Square(Character.getNumericValue(move.charAt(4)),colLetterToCol(Character.toUpperCase(move.charAt(3))));
        //Send move to board for further processing
        return board.movePiece(from, to, currentPlayer);
    }

    public int colLetterToCol(char c) {
        int col = 0;
        switch (c) {
            case 'A':
                col = 1;
                break;
            case 'B':
                col = 2;
                break;
            case 'C':
                col = 3;
                break;
            case 'D':
                col = 4;
                break;
            case 'E':
                col = 5;
                break;
            case 'F':
                col = 6;
                break;
            case 'G':
                col = 7;
                break;
            case 'H':
                col = 8;
                break;
            default:
                System.out.println("Something went wrong.");
        }
        return col;
    }
}