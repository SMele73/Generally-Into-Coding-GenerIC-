package game;

import board.Board;
import players.*;
import board.*;
import java.util.Scanner;

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

    public void play() {
        //Todo: Check for checkmate logic
        while(!checkmate){
            boolean legal = false;
            while (!legal) {
                requestMove(); //Get move from player, initial validation
                legal = sendMove(); //Attempt to perform move
            }
            currentPlayer = !currentPlayer; //Switch players
            //Check if new current player is checkmated
            if(board.isCheck(currentPlayer)){
                checkmate = board.isCheckmate(currentPlayer);
                if(checkmate){
                    if (currentPlayer == true) {System.out.print("White ");}
                    else /*currentPlayer == false*/ {System.out.print("Black ");}
                    System.out.print("may be checkmated. Admit defeat? Y/N ");
                    Scanner scan = new Scanner(System.in);
                    Character surrender = scan.next().charAt(0);
                    Character.toUpperCase(surrender);
                    if (surrender != 'Y') {
                        checkmate = false;}
                }
            }
            board.displayBoard();           //Show new board state
        }
    }

    public void end() {
        if(currentPlayer){
            System.out.println("White has been checkmated! Game over, black wins!");
        }
        else System.out.println("Black has been checkmated! Game over, white wins!");
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