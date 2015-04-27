package minesweeper;

import java.util.Random;

/**
 * Class containing the code for generating new mine fields.
 * Mine fields are 2d arrays of integers containing the numbers -1 through 9.
 * Squares without a mine are 0, squares with a mine are -1, and squares near mines
 * are numbered according to how many mines they are touching.
 */
public class FieldGenerator {
    private static final int DEFAULT_X = 9;
    private static final int DEFAULT_Y = 9;
    private static final int DEFAULT_MINES = 10;
    public static final int FIELD_MINE = -1;
    public static final int FIELD_EMPTY = 0;
    public static final int MASK_HIDDEN = 0;
    public static final int MASK_REVEALED = 1;
    public static final int MASK_MARKED = -1;
    public static final int MASK_LOSE = -2;
    public static final int MASK_INCORRECT = -3;

    private int[][] field;
    private int[][] mask;
    private Random rand = new Random();
    private int minesRemaining;
    private int unrevealed;
    private int mines;
    private boolean firstClick = true;

    /**
     * Constructor
     */
    public FieldGenerator() {
        generateField(DEFAULT_X, DEFAULT_Y, DEFAULT_MINES);
    }

    public FieldGenerator(int x, int y, int mines) {
        generateField(x, y, mines);
    }

    /**
     * The field creator for this class, creates a field
     * of given size and places the given amount of mines
     * on the field.
     * @param x desired x size of the field
     * @param y desired y size of the field
     * @param mines desired number of mines for the field
     */
    private void generateField(int x, int y, int mines) {
        // Initialize the variables
        minesRemaining = mines;
        unrevealed = x * y;
        this.mines = mines;
        // First create the field and fill it with 0's (non-mines)
        field = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                field[i][j] = FIELD_EMPTY;
            }
        }
        // Also create the reveal field and initialize it with false
        mask = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                mask[i][j] = MASK_HIDDEN;
            }
        }
        // Now randomize the mines and set the values around the mines
        for (int i = 0; i < mines; i++) {
            // Randomize the location of the mine
            int xMine = rand.nextInt(x - 1);
            int yMine = rand.nextInt(y - 1);
            // If the mine doesn't already exist, create it
            if (field[yMine][xMine] != -1)
                placeMine(xMine, yMine);
            else // If the mine does already exist, make sure to decrement i
                i--;
        }
    }

    /**
     * Places a mine at the given coordinates
     * @param x the desired x coordinate of the mine
     * @param y the desired y coordinate of the mine
     */
    private void placeMine(int x, int y) {
        // Create the mine
        field[y][x] = FIELD_MINE;

        // Modify the numbers around the mine HOORAY IF STATEMENTS
        // Top left
        if (x == 0 && y == 0) {
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y+1][x+1] != -1) field[y+1][x+1] = field[y+1][x+1] + 1; // Below right
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
        }
        // Top right
        else if (x == 0 && y == field[y].length - 1) {
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
            if (field[y+1][x-1] != -1) field[y+1][x-1] = field[y+1][x-1] + 1; // Below left
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
        }
        // Bottom left
        else if (x == field.length - 1 && y == 0) {
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y-1][x+1] != -1) field[y-1][x+1] = field[y-1][x+1] + 1; // Above right
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
        }
        // Bottom right
        else if (x == field.length - 1 && y == field[y].length - 1) {
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
            if (field[y-1][x-1] != -1) field[y-1][x-1] = field[y-1][x-1] + 1; // Above left
        }
        // Far left column
        else if (x == 0) {
            if (field[y-1][x+1] != -1) field[y-1][x+1] = field[y-1][x+1] + 1; // Above right
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
            if (field[y+1][x+1] != -1) field[y+1][x+1] = field[y+1][x+1] + 1; // Below right
        }
        // Far right column
        else if (x == field[y].length - 1) {
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
            if (field[y+1][x-1] != -1) field[y+1][x-1] = field[y+1][x-1] + 1; // Below left
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
            if (field[y-1][x-1] != -1) field[y-1][x-1] = field[y-1][x-1] + 1; // Above left
        }
        // Top row
        else if (y == 0) {
            if (field[y+1][x+1] != -1) field[y+1][x+1] = field[y+1][x+1] + 1; // Below right
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
            if (field[y+1][x-1] != -1) field[y+1][x-1] = field[y+1][x-1] + 1; // Below left
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
        }
        // Bottom row
        else if (y == field.length - 1) {
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
            if (field[y-1][x+1] != -1) field[y-1][x+1] = field[y-1][x+1] + 1; // Above right
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
            if (field[y-1][x-1] != -1) field[y-1][x-1] = field[y-1][x-1] + 1; // Above left
        }
        // Everything else
        else {
            if (field[y+1][x+1] != -1) field[y+1][x+1] = field[y+1][x+1] + 1; // Below right
            if (field[y+1][x] != -1) field[y+1][x] = field[y+1][x] + 1; // Below middle
            if (field[y+1][x-1] != -1) field[y+1][x-1] = field[y+1][x-1] + 1; // Below left
            if (field[y][x+1] != -1) field[y][x+1] = field[y][x+1] + 1; // Right
            if (field[y][x-1] != -1) field[y][x-1] = field[y][x-1] + 1; // Left
            if (field[y-1][x+1] != -1) field[y-1][x+1] = field[y-1][x+1] + 1; // Above right
            if (field[y-1][x] != -1) field[y-1][x] = field[y-1][x] + 1; // Above middle
            if (field[y-1][x-1] != -1) field[y-1][x-1] = field[y-1][x-1] + 1; // Above left
        }
    }

    /**
     * The function for revealing a space
     * @param x the x-coordinate to be revealed
     * @param y the y-coordinate to be revealed
     * @return whether or not the game has been lost
     */
    public boolean revealSpace(int x, int y) {
        if (x > mask[0].length || y > mask.length) {
            return false;
        }
        else if (mask[y][x] == MASK_MARKED) {
            return false;
        }
        else if (field[y][x] == FIELD_MINE) {
            if (firstClick) {
                generateField(field[0].length, field.length, mines);
                revealSpace(x, y);
            } else {
                mask[y][x] = MASK_LOSE;
                return true;
            }
        }
        else if (mask[y][x] == MASK_REVEALED) {
            return false;
        } else {
            firstClick = false;
            revealHelper(x, y);
            return false;
        }
        return false;
    }

    /**
     * Recursive function for revealing all spaces when
     * user reveals an empty space
     * @param x the x coordinate to be revealed
     * @param y the y coordinate to be revealed
     */
    private void revealHelper(int x, int y) {
        if (mask[y][x] == MASK_REVEALED) return;
        if (mask[y][x] == MASK_MARKED) return;
        else if (field[y][x] == 0) {
            mask[y][x] = MASK_REVEALED;
            unrevealed--;
            // Top left
            if (x == 0 && y == 0) {
                revealHelper(x+1,y); // Right
                revealHelper(x+1,y+1); // Below right
                revealHelper(x,y+1); // Below
            }
            // Top right
            else if (x == field[0].length - 1 && y == 0) {
                revealHelper(x-1,y); // Left
                revealHelper(x-1,y+1); // Below left
                revealHelper(x,y+1); // Below
            }
            // Bottom left
            else if (x == 0 && y == field.length - 1) {
                revealHelper(x+1,y); // Right
                revealHelper(x+1,y-1); // Above right
                revealHelper(x,y-1); // Above middle
            }
            // Bottom right
            else if (x == field[0].length - 1 && y == field.length - 1) {
                revealHelper(x-1,y); // Left
                revealHelper(x,y-1); // Above middle
                revealHelper(x-1,y-1); // Above left
            }
            // Far left column
            else if (x == 0) {
                revealHelper(x+1,y-1); // Above right
                revealHelper(x,y-1); // Above middle
                revealHelper(x+1,y); // Right
                revealHelper(x,y+1); // Below middle
                revealHelper(x+1,y+1); // Below right
            }
            // Far right column
            else if (x == field[0].length - 1) {
                revealHelper(x,y+1); // Below middle
                revealHelper(x-1,y+1); // Below left
                revealHelper(x-1,y); // Left
                revealHelper(x,y-1); // Above middle
                revealHelper(x-1,y-1); // Above left
            }
            // Top row
            else if (y == 0) {
                revealHelper(x+1,y+1); // Below right
                revealHelper(x,y+1); // Below middle
                revealHelper(x-1,y+1); // Below left
                revealHelper(x+1,y); // Right
                revealHelper(x-1,y); // Left
            }
            // Bottom row
            else if (y == field.length - 1) {
                revealHelper(x+1,y); // Right
                revealHelper(x-1,y); // Left
                revealHelper(x+1,y-1); // Above right
                revealHelper(x,y-1); // Above middle
                revealHelper(x-1,y-1); // Above left
            }
            // Everything else
            else {
                revealHelper(x+1,y+1); // Below right
                revealHelper(x,y+1); // Below middle
                revealHelper(x-1,y+1); // Below left
                revealHelper(x+1,y); // Right
                revealHelper(x-1,y); // Left
                revealHelper(x+1,y-1); // Above right
                revealHelper(x,y-1); // Above middle
                revealHelper(x-1,y-1); // Above left
            }
        }
        else if (field[y][x] == FIELD_MINE) return;
        else {
            mask[y][x] = MASK_REVEALED;
            unrevealed--;
        }
    }

    /**
     * Mark the space at x, y as a mine
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void markMine(int x, int y) {
        if (x > mask[0].length || y > mask.length) {
            return;
        }
        else if (mask[y][x] == MASK_REVEALED) {
            return;
        }
        else if (mask[y][x] == MASK_MARKED) {
            return;
        }
        minesRemaining--;
        mask[y][x] = MASK_MARKED;
    }

    /**
     * Unmark the space at x, y
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void unMark (int x, int y) {
        if (x > mask[0].length || y > mask.length) {
            return;
        }
        if (mask[y][x] != MASK_MARKED) {
            return;
        }
        minesRemaining++;
        mask[y][x] = MASK_HIDDEN;
    }

    /**
     * Gets the field
     * @return this field
     */
    public int[][] getField() {
        return field;
    }

    /**
     * Gets the reveal array
     * @return the reveal array
     */
    public int[][] getMask() {
        return mask;
    }

    /**
     * Gets the amount of mines remaining
     * @return the amount of unmarked mines
     */
    public int getMinesRemaining() {
        return minesRemaining;
    }

    public boolean getGameWin() {
        return (unrevealed <= mines);
    }

    /**
     * Reveals all the mines in the game and sets the
     * incorrect flags
     */
    public void loseGame() {
        for(int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == FIELD_MINE && mask[i][j] != MASK_LOSE && mask[i][j] != MASK_MARKED) {
                    mask[i][j] = MASK_REVEALED;
                } else if (mask[i][j] == MASK_MARKED && field[i][j] != FIELD_MINE) {
                    mask[i][j] = MASK_INCORRECT;
                }
            }
        }
    }
    public boolean getFirstClick() {
        return firstClick;
    }
}
