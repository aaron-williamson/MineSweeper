package minesweeper;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class for displaying the MineCraft game in a GUI.
 */
public class GUIDisplay {
    private JFrame frame;
    private MineCanvas canvas;

    /**
     * Default constructor. Creates a new GUIDisplay
     */
    public GUIDisplay(FieldGenerator aField) throws InterruptedException {
        // Create the window and canvas
        frame = new JFrame("Minesweeper");
        canvas = new MineCanvas(aField);

        // Add the canvas to a panel
        JPanel panel = new JPanel(new GridLayout(1, 1, 0 , 0));
        panel.add(canvas);

        // Set the panels border
        panel.setBorder(GUIDisplay.getSolarizedBorder());

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Size the window and set it up
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Border getSolarizedBorder() {
        // Colors
        Color solarizedHighlight = new Color(0x586e75);
        Color solarizedShadow = new Color(0x002b36);
        Color solarizedBase = new Color(0x073642);
        Border myBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, solarizedShadow, solarizedHighlight);
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createMatteBorder(4, 4, 4, 4, solarizedBase));
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createBevelBorder(BevelBorder.RAISED, solarizedShadow, solarizedHighlight));

        return myBorder;
    }
}
