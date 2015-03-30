package core;

import utils.Utils;

public class Runner {
    
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
