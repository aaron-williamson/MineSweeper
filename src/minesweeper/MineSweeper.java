package minesweeper;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private FieldGenerator field;
    private boolean gameOver = false;
    private boolean gameWin = false;
    private ConsoleDisplay display;
    private GUIDisplay gui;

    /**
     * Starts the game
     */
    public void startGame() {
        // Create a new display
        display = new ConsoleDisplay();
        gui = new GUIDisplay();
        field = new FieldGenerator(display);
        display.setField(field);
        // Output the revealed field for debugging
        display.printMessage("Revealed field:");
        display.printFieldRevealed();
        // Get and run commands
        while(!gameOver && !gameWin) {
            display.printField();
            String command = display.getCommand();
            Coordinate coord = display.getCoord(command);
            if (command.equals("mark")) {
                markMine(coord);
            }
            else if (command.equals("unmark")) {
                unMark(coord);
            }
            else if (command.equals("reveal")) {
                gameOver = revealCoord(coord);
            }
            gameWin = field.getGameWin();
        }
        // The end of the game
        if (gameOver) this.gameOver();
        else if (gameWin) this.gameWin();
        else display.printMessage("UNSPECIFIED END CONDITION.");
    }

    /**
     * The game over message
     */
    private void gameOver() {
        display.printMessage("You revealed a mine! GAME OVER");
    }

    /**
     * The game win message
     */
    private void gameWin() {
        display.printMessage("You won the game! Congratulations!");
    }

    /**
     * Use this command to reveal a square
     * @param coord the coordinate to reveal
     * @return boolean, true if the user revealed a mine and lost the game
     */
    private boolean revealCoord(Coordinate coord) {
        return field.revealSpace(coord.getX(), coord.getY());
    }

    /**
     * Use this command to mark a square as a mine
     * @param coord the coordinate of the mine to be marked
     */
    private void markMine(Coordinate coord) {
        field.markMine(coord.getX(), coord.getY());
    }

    /**
     * Unmark the mine at coord
     * @param coord the coordinate to be unmarked
     */
    private void unMark(Coordinate coord) {
        field.unMark(coord.getX(), coord.getY());
    }
}
