package assign9;

import assign9.testfiles.BoggleBoard;
import org.testng.annotations.Test;
import stdlib.In;

import static org.testng.Assert.assertTrue;

/**
 * Created by VGN on 12/31/15.
 */
public class BoggleSolverTest {

    @Test
    public void test1(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-dodo.txt");
        assertTrue(getSize(b) == 5);
    }

    @Test
    public void test2(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-noon.txt");
        assertTrue(getSize(b) == 3);
    }

    @Test
    public void test3(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-couscous.txt");
        assertTrue(getSize(b) == 12);
    }

    @Test
    public void test4(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-rotavator.txt");
        assertTrue(getSize(b) == 25);
    }

    @Test
    public void test5(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-estrangers.txt");
        assertTrue(getSize(b) == 50);
    }

    @Test
    public void test6(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-antidisestablishmentarianisms.txt");
        assertTrue(getSize(b) == 40);
    }

    @Test
    public void test7(){
        Iterable<String> b = boggleSolver("dictionary-yawl.txt", "board-dichlorodiphenyltrichloroethanes.txt");
        System.out.println(getSize(b));
        assertTrue(getSize(b) == 27);
    }

    private int getSize(Iterable<String> b){
        int count = 0;
        for (String word: b){
            count+=1;
        }
        return count;
    }

    private Iterable<String> boggleSolver(String dictionary, String board){
        String workingDirectory = System.getProperty("user.dir");
        String pathToTest = "/src/assign9/testfiles/";
        workingDirectory += pathToTest;
        String boardFullPath = workingDirectory + board;
        String dictFullPath = workingDirectory + dictionary;
        BoggleBoard boggleBoard = new BoggleBoard(boardFullPath);
        In in = new In(dictFullPath);
        String[] dict = in.readAllStrings();
        BoggleSolver b = new BoggleSolver(dict);
        return b.getAllValidWords(boggleBoard);
    }
}
