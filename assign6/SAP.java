package assign6;
import algs4.BreadthFirstDirectedPaths;
import algs4.Digraph;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Created by VGN on 12/9/15.
 */
public class SAP {
    private final Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        // make a deep copy of the graph
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        return findShortestPathOrAncestor(v, w, true);
    }

    private int findShortestPathOrAncestor(int v, int w, boolean isShortestPath){
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(this.digraph, w);
        return computeShortestPathOrAncestor(vpath, wpath, isShortestPath);
    }

    private int findShortestPathOrAncestorIterable(Iterable<Integer> v, Iterable<Integer> w, boolean isShortestPath) {
        BreadthFirstDirectedPaths vpaths = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths wpaths = new BreadthFirstDirectedPaths(this.digraph, w);
        return computeShortestPathOrAncestor(vpaths, wpaths, isShortestPath);
    }

    private int computeShortestPathOrAncestor(BreadthFirstDirectedPaths vpath,
                                              BreadthFirstDirectedPaths wpath,
                                              boolean isShortestPath){
        int shortestPath = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int vertex =0; vertex < digraph.V(); vertex++){
            if (vpath.hasPathTo(vertex)&& (wpath.hasPathTo(vertex))){
                int currentPath = vpath.distTo(vertex) + wpath.distTo(vertex);
                if (shortestPath > currentPath){
                    shortestPath = currentPath;
                    ancestor = vertex;
                }
            }
        }
        if (shortestPath == Integer.MAX_VALUE){
            return -1;
        }
        if (isShortestPath)
            return shortestPath;
        else
            return ancestor;
    }




    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        return findShortestPathOrAncestor(v, w, false);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        return findShortestPathOrAncestorIterable(v, w, true);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        return findShortestPathOrAncestorIterable(v, w, false);
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
