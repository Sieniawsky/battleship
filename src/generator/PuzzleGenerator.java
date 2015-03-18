package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class PuzzleGenerator {
    
    final static String[] ORIENTATIONS = {"vertical", "horizontal"};
    final static int[] SHIP_TYPES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    
    public PuzzleGenerator() {}
    
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
    
    public static String encode(int[][] grid) {
        //int[] x = new int[10];
        //int[] y = new int[10];
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
    
    public void printGrid(int[][] grid) {
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
    
    public static void writeDatasetToFile(String fileName, int n) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for (int i = 1; i <= n; i++) {
            bw.write(encode(generate()));
            bw.newLine();
        }
        
        bw.close();
    }
    
    public static void main(String[] args) {
        
        String fileName = "dataset.txt";
        int n = 100;
        
        if (args.length == 2) {
            fileName = args[0];
            n = Integer.parseInt(args[1]);
        }
        
        fileName = "data/" + fileName;
        
        try {
            writeDatasetToFile(fileName, n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
