package mit.unit2;

public class Heapsort
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sort the input array from index 1 to the end(index 0 is not included)
	 * */
	public static void sort(Comparable[] a)
	{
		int N = a.length - 1;
		for (int i = N/2; i>=1; i--)
			sink(a, i, N);
		
		for (int i = N; i >= 1; i--)
		{
			exch(a, 1, N--);
			sink(a, 1, N);
		}
	}
	
	private static void exch(Comparable[] a, int i, int j)
	{
		Comparable tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	private static void swim(Comparable[] a, int i)
	{
		while (i > 1) 
		{
			int parent = i / 2;
			int cmp = a[i].compareTo(parent);
			if (cmp < 0) break; 
			exch(a, i, parent);
		}
	}
	
	private static void sink(Comparable[] a, int i, int N)
	{
		while (i <= N/2)
		{
			int largerChild = i * 2;
			if (largerChild < N && a[largerChild].compareTo( a[largerChild+1] ) < 0)
				largerChild++;
			
			if ( a[i].compareTo(a[largerChild]) > 0) break;
			exch(a, i, largerChild);
			i = largerChild;
		}
	}
}
