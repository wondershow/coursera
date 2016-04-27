package topcoder.dp;

import java.util.Arrays;

public class ZigZag 
{	
	public static int longestZigZag(int[] sequence) 
	{
		if (sequence == null) throw new NullPointerException();
		int N = sequence.length;
		if (N <=2) return N;
		int res;
		int[] zzlen = new int[N];
		Arrays.fill(zzlen, 2);
		boolean[] aux = new boolean[N];
		
		
		for (int i = 1; i < N; i++)
			if (sequence[i] > sequence[i - 1]) 
				aux[i] = true; // true means this item is larger than its zigzag item
			else
				aux[i] = false; // false means this item is smaller than its zigzag item
				
		zzlen[0] = 2;
		zzlen[1] = 2;
		
		for (int i = 2; i < N; i++)
			for (int j = 1; j <= i; j++)
				if (sequence[j] < sequence[i] && !aux[j]  && (zzlen[j] + 1) > zzlen[i])
					{ zzlen[i] = zzlen[j] + 1; aux[i] = true; }
				else if (sequence[j] > sequence[i] && aux[j]  && (zzlen[j] + 1) > zzlen[i])
				{ zzlen[i] = zzlen[j] + 1; aux[i] = false; }
		
		res = Integer.MIN_VALUE;
		for (int i = 0; i < zzlen.length; i++)
			if (zzlen[i] > res) res = zzlen[i];
		return res;
	}
	
	public static void main(String[] args)
	{
		
	}
}
