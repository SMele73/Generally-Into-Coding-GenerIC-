package GUI;

import javax.swing.*;
import java.awt.*;

public class GUIBoard extends JFrame {

    public static void main(String[] args) {
        JFrame game = makeBoard();
        boolean winner = false;
        while(!winner) {
            play(game);
        }
    }

    public static JFrame makeBoard() {
        // Create the main frame for the chess board
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a JPanel that will hold the board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(800, 800));

        // Create the chess board squares and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new JLabel with a piece or an empty string
                Square square = new Square(row, col);
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
                        if(col == 3) {square.setText("King");}
                        if(col == 4) {square.setText("Queen");}
                        break;
                }

                boardPanel.add(square);
            }
        }


        frame.add(boardPanel);  // Add board panel to the frame
        frame.pack();  // Pack the frame to fit the board
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setVisible(true);

        return frame;
    }

    public static JFrame play(JFrame game) {

        int row = 0;
        int col = 0;

        return game;
    }
}
