import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	//instance variables
	private static double[] total_trails;
	private static int T = 0;
	
	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		
		// Throw exception if n <= 0 or trials <=0
		if(n<=0 || trials<=0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		//initialize percolation object to perform independent trails
		for(int t=0; t<trials; ++t) {
			Percolation p = new Percolation(n);
			while(true) {
				if(p.percolates()) {
					total_trails[t] = p.numberofOpenSites()/(double)(n*n);
					break;
				}
				//pick a random site to open
				int rand_site = StdRandom.uniform(n*n);
				for(int row=0; row<n; ++row) {
					for(int col=0; col<n; ++col) {
						if( ((row*n) + col) == rand_site) { p.open(row+1, col+1); }
					}
				}
			}
		}	
	}
	
	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(total_trails);
	}
	
	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(total_trails);
	}
	
	public double confidenceLo() {
		// low  endpoint of 95% confidence interval
		double xbar = mean();
		double sd = stddev();
		return xbar - ((1.96*sd)/Math.sqrt(T));
	}
	
	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		double xbar = mean();
		double sd = stddev();
		return xbar + ((1.96*sd)/Math.sqrt(T));
	}

	public static void main(String[] args) {
		// test client (described below)
		int n = Integer.parseInt(args[0]);
		T = Integer.parseInt(args[1]);
		PercolationStats pstats = new PercolationStats(n, T);
		System.out.println("mean                    = " + pstats.mean());
		System.out.println("stddev                  = " + pstats.stddev());
		System.out.println("95% confidence interval = [" + pstats.confidenceLo() + ", " + pstats.confidenceHi() + "]");
	}
}