import java.util.HashMap;

public class RadixTree {

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

}
