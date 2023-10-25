public class StandardTrie {

    public class StandardNode {
        StandardNode[] children;

        boolean isEnd;

        StandardNode() {
            children = new StandardNode[26];
        }
    }

    StandardNode root;

    public StandardTrie() {
        root = new StandardNode();
    }

    public boolean search(String word) {
        return search(word, root, 0);
    }

    private boolean search(String word, StandardNode StandardNode, int idx) {
        if (idx >= word.length())
            return false;

        StandardNode newStandardNode = StandardNode.children[word.charAt(idx) - 'a'];

        if (newStandardNode == null)
            return false;

        // do we want to return value of StandardNode, or boolean as to whether or not
        // it exists?
        // change this return value, the others, and the two signatures accordingly
        if (idx == word.length() - 1 && newStandardNode.isEnd)
            return true;

        return search(word, newStandardNode, idx + 1);
    }
}
