package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IllegalDialog extends JDialog {

    //Default constructor
    public IllegalDialog(GUIBoard game, String errorMessage) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);    //Close the dialog but not the entire game
        this.setLayout(new BorderLayout());
        this.setTitle("Illegal move!");
        this.setSize(200, 100);

        //Display details of illegal move
        JLabel message = new JLabel(errorMessage,  JLabel.CENTER);
        this.add(message, BorderLayout.CENTER);

        //Ok button to close dialog
        //Inner class for button
        class oopsListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        }
        JButton oops = new JButton("Oops");
        oops.addActionListener(new oopsListener());
        this.add(oops, BorderLayout.SOUTH);
        //this.pack();                        //Size the dialog to fit the options
        this.setLocationRelativeTo(game);   //Center the dialog over the chess game
        this.setVisible(true);
    }

    /**
     * Closing the dialog is given its own method as getting dispose() to work properly in-line was proving troublesome.
     */
    public void closeDialog(){this.dispose();}
}

/*
public boolean validMove(Square from, Square to) {
        try {
            if (piece == null) { //Does start square have a piece?
                throw new IllegalArgumentException("No piece in that square!");
            }
            //TODO: Re-implement a check to make sure the current player is moving their own color piece
            if (from.getPiece().getColor() != color) { //Is that piece the current player's color?
                throw new IllegalArgumentException("That piece is not your color.");
            }
            if (!piece.possibleMoves(board).contains(to)) { //Is this a legal move for that piece?
        throw new IllegalArgumentException("That move is not legal!");
            }
                    return true;

                    //Color of a prospective capture is checked at the piece level and doesn't need to be rechecked here
                    } catch (IllegalArgumentException e) {
IllegalDialog illegal = new IllegalDialog(this, e.getMessage());
            return false;
                    }
 */