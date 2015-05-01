package minesweeper;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class for displaying the MineCraft game in a GUI.
 */
public class GUIDisplay {
    public static final Color BASE03 = new Color(0x002b36);
    public static final Color BASE02 = new Color(0x073642);
    public static final Color BASE01 = new Color(0x586e75);
    public static final Color BASE0 = new Color(0x7da7ac);
    public static final Color BASE1 = new Color(0x6d9fa0);
    public static final Color BASE2 = new Color(0xeee8d5);
    public static final Color BASE3 = new Color(0xfdf6e3);
    public static final Color SOLAR_RED = new Color(0xdc322f);


    private JFrame frame;
    private MineCanvas canvas;
    private JLabel mineLabel;
    private JLabel timerLabel;
    private FieldGenerator field;
    private Timer timer;
    private int time;
    private MineSweeper game;
    private JMenuBar menu;
    private JPanel masterPanel;

    /**
     * Default constructor. Creates a new GUIDisplay
     */
    public GUIDisplay(FieldGenerator aField, MineSweeper aGame) throws InterruptedException {
        // Set the field
        field = aField;
        game = aGame;

        firstStart();
    }

    /**
     * The class for creating a border that matches the color scheme
     * @return a solarized border
     */
    private static Border getSolarizedBorder() {
        // Colors
        Color solarizedShadow = new Color(0x002b36);
        Border myBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, solarizedShadow, BASE02);
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createMatteBorder(4, 4, 4, 4, BASE02));
        myBorder = BorderFactory.createCompoundBorder(myBorder, BorderFactory.createBevelBorder(BevelBorder.RAISED, solarizedShadow, BASE01));

        return myBorder;
    }

    /**
     * The first creation of the JFrame
     */
    private void firstStart() {
        // Create the window and canvas
        frame = new JFrame("Minesweeper");
        startGUI();
    }

    /**
     * For starting the GUI
     */
    private void startGUI() {
        canvas = new MineCanvas(field, this);

        // Create the master panel
        masterPanel = new JPanel(new GridBagLayout());

        // Add the menu bar
        menu = mineMenuBar();

        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.HORIZONTAL;
        b.gridx = 0;
        b.gridy = 0;
        b.gridwidth = GridBagConstraints.REMAINDER;

        // Add the canvas to a panel
        JPanel canvasPanel = new JPanel(new GridLayout(1, 1, 0 , 0));
        canvasPanel.add(canvas);
        canvasPanel.setBorder(GUIDisplay.getSolarizedBorder());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;

        // Add the timer Label
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerUpdate();
            }
        });
        time = 0;
        timerLabel = new JLabel(String.format("%03d", time));
        timerLabel.setOpaque(true);
        timerLabel.setBackground(BASE02);
        timerLabel.setForeground(SOLAR_RED);
        timerLabel.setBorder(getSolarizedBorder());
        timerLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        GridBagConstraints t = new GridBagConstraints();
        t.gridx = 0;
        t.gridy = 1;
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
        m.gridy = 1;
        m.weightx = 0.5;
        m.fill = GridBagConstraints.HORIZONTAL;

        // Add the canvas, menu, and labels to the master panel
        masterPanel.add(menu, b);
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

    /**
     * Updates the number of mines remaining
     */
    public void updateMines() {
        mineLabel.setText(String.format("%03d", field.getMinesRemaining()));
    }

    /**
     * Updates the game timer
     */
    public void timerUpdate() {
        time++;
        timerLabel.setText(String.format("%03d", time));
    }

    /**
     * Starts the game timer
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * Stops the game timer
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * Get the current game time
     * @return the game time
     */
    public int getTime() {
        return time;
    }

    /**
     * Close the frame
     */
    public void close() {
        frame.dispose();
    }

    /**
     * The game over message
     */
    public void gameLose() {
        frame.setTitle("Minesweeper - GAME OVER");
        stopTimer();
    }

    /**
     * The game win message
     */
    public void gameWin() {
        frame.setTitle("Minesweeper - GAME WIN");
        stopTimer();
    }

    /**
     * The class for creating a menu bar that matches the color theme
     * @return a menu bar matching the color scheme
     */
    private JMenuBar mineMenuBar() {
        // UI Manager stuff for the menu
        UIManager.put("MenuItem.selectionBackground", BASE0);
        UIManager.put("MenuItem.selectionForeground", BASE02);
        UIManager.put("MenuBar.selectionBackground", BASE0);
        UIManager.put("MenuBar.selectionForeground", BASE02);
        UIManager.put("Menu.selectionBackground", BASE0);
        UIManager.put("Menu.selectionForeground", BASE02);
        UIManager.put("PopupMenu.foreground", BASE03);
        UIManager.put("PopupMenu.background", BASE03);
        UIManager.put("PopupMenu.border",BorderFactory.createBevelBorder(BevelBorder.LOWERED, BASE02, BASE01));
        JMenuBar mineBar = new JMenuBar();
        mineBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        mineBar.setBorderPainted(false);
        mineBar.setBackground(BASE02);


        // Add the game menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.setBorderPainted(false);
        gameMenu.setForeground(BASE1);

        // Add the new game and exit items to the menu
        JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
        newGame.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        newGame.setForeground(BASE1);
        newGame.setBackground(BASE02);
        newGame.setBorderPainted(false);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuNewGame();
            }
        });
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
        exit.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        exit.setForeground(BASE1);
        exit.setBackground(BASE02);
        exit.setBorderPainted(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuExit();
            }
        });

        // Create the difficulties and add them to the difficulty menu
        JMenuItem beginner = new JMenuItem("Beginner", KeyEvent.VK_B);
        beginner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        beginner.setBorderPainted(false);
        beginner.setForeground(BASE1);
        beginner.setBackground(BASE02);
        beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuBeginner();
            }
        });
        JMenuItem intermediate = new JMenuItem("Intermediate", KeyEvent.VK_I);
        intermediate.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        intermediate.setBorderPainted(false);
        intermediate.setForeground(BASE1);
        intermediate.setBackground(BASE02);
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuIntermediate();
            }
        });
        JMenuItem advanced = new JMenuItem("Advanced", KeyEvent.VK_A);
        advanced.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        advanced.setBorderPainted(false);
        advanced.setForeground(BASE1);
        advanced.setBackground(BASE02);
        advanced.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAdvanced();
            }
        });
        JMenuItem custom = new JMenuItem("Custom", KeyEvent.VK_C);
        custom.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        custom.setBorderPainted(false);
        custom.setForeground(BASE1);
        custom.setBackground(BASE02);
        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuCustom();
            }
        });

        // Add components to the menu
        gameMenu.add(newGame);
        gameMenu.add(mineSeparator());
        gameMenu.add(beginner);
        gameMenu.add(intermediate);
        gameMenu.add(advanced);
        gameMenu.add(custom);
        gameMenu.add(mineSeparator());
        gameMenu.add(exit);

        mineBar.add(gameMenu);

        return mineBar;
    }

    /**
     * The class for getting a separator for the matching color scheme
     * @return a separator matching the color scheme
     */
    private JSeparator mineSeparator() {
        // Create the separator
        JSeparator sep = new JSeparator();
        sep.setBackground(BASE02);
        sep.setForeground(BASE1);

        return sep;
    }

    private void menuExit() {
        System.exit(0);
    }

    private void menuNewGame() {

    }

    private void menuBeginner() {

    }

    private void menuIntermediate() {

    }

    private void menuAdvanced() {

    }

    private void menuCustom() {

    }
}
