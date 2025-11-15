package GUI;

//Imports within package
import pieces.*;
import utility.Constants;

//Java libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This is the main class for GUI implementation. It could probably stand to be split up a little more.
 * Currently, it makes and populates the board (a 2D array of buttons), has the minimal game flow logic needed for this step,
 * makes the menu bar, and holds the logic for moving pieces
 */
public class GUIBoard extends JFrame implements MouseListener, ActionListener {

    /**
     * board is the array of Squares that make up the board
     * All other private attributes except for the boardPanel (which holds the board) are used for piece movement logic
     */
    //Create squares to be used with mouse listeners
    private Square clickFrom;
    private Square clickTo;
    private Square dragFrom;
    private Square dragTo;
    //Flags to prevent a drag move and click move both from occurring
    private boolean destinationPick = false;
    private boolean leftSquare = false;


    private JPanel boardPanel = new JPanel();  //Initialized in makeBoard, modified by boardOptions
    private final Square[][] board = new Square[Constants.NUM_ROWS][Constants.NUM_COLS]; //Array of buttons
    private Piece piece;

    //Default constructor
    public GUIBoard() {
        //This is currently empty, all functions in it having been farmed out to makeBoard();
    }

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

    //Getters
    public void main(String[] args) {
        Square current = new Square(8, 8);
        makeBoard();
        boolean winner = false;
        while (!winner) {
            //play();
        }
    }

