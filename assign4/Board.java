package assign4;
import algs4.Bag;
import stdlib.In;

/**
 * Created by VGN on 7/17/15.
 */
public class Board {
    private final int [] grid;
    private final int size;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        grid = new int[blocks.length * blocks.length];
        size = blocks.length;
        int counter =0;
        for (int i=0; i < blocks.length; i++){
            for (int j=0; j < blocks.length; j++){
                this.grid[counter++] = blocks[i][j];
            }
        }
    }

    // board dimension N
    public int dimension()  {
        return size;
    }

    // number of blocks out of place
    public int hamming()   {
        int outOfPlace = 0;
        //dont look at last position
        for (int i=0; i< grid.length-1; i++){
            if (grid[i] != i+1){
                outOfPlace++;
            }
        }
        return outOfPlace;
    }

    //sum of Manhattan distances between blocks and goal
    public int manhattan()  {
        int totDist = 0;
        int deltaRow = 0;
        int deltaCol = 0;
        for (int i=0; i< grid.length; i++){
            if (grid[i] == 0){
                continue;
            }
            if (grid[i] != i+1){
                int expectedRow = (grid[i]-1)/this.size;
                int expectedCol = (grid[i]-1)%this.size;
                int actualRow = i/this.size;
                int actualCol = i%this.size;
                if (expectedRow >= actualRow){
                    deltaRow = expectedRow - actualRow;
                }
                else{
                    deltaRow = actualRow - expectedRow;
                }
                if (expectedCol >= actualCol){
                    deltaCol = expectedCol - actualCol;
                }
                else{
                    deltaCol = actualCol - expectedCol;
                }
                totDist += deltaRow + deltaCol;
                deltaRow = 0;
                deltaCol = 0;
            }

        }
        return totDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    public Board twin(){
        for (int i=0; i < grid.length; i++){
            //no blankpos in adjacent pos and adj pos belong to same row
            if (grid[i] != 0 && grid[i+1] != 0 && i/this.size == (i+1)/this.size){
                return this.createBoard(grid, i, i+1);
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.grid.length != that.grid.length){
            return false;
        }
        for(int i=0; i < this.grid.length; i++){
            if (this.grid[i] != that.grid[i]){
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Bag<Board> queueBag = new Bag<Board>();
        int blankPos = computeBlankSquarePos();
        int[] neighbors = computeNeighbors(blankPos);
        for (int neighbor: neighbors){
            if (neighbor != -1){
               queueBag.add(this.createBoard(this.grid,blankPos, neighbor));
            }
        }
        return queueBag;
    }

    private Board createBoard(int[] grid, int pos1, int pos2){
        //create a new board by swapping pos1 and pos2 from previous board
        int[][] blocks = new int[size][size];
        int counter = 0;
        for (int i = 0; i < size; i++){
            for (int j=0; j < size; j++){
                if (counter == pos1){
                    blocks[i][j] = grid[pos2];
                }
                else if (counter == pos2){
                    blocks[i][j] = grid[pos1];
                }
                else
                    blocks[i][j] = grid[counter];
                counter ++;
            }
        }
        return new Board(blocks);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.size + "\n");
        int row = 0;
        for (int i = 0; i < this.grid.length; i++) {
                int newrow = i/size;
                if (newrow != row) {
                    s.append("\n");
                    row = newrow;
                }
                s.append(String.format("%2d ", grid[i]));

        }
        return s.toString();
    }

    private int computeBlankSquarePos(){
        for (int i=0; i < this.grid.length; i++){
            if (this.grid[i] == 0){
                return i;
            }
        }
        return -1;
    }

    private int[] computeNeighbors(int pos){
        int left = pos -1;
        int right = pos + 1;
        int top = pos - size;
        int bottom = pos + size;
        int row = pos/size;
        int col = pos % size;
        if (row == 0){
            //no top
            top = -1;
        }
        if (row == size -1){
            //no bottom
            bottom = -1;
        }
        if (col == 0){
            //no left
            left = -1;
        }
        if (col == size -1){
            //no right
            right = -1;
        }
        int[] neighbors = new int[]{left,right,top,bottom};
        return neighbors;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int contents[][] = { {5,1,8} , { 2,7,3},{4,0,6} };
        Board newBoard = new Board(contents);
        System.out.println(newBoard);
        System.out.println(newBoard.isGoal());
        System.out.println(newBoard.hamming());
        System.out.println(newBoard.manhattan());


    }
}