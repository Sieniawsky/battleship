package core;

/**
 * Abstract class from which all algorithm classes will extend
 * 
 * @author Martin Sieniawski msien009@uottawa.ca
 */
public abstract class Solver
{
	/**
	 * Runs and times the algorithm, then returns the result
	 * 
	 * @param puzzle - the puzzle to solve
	 * @return The result of the solve
	 */
	public final Result solve(Puzzle puzzle)
	{
		// Start time for the run
    	long startTime = System.currentTimeMillis();
    	
    	// Run the algorithm and get the result
    	Result result = new Result(run(puzzle), System.currentTimeMillis() - startTime);
    	
    	return result;
    }
    
    /**
	 * Runs the algorithm
	 * 
	 * @param puzzle - The puzzle to be solved
	 * @return The number of operations
	 */
    protected abstract int run(Puzzle puzzle);
}
