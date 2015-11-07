package assign1.InterviewQuestions;

/**
 * Created by VGN on 11/6/15.
 * Implementation of weighted quick union based on size(linking shorter tree(with less members)
 * to larger tree(with more members)
 * Compression is done once the root of a member is found, to shorten the tree
 */
public class WeightedQuickUnionCompressionBySize {
    private int[] members;//members

    private int[] size; // to keep track of size of the elements under the component

    private boolean allConnected = false; // to keep track if all members of the set are connected

    private int[] connectedComponents;

    WeightedQuickUnionCompressionBySize(int N){
        members = new int[N];
        size = new int[N];
        connectedComponents = new int[N];
        for (int i=0; i< N; i++){
            members[i] = i;
            size[i] = 1;
            connectedComponents[i] = i;
        }
    }

    public int findRoot(int member){
        while (member != members[member]){
            member = members[member];
        }
        int root = member;
        // repeat loop to modify the path to point to root(path compression)
        while (member != members[member]){
            members[member] = root;
        }
        return member;
    }

    public int find(int member){
        /*to find largest elemnet in connected component*/
        int root = findRoot(member);
        return connectedComponents[root];
    }

    public boolean connected(int member1, int member2){
        return findRoot(member1) == findRoot(member2);
    }

    public void union(int member1, int member2){
        int root1 = findRoot(member1);
        int root2 = findRoot(member2);
        if (root1 == root2){
            return ; //already connected
        }
        if (size[member1] < size[member2]){
            //place the root of smaller member to larger one
            members[root1] = root2;
            size[root2] += size[root1];
            if (size[root2] == members.length){
                allConnected = true;
            }
            if (connectedComponents[root1] > connectedComponents[root2]){
                connectedComponents[root2] = connectedComponents[root1];
            }
        }
        else{
            members[root2] = root1;
            size[root1] += size[root2];
            if (size[root1] == members.length){
                allConnected = true;
            }
            if (connectedComponents[root2] > connectedComponents[root1]){
                connectedComponents[root1] = connectedComponents[root2];
            }
        }
    }

    public boolean allMembersConnected(){
        return allConnected;
    }
}
