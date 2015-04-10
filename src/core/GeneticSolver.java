package core;

import generator.PuzzleGenerator;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

public class GeneticSolver extends Solver {

	@Override
	protected int run(Puzzle puzzle) {
	    int[][][] field = new int[10][][];
	    Point[] fitnesses = new Point[10];
	    int operations = 0;
	    boolean found = false;
	    
	    for (int i = 0; i < 10; i++) {
	        fitnesses[i] = new Point();
	    }
	    
	    // Step 1. Randomly place ships on the grid
	    for (int i = 0; i < 10; i++) {
	        field[i] = PuzzleGenerator.generate();
	    }
	    
	    while (!found) {
	        // Step 2. Compute fitness of the field
	        fitnesses = computeFieldFitness(puzzle.getX(), puzzle.getY(), fitnesses, field);
	        operations += 10;
	        
	        // Check for fitness of 0
	        if (fitnesses[0].y == 0) {
	            return operations;
	        }
	        
	        // Step 3. Replace worst scoring grids with new mutations
	        for (int i = 5; i < 10; i++) {
	            field[i] = mutate(puzzle.getX(), puzzle.getY(), deepCopy(field[i-5]));
	        }
	    }
	    
		return operations;
	}
	
	private Point[] computeFieldFitness(int[] x, int[] y, Point[] fitnesses, int[][][] field) {
	    int xCount;
	    int yCount;
	    int[][] grid;
	    
	    for (int i = 0; i < field.length; i++) {
	        fitnesses[i].x = i;
	        grid = field[i];
	        
	        for (int j = 0; j < grid.length; j++) {
	            xCount = 0;
	            yCount = 0;
	            for (int k = 0; k < grid[0].length; k++) {
	                if (grid[j][k] != 0) {
	                    xCount++;
	                }
	                
	                if (grid[k][j] != 0) {
	                    yCount++;
	                }
	            }
	            fitnesses[i].y += (Math.abs(x[j] - xCount) + Math.abs(y[j] - yCount)); 
	        }
	    }
	    
	    // Sort fitness values
	    for (int i = 1; i < fitnesses.length; i++) {
	        int j = i;
	        while (j > 0 && fitnesses[j-1].y > fitnesses[j].y) {
	            Point temp = fitnesses[j];
	            fitnesses[j] = fitnesses[j-1];
	            fitnesses[j-1] = temp;
	            j -= 1;
	        }
	    }
	    
	    return fitnesses;
	}
	
	public Point[] findSurplusAndDeficit(int[] x, int[] y, int[][] grid) {
	    Point[] points = new Point[2];
	    int min = 1000;
	    int max = 0;
	    points[0] = new Point();
	    points[1] = new Point();
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
            
            // Find the min and max
            // Record their x,y coordinates
            
        }
        
	    return points;
	}
	
	private int[][] mutate(int[] xx, int[] yy, int[][] grid) {
	    // Find where too many ships are and where too few are
	    //Point[] points = findSurplusAndDeficit(x, y, grid);
	    
	    // find a ship to remove
	    
	    // Place it
	    
	    
	    // Just do a YOLO random mutate
	    Random rand = new Random();
	    int x = 0;
	    int y = 0;
	    boolean found = false;
	    int ship = 0;
	    
	    while (!found) {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            
            if (grid[x][y] != 0) {
                ship = grid[x][y];
                found = true;
            }
	    }
	    
	    // Find the location of the whole ship
	    
	    // Find its top-left corner
	    
	    // Remove it
	    
	    // Randomly place it somewhere else
	    
	    System.out.println(ship);
	    
	    return grid;
	}
	
	private int[][] deepCopy(int[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final int[][] result = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
	
    private int[][] removeShip(int x, int y, int size, boolean vertical, int[][] grid) {
        for (int i = 0; i < size; i++) {
            if (vertical) grid[i + x][y] = 0;
            else grid[x][i + y] = 0;
        }
        
        return grid;
    }
}
