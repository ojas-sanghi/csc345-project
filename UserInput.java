import java.util.Scanner;

public class UserInput
{
    private static final Scanner scanner; static { scanner = new Scanner(System.in); }

    public static void main(String[] args)
    {
        System.out.println("Do you want to use a 1) Standard Trie, or 2) Radix Tree?");

        int choice = scanner.nextInt();
        if(choice == 1 || choice == 2) parseTrieInputs(choice == 2);
        else System.out.println("Invalid input.");
    }

    private static void parseTrieInputs(boolean radix)
    {
        System.out.println("Standard Trie selected.");

        Trie trie = radix ? new RadixTree() : new StandardTrie();

        boolean exit = false;
        while(!exit)
        {
            System.out.println("Do you want to 1) insert a word, 2) search for a word, 3) delete a word, 4) print all words, 5) print all words starting with a prefix or 6) exit?");

            int choice = scanner.nextInt();

            switch(choice)
            {
                case 1 ->
                {
                    System.out.println("Type a word to insert: ");
                    String word = scanner.next();

                    trie.insert(word);
                    System.out.println("\"" + word + "\" has been inserted.");
                }
                case 2 ->
                {
                    System.out.println("Type a word to search for: ");
                    String word = scanner.next();

                    boolean search = trie.search(word);
                    System.out.println("\"" + word + "\" was " + (search ? "not" : "") + " found in the trie.");
                }
                case 3 ->
                {
                    System.out.println("Type a word to delete: ");
                    String word = scanner.next();

                    trie.delete(word);
                    System.out.println("\"" + word + "\" has been deleted.");
                }
                case 4 ->
                {
                    System.out.println("The trie contains these words: ");
                    trie.printWords();
                }
                case 5 ->
                {
                    System.out.println("Type a prefix to search for: ");
                    String prefix = scanner.next();

                    System.out.println("The trie contains these words starting with \"" + prefix + "-\": ");
                    trie.printWordsPrefix(prefix);
                }
                default ->
                {
                    System.out.println(choice == 6 ? "Exiting..." : "Invalid input, exiting...");
                    exit = true;
                }
            }
        }
    }
}
