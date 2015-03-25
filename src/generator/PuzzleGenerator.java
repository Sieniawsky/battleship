package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Simple service that generates data-sets for the Battleship Solitaire 
 * Puzzle. Randomly creates a user specified number of puzzles and 
 * outputs them to a data-set file.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public class PuzzleGenerator {
    
    final static String[] ORIENTATIONS = {"vertical", "horizontal"};
    final static int[] SHIP_TYPES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    
    public PuzzleGenerator() {}
    
    /**
     * Need to add hinter support.
     * 
     * This function returns a random instance of the Battleship
     * Solitaire puzzle. Randomly places battleships on a 10x10
     * integer grid. For each ship an orientation is randomly chosen,
     * as well as random x and y coordinates. A check is made to determine
     * if the ship will fit given its starting x and y coordinates and
     * orientation. If the ship does not fit the process repeats with
     * new values until all ships are placed.
     * 
     * @return An integer matrix
     */
    public static int[][] generate() {
        Random rand = new Random();
        int[][] grid = new int[10][10];
        int x, y, ship, endpoint = 0;
        String orientation = "";
        boolean placed = false;
        
        // Randomly add ships to grid
        for (int i = 0; i < 10; i++) {
            ship = SHIP_TYPES[i];
            placed = false;
            while (!placed) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                orientation = ORIENTATIONS[rand.nextInt(2)];
                
                if (isPlacementValid(x, y, ship, orientation, grid)) {
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
        }
        return grid;
    }
    
    /**
     * Utility function that determines if a given ship fits on
     * the provided grid starting at a certain x and y coordinate
     * and using a given orientation.
     * 
     * @param x
     * @param y
     * @param ship
     * @param orientation
     * @param grid
     * @return
     */
    public static boolean isPlacementValid(int x, int y, int ship, String orientation, int[][] grid) {
        int endpoint = 0;
        if (orientation.equals("vertical")) {
            // Check vertically
            endpoint = y + ship;
            for (int i = y; i < endpoint; i++) {
                if (i >= grid.length || grid[x][i] != 0) {
                    return false;
                }
            }
        } else {
            // Check horizontally
            endpoint = x + ship;
            for (int i = x; i < endpoint; i++) {
                if (i >= grid.length || grid[i][y] != 0) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Utility function that converts a 10x10 integer puzzle grid
     * into a string of integers. The number of ship components in
     * each row and column is counted. These values are then concatenated
     * into a string representation of the puzzle instance.
     * 
     * @param grid
     * @return
     */
    public static String encode(int[][] grid) {
        String x = "", y = "";
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
            x += xCount;
            y += yCount;
        }
        
        return x + y;
    }
    
    /**
     * Generates n puzzles, encodes them, and writes them to a file
     * using the provided filename.
     * 
     * @param fileName
     * @param n
     * @throws IOException
     */
    public static void writeDatasetToFile(String filename, int n) throws IOException {
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for (int i = 1; i <= n; i++) {
            bw.write(encode(generate()));
            bw.newLine();
        }
        
        bw.close();
    }
    
    /**
     * Main methods, parses arguments array for the requested filename
     * and number of puzzles. If now arguments are provided then defaults
     * are used. The appropriate data-set is then created.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        String filename = "dataset.txt";
        int n = 100;
        
        if (args.length == 2) {
            filename = args[0];
            n = Integer.parseInt(args[1]);
        }
        
        filename = "data/" + filename;
        
        try {
            writeDatasetToFile(filename, n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
