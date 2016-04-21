package class4.hw;

import java.util.Collection;
import java.util.HashSet;


public class TSTBoggleSolver
{
	private TST<Integer> trie;
	//private ArrayTrie<Integer> test;
	private int N;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
	
	public TSTBoggleSolver(String[] dictionary) 
	{
		trie = new TST<Integer>();
		for (int i = 0; i < dictionary.length; i++)
			trie.put(dictionary[i], 0);
	}
	
	// Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	if (board == null) throw new NullPointerException();
    	Collection<String> set = new HashSet<String>();
    	int row = board.rows(), col = board.cols();
    	boolean[][] marked;
    	marked = new boolean[board.rows()][board.cols()];
    	//StringBuilder sb = new StringBuilder("");
    	for (int i = 0; i < row; i++)
    		for (int j = 0; j < col; j++) 
    			dfs(i, j, board, set, marked, new StringBuilder());
    	return set;
    	//return trie.keys();
    }
    
    private void dfs(int row, int col, BoggleBoard board, Collection<String> set, boolean[][] marked, StringBuilder pre) 
    {
    	int ROW = board.rows(), COL = board.cols();
    	if (row < 0 || col < 0 || row >= ROW || col >= COL || marked[row][col]) return;
    	
    	char c = board.getLetter(row, col);
    	
    	
    	pushLetter(pre, c);
    	String word = pre.toString();
    	//System.out.println(N++ + ": word is " + word);
    	if (word.length() >= 3 && trie.contains(word)) set.add(word);
    	
    	
    	if (word.length() >= 3 && !containsPrefix(word)){
    		popLetter(pre, c);
    		//System.out.println(N++ + ": returns at " + word);
    		return;
    	}
    	
    	marked[row][col] = true;
		dfs(row - 1, col, board, set, marked, pre);
		dfs(row, col - 1, board, set, marked, pre);
		dfs(row + 1, col, board, set, marked, pre);
		dfs(row, col + 1, board, set, marked, pre);
		dfs(row - 1, col - 1, board, set, marked, pre);
		dfs(row + 1, col + 1, board, set, marked, pre);
		dfs(row - 1, col + 1, board, set, marked, pre);
		dfs(row + 1, col - 1, board, set, marked, pre);
		marked[row][col] = false;
		popLetter(pre, c);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (!trie.contains(word)) return 0;
    	int len = word.length();
    	if (len <= 2) return 0;
    	if (len <= 4) return 1;
    	if (len <= 5) return 2;
    	if (len <= 6) return 3;
    	if (len <= 7) return 5;
    	return 11;
    }
    
    //replace Q with Qu
    private String getWord(String word) 
    {
    	return word.replace("Q", "QU");
    }
    
    // returns if the treeset contains elements that has a given prefix
    private boolean containsPrefix(String pre) 
    {
    	if (trie.containsPrefixOf(pre)) return true;
    	return false;
    }
    
    private void pushLetter(StringBuilder sb, char c) 
    {
    	sb.append(c);
    	if (c == 'Q') sb.append('U');
    }
    
    private void popLetter(StringBuilder sb, char c)
    {
    	sb.setLength(sb.length() - 1);
    	if (c == 'Q') sb.setLength(sb.length() - 1);
    }
}
