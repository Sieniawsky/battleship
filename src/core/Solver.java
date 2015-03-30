package core;

/**
 * Interface that all algorithmic solvers will have to implement.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public interface Solver {
    
    public int[] solve(Puzzle puzzle);
}
