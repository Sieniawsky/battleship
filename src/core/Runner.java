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
            System.out.println("X: " + Utils.arrayToString(p.getX()));
            System.out.println("Y: " + Utils.arrayToString(p.getY()));
            System.out.println("Hints: " + p.getHints().length);
            System.out.println();
        }
    }

}
