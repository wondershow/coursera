package class4.hw;

import java.util.HashSet;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Queue;

public class TreeSetBoggleSolver
{
	private TreeSet<String> dict;
	
	
	public TreeSetBoggleSolver(String[] dictionary) 
	{
		dict = new TreeSet<String>();
		for (int i = 0; i < dictionary.length; i++)
			dict.add(dictionary[i]);
	}
	
	/***
     * find all the valid words in a BoggleBoard
     * 
     * @param board: the input board
     * @return an iterable data set that contains all the valid words
     * */
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	HashSet<String> set = new HashSet<String>();
    	
    	int row = board.rows(), col = board.cols();
    	
    	boolean[][] marked;
    	for (int i = 0; i < row; i++)
    		for (int j = 0; j < col; j++) 
    		{
    			marked = new boolean[board.rows()][board.cols()];
    			marked[i][j] = true;
    			dfs(i, j, board, set, marked, "");
    		}
    	
    }
    
    private void dfs(int row, int col, BoggleBoard board, HashSet<String> set, boolean[][] marked, String pre) 
    {
    	int ROW = board.rows(), COL = board.cols();
    	String word = getWord(pre);
    	if (dict.contains(word)&&word.length() >= 3) set.add(word);
    	if (row > 0) { dfs(row - 1, col, board, set, marked, pre + board.getLetter(row - 1, col));}
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (word == null) throw new NullPointerException();
    	
    }
    
    //replace Q with Qu
    private String getWord(String word) 
    {
    	return word.replace("Q", "QU");
    }
}
