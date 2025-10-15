package players;

import java.util.Scanner;
import utility.Constants;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 * Player handles the input of moves and confirms they are in proper chess notation
 * before passing them on to Game for further use.
 */
public class Player {
    //Private attributes
    private boolean color;
    //private ArrayList<Piece> piecesLeft;

    //Default constructor
    public Player(boolean color){
        this.color = color;
    }
    //Getter
    public boolean getColor(){
        return this.color;
    }
    //Method

    /**
     * Primary Player function. Asks players for their move, confirms that it's valid chess
     * notation, then sends it to Game.
     * @return The string holding the validated move
     * @throws IllegalArgumentException for moves that are off of the board
     */
    public String makeMove() {
        Scanner scan = new Scanner(System.in);
        boolean valid = false;
        String move = "";
        while (!valid) {
            System.out.print("Enter your move: ");
            move = scan.nextLine(); //Get move from player
            //Check that move is valid chess notation
            //This is where a working knowledge of RegEx would probably save some time...
            try {
                //Validate letters
                if (!isLetter(move.charAt(0)) || !isLetter(move.charAt(3))) {throw new IllegalArgumentException("Square declarations must start with a letter");}
                else {
                    char let1 = Character.toUpperCase(move.charAt(0));
                    char let2 = Character.toUpperCase(move.charAt(3));
                    //H is magic letter in this case, setting up a comparison to NUM_COLS is an optional enhancement
                    if (let1 < 'A' || let1 > 'H' || let2 < 'A' || let2 > 'H') {
                        throw new IllegalArgumentException("File is not on the board");
                    }
                }
                //Validate numbers
                //char num1 = move.charAt(1);
                //char num2 = move.charAt(4);
                if (!isDigit(move.charAt(1)) || !isDigit(move.charAt(4))) {throw new IllegalArgumentException("Square declarations must end with a digit");}
                int num1 = Character.getNumericValue(move.charAt(1));
                int num2 = Character.getNumericValue(move.charAt(4));
                if (num1 < 1 || num1 > Constants.NUM_ROWS - 1 || num2 < 1 || num2 > Constants.NUM_ROWS - 1) {
                    throw new IllegalArgumentException("Rank is not on the board");
                }
                //Set move to valid
                valid = true;
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            //These are to be handled by a more generic message.
            //Validate length and whitespace
            if(move.length() != 5) {valid = false;}
            if(move.charAt(2) != ' ') {valid = false;}
            if (!valid) {System.out.println("Invalid move. Please use chess notation of letter-number letter-number, IE a3 b3. Case insensitive.");}
        }
        return move;
    }
}
