package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(boolean color, Square square) {
        super(color, square);
    }
    private boolean moved = false;
    public void markMoved() { moved = true; }
    public boolean hasMoved() { return moved; }

    @Override
    public void move(Square newSquare) {
        setSquare(newSquare);
        markMoved();
    }

    @Override
    public List<Square> possibleMoves(Square[][] board) {
        List<Square> moves = new ArrayList<>();
        int currentRow = getSquare().getRow();
        int currentCol = getSquare().getColumn();
        int direction = (getColor()) ? 1 : -1;   // assign pawn direction
        boolean myColor = getColor();

        int nextRow = currentRow + direction;
        int twoRow = currentRow + 2 * direction;

        // one square forward
        if (nextRow >= 1 && nextRow <= 8) {                     // bounds checking
            Square forward = board[nextRow][currentCol];
            if (forward.getPiece() == null) {
                moves.add(forward);
            }
        }

        // two squares forward while checking path
        if (!moved && twoRow >= 1 && twoRow <= 8) {          // condition and bounds checking
            Square forward = board[nextRow][currentCol];
            Square twoForward = board[twoRow][currentCol];
            if (forward.getPiece() == null && twoForward.getPiece() == null) {
                moves.add(twoForward);
            }
        }

        // TODO check diagonals for capture
        //  board[nextRow][currentCol-1], board[nextRow][currentCol+1] if in bounds

        // TODO En passant somehow

        // TODO Promotion here?
        return moves;
    }



}
