import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int n;
	private boolean[][] opens;
	private final WeightedQuickUnionUF uf;

	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		// create n-by-n grid, with all sites blocked
		this.n = n;
		uf = new WeightedQuickUnionUF(n * n + 2);
		opens = new boolean[n][n];
	}

	private int to1D(int row, int col) {
		return n * row + col;
	}

	public void open(int row, int col) {
		if (row < 1 || col < 1 || row > n || col > n)
			throw new IllegalArgumentException();
		int r = row - 1;
		int c = col - 1;
		opens[r][c] = true;
		if (r == 0)
			uf.union(n * n, to1D(r, c));
		if (r == n - 1)
			uf.union(n * n + 1, to1D(r, c));
		if (c != 0 && isOpen(row, col - 1))
			uf.union(to1D(r, c), to1D(r, c - 1));
		if (c != n - 1 && isOpen(row, col + 1))
			uf.union(to1D(r, c), to1D(r, c + 1));
		if (r != 0 && isOpen(row - 1, col))
			uf.union(to1D(r - 1, c), to1D(r, c));
		if (r != n - 1 && isOpen(row + 1, col))
			uf.union(to1D(r + 1, c), to1D(r, c));
		// open site (row, col) if it is not open already
	}

	public boolean isOpen(int row, int col) {
		if (row < 1 || col < 1 || row > n || col > n)
			throw new IllegalArgumentException();
		int r = row - 1;
		int c = col - 1;
		return opens[r][c];
		// is site (row, col) open?
	}

	public boolean isFull(int row, int col) {
		if (row < 1 || col < 1 || row > n || col > n)
			throw new IllegalArgumentException();
		int r = row - 1;
		int c = col - 1;
		return (uf.connected(to1D(r, c), n * n));
		// is site (row, col) full?
	}

	public int numberOfOpenSites() {
		int count = 0;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (isOpen(i, j))
					count++;
		return count;
		// number of open sites
	}

	public boolean percolates() {
		// does the system percolate?
		// for (int i = 0; i < n; i++)
		// if (uf.connected(n * n, to1D(n - 1, i)))
		// return true;
		// return false;
		return uf.connected(n * n, n * n + 1);
	}

	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		p.open(3, 1);
		p.open(2, 1);
		p.open(1, 1);
		System.out.println(p.percolates());
		p.open(3, 3);
		System.out.println(p.isFull(3, 3));
		// test client (optional)
	}
}
