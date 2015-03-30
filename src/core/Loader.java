package core;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that loads a data-set and parses its contents.
 * Since a data-set with 10000 entries would have a file size
 * of less than a megabyte the entire data-set is loaded into
 * memory.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public class Loader {
    // Loads a data-set and parses lines, marshaling each into
    // an instance of Puzzle. The instance is then fed to Runner
    int index;
    int size;
    List<String> dataset;
    
    public Loader() {
        index = 0;
    }
    
    /**
     * Opens the specified data set file and loads its
     * contents into memory.
     * 
     * @param filename
     * @return
     */
    public boolean load(String filename) {

        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(reader);
            dataset = new ArrayList<String>();
            String line = null;
            while ((line = bfr.readLine()) != null) {
                dataset.add(line);
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        size = dataset.size();
        
        return false;
    }
    
    /**
     * Takes a single line from a data set file and decodes the
     * string representation. A Puzzle object instance is returned.
     * 
     * @param encoded
     * @return
     */
    public Puzzle decode(String encoded) {
        if (encoded.length() > 20) {
            throw new IllegalArgumentException("Invalid encoded string.");
        }
         
        char[] chars = encoded.toCharArray();
        int[] x = new int[10];
        int[] y = new int[10];
        
        // Parse labels
        for (int i = 0; i < chars.length; i++) {
            if (i < 10) {
                x[i] = Character.getNumericValue(chars[i]);
            } else {
                y[i - 10] = Character.getNumericValue(chars[i]);
            }
        }
        
        // Parse hints
        int length = (encoded.length() - 20)/2;
        Point[] hints = new Point[length];
        int[][] grid = new int[10][10];
        
        // Add the hints to the blank grid
        for (int i = 0; i < hints.length; i++) {
            grid[hints[i].x][hints[i].y] = 9;
        }
        
        return new Puzzle(grid, x, y, hints);
    }
    
    /**
     * Return true if the data set has at least one more
     * line to read.
     * 
     * @return
     */
    public boolean hasNext() {
        return (index + 1) <= size;
    }
    
    /**
     * Decodes the next line in the data set and returns its
     * puzzle object representation.
     * 
     * @return
     */
    public Puzzle getNext() {
        
        if (index + 1 > size) {
            return null;
        }
        
        Puzzle instance = decode(dataset.get(index));
        index++;
        
        return instance;
    }
}
