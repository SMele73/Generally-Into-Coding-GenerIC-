package utility;

/**
 * Because magic numbers aren't as cool as normal magic
 */
public final class Constants {
    //This wastes 17/81 of the array but the game is not exactly big enough to suffer from lack of optimization
    //and it will be much easier to work with
    public final static int NUM_COLS = 9;
    public final static int NUM_ROWS = 9;

    public enum PromotionChoice{QUEEN, ROOK, BISHOP, KNIGHT}

    private Constants() {
        //None
    }
}
