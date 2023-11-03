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

        StandardTrie sTrie = new StandardTrie();
        RadixTree rTree = new RadixTree();

        boolean exit = false;
        while(!exit)
        {
            System.out.println("Do you want to 1) insert a word, 2) search for a word, 3) delete a word, 4) print all words, or 5) exit?");

            int choice = scanner.nextInt();

            switch(choice)
            {
                case 1 ->
                {

                }
                case 2 ->
                {

                }
                case 3 ->
                {

                }
                case 4 ->
                {

                }
                default ->
                {
                    System.out.println(choice == 5 ? "Exiting..." : "Invalid input, exiting...");
                    exit = true;
                }
            }
        }
    }

    private static void radixTreeInputs()
    {
        System.out.println("Radix Tree selected.");

        while(true)
        {
            System.out.println("Do you want to 1) insert a word, 2) search for a word, 3) delete a word, or 4) print all words?");
        }
    }
}
