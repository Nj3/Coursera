import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	// declare instance variables as private
    private int N;
	private boolean[][] grid;
	private WeightedQuickUnionUF uf;
	private int vtop;
	private int vbot;
	
	public Percolation(int n) {
		/* create n by n grid with all sites blocked */
		
		N = n;
		uf = new WeightedQuickUnionUF(N*N + 2); // n^2 + 1 is for vtop and n^2 + 2 is for vbot
		vtop = N*N;
		vbot = N*N + 1;
		
		// Exception Handling
		if (N <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		// Blocked - false, open - true. Creating a 2d array and blocking all sites
		boolean[][] tmp_grid = new boolean[n][n];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) { tmp_grid[i][j] = false; }
		}
		
		grid = tmp_grid;
	}
	
	public void open(int row, int col) {
		/* open site(row,col) if it's not open already */
		
		// declare current_site		
		int current_site = ((row-1)*N) + (col-1);
		
		// Exception Handling
		if (row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		// is the site open?
		if (!isOpen(row, col)) {
			if (row == 1) {
				grid[row-1][col-1] = true;
				uf.union(current_site, vtop);
			} else if (row == N) {
				grid[row-1][col-1] = true;
				uf.union(current_site, vbot);
			} else { grid[row-1][col-1] = true; }
		
		// check if adjacent sites exists, If yes, check if its open. If yes, perform union, else pass.
		
		// left side
		if (col-2 >= 0 && grid[row-1][col-2]) { uf.union(current_site, current_site-1); }
		// right side
		if (col+1 <= N && grid[row-1][col]) { uf.union(current_site, current_site+1); }
		// up side
		if (row-2 >= 0 && grid[row-2][col-1]) { uf.union(current_site, current_site-N); }
		// down side
		if (row+1 <= N && grid[row][col-1]) { uf.union(current_site, current_site+N); }
		}
	}
	
	public boolean isOpen(int row, int col) {
		// is site(row, col) open?
		
		// Exception Handling
		if (row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		// returns true if its opened, false if its blocked
		return grid[row-1][col-1];
	
	}
	
	public boolean isFull(int row, int col) {
		// is site (row, col) Full?
		 
		// Exception Handling
		if (row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException(); 
		}
		
		// declare current_site		
		int current_site = ((row-1)*N) + (col-1);
		
		// check whether top row sites are open and then check if its connected
		return uf.connected(vtop, current_site);
	}
	
	public int numberOfOpenSites() {
		// number of open sites
		int count = 0;
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (grid[i][j]) { ++count; }
			}
		}
		return count;
	}
	
	public boolean percolates() {
		// does the system percolates?
		return uf.connected(vtop, vbot);
	}
}