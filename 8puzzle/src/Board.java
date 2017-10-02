import java.util.Objects;
import java.util.Stack;

public class Board {
	
	private final int[] bord; 
	private final int N;
	private int manhattanDist = 0;
	/*
	 * construct a board from an n-by-n array of blocks
	 * where blocks[i][j] = block in row i, column j
	 */
	public Board(int[][] blocks) {
		N = blocks.length;
		bord = new int[N*N];
		int k = 0;
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				bord[k++] = blocks[i][j];
				
				// if block is zero(i.e., blank no need to calculate manhattanDist)
				if ( blocks[i][j] == 0 ) continue;
				
				// Desired 2D position
				int val = blocks[i][j] - 1;
				int x = val / N;
				int y = val % N;
				
				if ( i==x && j==y ) continue; // Blocks are already in position
				manhattanDist += (Math.abs(i - x) + Math.abs(j - y));
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
	public int manhattan() { 
		return manhattanDist;
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
		int[][] itsTwin = convert1DTo2D(bord);
		int zeroRowPositon = findZero() / N;
		if ( zeroRowPositon == 0) {
			int swap = itsTwin[1][1];
			itsTwin[1][1] = itsTwin[1][0];
			itsTwin[1][0] = swap;
		}else {
			int swap = itsTwin[0][1];
			itsTwin[0][1] = itsTwin[0][0];
			itsTwin[0][0] = swap;
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
		else if ( getClass() != y.getClass() ) return false;
		Board that = (Board) y;
		if ( this.bord.length != that.bord.length ) return false;
		boolean flag = false;
		for ( int k=0; k<N*N; ++k ) {
			if ( Objects.equals(bord[k], that.bord[k]) ) flag = true;
			else {
				flag = false;
				break;	
			}
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
		int[][] temp = convert1DTo2D(bord);
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
	 * Creates a game board and returns it.
	 */
	private Board neighbourBoardGenerator(int[] zeroIndex, int neighbourPos) {
		int[] Index = indexConverter(neighbourPos, N);
		int[][] board = swap(zeroIndex[0], zeroIndex[1], Index[0], Index[1]);
		return new Board(board);	
	}
	
	/*
	 * Converts 1D array to 2D array.
	 */
	private int[][] convert1DTo2D(int[] oneDimenArray) {
		int[][] twoDimenArray = new int[N][N];
		int k = 0;
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				twoDimenArray[i][j] = oneDimenArray[k++];
			}
		}
		return twoDimenArray;
	}

	/*
	 * return all possible neighboring boards.
	 */
	public Iterable<Board> neighbors() {
		Stack<Board> neighbours = new Stack<Board>();
		int zeroPos = findZero();
		// 2D array index conversion for zero
		int[] zeroIndex = indexConverter(zeroPos, N);
		
		// Calculation of neighbours
		
		// Left
		if ( zeroIndex[1] - 1 >= 0 ) {
			int neighbourLeft = zeroPos - 1;
			neighbours.push(neighbourBoardGenerator(zeroIndex, neighbourLeft));
		}
		// right
		if ( zeroIndex[1] + 1 < N ) {
			int neighbourRight = zeroPos + 1;
			neighbours.push(neighbourBoardGenerator(zeroIndex, neighbourRight));
		}
		// up
		if ( zeroIndex[0] - 1 >= 0 ) {
			int neighbourUp = zeroPos - N;
			neighbours.push(neighbourBoardGenerator(zeroIndex, neighbourUp));
		}
		// down
		if ( zeroIndex[0] + 1 < N ) {
			int neighbourDown = zeroPos + N;
			neighbours.push(neighbourBoardGenerator(zeroIndex, neighbourDown));
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
