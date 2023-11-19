/**
 * This is a Junit test file used to test both our Trie and RadixTree structures.
 * This includes test cases we used to debug our structures along with extensive tests that utilize a file with thousands of words.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


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
        /**
        Very basic trie insert test
         */
        Trie trie = new StandardTrie();
        trie.insert("test");
        assertTrue(trie.search("test"));
    }

    @Test
    void insertManySamePrefix() {
        Trie trie = new StandardTrie();
        trie.insert("test");
        trie.insert("tester");
        trie.insert("testing");
        trie.insert("testable");

        assertTrue(trie.search("test"));
        assertTrue(trie.search("tester"));
        assertTrue(trie.search("testing"));
        assertTrue(trie.search("testable"));
        assertFalse(trie.search("tes"));
        assertFalse(trie.search("testab"));
        assertFalse(trie.search("testabl"));
        assertFalse(trie.search("testin"));
    }

    @Test
    void insertManySamePrefixReverse() {
        Trie trie = new StandardTrie();
        trie.insert("testable");
        trie.insert("testing");
        trie.insert("tester");
        trie.insert("test");

        assertTrue(trie.search("test"));
        assertTrue(trie.search("tester"));
        assertTrue(trie.search("testing"));
        assertTrue(trie.search("testable"));
        assertFalse(trie.search("tes"));
        assertFalse(trie.search("testab"));
        assertFalse(trie.search("testabl"));
        assertFalse(trie.search("testin"));
    }

    @Test
    void insertAllCharactersAndDeleteRandom() {
        int start = 'a', end = 'z';

        StandardTrie trie = new StandardTrie();
        for(int i = start; i <= end; i++)
            trie.insert(String.valueOf((char)(i)));

        String selected = String.valueOf((char)(int)(Math.random() * (end - start) + start));
        trie.delete(selected);

        assertFalse(trie.search(selected));
    }

    @Test
    void search() {
        /**
         * Very basic trie search test
         */
        Trie trie = new StandardTrie();
        trie.insert("test");
        assertTrue(trie.search("test"));
        assertFalse(trie.search("testing"));
    }

    @Test
    void delete() {
        /**
         * Very basic trie delete test
         */
        Trie trie = new StandardTrie();
        trie.insert("test");
        trie.delete("test");
        assertFalse(trie.search("test"));
    }

    @Test
    void printWordsPrefix() {
        /**
         * Very basic printwords from prefix trie test
         */
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
        /**
         * Very basic print all words test
         */
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
        /**
         * Insert radix stress test utilized to debug extra word bug we were encountering
         */
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
        /**
         * Another stress test for insert but this time with basic words
         */
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
    void insertRadix3(){
        /**
         * An expanded version of the previous test
         */
        Trie trie = new RadixTree();
        trie.insert("of");
        trie.insert("on");
        trie.insert("one");
        trie.insert("or");
        trie.insert("out");
        trie.insert("only");
        trie.insert("over");
        trie.insert("other");
        trie.insert("old");
        trie.insert("our");
        trie.insert("own");
        trie.insert("off");
        trie.insert("once");
        trie.insert("open");
        trie.insert("others");
        trie.insert("often");
        assertTrue(trie.search("often"));
    }

    @Test
    void insertRadix4(){
        /**
         * Used to find last bug that was causing the extensive insert test to fail
         */
        Trie trie = new RadixTree();
        //trie.insert("do");
        //trie.insert("did");
        //trie.insert("down");
        //trie.insert("day");
        //trie.insert("door");
        //trie.insert("done");
        //trie.insert("days");
        trie.insert("dear");
        //trie.insert("dark");
        trie.insert("dead");
        trie.insert("death");
        //trie.insert("does");
        trie.insert("de");
        assertTrue(trie.search("de"));
    }

    @Test
    void insertManySamePrefixRadix() {
        Trie trie = new RadixTree();
        trie.insert("test");
        trie.insert("tester");
        trie.insert("testing");
        trie.insert("testable");

        assertTrue(trie.search("test"));
        assertTrue(trie.search("tester"));
        assertTrue(trie.search("testing"));
        assertTrue(trie.search("testable"));
        assertFalse(trie.search("tes"));
        assertFalse(trie.search("testab"));
        assertFalse(trie.search("testabl"));
        assertFalse(trie.search("testin"));
    }

    @Test
    void searchRadix() {
        /**
         * Very basic radix tree search test
         */
        Trie trie = new RadixTree();
        assertFalse(trie.search("test"));
        assertFalse(trie.search("testing"));
    }

    @Test
    void deleteRadix() {
        /**
         * Extensive stress delete test, found bug in insert using this test
         */
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
        /**
         * A more basic delete test
         */
        Trie trie = new RadixTree();
        trie.insert("rubens");
        trie.insert("ruber");
        trie.delete("rubens");
        trie.delete("ruber");
    }

    @Test
    void printWordsPrefixRadix() {
        /**
         * A simple prefix print for Radix Tree
         */
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
        /**
         * A simple print words for radix tree
         */
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
        /**
         * This reads the words from a test file and puts them in an arraylist
         */
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
        /**
         * This test case takes in words from a file, randomizes them,
         * and then makes sure each word was inserted and can be found both during and after the insertions.
         * Most insert test cases above are derived from this test case.
         */
        Trie radix = new RadixTree();
        Trie standard = new StandardTrie();
        List<String> words;

        try {
            words = readWordsFromFile("words.txt"); // Grab words
            Collections.shuffle(words); // Randomize
        }
        catch (IOException ex){
            assertTrue(false); // Fail test case if file didn't load in
            return;
        }

        for (String word : words) { // Insert words and check for them
            standard.insert(word);
            radix.insert(word);
            assertTrue(standard.search(word));
            assertTrue(radix.search(word));
        }

        for (String word : words) { // Make sure they can still be located
            assertTrue(standard.search(word));
            assertTrue(radix.search(word));
        }
    }
    @Test
    void deleteWordsFromFile() throws IOException {
        /**
         * This test case takes in words from a file, randomizes them,
         * and then deletes a random amount of them.
         * It then makes sure deleted words are no longer in the structure
         * while other words still are in the structure.
         * Most insert test cases above are derived from this test case.
         */
        Trie standard = new StandardTrie();
        Trie radix = new RadixTree();
        List<String> words = readWordsFromFile("words.txt");
        Collections.shuffle(words); // Randomize words

        for (String word : words) { // Place into structures
            standard.insert(word);
            radix.insert(word);
        }

        Collections.shuffle(words); // Shuffle again for deletion

        int numWordsToDelete = new Random().nextInt(words.size()); // Delete a random subset of inserted words
        for (int i = 0; i < numWordsToDelete; i++) {
            standard.delete(words.get(i));
            radix.delete(words.get(i));
            assertFalse(standard.search(words.get(i)));
            assertFalse(radix.search(words.get(i)));
        }
        for(int i = numWordsToDelete; i < words.size(); i ++){ // Make sure no other words got deleted
            assertTrue(standard.search(words.get(i)));
            assertTrue(radix.search(words.get(i)));
        }
    }
    @Test
    void printAllWords() {
        /**
         * This prints all words found in the structures and makes sure it got the right words
         */
        Trie standard = new StandardTrie();
        Trie radix = new RadixTree();
        List<String> words;
        try {
            words = readWordsFromFile("words.txt");
            Collections.shuffle(words);
        } catch (IOException ex) {
            assertTrue(false);
            return;
        }

        for (String word : words) { // Insert words into the structures
            standard.insert(word);
            radix.insert(word);
        }

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        standard.printWords();

        String printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", ""); // Gets rid of new line for easy comparison

        // Check that printed output is exactly the same as the expected words
        Collections.sort(words); // Output will be sorted
        assertEquals(String.join("", words), printedOutput);

        outputStreamCaptor = new ByteArrayOutputStream(); // Repeat but for radix
        System.setOut(new PrintStream(outputStreamCaptor));

        radix.printWords();

        printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", "");
        assertEquals(String.join("", words), printedOutput);

    }
    @Test
    void printAllWordsWithRandomPrefix() {
        /**
         * This is similar to the above test case but for prefixes instead
         */
        Trie standard = new StandardTrie();
        Trie radix = new RadixTree();
        List<String> words;
        try {
            words = readWordsFromFile("words.txt");
            Collections.shuffle(words); // Randomize words
        } catch (IOException ex) {
            assertTrue(false);
            return;
        }

        for (String word : words) {
            standard.insert(word);
            radix.insert(word);
        }

        String randomPrefix = getRandomPrefix(words); // Randomly select a prefix from one of the words

        List<String> prefixedWords = words.stream() // This gets words that only start with the prefix and puts them into a list
                .filter(word -> word.startsWith(randomPrefix))
                .sorted()
                .collect(Collectors.toList());

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        standard.printWordsPrefix(randomPrefix);
        String printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", ""); // Gets rid of new line for easy comparison

        assertEquals(String.join("", prefixedWords), printedOutput);

        outputStreamCaptor = new ByteArrayOutputStream(); // Repeat but for radix
        System.setOut(new PrintStream(outputStreamCaptor));

        radix.printWordsPrefix(randomPrefix);
        printedOutput = outputStreamCaptor.toString().trim().replaceAll("\\r?\\n", "");

        assertEquals(String.join("", prefixedWords), printedOutput);
    }

    private String getRandomPrefix(List<String> words) {
        /**
         * This gets a random word and then extracts a random prefix from it
         */
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
