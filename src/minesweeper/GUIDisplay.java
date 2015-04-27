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
    private JLabel mineLabel;
    private JLabel timerLabel;
    private FieldGenerator field;
    private static final Color BASE02 = new Color(0x073642);
    private static final Color BASE01 = new Color(0x586e75);
    private static final Color SOLAR_RED = new Color(0xdc322f);

    /**
     * Default constructor. Creates a new GUIDisplay
     */
    public GUIDisplay(FieldGenerator aField) throws InterruptedException {
        // Set the field
        field = aField;

        // Create the window and canvas
        frame = new JFrame("Minesweeper");
        canvas = new MineCanvas(field, this);

        // Create the master panel
        JPanel masterPanel = new JPanel(new GridBagLayout());

        // Add the canvas to a panel
        JPanel canvasPanel = new JPanel(new GridLayout(1, 1, 0 , 0));
        canvasPanel.add(canvas);
        canvasPanel.setBorder(GUIDisplay.getSolarizedBorder());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;

        // Add the timer Label
        timerLabel = new JLabel(String.format("%03d",5));
        timerLabel.setOpaque(true);
        timerLabel.setBackground(BASE02);
        timerLabel.setForeground(SOLAR_RED);
        timerLabel.setBorder(getSolarizedBorder());
        timerLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        GridBagConstraints t = new GridBagConstraints();
        t.gridx = 0;
        t.gridy = 0;
        t.weightx = 0.5;
        t.fill = GridBagConstraints.HORIZONTAL;

        // Add the mine counter Label
        mineLabel = new JLabel(String.format("%03d", field.getMinesRemaining()), SwingConstants.RIGHT);
        mineLabel.setOpaque(true);
        mineLabel.setBackground(BASE02);
        mineLabel.setForeground(SOLAR_RED);
        mineLabel.setBorder(getSolarizedBorder());
        mineLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        GridBagConstraints m = new GridBagConstraints();
        m.gridx = 1;
        m.gridy = 0;
        m.weightx = 0.5;
        m.fill = GridBagConstraints.HORIZONTAL;

        // Add the canvas and labels to the master panel
        masterPanel.add(timerLabel, t);
        masterPanel.add(mineLabel, m);
        masterPanel.add(canvasPanel, c);

        // Add the master panel to the frame
        frame.getContentPane().add(masterPanel);

        // Size the window and set it up
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Border getSolarizedBorder() {
        // Colors
        Color solarizedShadow = new Color(0x002b36);
        Border myBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, solarizedShadow, BASE02);
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createMatteBorder(4, 4, 4, 4, BASE02));
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createBevelBorder(BevelBorder.RAISED, solarizedShadow, BASE01));

        return myBorder;
    }

    public void updateMines() {
        mineLabel.setText(String.format("%03d", field.getMinesRemaining()));
    }
}
