public class StandardTrie implements Trie {

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
    public void insert(String key) {
        int pointer = 0; // This points to the current char being added
        StandardNode current = root; // This points to the node we are currently exploring
        while (pointer < key.length()) {
            char c = Character.toLowerCase(key.charAt(pointer)); // Makes it lowercase
            if (Character.isLetter(c)) { // This will make sure it is a letter
                int code = c - 97; // Gets ascii and shifts 0-25 scale
                if (current.children[code] == null) { // Create one if not already there
                    current.children[code] = new StandardNode();
                }
                current = current.children[code];
            }
            pointer++;
        }
        current.isEnd = true;
    }
    public void delete(String key)
    {
        StandardNode curNode = root;
        //if empty string is given
        for(int i = 0;i<key.length();i++)
        {
            if(curNode== null)
            {
                return;
            }
            char c = Character.toLowerCase(key.charAt(i));
            if(Character.isLetter(c))
            {
                int code = c-97;
                if(curNode.children[code] == null)
                {
                    return;
                }
                curNode = curNode.children[code];
            }
        }
        curNode.isEnd = false;
    }

    
    public void printWords()
    {
        this.printWords(this.root, "");
    }

    private void printWords(StandardNode current, String s)
    {
        //If current node is the end of a word, print out the string
        if(current.isEnd) System.out.println(s);

        //Then keep checking children for more potential words
        for(int i = 0; i < 26; i++)
            if(current.children[i] != null)
                this.printWords(current.children[i], s + (char)(97 + i));
    }
    
    @Override
    public void printWordsPrefix(String prefix){
        StandardNode current = root;
        for(int pointer = 0; pointer < prefix.length(); pointer ++){ // Go through all letters in the prefix
            char c = Character.toLowerCase(prefix.charAt(pointer));
            if(Character.isLetter(c)){
                int code = c - 97;
                if(current.children[code] == null){ // If null then we know we have no words there
                    return;
                }
                current = current.children[code];
            }
        }
        printWords(current, prefix); // Made it through the prefix, now just call printWords but starting from this node instead of root
    }
}
