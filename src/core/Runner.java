package core;

import generator.PuzzleGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if (!loader.load("data/" + args[0]))
        {
        	PuzzleGenerator.main(null);
        	loader.load("data/dataset.txt");
        }
        
        int s = 0;
        try
        {
        	s = Integer.parseInt(args[1]);
        } catch (Exception e) {}
        
        Solver solver = s == 0 ? new BruteForceSolver() : s == 1 ? new BacktrackingSolver() : new GeneticSolver();
        
        Result result;
        
        List<Long> elapsedTimes = new ArrayList<Long>();
        List<Integer> operationCounts = new ArrayList<Integer>();
        
        while (loader.hasNext()) {
            result = solver.solve(loader.getNext());
            
            elapsedTimes.add(result.getTimeElapsed());
            operationCounts.add(result.getOperationCount());
        }
        
        System.out.println("Min time: " + Collections.min(elapsedTimes));
        System.out.println("Max time: " + Collections.max(elapsedTimes));
        System.out.println("Average time: " + Utils.computeLongAverage(elapsedTimes));
        System.out.println();
        System.out.println("Min operations: " + Collections.min(operationCounts));
        System.out.println("Max operations: " + Collections.max(operationCounts));
        System.out.println("Average operations: " + Utils.computeIntAverage(operationCounts));
    }

}
