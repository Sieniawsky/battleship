package generator;

import java.util.Random;

public class Cool {
    
    final String[] ORIENTATIONS = {"vertical", "horizontal"};
    final int[] SHIP_TYPES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    
    //public PuzzleGenerator() {}
    
    public int[][] generate() {
        Random rand = new Random();
        int[][] grid = new int[10][10];
        int x, y, ship = 0;
        
        // Randomly add ships to grid
        for (int i = 0; i < 10; i++) {
            boolean placed = false;
            while (!placed) {
                x = rand.nextInt(9);
                y = rand.nextInt(9);
                
                if (grid[x][y] == 0) {
                    ship = SHIP_TYPES[i];
                    grid[x][y] = ship;
                    placed = true;
                }
            }
        }
        return grid;
    }
    
    public void printGrid(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0)
                    count++;
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Count: " + count);
        System.out.println();
    }
    
    public static void main(String[] args) {
        //PuzzleGenerator p = new PuzzleGenerator();
        
        for (int i = 0; i < 4; i++) {
            //p.printGrid(p.generate());
            System.out.println();
        }
    } 
}
