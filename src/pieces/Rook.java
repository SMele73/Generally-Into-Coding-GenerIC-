package pieces;

import GUI.Square;
import java.util.ArrayList;
import java.util.List;
import utility.Constants;

import static utility.Constants.EMPTY;
import static utility.Constants.ENEMY_COLOR;

public class Rook extends Piece {

    public Rook(Square square) {
        super(square);
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
        int row = getSquare().getRow();
        int col = getSquare().getCol();

        int[][] directions = {
                { 1, 0},      // up
                {-1, 0},     // down
                { 0,-1},     // left
                { 0, 1},    // right
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
