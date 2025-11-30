package GUI;

import board.Board;
import pieces.*;
import utility.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Frontend of the chess game. Displays the board and options to the players, receives and passes on their input
 */
public class GUIBoard extends JFrame implements MouseListener, ActionListener {

    //Create squares to be used with mouse listeners and listener flags
    private Square squareClick;
    private Square squareDrag;
    private boolean destinationPick = false;
    private boolean leftSquare = false;
    private Square newSquare;
    private JPanel boardPanel = new JPanel();  //Initialized in makeBoard, modified by boardOptions
    private final Square[][] board = new Square[8][8]; //Array of buttons

    private Board gameBoard;

    private boolean whiteToMove = true;                                 //Keeps track of the current player
    private JLabel playerTurnLabel = new JLabel("White to move "); //Turn indicator

    /**
     * @see GUIOptions These setters are used by GUIOptions to modify the board
     * All options go through each square on the board in turn.
     */
    //Setters
    //These operations could be much more efficient if combined, but we're not exactly hurting for processor power
    public void setLightSquareColor(Color color) {
        for(Square[] row : board){
            for(Square column : row){
                if(column.isLightSquare()){
                    column.setBackground(color);}}}}
    public void setDarkSquareColor(Color color) {
        for(Square[] row : board){
            for(Square column : row){
                if(!column.isLightSquare()){
                    column.setBackground(color);}}}}
    public void setLightPieceColor(Color color) {
        for(Square[] row : board){
            for(Square column : row){
                //If square holds a light-colored piece
                if(column.getText().equals("\u2654") || column.getText().equals("\u2655") || column.getText().equals("\u2656") || column.getText().equals("\u2657")
                        || column.getText().equals("\u2658") || column.getText().equals("\u2659")){
                    column.setForeground(color);}}}}
    public void setDarkPieceColor(Color color) {
        for(Square[] row : board){
            for(Square column : row){
                //If square holds a light-colored piece
                if(column.getText().equals("\u265A") || column.getText().equals("\u265B") || column.getText().equals("\u265C") || column.getText().equals("\u265D")
                        || column.getText().equals("\u265E") || column.getText().equals("\u265F")){
                    column.setForeground(color);}}}}
    public void setBoardSize(int size) {
        this.setSize(size, size);
    }
    public void setPieceSize(int size) {
        for(Square[] row : board){
            for(Square column : row){
                column.setFont(new Font("Serif", Font.BOLD, size));}}}

    public GUIBoard() {
        gameBoard = new Board();
        makeBoard();
        syncGuiFromBoard();
    }

    /**
     * Makes the GUI of the board; a 2x2 array of buttons with a menu bar and board borders
     * Does not place the pieces, those are pulled from the backend in a different method
     */
    public void makeBoard() {
        // Create the main frame for the chess board
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // make MenuBar
        makeMenuBar();

        // Create a JPanel that will hold the board
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setPreferredSize(new Dimension(800, 800));

        //Create the chess board squares and pieces
        for (int row = 0; row < Constants.GUI_ROWS; row++) {
            for (int col = 0; col < Constants.GUI_COLS; col++) {
                // Create a new Square
                Square square = new Square(row, col);

                //Assign listeners to square, square to button array and board container
                board[row][col] = square;
                board[row][col].addMouseListener(this);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
            }
        }

        //Create the board border panels
        JPanel topAlpha = createAlphaPanel();
        JPanel bottomAlpha = createAlphaPanel();
        JPanel leftNums = createNumericPanel();
        JPanel rightNums = createNumericPanel();

        // add the panels to the board
        this.add(topAlpha, BorderLayout.NORTH);     // Alpha guides
        this.add(bottomAlpha, BorderLayout.SOUTH);

        this.add(leftNums, BorderLayout.WEST);      // Numeric guides
        this.add(rightNums, BorderLayout.EAST);

        this.add(boardPanel, BorderLayout.CENTER);  // Add board panel to the frame
        this.pack();  // Pack the frame to fit the board
        this.setLocationRelativeTo(null);  // Center the frame

        this.setVisible(true);      // make it all visible
    }

