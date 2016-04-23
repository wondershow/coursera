package ch5;

import java.util.Arrays;

public class BoyerMoore
{

	private int[] right;
	private String pat;
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}
	
	public BoyerMoore(String pattern)
	{
		int M = pattern.length();
		int R = 256;
		this.pat = pattern;
		right = new int[R];
		Arrays.fill(right, -1);
		for (int i = 0; i < M; i++)
			right[pat.charAt(i)] = i;
	}

	public int search(String txt)
	{
		int i, j, skip, N = txt.length(), M = pat.length();
		//for (i = 0, j = 0; i < N && j < M; )
		skip = 0;
		for (i = 0; i< N-M; i += skip)
		{
			j = M-1;
			while (txt.charAt(i+j) == pat.charAt(j)) j--;
			if (j == 0) return i;
			skip = Math.max(1, j - right[txt.charAt(i+j)]);
		}
		return N;
	}
	
}
