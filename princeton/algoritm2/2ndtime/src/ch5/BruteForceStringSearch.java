package ch5;

public class BruteForceStringSearch
{
	/**
	 * 
	 * find a pattern in a large string
	 * @param str: the large string context
	 * @param pat: the pattern to be looked for
	 * @return the index of the 1st character of 1st found match
	 * **/
	public static int search(String str, String pat)
	{
		int M = pat.length();
		int N = str.length();
		for (int i = 0; i < N - M; i++)
		{
			int j;
			for (j = i; j < M; j++)
				if (str.charAt(i) != pat.charAt(j)) break;
			if (j == M) return i;
		}
		return N;
	}
	
	
	public static int search2(String str, String pat) 
	{
		int M = pat.length();
		int N = str.length();
		
		int i,j;
		for (i = 0, j = 0; i < N && j < M; i++)
		{
			if(str.charAt(i) == pat.charAt(j)) j++;
			else {i -= j; j=0;  }
		}
		if (j == M) return i - M;
		return N;
	}
}
