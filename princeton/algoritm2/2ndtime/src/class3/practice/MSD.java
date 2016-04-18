package class3.practice;

import edu.princeton.cs.algs4.In;

public class MSD 
{
	private static int R = 256;
	private static final int M = 1;
	private static String[] aux;
	
	
	public static void main(String[] args) 
	{
		String path = "./src/class3/practice/input/shells.txt";
		String[] a = LSD.readAllStrings(path);
		
		sort(a);
		
		for (int i = 0; i< a.length; i++)
			System.out.println(a[i]);
	}
	
	public static void sort(String[] a) 
	{
		int N = a.length;
		aux = new String[N];
		sort(a, 0, N-1, 0);
	}
	
	private static int charAt(String s, int d) 
	{ if (d < s.length()) return s.charAt(d); else return -1; }
	
	private static void sort(String[] a, int lo, int hi, int d) 
	{
		if (hi  <= lo + M) 
		{insertionSort(a, lo, hi, d); return;}
		
		int[] count = new int[R+2];
		for (int i = lo; i <= hi; i++)
			count[charAt(a[i], d)+2]++;
		for (int r = 0; r <=R; r++)
			count[r+1] += count[r];
		for (int i=lo; i<=hi; i++)
			aux[lo + count[charAt(a[i], d)+1]++] = a[i];
		for (int i = lo; i<=hi; i++)
			a[i] = aux[i];
		
		for (int r = 0; r<R; r++)
			sort(a, lo + count[r], lo + count[r+1] - 1, d+1);
	}
	
	private static void insertionSort(String[] a, int lo, int hi,int d) 
	{
		for (int i= lo+1; i<= hi; i++)
			for (int j = i; j>lo && less(a[j],a[j-1],d); j--)
				exch(a, j-1, j);
	}
	
	private static boolean less(String s1, String s2, int d) 
	{
		if (charAt(s1, d) < charAt(s2,d)) return true;
		else return false;
	}
	
	private static void exch(String[] a, int p, int q) 
	{
		String tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
}
