package GUI;

import pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    //Private attributes
    private int col;
    private int row;
    private String piece;
    //Default constructor
    public Square(int row, int col) {
        //Initialize class attributes
        this.row = row;
        this.col = col;
        this.piece = "";
        //Format button
        this.setFont(new Font("Serif", Font.BOLD, 32)); // Make the piece characters bigger
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        //Give button a border
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //Define square colors
        Color lightColor = new Color(240, 217, 181);
        Color darkColor = new Color(181, 136, 99);
        //Assign square colors
        if ((row + col) % 2 == 0) {
            this.setBackground(lightColor);
        } else {
            this.setBackground(darkColor);
        }
    }

    //Getters
    public int getCol() {return col;}
    public int getRow() {return row;}

    //Setters
    public void setColumn(int c) {col = c;}
    public void setRow(int r) {row = r;}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {return true;}
        if (obj == null || this.getClass() != obj.getClass()) {return false;}
        Square square = (Square) obj;
        return this.col == square.getCol() && this.row == square.getRow();
    }
}
