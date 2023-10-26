import java.util.HashMap;

public class RadixTree {

    private class RadixNode {

        boolean isEnd;
        private HashMap<Character,Edge> edges;


        RadixNode() {
            // TODO: change this, don't need 26
            // maybe also change in standard trie? make it a dynamic list?
            // can change to more memory efficient one once we have tests at a later point
            // This needs to be a hash map since each edge can be a substring of unkown size
            edges = new HashMap<Character, Edge>();
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
            if(curr.edges.isEmpty() && curr != root){ // Go ahead and null out if not the root and nothing inside
                return null;
            }
            curr.isEnd = false; // Else only pathway to other words, no longer ending
            return curr;
        }
        // Verification that it actually exists
        Edge edge = curr.edges.get(word.charAt(0)); // Gets the edge which points to the next node
        if(edge == null || ! word.startsWith(edge.label)){ // We can do this since we know that there will be at most one substring with that char
            return curr; // Does not exist so don't modify
        }
        // Deletion
        RadixNode deleted = delete(edge.next, word.substring(edge.label.length())); // recursive call onto next node with remianing substring
        // Handle case where it now points to nothing
        if(deleted == null){ // Point to nothing, then remove edge that points to it!
            curr.edges.remove(word.charAt(0));
            if(curr.edges.size() == 0 && !curr.isEnd && curr != root){ // This node is now useless since doesn't point to ending or next word!
                return null;
            }
        }
        // Node with only one next, hence lets just merge the two together
        else if(!deleted.isEnd && deleted.edges.size() == 1){ // Just an empty node pointing to only one edge
            curr.edges.remove(word.charAt(0)); // We will be replacing this with the new merged edge
            for(Edge oneEdge: deleted.edges.values()){ // This is just a fancy way of grabing that one edge
                curr.edges.put(word.charAt(0), new Edge(edge.label + oneEdge.label, oneEdge.next)); // Placing the merged edge in
            }
        }
        return curr; // No changes need to be done

    }

}
