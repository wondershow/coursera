package mit.unit2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class IndexMinPQ<Key> implements Iterable<Key>
{
	private int maxN;
	private int N;
	private Key[] keys;
	
	/* binary heap using 1-indexed heap, remember
	   that pq[1] = 3, pq[2] = 4 means, 3rd element 
	   in keys arryay is the root of the binary heap
	   NOTE that pq starts from 1
	*/
	private int[] pq;
	
	/*
	 * qp is the reverse of pq. 
	 * e.g. qp[ 3 ] = 5, means, the 3rd element in keys array
	 * is position 5 of the min-heap represented by pq.
	 * NOTE that qp starts from 0
	 * */
	private int[] qp;
	
	private Comparator<Key> comparator;
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public IndexMinPQ(int maxN)
	{
		if (maxN <= 0) throw new java.lang.IllegalArgumentException();
		this.maxN = maxN;
		pq = new int[maxN + 1];
		qp = new int[maxN + 1];
		keys =(Key[]) new Object[maxN + 1];
		for (int i = 1; i < maxN + 1; i++)
			qp[i] = -1; 
	}
	
	
	public void insert(int i, Key k)
	{
		N++;
		keys[i] = k;
		pq[N] = i;
		qp[i] = N;
		
		swim(N);
	}
	
	public void changeKey(int i, Key k)
	{
		if (qp[i] == -1) { insert(i, k); return; }
		keys[i] = k;
		int pqPos = qp[i];
		swim(pqPos);
		sink(pqPos);
	}
	
	/**
	 * Is index i associated with some key
	 * */
	public boolean contains(int i)
	{
		return qp[i] != -1;
	}
	
	/**
	 * delete index i and its associated key
	 * */
	public void delete(int i)
	{
		Key res = keys[pq[1]];
		
	}
	
	/**
	 * return minimum key
	 * */
	public Key minKey()
	{
		return keys[pq[1]];
	}
	
	/**
	 * returns the index of the minimum key
	 * **/
	public int minIndex()
	{
		return pq[1];
	}
	
	/**
	 * remove a minimal key and return its index
	 * **/
	public int delMin()
	{
		int res = pq[1];
		exch(1, N--);
		sink(1);
		qp[res] = -1;
		keys[res] = null;
		return res;
	}
	
	public boolean isEmpty()
	{
		return N == 0;
	}
	
	public int size()
	{
		return N;
	}
	
	/**
	 * return key associated with index i
	 * */
	public Key keyOf(int i)
	{
		if(!contains(i)) throw new java.lang.NullPointerException();
		return keys[i];
	}
	
	/**
	 * exchange two nodes in the 
	 * minpq
	 * */
	private void exch(int i, int j)
	{
		// tmp_i is the index of element in Keys array which rank i-th in the minpq
		int tmp_i = pq[i], tmp_j = pq[j];
		
		//the new rank of tmp_i-th index in the key element will be found at j-th position of pq
		qp[tmp_i] = j;
		qp[tmp_j] = i;
		
		pq[i] = tmp_j;
		qp[j] = tmp_i;
	}
	
	private void swim(int i)
	{
		while (i > 1) 
		{
			int parent = i / 2;
			if ( compare(parent,i) < 0) break;// child smaller than parent
			exch(i, parent);
			i = parent;
		}
	}
	
	private void sink(int i)
	{
		while (i <= N / 2)
		{
			int smallerChild = i * 2;
			if (smallerChild < N && compare(smallerChild, smallerChild+1) > 0) smallerChild++;
			if ( compare(i, smallerChild) < 0) break; 
			exch(i, smallerChild);
			i = smallerChild;
		}
	}
	
	
	/**
	 * Compare the two elements at the position i and j 
	 * of the minpq
	 * **/
	private int compare(int i, int j)
	{
		int pos_i = pq[i]; // the node i 's index in keys array
		int pos_j = pq[j];
		
		if (comparator == null)
			return ((Comparable<Key>)keys[pos_i]).compareTo(keys[pos_j]);
		else
			return comparator.compare(keys[pos_i], keys[pos_j]);
	}
	
	/**
	 * 
	 * */
	public Iterator<Key> iterator()
	{
		ArrayList<Key> res = new ArrayList<Key>();
		
		for (int i = 1; i <= N; i++)
			res.add(keys[pq[i]]);
		
		return null;
	}
}


	
