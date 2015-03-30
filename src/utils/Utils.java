package utils;

import core.Puzzle;

/**
 * Utility class that provides several static helper methods.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public class Utils {
    
    /**
     * Takes a Puzzle instance and prints its grid,
     * x and y labels, and its hints. The data is printed
     * in such a way that a complete snapshot of the puzzle
     * can be easily seen and understood.
     * 
     * @param puzzle
     */
    public static void printPuzzle(Puzzle puzzle) {
        
        for (int i = puzzle.getGrid()[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < puzzle.getGrid().length; j++) {
                if (j == 9) {
                    System.out.print(puzzle.getGrid()[j][i] + " - " + puzzle.getY()[i]);
                } else {
                    System.out.print(puzzle.getGrid()[i][j] + " ");
                }
            }
            System.out.println();
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.print("| ");
        }
        System.out.println();
        
        for (int i = 0; i < puzzle.getX().length; i++) {
            System.out.print(puzzle.getX()[i] + " ");
        }
        System.out.println();
    }
    
    /**
     * Creates a string representation of the provided array.
     * 
     * @param x
     * @return
     */
    public static String arrayToString(int[] x) {
        String array = "[";
        for (int i = 0; i < x.length; i++) {
            if (i != x.length -1) {
                array += x[i] + ", ";
            } else {
                array += x[i] + "]";
            }
        }
        
        return array;
    }
    
    /**
     * Utility function that prints the contents of a matrix
     * such that the coordinate [0][0] is in the bottom left
     * hand corner of the printed grid.
     * 
     * @param grid
     */
    public static void printGrid(int[][] grid) {
        int count = 0;
        for (int i = grid[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0)
                    count++;
                System.out.print(grid[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Count: " + count);
        System.out.println();
    }

}
