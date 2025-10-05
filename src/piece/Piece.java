package piece;

import board.Square;
import java.util.List;

public abstract class Piece {
    public enum Color { WHITE, BLACK }

    private final Color color;
    private Square square;

    public Piece(Color color, Square square) {
        this.color = color;
        this.square = square;
    }
    public Color getColor() { return color; }
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
