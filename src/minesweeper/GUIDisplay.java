package minesweeper;


import javax.swing.*;
import java.awt.*;

/**
 * Class for displaying the MineCraft game in a GUI.
 */
public class GUIDisplay {
    private FieldGenerator field;
    private JFrame frame;
    private MineCanvas canvas;

    /**
     * Default constructor. Creates a new GUIDisplay
     */
    public GUIDisplay() throws InterruptedException {
        frame = new JFrame("Minesweeper");
        canvas = new MineCanvas();
        frame.add(canvas);
        frame.setSize(304, 327);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setField(FieldGenerator aField) {
        field = aField;
        canvas.setField(field);
    }

    public void repaint() {
        canvas.repaint();
    }
}
