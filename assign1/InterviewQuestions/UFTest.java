package assign1.InterviewQuestions;

import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import static org.testng.Assert.*;





public class UFTest{

    @Test
    public void testCanonicalElement(){
        /*
        Testing find(i) returns the largest element in the connected component containing i
         */
        WeightedQuickUnionCompressionBySize uf = new WeightedQuickUnionCompressionBySize(10);
        uf.union(1,4);
        assertTrue(uf.find(1)== 4);
        assertTrue(uf.find(4)== 4);
        uf.union(2,7);
        assertTrue(uf.find(2)== 7);
        assertTrue(uf.find(7)== 7);
        uf.union(1,2);
        assertTrue(uf.find(1)== 7);
        assertTrue(uf.find(2)== 7);
    }

    @Test
    public void testSocialNetwork() {
        /**
         * Given a social network containing N members and a log file containing M timestamps at which time
         * pairs of members formed friendships, design an algorithm to determine the earliest time at
         * which all emmebers are connected
         * Running time should be MlogN and use space proportional to N
         */

        // The name of the file to open.
        String fileName = "socialnetwork.txt";

        // This will reference one line at a time
        String line = null;
        WeightedQuickUnionCompressionBySize sn = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            boolean setMembers = false;
            int members = 0;
            while((line = bufferedReader.readLine()) != null) {
                if (!setMembers) {
                    members = Integer.parseInt(line);
                    sn = new WeightedQuickUnionCompressionBySize(members);
                    setMembers = true;
                    continue;
                }
                String timeStamp = line.split("\\s+")[0];
                int member1 = Integer.parseInt(line.split("\\s+")[1]);
                int member2 = Integer.parseInt(line.split("\\s+")[2]);
                if (!sn.connected(member1, member2)){
                    sn.union(member1, member2);
                    System.out.println(member1 + " " + member2);
                    System.out.println("All members connected "+ sn.allMembersConnected());
                    if (!sn.allMembersConnected()) {
                        assertFalse(timeStamp.equals("t9"));
                    }
                    else{
                        assertTrue(timeStamp.equals("t9"));
                    }


                }

            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

}
