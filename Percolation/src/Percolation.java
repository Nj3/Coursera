//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
	           
	//public    void open(int row, int col);    // open site (row, col) if it is not open already
	//public boolean isFull(int row, int col); // is site (row, col) full?
	//public     int numberOfOpenSites();       // number of open sites
	//public boolean percolates();              // does the system percolate?

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		int[][] grid = new int[n][n];
		int val = 0;
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				grid[i][j] = val;
				++val;
			}
		}
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				System.out.print(grid[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		
	}
	
	/*public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		return True;
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("hello");
		System.out.print("\n");
		Percolation perc = new Percolation(5);
	}

}
