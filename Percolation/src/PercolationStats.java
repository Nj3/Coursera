import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private int[] xt; //xt is the array which contains number of open sites when system percolates
	private double xbar; //mean
	private double sd; //standard deviation
	
	public PercolationStats(int n, int trails) {
		// Throw exception if n <= 0 or trails <=0
		if(n<=0 || trails<=0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		//call percolation with input as n and call it trail times. record each o/p of call in an array xt
	}
	
	public double mean() {
	   // sample mean of percolation threshold
	}
	
	public double stddev() {
	   // sample standard deviation of percolation threshold
	}
	
	public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
	}
	
	public double confidenceHi()  {
	   // high endpoint of 95% confidence interval
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats pstats = new PercolationStats(n, T);
	}

}
