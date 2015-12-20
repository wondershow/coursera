import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{
	private Dictionary dict;
	
	boolean[][] calculated;
	int[][] neighbors;
	
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	dict = new TreeSetDict();
    	for (int i = 0; i < dictionary.length; i++) 
    		if (dictionary[i].length() > 2) dict.put(dictionary[i]);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	Set<String> res = new HashSet<String>();
    	boolean[][] checked = new boolean[board.rows()][board.cols()];
    	
    	
    	for (int i = 0; i < board.rows(); i++)
    		for (int j = 0; j < board.cols(); j++) {
    			//System.out.println("start dfs from i = " + i + ", j = " + j);
    			dfs(board, res, checked, i, j, "");
    		}
    	return res;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
    	if (!this.dict.contains(word)) return 0;
    	
    	int len = word.length();
    	if (len <= 2)	return 0;
    	else if (len <= 4)	return 1;
    	else if (len == 5)	return 2;
    	else if (len == 6) 	return 3;
    	else if (len == 7)  return 5;
    	else return 11;
    }
    
    public static void main(String[] args) {
    	In in = new In("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw4/src/hw4/boggle/dictionary-16q.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        
        BoggleBoard board = new BoggleBoard("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw4/src/hw4/boggle/board-16q.txt");
        
       // System.out.println(board.getLetter(0, 0));
       // System.out.println(board.getLetter(2, 2));
        
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        
        int [][] res = solver.neighbors(4, 4, 3, 3);
       
        System.out.println();
    }
    
    private void dfs(BoggleBoard board, Set<String> res, boolean[][] checked, int i, int j, String prefix) {
    	prefix = prefix + board.getLetter(i, j);
//    	prefix = prefix.replaceFirst("Q", "QU");
//    	System.out.println(prefix);
    	String replaced = prefix.replaceFirst("Q", "QU");
    	if (!this.dict.hasPrefix(replaced)) return;
    	if (this.dict.contains(replaced)) res.add(replaced);
    	checked[i][j] = true;
    	int[][] next = neighbors(board.cols(), board.rows(), i, j);
    	if (next == null) return;
    	//System.out.println("starting point i = " + i + " j = " + j + ", col = " + board.cols() + ", row = " + board.rows());
    	for (int p = 0; p < next.length; p++) {
    		//System.out.println("checking:" + next[p][0] + "," + next[p][1]);
    		if (!checked[next[p][0]][next[p][1]]) {
    			//System.out.println("row = " + next[p][0] + ", col = " + next[p][1]);
    			dfs(board, res, checked, next[p][0], next[p][1], prefix);
    		}
    	}
    	checked[i][j] = false;
    }
    
    private int[][] neighbors(int cols, int rows, int i, int j) {
    	int[][] res;
    	int b = 0;
    	
    	if (cols == 1 && rows == 1) {
    		return null;
    	} if (cols == 1) {
    		if (i == 0 || i == rows - 1) res = new int[1][2];
    		else res = new int[2][2];
    		if (i != 0) res[b++] = new int[] {i - 1, j};
    		if (i != rows - 1) res[b++] = new int[] {i + 1, j};
    		return res;
    	} else if (rows == 1) {
    		if (j == 0 || j == cols - 1) res = new int[1][2];
    		else res = new int[2][2];
    		if (j != 0) res[b++] = new int[] {i, j - 1};
    		if (j != cols - 1) res[b++] = new int[] {i, j + 1};
    		return res;
    	}
    	
    	if ( (i == 0 || i == rows - 1) && (j == 0 || j == cols - 1) )      
    		res = new int[3][2];
    	else if ( (i == 0 || i == rows - 1) || (j == 0 || j == cols - 1) )
    		res = new int[5][2];
    	else
    		res = new int[8][2];
    	
    	if (i - 1 >= 0)	res[b++] = new int[] { i - 1 , j};
    	if (i + 1 < rows) res[b++] = new int[] { i + 1 , j};
    	if (j - 1 >= 0) res[b++] = new int[] { i, j - 1};
    	if (j + 1 < cols) res[b++] = new int[] { i, j + 1};
    	if (i - 1 >= 0 && j - 1 >= 0) res[b++] = new int[] { i - 1, j - 1};
    	if (i - 1 >= 0 && j + 1 < cols) res[b++] = new int[] { i - 1, j + 1};
    	if (i + 1 < rows && j - 1 >= 0) res[b++] = new int[] { i + 1, j - 1};
    	if (i + 1 < rows && j + 1 < cols) res[b++] = new int[] { i + 1, j + 1};
    	
    	return res;
    }
}

