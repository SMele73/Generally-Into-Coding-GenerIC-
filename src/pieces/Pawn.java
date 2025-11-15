package pieces;

import GUI.Square;
import java.util.ArrayList;
import java.util.List;
import utility.Constants;

import static utility.Constants.ENEMY_COLOR;

public class Pawn extends Piece {

    public Pawn(Square square) {super(square);}
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
        int currentCol = getSquare().getCol();
        int direction = (getColor()) ? 1 : -1;   // assign pawn direction

        int nextRow = currentRow + direction;
        int twoRow = currentRow + 2 * direction;

        // one square forward
        if (nextRow >= 1 && nextRow <= 8) {                     // bounds checking
            Square forward = board[nextRow][currentCol];
            if (forward.getText().isEmpty()) {
                moves.add(forward);
            }
        }

        // two squares forward while checking path
        if (!moved && twoRow >= 1 && twoRow <= 8) {          // condition and bounds checking
            Square forward = board[nextRow][currentCol];
            Square twoForward = board[twoRow][currentCol];
            if (forward.getText().isEmpty() && twoForward.getText().isEmpty()) {
                moves.add(twoForward);
            }
        }

        int[] diagnolCol = {currentCol -1, currentCol +1};
        for (int diagCol : diagnolCol) {
            if (nextRow >= 1 && nextRow <= 8 && diagCol >= 1 && diagCol <= 8) {     //bound checking and diag
                Square target = board[nextRow][diagCol];
                if (sameColor(target) == ENEMY_COLOR) { //If target holds an enemy piece
                    moves.add(target);
                }

            }
        }

        // TODO Promotion here?
        return moves;
    }



}
