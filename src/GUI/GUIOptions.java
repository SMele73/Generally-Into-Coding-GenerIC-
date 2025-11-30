package GUI;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a dialog containing customization options for the board.
 * Board size, piece color, and square colors can all be changed. Piece and square colors are split into light and dark
 */
public class GUIOptions extends JDialog{

    /**
     * Most of the attributes are self-explanatory
     * sizeCheck is assigned from the height of the game board calling this class and is set as a class-wide variable
     * so the board doesn't need to be passed around as much. It's sole use is for determining which radio button should
     * be pre-selected in the board sizing panel.
     */
    //Private attributes
    private Color lightSquaresColor;
    private Color darkSquaresColor;
    private Color lightPiecesColor;
    private Color darkPiecesColor;
    //Arrays of colors available
    Color[] lightColors = {new Color(240,217,181), Color.white, Color.yellow, Color.pink, Color.lightGray, Color.cyan};
    Color[] darkColors = {new Color(181,136,99), Color.black, Color.darkGray, Color.gray, Color.blue, Color.orange, Color.magenta, Color.red};
    //private enum boardSize {Small, Medium, Large, NULL}
    private Integer boardSize = null;
    private int sizeCheck;

    //Default constructor
    public GUIOptions(GUIBoard game){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);    //Close the dialog but not the entire game
        this.setLayout(new BorderLayout());
        this.setTitle("Options");
        sizeCheck = game.getHeight();
        this.makeMiddle();
        this.makeBottom(game);                  //Add bottom buttons to
        this.pack();                        //Size the dialog to fit the options
        this.setLocationRelativeTo(game);   //Center the dialog over the chess game
        this.setVisible(true);
    }

    /**
     * Make the options and required panels to contain them
     */
    public void makeMiddle(){
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));

        //Panel to hold the coloring options
        JPanel upperMiddle = new JPanel(new GridLayout(3, 3));

        //Panel to hold the sizing options
        JPanel lowerMiddle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lowerMiddle.setBorder(new EtchedBorder());

        //Create color choice elements
        upperMiddle.add(new JLabel("Colors"));
        upperMiddle.add(new JLabel("Light"));
        upperMiddle.add(new JLabel("Dark"));
        upperMiddle.add(new JLabel("Squares"));

        JComboBox lightSquares = new JComboBox(lightColors);
        lightSquares.setSelectedIndex(0);
        class lSquareListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                lightSquaresColor = (Color)cb.getSelectedItem();}}
        lightSquares.addActionListener(new lSquareListener());
        upperMiddle.add(lightSquares);

        JComboBox darkSquares = new JComboBox(darkColors);
        darkSquares.setSelectedIndex(0);
        class dSquareListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                darkSquaresColor = (Color)cb.getSelectedItem();}}
        darkSquares.addActionListener(new dSquareListener());
        upperMiddle.add(darkSquares);

        upperMiddle.add(new JLabel("Pieces"));

        JComboBox lightPieces = new JComboBox(lightColors);
        lightPieces.setSelectedIndex(1);
        class lPieceListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                lightPiecesColor = (Color)cb.getSelectedItem();}}
        lightPieces.addActionListener(new lPieceListener());
        upperMiddle.add(lightPieces);

        JComboBox darkPieces = new JComboBox(darkColors);
        darkPieces.setSelectedIndex(1);
        class dPieceListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                darkPiecesColor = (Color)cb.getSelectedItem();}}
        darkPieces.addActionListener(new dPieceListener());
        upperMiddle.add(darkPieces);

        //Create board size option element
        lowerMiddle.add(new JLabel("Board size:"));

        JRadioButton small = new JRadioButton("Small");
        class smallListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                boardSize = 450;}}
        small.addActionListener(new smallListener());

        JRadioButton medium = new JRadioButton("Medium");
        class mediumListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                boardSize = 625;}}
        medium.addActionListener(new mediumListener());

        JRadioButton large = new JRadioButton("Large");
        class largeListener implements ActionListener{
            @Override public void actionPerformed(ActionEvent e) {
                boardSize = 800;}}
        large.addActionListener(new largeListener());

        ButtonGroup group = new ButtonGroup();
        group.add(small);
        group.add(medium);
        group.add(large);

        //Determine which button should start selected
        if (sizeCheck >= 800){large.setSelected(true);}
        else if (sizeCheck >= 600){medium.setSelected(true);}
        else{small.setSelected(true);}

        //Add radio buttons to panel
        lowerMiddle.add(small);
        lowerMiddle.add(medium);
        lowerMiddle.add(large);

        //Add nested panels to outer panel and outer panel to frame
        middlePanel.add(upperMiddle);
        middlePanel.add(lowerMiddle);
        this.add(middlePanel,BorderLayout.CENTER);
    }

    /**
     * Add standard ok/cancel/apply buttons to the bottom of the dialog
     * @param g is the board that will be modified with the chosen option(s).
     *          It's not used in this method but passed on to another
     */
    public void makeBottom(GUIBoard g){
        JPanel bottom = new JPanel();   //Make panel

        //Construct & add okButton
        //Inner class declaration for Ok button
        class okListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                applyChoices(g);
                closeDialog();}}

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new okListener());
        bottom.add(okButton);

        //Construct & add cancelButton
        //Inner class declaration for Cancel button
        class cancelListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                closeDialog();}}

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new cancelListener());
        bottom.add(cancelButton);

        //Construct & add applyButton
        //Inner class declaration for Apply button
        class applyListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                applyChoices(g);}}

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new applyListener());
        bottom.add(applyButton);

        //Add bottom buttons to main dialog
        this.add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Applies any changed options to the game
     * @param g is the game being modified
     */
    //Modify the chess game GUI elements according to user choices
    public void applyChoices(GUIBoard g){
        if (lightSquaresColor != null){g.setLightSquareColor(lightSquaresColor);}
        if (darkSquaresColor != null){g.setDarkSquareColor(darkSquaresColor);}
        if (lightPiecesColor != null){g.setLightPieceColor(lightPiecesColor);}
        if (darkPiecesColor != null){g.setDarkPieceColor(darkPiecesColor);}
        if (boardSize != null){g.setBoardSize(boardSize);}
        g.repaint();
    }

    /**
     * Method to close the dialog as doing so without wrapping it in a method causes unexpected behavior
     */
    public void closeDialog(){this.dispose();}
}