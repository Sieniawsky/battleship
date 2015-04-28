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

    /**
     * Attemps to place the ship in the grid, and returns true if the ship was successfully placed
     * 
     * @param x - the x coordinate of the ship
     * @param y - the y coordinate of the ship
     * @param size - the size of the ship
     * @param vertical - whether the ship is vertical or horizontal
     * @return True if the ship was successfully placed, false otherwise
     */
    public boolean placeShip(int x, int y, int size, boolean vertical)
    {	
    	// Return false if the ship cannot fit in the grid
    	if (vertical && (size + x > grid.length) || !vertical && (size + y > grid.length)) return false;

    	// Return false if the ship intersects with another ship
    	for (int i = 0; i < size; i++)
    	{
    		if (vertical && this.grid[i + x][y] != 0 || !vertical && this.grid[x][i + y] != 0) return false;
    	}

    	// Return false if the ship's placement makes the parallel numbers too large
        int count = size;
        for (int i = 0; i < grid.length; i++)
        {
        	if ((vertical ? grid[i][y] : grid[x][i]) > 0) count++;
        }
        if (count > (vertical ? this.y[y] : this.x[x])) return false;
        
        // Return false if the ship's placement any of the perpendicular numbers too large
        for (int i = 0; i < size; i++)
        {
        	count = 1;
        	
        	for (int j = 0; j < grid.length; j++)
        	{
        		if ((vertical ? grid[x + i][j] : grid[j][y + i]) > 0) count++;
        	}
        	if (count > (vertical ? this.x[x + i] : this.y[y + i])) return false;
        }

    	// Place the ship
    	for (int i = 0; i < size; i++)
    	{
    		if (vertical) this.grid[i + x][y] = size;
    		else this.grid[x][i + y] = size;
    	}
    	
    	return true;
    }

	public boolean placeShipNoCheck(int x, int y, int size, boolean vertical)
    {
    	// Return false if the ship cannot fit in the grid
    	if (vertical && (size + x > grid.length) || !vertical && (size + y > grid.length)) return false;

    	// Return false if the ship intersects with another ship
    	for (int i = 0; i < size; i++)
    	{
    		if (vertical && this.grid[i + x][y] != 0 || !vertical && this.grid[x][i + y] != 0) return false;
    	}

    	// Place the ship
    	for (int i = 0; i < size; i++)
    	{
    		if (vertical) this.grid[i + x][y] = size;
    		else this.grid[x][i + y] = size;
    	}
    	
    	return true;
    }

    /**
     * Removes the ship by zero-ing out the location of the ship
     * 
     * @param x - the x coordinate of the ship
     * @param y - the y coordinate of the ship
     * @param size - the size of the ship
     * @param vertical - whether the ship is vertical or horizontal
     */
    public void removeShip(int x, int y, int size, boolean vertical)
    {
    	for (int i = 0; i < size; i++)
    	{
    		if (vertical) this.grid[i + x][y] = 0;
    		else this.grid[x][i + y] = 0;
    	}
    }
}
