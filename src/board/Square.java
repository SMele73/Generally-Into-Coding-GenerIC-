package board;

import piece.Piece;

public class Square {
    //Private attributes
    //Should be file and rank to be formal chess notation, but column and row will greatly reduce confusion
    private int col;
    private Character colLetter;
    private int row;
    private Piece piece;

    //Default constructor
    public Square(int f, int r) {
        piece = null;
        col = f;
        row = r;
        //Yes, there's a much more elegant way to do this. No, I'm not taking the time to implement it.
        switch(col) {
            case 1:
                colLetter = 'A';
                break;
            case 2:
                colLetter = 'B';
                break;
            case 3:
                colLetter = 'C';
                break;
            case 4:
                colLetter = 'D';
                break;
            case 5:
                colLetter = 'E';
                break;
            case 6:
                colLetter = 'F';
                break;
            case 7:
                colLetter = 'G';
                break;
            case 8:
                colLetter = 'H';
                break;
        }
    }

    //Getters
    public int getColumn() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public Piece getPiece() {
        return piece;
    }

    //Setters
    public void setColumn(int c) {
        col = c;
    }
    public void setRow(int r) {
        row = r;
    }
    //Todo: Setter for piece goes here. Exact wording depends on piece attributes
}
