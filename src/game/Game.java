package game;

import board.Board;
import players.*;
import board.*;

public class Game {

    //Attributes
    private Board board;
    public Player white = new Player(true);
    public Player black = new Player(false);
    public boolean currentPlayer = false;

    //Default constructor
    public Game() {
    }

    public void start() {
        board = new Board();
        board.displayBoard();
    }

    //public void end() {}

    public void play() {
        requestMove();
    }

    public void requestMove() {}
}