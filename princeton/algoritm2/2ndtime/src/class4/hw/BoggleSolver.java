package class4.hw;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{
	private TreeSetBoggleSolver treeSolver; 
	public static void main(String[] args)
	{
		String path1 = "./src/class4/hw/files/dictionary-algs4.txt";
		String path2 = "./src/class4/hw/files/board-q.txt";
		In in = new In(path1);
	    String[] dictionary = in.readAllStrings();
	    BoggleSolver solver = new BoggleSolver(dictionary);
	    BoggleBoard board = new BoggleBoard(path2);
	    int score = 0;
	    for (String word : solver.getAllValidWords(board))
	    {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
	    }
	    StdOut.println("Score = " + score);
	}
	
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    	treeSolver = new TreeSetBoggleSolver(dictionary);
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
    	return treeSolver.getAllValidWords(board);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	return treeSolver.scoreOf(word);
    }
}