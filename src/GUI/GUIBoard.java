package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        makeGUIOptions();
        boolean winner = false;
        while (!winner) {
            //play();
        }
    }

    public void makeBoard() {
        // Create the main frame for the chess board
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Create a JPanel that will hold the board
        boardPanel.setLayout(new GridLayout(8,8));
        boardPanel.setPreferredSize(new Dimension(800, 800));

        // Create the chess board squares and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new Square
                Square square = new Square(row, col);
                //Place piece if appropriate
                switch (row) {
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
                }
                //Assign listeners to square, square to button array and board container
                board[row][col] = square;
                board[row][col].addMouseListener(this);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
            }
        }
        // special color for border background
        Color borderColor = new Color(120, 85, 60);

        // Adding Border panels for the aplha and num guides
        // Needs a blank corner panel to offset for centered alphas
        JPanel cornerPanel = new JPanel(new GridLayout(1, 1));
        cornerPanel.setPreferredSize(new Dimension(25, 25));
        cornerPanel.setBackground(borderColor);
        // cornerPanel.add(new JLabel(""));             if we wanted anything in the corner panel

        // Create a JPanel that will hold row identifiers
        JPanel rowPanel = new JPanel(new GridLayout(8, 1));
        rowPanel.setPreferredSize(new Dimension(25, 800));
        rowPanel.setBackground(borderColor);

        // iterate through to label row
        for (int i = 8; i >= 1; i--) {
            JLabel row = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            row.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            row.setForeground(Color.white);
            rowPanel.add(row);
        }

        // Create a JPanel that will hold column identifiers
        JPanel colPanel = new JPanel(new GridLayout(1, 8));
        colPanel.setPreferredSize(new Dimension(800, 25));
        colPanel.setBackground(borderColor);

        // iterate through to label col
        for (char c = 'A'; c <= 'H'; c++) {
            JLabel col = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            col.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            col.setForeground(Color.white);
            colPanel.add(col);
        }

        // now a separate panel to hold the spacer corner and column identifiers
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(cornerPanel, BorderLayout.WEST);
        topPanel.add(colPanel, BorderLayout.CENTER);

        // add the panels to the board
        this.add(topPanel, BorderLayout.NORTH);     // space corner and column aplha
        this.add(rowPanel, BorderLayout.WEST);      // row numeric
        this.add(boardPanel, BorderLayout.CENTER);  // Add board panel to the frame
        this.pack();  // Pack the frame to fit the board
        this.setLocationRelativeTo(null);  // Center the frame
        this.setVisible(true);
    }

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
                System.out.println("Click origin square: " + (source.getRow() + 1) + " " + (source.getCol() + 1));
            }
            else { //If this is the second click of a move, overwrite the second square with the first

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
                System.out.println("Click destination square: " + (source.getRow() + 1) + " " + (source.getCol() + 1));
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
            System.out.println("Destination square: " + (newSquare.getRow() + 1) + " " + (newSquare.getCol() + 1));


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
}
