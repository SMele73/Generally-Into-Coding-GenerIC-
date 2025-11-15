package pieces;

import GUI.Square;
import java.util.List;
import utility.Constants.*;

import static utility.Constants.EMPTY;
import static utility.Constants.FRIENDLY_COLOR;
import static utility.Constants.ENEMY_COLOR;

public abstract class Piece {

    private final boolean color;    //White = true, black = false
    private Square square;

    public Piece(Square square) {
        this.square = square;
        String pieceText = square.getText();
        /*Examine the text field of the new piece's square to determine its color
        If the piece is greater than whatever the unicode character before white king is
        but less than the unicode value of the black king, color is white (true)*/
        if (pieceText.compareTo("\u2653") > 0 && pieceText.compareTo("\u265A") < 0) {this.color = true;}
        else {this.color = false;}
    }
    //Getters
    public boolean getColor() { return color; }
    public Square getSquare() { return square; }
    public void setSquare(Square square) { this.square = square; }

    /*Helper method to compare colors of two different pieces
    Trinary value, -1 (EMPTY), 0 (ENEMY_COLOR), 1 (FRIENDLY_COLOR)*/
    public int sameColor(Square square){
        String otherText = square.getText();
        if (otherText.isEmpty()){return EMPTY;}
        boolean otherColor = otherText.compareTo("\u2653") > 0 && otherText.compareTo("\u265A") < 0;
        if (this.color == otherColor){return FRIENDLY_COLOR;}
        else {return ENEMY_COLOR;}
    }

    public abstract void move(Square newSquare);

    public abstract List<Square> possibleMoves(Square[][] board);
}