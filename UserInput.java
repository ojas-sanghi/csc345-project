import java.util.Scanner;

public class UserInput
{
    private static final Scanner scanner; static { scanner = new Scanner(System.in); }

    public static void main(String[] args)
    {
        System.out.println("Do you want to use a 1) Standard Trie or 2) Radix Tree?");

        int choice = scanner.nextInt();
        if(choice == 1) standardTrieInputs();
        else if(choice == 2) radixTreeInputs();
        else System.out.println("Invalid input.");
    }

    private static void standardTrieInputs()
    {
        System.out.println("Standard Trie selected.");
    }

    private static void radixTreeInputs()
    {
        System.out.println("Radix Tree selected.");
    }
}
