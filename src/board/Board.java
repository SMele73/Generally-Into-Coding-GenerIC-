package board;

import pieces.*;
import utility.Constants;
import java.util.ArrayList;
import java.util.List;

public class Board {
    /**
     * Private attributes
     */


    //Declare board's squares
    private final Square[][] squares = new Square[Constants.NUM_ROWS][Constants.NUM_COLS];

    private Piece piece;
    //private Array pieceStatus -- Single Boolean array for capture instead of 2 array lists?
    //Currently no need for an array at all
    //private ArrayList<Piece> pieces;

    //Default constructor
    public Board() {
        //Initialize squares
        for (int r = Constants.NUM_ROWS - 1; r > 0; r--) {
            for (int c = 1; c < Constants.NUM_COLS; c++) {
                squares[r][c] = new Square(r, c);
            }
        }
        placeBlack();
        placeWhite();
    }

    //Getters
    public Square[][] getSquares() {
        return squares;
    }

    private Piece getPiece() {
        return piece;
    }
    /*public ArrayList<Piece> getLivePieces() {
        return livePieces;
    }
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }*/

    //Setters
    public void setSquares(ArrayList<Square> squares) {
    }

    //Methods
    public boolean movePiece(Square from, Square to, boolean color) {
        //Load corresponding squares on the board
        Square orig = squares[from.getRow()][from.getColumn()];
        Square dest = squares[to.getRow()][to.getColumn()];
        piece = orig.getPiece();

        //Prepare squares for a move invalidated by checks
        Square rollbackFrom = squares[from.getRow()][from.getColumn()];
        Piece backFrom = rollbackFrom.getPiece();
        Square rollbackTo = squares[to.getRow()][to.getColumn()];
        Piece backTo = rollbackTo.getPiece();

        //Validate move
        if (!validMove(orig, dest, color)) {
            return false;
        }

        //Perform move
        dest.setPiece(piece);
        piece.setSquare(to); //Originally was set to dest rather than two. This should help avoid circular logic problems
        orig.setPiece(null);

        //Check if self is now in check. If checkChecks find self in check, rollback the move
        if (isCheck(color)){
            System.out.println("Illegal move, you leave yourself in check");
            //Rollback end square
            piece = rollbackTo.getPiece();
            piece.setSquare(to);
            squares[to.getRow()][to.getColumn()] = rollbackTo;
            //Rollback start square
            piece = backFrom;
            piece.setSquare(from);
            squares[from.getRow()][from.getColumn()] = rollbackFrom;
            return false;
        }
        return true;
    }

