package core;

import java.util.Arrays;

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
    
    public Puzzle(int[][] grid, int[] x, int[] y) {
        this.grid = grid;
        this.x = x;
        this.y = y;
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
    
    public boolean isValid()
    {
    	int[] x = new int[10];
    	int[] y = new int[10];
        int xCount = 0;
        int yCount = 0;
        
        // Determine x-axis and y-axis labels
        for (int i = 0; i < grid.length; i++) {
            xCount = 0;
            yCount = 0;
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) {
                    xCount++;
                }
                
                if (grid[j][i] != 0) {
                    yCount++;
                }
            }
            x[i] = xCount;
            y[i] = yCount;
        }
    	
        return Arrays.equals(x, this.x) && Arrays.equals(y, this.y);
    }
}
