package class3.practice;

import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class LSD {

	//private static final int R = 26;
	
	/**
	 * return all strings(words) in a file
	 * @file the path of the file that contains words
	 * **/
	public static String[] readAllStrings(String file) {
		try {
			In in;
			in = new In(file);
			ArrayList<String> wordList = new ArrayList<String>();
			while (!in.isEmpty()) {
				String line = in.readLine();
				String[] words = line.split("\\s+");
				for (int i = 0; i < words.length; i++)
					wordList.add(words[i]);
			}
			String[] allWords = wordList.toArray(new String[wordList.size()]);
			
			return allWords;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) 
	{
		
		int[] a = {2,3,4,5,6,7,8,9,10,1};
		
		for (int j = a.length - 1; j >0 && a[j] < a[j-1]; j-- )
		{
			//System.out.println("exchanging " + j + " and " + (j-1));
			int tmp = a[j];
			a[j] = a[j-1];
			a[j-1] = tmp;
		}
		
		for (int i = 0; i < a.length;i++)
			System.out.println(a[i]);
		
		
		// TODO Auto-generated method stub
		ArrayList<String> wordList = new ArrayList<String>();
		String path = "./src/class3/practice/input/words.txt";
		In in;
		try {
			in = new In(path);
			while (!in.isEmpty()) {
				String line = in.readLine();
				String[] words = line.split("\\s+");
				for (int i = 0; i < words.length; i++)
					wordList.add(words[i]);
			}
			String[] allWords = wordList.toArray(new String[wordList.size()]);
			
			
			LSD.sort(allWords, 3);
			for (int i = 0; i < allWords.length; i++)
				System.out.println(allWords[i]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * W how many digits a string has
	 * Time complexity is O(N + R)
	 * Space Complexity is O (N)
	 * ***/
	public static void sort(String[] a, int W) 
	{
		
		int N = a.length;
		int R = 256;
		
		String[] aux = new String[N];
		
		for (int d = W-1; d >= 0; d--) 
		{
			int[] count = new int[R+1];
			for (int i = 0; i < N; i++) 
				count[a[i].charAt(d)+1]++;
			for (int i = 0; i < R; i++ )
				count[i+1] += count[i];
			for (int i = 0; i < N; i++) 
				aux[count[a[i].charAt(d)]++] = a[i];
			for (int i = 0; i < N; i++)
				a[i] = aux[i];
		}
	}
}	
