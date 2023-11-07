import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        trie.insert("test");
        assertTrue(trie.search("test"));
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
        Trie trie = new RadixTree();
        trie.insert("test");
        assertTrue(trie.search("test"));
        assertFalse(trie.search("testing"));
    }

    @Test
    void deleteRadix() {
        Trie trie = new RadixTree();
        trie.insert("test");
        trie.delete("test");
        assertFalse(trie.search("test"));
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

}
