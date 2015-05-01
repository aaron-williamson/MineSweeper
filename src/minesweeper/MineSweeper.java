package minesweeper;

import javax.swing.*;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private static final int DEFAULT_X = 10;
    private static final int DEFAULT_Y = 10;
    private static final int DEFAULT_MINES = 20;

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

    public void startGame() {
        startGame(DEFAULT_X, DEFAULT_Y, DEFAULT_MINES);
    }

    public void newGame() {
        gui.close();
        startGame();
    }
}
