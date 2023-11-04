import java.util.HashMap;

public class RadixTree implements Trie {

    private class RadixNode {

        boolean isEnd;
        private RadixNode [] children;
        int childrenSize;
        String label;

        RadixNode() {
            // TODO: change this, don't need 26
            // maybe also change in standard trie? make it a dynamic list?
            // can change to more memory efficient one once we have tests at a later point
            label = "";
            children = new RadixNode [26];
            childrenSize = 0;
            isEnd = false;
        }
    }


    RadixNode root;

    public RadixTree() {
        root = new RadixNode();
    }

    //  public void preOrder() {
    //  preOrder(root);
   // }

//    private void preOrder(RadixNode node) { Will have to redo this to work with the structure of radix tree,
//        System.out.println(node.value);
//
//        for (RadixNode n : node.children.values()) { // Made changes to work with hashmap
//            preOrder(n);
//        }
//    }
    public void insert(String word)
    {
        RadixNode curr = root;
        int index = 0;

        while(index<word.length())
        {
            char c = Character.toLowerCase(word.charAt(index));
            String curString = word.substring(index);
            int childIndex = c-97;
            
            //string not in tree, adds the rest as a new node
            if(curr.children[childIndex] == null)
            {
                curr.children[childIndex] = new RadixNode();
                curr.children[childIndex].label = curString;
                curr.children[childIndex].isEnd = true;
                curr.childrenSize++;
                break;
            }
            //finds index of first different letter between rest of word and child lable
            //-1 if one is contained in the other one
            int split = -1;
            int length = Math.min(curString.length(),curr.children[childIndex].label.length());
            for(int i = 0;i<length;i++)
            {
                if(curString.charAt(i) != curr.children[childIndex].label.charAt(i))
                {
                    split = i;
                    break;
                }
            }

            //case where either child or string is contained in the other
            if(split == -1)
            {
                //if remainder of string matched nodes's label
                //if equal in length, sets child node as an end
                if(curString.length() ==curr.children[childIndex].label.length())
                {
                    curr.children[childIndex].isEnd = true;
                }
                //if smaller than node's lable
                else if(curString.length() < curr.children[childIndex].label.length())
                {
                    String subString = curr.children[childIndex].label.substring(curString.length());
                    curr.label = curString;
                    char s = Character.toLowerCase(subString.charAt(0));
                    int subindex = s -97;
                    curr.children[subindex] = new RadixNode();
                    curr.children[subindex].label = subString;
                    curr.children[subindex].isEnd = true; 
                    curr.childrenSize++;
                }
                //if greater than child node, repeat loop with rest of current string
                else
                {
                    split = curr.label.length();
                }  
                    
            }
            else
            {
                // if child and string differ, split and create two new branches
                String subString = curr.label.substring(split); //different part of prev lable
                curr.label = curr.label.substring(0,split);    //similar part is new label
                char sub1 = Character.toLowerCase(subString.charAt(0));   //gets index for children array
                int subindex = sub1 -97;
                
                RadixNode restlabel = new RadixNode();    //creates a new node
                restlabel.label = subString;            //different part of string is label
                restlabel.children = curr.children;    //copies children from current
                restlabel.isEnd = curr.isEnd;          //is curr was a word, child is
                curr.isEnd = false;                    //currno longer a word since split
                curr.children = new RadixNode[26];    //resets chilren of curr
                curr.children[subindex] = restlabel;  //new node as a child of curr
                
                char rest1 = Character.toLowerCase(curString.charAt(split));//adds rest of string
                int wordindex = rest1 - 97;                              //get child index
                curr.children[wordindex] = new RadixNode();
                curr.children[wordindex].label = curString.substring(split); //adds rest of string a new childnode
                curr.children[wordindex].isEnd = true;
                curr.childrenSize = 2;              //curr has 2 children, two different parts of curr and string;
                break;
            }
            //increments index and traveres tree before repeating
            curr = curr.children[childIndex];
            index += split;
            
        }
    }

    @Override
    public void delete(String word)
    {
        this.delete(this.root, word);
    }

