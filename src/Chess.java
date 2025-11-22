import GUI.GUIBoard;
import board.Board;
import game.Game;

import javax.swing.*;

/**
 * Main class of the project. Calls the methods of the Game class in order.
 */
public class Chess {
    public static void main(String[] args) {
        /*Game game = new Game();
        game.start();
        game.play();
        game.end();*/
        SwingUtilities.invokeLater(GUIBoard::new);
    }
}