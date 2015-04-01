package core;

/**
 * Interface that all algorithmic solvers will have to implement.
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 *
 */
public abstract class Solver {
    
    public final Result solve(Puzzle puzzle)
    {    	
    	long startTime = System.currentTimeMillis();
    	
    	Result result = new Result(run(puzzle), System.currentTimeMillis() - startTime);
    	
    	return result;
    }
    
    protected abstract int run(Puzzle puzzle);
}