    /**
     * Method handles click-moves
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Square source = (Square) e.getSource();
        //If the cursor changed squares during the click, do nothing
        if (!leftSquare) {
            //If this is the first click of a move, assign clicked square to source
            if (!destinationPick) {
                squareClick = source;
                destinationPick = true;
            }
            else { // second click. destination to attempt move thru backend
                // Attempts to move instead of triggering move regardless
                attemptMove(squareClick, source);

                // reset flag and empty squareClick to prepare for next move
                destinationPick = false;
                squareClick = null;
            }
        }
    }

    /**
     * @deprecated Does nothing, but can't use MouseAdapter instead as the class is already extending JFrame
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //This does nothing, mouse clicks being handled more consistently by the actionPerformed method
    }

    /**
     * Sets flag and loads clicked square in preparation for a possible drag-move
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        leftSquare = false;
        squareDrag = (Square) e.getSource();
        System.out.println("Origin square: " + (squareDrag.getRow() + 1) + " " + (squareDrag.getCol() + 1));
    }

    /**
     * On LMB release, sends the move to the backend if the cursor left the original square after LMB press
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //If the mouse has left the original square since the button was clicked, treat it as a drag.
        if (leftSquare) {
            attemptMove(squareDrag, newSquare);
        }
    }

    /**
     * Each time the cursor enters a new square that square loaded into newSquare in case it's needed
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {newSquare = (Square) e.getSource();}

    /**
     * This method sets a flag that determines whether a click is handled by the ActionPerformed listener (click-move)
     * or the mousePressed and mouseReleased listeners (drag-move)
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        leftSquare = true;
    }

    /**
     * Dialog box popup once a checkmate ends the game
     * @param winnerColor is the victor
     * @param loserColor is the vanquished
     */
    private void showWinnerDialog(String winnerColor, String loserColor) {
        // Pass winning color + message to display
        String message = winnerColor + " wins! The " + loserColor + " king has been checkmated.";
        // Option pane with yes and no continuing on Winner dialog
        // YES_NO_OPTION will return as an int
        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\nWould you like to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);

