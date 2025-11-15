package utility;

public final class Constants {
    //This wastes 17/81 of the array but the game is not exactly big enough to suffer from lack of optimization
    //and it will be much easier to work with
    public final static int NUM_COLS = 9;
    public final static int NUM_ROWS = 9;
    public final static int EMPTY = -1;
    public final static int ENEMY_COLOR = 0;
    public final static int FRIENDLY_COLOR = 1;

    private Constants() {
        //None
    }
}
