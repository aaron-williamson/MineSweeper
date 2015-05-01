package minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The GUI for the Minesweeper application
 */
public class MineCanvas extends JPanel implements MouseListener {
    private boolean gameLose = false;
    private boolean gameWin = false;
    private FieldGenerator field;
    private static final int NUMBER_OF_IMAGES = 14;
    private static final int BOMB = 9;
    private static final int FLAG = 10;
    private static final int HIDDEN = 11;
    private static final int LOSE = 12;
    private static final int INCORRECT = 13;
    private String[] imagePaths = new String[NUMBER_OF_IMAGES];
    private BufferedImage[] images = new BufferedImage[NUMBER_OF_IMAGES];
    private GUIDisplay gui;
    private int tempX;
    private int tempY;

    public MineCanvas(FieldGenerator aField, GUIDisplay aGui) {
        // Initialize the field and gui
        field = aField;
        gui = aGui;
        // Initialize the image paths
        imagePaths[0] = "tileclear.png";
        imagePaths[1] = "tile1.png";
        imagePaths[2] = "tile2.png";
        imagePaths[3] = "tile3.png";
        imagePaths[4] = "tile4.png";
        imagePaths[5] = "tile5.png";
        imagePaths[6] = "tile6.png";
        imagePaths[7] = "tile7.png";
        imagePaths[8] = "tile8.png";
        imagePaths[9] = "tilebomb.png";
        imagePaths[10] = "tileflag.png";
        imagePaths[11] = "tilehidden.png";
        imagePaths[12] = "tilelose.png";
        imagePaths[13] = "tileincorrect.png";

        // Add the mouse listener
        this.addMouseListener(this);

        // JPanel Stuff
        //this.setPreferredSize(new Dimension(field.getField()[0].length * 32, field.getField().length * 32));
        // Load the images
        loadImages();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(field.getField()[0].length * 32, field.getField().length * 32);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        gui.updateMines();
        for(int x = 0; x < field.getField()[0].length; x++) {
            for (int y = 0; y < field.getField().length; y++) {
                if (field.getMask()[y][x] == FieldGenerator.MASK_HIDDEN) {
                    g.drawImage(images[HIDDEN], x * 32, y * 32, 32, 32, null);
                } else if (field.getMask()[y][x] == FieldGenerator.MASK_MARKED) {
                    g.drawImage(images[FLAG], x * 32, y * 32, 32, 32, null);
                }else if (field.getMask()[y][x] == FieldGenerator.MASK_LOSE) {
                    g.drawImage(images[LOSE], x * 32, y * 32, 32, 32, null);
                } else if (field.getMask()[y][x] == FieldGenerator.MASK_INCORRECT) {
                    g.drawImage(images[INCORRECT], x * 32, y * 32, 32, 32, null);
                } else {
                    if (field.getField()[y][x] == FieldGenerator.FIELD_MINE) {
                        g.drawImage(images[BOMB], x * 32, y * 32, 32, 32, null);
                    } else {
                        g.drawImage(images[field.getField()[y][x]], x * 32, y * 32, 32, 32, null);
                    }
                }
            }
        }
    }

    /**
     * Load the images from imagePaths into the BufferedImage array
     * images.
     */
    private void loadImages() {
        for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
            try {
                images[i] = ImageIO.read(new File("minesweeper/img/" + imagePaths[i]));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    /**
     * Load the images from imagePaths into the BufferedImage array
     * images.
     * FOR JAR FILE
     *
    private void loadImages() {
        System.out.println(this.getClass().getClassLoader().getResourceAsStream("img/" + imagePaths[0]));
        for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
            try {
                images[i] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("img/" + imagePaths[i]));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameLose && !gameWin) {
            if (tempX != e.getX() || tempY != e.getY()) {
                if ((e.getX() / 32) == (tempX / 32) && (e.getY() / 32 == tempY / 32)) {
                    this.mouseClicked(e);
                } else {
                    if (field.getMask()[tempY / 32][tempX / 32] == 0) {
                        this.getGraphics().drawImage(images[HIDDEN], ((tempX / 32) * 32), ((tempY / 32) * 32), 32, 32, null);
                    }
                }
            }
            tempX = 0;
            tempY = 0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameLose && !gameWin) {
            if (field.getFirstClick())
                gui.startTimer(); // Start timer
            int x = (e.getX() / 32);
            int y = (e.getY() / 32);
            if (x < field.getField()[0].length && y < field.getField().length) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    this.gameLose = field.revealSpace(x, y);
                    this.repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field.getMask()[y][x] == -1) {
                        field.unMark(x, y);
                        this.repaint();
                    } else if (field.getMask()[y][x] == 0) {
                        field.markMine(x, y);
                        this.repaint();
                    }
                }
                if (gameWin = field.getGameWin()) {
                    gui.gameWin();
                } else if (this.gameLose) {
                    field.loseGame();
                    this.repaint();
                    gui.gameLose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR: CLICK OUT OF BOUNDS", "CLICK OUT OF BOUNDS", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameLose && !gameWin) {
            tempX = e.getX();
            tempY = e.getY();
            if ((e.getButton() == MouseEvent.BUTTON1) && field.getMask()[e.getY() / 32][e.getX() / 32] == 0)
                this.getGraphics().drawImage(images[0], (e.getX() / 32) * 32, (e.getY() / 32) * 32, 32, 32, null);
        }
    }

    public void newGame(FieldGenerator aField) {
        gameLose = false;
        gameWin = false;
        field = aField;
        //this.setPreferredSize(new Dimension(field.getField()[0].length * 32, field.getField().length * 32));
    }
}
