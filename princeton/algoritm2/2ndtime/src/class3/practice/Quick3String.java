package class3.practice;

public class Quick3String
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	private static int charAt(String s, int d) 
	{ if (d < s.length()) return s.charAt(d); else return -1; }
	
	public static void sort(String[] args) 
	{
		
	}
	
	private static void sort(String[] a, int lo, int hi, int d) {
		int pivot = charAt(a[lo], d);
		int lt = lo, i = lo+1, gt = hi;
		
		while (i <= gt) 
		{
			int t = charAt(a[i],d);
			if (t < pivot ) { exch(a, i++, lt++);}
			else if (t > pivot) { exch(a, i, gt--);}
			else i++;
		}
		
		sort(a, lo, lt-1, d);
		if (pivot != 0) sort(a, lt, gt, d+1);
		sort(a, gt+1, hi, d);
	}
	
	private static void exch(String[] a, int p, int q) 
	{
		String tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
}
