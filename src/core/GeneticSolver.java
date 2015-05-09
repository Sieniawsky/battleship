package core;

import generator.PuzzleGenerator;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

/**
 * A simple implementation of the genetic algorithm. This
 * implementation focuses on solving 10x10 instances of the
 * Battleship Solitaire puzzle. The basic procedure is as
 * follows:
 * 
 * 1. A search space (field) of random solutions is generated
 *    for the given puzzle.
 *    
 * 2. Fitness values are computed for all elements in the field.
 *    If a fitness of zero is computed for an element then the
 *    optimal solution has been found.
 * 
 * 3. Elements of the field with the worst fitness values are
 *    removed from the field. The remaining elements are mutated
 *    in order to re-populate the search space.
 *    
 * 4. Go to step 2 and repeat until an optimal solution is found
 *    or the generation cap has been reached (5000).
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public class GeneticSolver extends Solver {
    
    final int FIELD_SIZE = 200;
    final int GENERATION_CAP = 5000;

    @Override
    protected int run(Puzzle puzzle) {
        int[][][] field = new int[FIELD_SIZE][][];
        Point[] fitnesses = new Point[FIELD_SIZE];
        int operations = 0;
        boolean found = false;

        for (int i = 0; i < FIELD_SIZE; i++) {
            fitnesses[i] = new Point();
        }

        // Step 1. Randomly place ships on the grid
        for (int i = 0; i < FIELD_SIZE; i++) {
            field[i] = PuzzleGenerator.generate();
        }
        
        int generations = 0;
        
        while (!found) {
            generations++;
            // Step 2. Compute fitness of the field
            fitnesses = computeFieldFitness(puzzle.getX(), puzzle.getY(), field);
            operations += FIELD_SIZE;

            // Check for fitness of 0
            if (fitnesses[0].y == 0) {
                break;
            }

            // Step 3. Replace worst scoring grids with new mutations
            for (int i = FIELD_SIZE/2; i < fitnesses.length; i++) {
                field[fitnesses[i].x] = mutate(deepCopy(field[fitnesses[i-FIELD_SIZE/2].x]));
            }
            
            // If stuck for too long in a local minimum, restart
            if (generations == GENERATION_CAP) {
                generations = 0;
                for (int i = 0; i < field.length; i++) {
                    field[i] = PuzzleGenerator.generate();
                }
            }
        }

        return operations;
    }
    
    /**
     * Takes the x and y constraints for the given puzzle and the field
     * of possible solutions. Using this information fitness values are
     * computed and returned for later use. An array of Point objects is
     * used to store the fitness values. We would like to have a sorted
     * list of fitness values so that finding the worst scores is easy.
     * However, we need to know which fitness value corresponds to which
     * element of the field. Thus the x value of the Point object is the
     * index value of an element in the field, and the y value is the fitness.
     * 
     * @param x The puzzle constraints for the x-axis
     * @param y The puzzle constraints for the y-axis
     * @param field The search space of potential solutions
     * @return
     */
    private Point[] computeFieldFitness(int[] x, int[] y, int[][][] field) {
        int xCount;
        int yCount;
        int[][] grid;
        Point[] fits = new Point[FIELD_SIZE];
        for (int i = 0; i < fits.length; i++) {
            fits[i] = new Point();
        }

        for (int i = 0; i < field.length; i++) {
            fits[i].x = i;
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
                fits[i].y += (Math.abs(x[j] - xCount) + Math.abs(y[j]
                        - yCount));
            }
        }

        // Sort fitness values
        for (int i = 1; i < fits.length; i++) {
            int j = i;
            while (j > 0 && fits[j - 1].y > fits[j].y) {
                Point temp = fits[j];
                fits[j] = fits[j - 1];
                fits[j - 1] = temp;
                j -= 1;
            }
        }

        return fits;
    }
    
    /**
     * Performs the genetic operation of mutation. A random
     * ship is selected and removed from the solution. It is
     * then randomly placed elsewhere on the grid. Several
     * edge-cases must be considered when removing a ship to
     * ensure that the grid is not corrupted.
     * 
     * @param grid
     * @return
     */
    private int[][] mutate(int[][] grid) {
        Random rand = new Random();
        int x = 0;
        int y = 0;
        boolean found = false;
        int ship = 0;
        
        // Find a random ship segment
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
        boolean vertical = true;
        boolean cool = false;
        while (!cool) {
            if (y - 1 >= 0 && grid[x][y-1] == ship) {
                // search left
                y = y - 1;
            } else if (x - 1 >= 0 && grid[x-1][y] == ship) {
                // search up
                x = x - 1;
            } else if (isValidForRemoval(false, x, y, ship, grid)) {
                cool = true;
                vertical = false;
            } else if (isValidForRemoval(true, x, y, ship, grid)) {
                cool = true;
            } else {
                found = false;
                while (!found) {
                    x = rand.nextInt(10);
                    y = rand.nextInt(10);

                    if (grid[x][y] != 0) {
                        ship = grid[x][y];
                        found = true;
                    }
                }
            }
        }

        // Remove it
        for (int i = 0; i < ship; i++) {
            if (vertical)
                grid[i + x][y] = 0;
            else
                grid[x][i + y] = 0;
        }
        
        // Randomly place it somewhere else
        String orientation = "";
        int endpoint = 0;
        boolean placed = false;
        while (!placed) {
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            orientation = PuzzleGenerator.ORIENTATIONS[rand.nextInt(2)];
            
            if (PuzzleGenerator.isPlacementValid(x, y, ship, orientation, grid)) {
                if (orientation.equals("vertical")) {
                    // Place ship vertically
                    endpoint = y + ship;
                    for (int j = y; j < endpoint; j++) {
                        grid[x][j] = ship;
                    }
                } else {
                    // Place ship horizontally
                    endpoint = x + ship;
                    for (int j = x; j < endpoint; j++) {
                        grid[j][y] = ship;
                    }
                }
                placed = true;
            }
        }

        return grid;
    }
    
    /**
     * Utility function that determines if a ship located at the
     * provided coordinates is eligible for removal without
     * corrupting the grid. Several edge-cases must be checked
     * in order to ensure that the ship location is eligible for
     * removal.
     * 
     * @param vertical True if the ship is vertical, false if horizontal
     * @param x The top-left most x coordinate of the ship
     * @param y The top-left most y coordinate of the ship
     * @param ship The ship type that is located at the coordinates
     * @param grid The current solution
     * @return
     */
    private boolean isValidForRemoval(boolean vertical, int x, int y, int ship,
            int[][] grid) {
        
        // No need to check is ship is of length one.
        if (ship == 1)
            return true;
        
        // Check if the entire ship can be sequentially found
        // in the given orientation.
        for (int i = 0; i < ship; i++) {
            if (vertical) {
                if (x+i > 9 || grid[x + i][y] != ship) {
                    return false;
                }
            } else {
                if (y+i > 9 || grid[x][y + i] != ship) {
                    return false;
                }
            }
        }
        
        // *******************
        // Edge-cases begin
        // *******************
        
        // Check extended range
        if (vertical && x+ship <= 9 && grid[x+ship][y] == ship && y+1 <= 9 && grid[x][y+1] == ship)
            return false;
        if (!vertical && y+ship <= 9 && grid[x][y+ship] == ship && x+1 <= 9 && grid[x+1][y] == ship) {
            boolean another = true;
            for (int i = 1; i <= ship; i++) {
                if (x+i>=10 || grid[x+i][y] != ship)
                    another = false;
            }
            if (!another)
                return false;
        }
        // Horizontal stack cases
        if (vertical && y+1 <= 9 && grid[x][y+1]==ship && y-1 >= 0 && grid[x+ship-1][y-1]==ship) {
            return false;
        }
        if (vertical && y-1>=0 && grid[x][y-1]==ship && y+1<=9 && grid[x+ship-1][y+1]==ship) {
            return false;
        }
        // Vertical beside/offset cases
        if (!vertical && x-1>=0 && grid[x-1][y+ship-1]==ship && x+1<=9 && grid[x+1][y]==ship) {
            return false;
        }
        if (!vertical && x-1>=0 && grid[x-1][y]==ship && x+1<=9 && grid[x+1][y+ship-1]==ship) {
            return false;
        }

        return true;
    }
    
    /**
     * Utility function that creates a deep copy of a given
     * two-dimensional array.
     * 
     * @param original
     * @return
     */
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
}
