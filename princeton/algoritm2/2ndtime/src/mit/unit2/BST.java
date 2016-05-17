package mit.unit2;

import java.util.Comparator;

import edu.princeton.cs.algs4.Queue;

/**
 * This is the recursive version of Binary Search Tree
 * **/
public class BST1<Key, Value> extends ST<Key, Value>
{
	private Node root;
	private Comparator<Key> comparator;
	private class Node 
	{
		Key k;
		Value v;
		Node left, right;
		int count; //size of the tree rooted at this node
		int height;
		
		public Node(Key key, Value val)
		{
			k = key; v = val;
			count = 1; height = 0;
		}
	}
	
	/**
	 * returns true if this ST is empty
	 * */
	public boolean isEmpty()
	{
		return root == null;
	}
	
	/**
	 * returns the number of nodes in this bst
	 * */
	public int size()
	{
		return size(root);
	}
	
	//return the size of the subtree rooted at node n
	private int size(Node n)
	{
		if (n == null) return 0;
		return n.count;
	}
	
	/**
	 * Does the BST symbol table contain the given key?
	 * **/
	public boolean contains(Key key)
	{
		if (key == null) throw new NullPointerException("argument of contains is null");
		return get(root, key) != null;
	}
	
	/**
	 * return the value associated with the given key
	 * @param key the given key
	 * @return the value of the key given if the key is in the st or null if the key is 
	 * 		   not in the table
	 * @throws NullPointerException if key is null
	 * **/
	public Value get(Key key)
	{
		if (key == null) throw new NullPointerException("argument of contains is null");
		Node n = get(root, key);
		return n == null? null:n.v;
	}
	
