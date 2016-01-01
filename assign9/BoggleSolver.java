package assign9;
import algs4.Queue;
import assign9.testfiles.BoggleBoard;
import stdlib.In;
import stdlib.StdOut;

import java.util.HashSet;
/**
 * Created by VGN on 12/31/15.
 */
public class BoggleSolver
{
    private final BoggleTrie dictionary =  new BoggleTrie(); //trie to store dictionary words

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        for (String word: dictionary){
            this.dictionary.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        HashSet<String> words = new HashSet<String>();
        boolean[] visited =  new boolean[board.rows()*board.cols()];
        StringBuffer sb = new StringBuffer();
        for(int row=0; row< board.rows(); row++){
            for(int col=0; col<board.cols(); col++){
                this.recursiveDFSIteration(row, col, visited, board, sb, words);
            }
        }
        return words;
    }

    private void recursiveDFSIteration(int row, int col, boolean[] visited, BoggleBoard board, StringBuffer sb, HashSet<String> words){
        int pos = this.getPosition(board, row, col);
        visited[pos] = true;
        char ch = board.getLetter(row, col);
        if (ch == 'Q'){
            sb.append("QU");
        }
        else {
            sb.append(ch);
        }
        if (!this.dictionary.containsPrefix(sb.toString())){
            visited[pos] = false;
            if (ch != 'Q')
                sb.deleteCharAt(sb.length()-1);
            else
                sb.delete(sb.length()-2, sb.length());
            return;
        }
        if(sb.length() >=3 && this.dictionary.contains(sb.toString()) ){
            //System.out.println(sb.toString());
            words.add(sb.toString());
        }
        for(int[] neighborPos: this.getAdjacentDices(board, row, col)){
            if (!visited[this.getPosition(board, neighborPos[0], neighborPos[1])]){
                recursiveDFSIteration(neighborPos[0], neighborPos[1], visited, board, sb, words);
            }
        }
        //System.out.println(sb.toString());
        visited[pos] = false;
        if (ch != 'Q')
            sb.deleteCharAt(sb.length()-1);
        else
            sb.delete(sb.length()-2, sb.length());


    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if (!this.dictionary.contains(word)){
            return 0;
        }
        if (word.length() <=2){
            return 0;
        }
        if (word.length() <=4){
            return 1;
        }
        if (word.length() == 5){
            return 2;
        }
        if (word.length() == 6){
            return 3;
        }
        if (word.length() == 7){
            return 5;
        }
        return 11;
    }

    private int[] getDim(BoggleBoard board, int row, int col){
        if (row >=0 && row < board.rows() && (col >=0 && col < board.cols())){
            return new int[]{row, col};
        }
        return null;
    }

    private Iterable<int[]> getAdjacentDices(BoggleBoard board, int row, int col){
        Queue<int[]> adjDices = new Queue<int[]>();
        int[] up = this.getDim(board, row-1, col);
        if (up != null){
            adjDices.enqueue(up);
        }
        int[] down = this.getDim(board, row+1, col);
        if (down != null){
            adjDices.enqueue(down);
        }
        int[] left = this.getDim(board, row, col-1);
        if (left != null){
            adjDices.enqueue(left);
        }
        int[] right = this.getDim(board, row, col+1);
        if (right != null){
            adjDices.enqueue(right);
        }
        int[] upLeftDiag = this.getDim(board, row-1, col-1);
        if (upLeftDiag != null){
            adjDices.enqueue(upLeftDiag);
        }
        int [] upRightDiag = this.getDim(board, row-1, col+1);
        if (upRightDiag != null){
            adjDices.enqueue(upRightDiag);
        }
        int[] downLeftDiag = this.getDim(board, row+1, col-1);
        if (downLeftDiag != null){
            adjDices.enqueue(downLeftDiag);
        }
        int[] downRightDiag = this.getDim(board, row+1, col+1);
        if (downRightDiag != null){
            adjDices.enqueue(downRightDiag);
        }
        return adjDices;
    }

    private int getPosition(BoggleBoard board, int row, int col){
        return (board.cols() * row)  + col;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }
}
