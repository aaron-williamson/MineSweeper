package minesweeper;

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
        ConsoleDisplay.printFieldRevealed(field);
    }

    /**
     * The game over message
     */
    public static void gameOver() {
        System.out.println("You revealed a mine! GAME OVER");
        System.exit(0);
    }

    /**
     * The game win message
     */
    public static void gameWin() {
        System.out.println("You won the game! Congratulations!");
        System.exit(0);
    }
}
