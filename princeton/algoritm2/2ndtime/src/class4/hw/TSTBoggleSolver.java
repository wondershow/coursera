package class4.hw;

import java.util.HashSet;


public class TSTBoggleSolver
{
	private TST<Integer> trie;
	
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
    	//return trie.keys();
    }
    
    private void dfs(int row, int col, BoggleBoard board, HashSet<String> set, boolean[][] marked, String pre) 
    {
    	int ROW = board.rows(), COL = board.cols();
    	String word = getWord(pre);
    	//System.out.println(pre);
    	if (trie.contains(word) && word.length() >= 3) set.add(word);
    	if (!containsPrefix(word)) 
    	{
    		//System.out.println("returns at" + pre);
    		return;
    	}
    	
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
}
