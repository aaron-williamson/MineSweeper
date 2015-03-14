package minesweeper;

import java.util.Scanner;

/**
 * Class for displaying the MineSweeper game on the command console
 */
public class ConsoleDisplay {
    private FieldGenerator field;

    /**
     * Constructor
     * @param aField the field to construct this console display with
     */
    public ConsoleDisplay(FieldGenerator aField) {
        // Set the field
        field = aField;
    }
    /**
     * Prints out the minefield with all tiles revealed
     */
    public void printFieldRevealed() {
        // Print out the number lines
        System.out.print("      ");
        for (int i = 0; i < field.getField().length; i++) {
            if (i < 10)
                System.out.print(" [" + (i) + "] ");
            else
                System.out.print(" [" + (i) + "]");
        }
        System.out.println();
        for (int i = 0; i < field.getField().length; i++) {
            if (i < 10)
                System.out.print("  [" + (i) + "] ");
            else
                System.out.print("  [" + (i) + "]");
            for (int j = 0; j < field.getField()[0].length; j++) {
                int out = field.getField()[i][j];
                if (out == -1) System.out.print("  X  ");
                else if (out == 0) System.out.print("  .  ");
                else System.out.printf("  %d  ",out);
            }
            System.out.println();
            System.out.println();
        }
    }

    public void printField() {
        // Print out the number lines
        System.out.print("      ");
        for (int i = 0; i < field.getField().length; i++) {
            if (i < 10)
                System.out.print(" [" + (i) + "] ");
            else
                System.out.print(" [" + (i) + "]");
        }
        System.out.println();
        // Print out the field
        for (int i = 0; i < field.getField().length; i++) {
            if (i < 10)
                System.out.print("  [" + (i) + "] ");
            else
                System.out.print("  [" + (i) + "]");
            for (int j = 0; j < field.getField()[0].length; j++) {
                boolean revealed = field.getReveal()[i][j];
                if (revealed) {
                    int out = field.getField()[i][j];
                    if (out == 0) System.out.print("  .  ");
                    else if (out == -1) System.out.print("  X  ");
                    else System.out.printf("  %d  ", out);
                }
                else System.out.print("  O  ");
            }
            System.out.println("\n");
        }
    }

    public int[] getMove() {
        String strMove;
        int[] move;
        Scanner input = new Scanner(System.in);
        System.out.println("What is the move you would like to make? (Enter as: x, y)");
        strMove = input.nextLine();
        try {
            int x = Integer.parseInt(strMove.substring(0, strMove.indexOf(",")));
            int y = Integer.parseInt(strMove.substring(strMove.indexOf(",") + 2));
            move = new int[2];
            move[0] = x;
            move[1] = y;
        } catch (NumberFormatException e) {
            System.out.println("INVALID INPUT. TRY AGAIN.");
            move = this.getMove();
        }
        return move;
    }
}
