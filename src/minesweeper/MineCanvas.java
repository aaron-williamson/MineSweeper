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
    private FieldGenerator field;
    private static final int NUMBER_OF_IMAGES = 13;
    private static final int BOMB = 9;
    private static final int HIDDEN = 11;
    private static final int FLAG = 10;
    private static final int INCORRECT = 12;
    private String[] imagePaths = new String[NUMBER_OF_IMAGES];
    private BufferedImage[] images = new BufferedImage[NUMBER_OF_IMAGES];

    public MineCanvas(FieldGenerator aField) {
        // Initialize the field parameter
        field = aField;
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
        imagePaths[12] = "tileincorrect.png";

        // Add the mouse listener
        this.addMouseListener(this);

        // JPanel Stuff
        this.setPreferredSize(new Dimension(field.getField().length * 32, field.getField()[0].length * 32));
        // Load the images
        loadImages();
    }



    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        for(int x = 0; x < field.getField().length; x++) {
            for (int y = 0; y < field.getField()[0].length; y++) {
                if (field.getMask()[y][x] == 0) {
                    g.drawImage(images[HIDDEN], x * 32, y * 32, 32, 32, null);
                } else if (field.getMask()[y][x] == -1) {
                    g.drawImage(images[FLAG], x * 32, y * 32, 32, 32, null);
                } else {
                    if (field.getField()[y][x] == -1) {
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

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int y = (e.getX() / 32);
        int x = (e.getY() / 32);
        if (x < field.getField().length && y < field.getField()[0].length) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                this.gameLose = field.revealSpace(x, y);
                this.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (field.getMask()[x][y] == -1) {
                    field.unMark(x, y);
                    this.repaint();
                } else if (field.getMask()[x][y] == 0) {
                    field.markMine(x, y);
                    this.repaint();
                }
            }
            if (this.field.getGameWin()) {
                JOptionPane.showMessageDialog(null,"Congratulations! You Win!","Victory",JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else if (this.gameLose) {
                field.revealMines();
                this.repaint();
                JOptionPane.showMessageDialog(null, "Oops, you revealed a mine! You lose!", "Defeat", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ERROR: CLICK OUT OF BOUNDS", "CLICK OUT OF BOUNDS", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing
    }
}
