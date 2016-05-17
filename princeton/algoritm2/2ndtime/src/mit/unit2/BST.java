package mit.unit2;

import java.util.Comparator;

import edu.princeton.cs.algs4.Queue;

public class BST<Key, Value> extends ST<Key, Value>
{
	private Node root;
	private Comparator<Key> comparator;
	private class Node 
	{
		Key k;
		Value v;
		int count;
		Node left, right;
		int height;
		
		public Node(Key key, Value val)
		{
			k = key;
			v = val;
			height = 0;
		}
	}
	
	public void put(Key key, Value val)
	{
		root = put(root, key, val);
	}
	
	private Node put(Node n, Key key, Value val)
	{
		if (n == null) return new Node(key, val);
		int cmp = compare(key, n.k);
		if (cmp == 0) n.v = val;
		if (cmp > 0) n.right = put(n.right, key, val);
		else n.left = put(n.left, key, val);
		n.count = count(n);
		return n;
	}

	public Value get(Key key)
	{
		Node n = get(root, key);
		if (n != null) return n.v;
		return null;
	}
	
	private Node get(Node n, Key key)
	{
		if (n == null) return null;
		int cmp = compare(key, n.k);
		if (cmp == 0) return n;
		else if (cmp > 1) return get(n.right, key);
		else return get(n.left, key);
	}

	public void delete(Key key)
	{
		
		// TODO Auto-generated method stub
	}

	public boolean contains(Key key)
	{
		Node n = get(root, key);
		return n != null;
	}

	public boolean isEmpty()
	{
		return root == null;
	}

	public int size()
	{
		if (root == null) return 0;
		else return root.count;
	}

	private int compare(Key k1, Key k2)
	{
		if (comparator == null) 
			return ((Comparable) k1).compareTo(k2);
		return comparator.compare(k1, k2);
	}
	
	private int count(Node n)
	{
		if (n == null) return 0;
		n.count = 1 + count(n.left) + count(n.right);
		return n.count;
	}
	
	public Key min()
	{
		if (root == null) return null;
		Node n = min(root);
		return n.k;
	}
	
	private Node min(Node n){
		if (n.left == null) return n;
		return min(n.left);
	}
	
	public Key max() 
	{
		if (root == null) return null;
		Node n = max(root);
		return n.k;
	}
	
	private Node max(Node n)
	{
		if (n.right == null) return n;
		return max(n.right);
	}
	
	public Key floor(Key k)
	{
		Node n = floor(root, k);
		return n == null ? null : n.k;
	}
	
	private Node floor(Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp == 0) return n;
		if (cmp < 0) return floor(n.left, k);
		Node t = floor(n.right, k);
		if (t == null) return n;
		return t;
	}
	
	public Key ceiling(Key k)
	{
		Node n = ceiling(root, k);
		return n == null ? null : n.k;
	}
	
	private Node ceiling(Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp == 0) return n;
		if (cmp > 0) return ceiling(n.right, k);
		Node t = ceiling(n.left, k);
		if (t == null) return n;
		else return t;
	}
	
	/**
	 * return the kth largest (0,1,2,...,k-1)
	 * */
	public Key select(int k) 
	{
		Node n = select(root, k);
		return n == null? null : n.k;
	}
	
	private Node select(Node n, int k)
	{
		int t = count(n.left);
		if (t == k) return n;
		if (t > k) return select(n.left, k);
		return select(n.right, k - t - 1);
	}
	
	
	/**
	 * how many keys in this BST are smaller or equal than 
	 * the given key
	 * **/
	public int rank(Key key)
	{
		return rank(root, key);
	}
	
	private int rank(Node n, Key key)
	{
		if (n == null) return 0;
		int cmp = compare(key, n.k);
		if (cmp == 0) return count(n.left);
		if (cmp > 0) return 1 + count(n.left) + rank(n.right, key);
		return rank(n.left, key);
	}
	
	public void delMin()
	{
		root = delMin(root);
	}
	
	private Node delMin(Node n)
	{
		if (n == null) return null;
		if (n.left == null) return n.right;
		n.left = delMin(n.left);
		n.count = 1 + count(n.left) + count(n.right);
		return n;
	}
	
	public void delelte(Key key)
	{
		root = delete(root, key);
	}
	
	private Node delete(Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp < 0) return delete(n.left, k);
		else if (cmp > 0) return delete(n.right, k);
		else 
		{
			if (n.left == null) return n.right;
			if (n.right == null) return n.left;
			Node t = n;
			n = min(n.right); // find the successor
			n.left = t.left;
			n.right = delMin(t.right);
		}
		n.count = 1 + count(n.left) + count(n.right);
		//n.height = 1 + Math.max(n.left, b)
		return n;
	}
	
	public Iterable<Key> keys(Key lo, Key hi)
	{
		//java.util.Queue<Key> que = new java.util.LinkedList<Key>();
		Queue<Key> que = new Queue<Key>();
		keys(root, que, lo, hi);
		return que;
	}
	
	private void keys(Node n, Queue<Key> que, Key lo, Key hi)
	{
		if (n == null) return;
		int cmplo = compare(n.k, lo);
		int cmphi = compare(n.k, hi);
		if (cmplo < 0 || cmphi > 0) return;
		keys(n.left, que, lo, hi);
		que.enqueue(n.k);
		keys(n.right, que, lo, hi);
	}
	
	public Iterable<Key> keys() 
	{
		return keys(min(), max());
	}
	
	public int height()
	{
		return	height(root);
	}
	
	private int height(Node t)
	{
		if (t == null) return -1;
		return 1 + Math.max(height(t.left), height(t.right));
	}
	
	public int heightNonrecur()
	{
		if (root == null) return -1;
		else return root.height;
	}
	
	/**
	 * Non recursion version of get
	 * */
	public Key getNonRecur(Key key)
	{
		Node x = root;
		while (x != null)
		{
			int cmp = compare(key, x.k);
			if (cmp == 0) return x.k;
			if (cmp > 0) x = x.right;
			x = x.left;
		}
		return null;
	}
}
