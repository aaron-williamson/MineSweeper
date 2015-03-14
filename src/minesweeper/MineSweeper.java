package minesweeper;

/**
 * The connecting class for connecting the GUI and Logic
 */
public class MineSweeper {
    private FieldGenerator field;
    private boolean gameOver = false;
    public MineSweeper() {
        field = new FieldGenerator();
    }

    public void startGame() {
        ConsoleDisplay display = new ConsoleDisplay(field);
        System.out.println("Revealed field:");
        display.printFieldRevealed();
        while(!gameOver) {
            display.printField();
            int[] move = display.getMove();
            gameOver = this.makeMove(move);
        }
    }

    public void gameOver() {
        System.out.println("You revealed a mine! GAME OVER");
        System.exit(0);
    }

    public boolean makeMove(int[] move) {
        return field.revealSpace(move[0], move[1]);
    }
}
