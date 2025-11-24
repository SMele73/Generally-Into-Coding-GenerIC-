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

// TODO this now must be run through Chess to start

public class GUIBoard extends JFrame implements MouseListener, ActionListener {

    //Create square to be used with mouse listeners
    //Set it outside of the board for validation
    private Square squareClick;
    private Square squareDrag;
    private boolean destinationPick = false;
    private boolean leftSquare = false;
    private Square newSquare;
    private JPanel boardPanel = new JPanel();  //Initialized in makeBoard, modified by boardOptions
    private final Square[][] board = new Square[8][8]; //Array of buttons

    private Board gameBoard;
    private boolean whiteToMove = true;

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

    //Getters

    /*  REMOVING TO RUN FROM CHESS

    public void main(String[] args) {
        Square current = new Square(8, 8);
        //makeBoard();
        boolean winner = false;
        while (!winner) {
            //play();
        }
    }

     */

    public GUIBoard() {
        gameBoard = new Board();
        makeBoard();
        syncGuiFromBoard();
    }

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
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new Square
                Square square = new Square(row, col);
                //Place piece if appropriate

                // likely be unused if i can get it working to pull from back end
                // so it will be filling the GUI a different way
                /*switch (row) {
                    case 0:
                        //square.setForeground(Color.BLACK); Line superceded by the unicode specifying color
                        if(col == 0 || col == 7) {square.setText("\u265C");} //Black rooks
                        if(col == 1 || col == 6) {square.setText("\u265E");} //Black knights
                        if(col == 2 || col == 5) {square.setText("\u265D");} //Black bishops
                        if(col == 3) {square.setText("\u265B");}             //Black queen
                        if(col == 4) {square.setText("\u265A");}             //Black king
                        break;
                    case 1:
                        //square.setForeground(Color.BLACK); Line superceded by the unicode specifying color
                        square.setText("\u265F");                            //Black pawns
                        break;
                    case 6:
                        //square.setForeground(Color.BLUE); Line superceded by the unicode specifying color
                        square.setText("\u2659");                            //White pawns
                        break;
                    case 7:
                        //square.setForeground(Color.BLUE); //Line superceded by the unicode specifying color
                        if(col == 0 || col == 7) {square.setText("\u2656");} //White rooks
                        if(col == 1 || col == 6) {square.setText("\u2658");} //White knights
                        if(col == 2 || col == 5) {square.setText("\u2657");} //White bishops
                        if(col == 3) {square.setText("\u2655");}              //White queen
                        if(col == 4) {square.setText("\u2654");}              //White king
                        break;
                }*/

                //Assign listeners to square, square to button array and board container
                board[row][col] = square;
                board[row][col].addMouseListener(this);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
            }
        }

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
/*
    //Todo: Turn this into another menu bar option once those are ready
    public void makeGUIOptions(){
        JButton GUIOptions = new JButton("Board display options");
        class optionListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                GUIOptions option = new GUIOptions(GUIBoard.this);
            }
        }
        GUIOptions.addActionListener(new optionListener());
        this.add(GUIOptions, BorderLayout.NORTH);
    }*/
    public void makeGUIOptions(){
        GUIOptions optionWindow = new GUIOptions(this);
    }

    /*Todo: Currently, these listener methods are for the main board only. Additional features may need to either
        override these events again in their method or be split into separate classes, depending on what kind of conflicts arise*/
    @Override
    public void actionPerformed(ActionEvent e) {
        Square source = (Square) e.getSource();
        //If the cursor changed squares during the click, do nothing
        if (!leftSquare) {
            //If this is the first click of a move, assign clicked square to square
            if (!destinationPick) {
                squareClick = source;
                destinationPick = true;
                System.out.println("Click origin square: " +
                        (source.getRow() + 1) + " " + (source.getCol() + 1));
            }
            else { // second click. destination to attempt move thru backend

                /*   REPLACING WITH BACKEND CHECKING
                //If this is the second click of a move, overwrite the second square with the first

                // if destination has a king, trigger game over
                String destinationText = source.getText();
                if (destinationText.equals("\u265A")) {         //Black king
                    showWinnerDialog("White", "Black");
                }
                else if (destinationText.equals("\u2654")) {    //White king
                    showWinnerDialog("Black", "White");
                }

                // do the move
                source.setText(squareClick.getText());
                source.setForeground(squareClick.getForeground());
                squareClick.setText("");
                destinationPick = false;
                 */

                System.out.println("Click destination square: "
                        + (source.getRow() + 1) + " " + (source.getCol() + 1));

                // Attempts to move instead of triggering move regardless
                attemptMove(squareClick, source);

                // resets to empty/null values for next move
                destinationPick = false;
                squareClick = null;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //This does nothing, mouse clicks being handled more consistently by the actionPerformed method
    }

    @Override
    public void mousePressed(MouseEvent e) {
        leftSquare = false;
        squareDrag = (Square) e.getSource();
        System.out.println("Origin square: " + (squareDrag.getRow() + 1) + " " + (squareDrag.getCol() + 1));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //If the mouse has left the original square since the button was clicked, treat it as a drag.
        if (leftSquare) {

            /* AGAIN USING BACK END TO HANDLE
            // if destination has a king, trigger game over
            String destinationText = newSquare.getText();
            if (destinationText.equals("\u265A")) {         //Black king
                showWinnerDialog("White", "Black");
            }
            else if (destinationText.equals("\u2654")) {    //White king
                showWinnerDialog("Black", "White");
            }

            newSquare.setText(squareDrag.getText());
            newSquare.setForeground(squareDrag.getForeground());
            squareDrag.setText("");

             */
            System.out.println("Destination square: " +
                    (newSquare.getRow() + 1) + " " + (newSquare.getCol() + 1));

            attemptMove(squareDrag, newSquare);

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        newSquare = (Square) e.getSource();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        leftSquare = true;
    }

    // Dialog box pop up for king capture
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
        boardOptions.addActionListener(e -> makeGUIOptions());    // make GUIOptions window
        optionsMenu.add(boardOptions);      // add board options to options menu


        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);

        this.setJMenuBar(menuBar);
    }

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

    private String getUnicodeForPiece(Piece piece) {
        boolean isWhite = piece.getColor();

        // instead of placing on board directly, pull from back-end
        // if piece is instanceof paint unicode
        if (piece instanceof King)  return isWhite ? "♔" : "♚";   //"\u2654" : "\u265A"
        if (piece instanceof Queen) return isWhite ? "♕" : "♛";   //"\u2655" : "\u265B"
        if (piece instanceof Rook)  return isWhite ? "♖" : "♜";   //"\u2656" : "\u265C"
        if (piece instanceof Bishop)return isWhite ? "♗" : "♝";   //"\u2657" : "\u265D"
        if (piece instanceof Knight)return isWhite ? "♘" : "♞";   //"\u2658" : "\u265E"
        if (piece instanceof Pawn)  return isWhite ? "♙" : "♟";   //"\u2659" : "\u265F"

        return "";
    }

    // since we use 1-8 for back-end we need to translate between GUI and back-end
    // also swaps side for row since 8 is up top
    private int guiRowToBoardRow(int guiRow) {return 8 - guiRow;}
    private int guiColToBoardCol(int guiCol) {return guiCol + 1;}

    // this is main driver to keep Gui correct and pulling from back end
    // will iterate through board and fill Gui square based on what back end has
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

        // generic message to warn of move failure and exit function
        // before switching player turn
        // TODO: change from generic catch all to specific illegal move
        // TODO: or such messages. Probably use kevin's version of this
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

}
