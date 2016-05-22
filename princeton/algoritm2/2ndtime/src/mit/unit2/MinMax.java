package mit.unit2;

import java.util.Random;
/**
 * This program compares two algorithms to find two extreme values in an array (min and max)
 * Supposer arraysize is N
 * 1. scan the array twice, first time get min and 2nd time gets max; we need 2(N-1) comparisons
 * 2. scan the array once, using two neighbor elements as an unit, at each unit, compare min with the smaller 
 *    one and compare max with the greater one. we need 3/2(N-1) comparisons
 * */
public class MinMax
{
	//number of compares
	private static int C = 0;
	
	public static void main(String[] args)
	{
		int N = 10000;
		// TODO Auto-generated method stub
		Random rand = new Random();
		int[] A = new int[N];
		for (int i = 0; i < N; i++ )
			A[i] = rand.nextInt(100000);
		int min = min(A);
		int max = max(A);
		 
		System.out.println("min = " + min + ", max = " + max + ", # of comparisons = " + C);
		C = 0;
		int[] minmax = minMax(A);
		System.out.println("min = " + minmax[0] + ", max = " + minmax[1] + ", # of comparisons = " + C);
		
	}
	
	private static int min(int[] A)
	{
		int min = A[0];
		for (int i = 1; i < A.length; i++)
			if (compare(A[i], min) < 0) min = A[i];
		return min;
	}
	
	private static int max(int[] A)
	{
		int max = A[0];
		for (int i = 1; i < A.length; i++)
			if (compare(A[i], max) > 0) max = A[i];
		return max;
	}
	
	private static int compare(int i, int j)
	{
		int res = -2;
		if (i == j) res = 0;
		else if (i > j) res = 1;
		else res = -1;
		C++;
		return res;
	}
	
	private static int[] minMax(int[] A)
	{
		int N = A.length;
		//res[0] is min, res[1] is max
		int[] res = new int[2];
		
		if (N % 2 == 0) { res[0] = A[0]; res[1] = A[1];}
		else {res[0] = A[0]; res[1] = A[0];}
		
		for (int i = (N%2) == 0? 2:1 ; i < N; i += 2 )
		{
			if (compare(A[i], A[i+1]) > 0 ) // A[i] is larger than A[i+1]
			{
				if (compare(A[i+1], res[0]) < 0) res[0] = A[i+1];
				if (compare(A[i], res[1]) > 0) res[1] = A[i];
			} 
			else
			{
				if (compare(A[i], res[0]) < 0) res[0] = A[i];
				if (compare(A[i+1], res[1]) > 0) res[1] = A[i+1];
			}
		}
		return res;
	}
}
