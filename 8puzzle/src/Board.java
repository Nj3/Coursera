import java.util.Stack;

public class Board {
	
	private final int[] bord; 
	private final int N;
	private final int[][] board2D;
	private Stack<Board> neighbours = new Stack<Board>();
	/*
	 * construct a board from an n-by-n array of blocks
	 * where blocks[i][j] = block in row i, column j
	 */
	public Board(int[][] blocks) {
		N = blocks.length;
		board2D = blocks;
		bord = new int[N*N];
		int k = 0;
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				bord[k++] = blocks[i][j];
			}
		}
	}
    
	/*
	 * board dimension n
	 */
	public int dimension() {
		return bord.length/N;
	}
	
	/*
	 * number of blocks out of place
	 */
	public int hamming() {
		int blockOutOfPosition = 0;
		for ( int i=0; i<N*N; ++i ) {
			if ( bord[i] != 0 && i != (bord[i] - 1) ) blockOutOfPosition++;
		}
		return blockOutOfPosition;
	}
	
	/*
	 * sum of Manhattan distances between blocks and goal
	 */
	public int manhattan() { // try compute this while constructing the game board.
		int manhattanDistance = 0;
		for ( int i=0; i<N*N; ++i ) {
			// No need to calculate manhattan distance for blank block denoted by '0'
			if ( bord[i] == 0 ) continue;
			
			int elemDist = 0;
			
			// 2D array index conversion
			int a = i / N;
			int b = i % N;
			
			// Desired 2d Array index
			int val = bord[i] - 1;
			int x = val / N;
			int y = val % N;
			
			if ( a == x && b == y) continue; // blocks are already in position.
			elemDist = Math.abs(a - x) + Math.abs(b - y);
			
			// Adding element level manhattan distance to board level manhattan distance
			manhattanDistance += elemDist;
		}
		return manhattanDistance;
	}
	
	/*
	 * is this board the goal board?
	 */
	public boolean isGoal() {
		if ( manhattan() == 0 ) return true;
		else return false;
	}
	/*
	 * a board that is obtained by exchanging any pair of blocks
	 */
	public Board twin() {
		int[][] itsTwin = new int[N][N];
		int k = 0;
		boolean flag = true;
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				if ( flag && bord[k] != 0 && bord[k+1] != 0 ) {
					// swapping non-blank blocks only once
					itsTwin[i][j] = bord[k+1];
					itsTwin[i][++j] = bord[k];
					k += 2;
					flag = false;
				}else {
					itsTwin[i][j] = bord[k];
					k++;
				}
			}
		}
		return new Board(itsTwin);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * does this board equal y?
	 */
	public boolean equals(Object y) {
		if ( y == null) return false;
		else if ( y == this ) return true;
		Board that = (Board) y;
		boolean flag = false;
		if ( that.bord.length != this.bord.length ) return false;
		for ( int k=0; k<N*N; ++k ) {
			if ( this.bord[k] != that.bord[k] ) {
				flag = false;
				break;
			}else flag = true;
		}
		return flag;
	}
	
	/*
	 * This method will return the position of zero in bord.
	 */
	private int findZero() {
		for ( int k=0; k<N*N; ++k ) {
			if ( bord[k] == 0 ) return k;
		}
		return N*N-1; // this won't be reached as ideally there will be a 0 block in the 8puzzle.
	}
	
	/*
	 * This method will accept position of zero and one of its neighbour
	 * and create a 2d array ( game board ) and return it to the caller.
	 */
	private int[][] swap(int x0, int y0, int x1, int y1) {
		int[][] temp = new int[N][N];
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				temp[i][j] = board2D[i][j];
			}
		}
		int swap = temp[x1][y1];
		temp[x1][y1] = temp[x0][y0];
		temp[x0][y0] = swap;
		return temp;
	}
	
	/*
	 * Converts 1D index to 2D index
	 */
	private int[] indexConverter(int index1D, int dimension) {
		int[] index2D = new int[2];
		index2D[0] = index1D / dimension;
		index2D[1] = index1D % dimension;
		return index2D;
	}
	
	/*
	 * Creates a game board and pushes it in neighbour Stack
	 */
	private void neighbourBoardGenerator(int[] zeroIndex, int neighbourPos) {
		int[] leftIndex = new int[2];
		leftIndex = indexConverter(neighbourPos, N);
		int[][] boardLeft = new int[N][N];
		boardLeft = swap(zeroIndex[0], zeroIndex[1], leftIndex[0], leftIndex[1]);
		neighbours.push(new Board(boardLeft));	
	}

	/*
	 * return all possible neighboring boards.
	 */
	public Iterable<Board> neighbors() {
		int zeroPos = findZero();
		int[] zeroIndex = new int[2];
		
		
		// 2D array index conversion for zero
		zeroIndex = indexConverter(zeroPos, N);
		
		// Calculation of neighbours
		
		// Left
		if ( zeroPos - 1 > 0 ) {
			int neighbourLeft = zeroPos - 1;
			neighbourBoardGenerator(zeroIndex, neighbourLeft);
		}
		// right
		if ( zeroPos + 1 < N ) {
			int neighbourRight = zeroPos + 1;
			neighbourBoardGenerator(zeroIndex, neighbourRight);
		}
		// up
		if ( zeroPos - N > 0 ) {
			int neighbourUp = zeroPos - N;
			neighbourBoardGenerator(zeroIndex, neighbourUp);
		}
		// down
		if ( zeroPos + N < N ) {
			int neighbourDown = zeroPos + N;
			neighbourBoardGenerator(zeroIndex, neighbourDown);
		}
		return neighbours;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * string representation of this board (in the output format specified below)
	 * 3
	 * 1  0  3 
	 * 4  2  5 
 	 * 7  8  6 
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for ( int k=0; k<N*N; ++k ) {
			s.append(String.format("%2d ", bord[k]));
			if ( (k + 1) % N == 0 ) s.append("\n"); 
		}
		return s.toString();
	}
	
}
