import java.util.HashMap;

public class RadixTree {

    private class RadixNode {

        boolean isEnd;
//        private HashMap<Character,Edge> edges;
        private Edge [] edges;
        int edgeSize;

        RadixNode() {
            // TODO: change this, don't need 26
            // maybe also change in standard trie? make it a dynamic list?
            // can change to more memory efficient one once we have tests at a later point
            // This needs to be a hash map since each edge can be a substring of unkown size
            edges = new Edge[26];
            edgeSize = 0;
            isEnd = false;
        }
    }
    private class Edge{
        String label;
        RadixNode next;
        Edge(String label, RadixNode next){
            this.label = label;
            if(next == null){
                next = new RadixNode();
                next.isEnd = true;
            }
            else {
                next = new RadixNode();
            }
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
            if(curr.edgeSize == 0 && curr != root){ // Go ahead and null out if not the root and nothing inside
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
        Edge edge = curr.edges[index]; // Gets the edge which points to the next node
        if(edge == null || ! word.startsWith(edge.label)){ // We can do this since we know that there will be at most one substring with that char
            return curr; // Does not exist so don't modify
        }
        // Deletion
        RadixNode deleted = delete(edge.next, word.substring(edge.label.length())); // Recursive call onto next node with remianing substring
        // Handle case where it now points to nothing
        if(deleted == null){ // Point to nothing, then remove edge that points to it!
            curr.edgeSize -= 1;
            curr.edges[index] = null;
            if(curr.edgeSize == 0 && !curr.isEnd && curr != root){ // This node is now useless since doesn't point to ending or next word!
                return null;
            }
        }
        // Node with only one next, hence lets just merge the two together
        else if(!deleted.isEnd && deleted.edgeSize == 1){ // Just an empty node pointing to only one edge
            deleted.edgeSize -= 1;
            for(int i = 0; i < deleted.edges.length; i ++){ // Right now we have to loop through to find removal Edge, pehaps can be made O(1)
                if(deleted.edges[i] != null){
                    curr.edges[index] = new Edge(edge.label + deleted.edges[i].label, deleted.edges[i].next);
                }
            }
        }
        return curr; // No changes need to be done

    }

}
