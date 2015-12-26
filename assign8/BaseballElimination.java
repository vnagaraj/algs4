package assign8;
import algs4.FlowNetwork;
import algs4.FordFulkerson;
import algs4.Queue;
import stdlib.In;
import stdlib.StdOut;
import algs4.FlowEdge;

import java.util.HashMap;

/**
 * Created by VGN on 12/24/15.
 */
public class BaseballElimination {
    private HashMap<String, Integer> teamMap ; //maps team to index to look in other arrays
    private HashMap<Integer, String> indexMap ;//maps index to team for iteration(certifcate of elimination)
    private int[] win ;
    private int[] loss;
    private int[] remaining;
    private int[][] games;
    private int teamCount;
    private int totCapacity;

    /*
        create a baseball division from given filename in format specified below
    */
    public BaseballElimination(String filename) {
        teamMap = new HashMap<String, Integer>();
        indexMap = new HashMap<Integer, String>();
        In in = new In(filename);
        teamCount = Integer.parseInt(in.readLine());
        win = new int[teamCount];
        loss = new int[teamCount];
        remaining = new int[teamCount];
        games = new int[teamCount][teamCount];
        int count = 0;
        int stringCount =0;
        while (count < teamCount){
           String[] lines = in.readLine().split("\\s+");
           //special case when space in first position
           if (lines[0].length() == 0){
               stringCount++;
           }
           String team = lines[stringCount++];
           int w = Integer.parseInt(lines[stringCount++]);
           int l = Integer.parseInt(lines[stringCount++]);
           int r = Integer.parseInt(lines[stringCount++]);

           teamMap.put(team, count);
           indexMap.put(count , team);
           win[count] = w;
           loss[count] = l;
           remaining[count] = r;
           for (int counter=0; counter< teamCount; counter++){
               games[count][counter] = Integer.parseInt(lines[stringCount++]);
           }
           count +=1;
           stringCount=0;
        }

    }

    /*
        number of teams
    */
    public int numberOfTeams() {
        return this.teamCount;
    }

    /*
        all teams
     */
    public Iterable<String> teams() {
        return this.teamMap.keySet();
    }

    /*
        number of wins for given team
     */
    public int wins(String team) {
        int index = getTeamIndex(team);
        return win[index];
    }

    /*
        number of losses for given team
    */
    public int losses(String team)   {
       int index = getTeamIndex(team);
       return loss[index];
    }

    /*
    number of remaining games for given team
    */
    public  int remaining(String team) {
        if (this.teamMap.containsKey(team)){
            int index = teamMap.get(team);
            return this.remaining[index];
        }
        throw new IllegalArgumentException("Invalid team");
    }

    /*
     number of remaining games between team1 and team2
     */
    public int against(String team1, String team2){
        if (this.teamMap.containsKey(team1) && (this.teamMap.containsKey(team2))){
            int index1 = teamMap.get(team1);
            int index2 = teamMap.get(team2);
            return this.games[index1][index2];
        }
        throw new IllegalArgumentException("Invalid teams");
    }

    /*
        is given team eliminated?
     */
    public boolean isEliminated(String team) {
        int teamIndex = getTeamIndex(team);
        int vertexCount = this.getVertexCount();
        FlowNetwork flowNetwork = new FlowNetwork(vertexCount);
        if (!this.addEdgeFlowNetwork(flowNetwork, teamIndex, vertexCount)){
            //trivial elimination
            return true;
        }
        FordFulkerson fordFulkerson = runFordFulkerson(flowNetwork);
        if (fordFulkerson.value() == totCapacity){
            return false;
        }
        return true;
    }

    /*
        subset R of teams that eliminates given team; null if not eliminated
    */
    public Iterable<String> certificateOfElimination(String team)  {
        int teamIndex = getTeamIndex(team);
        int vertexCount = this.getVertexCount();
        FlowNetwork flowNetwork = new FlowNetwork(vertexCount);
        if (!addEdgeFlowNetwork(flowNetwork, teamIndex, vertexCount)){
            //trivial elimination
            return certificationOfEliminationTrivial(teamIndex);
        }
        FordFulkerson fordFulkerson = runFordFulkerson(flowNetwork);
        if (fordFulkerson.value() == totCapacity){
            //not eliminated
            return null;
        }
        else{
            return certificationOfElimination(teamIndex, fordFulkerson);
        }
    }

