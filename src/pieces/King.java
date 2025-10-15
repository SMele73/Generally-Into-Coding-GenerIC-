package pieces;

import board.*;
import java.util.ArrayList;
import java.util.List;


//TODO Castling


public class King extends Piece {

    public King(boolean color, Square square) {super(color, square);}

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
                { 1, 0},    // up
                {-1, 0},    // down
                { 0,-1},    // left
                { 0, 1},    // right
                { 1, 1},    // up right
                { 1,-1},    // up left
                {-1, 1},    // down right
                {-1,-1},    // down left
        };

        // check adjacent squares
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {

                Square target = board[newRow][newCol];      // pull target square from board
                Piece occupant = target.getPiece();         // check if piece exists

                if (occupant == null || occupant.getColor() != myColor) { //if empty or piece is enemy, add to moves
                    moves.add(target);
                }
            }
        }
        /*
        actually don't want castling moves to be listed as possible moves to move e1 g1
        only with special input
        // castling logic
        if (!hasMoved()) {
            // kingside
            if (canCastle(board, row, col, true)){
                moves.add(board[row][col + 2]);
            }
            // queenside
            if (canCastle(board, row, col, false)){
                moves.add(board[row][col - 2]);
            }
            }*/


        return moves;
    }
}