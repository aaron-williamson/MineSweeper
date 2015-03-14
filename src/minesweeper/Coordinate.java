package minesweeper;

/**
 * The class for holding the coordinate of a point on the field
 */
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(int anX, int aY) {
        x = anX;
        y = aY;
    }

    /**
     * Get the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
}
