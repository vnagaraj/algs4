package assign4;
import algs4.MinPQ;
import algs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * Created by VGN on 7/21/15.
 */
public class Solver {
    private Stack<Board> boardSequence;
    private boolean solved = false;
    private int moves ;
    // find a boardSequence to the initial board (using the A* algorithm)
    public Solver(Board initial)    {
        //storing sequence of board moves in final boardSequence
        boardSequence = new Stack<Board>();
        //data structure for storing search nodes and extracting the ones with min manhattan function
        MinPQ<SearchNode> minPq = new MinPQ<SearchNode>();
        SearchNode root = new SearchNode(initial);
        minPq.insert(root);
        SearchNode dequeuedSearchNode = minPq.delMin();
        // repeating for twin
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        SearchNode rootTwin = new SearchNode(initial.twin());
        minPQTwin.insert(rootTwin);
        SearchNode dequeuedSearchNodeTwin = minPQTwin.delMin();
        while (!dequeuedSearchNode.board.isGoal()
                && !dequeuedSearchNodeTwin.board.isGoal() )
               {
            dequeuedSearchNode = this.computeASearch(minPq, dequeuedSearchNode);
            //repeating for twin
            dequeuedSearchNodeTwin =this.computeASearch(minPQTwin, dequeuedSearchNodeTwin);
        }
        if (dequeuedSearchNode.board.isGoal()) {
            solved = true;
            moves = dequeuedSearchNode.moves;
            computeBoardSequence(boardSequence, dequeuedSearchNode);
        }
        else
            moves = -1;

    }

    private void computeBoardSequence(Stack<Board> boardSequence, SearchNode searchNode) {
        while (searchNode != null){
            boardSequence.push(searchNode.board);
            searchNode = searchNode.previous;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()  {
        return moves;
    }

    // sequence of boards in a shortest boardSequence; null if unsolvable
    public Iterable<Board> solution()   {
        if (this.solved){
            return this.boardSequence;
        }
        return null;
    }

    private SearchNode computeASearch(MinPQ<SearchNode> pq, SearchNode searchNode){
        for (Board neighbor : searchNode.board.neighbors()){
            SearchNode neighborSearchNode = new SearchNode(neighbor);
            neighborSearchNode.moves = searchNode.moves + 1;
            neighborSearchNode.previous = searchNode;
            //critical optimization step
            if (searchNode.previous != null){
                if (neighborSearchNode.board.equals(searchNode.previous.board)){
                    continue;
                }
            }
            pq.insert(neighborSearchNode);
        }
        return pq.delMin();
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
            // create initial board from file
            In in = new In(args[0]);
            int N = in.readInt();
            int[][] blocks = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    blocks[i][j] = in.readInt();
            Board initial = new Board(blocks);

            // solve the puzzle
            Solver solver = new Solver(initial);

            // print boardSequence to standard output
            if (!solver.isSolvable())
                StdOut.println("No boardSequence possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
      }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves ;
        SearchNode previous = null;

        SearchNode(Board board){
            this.board = board;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.board.manhattan() + moves < o.board.manhattan() + o.moves){
                return -1;
            }
            if (this.board.manhattan() + moves > o.board.manhattan() + o.moves){
                return 1;
            }
            return 0;
        }
    }
}