    /**
     * makeBoard sets up the Square array, places the pieces (currently the unicode chess characters in each button's text field),
     * and adds borders to the board. It also contains the initial settings and code for the class as a whole
     */
    public void makeBoard() {
        // Create the chess board
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // make MenuBar
        makeMenuBar();

        // Create a JPanel that will hold the board
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setPreferredSize(new Dimension(800, 800));

        // Create the chess board squares and pieces
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                // Create a new Square
                Square square = new Square(row, col);
                //Place piece if appropriate
                switch (row) {
                    case 1:
                        //square.setForeground(Color.BLACK); Line superceded by the unicode specifying color
                        if(col == 1 || col == 8) {square.setText("\u265C");} //Black rooks
                        if(col == 2 || col == 7) {square.setText("\u265E");} //Black knights
                        if(col == 3 || col == 6) {square.setText("\u265D");} //Black bishops
                        if(col == 4) {square.setText("\u265B");}             //Black queen
                        if(col == 5) {square.setText("\u265A");}             //Black king
                        break;
                    case 2:
                        //square.setForeground(Color.BLACK); Line superceded by the unicode specifying color
                        square.setText("\u265F");                            //Black pawns
                        break;
                    case 7:
                        //square.setForeground(Color.BLUE); Line superceded by the unicode specifying color
                        square.setText("\u2659");                            //White pawns
                        break;
                    case 8:
                        //square.setForeground(Color.BLUE); //Line superceded by the unicode specifying color
                        if(col == 1 || col == 8) {square.setText("\u2656");} //White rooks
                        if(col == 2 || col == 7) {square.setText("\u2658");} //White knights
                        if(col == 3 || col == 6) {square.setText("\u2657");} //White bishops
                        if(col == 4) {square.setText("\u2655");}              //White queen
                        if(col == 5) {square.setText("\u2654");}              //White king
                        break;
                }
                //Assign listeners to square, square to button array and board container
                board[row][col] = square;
                board[row][col].addMouseListener(this);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
            }
        }

        //Create row panels
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
     * @see GUIOptions This method adds a way to bring up the GUIOptions window to the frame
     */
    public void makeGUIOptions(){
        GUIOptions optionWindow = new GUIOptions(this);
    }

    /**
     * Listener for the buttons that make up the board. Used lieu of the mouseEvent mouseClicked listener due to
     * consistency issues.
     * @param e the event to be processed
     */
    /*Todo: Currently, these listener methods are for the main board only. Additional features may need to either
        override these events again in their method or be split into separate classes, depending on what kind of conflicts arise*/
    @Override
    public void actionPerformed(ActionEvent e) {
        Square source = (Square) e.getSource();
        //If the cursor changed squares during the click, do nothing
        if (!leftSquare) {
            //If this is the first click of a move, store clicked square to clickFrom
            if (!destinationPick) {
                clickFrom = source;
                destinationPick = true;
                System.out.println("Click origin square: " + (source.getRow() + 1) + " " + (source.getCol() + 1));
            }
            else { //If this is the second click of a move, store to clickTo
                clickTo = source;
                piece = makePiece(clickFrom);
                //Perform initial validation
                if(validMove(clickFrom, clickTo)) {
                    source.setText(clickFrom.getText());
                    source.setForeground(clickFrom.getForeground());
                    clickFrom.setText("");
                    destinationPick = false;
                    System.out.println("Click destination square: " + (source.getRow() + 1) + " " + (source.getCol() + 1));
                }
                else{
                    destinationPick = false;
                }
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
        dragFrom = (Square) e.getSource();
        System.out.println("Origin square: " + (dragFrom.getRow() + 1) + " " + (dragFrom.getCol() + 1));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //If the mouse has left the original square since the button was clicked, treat it as a drag.
        if (leftSquare) {
            piece = makePiece(dragFrom);
            if(validMove(dragFrom, dragTo)) {
                dragTo.setText(dragFrom.getText());
                dragTo.setForeground(dragFrom.getForeground());
                dragFrom.setText("");
                System.out.println("Destination square: " + (dragTo.getRow() + 1) + " " + (dragTo.getCol() + 1));
            }
        }
    }

    /**
     * Each time the mouse enters a new square, record that square in case it gets used for a move
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        dragTo = (Square) e.getSource();
    }

    /**
     * This method sets a flag that determines whether a click is handled by the ActionPerformed listener (click-move)
     * or the mousePressed and mouseReleased listeners (drag-move)
     * @param e Doesn't mean anything in this context
     */
    @Override
    public void mouseExited(MouseEvent e) {
        leftSquare = true;
    }

    // Dialog box pop up for king capture
    private void showWinnerDialog(String winnerColor, String loserColor) {
        // Pass winning color + message to display
        String message = winnerColor + " wins! The " + loserColor + " king has be captured.";
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
            new GUIBoard().makeBoard();  // restart a new game window
        }
        else {
            System.exit(0);
        }
    }

    /**
     * This method handles the menu bar, except for the link to GUIOptions
     */
    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Game Menu    -- could make this its own method...
        JMenu gameMenu = new JMenu("Game");                         // Game tab in menu
        JMenuItem newGame = new JMenuItem("New Game");            // New Game item
        newGame.addActionListener(e -> {                    // action for newGame
            this.dispose();
            new GUIBoard().makeBoard();
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

    /**
     * Helper method to construct labels for the board's files
     * @return The panel holding the labels
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
     * Helper method to construct labels for the board's ranks
     * @return The panel holding the labels
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

    //There is definitely room to tighten this up a bit, currently the piece text is compared
    //both here and in its respective piece subclass
    //Generate a piece for the relevant square to use in validation
    public Piece makePiece(Square square){
        Piece piece;
        switch (square.getText()) {
            case "\u2659", "\u265F" -> piece = new Pawn(square);
            case "\u2658", "\u265E" -> piece = new Knight(square);
            case "\u2657", "\u265D" -> piece = new Bishop(square);
            case "\u2656", "\u265C" -> piece = new Rook(square);
            case "\u2655", "\u265B" -> piece = new Queen(square);
            case "\u2654", "\u265A" -> piece = new King(square);
            default -> {return null;}
        }
        return piece;
    }
    public boolean validMove(Square from, Square to) {
        try {
            if (piece == null) { //Does start square have a piece?
                throw new IllegalArgumentException("No piece in that square!");
            }
            //TODO: Re-implement a check to make sure the current player is moving their own color piece
            /*if (from.getPiece().getColor() != color) { //Is that piece the current player's color?
                throw new IllegalArgumentException("That piece is not your color.");
            }*/
            if (!piece.possibleMoves(board).contains(to)) { //Is this a legal move for that piece?
                throw new IllegalArgumentException("That move is not legal!");
            }
            return true;

            //Color of a prospective capture is checked at the piece level and doesn't need to be rechecked here
        } catch (IllegalArgumentException e) {
            IllegalDialog illegal = new IllegalDialog(this, e.getMessage());
            return false;
        }
    }
}