package pieces;

import board.Square;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(boolean color) {super(color);}
    public Rook(boolean color, Square square) {
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
        int row = getSquare().getRow();
        int col = getSquare().getColumn();
        boolean myColor = getColor();

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
                Piece occupant = target.getPiece();         // check if piece exists

                if (occupant == null) {     //if empty or occupied by enemy piece, add to moves
                    moves.add(target);
                }
                else if(occupant.getColor() != myColor){
                    moves.add(target);
                    break;
                }
                else {
                    break;                                  // end sliding if ever encounter another piece
                }

                newRow += dir[0];
                newCol += dir[1];
            }

        }
        return moves;
    }

}
