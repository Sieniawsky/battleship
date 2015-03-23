package core;

import java.awt.Point;
import java.util.HashMap;

/**
 * Basic implementation of a puzzle instance. Container
 * for the puzzle grid, x/y axis labels, and hints.
 * 
 * @author Martin Sieniawski msien009@uottawa.cq
 *
 */
public class Puzzle {
    
    private int[][] grid;
    private int[] x;
    private int[] y;
    private HashMap<Point, Integer> hints;
    
    public Puzzle(int[][] grid, int[] x, int[] y, HashMap<Point, Integer> hints) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.hints = hints;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public HashMap<Point, Integer> getHints() {
        return hints;
    }

    public void setHints(HashMap<Point, Integer> hints) {
        this.hints = hints;
    }
}
