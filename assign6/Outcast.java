package assign6;
import stdlib.In;
import stdlib.StdOut;

/**
 * Created by VGN on 12/9/15.
 */
public class Outcast {
    private WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)  {
        this.wordNet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns)  {
        int maxDistance = -1;
        String result = null;
        for (int i=0; i < nouns.length ; i++){
            int totDistance = 0;
            String noun = nouns[i];
            for (int j =0; j < nouns.length; j++){
                if (i == j){
                    continue;
                }
                String noun2 = nouns[j];
                int distance = wordNet.distance(noun, noun2);
                totDistance += distance;
            }
            if (totDistance > maxDistance){
                maxDistance = totDistance;
                result = noun;
            }
            totDistance = 0;
        }
        return result;
    }

    // see test client below
    public static void main(String[] args)  {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
