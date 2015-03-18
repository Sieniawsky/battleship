package generator;

import java.util.Random;

public class PuzzleGenerator {
    
    final String[] ORIENTATIONS = {"vertical", "horizontal"};
    final int[] SHIP_TYPES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    
    public PuzzleGenerator() {}
    
    public int[][] generate() {
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
                
                if (isPlacementValid(x, y, orientation, ship, grid)) {
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
    
    public boolean isPlacementValid(int x, int y, String orientation, int ship, int[][] grid) {
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
    
    public static void main(String[] args) {
        PuzzleGenerator p = new PuzzleGenerator();
        
        for (int i = 0; i < 1; i++) {
            p.printGrid(p.generate());
            System.out.println();
        }
    } 
}