	//return the node associated the a given key(k) in the subtree rooted at n
	private Node get(Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp == 0) return n;
		if (cmp > 0) return get(n.right, k);
		return get(n.left, k);
	}
	
	/**
	 * given a key-val pair, if the key is not present in the bst, insert this key-val 
	 * pair into the bst, if the key is already in the bst, update the value, if the val
	 * is null, delete the key in the bst
	 * @param key the key
	 * @param val the value
	 * @throws NullPointerException if key is null
	 * **/
	public void put(Key key, Value val)
	{
		if (key == null) throw new NullPointerException("argument of contains is null");
		if (val == null) { delete(key); return; }
		root = put(key, val, root);
	}
	
	private Node put(Key key, Value val, Node n)
	{
		if (n == null) return new Node(key, val);
		int cmp = compare(key, n.k);
		if (cmp == 0) n.v = val;
		else if (cmp > 0) n.right = put(key, val, n.right);
		n.left = put(key, val, n.left);
		
		/**
		 * TODO: How to analysis the overall complexity of the put
		 * fuction here? (is it h^2?)
		 * 
		 * Note that we have to put two recursion here
		 * since we want to update from a leaf all the way down
		 * to the root, if a new node is inserted/updated.
		 * but this operations seems to be a little bit redundant
		 * since only one branch should be updated.
		 * 
		 * Also note that this statment is after all the recursive
		 * calls, that means node closer to leaves carries this 
		 * udpate earlier than node closer to root.
		 * */
		n.count = 1 + size(n.right) + size(n.left);
		return n;
	}

	/**
	 * delete the key-val pair with the smallest key
	 * @throws NoSuchElementException when the bst is emtpy
	 * */
	public void deleteMin()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		root = delMin(root);
	}
	
	//delete the node with a minimal key in a bst(left most one)
	private Node delMin(Node n)
	{
		if (n.left == null) return null;
		n.left = delMin(n.left);
		//TODO update local counts varabbls
		n.count = size(n.left) + size(n.right) + 1;
		return n;
	}
	
	/**
	 * delete the key-val pair with the largest key
	 * @throws NoSuchElementException when the bst is emtpy
	 * */
	public void deleteMax()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		root = delMax(root);
	}
	
	private Node delMax(Node n)
	{
		if (n.right == null) return null;
		n.right = delMax(n.right);
		n.count = size(n.left) + size(n.right) + 1;
		return n;
	}
	
	/**
	 *Delete a key-val pair of the given key(when this key is present in the bst)
	 *@param the given key 
	 ***/
	public void delete(Key key)
	{
		if (key == null) throw new NullPointerException("argument of delete is null");
		delete(root, key);
	}
	
	private Node delete (Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp < 0) n.left = delete(n.left, k);
		else if (cmp > 0) n.right = delete(n.right, k);
		else 
		{
			if (n.left == null) return n.right;
			if (n.right == null) return n.left;
			Node t = n;
			n = max(n.right);
			n.right = delMin(t.right);
			n.left = t.left;
		}
		n.count = size(n.left) + size(n.right) + 1;
		return n;
	}
	
	/**
	 * return the smallest key in the st
	 * **/
	public Key min()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		return min(root).k;
	}
	
	//return the node with the min key rooted at a given node
	private Node min(Node n)
	{
		if (n.left == null) return n;
		return min(n.left);
	}
	
	public Key max()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		return max(root).k;
	}
	
	//return the node with the max key of the subtree rooted at node n
	private Node max(Node n)
	{
		if (n.right == null) return n;
		return max(n.right);
	}
	
	/**
	 * Returns the largest key in the st that is smaller or equal
	 ***/
	public Key floor(Key key)
	{
		if (key == null) throw new NullPointerException("argument of delete is null");
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		Node x = floor(root, key);
		return x == null ? null : x.k;
	}

	//return the node in rooted in n
	private Node floor(Node n, Key k)
	{
		if (n == null) return null;
		int cmp = compare(k, n.k);
		if (cmp == 0) return n;
		if (cmp < 0) return floor(n.left,k);
		Node t = floor(n.left, k);
		return t == null ? n:t;
	}
	
	/**
	 * returns the smallest key that is larger or equal than a given key
	 * **/
	public Key ceiling(Key key)
	{
		if (key == null) throw new NullPointerException("argument of delete is null");
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		Node x = ceiling(root, key);
		return x == null? null : x.k;
	}
	
	private Node ceiling(Node n, Key key)
	{
		if (n == null) return null;
		int cmp = compare(key, n.k);
		if (cmp == 0) return n;
		if (cmp > 0) return ceiling(n.right, key);
		Node t = ceiling(n.left, key);
		return t == null? n : t;
	}
	
	/**
	 * return the kth smallest key in the symbol table.
	 * **/
	public Key select(int k)
	{
		if (k < 0 || k >= size()) throw new IllegalArgumentException();
		Node x = select(root, k);
		return x.k;
	}
	
	private Node select(Node n, int k)
	{
		if (n == null) return null;
		int lsize = size(n.left);
		if (lsize == k) return n;
		else if (lsize > k) return select(n.left, k);
		return select(n.right, k - lsize - 1);
	}
	
	/**
	 * How many keys are strictly smaller than key
	 * */
	public int rank(Key key)
	{
		if (key == null) throw new NullPointerException("argument of delete is null");
		if (isEmpty()) throw new java.util.NoSuchElementException("Symbol table underflow");
		return rank(root, key);
	}
	
	private int rank(Node n, Key k)
	{
		if (n == null) return 0;
		int cmp = compare(k, n.k);
		if (cmp == 0) return size(n.left);
		if (cmp < 0) return rank(n.left, k);
		return size(n.left) + 1 + rank(n.right, k);
	}
	
	@Override
	public Iterable<Key> keys()
	{
		// TODO Auto-generated method stub
		Queue<Key> que = new Queue<Key>();
		keys(root, min(), max(), que);
		return que;
	}
	
	/**
	 * Return all the nodes whose keys are between lo and hi
	 * */
	public Iterable<Key> keys(Key lo, Key hi)
	{
		if (lo == null || hi == null) throw new NullPointerException("argument of keys is null");
		Queue<Key> que = new Queue<Key>();
		keys(root, lo, hi, que);
		return que;
	}
	
	private void keys(Node n, Key lo, Key hi, Queue<Key> que)
	{
		if (n == null) return;
		int complo = compare(lo, n.k);
		int comphi = compare(hi, n.k);
		if (complo < 0 || comphi > 0) return;
		keys(n.left, lo, hi, que);
		que.enqueue(n.k); // do we have immutability problems here? TODO
		keys(n.right, lo, hi, que);
	}

	private int compare(Key k1, Key k2)
	{
		if (comparator == null)
			return ((Comparable<Key>)k1).compareTo(k2);
		else return comparator.compare(k1,k2);
	}
	
	public int size(Key lo, Key hi)
	{
		
	}
	
	public int height()
	{
		
	}
	
	public int levelOrder(Key key)
	{
		
	}
}
