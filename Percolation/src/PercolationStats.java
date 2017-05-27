import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

	private static double[] total_trails;
	private static int n = 0;
	private static int T = 0;
	//private double xbar; //mean
	//private double sd; //standard deviation
	
	public PercolationStats(int n, int trails) {
		
		// Throw exception if n <= 0 or trails <=0
		if(n<=0 || trails<=0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		try {
			Percolation perc = new Percolation(n);
			for(int trail=0; trail<trails; ++trail) {
				while(true) {
					if(perc.percolates()) {
						//System.out.println(perc.numberOfOpenSites()/(double)(N*N));
						total_trails[trail]= perc.numberOfOpenSites()/(double)(n*n);
						//System.out.println(xt[trail]);
						break;
					}
					
					int rand_site = StdRandom.uniform(n*n);
					for(int i=0; i<n; ++i) {
						for(int j=0; j<n; ++j) {
							if(perc.site[i][j] == rand_site) {
								perc.open(i+1, j+1);
								//perc.printit();
							}
						}
					}
					
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.print(total_trails[1]);
	}
	
	public double mean() {
	   // sample mean of percolation threshold
		/*
		double sum=0;
		for(int i=0; i<T; ++i) {
			sum += total_trails[i];
		}
		return sum/T;
		*/
		return StdStats.mean(total_trails);
	}
	
	public double stddev() {
	   // sample standard deviation of percolation threshold
		/*
		double sum=0;
		double xbar = mean();
		for(int i=0; i<T; ++i) {
			sum += ((total_trails[i] - xbar) * (total_trails[i] - xbar));
		}
		return Math.sqrt(sum/(T-1));
		*/
		return StdStats.stddev(total_trails);
	}
	
	public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
		double xbar = mean();
		double s = stddev();
		return xbar - ((1.96*s)/Math.sqrt(T));
	}
	
	public double confidenceHi()  {
	   // high endpoint of 95% confidence interval
		double xbar = mean();
		double s = stddev();
		return xbar + ((1.96*s)/Math.sqrt(T));
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//n = 200;
		//T = 100;
		n = Integer.parseInt(args[0]);
		T = Integer.parseInt(args[1]);
		PercolationStats pstats = new PercolationStats(n, T);
		//System.out.println(n);
		System.out.println("the mean = " + pstats.mean());
		System.out.println("The standard deviation = " + pstats.stddev());
		System.out.println("95% confidence interval = [" + pstats.confidenceLo() + "," + pstats.confidenceHi() + "]" );
	}

}
