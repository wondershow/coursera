import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.BinaryStdIn;


public class BoggleSolver
{
	private Dictionary dict;
	
	private boolean[][] calculated;
	private int[][] neighbors;
	private HashMap<String, int[][]> neiborcache;
	private Stack<int[]> dfsStack;
	
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	//dict = new TreeSetDict();
    	dict = new TSTDict();
    	dfsStack = new Stack<int[]>();
    	neiborcache = new HashMap<String, int[][]>();
    	for (int i = 0; i < dictionary.length; i++) 
    		if (dictionary[i].length() > 2) dict.put(dictionary[i]);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	Set<String> res = new HashSet<String>();
    	boolean[][] onStack = new boolean[board.rows()][board.cols()];
    	neiborcache = new HashMap<String, int[][]>();
    	for (int i = 0; i < board.rows(); i++)
    		for (int j = 0; j < board.cols(); j++) {
    			//System.out.println("start dfs from i = " + i + ", j = " + j);
    			dfs2(board, res, onStack, i, j, "");
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
    	/*
    	Queue<String> s = new Queue<String>();
    	s.enqueue("1");
    	s.enqueue("2");
    	s.enqueue("3");
    	s.enqueue("4");
    	s.enqueue("5");
    	//System.out.println(queueString(s));    */	
    	In in = new In("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw4/src/boggle/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        
        BoggleBoard board = new BoggleBoard("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw4/src/boggle/board4x4.txt");
        
        
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        
    }
    
    /**
     * 
     ***/
    private void dfs2(BoggleBoard board, Set<String> res, boolean[][] onStack, int i, int j, String prefix) {
    	dfsStack.push(new int[] {i,j });
    	onStack[i][j] = true;
    	String pfx = "";
    	Queue<Character> strTrace = new Queue<Character>();
    	
    	while (!dfsStack.isEmpty()) {
    		int[] index = dfsStack.pop();
    		strTrace.enqueue(board.getLetter(index[0], index[1]));
    		
    		onStack[index[0]][index[1]] = true;
    		prefix = this.queueString(strTrace);
    		
    		String replaced = prefix.replaceAll("Q", "QU");
    		
    		//there is prefix in the dictonary, stop searching
    		if (!this.dict.hasPrefix(replaced)) {
    			onStack[index[0]][index[1]] = false;
    			continue;
    		}
    		
        	if (this.dict.contains(replaced)) res.add(replaced);
    		
    		int[][] next = neighbors(board.cols(), board.rows(), index[0], index[1]);
    		onStack[index[0]][index[1]] = true;
    		for (int k = 0; k < next.length; k++) {
    			if (!onStack[next[k][0]][next[k][1]])
    				this.dfsStack.push(next[k]);
    		}
    		onStack[index[0]][index[1]] = false;
    	}
    }
    
    private String queueString(Queue<Character> s) {
    	Iterator it = s.iterator();
    	StringBuilder sb = new StringBuilder();
    	while (it.hasNext())
    		sb.append(it.next());
    	return sb.toString();
    }
    
    private void dfs(BoggleBoard board, Set<String> res, boolean[][] onStack, int i, int j, String prefix) {
    	prefix = prefix + board.getLetter(i, j);
//    	prefix = prefix.replaceFirst("Q", "QU");
//    	System.out.println(prefix);
    	String replaced = prefix.replaceAll("Q", "QU");
    	if (!this.dict.hasPrefix(replaced)) return;
    	if (this.dict.contains(replaced)) res.add(replaced);
    	onStack[i][j] = true;
    	int[][] next = neighbors(board.cols(), board.rows(), i, j);
    	if (next == null) return;
    	//System.out.println("starting point i = " + i + " j = " + j + ", col = " + board.cols() + ", row = " + board.rows());
    	for (int p = 0; p < next.length; p++) {
    		//System.out.println("checking:" + next[p][0] + "," + next[p][1]);
    		if (!onStack[next[p][0]][next[p][1]]) {
    			//System.out.println("row = " + next[p][0] + ", col = " + next[p][1]);
    			dfs(board, res, onStack, next[p][0], next[p][1], prefix);
    		}
    	}
    	onStack[i][j] = false;
    }
    
    private int[][] neighbors(int cols, int rows, int i, int j) {
    	if (neiborcache.containsKey(i+","+j)) return neiborcache.get(i+","+j);
    	
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
    	neiborcache.put(i+","+j, res);
    	return res;
    }
}

