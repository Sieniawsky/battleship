package core;

/**
 * A Result contains the number of operations and the time to find a solution for an algorithm
 */
public class Result
{
	// The number of operations called during the solve
	private int operationCount;
	// The time it took to find the solution
	private long timeElapsed;
	
	/**
	 * Constructor
	 * 
	 * @param operationCount - the number of operations called during the solve
	 * @param timeElapsed - the time it took to find the solution
	 */
	public Result(int operationCount, long timeElapsed)
	{
		this.operationCount = operationCount;
		this.timeElapsed = timeElapsed;
	}

	/**
	 * Getter for the number of operations
	 * 
	 * @return The number of operations called during the solve
	 */
	public int getOperationCount()
	{
		return operationCount;
	}

	/**
	 * Getter for the time elapsed
	 * 
	 * @return The time it took to find the solution
	 */
	public long getTimeElapsed()
	{
		return timeElapsed;
	}
}
