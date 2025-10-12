import board.Board;
import game.Game;

public class Chess {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        game.play();
        game.end();
    }
}