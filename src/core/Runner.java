package core;

import utils.Utils;

/**
 * Main class of the project. Opens a data set and solves all instances
 * in the set using a certain heuristic algorithm. Certain metrics that are
 * recorded during the solving process are accumulated. Once the data set is
 * completely solved the final results, statistics, and summary are displayed.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public class Runner {
    
    /**
     * Main method.
     * 
     * @param args
     */
    public static void main (String[] args) {
        // Run an algorithm on the data-set, record metrics, and display results.
        Loader loader = new Loader();
        loader.load("data/dataset-10.txt");
        Puzzle p;
        
        while (loader.hasNext()) {
            p = loader.getNext();
            Utils.printPuzzle(p);
            System.out.println();
        }
    }

}
