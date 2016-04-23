package ch5;

public class KMP
{
	private String pat;
	private int[][] dfa;
	
	public KMP(String pattern)
	{
		this.pat = pattern;
		int M = pat.length();
		int R = 256;
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		int x =  0;
		for (int i = 1; i < M; i++)
		{
			for (int c = 0; c < R; c++)
				dfa[c][i] = dfa[c][x];
			dfa[pat.charAt(i)][i] = i+1;
			x = dfa[pat.charAt(i)][x];
		}
	}
	
	/***
	 * use KMP search to search a pattern in the context
	 * 
	 * @param txt: the context string
	 * @return the index of 1st matched substring or txt length if not found
	 * @throws NullPointerException if the input context is null
	 * **/
	public int search(String txt) 
	{
		if (txt == null) throw new NullPointerException();
		int i, s, N = txt.length(), M = pat.length();
		
		for (i = 0, s = 0; i < N && s < M; i++, s++ )
			s = dfa[txt.charAt(i++)][s];
		
		if (s == M) return i - M;
		else return N;
	}
}
