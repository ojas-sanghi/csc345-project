# CSC 345 Project

**Team Members:** Jake Balla, Saptarshi Mallick, Ojas Sanghi, Jackson Randolph

The code for our CSC 345 project implements two types of tries - a standard trie and a radix tree - and enables the user to utilize both tries with words of their choice.

`Trie.java` provides a common interface for both types of Tries.

`StandardTrie.java` implements a standard trie.

`RadixTree.java` implements a radix tree.

To run the project, open up the UserInput.java file and press F5. The program will take the guide the user in an interactive session, asking the user what type of trie they'd like to use, and then at each step asking the user if they want to a) insert a word, b) search for a word, c) delete a word, d) print all words, or e) print all words starting with a prefix. 

Choose each option as desired to interact with both tries as desired.

The code was thoroughly tested with all words in `words.txt` in `TrieTest.java`.
