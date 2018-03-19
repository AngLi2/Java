import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] perSum;
	private int trials;

	public PercolationStats(int n, int trials) {
		this.trials = trials;
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();
		Percolation p;
		perSum = new double[trials];
		for (int j = 0; j < trials; j++) {
			p  = new Percolation(n);
			for (int i = 0; i < n * n; i++) {
				int randomRow;
				int randomCol;
				do {
					randomRow = StdRandom.uniform(n) + 1;
					randomCol = StdRandom.uniform(n) + 1;
				} while (p.isOpen(randomRow, randomCol));
				p.open(randomRow, randomCol);
				if (p.percolates()) {
					perSum[j] = (double) (i + 1) / n / n;
					break;
				}
			}
		}
		// perform trials independent experiments on an n-by-n grid

	}

	public double mean() {
		return StdStats.mean(perSum);
	}

	public double stddev() {
		return StdStats.stddev(perSum);
		// sample standard deviation of percolation threshold
	}

	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
		// low endpoint of 95% confidence interval
	}

	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
		// high endpoint of 95% confidence interval
	}

	public static void main(String[] args) {
		PercolationStats per = new PercolationStats(10, 100);
		System.out.println(per.mean());
		System.out.println(per.stddev());
	}
}