    private RadixNode delete(RadixNode curr, String word){
        // Base Case
        if(word.isEmpty()){ // Base case when all characters are gone
            if(curr.childrenSize == 0 && curr != root){ // Go ahead and null out if not the root and nothing inside
                return null;
            }
            curr.isEnd = false; // Else only pathway to other words, no longer ending
            return curr;
        }
        if(!Character.isLetter(word.charAt(0))){ // Passes over chars which are not letters
            return delete(curr, word.substring(1));
        }
        // Verification that it actually exists
        char c = Character.toLowerCase(word.charAt(0));
        int index = Character.charCount(c) - 97;
        RadixNode child = curr.children[index];
        if(child == null || ! word.startsWith(child.label)){ // We can do this since we know that there will be at most one substring with that char
            return curr; // Does not exist so don't modify
        }
        // Deletion
        RadixNode deleted = delete(child, word.substring(child.label.length())); // Recursive call onto next node with remianing substring
        // Handle case where it now points to nothing
        if(deleted == null){ // Point to nothing, then remove edge that points to it!
            curr.childrenSize -= 1;
            curr.children[index] = null;
            if(curr.childrenSize == 0 && !curr.isEnd && curr != root){ // This node is now useless since doesn't point to ending or next word!
                return null;
            }
        }
        // Node with only one next, hence lets just merge the two together
        else if(!deleted.isEnd && deleted.childrenSize == 1){ // Just an empty node pointing to only one edge
            deleted.childrenSize -= 1;
            for(int i = 0; i < deleted.children.length; i ++){ // Right now we have to loop through to find removal Edge, pehaps can be made O(1)
                if(deleted.children[i] != null){
                    deleted.children[i].label = child.label + deleted.children[i].label; // Plop in new label
                    curr.children[index] = deleted.children[i]; // Point past empty node
                }
            }
        }
        return curr; // No changes need to be done

    }

    public boolean search(String input)
    {
        return this.search(input, this.root);
    }

    private boolean search(String search, RadixNode current)
    {
        //If input string is empty, then it's been found
        if(search.isEmpty()) return true;
        //If current node is the end of a word (and input string isn't empty), then not found
        else if(current.childrenSize == 0) return false;
        //Recursive case
        else
        {
            boolean result = false;

            //Loop through all nonnull children and check if they match the start of the input string, and recurse if so
            //Input string is trimmed (removing the prefix) before recursing
            //If any of them return true, then the word has been found
            for(RadixNode n : current.children)
                if(n != null)
                    if(search.startsWith(n.label))
                        result = result || search(search.substring(n.label.length()), n);

            return result;
        }
    }

    @Override
    public void printWords() {
        printWordsHelper(root, "");
    }

    private void printWordsHelper(RadixNode cur, String word){
        String nextWord = word + cur.label; // Grab current word built out
        if(cur.isEnd){ // End of word so print
            System.out.println(nextWord);
        }
        if(cur.childrenSize == 0){ // No children so no reason to check here
            return;
        }
        int childrenAmount = cur.childrenSize; // Keep going until we run out of children, makes loop not always O(26)
        for(int i = 0; childrenAmount != 0; i ++){
            if(cur.children[i] != null) { // We have a child to visit
                childrenAmount--;
                printWordsHelper(cur.children[i], nextWord);
            }
        }
    }

    @Override
    public void printWordsPrefix(String prefix) {
        printWordsPrefixHelper(root, prefix, 0);
    }
    private void printWordsPrefixHelper(RadixNode cur, String prefix, int pointer){
        // First we see if we can use up all that we currently have
        // 3 cases, I can think of
        // 1: The prefix equals current label -> pass all cur children to printWordsHelper
        // 2: The prefix is smaller than rest of label -> pass cur children with prefix + label to printWordsHelper
        // 3: The prefix is larger than rest of label -> find appropriate child and recurse
        // First two cases can be merged into one op
        int curPointer = 0;
        while(curPointer < cur.label.length() && pointer < prefix.length()){
            if(Character.toLowerCase(prefix.charAt(pointer)) != cur.label.charAt(curPointer)){
                return; // Does not match up hence no words for this prefix
            }
            curPointer ++;
            pointer ++;
        }
        if(pointer == prefix.length()){ // First two cases where prefix has been used up
            String nextWord = prefix + cur.label.substring(curPointer);
            int childrenAmount = cur.childrenSize; // Keep going until we run out of children, makes loop not always O(26)
            for(int i = 0; childrenAmount != 0; i ++){
                if(cur.children[i] != null) { // We have a child to visit
                    childrenAmount--;
                    printWordsHelper(cur.children[i], nextWord);
                }
            }
        }
        else{ // Last case where we need to keep using prefix up
            int code = Character.toLowerCase(prefix.charAt(pointer)) - 97;
            if(cur.children[code] == null){ // Nothing to go to!
                return;
            }
            printWordsPrefixHelper(cur.children[code], prefix, pointer);
        }

    }
}
