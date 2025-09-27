package board;

import piece.Piece;

import java.util.ArrayList;

public class Board {
    /**Private attributes*/
    final int NUMFILES = 8;
    final int NUMRANKS = 8;

    private final Square[][] squares = new Square[NUMRANKS][NUMFILES];
    private Piece piece;
    //private Array pieceStatus -- Single Boolean array for capture instead of 2 array lists?
    private ArrayList<Piece> livePieces;
    private ArrayList<Piece> capturedPieces;

    //Default constructor
    public Board() {
        //Initialize full livePieces;
        //Initialize empty capturedPieces;
        //Place pieces on board;
        //Start white turn;
    }

    //Getters
    public Square[][] getSquares() {
        return squares;
    }
    private Piece getPiece() {
        return piece;
    }
    public ArrayList<Piece> getLivePieces() {
        return livePieces;
    }
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    //Setters
    public void setSquares(ArrayList<Square> squares) {
    }

    //Other methods
    public void movePiece(Square from, Square to) {
        /*if (piece on square)
            if (valid move) -- Is currentPlayer in check, is move legal
                if willCapture
                    captureSteps
                move piece
                run isCheck
                run isCheckmate
                pass turn
            else printError
         */
    }
    //public Boolean isCheck(Color color) {}
    public void displayBoard() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int r = 8; r > 0; r--) {
            System.out.print(r + " ");
            for (int f = 1; f <= NUMFILES; f++) {
                //if (getPiece(r, f) != null) Display piece if there is one
                //else if
                if ((r + f) % 2 == 0)
                    System.out.print("## ");
                else
                    System.out.print("   ");
            }
            System.out.println();
        }
    }
}