        // previous choice initiates either a clear and restart or exit of the game
        if (choice == JOptionPane.YES_OPTION) {
            // dispose the current GUI and make a new board. kinda like delete but doesn't exit program
            this.dispose();
            new GUIBoard();  // restart a new game window
        }
        else {
            System.exit(0);
        }
    }

    /**
     * Method to construct the game's menu options
     */
    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Game Menu    -- could make this its own method...
        JMenu gameMenu = new JMenu("Game");                         // Game tab in menu
        JMenuItem newGame = new JMenuItem("New Game");            // New Game item
        newGame.addActionListener(e -> {                    // action for newGame
            this.dispose();
            new GUIBoard();
        });
        JMenuItem exit = new JMenuItem( "Exit");                    // Exit item
        exit.addActionListener(e -> System.exit(0));    // exit listener
        gameMenu.add(newGame);      // adding the newgame
        gameMenu.add(exit);         // adding exit

        // Options Menu     -- could also make its own method to keep separate....
        JMenu optionsMenu = new JMenu("Options");                         // Options tab in menu
        JMenuItem boardOptions = new JMenuItem("Board Options");        // board options
        boardOptions.addActionListener(e -> new GUIOptions(this));    // make GUIOptions window
        optionsMenu.add(boardOptions);      // add board options to options menu

        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);

        // glue turn indicator to the right side of the menu bar
        menuBar.add(Box.createHorizontalGlue());    // glues following menu items to the right side of the menu bar
        menuBar.add(playerTurnLabel);

        this.setJMenuBar(menuBar);
    }

    /**
     * Creates the bars that go above and below the board and show file letters
     * @return the panel
     */
    private JPanel createAlphaPanel() {
        Color borderColor = new Color(120, 85, 60);     // special background color

        JPanel alphaPanel = new JPanel(new BorderLayout());

        // need spacer in corner
        JPanel westCorner =  new JPanel();
        westCorner.setPreferredSize(new Dimension(25, 25));
        westCorner.setBackground(borderColor);
        // westCorner.add(new JLabel(""));             if we wanted anything in the corner panel
        alphaPanel.add(westCorner, BorderLayout.WEST);

        // another spacer on other side for matchy matchy
        JPanel eastCorner =  new JPanel();
        eastCorner.setPreferredSize(new Dimension(25, 25));
        eastCorner.setBackground(borderColor);
        // eastCorner.add(new JLabel(""));             if we wanted anything in the corner panel
        alphaPanel.add(eastCorner, BorderLayout.EAST);

        JPanel colPanel = new JPanel(new GridLayout(1, 8));
        colPanel.setPreferredSize(new Dimension(800, 25));
        colPanel.setBackground(borderColor);

        for (char c = 'A'; c <= 'H'; c++) {
            JLabel col = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            col.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            col.setForeground(Color.WHITE);
            colPanel.add(col);
        }

        alphaPanel.add(colPanel, BorderLayout.CENTER);
        return alphaPanel;
    }

    /**
     * Creates the bars that go to the left and right of the board and show row numbers
     * @return the panel
     */
    private JPanel createNumericPanel(){
        Color borderColor = new Color(120, 85, 60);
        JPanel rowPanel = new JPanel(new GridLayout(8, 1));
        rowPanel.setPreferredSize(new Dimension(25, 800));
        rowPanel.setBackground(borderColor);

        for (int i = 8; i >= 1; i--) {
            JLabel row = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            row.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            row.setForeground(Color.WHITE);
            rowPanel.add(row);
        }
        return rowPanel;
    }

    /**
     * Method pulls pieces from backend and translates them to player-visible icons
     * @param piece is the piece object to translate
     * @return the proper unicode character
     */
    private String getUnicodeForPiece(Piece piece) {
        boolean isWhite = piece.getColor();

        return switch (piece) {
            case King king -> isWhite ? "♔" : "♚";     //"\u2654" : "\u265A"
            case Queen queen -> isWhite ? "♕" : "♛";   //"\u2655" : "\u265B"
            case Rook rook -> isWhite ? "♖" : "♜";     //"\u2656" : "\u265C"
            case Bishop bishop -> isWhite ? "♗" : "♝"; //"\u2657" : "\u265D"
            case Knight knight -> isWhite ? "♘" : "♞"; //"\u2658" : "\u265E"
            case Pawn pawn -> isWhite ? "♙" : "♟";     //"\u2659" : "\u265F"
            default -> "";
        };

    }

    /**
     * Main driver for what the GUI shows to players. Iterates through board and translates contents of
     * each square to what the players should see
     */
    private void syncGuiFromBoard () {
        board.Square[][] simSquares = gameBoard.getSquares();

        for (int guiRow = 0; guiRow < 8; guiRow++) {
            for (int guiCol = 0; guiCol < 8; guiCol++) {
                // needs conversion to backend
                int boardRow = guiRowToBoardRow(guiRow);
                int boardCol = guiColToBoardCol(guiCol);

                // checks the square and gets the piece
                board.Square backendSquare = simSquares[boardRow][boardCol];
                Piece piece = backendSquare.getPiece();
                Square guiSquare = board[guiRow][guiCol];

                // leaves the square empty if no piece, else it grabs the
                // unicode with the helper function based on the piece found
                if (piece == null) {
                    guiSquare.setText("");
                } else {
                    guiSquare.setText(getUnicodeForPiece(piece));
                }
            }
        }
        repaint();
    }

    // our new method to try moving pieces that will go through the backend

    /**
     * Method sends a move to the backend for processing. Aborts and displays popup for invalid moves.
     * If move is valid, update the board state and display any required messages
     * @param fromGui is the move's origin square
     * @param toGui is the move's destination square
     */
    private void attemptMove(Square fromGui,  Square toGui) {
        if (fromGui == null || toGui == null)
            return;

        // conversions
        int fromBoardRow = guiRowToBoardRow(fromGui.getRow());
        int fromBoardCol = guiColToBoardCol(fromGui.getCol());
        int toBoardRow = guiRowToBoardRow(toGui.getRow());
        int toBoardCol = guiColToBoardCol(toGui.getCol());

        board.Square[][] simSquares = gameBoard.getSquares();
        board.Square from = simSquares[fromBoardRow][fromBoardCol];
        board.Square to = simSquares[toBoardRow][toBoardCol];

        //If move is invalid, display a popup with the reason why and don't switch turns
        String moved = gameBoard.movePiece(from, to, whiteToMove);
        if (!moved.equals("Valid move")) {
            JOptionPane.showMessageDialog(
                    this,
                    moved,
                    "Illegal move!",
                    JOptionPane.WARNING_MESSAGE);
            return; // must exit if failed to not end turn
        }
        // handle promotion if needed
        handlePromotion(to);

        // player turn switching after successful move
        whiteToMove = !whiteToMove;
        playerTurnLabel.setText(whiteToMove ? "White to move " : "Black to move ");   //Swap turn label

        // update Gui after move
        syncGuiFromBoard();

        // check for check/mate of next player
        boolean sideInCheckColor = whiteToMove;
        if (gameBoard.isCheck(sideInCheckColor)) {
            String checked = sideInCheckColor ? "White" : "Black";
            JOptionPane.showMessageDialog(
                    this,
                    checked + " king is in check!");

            //
            if (gameBoard.isCheckmate(sideInCheckColor)) {
                String winner = sideInCheckColor ? "Black" : "White";
                showWinnerDialog(winner, checked);
            }
        }
    }
    // moved pawn promotion to pop up box for options and
    private void handlePromotion(board.Square dest){
        pieces.Piece p = dest.getPiece();

        // not pawn, no promotion needed
        if (!(p instanceof Pawn)) {
            return;
        }

        // pawn is promoted if it reaches the end of the board
        int promotionRow = p.getColor() ? 8 : 1; // white promotes on 8, black is 1
        if (dest.getRow() != promotionRow) {
            return;
        }

        // show dialog box for promotion choice
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};

        int choiceIndex = JOptionPane.showOptionDialog(
                this,
                "Promote pawn to:",
                "Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        // close after option selected
        if (choiceIndex == JOptionPane.CLOSED_OPTION) {
            choiceIndex = 0;        // default to queen if window closed
        }

        // switch for choice and promote pawn
        Constants.PromotionChoice choice = switch (choiceIndex) {
            case 1 -> Constants.PromotionChoice.ROOK;
            case 2 -> Constants.PromotionChoice.BISHOP;
            case 3 -> Constants.PromotionChoice.KNIGHT;
            default -> Constants.PromotionChoice.QUEEN;
        };
        gameBoard.promotePawn(dest, choice);
    }

    /**
     * Helper method, translates frontend row to backend row
     * @param guiRow is the frontend row
     * @return is the backend row
     */
    private int guiRowToBoardRow(int guiRow) {return 8 - guiRow;}

    /**
     * Helper method, translates frontend column to backend column
     * @param guiCol is the frontend column
     * @return is the backend column
     */
    private int guiColToBoardCol(int guiCol) {return guiCol + 1;}
}