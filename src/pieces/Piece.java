package pieces;

import board.Square;
import java.util.List;

public abstract class Piece {

    private final boolean color;    //White = true, black = false
    private Square square;

    public Piece(boolean color) {
        this.color = color;
    }
    public Piece(boolean color, Square square) {
        this.color = color;
        this.square = square;
    }
    public boolean getColor() { return color; }
    public Square getSquare() { return square; }
    public void setSquare(Square square) { this.square = square; }

    public abstract void move(Square newSquare);

    public abstract List<Square> possibleMoves(Square[][] board);

    public static int[] fromAlgebraic(String square) {
        int col = Character.toUpperCase(square.charAt(0)) - 'A' + 1;
        int row = Character.getNumericValue(square.charAt(1));
        return new int[]{row, col};
    }

    public static String toAlgebraic(Square square) {
        char col = (char)('A' + square.getColumn() -1);
        int row = square.getRow();
        return "" + col + row;    //"" quick string cast
    }

}
