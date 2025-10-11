package board;

import pieces.*;
import utility.Constants;
import java.util.ArrayList;

public class Board {
    /**Private attributes*/
    //This wastes 17/81 of the array but the game is not exactly big enough to suffer from lack of optimization
    //and it will be much easier to work with
    //final int Constants.NUM_COLS = 9;
    //final int Constants.NUM_ROWS = 9;

    //Declare board's squares
    private final Square[][] squares = new Square[Constants.NUM_ROWS][Constants.NUM_COLS];

    private Piece piece;
    //private Array pieceStatus -- Single Boolean array for capture instead of 2 array lists?
    private ArrayList<Piece> pieces;

    //Default constructor
    public Board() {
        //Initialize squares
        for (int r = Constants.NUM_ROWS-1; r > 0; r--) {
            for (int c = 1; c < Constants.NUM_COLS; c++) {
                squares[r][c] = new Square(r, c);
            }
        }
        placeBlack();
        placeWhite();
    }

    //Getters
    public Square[][] getSquares() {
        return squares;
    }
    private Piece getPiece() {
        return piece;
    }
    /*public ArrayList<Piece> getLivePieces() {
        return livePieces;
    }
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }*/

    //Setters
    public void setSquares(ArrayList<Square> squares) {
    }

    //Methods
    public boolean movePiece(Square from, Square to, boolean color) {
        //Todo: Flag for valid move should be handled by game.play() method so a new move can be input
        //Load corresponding squares on the board
        Square orig = squares[from.getRow()][from.getColumn()];
        Square dest = squares[to.getRow()][to.getColumn()];
        piece = orig.getPiece();
        //Validate move
        if (!validMove(orig, dest, color)) {
            return false;
        }
        //Todo: Checking for check logic goes here or in validMove?

        //Perform move
        dest.setPiece(piece);
        piece.setSquare(dest); //Alternatively, set it to 'to' if circular logic problems arise
        orig.setPiece(null);
        return true;
    }
    //public boolean isCheck(boolean color) {}
    //public boolean isCheckmate(boolean color) {}
    public void displayBoard() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int c = Constants.NUM_ROWS-1; c > 0; c--) {
            System.out.print(c + " "); //Prints row numbers on left
            for (int r = 1; r < Constants.NUM_COLS; r++) {
                Square square = squares[c][r];
                if (square.getPiece() != null){ //Check for piece on space
                    piece = square.getPiece();
                    if (piece.getColor()){      //If a piece is there, print letter for piece's color
                        System.out.print("w");
                    }
                    else { //(!piece.getColor()
                        System.out.print("b");
                    }
                    //Then print a letter corresponding to the type of piece
                    if (piece.getClass().equals(Pawn.class)) {
                        System.out.print("P ");
                    }
                    else if (piece.getClass().equals(Rook.class)) {
                        System.out.print("R ");
                    }
                    else if (piece.getClass().equals(Queen.class)) {
                        System.out.print("Q ");
                    }
                    else if (piece.getClass().equals(King.class)) {
                        System.out.print("K ");
                    }
                    else if (piece.getClass().equals(Bishop.class)) {
                        System.out.print("B ");
                    }
                    else{ // (piece.getClass().equals(Knight.class))
                        System.out.print("N ");
                    }
                } else if ((c + r) % 2 == 0)
                    System.out.print("## "); //## for black spaces
                else
                    System.out.print("   "); //Blank for white
            }
            System.out.println();
        }
    }

    public void placeBlack() {
        squares[8][1].setPiece(new Rook(false, squares[8][1]));
        squares[8][2].setPiece(new Bishop(false, squares[8][2]));
        squares[8][3].setPiece(new Knight(false, squares[8][3]));
        squares[8][4].setPiece(new Queen(false, squares[8][4]));
        squares[8][5].setPiece(new King(false, squares[8][5]));
        squares[8][6].setPiece(new Knight(false, squares[8][6]));
        squares[8][7].setPiece(new Bishop(false, squares[8][7]));
        squares[8][8].setPiece(new Rook(false, squares[8][8]));
        for (int c = 1; c < Constants.NUM_COLS; c++) {
            squares[7][c].setPiece(new Pawn(false, squares[7][c]));
        }
    }

    public void placeWhite() {
        squares[1][1].setPiece(new Rook(true, squares[1][1]));
        squares[1][2].setPiece(new Bishop(true, squares[1][2]));
        squares[1][3].setPiece(new Knight(true, squares[1][3]));
        squares[1][4].setPiece(new Queen(true, squares[1][4]));
        squares[1][5].setPiece(new King(true, squares[1][5]));
        squares[1][6].setPiece(new Knight(true, squares[1][6]));
        squares[1][7].setPiece(new Bishop(true, squares[1][7]));
        squares[1][8].setPiece(new Rook(true, squares[1][2]));
        for (int c = 1; c < Constants.NUM_COLS; c++) {
            squares[2][c].setPiece(new Pawn(true, squares[2][c]));
        }
    }

    public boolean validMove(Square from, Square to, boolean color) {
        //Move validation
        try {
            //Are the entered squares on the board?
            //Todo: Deletion candidate, board boundaries are checked during initial move validation
            if(from.getRow() < 1 || from.getRow() > Constants.NUM_ROWS || from.getColumn() < 1 || from.getColumn() > Constants.NUM_COLS ||
                    to.getRow() < 1 || to.getRow() > Constants.NUM_ROWS || to.getColumn() < 1 || to.getColumn() > Constants.NUM_COLS) {
                throw new IllegalArgumentException("Invalid square(s)");
            }
            if(from.getPiece() == null) { //Does start square have a piece?
                throw new IllegalArgumentException("No piece in that square!");
            }
            if(from.getPiece().getColor() != color) { //Is that piece the current player's color?
                throw new IllegalArgumentException("That piece is not your color.");
            }
            if(!piece.possibleMoves(squares).contains(to)) { //Is this a legal move for that piece?
                throw new IllegalArgumentException("That move is not legal!");
            }
            return true;
            //Todo: Check if the move will put the player's own king in check.

            //Color of a prospective capture is checked at the piece level and doesn't need to be rechecked here
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
