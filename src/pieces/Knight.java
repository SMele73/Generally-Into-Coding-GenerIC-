package pieces;

import GUI.Square;
import java.util.ArrayList;
import java.util.List;
import utility.Constants;

import static utility.Constants.FRIENDLY_COLOR;

public class Knight extends Piece {

    public Knight(Square square) {
        super(square);
    }

    @Override
    public void move(Square newSquare) {
        setSquare(newSquare);
    }

    @Override
    public List<Square> possibleMoves(Square[][] board) {
        List<Square> moves = new ArrayList<>();
        int row = getSquare().getRow();
        int col = getSquare().getCol();
        boolean myColor = getColor();

        // only 8 possible moves a knight can make
        int[][] moveOffsets = {
                { 2, 1}, { 2, -1},     // up two rows, left and right
                { 1, 2}, { 1, -2},     // up one row, two left and right
                {-1, 2}, {-1, -2},     // down one row, two left and right
                {-2, 1}, {-2, -1}      // down two rows, left and right
        };

        for (int[] offset : moveOffsets) {
            int newRow = offset[0] + row;
            int newCol = offset[1] + col;
            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {     // bounds checking
                Square target = board[newRow][newCol];                          // pull target from board
                //If target square is empty or has an enemy piece, it's a valid move
                if (sameColor(target) != FRIENDLY_COLOR) {
                    moves.add(target);
                }
            }
        }
        return moves;
    }
}
