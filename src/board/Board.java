package board;

import piece.Piece;

import java.util.ArrayList;

public class Board {
    /**Private attributes*/
    //This wastes 17/81 of the array but the game is not exactly big enough to suffer from lack of optimization
    //and it will be much easier to work with
    final int NUMCOLUMNS = 9;
    final int NUMROWS = 9;

    //Declare board's squares
    private final Square[][] squares = new Square[NUMROWS][NUMCOLUMNS];

    private Piece piece;
    //private Array pieceStatus -- Single Boolean array for capture instead of 2 array lists?
    private ArrayList<Piece> livePieces;
    private ArrayList<Piece> capturedPieces;

    //Default constructor
    public Board() {
        //Initialize squares
        for (int r = NUMROWS-1; r > 0; r--) {
            for (int c = 1; c < NUMCOLUMNS; c++) {
                squares[r][c] = new Square(r, c);
            }
        }
        //Todo: Shuffle some of this
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
        for (int r = NUMROWS-1; r > 0; r--) {
            System.out.print(r + " ");
            for (int f = 1; f < NUMCOLUMNS; f++) {
                Square square = squares[r][f];
                if (square.getPiece() != null)
                    System.out.print("PH ");  //Display piece if there is one (currently displaying placeholder text)
                else if ((r + f) % 2 == 0)
                    System.out.print("## ");
                else
                    System.out.print("   ");
            }
            System.out.println();
        }
    }
}
