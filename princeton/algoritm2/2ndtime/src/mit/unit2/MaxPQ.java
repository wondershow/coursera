package mit.unit2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<Key> implements Iterable<Key>
{
	private Key[] pq;
	private int N; //N means how many items in this PQ
	private int size;
	private Comparator<Key> comparator;
	
	public static void main(String[] args) 
	{
		
	}
	
	MaxPQ(int max)
	{
		N = max;
		pq = (Key [])new Object[N + 1];
		size = N + 1;
	}
	
	MaxPQ(Key[] a)
	{
		N = a.length;
		//TODO how to make this one immutable?
		pq = (Key[]) new Object[N + 1];
		for (int i = 1; i <= N; i++)
			pq[i] = a[i-1];
		
		for (int i = N / 2; i >= 1; i--)
			sink(i);
		size = N + 1;
	}
	
	public void insert(Key v)
	{
		if (N == size) resize(2 * N); 
		pq[++N] = v;
		swim(N);
	}
	
	private void resize(int s)
	{
		Key[] newPQ = (Key[]) (new Object[s]);
		
		for (int i = 1; i < N; i++)
			newPQ[i] = pq[i];
		
		size = s;
	}
	
	public Key max()
	{
		if (isEmpty()) throw new java.lang.NullPointerException();
		return pq[1];
	}
	
	public boolean isEmpty()
	{
		return N <= 0;
	}
	
	public int size()
	{
		return N;
	}
	
	private void swim(int k)
	{
		while (k >= 2)
		{
			int parent = k/2;
			int cmp = compare(parent, k);
			if (cmp > 0) break; 
			exch(parent, k);
			k = parent;
		}
	}
	
	private void sink(int k)
	{
		while (k <= N/2 )
		{
			int largerChild = k*2;
			if (largerChild < N - 1 && compare(largerChild, largerChild+1) < 0)
				largerChild++;
			if (compare(k, largerChild) > 0) break;
			exch(largerChild, k);
			k = largerChild;
		}
	}
	
	private void exch(int i, int j)
	{
		Key tmp = pq[i]; pq[i] = pq[j]; pq[j] = tmp;
	}
	
	private int compare(int i, int j)
	{
		if (comparator == null)
			return ((Comparable<Key>) pq[i]).compareTo(pq[j]);
		else
			return comparator.compare(pq[i], pq[j]);
	}
	
	public Key delMax()
	{
		Key res = pq[1];
		exch(1,N--);
		pq[N+1] = null; // release reference
		sink(1);
		
		if (N <= size / 4) resize(size/2);
		return res;
	}

	@Override
	public Iterator<Key> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private class HelpIterator implements Iterator<Key>
	{
		
		private MaxPQ<Key> copy;
		
		public HelpIterator() {
			copy = new MaxPQ<Key>(size());
			for (int i = 1; i <= N; i++)
				copy.insert(pq[i]);
		}
		
		public boolean hasNext()
		{
			return !copy.isEmpty();
		}

		public Key next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			return copy.delMax();
		}
		
	}
}