    public void displayBoard() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int c = Constants.NUM_ROWS - 1; c > 0; c--) {
            System.out.print(c + " "); //Prints row numbers on left
            for (int r = 1; r < Constants.NUM_COLS; r++) {
                Square square = squares[c][r];
                if (square.getPiece() != null) { //Check for piece on space
                    piece = square.getPiece();
                    if (piece.getColor()) {      //If a piece is there, print letter for piece's color
                        System.out.print("w");
                    } else { //(!piece.getColor()
                        System.out.print("b");
                    }
                    //Then print a letter corresponding to the type of piece
                    if (piece.getClass().equals(Pawn.class)) {
                        System.out.print("P ");
                    } else if (piece.getClass().equals(Rook.class)) {
                        System.out.print("R ");
                    } else if (piece.getClass().equals(Queen.class)) {
                        System.out.print("Q ");
                    } else if (piece.getClass().equals(King.class)) {
                        System.out.print("K ");
                    } else if (piece.getClass().equals(Bishop.class)) {
                        System.out.print("B ");
                    } else { // (piece.getClass().equals(Knight.class))
                        System.out.print("N ");
                    }
                } else if ((c + r) % 2 == 0)
                    System.out.print("## "); //## for black spaces
                else
                    System.out.print("   "); //Blank for white
            }
            System.out.println();
        }
    }

    public void placeBlack() {
        squares[8][1].setPiece(new Rook(false, squares[8][1]));
        squares[8][2].setPiece(new Knight(false, squares[8][2]));
        squares[8][3].setPiece(new Bishop(false, squares[8][3]));
        squares[8][4].setPiece(new Queen(false, squares[8][4]));
        squares[8][5].setPiece(new King(false, squares[8][5]));
        squares[8][6].setPiece(new Bishop(false, squares[8][6]));
        squares[8][7].setPiece(new Knight(false, squares[8][7]));
        squares[8][8].setPiece(new Rook(false, squares[8][8]));
        for (int c = 1; c < Constants.NUM_COLS; c++) {
            squares[7][c].setPiece(new Pawn(false, squares[7][c]));
        }
    }

    public void placeWhite() {
        squares[1][1].setPiece(new Rook(true, squares[1][1]));
        squares[1][2].setPiece(new Knight(true, squares[1][2]));
        squares[1][3].setPiece(new Bishop(true, squares[1][3]));
        squares[1][4].setPiece(new Queen(true, squares[1][4]));
        squares[1][5].setPiece(new King(true, squares[1][5]));
        squares[1][6].setPiece(new Bishop(true, squares[1][6]));
        squares[1][7].setPiece(new Knight(true, squares[1][7]));
        squares[1][8].setPiece(new Rook(true, squares[1][8]));
        for (int c = 1; c < Constants.NUM_COLS; c++) {
            squares[2][c].setPiece(new Pawn(true, squares[2][c]));
        }
    }

    public boolean validMove(Square from, Square to, boolean color) {
        from = squares[from.getRow()][from.getColumn()];
        piece = from.getPiece();
        //Move validation
        try {
            //Are the entered squares on the board?
            //Todo: Deletion candidate, board boundaries are checked during initial move validation
            if (from.getRow() < 1 || from.getRow() > Constants.NUM_ROWS || from.getColumn() < 1 || from.getColumn() > Constants.NUM_COLS ||
                    to.getRow() < 1 || to.getRow() > Constants.NUM_ROWS || to.getColumn() < 1 || to.getColumn() > Constants.NUM_COLS) {
                throw new IllegalArgumentException("Invalid square(s)");
            }
            if (piece == null) { //Does start square have a piece?
                throw new IllegalArgumentException("No piece in that square!");
            }
            if (from.getPiece().getColor() != color) { //Is that piece the current player's color?
                throw new IllegalArgumentException("That piece is not your color.");
            }
            if (!piece.possibleMoves(squares).contains(to)) { //Is this a legal move for that piece?
                throw new IllegalArgumentException("That move is not legal!");
            }
            return true;

            //Color of a prospective capture is checked at the piece level and doesn't need to be rechecked here
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isCheck(boolean targetKingColor) {
        //Find king to check for check
        Square kingLoc = kingSearch(targetKingColor);
        //Check board for checks
        for (int r = Constants.NUM_ROWS - 1; r > 0; r--) {
            //Is checked king in check?
            for (int c = 1; c < Constants.NUM_COLS; c++) {
                if (checkCheck(squares[r][c], kingLoc)) {return true;}
            }
        }
        //If checkChecks show no check, check off that there is no check
        return false;
    }

    public boolean isCheckmate(boolean color){
        //Find king to check checkmate
        Square kingLoc = kingSearch(color);
        Square kingDest = new Square(0, 0);  //Part of cheap isCheckmate impl
        Piece kingCap = null;       //Part of cheap isCheckmate impl

        //Get possible moves of king in check
        List<Square> kingMoves = (kingLoc.getPiece().possibleMoves(squares));
        for (Square kingMove: kingMoves) {
            //Setup rollback for possible king destination
            /*Square*/ kingDest = kingMove;
            /*Piece*/ kingCap = kingDest.getPiece();

            //If king is able to move to square without ending up in check,
            //rollback the move and return false
            if (movePiece(kingLoc, kingMove, color)){
                squares[kingLoc.getRow()][kingLoc.getColumn()] = kingLoc;
                squares[kingDest.getRow()][kingDest.getColumn()] = kingDest;
                if (kingCap != null){
                    kingCap.setSquare(kingDest);
                }
                return false;
            }
        }
        //Revert final king checkmate check
        squares[kingLoc.getRow()][kingLoc.getColumn()] = kingLoc;
        squares[kingDest.getRow()][kingDest.getColumn()] = kingDest;
        if (kingCap != null){
            kingCap.setSquare(kingDest);
        }
        return true; //If king has no valid moves, return true
    }

    //Helper methods
    //Locate and return the square containing the king of a particular color
    public Square kingSearch ( boolean color){
        for (int r = Constants.NUM_ROWS - 1; r > 0; r--) {
            for (int c = 1; c < Constants.NUM_COLS; c++) {
                if (squares[r][c].getPiece() != null) {
                    if (squares[r][c].getPiece().getClass().equals(King.class) && squares[r][c].getPiece().getColor() == color) {
                        return squares[r][c];
                    }
                }
            }
        }
        //This should never be reached. Optional enhancement is to set up a proper exception throw
        System.out.println("Fatal error 404, king not found.");
        return squares[0][0];
    }
    //Determine if that square contains a piece that could capture the enemy king
    public boolean checkCheck(Square s, Square k) {
        //Is there a piece on this square?
        //Todo: Optional enhancement: Don't check move list if currently examined piece is king's color
        if (s.getPiece() != null) {
            //Get each possible move of the current piece
            List<Square> current = s.getPiece().possibleMoves(squares);
            //Check if the king's location is a possible move for the current piece
            for (Square curr : current) {
                if (curr.same(k)) {return true;}
            }
        }
        //If no piece or can't reach king, return false
        return false;
    }

    // Check if castling is an option.
    private boolean canCastle(boolean color, boolean kingside) {
        int row = color ? 1 : 8;            // white = 1, black = 8
        int kingCol = 5;                    // kings always in col 5
        int direction = kingside ? 1 : -1;  // kingside 1, queenside -1
        int rookCol = kingside ? 8 : 1;     // kingside 8, queenside 1

        // get king
        Square kingSquare = squares[row][kingCol];
        Piece king = kingSquare.getPiece();

        // validate king
        if (!(king instanceof King) || ((King) king).hasMoved()) {
            return false;
        }

        // get rook
        Square rookSquare = squares[row][rookCol];
        Piece rook =  rookSquare.getPiece();

        // validate rook
        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false;
        }

        // check for empty spaces between king and rook
        // iterates along board in the indicated direction checking squares are empty
        // returning false if anything occupies
        for (int c = kingCol + direction; c != rookCol; c += direction) {
            if (squares[row][c].getPiece() != null) {
                return false;
            }
        }
        return true;
    }

    // actually perform the castle
    public boolean performCastling(boolean color, boolean kingside) {
        if (!canCastle(color, kingside)) {
            System.out.println("Castling not allowed.");
            return false;
        }

        // set coordinates for move
        int row = color ? 1 : 8;            // white = 1, black = 8
        int kingCol = 5;                    // kings always col 5
        int rookCol = kingside ? 8 : 1;     // kingside 8, queenside 1
        int kingTarget = kingside ? 7 : 3;  // kingside move king to 7, queenside to 3
        int rookTarget = kingside ? 6 : 4;  // kingside rook to 6, queenside to 4

        Square kingSquare = squares[row][kingCol];  // grab square where king should be
        Square rookSquare = squares[row][rookCol];  // for later clearing
        King king = (King) kingSquare.getPiece();   // cast grabbed piece to king or methods don't work
        Rook rook = (Rook) rookSquare.getPiece();

        // Perform move
        squares[row][kingTarget].setPiece(king);    // move king to new spot
        squares[row][rookTarget].setPiece(rook);
        kingSquare.setPiece(null);                  // clear original spot
        rookSquare.setPiece(null);

        king.markMoved();                           // mark piece moved
        rook.markMoved();

        // output if successful by color and side
        System.out.println((color ? "White" : "Black") +
                (kingside ? " castles kingside" : " castles queenside"));
        return true;
    }

}