public class RadixTree {

    public class RadixNode {
        RadixNode[] children;

        boolean isEnd;
        String value;

        RadixNode() {
            // TODO: change this, don't need 26
            // maybe also change in standard trie? make it a dynamic list?
            // can change to more memory efficient one once we have tests at a later point
            children = new RadixNode[26];
        }
    }

    RadixNode root;

    public RadixTree() {
        root = new RadixNode();
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(RadixNode node) {
        System.out.println(node.value);

        for (RadixNode n : node.children) {
            preOrder(n);
        }
    }

}
