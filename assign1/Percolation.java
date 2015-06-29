/**
 * Created by VGN on 6/23/15.
 */
package assign1;
import algs4.WeightedQuickUnionUF;
/**
 * Created by VGN on 6/23/15.
 */
public class Percolation {
    private int gridSize;
    private boolean[] grid;    // to track open/blocked sites
    private WeightedQuickUnionUF uf; //to find/make connections to other sites
    private WeightedQuickUnionUF ufCopy; //to check for full sites


    public Percolation(int N)   {
        if (N <= 0)
            throw new IllegalArgumentException("Site value out of bounds");
        // create N-by-N grid, with all sites blocked
        gridSize = N;
        grid = new boolean[gridSize * gridSize+2]; //+2 to account for virtual sites
        uf = new WeightedQuickUnionUF(gridSize * gridSize +2);
        ufCopy = new WeightedQuickUnionUF(gridSize * gridSize +1);
        //make connection of first row with first virtual site
        //located at gridSize index
        for (int i = 0; i < N; i++) {
            uf.union(i, gridSize * gridSize);
            ufCopy.union(i, gridSize * gridSize);
        }
        // make connection of bottom row with second virtual site
        //located at gridSize index+1
        for (int i = N*(N-1); i < N*N; i++) {
            uf.union(i, gridSize * gridSize +1);
        }
    }

    public void open(int i, int j)     {
        //validating j
        validateCol(j);
        // open site (row i, column j) if it is not open already
        int gridPos = this.convToGridPos(i, j);
        //mark site as open
        this.grid[gridPos] = true;
        // connect to open neighbors
        for (int neighbor: this.computeNeighbors(gridPos)) {
            //check if valid neighbor
            if (neighbor != -1) {
                // check if neighbor is open
                if (this.grid[neighbor]) {
                    this.uf.union(gridPos, neighbor);
                    this.ufCopy.union(gridPos, neighbor);
                }
            }
        }
    }

    private int convToGridPos(int i, int j) {
        int pos = (i-1) * gridSize + (j-1);
        if (pos < 0 || pos >= gridSize * gridSize) {
            //System.out.println("pos "+ pos);
            throw new java.lang.IndexOutOfBoundsException("indexes exception");
        }
        return pos;
    }

    private int[] computeNeighbors(int gridPos) {
        int[] neighbors = new int[4];
        if (gridPos % gridSize == 0) {
            // leftmost pos in 2D array
            neighbors[0] = -1; // implying no left neighbor
        }
        else
            neighbors[0] = gridPos -1;
        if (gridPos % gridSize == gridSize -1) {
            //right most pos in 2 D array
            neighbors[1] = -1;
        }
        else
            neighbors[1] = gridPos + 1;
        if (gridPos + gridSize >= gridSize * gridSize) {
            //no bottom neighbor
            neighbors[2] = -1;
        }
        else
            neighbors[2] = gridPos + gridSize;
        if (gridPos - gridSize < 0) {
            //no top neighbor
            neighbors[3] = -1;
        }
        else
            neighbors[3] = gridPos - gridSize;
        return neighbors;
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        validateCol(j);
        int gridpos = this.convToGridPos(i, j);
        return grid[gridpos];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        validateCol(j);
        int gridpos = this.convToGridPos(i, j);
        if (this.grid[gridpos])
            return this.ufCopy.connected(gridpos, gridSize*gridSize);
        //connection between gridpos and gridSize
        return false;
    }
    public boolean percolates()             // does the system percolate?
    {
        if (gridSize == 1 && !this.grid[0])
            return false;
        return this.uf.connected(gridSize * gridSize, gridSize * gridSize +1);
    }

    private void validateCol(int j) {
        if (j <= 0 || j > gridSize) {
            throw new java.lang.IndexOutOfBoundsException("indexes exception");
        }
    }

    public static void main(String[] args)  // test client (optional)
    {
        Percolation p = new Percolation(10);
        p.open(6, 12);
    }
}