    /*
    Compute the vertexCount in the FlowNetwork datatype
       source is vertexCount-2
       sink is vertexCount-1
       teamVertex is 0-teamVertices-1(inclusive)
       gameVertex is teamVertices-vertexCount-3(inclusive)
     */
    private int getVertexCount(){
        int teamVertices = teamCount -1;//excluding the team
        int gameVertices = (teamVertices * (teamVertices-1))/2; //nchoose2
        return teamVertices + gameVertices + 2; //including source and sink
    }

    /*
    subset R of teams that eliminates given team due to trivial elimination
    */
    private Iterable<String> certificationOfEliminationTrivial(int teamIndex){
        Queue<String> queue = new Queue<String>();
        for(int i=0; i< teamCount; i++) {
            if (i == teamIndex) {
                continue;
            }
            int gamesConstraint = this.win[teamIndex] + this.remaining[teamIndex] - this.win[i];
            if (gamesConstraint < 0){
                queue.enqueue(this.indexMap.get(i));
            }
        }
        return queue;
    }

    /*
        compute subset R of teams that eliminates given team due based on mincut in Ford Fulkerson
    */
    private Iterable<String> certificationOfElimination(int teamIndex, FordFulkerson fordFulkerson){
        Queue<String> queue = new Queue<String>();
        for (int i=0; i < teamCount-1; i++){
            if (fordFulkerson.inCut(i)){
                String t = indexMap.get(i);
                if (i >= teamIndex){
                    t = indexMap.get(i+1);
                }
                queue.enqueue(t);
            }

        }
        return queue;
    }

    /*
    add edge to flowNetwork
    return true: if edge added
           false: if team is trivially eliminated
     */
    private boolean addEdgeFlowNetwork(FlowNetwork flowNetwork, int teamIndex, int vertexCount){
        totCapacity = 0;
        int teamVertices = teamCount -1;
        int sink = vertexCount -1;
        int source = vertexCount -2;
        int gameVertex = teamVertices;
        for(int i=0; i< teamVertices; i++) {
            //add edge from team vertex to sink
            int wins = win[i];
            if (i >= teamIndex){
                wins = win[i+1];
            }
            int gamesConstraint = win[teamIndex] + remaining[teamIndex] - wins;
            if (gamesConstraint < 0){
                //trivial elimination
                return false;
            }
            FlowEdge flowEdge = new FlowEdge(i, sink, gamesConstraint);
            flowNetwork.addEdge(flowEdge);
            for (int j= i+1; j< teamVertices ;j++){
                //System.out.println("i " + i);
                //System.out.println("j " + j);
                //System.out.println("counter  " + gameVertex);
                //add edge from game vertex to each of the team vertices
                flowEdge = new FlowEdge(gameVertex, i, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(flowEdge);
                flowEdge = new FlowEdge(gameVertex, j, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(flowEdge);
                //add edge from source to gameVertex
                int games = this.games[i][j];
                if (i >= teamIndex){
                    games = this.games[i+1][j+1];
                }
                else if (j >= teamIndex){
                    games = this.games[i][j+1];
                }
                totCapacity += games;
                flowEdge = new FlowEdge(source, gameVertex, games);
                flowNetwork.addEdge(flowEdge);
                gameVertex +=1;
            }
        }
        return true;

    }

    /*
    Run fordFulkerson on flowNetwork
    */
    private FordFulkerson runFordFulkerson(FlowNetwork flowNetwork){
        int vertexCount = flowNetwork.V();
        int source = vertexCount-2;
        int sink = vertexCount -1;
        //run FordFulkerson algorithm to compute maxFlow
        return new FordFulkerson(flowNetwork, source, sink);
    }

    /*
    Get team index for given team
     */
    private int getTeamIndex(String team){
        if (!this.teamMap.containsKey(team)){
            throw new IllegalArgumentException("Invalid teams");
        }
        return teamMap.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
