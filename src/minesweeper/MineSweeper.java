package minesweeper;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private FieldGenerator generator;
    public MineSweeper() {
        generator = new FieldGenerator();
    }

    public void startGame() {
        ConsoleDisplay display = new ConsoleDisplay(generator);
        System.out.println("Revealed field:");
        display.printFieldRevealed();
        System.out.println("\nTrue field:");
        display.printField();
    }

    public void gameOver() {
        System.out.println("You revealed a mine! GAME OVER");
    }
}
