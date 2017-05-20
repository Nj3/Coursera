import edu.princeton.cs.algs4.StdIn;

//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
	
	public static int N = 0;
	public int[][] grid;
	public int[][] site;
	public int[] uf;
	
	//public boolean isFull(int row, int col); // is site (row, col) full?
	//public     int numberOfOpenSites();       // number of open sites
	//public boolean percolates();              // does the system percolate?

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
		System.out.println("----------------------------------");		
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

	public    void open(int row, int col) {
		// open site (row, col) if it is not open already
		
		if(row > N || col > N || row < 1 || col < 1) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		//local variable, r & c to point the array spot
		int r = row -1;
		int p;
		int q;
		int c = col -1;
		grid[r][c] = 0;
		//check if adjacent sites is open and do union
		//left side
		if(grid[r][c-1] == 0) {
			p = site[r][c];
			q = site[r][c-1];

		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.print("Enter the size:");
		//System.out.print("\n");
		int ip = StdIn.readInt();
		N = ip;
		Percolation perc = new Percolation(N);

	}

}
