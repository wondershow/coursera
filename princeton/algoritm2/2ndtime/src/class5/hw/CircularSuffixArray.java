package class5.hw;

public class CircularSuffixArray
{
	private int N; 
	private int[] index;
	
	private static final int R = 256;
	private static final int CUTOFF = 5;
	//private final String csstr;
	private final char[] strArr;
	/*
	private class MySubStr 
	{
		public int start, cur;
		MySubStr(int s, int d) 
		{
			start = s;
			cur = d;
		}
	}*/
	
	public CircularSuffixArray(String s)  // circular suffix array of s
	{
		if (s == null) throw new java.lang.NullPointerException();
		N = s.length();
		
		index = new int[N];
		strArr = new char[2*N];
		int i;
		
		for (i = 0; i < N; i++) 
		{
			index[i] = i;
			
			strArr[i] = s.charAt(i); 
			strArr[i+N] = s.charAt(i);
			
		}
		
		quick3sort(index, 0, N-1, 0);
		//LSDSort(suffixChars, subStrs); 
	}
	
	private int charAt(int offset, int d) 
	{
		return strArr[offset + d];
	}
	
	private void quick3sort(int[] rank, int lo, int hi, int d)
	{
		//System.out.println("hi = " + hi + ", lo = " + lo + " d = " + d);
		if ( hi <= lo + CUTOFF) 
		{
			insertion(rank, lo, hi, d);
			return;
		}
		
		int p = charAt(rank[lo], d);
		
		int lt = lo, gt = hi, i = lo +1;
		
		while (i <= gt)
		{
			int c = charAt(rank[i], d);
			if (c > p) { exch(rank, i, gt--); }
			else if (c < p) {exch(rank, i++, lt++); }
			else i++;
		}
		
		quick3sort(rank, lo, lt-1, d);
		if (d < N)
			quick3sort(rank, lt, gt, d+1);
		quick3sort(rank, gt+1, hi, d);
	}
	
	private void exch(int[] rank, int i, int j)
	{
		int tmp = rank[i];
		rank[i] = rank[j];
		rank[j] = tmp;
	}
	
	private void insertion(int[] rank, int lo, int hi, int d) 
	{
		
		for (int i = lo; i <= hi; i++)
			for (int j = i; j > lo; j--)
			{
				if ( compare(rank, j, j-1, d) < 0 )
					exch(rank, j, j-1);
				else
					break;
			}
		
	}
	
	private int compare(int[] rank, int i, int j, int d)
	{
		while (d<=N-1 && charAt(rank[i], d) == charAt(rank[j], d)) d++;
		return charAt(rank[i], d) - charAt(rank[j], d);
	}
	
	
	/*
	private void LSDSort(char[] sfxChar, MySubStr[] subStrArr) 
	{
		int i;
		
		for (int d = N - 1; d >= 0; d--)
		{
			int[] count = new int[R+1];
			for (i = 0; i < N; i++)
				count[charAt(sfxChar, subStrArr[i]) + 1]++; 
			
			for (i = 1; i < R+1; i++)
				count[i] += count[i-1];
			
			for (i = 0; i < N; i++)
				aux[count[charAt(sfxChar, subStrArr[i])]++] = subStrArr[i];
			
			for (i = 0; i < N; i++) 
			{
				subStrArr[i] = aux[i];
				aux[i].cur--;
			}
			//System.out.println("d = " + d);
		}
	}*/
	
	public int length()                   // length of s
	{
		return N;
	}
	
	public int index(int i)               // returns index of ith sorted suffix
	{
		if (i < 0 || i >= N) throw new java.lang.IndexOutOfBoundsException();
		return index[i];
	}
	
	public static void main(String[] args)// unit testing of the methods (optional)
	{
		CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
		for (int i = 0; i < csa.length(); i++)
			System.out.println(csa.index(i));
	}
}
