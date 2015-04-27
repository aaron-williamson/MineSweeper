package minesweeper;

import javax.swing.*;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private FieldGenerator field;
    private GUIDisplay gui;

    /**
     * Starts the game
     */
    public void startGame() {
        field = new FieldGenerator();
        try {
            gui = new GUIDisplay(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Output the revealed field for debugging
        //ConsoleDisplay.printFieldRevealed(field);
    }

    /**
     * The game over message
     */
    public static void gameLose() {
        JOptionPane.showMessageDialog(null, "Oops, you revealed a mine! You lose!", "Defeat", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    /**
     * The game win message
     */
    public static void gameWin() {
        JOptionPane.showMessageDialog(null, "Congratulations! You Win!", "Victory", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }
}
