package minesweeper;

import java.util.Scanner;

/**
 * Class for displaying the MineSweeper game on the command console
 */
public class ConsoleDisplay {
    private FieldGenerator field;
    private Scanner input = new Scanner(System.in);

    /**
     * Constructors
     * @param aField the field to construct this console display with
     */
    public ConsoleDisplay(FieldGenerator aField) {
        // Set the field
        field = aField;
    }

    public ConsoleDisplay() {
        // Do nothing
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
        // Print the number of mines remaining
        System.out.println("Mines remaining: " + field.getMinesRemaining());
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
                int mask = field.getMask()[i][j];
                if (mask == 1) {
                    int out = field.getField()[i][j];
                    if (out == 0) System.out.print("  .  ");
                    else System.out.printf("  %d  ", out);
                }
                else if (mask == -1) {
                    System.out.print("  X  ");
                }
                else System.out.print("  O  ");
            }
            System.out.println("\n");
        }
    }

    public Coordinate getCoord(String command) {
        String strMove;
        Coordinate coord;
        System.out.println("What is the position you would like to " + command + "? (Enter as: " +
                "vertical, horizontal)");
        strMove = input.nextLine();
        try {
            int x = Integer.parseInt(strMove.substring(0, strMove.indexOf(",")));
            int y = Integer.parseInt(strMove.substring(strMove.indexOf(",") + 2));
            coord = new Coordinate(x,y);
        } catch (NumberFormatException e) {
            System.out.println("INVALID INPUT. TRY AGAIN.");
            coord = this.getCoord(command);
        }
        return coord;
    }

    public String getCommand() {
        String command;
        System.out.println("What would you like to do? (reveal, mark, unmark)");
        command = input.nextLine();
        if (!command.equals("unmark") && !command.equals("mark") && !command.equals("reveal")) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
            command = this.getCommand();
        }
        return command;
    }
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void setField(FieldGenerator field) {
        this.field = field;
    }
}
