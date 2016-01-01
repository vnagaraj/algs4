package assign9;
/*************************************************************************
 *
 *  A modified version of TrieSET, implemented  using a 26-way trie representing letters A-Z
 *
 *
 *************************************************************************/

public class BoggleTrie {
    private static final int R = 26;        // only representing capital letters A-Z

    private Node root;      // root of trie
    private int N;          // number of keys in trie

    // 26-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    /**
     * Initializes an empty set of strings.
     */
    public BoggleTrie() {
    }

    /**
     * Does the set contain the given key?
     * @param key the key
     * @return <tt>true</tt> if the set contains <tt>key</tt> and
     *     <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        c = (char) (c -65);
        return get(x.next[c], key, d+1);
    }

    /**
     * Adds the key to the set if it is not already present.
     * @param key the key to add
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) N++;
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            c = (char) (c -65);
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }

    /**
     * Returns the number of strings in the set.
     * @return the number of strings in the set
     */
    public int size() {
        return N;
    }

    /**
     * Is the set empty?
     * @return <tt>true</tt> if the set is empty, and <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Checks if the keys in the set match the given prefix.
     * @param prefix the prefix
     * @return true/false
     */
    public boolean containsPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        if (x == null){
            return false;
        }
        return true;
    }

}

