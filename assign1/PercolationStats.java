package assign1;

import stdlib.StdRandom;
import stdlib.StdStats;
import stdlib.Stopwatch;
public class PercolationStats {
    private double[] threshold;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <=0 || T<=0)
            throw new IllegalArgumentException("out of bounds");
        int t = 0;
        threshold = new double[T];
        while (t < T) {
            double sitecount = 0;
            boolean[][] sites = new boolean[N][N];
            Percolation p = new Percolation(N);
            // as long as system does not percolate
            while (!p.percolates()) {
                //generates random no in range from 1-N (inclusive)
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                if (!sites[row-1][col-1]) {
                    //System.out.println("Site count " + sitecount);
                    sitecount += 1;
                    sites[row-1][col-1] = true;
                    //System.out.println("Row " + row);
                    //System.out.println("Column " + col);
                    p.open(row, col);
                }
            }
            double percolationThreshold = sitecount / (N * N);
            threshold[t] = percolationThreshold;
            t += 1;
        }

    }

    // sample mean of testfiles threshold
    public double mean()
    {
        return StdStats.mean(this.threshold);
    }

    // sample standard deviation of testfiles threshold
    public double stddev()
    {

        return StdStats.stddev(this.threshold);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return (mean() -(1.96* stddev())/Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return (mean() +(1.96* stddev())/Math.sqrt(threshold.length));
    }

    // test client (described below)
    public static void main(String[] args)
    {

        Stopwatch stopwatch = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);
        System.out.println("mean          = "+ percolationStats.mean());
        System.out.println("stddev        = "+ percolationStats.stddev());
        System.out.println("95% conf interval = " + percolationStats.confidenceLo()
                +", " + percolationStats.confidenceHi());
        System.out.println("Elapsed time " + stopwatch.elapsedTime());
    }
}