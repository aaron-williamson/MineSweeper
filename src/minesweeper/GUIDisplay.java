package minesweeper;


import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

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
        frame.setLayout(new CardLayout());

        // Add the master panel to the frame
        setupMasterPanel();
        frame.getContentPane().add(masterPanel);
        frame.setIconImage(new ImageIcon("minesweeper/img/icon.png").getImage());

        // Size the window and set it up
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * For starting the GUI
     */
    private void setupMasterPanel() {
        canvas = new MineCanvas(field, this);

        // Create the master panel
        masterPanel = new JPanel(new GridBagLayout());

        // Add the menu bar
        menu = mineMenuBar();

        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.HORIZONTAL;
        b.gridx = 0;
        b.gridy = 0;
        b.weightx = 1;
        b.gridwidth = GridBagConstraints.REMAINDER;

        // ScrollBar theming
        UIManager.put("ScrollBar.thumb", new Color(0x0a4a5b));
        UIManager.put("ScrollBar.thumbDarkShadow", BASE03);
        UIManager.put("ScrollBar.thumbHighlight", BASE01);
        UIManager.put("ScrollBar.thumbShadow", BASE02);
        UIManager.put("ScrollBar.track", BASE01);
        UIManager.put("ScrollPane.background", BASE02);

        // Add the canvas to a panel and the panel to a scroll pane
        JPanel canvasPanel = new JPanel(new GridLayout(1, 1, 0 , 0));
        canvasPanel.add(canvas);
        JScrollPane canvasPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        canvasPane.setViewportView(canvasPanel);
        canvasPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI());
        canvasPane.getVerticalScrollBar().setUI(new BasicScrollBarUI());
        canvasPane.setBorder(GUIDisplay.getSolarizedBorder());
        if (field.getField()[0].length >= 40 || field.getField().length >= 20) {
            canvasPane.getViewport().setPreferredSize(new Dimension(1280,640));
        }
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
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
        masterPanel.add(canvasPane, c);
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
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
        UIManager.put("MenuItem.acceleratorForeground", BASE0);
        UIManager.put("MenuItem.acceleratorSelectionForeground", BASE03);
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
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
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
        stopTimer();
        close();
    }

    private void menuNewGame() {
        game.newGame();
    }

    private void menuBeginner() {
        game.newGame(9, 9, 10);
    }

    private void menuIntermediate() {
        game.newGame(16, 16, 40);
    }

    private void menuAdvanced() {
        game.newGame(40, 20, 99);
    }

    private void menuCustom() {
        int[] custom = new int[3];

        custom = getCustom();

        game.newGame(custom[0], custom[1], custom[2]);
    }

    public void newGame(FieldGenerator aField) {
        // Stop the timer
        stopTimer();
        // Set the new field
        field = aField;
        // Create the new timer and set it up
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerUpdate();
            }
        });
        time = 0;
        timerLabel.setText(String.format("%03d", time));

        // Updating the mine counter
        updateMines();

        // Create the new canvas
        canvas.newGame(aField);

        frame.getContentPane().removeAll();

        setupMasterPanel();

        frame.getContentPane().add(masterPanel);
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }

    private int[] getCustom() {
        int[] custom = new int[3];
        custom[1] = 10;
        custom[2] = 10;

        JFrame input = new JFrame("Custom Grid");
        input.setIconImage(new ImageIcon("minesweeper/img/icon.png").getImage());
        input.setUndecorated(true);
        input.setAlwaysOnTop(true);

        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        JLabel customLabel = new JLabel("Custom mine field");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 4, 0);
        c.anchor=GridBagConstraints.CENTER;


        JLabel width = new JLabel("Width:");
        JLabel height = new JLabel("Height:");
        JLabel mines = new JLabel("Mines:");
        GridBagConstraints d = new GridBagConstraints();
        d.gridx = 0;
        d.gridy = 1;
        inputPanel.add(width, d);
        d.gridy = 2;
        inputPanel.add(height, d);
        d.gridy = 3;
        inputPanel.add(mines, d);

        GridBagConstraints b = new GridBagConstraints();
        JTextField widthField = new JTextField();
        widthField.setPreferredSize(new Dimension(50, 24));
        b.gridx = 1;
        b.gridy = 1;
        inputPanel.add(widthField, b);

        JTextField heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(50, 24));
        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 1;
        f.gridy = 2;
        inputPanel.add(heightField, f);
        JTextField mineField = new JTextField();
        mineField.setPreferredSize(new Dimension(50, 24));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = 3;
        inputPanel.add(mineField, g);


        JButton ok = new JButton("Ok");
        GridBagConstraints a = new GridBagConstraints();
        a.gridx = 0;
        a.gridy = 4;
        a.insets = new Insets(4, 0, 0, 4);
        a.anchor = GridBagConstraints.CENTER;
        JButton cancel = new JButton("Cancel");
        GridBagConstraints h = new GridBagConstraints();
        h.gridx = 1;
        h.gridy = 4;
        h.insets = new Insets(4, 4, 0, 0);
        inputPanel.add(cancel, h);


        inputPanel.add(ok, a);
        inputPanel.add(customLabel, c);
        inputPanel.add(widthField, b);
        input.getContentPane().add(inputPanel);
        input.setResizable(false);


        input.setSize(new Dimension(200, 175));
        input.setVisible(true);
        input.setLocationRelativeTo(frame);
        input.setLocation((int)frame.getLocationOnScreen().getX() + frame.getWidth()/2 - input.getWidth()/2,
                (int)frame.getLocationOnScreen().getY() + frame.getHeight()/2 - input.getHeight()/2);


        custom[0] = Integer.parseInt(widthField.getText());

        return custom;
    }
}
