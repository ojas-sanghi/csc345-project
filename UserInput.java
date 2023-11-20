import java.util.Scanner;

public class UserInput {
    private static final Scanner scanner;
    static {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("Do you want to use a 1) Standard Trie, 2) Radix Tree?, or 3) exit?");

        int choice;
        try {
            choice = scanner.nextInt();
            // If the user enters a non-integer, set choice to 3 so the default case is
            // executed.
        } catch (java.util.InputMismatchException e) {
            choice = 3;
        }

        if (choice == 1 || choice == 2)
            parseTrieInputs(choice == 2);
        else
            System.out.println("Invalid input.");
    }

    private static void parseTrieInputs(boolean radix) {
        System.out.println("" + (radix ? "Radix" : "Standard") + " Trie selected.");

        Trie trie = radix ? new RadixTree() : new StandardTrie();

        boolean exit = false;
        while (!exit) {
            System.out.println(
                    "Do you want to 1) insert a word, 2) search for a word, 3) delete a word, 4) print all words, 5) print all words starting with a prefix or 6) exit?");

            int choice;
            try {
                choice = scanner.nextInt();
                // If the user enters a non-integer, set choice to 7 so the default case is
                // executed.
            } catch (java.util.InputMismatchException e) {
                choice = 7;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Type a word to insert: ");
                    String word = scanner.next().trim();

                    System.out.println("Inserting for \"" + word + "\"...");

                    trie.insert(word);
                    System.out.println("\"" + word + "\" has been inserted.");
                }
                case 2 -> {
                    System.out.println("Type a word to search for: ");
                    String word = scanner.next().trim();

                    System.out.println("Searching for \"" + word + "\"...");

                    boolean search = trie.search(word);
                    System.out.println("\"" + word + "\" was " + (search ? "" : "not") + " found in the trie.");
                }
                case 3 -> {
                    System.out.println("Type a word to delete: ");
                    String word = scanner.next();

                    boolean delete = trie.delete(word);
                    System.out.println("\"" + word + "\""
                            + (delete ? " has been deleted." : " was not found in the trie, nothing was deleted."));
                }
                case 4 -> {
                    System.out.println("The trie contains these words: ");
                    trie.printWords();
                }
                case 5 -> {
                    System.out.println("Type a prefix to search for: ");
                    String prefix = scanner.next();

                    System.out.println("The trie contains these words starting with \"" + prefix + "-\": ");
                    trie.printWordsPrefix(prefix);
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    exit = true;
                }
                default -> {
                    System.out.println("Invalid input, exiting...");
                    exit = true;
                }
            }
        }
    }
}
