import edu.princeton.cs.algs4.StdIn;

import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	public static int N = 0;
	private int[][] grid;
	private static int[][] site;
	WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N*N);

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		
		if( n <= 0 ) {
			throw new java.lang.IllegalArgumentException();
		}
		
		int[][] tmp = new int[n][n];

		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				tmp[i][j] = 1;
			}
		}
		
		// create another n-by-n grid which tells the name of the site with numbers from 0 to N*N - 1
		int[][] temp_site = new int[n][n];
		int name = 0;
		for(int i = 0; i<n; ++i) {
			for(int j = 0; j<n; ++j) {
				temp_site[i][j] = name++;
			}
		}
		grid = tmp;
		site = temp_site;
	/*	
		// ensuring all sites are blocked.
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				System.out.print(grid[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		System.out.println("----------------------------------");
		//checking the name of the sites
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				System.out.print(site[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		System.out.println("----------------------------------"); */		
	}
	/*
	public void printit() {
		// ensuring all sites are blocked.
		for(int i = 0; i < N; ++i) {
			for(int j = 0; j < N; ++j) {
				System.out.print(grid[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		System.out.println("----------------------------------");		
		
	}
	*/
	
	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		// r, c are spatial co-ords for 2d array
		int r = row - 1;
		int c = col - 1;
		
		//curr_site holds the positional value in the  union array.
		int curr_site = site[r][c];
		
		//iterate the first row and check if its connected. If yes, return true else false
		for( int i=1; i<=N; ++i ) {
			if(isOpen(1,i)) {
				if(uf.connected(i-1, curr_site)) {
					return true;
				}
			}
			}
		return false;
		}
	
	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		if( grid[row-1][col-1] == 1 ) {
			return false;
		}else {
			return true;
		}		
	}

	public     int numberOfOpenSites() {
		// number of open sites
		int count = 0;
		
		for(int i = 0; i<N; ++i) {
			for(int j=0; j<N; ++j) {
				if(grid[i][j] == 0) {
					++count;
				}
			}
		}
		return count;
	}
	
	
	public    void open(int row, int col) {
		// open site (row, col) if it is not open already
		
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		
		//local variable, r & c to point the array spot
		int r = row -1;
		int c = col -1; 
		grid[r][c] = 0;
		
		//check if adjacent sites is open and do union
		
		//System.out.println("perform union with adjacent sides and print yes/no if itsopen");
		if( c-1 >= 0 && grid[r][c-1] == 0 ) { //left side
			uf.union(site[r][c], site[r][c-1]);
			//System.out.println(uf.connected(site[r][c], site[r][c-1]));
		} 
		if( c+1 <= N-1 && grid[r][c+1] == 0 ) { //right side
			uf.union(site[r][c], site[r][c+1]);
			//System.out.println(uf.connected(site[r][c], site[r][c+1]));
		} 
		if( r-1 >= 0 && grid[r-1][c] == 0 ) { //up 
			uf.union(site[r][c], site[r-1][c]);
			//System.out.println(uf.connected(site[r][c], site[r-1][c]));
		}
		if( r+1 <= N-1 && grid[r+1][c] == 0 ) { // down
			uf.union(site[r][c], site[r+1][c]);
			//System.out.println(uf.connected(site[r][c], site[r+1][c]));
		}
	}
	
	public boolean percolates() {
		// does the system percolate?
		for(int i=1; i<=N; ++i) {
			if(isOpen(N,i)) {
				if(isFull(N,i)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("Enter the size:");
		//System.out.print("\n");
		int ip = StdIn.readInt();
		N = ip;
		Percolation perc = new Percolation(N);
		while(true) {
			int rand_site = StdRandom.uniform(N*N);
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					if(site[i][j] == rand_site) {
						perc.open(i+1, j+1);
						//perc.printit();
					}
				}
			}
			if(perc.percolates()) {
				System.out.println(perc.numberOfOpenSites());
				break;
			}
		}
	}

	
}
