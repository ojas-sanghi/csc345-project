import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class TrieTest {

    // enable us to test output from print statements
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // redirects output to outputStreamCaptor
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void insert() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        assertTrue(trie.search("test"));
    }

    @Test
    void search() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        assertTrue(trie.search("test"));
        assertFalse(trie.search("testing"));
    }

    @Test
    void delete() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        trie.delete("test");
        assertFalse(trie.search("test"));
    }

    @Test
    void printWordsPrefix() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        trie.insert("testing");
        trie.insert("tester");

        trie.printWordsPrefix("test");
        assertTrue(outputStreamCaptor.toString().trim().contains("test"));
        assertTrue(outputStreamCaptor.toString().trim().contains("testing"));
        assertTrue(outputStreamCaptor.toString().trim().contains("tester"));
    }

    @Test
    void printWords() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        trie.insert("testing");
        trie.insert("tester");

        trie.printWords();
        assertTrue(outputStreamCaptor.toString().trim().contains("test"));
        assertTrue(outputStreamCaptor.toString().trim().contains("test"));
        assertTrue(outputStreamCaptor.toString().trim().contains("testing"));
        assertTrue(outputStreamCaptor.toString().trim().contains("tester"));
    }

    @Test
    void insertRadix() {
        Trie trie = new RadixTree();
        trie.insert("romanae");
        trie.insert("romanus");
        trie.insert("romulus");
        trie.insert("rubens");
        trie.insert("ruber");
        trie.insert("rubicon");
        trie.insert("rubicundus");
        assertFalse(trie.search("test"));
        assertTrue(trie.search("romanae"));
        assertTrue(trie.search("romulus"));
        assertFalse(trie.search("rom"));
        assertTrue(trie.search("rubens"));
        assertFalse(trie.search("romana"));
        assertTrue(trie.search("rubicundus"));
        assertTrue(trie.search("rubicon"));
        assertTrue(trie.search("ruber"));
        assertFalse(trie.search("xor"));
    }
    @Test
    void insertRadix2(){
        Trie trie = new RadixTree();
        trie.insert("the");
        trie.insert("and");
        trie.insert("of");
        trie.insert("to");
        trie.insert("a");
        trie.insert("in");
        assertTrue(trie.search("the"));
        assertTrue(trie.search("and"));
        assertTrue(trie.search("of"));
        assertTrue(trie.search("to"));
        assertTrue(trie.search("a"));
        assertTrue(trie.search("in"));
    }

    @Test
    void searchRadix() {
        Trie trie = new RadixTree();
        assertFalse(trie.search("test"));
        assertFalse(trie.search("testing"));
    }

    @Test
    void deleteRadix() {
        Trie trie = new RadixTree();
        trie.insert("romanae");
        trie.insert("romanus");
        trie.insert("romulus");
        trie.insert("rubens");
        trie.insert("ruber");
        trie.insert("rubicon"); // ADDING THIS SEEMS TO ADD AN EXTRA N NODE FOR SOME REASON?????
        trie.insert("test");
        trie.delete("test");
        assertFalse(trie.search("test"));
        trie.delete("romanae");
        assertFalse(trie.search("romanae"));
        assertTrue(trie.search("romanus"));
        trie.delete("rubicon");
        assertFalse(trie.search("rubicon"));
        assertTrue(trie.search("ruber"));
        trie.delete("ruber");
        assertFalse(trie.search("ruber"));
        assertTrue(trie.search("romulus"));
        trie.delete("romulus");
        assertFalse(trie.search("romulus"));
        trie.delete("rubens");
        trie.delete("romanus");
        assertFalse(trie.search("rubens"));
        assertFalse(trie.search("romanus"));
        assertFalse(trie.search("r"));
        assertFalse(trie.search("rubn"));
    }
    @Test
    void extraDelete(){
        Trie trie = new RadixTree();
        trie.insert("rubens");
        trie.insert("ruber");
        trie.delete("rubens");
        trie.delete("ruber");
    }

    @Test
    void printWordsPrefixRadix() {
        Trie trie = new RadixTree();
        trie.insert("test");
        trie.insert("testing");
        trie.insert("tester");

        trie.printWordsPrefix("test");
        assertTrue(outputStreamCaptor.toString().trim().contains("test"));
        assertTrue(outputStreamCaptor.toString().trim().contains("testing"));
        assertTrue(outputStreamCaptor.toString().trim().contains("tester"));
    }

    @Test
    void printWordsRadix() {
        Trie trie = new RadixTree();
        trie.insert("test");
        trie.insert("testing");
        trie.insert("tester");

        trie.printWords();
        assertTrue(outputStreamCaptor.toString().trim().contains("test"));
        assertTrue(outputStreamCaptor.toString().trim().contains("testing"));
        assertTrue(outputStreamCaptor.toString().trim().contains("tester"));
    }

    // reset output stream
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }


    private List<String> readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim());
            }
        }
        return words;
    }
    @Test
    void insertWordsFromFile(){
        //Trie trie = new RadixTree();
        Trie trie = new StandardTrie();
        List<String> words;
        try {
            words = readWordsFromFile("words.txt");
            Collections.shuffle(words);
        }
        catch (IOException ex){
            assertTrue(false);
            return;
        }
        for(int i = 0; i < words.size(); i ++){
            trie.insert(words.get(i));
            assertTrue(trie.search(words.get(i)));
        }
        // It adds words but others are getting broken afterwards


       for(int i = 0; i < words.size(); i ++){
           assertTrue(trie.search(words.get(i)));
       }
    }
    @Test
    void deleteWordsFromFile() throws IOException {
        Trie trie = new StandardTrie();
        List<String> words = readWordsFromFile("words.txt");

        // Shuffle the words to insert them in random order
        Collections.shuffle(words);

        // Insert a random subset of words into the trie
        int numWordsToInsert = new Random().nextInt(words.size()) + 1;
        for (int i = 0; i < numWordsToInsert; i++) {
            trie.insert(words.get(i));
        }

        // Shuffle the words again for deletion
        Collections.shuffle(words);

        // Delete a random subset of inserted words
        int numWordsToDelete = new Random().nextInt(numWordsToInsert + 1);
        for (int i = 0; i < numWordsToDelete; i++) {
            String wordToDelete = words.get(i);
            trie.delete(wordToDelete);
            assertFalse(trie.search(wordToDelete));
        }
    }
    @Test
    void printAllWords() {
        Trie trie = new StandardTrie(); // Change to StandardTrie if needed
        List<String> words;
        try {
            words = readWordsFromFile("words.txt");
            Collections.shuffle(words);
        } catch (IOException ex) {
            assertTrue(false);
            return;
        }

        for (String word : words) {
            trie.insert(word);
        }

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        trie.printWords();

        System.setOut(System.out); // Reset System.out to the standard output stream

        String printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", ""); // Gets rid of new line for easy comparison

        // Check that printed output is exactly the same as the expected words
        Collections.sort(words); // Output will be sorted
        assertEquals(String.join("", words), printedOutput);
    }
    @Test
    void printAllWordsWithRandomPrefix() {
        Trie trie = new StandardTrie(); // Change to StandardTrie if needed
        List<String> words;
        try {
            words = readWordsFromFile("words.txt");
            Collections.shuffle(words);
        } catch (IOException ex) {
            assertTrue(false);
            return;
        }

        for (String word : words) {
            trie.insert(word);
        }

        // Randomly select a prefix from one of the words
        String randomPrefix = getRandomPrefix(words);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        trie.printWordsPrefix(randomPrefix);

        System.setOut(System.out); // Reset System.out to the standard output stream

        String printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", ""); // Gets rid of new line for easy comparison

        // Check that printed output is exactly the same as the expected words with the randomly selected prefix
        List<String> prefixedWords = words.stream()
                .filter(word -> word.startsWith(randomPrefix))
                .sorted()
                .collect(Collectors.toList());

        assertEquals(String.join("", prefixedWords), printedOutput);
    }

    private String getRandomPrefix(List<String> words) {
        if (words.isEmpty()) {
            return ""; // Return an empty string if the list is empty
        }

        Random random = new Random();
        String randomWord = words.get(random.nextInt(words.size()));

        // Randomly select a prefix from the random word
        int prefixLength = random.nextInt(randomWord.length() + 1);
        return randomWord.substring(0, prefixLength);
    }




}
