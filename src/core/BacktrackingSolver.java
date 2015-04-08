package core;

/**
 * This class implements the Backtracking algorithm.
 */
public class BacktrackingSolver extends Solver
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
		
		// Call the algorithm method
		backtrackRecursion(puzzle, 0, 0, shipCount);
		
		// Return the number of operations
		return count;
	}
	
	/**
	 * Uses recursion to find a solution to the puzzle
	 * 
	 * It attempts to place a ship at position (x, y): first vertically, then horizontally.
	 * If that ship can be placed, then try to place another ship (recursion)
	 * 
	 * If the recursion fails, then that ship is not at the right location, 
	 * so move to the next position and try placing it again.
	 * 
	 * @param puzzle - the puzzle to be solved
	 * @param x - the x coordinate for which we are trying to place the next ship
	 * @param y - the y coordinate for which we are trying to place the next ship
	 * @param n - the number of remaining ships to place
	 * @return Whether or not the solution was found
	 */
	private boolean backtrackRecursion(Puzzle puzzle, int x, int y, int n)
	{
		// Incorrect Base Case: we've gone past the last row, return false
		if (x >= length) return false;
		
		// Correct Base Case: we've placed all the ships, return true
		if (n == 0) return true;
		
		// The next position that we will try
		int yNext = (y + 1) % length;
		int xNext = x + (yNext == 0 ? 1 : 0);
		
		// These indicate whether or not a ship was unable to be placed, so we can avoid checking bigger ships.
		// E.G. If a ship of size 2 cannot be vertically placed, then neither can a ship of size 3.
		boolean fitVertical = true;
		boolean fitHorizontal = true;
		
		/*
		 * Tries to place each size of ship, from size 1 to 4.
		 * The loop will end if there are ships that failed to fit vertically and horizontally, 
		 * since there is nothing more to check
		 */
		for (int i = 0; i < shipSizes.length && (fitVertical || fitHorizontal); i++)
		{			
			// If there is a ship of this size left...
			if (shipSizes[i] > 0)
			{
				// The ship's size is the index + 1
				int size = i + 1;
				
				// If we haven't had a ship fail the vertical placement...
				if (fitVertical)
				{
					// We count the attempt to place a ship as an operation:
					count++;
					
					// If the ship can be placed...
					if (puzzle.placeShip(x, y, size, true))
					{
						// Take away a ship of that size from the array
						shipSizes[i]--;
						
						// If a solution is found recursively, then the placement for this ship is good
						if (backtrackRecursion(puzzle, xNext, yNext, n - 1)) return true;
						// Otherwise it's a bad placement, so remove the ship and re-add to the array
						else
						{
							puzzle.removeShip(x, y, size, true);
							shipSizes[i]++;
						}
					}
					// If the ship can't be placed...
					else
					{
						// Set the flag to false
						fitVertical = false;
						// If this is a ship of size 1, then horizontal ships can't be placed either
						if (size == 1) fitHorizontal = false;
					}
				}

				// If we haven't had a ship fail the horizontal placement...
				if (size > 1 && fitHorizontal)
				{
					// We count the attempt to place a ship as an operation:
					count++;
					
					// If the ship can be placed...
					if (puzzle.placeShip(x, y, size, false))
					{
						// Take away a ship of that size from the array
						shipSizes[i]--;
						
						// The position for the next ship is moved more, since the ship now takes up space horizontally
						int yNextHorizontal = (y + size + 1) % length;
						
						// If a solution is found recursively, then the placement for this ship is good
						if (backtrackRecursion(puzzle, x + (yNextHorizontal == 0 ? 1 : 0), yNextHorizontal, n - 1)) return true;
						// Otherwise it's a bad placement, so remove the ship and re-add to the array
						else
						{
							puzzle.removeShip(x, y, size, false);
							shipSizes[i]++;
						}
					}
					// If the ship can't be placed, set the flag to false
					else fitHorizontal = false;
				}
			}
		}
		
		// We can't place a ship of any size at this location, so try the next one
		return backtrackRecursion(puzzle, xNext, yNext, n);
	}
}
