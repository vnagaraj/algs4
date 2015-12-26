package assign8;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotNull;

/**
 * Created by VGN on 12/26/15.
 */
public class BaseBallEliminationTest {

    @Test
    public void test_teams4(){
       baseballElimination("teams4.txt", "Philadelphia");
    }

    @Test
    public void test_teams4a(){
        baseballElimination("teams4a.txt", "Ghaddafi");
    }

    @Test
    public void test_teams5(){
        baseballElimination("teams5.txt", "Detroit");
    }

    @Test
    public void test_teams7(){
        baseballElimination("teams7.txt", "Ireland");
    }

    @Test
    public void test_teams24(){
        baseballElimination("teams24.txt", "Team13");
    }

    @Test
    public void test_teams32(){
        String[] result = {"Team25", "Team29"};
        for (String t: result) {
            baseballElimination("teams32.txt", t);
        }
    }

    @Test
    public void test_teams36(){
        baseballElimination("teams36.txt", "Team21");
    }

    @Test
    public void test_teams42(){
        String[] result = {"Team6", "Team15", "Team25"};
        for (String t: result) {
            baseballElimination("teams42.txt", t);
        }
    }

    @Test
    public void test_teams48(){
        String[] result = {"Team6", "Team23", "Team47"};
        for (String t: result) {
            baseballElimination("teams48.txt", t);
        }
    }

    @Test
    public void test_teams54(){
        String[] result = {"Team3", "Team29", "Team37", "Team50"};
        for (String t: result) {
            baseballElimination("teams54.txt", t);
        }
    }

    private void baseballElimination(String fileName, String team){
        String workingDirectory = System.getProperty("user.dir");
        String pathToTest = "/src/assign8/testfiles/";
        workingDirectory += pathToTest;
        String fileNameFullPath = workingDirectory + fileName;
        BaseballElimination baseballElimination = new BaseballElimination(fileNameFullPath);
        for (String t: baseballElimination.teams()){
            if (t.equals(team)){
                assertTrue(baseballElimination.isEliminated(team));
                assertNotNull(baseballElimination.certificateOfElimination(team));
            }
        }
    }
}
