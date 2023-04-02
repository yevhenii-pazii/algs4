import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final int fieldSize;
    private int openTiles = 0;
    private final boolean[][] field; // true means open, false - closed/full
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        this.n = n;
        fieldSize = n * n;
        field = new boolean[n][n];
        uf = new WeightedQuickUnionUF(fieldSize + 2);
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(fieldSize + 1 - i, fieldSize + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            field[row - 1][col - 1] = true;
            openTiles++;
            int currentIndex = index(row, col);

            if (col - 1 > 0 && isOpen(row, col - 1)) {
                uf.union(currentIndex, index(row, col - 1));
            }

            if (col + 1 <= n && isOpen(row, col + 1)) {
                uf.union(currentIndex, index(row, col + 1));
            }

            if (row - 1 > 0 && isOpen(row - 1, col)) {
                uf.union(currentIndex, index(row - 1, col));
            }

            if (row + 1 <= n && isOpen(row + 1, col)) {
                uf.union(currentIndex, index(row + 1, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        return field[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.find(0) == uf.find(index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openTiles;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(fieldSize + 1);
    }

    private int index(int row, int col) {
        return (row - 1) * n + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(1);

        //int[][] tilesToOpen = {{1, 2}, {2, 1}, {3, 1}, {2, 2}};
        int[][] tilesToOpen = {{1, 1}};

        System.out.println(percolation.percolates());
        for (int[] point : tilesToOpen) {
            System.out.println("open: " + point[0] + ", " + point[1]);
            percolation.open(point[0], point[1]);
            System.out.println("Percolates -> " + percolation.percolates());
            System.out.println();
        }

        System.out.println();
    }
}
