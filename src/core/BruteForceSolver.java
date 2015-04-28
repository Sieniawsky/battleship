package core;

/**
 * This class implements the Brute Force algorithm.
 */
public class BruteForceSolver extends Solver 
{
	// An array containing the number of ships for each size (from 1 to 4)
	private int[] shipSizes;
	// The number of operations
	private int count;
	// The length of the grid
	private int length;
	
	/**
	 * Runs the algorithm
	 * 
	 * @param puzzle - The puzzle to be solved
	 * @return The number of operations
	 */
	@Override
    protected int run(Puzzle puzzle) 
	{
		// Initialize variables
		shipSizes = new int[] {4, 3, 2, 1};
		count = 0;
		length = puzzle.getGrid().length;

		// Count the number of ships to place
		int shipCount = 0;
		for (int num : shipSizes) shipCount += num;

		// Counts the combinations per millisecond, averaged each second
		new Thread() {
			int c = 0;
			
			public void run()
			{
				while(true)
				{
					try
					{
						sleep(1000);
						System.out.println((count-c)/1000);
						c=count;
					}
					catch(Exception e) {}
				}
			}
		}.start();

		// Call the algorithm method
		bruteForceRecursion(puzzle, 0, 0, shipCount);

		// Return the number of operations
        return count;
    }
	
	/**
	 * Uses recursion to test all possibilities until a solution is found
	 * Each call places one ship and recursively calls to place the remaining ships.
	 * 
	 * @param puzzle - the puzzle to solve
	 * @param x - the x coordinate of the ship to place
	 * @param y - the y coordinate of the ship to place
	 * @param n - the number of ships left to place
	 * @return True if the solution was found, false otherwise
	 */
	private boolean bruteForceRecursion(Puzzle puzzle, int x, int y, int n)
	{	
		/****************************************************************
		 * This algorithm takes forever, no point in commenting further *
		 ****************************************************************/

		if (n == 0) 
		{
			count++;
			return puzzle.isValid();
		}

		for (int row = x; row < length; row++)
		{
			for (int col = y; col < length; col++)
			{
				for (int i = 0; i < shipSizes.length; i++)
				{
					int size = i + 1;
					
					if (shipSizes[i] > 0)
					{	
						if (puzzle.placeShipNoCheck(x, y, size, true))
						{
							shipSizes[i]--;

							if (bruteForceRecursion(puzzle, row, col, n - 1)) return true;
							else
							{
								puzzle.removeShip(x, y, size, true);
								shipSizes[i]++;
							}
						}

						if (puzzle.placeShipNoCheck(x, y, size, false))
						{
							shipSizes[i]--;

							if (bruteForceRecursion(puzzle, row, col + size, n - 1)) return true;
							else
							{
								puzzle.removeShip(x, y, size, false);
								shipSizes[i]++;
							}
						}
					}	
				}
			}
		}
		
		return false;
	}
}
