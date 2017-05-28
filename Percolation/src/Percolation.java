import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	//declare instance variables as private
	private int N = 0;
	private boolean[][] grid;
	private static WeightedQuickUnionUF uf;
	
	public Percolation(int n) {
		/* create n by n grid with all sites blocked */
		
		N = n;
		uf = new WeightedQuickUnionUF(N*N);
		
		//Exception Handling
		if( N <= 0 ) {
			throw new java.lang.IllegalArgumentException();
		}
		
		//Blocked - false, open - true. Creating a 2d array and blocking all sites
		boolean[][] tmp_grid = new boolean[n][n];
		for(int i=0; i<N; ++i) {
			for(int j=0; j<N; ++j) { tmp_grid[i][j] = false; }
		}
		
		grid = tmp_grid;
	}
	
	public void open(int row, int col) {
		/* open site(row,col) if it's not open already */
		
		//declare current_site		
		int current_site = ((row-1)*N) + (col-1);
		
		//Exception Handling
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		//is the site open?
		if(!isOpen(row, col)) {
			grid[row-1][col-1] = true;
		}
		
		//check if adjacent sites exists, If yes, check if its open. If yes, perform union, else pass.
		
		//left side
		if( col-2 >= 0 && grid[row-1][col-2] ) { uf.union(current_site, current_site-1); }
		//right side
		if( col+1 <= N && grid[row-1][col] ) { uf.union(current_site, current_site+1); }
		//up side
		if( row-2 >= 0 && grid[row-2][col-1] ) { uf.union(current_site, current_site-N); }
		//down side
		if( row+1 <= N && grid[row][col-1] ) { uf.union(current_site, current_site+N); }
		
	}
	
	public boolean isOpen(int row, int col) {
		//is site(row, col) open?
		
		//Exception Handling
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		//returns true if its opened, false if its blocked
		return grid[row-1][col-1];
	
	}
	
	public boolean isFull(int row, int col) {
		//is site (row, col) Full?
		 
		//Exception Handling
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException(); 
		}
		
		//declare current_site		
		int current_site = ((row-1)*N) + (col-1);
		
		//check whether top row sites are open and then check if its connected
		for(int i=1; i<=N; ++i) {
			if(isOpen(1,i)) {
				if(uf.connected(i-1, current_site)) { return true; }
			}
		}
		return false;
	}
	
	public int numberofOpenSites() {
		//number of open sites
		int count = 0;
		
		for(int i=0; i<N; ++i) {
			for(int j=0; j<N; ++j) {
				if(grid[i][j]) { ++count; }
			}
		}
		return count;
	}
	
	public boolean percolates() {
		//does the system percolates?
		for(int i=1; i<=N; ++i) {
			if(isOpen(N,i)) {
				if(isFull(N,i)) { return true;	}
			}
		}
		return false;
	}
	
	/*
	public static void main(String[] args) {
		//test client
		int n = 3;
		Percolation p1 = new Percolation(n);
		//uf = new WeightedQuickUnionUF(n*n);
		p1.open(1, 1);
		p1.open(2, 1);
		p1.open(2, 2);
		p1.open(3, 2);
		System.out.print(p1.percolates());
	}*/
}