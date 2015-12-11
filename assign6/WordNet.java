package assign6;

import algs4.*;
import stdlib.In;

/**
 * Created by VGN on 12/9/15.
 */
public class WordNet {
    private final Digraph digraph;
    //maps noun -> synsetid
    private final SeparateChainingHashST<String, Bag<Integer>> nounST;
    //maps synsetid -> noun
    private final RedBlackBST<Integer, String> synsetST;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null){
            throw new java.lang.NullPointerException("Null pointer exception");
        }
        nounST = new SeparateChainingHashST<String, Bag<Integer>>();
        synsetST = new RedBlackBST<Integer, String>();
        In in = new In(synsets);
        while (!in.isEmpty()) {
            String str = in.readLine();
            String[] stringArray = str.split(",");
            //System.out.println(stringArray[0]);
            int synsetId = Integer.parseInt(stringArray[0]);
            String nouns = stringArray[1];
            synsetST.put(synsetId, nouns);
            String[] nounArray = nouns.split("\\s+");
            for (int i=0; i < nounArray.length; i++){
                String noun = nounArray[i];
                if (!nounST.contains(noun)){
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(synsetId);
                    nounST.put(noun, bag);
                }
                else{
                    Bag<Integer> bag = nounST.get(noun);
                    bag.add(synsetId);
                }
            }
        }
        digraph = new Digraph(synsetST.max()+1);
        in = new In(hypernyms);
        while (!in.isEmpty()) {
            String str = in.readLine();
            String[] stringArray = str.split(",");
            int vertex1 = Integer.parseInt(stringArray[0]);
            for (int i=1 ; i < stringArray.length; i++){
                int vertex2 = Integer.parseInt(stringArray[i]);
                digraph.addEdge(vertex1, vertex2);
            }
        }
        if (new DirectedCycle(this.digraph).hasCycle()){
            throw new java.lang.IllegalArgumentException("Graph has cycle");
        }
        //root validation
        int root =0;
        for (int vertex =0; vertex < this.digraph.V(); vertex ++){
            if (this.digraph.outdegree(vertex) == 0){
                root+= 1;
            }
        }
        if (root != 1){
            throw new java.lang.IllegalArgumentException("Graph has "+ root + " root(s)");
        }
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null){
            throw new java.lang.NullPointerException("Null pointer exception");
        }
        return nounST.contains(word);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nounST.keys();
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (nounA == null || nounB == null){
            throw new java.lang.NullPointerException("Null pointer exception");
        }
        if (!this.nounST.contains(nounA) || !this.nounST.contains(nounB)){
            throw new java.lang.IllegalArgumentException(" Not wordnet noun");
        }
        Bag<Integer> synsetIdAs = this.nounST.get(nounA);
        Bag<Integer> synsetIdBs = this.nounST.get(nounB);
        if (sap == null){
            sap = new SAP(this.digraph);
        }
        int ancestor = sap.ancestor(synsetIdAs, synsetIdBs);
        return this.synsetST.get(ancestor);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (nounA == null || nounB == null){
            throw new java.lang.NullPointerException("Null pointer exception");
        }
        if (!this.nounST.contains(nounA)){
            throw new java.lang.IllegalArgumentException(nounA + " Not wordnet noun");
        }
        if (!this.nounST.contains(nounB)){
            throw new java.lang.IllegalArgumentException(nounB +" Not wordnet noun");
        }
        Bag<Integer> synsetIdAs = this.nounST.get(nounA);
        Bag<Integer> synsetIdBs = this.nounST.get(nounB);
        if (sap == null){
            sap = new SAP(this.digraph);
        }
        return sap.length(synsetIdAs, synsetIdBs);
    }

    public static void main(String[] args){
        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}
