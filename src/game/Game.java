package game;

import board.Board;
import players.*;
import board.*;

import java.util.Objects;
import java.util.Scanner;

/**
 * The Game consists of two players and the Board they play on.
 * It handles the players, processes their moves for Board to use,
 * and determines the winner
 * @deprecated Class was phased out in favor of GUIBoard
 */
public class Game {

    //Attributes
    private Board board;
    private Player white = new Player(true);
    private Player black = new Player(false);
    private boolean currentPlayer = true;
    private String move;
    public boolean checkmate = false;


    //Default constructor
    public Game() {
    }

    public void start() {
        board = new Board();
    }

    //Getters
    public String getMove(){
        return move;
    }

    //Setters
    public void setMove(String move){
        this.move = move;
    }

    /**
     * Core method of Game. Each turn, asks the current player for their
     * move and attempts to execute it. Invalid or illegal moves are
     * discarded and another move requested. Once a move is successful,
     * the updated board is printed to the console
     */
    /*public void play() {
        while(!checkmate){
            boolean legal = false;
            while (!legal) {
                requestMove(); //Get move from player, initial validation
                legal = sendMove(); //Attempt to perform move
            }
            currentPlayer = !currentPlayer; //Switch players
            *//*Check if new current player is checkmated
            if(board.isCheck(currentPlayer)){
                checkmate = board.isCheckmate(currentPlayer);
                if(checkmate){
                    if (currentPlayer == true) {System.out.print("White ");}
                    else  {System.out.print("Black ");}
                    System.out.print("may be checkmated. Admit defeat? Y/N ");
                    Scanner scan = new Scanner(System.in);
                    Character surrender = scan.next().charAt(0);
                    Character.toUpperCase(surrender);
                    if (surrender != 'Y') {
                        checkmate = false;}
                }
            }*//*
            board.displayBoard();           //Show new board state
        }
    }*/

    /**
     * Ends the game, announcing the victor
     */
    public void end() {
        if(currentPlayer){
            System.out.println("White has been checkmated! Game over, black wins!");
        }
        else System.out.println("Black has been checkmated! Game over, white wins!");
    }

    /**
     * Asks the current player to input a move, warning them if they are
     * currently in check.
     * Actual input and initial validation of the move is handled by Player
     */
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

    /**
     * Splits the validated move string into squares and calls Board to
     * confirm the move is legal and perform it
     * @return Boolean value. True if move was successfully performed
     */
    public String sendMove() {
        //Parse received move into square objects
        //Check if move is a castle attempt. If yes, check if castling is legal.
        //If yes, castle and end the move.
        if (Objects.equals(move, "O-O") || Objects.equals(move, "o-o")) {//Kingside castle attempt
            return board.performCastling(currentPlayer, true);
            //Alternate logic if the original method bugs out
            /*if (board.canCastle(currentPlayer, true)) {
                board.performCastling(currentPlayer, true);
                return true;
            }
            else { //Illegal castle attempt
                return false;
            }*/
        }
        else if (Objects.equals(move, "O-O-O") || Objects.equals(move, "o-o-o")) {//Queenside castle attempt
            return board.performCastling(currentPlayer, false);
            /*if (board.canCastle(currentPlayer, false)) {
                board.performCastling(currentPlayer, false);
                return true;
            }
            else { //Illegal castle attempt
                return false;
            }*/

        }
        else { //Not a castling attempt
            Square from = new Square(Character.getNumericValue(move.charAt(1)), colLetterToCol(Character.toUpperCase(move.charAt(0))));
            Square to = new Square(Character.getNumericValue(move.charAt(4)), colLetterToCol(Character.toUpperCase(move.charAt(3))));
            //Send move to board for further processing
            return board.movePiece(from, to, currentPlayer);
        }
    }

    /**
     * Helper function, changes input file letter to an integer for processing
     * @param c The given file
     * @return Integer value, 1-8 inclusive
     */
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