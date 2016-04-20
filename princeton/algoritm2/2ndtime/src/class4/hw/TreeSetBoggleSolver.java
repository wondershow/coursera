package class4.hw;

import java.util.ArrayList;
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
		//System.out.println(dict.ceiling("ESOTERIC "));
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
    	if (board == null) throw new NullPointerException();
    	HashSet<String> set = new HashSet<String>();
    	int row = board.rows(), col = board.cols();
    	boolean[][] marked;
    	for (int i = 0; i < row; i++)
    		for (int j = 0; j < col; j++) 
    		{
    			marked = new boolean[board.rows()][board.cols()];
    			marked[i][j] = true;
    			dfs(i, j, board, set, marked, board.getLetter(i, j)+"");
    		}
    	return set;
    	//return new ArrayList();
    }
    
    private void dfs(int row, int col, BoggleBoard board, HashSet<String> set, boolean[][] marked, String pre) 
    {
    	int ROW = board.rows(), COL = board.cols();
    	String word = getWord(pre);
    	//System.out.println(pre);
    	if (dict.contains(word) && word.length() >= 3) set.add(word);
    	if (!containsPrefix(word)) return;
    	
    	if (row > 0 && !marked[row - 1][col]) 
    	{ 
    		marked[row - 1][col] = true;
    		dfs(row - 1, col, board, set, marked, pre + board.getLetter(row - 1, col));
    		marked[row - 1][col] = false;
    	}
    	if (col > 0 && !marked[row][col - 1]) 
    	{ 
    		marked[row][col - 1] = true;
    		dfs(row, col - 1, board, set, marked, pre + board.getLetter(row, col - 1));
    		marked[row][col - 1] = false;
    	}
    	if (row < ROW - 1 && !marked[row + 1][ col])  
    	{ 
    		marked[row + 1][col] = true;
    		dfs(row + 1, col, board, set, marked, pre + board.getLetter(row + 1, col));
    		marked[row + 1][col] = false;
    	}
    	if (col < COL - 1 && !marked[row][col + 1]) 
    	{ 
    		marked[row][col + 1] = true;
    		dfs(row, col + 1, board, set, marked, pre + board.getLetter(row, col + 1));
    		marked[row][col + 1] = false;
    	}
    	if (row > 0 && col > 0 && !marked[row - 1][col - 1] ) 
    	{ 
    		marked[row - 1][col - 1] = true;
    		dfs(row - 1, col - 1, board, set, marked, pre + board.getLetter(row - 1, col - 1));
    		marked[row - 1][col - 1] = false;
    	}
    	if (row < ROW - 1 && col < COL - 1 && !marked[row + 1][col + 1]) 
    	{
    		marked[row + 1][col + 1] = true;
    		dfs(row + 1, col + 1, board, set, marked, pre + board.getLetter(row + 1, col + 1));
    		marked[row + 1][col + 1] = false;
    	}
    	if (row > 0 && col < COL - 1 && !marked[row - 1][col + 1]) 
    	{ 
    		marked[row - 1][col + 1] = true;
    		dfs(row - 1, col + 1, board, set, marked, pre + board.getLetter(row - 1, col + 1));
    		marked[row - 1][col + 1] = false;
    	}
    	if (row < ROW - 1 && col > 0 && !marked[row + 1][col - 1]) 
    	{ 
    		marked[row + 1][ col - 1] = true;
    		dfs(row + 1, col - 1, board, set, marked, pre + board.getLetter(row + 1, col - 1));
    		marked[row + 1][ col - 1] = false;
    	}
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (word == null) throw new NullPointerException();
    	if (!dict.contains(word)) return 0;
    	//String realWord = getWord(word);
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
    
    public static void main(String[] args)
    {
    	for (int i = 0; i < 54; i ++)
    	{
    		System.out.println(i +  ":"  + (double)Math.round((double)i*40/54) / 2     );
    	}
    }
    
    // returns if the treeset contains elements that has a given prefix
    private boolean containsPrefix(String pre) 
    {
    	String next = dict.ceiling(pre + " ");
    	if (next != null && next.startsWith(pre)) return true;
    	return false;
    }
}
