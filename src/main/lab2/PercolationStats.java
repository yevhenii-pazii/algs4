import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        double totalTiles = n * n;
        double sumOfAttemptsRatio = 0.0;
        double[] rations = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            do {
                p.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
            } while (!p.percolates());

            double ratio = p.numberOfOpenSites() / totalTiles;
            sumOfAttemptsRatio += ratio;
            rations[i] = ratio;
        }

        //mean = sumOfAttemptsRatio / trials;
        mean = StdStats.mean(rations);

//        double ssum = 0.0;
//        for (double ratio : rations) {
//            ssum += (ratio - mean) * (ratio - mean);
//        }
//        stddev = Math.sqrt(ssum / (trials - 1));

        stddev = StdStats.stddev(rations);

        double range = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - range;
        confidenceHi = mean + range;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
