package core;

import generator.PuzzleGenerator;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

import utils.Utils;

public class GeneticSolver extends Solver {
    
    final int FIELD_SIZE = 200;

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
                System.out.println("Valid solution has been found!");
                break;
            }
            
            //System.out.println(fitnessToString(fitnesses));

            // Step 3. Replace worst scoring grids with new mutations
            for (int i = FIELD_SIZE/2; i < fitnesses.length; i++) {
                field[fitnesses[i].x] = mutate(deepCopy(field[fitnesses[i-FIELD_SIZE/2].x]));
            }
            
            // If stuck, restart
            if (generations == 5000) {
                System.out.println("reset");
                generations = 0;
                for (int i = 0; i < field.length; i++) {
                    field[i] = PuzzleGenerator.generate();
                }
            }
        }

        return operations;
    }
    
     private String fitnessToString(Point[] fits) {
         String x = "";
         for (Point p: fits) {
             x += p.y + " ";
         }
         return x;
     }

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

    private int[][] mutate(int[][] grid) {
        // Just do a YOLO random mutation
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
        boolean vertical = true;
        boolean cool = false;
        while (!cool) {
            if (y - 1 >= 0 && grid[x][y-1] == ship) {
                // search left
                y = y - 1;
            } else if (x - 1 >= 0 && grid[x-1][y] == ship) {
                // search up
                x = x - 1;
            } else if (isSequential(false, x, y, ship, grid)) {
                cool = true;
                vertical = false;
            } else if (isSequential(true, x, y, ship, grid)) {
                cool = true;
            } else {
                //System.out.println("no op");
                //Utils.printGrid(grid);
                //System.out.println("ship: " + ship + ", x: " + x + ", y: " + y);
                //return null;
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
        
        //System.out.println("before");
        //Utils.printGrid(grid);
        //System.out.println("ship: " + ship + ", x: " + x + ", y: " + y);

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
        
        //System.out.println("after");
        //Utils.printGrid(grid);

        return grid;
    }

    private boolean isSequential(boolean vertical, int x, int y, int ship,
            int[][] grid) {
        
        if (ship == 1)
            return true;
        
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
        
        // check extended range
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
        // horizontal stack cases
        if (vertical && y+1 <= 9 && grid[x][y+1]==ship && y-1 >= 0 && grid[x+ship-1][y-1]==ship) {
            return false;
        }
        if (vertical && y-1>=0 && grid[x][y-1]==ship && y+1<=9 && grid[x+ship-1][y+1]==ship) {
            return false;
        }
        // vertical beside/offset cases
        if (!vertical && x-1>=0 && grid[x-1][y+ship-1]==ship && x+1<=9 && grid[x+1][y]==ship) {
            return false;
        }
        if (!vertical && x-1>=0 && grid[x-1][y]==ship && x+1<=9 && grid[x+1][y+ship-1]==ship) {
            return false;
        }
        
        //System.out.println("is sequential");
        //Utils.printGrid(grid);
        //System.out.println("v: " + vertical + ", ship: " + ship + ", x: " + x + ", y: " + y);

        return true;
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
}
