package board;

import piece.Piece;

public class Square {
    //Private attributes
    private int file;
    private Character fileLetter;
    private int rank;
    private Piece piece;

    //Default constructor
    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        switch(file) {
            case 1:
                fileLetter = 'A';
                break;
            case 2:
                fileLetter = 'B';
                break;
            case 3:
                fileLetter = 'C';
                break;
            case 4:
                fileLetter = 'D';
                break;
            case 5:
                fileLetter = 'E';
                break;
            case 6:
                fileLetter = 'F';
                break;
            case 7:
                fileLetter = 'G';
                break;
            case 8:
                fileLetter = 'H';
                break;
        }
    }

    //Getters
    public int getFile() {
        return file;
    }
    public int getRank() {
        return rank;
    }

    //Setters
    public void setFile(int file) {
        this.file = file;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
}
