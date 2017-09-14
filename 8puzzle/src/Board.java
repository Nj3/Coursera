
public class Board {
	
	private char[] bord; // used char array instead of 2d array to minimize memory utilization.
	private int N;
	/*
	 * construct a board from an n-by-n array of blocks
	 * where blocks[i][j] = block in row i, column j
	 */
	public Board(int[][] blocks) {
		N = blocks.length;
		bord = new char[N*N];
		int k = 0;
		for ( int i=0; i<N; ++i ) {
			for ( int j=0; j<N; ++j ) {
				bord[k++] = (char) blocks[i][j];
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
			if ( bord[i] != '0' && i != Character.getNumericValue(bord[i]) - 1 ) blockOutOfPosition++;
		}
		return blockOutOfPosition;
	}
	
	/*
	 * sum of Manhattan distances between blocks and goal
	 */
	public int manhattan() {
		int manhattanDistance = 0;
		for ( int i=0; i<N*N; ++i ) {
			// No need to calculate manhattan distance for blank block denoted by '0'
			if ( bord[i] == '0' ) continue;
			int elemDist = 0;
			// 2D array index conversion
			int a = i / N;
			int b = i % N;
			// Desired 2d Array index
			int val = Character.getNumericValue(bord[i]) - 1;
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
		
	}
	/*
	 * a board that is obtained by exchanging any pair of blocks
	 */
	public Board twin() {
		
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * does this board equal y?
	 */
	public boolean equals(Object y) {
		
	}
	/*
	 * all neighboring boards
	 */
	public Iterable<Board> neighbors() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * string representation of this board (in the output format specified below)
	 */
	public String toString() {
		
	}
	
}
