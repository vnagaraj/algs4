package assign6;

import org.testng.annotations.Test;

/**
 * Created by VGN on 12/10/15.
 */
public class WordNetTest {

    @Test ( expectedExceptions = { IllegalArgumentException.class} )
    public void test1(){
        this.wordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
    }

    @Test ( expectedExceptions = { IllegalArgumentException.class} )
    public void test2(){
        this.wordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");

    }

    @Test ( expectedExceptions = { IllegalArgumentException.class} )
    public void test3(){
        this.wordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt");

    }


    @Test ( expectedExceptions = { IllegalArgumentException.class} )
    public void test4(){
        this.wordNet("synsets6.txt", "hypernyms6InvalidCycle.txt");

    }

    @Test ( expectedExceptions = { IllegalArgumentException.class} )
    public void test5(){
        this.wordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt");

    }

    @Test
    public void test6(){
        this.wordNet("synsets.txt", "hypernyms.txt");

    }

    @Test
    public void test7(){
        this.wordNet("synsets15.txt", "hypernyms15Path.txt");

    }

    @Test
    public void test8(){
        this.wordNet("synsets15.txt", "hypernyms15Tree.txt");

    }

    @Test
    public void test9(){
        this.wordNet("synsets6.txt", "hypernyms6TwoAncestors.txt");

    }

    @Test
    public void test10(){
        this.wordNet("synsets11.txt", "hypernyms11AmbiguousAncestor.txt");

    }

    @Test
    public void test11(){
        this.wordNet("synsets8.txt", "hypernyms8WrongBFS.txt");

    }

    @Test
    public void test12(){
        this.wordNet("synsets11.txt", "hypernyms11ManyPathsOneAncestor.txt");

    }

    @Test
    public void test13(){
        this.wordNet("synsets8.txt", "hypernyms8ManyAncestors.txt");

    }

    @Test
    public void test14(){
        WordNet wordNet = this.wordNet("synsets.txt", "hypernyms.txt");
        String nounA = "fertilization_age";
        String nounB = "legal_transfer";
        int distance = wordNet.distance(nounA, nounB);
        System.out.println("distance " + distance);
        String sap = wordNet.sap(nounA, nounB);
        System.out.println("sap " + sap);

    }

    private WordNet wordNet(String fileName1, String fileName2){
        String workingDirectory = System.getProperty("user.dir");
        String pathToTest = "/src/assign6/testfiles/";
        workingDirectory += pathToTest;
        String fileName1FullPath = workingDirectory + fileName1;
        String fileName2FullPath  = workingDirectory + fileName2;
        return new WordNet(fileName1FullPath, fileName2FullPath);
    }


}
