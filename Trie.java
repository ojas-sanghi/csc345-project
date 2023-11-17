public interface Trie
{
    void insert(String word);
    boolean search(String word);
    void delete(String word);
    void printWordsPrefix(String prefix);
    void printWords();
}
