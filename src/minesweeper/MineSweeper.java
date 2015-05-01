package minesweeper;

import javax.swing.*;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private static final int DEFAULT_X = 9;
    private static final int DEFAULT_Y = 9;
    private static final int DEFAULT_MINES = 10;

    private FieldGenerator field;
    private GUIDisplay gui;

    /**
     * Starts the game
     */
    public void startGame(int x, int y, int mines) {
        field = new FieldGenerator(x, y, mines);
        try {
            gui = new GUIDisplay(field, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Output the revealed field for debugging
        ConsoleDisplay.printFieldRevealed(field);
    }

    /**
     * Start the game with default mines and grid size (beginner)
     */
    public void startGame() {
        startGame(DEFAULT_X, DEFAULT_Y, DEFAULT_MINES);
    }

    public void newGame(int x, int y, int mines) {
        field = new FieldGenerator(x, y, mines);
        gui.newGame(field);
    }

    /**
     * Create a new game with previous size/mine settings
     */
    public void newGame() {
        newGame(field.getField()[0].length, field.getField().length, field.getMines());
    }
}
