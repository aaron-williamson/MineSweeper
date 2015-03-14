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
    private int[][] field;
    private boolean[][] reveal;
    private Random rand = new Random();

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
        // First create the field and fill it with 0's (non-mines)
        field = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                field[i][j] = 0;
            }
        }
        // Also create the reveal field and initialize it with false
        reveal = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                reveal[i][j] = false;
            }
        }
        // Now randomize the mines and set the values around the mines
        for (int i = 0; i < mines; i++) {
            // Randomize the location of the mine
            int xMine = rand.nextInt(x - 1);
            int yMine = rand.nextInt(y - 1);
            // If the mine doesn't already exist, create it
            if (field[xMine][yMine] != -1)
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
        field[x][y] = -1;

        // Modify the numbers around the mine HOORAY IF STATEMENTS
        // Top left
        if (x == 0 && y == 0) {
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x+1][y+1] != -1) field[x+1][y+1] = field[x+1][y+1] + 1; // Below right
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
        }
        // Top right
        else if (x == 0 && y == field[x].length - 1) {
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
            if (field[x+1][y-1] != -1) field[x+1][y-1] = field[x+1][y-1] + 1; // Below left
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
        }
        // Bottom left
        else if (x == field.length - 1 && y == 0) {
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x-1][y+1] != -1) field[x-1][y+1] = field[x-1][y+1] + 1; // Above right
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
        }
        // Bottom right
        else if (x == field.length - 1 && y == field[x].length - 1) {
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
            if (field[x-1][y-1] != -1) field[x-1][y-1] = field[x-1][y-1] + 1; // Above left
        }
        // Far left column
        else if (y == 0) {
            if (field[x-1][y+1] != -1) field[x-1][y+1] = field[x-1][y+1] + 1; // Above right
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
            if (field[x+1][y+1] != -1) field[x+1][y+1] = field[x+1][y+1] + 1; // Below right
        }
        // Far right column
        else if (y == field[x].length - 1) {
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
            if (field[x+1][y-1] != -1) field[x+1][y-1] = field[x+1][y-1] + 1; // Below left
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
            if (field[x-1][y-1] != -1) field[x-1][y-1] = field[x-1][y-1] + 1; // Above left
        }
        // Top row
        else if (x == 0) {
            if (field[x+1][y+1] != -1) field[x+1][y+1] = field[x+1][y+1] + 1; // Below right
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
            if (field[x+1][y-1] != -1) field[x+1][y-1] = field[x+1][y-1] + 1; // Below left
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
        }
        // Bottom row
        else if (x == field.length - 1) {
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
            if (field[x-1][y+1] != -1) field[x-1][y+1] = field[x-1][y+1] + 1; // Above right
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
            if (field[x-1][y-1] != -1) field[x-1][y-1] = field[x-1][y-1] + 1; // Above left
        }
        // Everything else
        else {
            if (field[x+1][y+1] != -1) field[x+1][y+1] = field[x+1][y+1] + 1; // Below right
            if (field[x+1][y] != -1) field[x+1][y] = field[x+1][y] + 1; // Below middle
            if (field[x+1][y-1] != -1) field[x+1][y-1] = field[x+1][y-1] + 1; // Below left
            if (field[x][y+1] != -1) field[x][y+1] = field[x][y+1] + 1; // Right
            if (field[x][y-1] != -1) field[x][y-1] = field[x][y-1] + 1; // Left
            if (field[x-1][y+1] != -1) field[x-1][y+1] = field[x-1][y+1] + 1; // Above right
            if (field[x-1][y] != -1) field[x-1][y] = field[x-1][y] + 1; // Above middle
            if (field[x-1][y-1] != -1) field[x-1][y-1] = field[x-1][y-1] + 1; // Above left
        }
    }

    public boolean revealSpace(int x, int y) {
        if (field[x][y] == -1) return true;
        reveal[x][y] = true;
        return false;
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
    public boolean[][] getReveal() {
        return reveal;
    }
}
