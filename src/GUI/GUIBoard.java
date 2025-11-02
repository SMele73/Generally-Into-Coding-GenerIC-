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
    //appPanel is the main panel of the frame, each method adding new GUI elements should end by appending itself to appPanel
    private JPanel appPanel = new JPanel(new BorderLayout());

    public void main(String[] args) {
        Square current = new Square(8,8);
        makeBoard();
        boolean winner = false;
        while(!winner) {
            //play();
        }
    }

    private Square[][] board = new Square[8][8];

    public void makeBoard() {
        // Create the main frame for the chess board
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Create a JPanel that will hold the board
        JPanel boardPanel = new JPanel(new GridLayout(8,8));
        boardPanel.setPreferredSize(new Dimension(800, 800));

        // Create the chess board squares and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new Square
                Square square =  new Square(row, col);
                //Place piece if appropriate
                switch(row) {
                    case 0:
                        square.setForeground(Color.BLACK);
                        if(col == 0 || col == 7) {square.setText("Rook");}
                        if(col == 1 || col == 6) {square.setText("Knight");}
                        if(col == 2 || col == 5) {square.setText("Bishop");}
                        if(col == 3) {square.setText("Queen");}
                        if(col == 4) {square.setText("King");}
                        break;
                    case 1:
                        square.setForeground(Color.BLACK);
                        square.setText("Pawn");
                        break;
                    case 6:
                        square.setForeground(Color.BLUE);
                        square.setText("Pawn");
                        break;
                    case 7:
                        square.setForeground(Color.BLUE);
                        if(col == 0 || col == 7) {square.setText("Rook");}
                        if(col == 1 || col == 6) {square.setText("Knight");}
                        if(col == 2 || col == 5) {square.setText("Bishop");}
                        if(col == 3) {square.setText("Queen");}
                        if(col == 4) {square.setText("King");}
                        break;
                }
                //Assign listeners to square, square to button array and board container
                board[row][col] = square;
                board[row][col].addMouseListener(this);
                board[row][col].addActionListener(this);
                boardPanel.add(board[row][col]);
                appPanel.add(boardPanel, BorderLayout.CENTER);
            }
        }


        this.add(boardPanel);  // Add board panel to the frame
        this.pack();  // Pack the frame to fit the board
        this.setLocationRelativeTo(null);  // Center the frame
        this.setVisible(true);
    }

    /*Todo: Currently, these listener methods are for the main board only. Additional features may need to either
    override these events again in their method or be split into separate classes, depending on what kind of conflicts arise*/
    @Override
    public void actionPerformed(ActionEvent e){
        Square source = (Square) e.getSource();
        //If the cursor changed squares during the click, do nothing
        if (!leftSquare) {
            //If this is the first click of a move, assign clicked square to square
            System.out.println("LeftSquare was false");
            if(!destinationPick) {
                squareClick = source;
                destinationPick = true;
                System.out.println("Click origin square: " + (source.getRow() + 1) + " " + (source.getCol() + 1));
            }
            else { //If this is the second click of a move, overwrite the second square with the first
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
            newSquare.setText(squareDrag.getText());
            newSquare.setForeground(squareDrag.getForeground());
            squareDrag.setText("");
            System.out.println("Destination square: " + (newSquare.getRow() + 1) + " " + (newSquare.getCol() + 1));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        newSquare =  (Square) e.getSource();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        leftSquare = true;
    }
}
