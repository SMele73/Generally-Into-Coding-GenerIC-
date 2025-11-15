package pieces;

import GUI.Square;
import java.util.ArrayList;
import java.util.List;
import utility.Constants;

import static utility.Constants.EMPTY;
import static utility.Constants.ENEMY_COLOR;

public class Bishop extends Piece {

    public Bishop(Square square) {super(square);}

    @Override
    public void move(Square newSquare) {
        setSquare(newSquare);
    }

    @Override
    public List<Square> possibleMoves(Square[][] board) {
        List<Square> moves = new ArrayList<>();
        int row = getSquare().getRow();
        int col = getSquare().getCol();

        int[][] directions = {
                { 1, 1},    // up right
                { 1,-1},    // up left
                {-1, 1},    // down right
                {-1,-1},    // down left
        };

        // slide until leave board or blocked
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            while (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8){

                Square target = board[newRow][newCol];      // pull target square from board
                int colorCheck = sameColor(target);

                //If target square is empty, add it and continue
                if (colorCheck == EMPTY)
                    moves.add(target);

                //If target square has an enemy piece, add it and stop
                else if (colorCheck == ENEMY_COLOR){
                    moves.add(target);
                    break;
                }
                //If target square has a friendly piece, stop immediately
                else{
                    break;
                }

                newRow += dir[0];
                newCol += dir[1];
            }

        }
        return moves;
    }

}
