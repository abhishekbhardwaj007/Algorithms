
public class PercolationStats { 
	
	private int N;
	private int T;
	
	private double[] TotalOpenedSites;
	
	private int PerformPercolation(Percolation P)
	{
		int opened = 0;
		
		while (!P.percolates())
		{
			int i = StdRandom.uniform(1, N + 1);
			int j = StdRandom.uniform(1, N + 1);
			
			if (!P.isOpen(i, j))
			{
				opened++;
				P.open(i, j);
			}
		}
		
		return opened;
	}
	
	public PercolationStats(int N, int T)
	{
		Percolation P;
		
		if ((N <= 0) || (T <= 0))
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		this.N = N;
		this.T = T;
		
		TotalOpenedSites = new double[T];
		
		for (int i = 0; i < T; i++)
		{
			P = new Percolation(N);
			TotalOpenedSites[i] = (double)PerformPercolation(P) / (N * N);
		}
	}
		
	public double mean() {
		return StdStats.mean(TotalOpenedSites);
	}
	
	public double stddev() {
		
		if (this.T <= 1)
		{
			return Double.NaN;
		}
		
		return StdStats.stddev(TotalOpenedSites);
	}
	
	public double confidenceLo() {
		return (mean() - ((1.96 * stddev()) / Math.sqrt(this.T)));
	}
	
	public double confidenceHi() {
		return (mean() + ((1.96 * stddev()) / Math.sqrt(this.T)));
	}
	
	public static void main(String[] Args) {
	    PercolationStats Ps = new PercolationStats(Integer.parseInt(Args[0]), Integer.parseInt(Args[1]));
	    
		System.out.println("mean                    = " + Ps.mean());
		System.out.println("stddev                  = " + Ps.stddev());
		System.out.println("95% confidence interval = " + Ps.confidenceLo() + ", " + Ps.confidenceHi());
	}

}
